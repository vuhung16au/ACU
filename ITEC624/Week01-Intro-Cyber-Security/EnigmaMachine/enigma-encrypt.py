#!/usr/bin/env python3
"""
Enigma Machine Implementation

A Python implementation of the Enigma machine, a cipher device used in World War II.
This is a command-line tool that simulates the operation of the Enigma machine.

Author: Enigma Machine Implementation
License: MIT
Version: 1.0.0
"""

import argparse
import json
import sys
from typing import Dict, List, Tuple, Optional
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class Rotor:
    """Represents a single rotor in the Enigma machine."""
    
    # Rotor wirings and notches based on historical Enigma specifications
    ROTOR_SPECS = {
        'I': {
            'wiring': 'EKMFLGDQVZNTOWYHXUSPAIBRCJ',
            'notch': 'Q',  # Notch at N position (Q in 0-indexed)
            'turnover': 'Q'
        },
        'II': {
            'wiring': 'AJDKSIRUXBLHWTMCQGZNPYFVOE',
            'notch': 'E',  # Notch at V position (E in 0-indexed)
            'turnover': 'E'
        },
        'III': {
            'wiring': 'BDFHJLCPRTXVZNYEIWGAKMUSQO',
            'notch': 'V',  # Notch at H position (V in 0-indexed)
            'turnover': 'V'
        }
    }
    
    def __init__(self, rotor_type: str, position: int = 0, ring_setting: int = 0):
        """
        Initialize a rotor.
        
        Args:
            rotor_type: Type of rotor (I, II, or III)
            position: Initial position (0-25, where 0=A, 1=B, etc.)
            ring_setting: Ring setting (0-25)
        """
        if rotor_type not in self.ROTOR_SPECS:
            raise ValueError(f"Invalid rotor type: {rotor_type}")
        
        self.rotor_type = rotor_type
        self.position = position
        self.ring_setting = ring_setting
        
        # Get rotor specifications
        spec = self.ROTOR_SPECS[rotor_type]
        self.wiring = spec['wiring']
        self.notch = spec['notch']
        self.turnover = spec['turnover']
        
        # Create forward and reverse mappings
        self.forward_map = {}
        self.reverse_map = {}
        
        for i, char in enumerate(self.wiring):
            self.forward_map[i] = ord(char) - ord('A')
            self.reverse_map[ord(char) - ord('A')] = i
    
    def step(self) -> bool:
        """
        Step the rotor and return True if the next rotor should also step.
        
        Returns:
            True if the next rotor should step (double-stepping mechanism)
        """
        should_step_next = False
        
        # Check if this rotor is at its notch position
        if chr(self.position + ord('A')) == self.turnover:
            should_step_next = True
        
        # Step the rotor
        self.position = (self.position + 1) % 26
        
        return should_step_next
    
    def encrypt_forward(self, input_char: int) -> int:
        """
        Encrypt a character in the forward direction.
        
        Args:
            input_char: Input character (0-25)
        
        Returns:
            Encrypted character (0-25)
        """
        # Apply ring setting offset
        adjusted_input = (input_char + self.ring_setting) % 26
        
        # Apply position offset
        adjusted_input = (adjusted_input + self.position) % 26
        
        # Apply wiring
        output = self.forward_map[adjusted_input]
        
        # Remove position offset
        output = (output - self.position) % 26
        
        # Remove ring setting offset
        output = (output - self.ring_setting) % 26
        
        return output
    
    def encrypt_reverse(self, input_char: int) -> int:
        """
        Encrypt a character in the reverse direction.
        
        Args:
            input_char: Input character (0-25)
        
        Returns:
            Encrypted character (0-25)
        """
        # Apply ring setting offset
        adjusted_input = (input_char + self.ring_setting) % 26
        
        # Apply position offset
        adjusted_input = (adjusted_input + self.position) % 26
        
        # Apply reverse wiring
        output = self.reverse_map[adjusted_input]
        
        # Remove position offset
        output = (output - self.position) % 26
        
        # Remove ring setting offset
        output = (output - self.ring_setting) % 26
        
        return output


class Reflector:
    """Represents the reflector in the Enigma machine."""
    
    REFLECTOR_SPECS = {
        'B': 'YRUHQSLDPXNGOKMIEBFZCWVJAT'
    }
    
    def __init__(self, reflector_type: str = 'B'):
        """
        Initialize a reflector.
        
        Args:
            reflector_type: Type of reflector (B)
        """
        if reflector_type not in self.REFLECTOR_SPECS:
            raise ValueError(f"Invalid reflector type: {reflector_type}")
        
        self.reflector_type = reflector_type
        self.wiring = self.REFLECTOR_SPECS[reflector_type]
        
        # Create mapping
        self.mapping = {}
        for i, char in enumerate(self.wiring):
            self.mapping[i] = ord(char) - ord('A')
    
    def reflect(self, input_char: int) -> int:
        """
        Reflect a character.
        
        Args:
            input_char: Input character (0-25)
        
        Returns:
            Reflected character (0-25)
        """
        return self.mapping[input_char]


class Plugboard:
    """Represents the plugboard in the Enigma machine."""
    
    def __init__(self, pairs: List[str] = None):
        """
        Initialize a plugboard.
        
        Args:
            pairs: List of plugboard pairs (e.g., ['AB', 'CD', 'EF'])
        """
        self.pairs = pairs or []
        self.mapping = {}
        
        # Create mapping
        for i in range(26):
            self.mapping[i] = i
        
        # Apply plugboard pairs
        for pair in self.pairs:
            if len(pair) != 2:
                raise ValueError(f"Invalid plugboard pair: {pair}")
            
            char1, char2 = pair[0].upper(), pair[1].upper()
            if not (char1.isalpha() and char2.isalpha()):
                raise ValueError(f"Invalid characters in plugboard pair: {pair}")
            
            idx1, idx2 = ord(char1) - ord('A'), ord(char2) - ord('A')
            
            # Check for conflicts
            if self.mapping[idx1] != idx1 or self.mapping[idx2] != idx2:
                raise ValueError(f"Plugboard pair conflicts with existing mapping: {pair}")
            
            self.mapping[idx1] = idx2
            self.mapping[idx2] = idx1
    
    def encrypt(self, input_char: int) -> int:
        """
        Encrypt a character through the plugboard.
        
        Args:
            input_char: Input character (0-25)
        
        Returns:
            Encrypted character (0-25)
        """
        return self.mapping[input_char]


class EnigmaMachine:
    """Main Enigma machine class."""
    
    def __init__(self, rotors: List[str], positions: List[str], 
                 ring_settings: List[int], plugboard_pairs: List[str] = None,
                 reflector_type: str = 'B'):
        """
        Initialize the Enigma machine.
        
        Args:
            rotors: List of rotor types (e.g., ['I', 'II', 'III'])
            positions: List of initial positions (e.g., ['A', 'B', 'C'])
            ring_settings: List of ring settings (e.g., [1, 1, 1])
            plugboard_pairs: List of plugboard pairs (e.g., ['AB', 'CD'])
            reflector_type: Type of reflector (default: 'B')
        """
        if len(rotors) != 3:
            raise ValueError("Exactly 3 rotors are required")
        if len(positions) != 3:
            raise ValueError("Exactly 3 positions are required")
        if len(ring_settings) != 3:
            raise ValueError("Exactly 3 ring settings are required")
        
        # Convert positions to integers
        position_ints = [ord(pos.upper()) - ord('A') for pos in positions]
        
        # Create components
        self.rotors = [
            Rotor(rotor, pos, ring - 1)  # Ring settings are 1-indexed
            for rotor, pos, ring in zip(rotors, position_ints, ring_settings)
        ]
        
        self.reflector = Reflector(reflector_type)
        self.plugboard = Plugboard(plugboard_pairs)
    
    def step_rotors(self):
        """Step the rotors according to Enigma stepping rules."""
        # Check if middle rotor is at its notch
        middle_at_notch = chr(self.rotors[1].position + ord('A')) == self.rotors[1].turnover
        
        # Check if right rotor is at its notch
        right_at_notch = chr(self.rotors[2].position + ord('A')) == self.rotors[2].turnover
        
        # Step right rotor (always steps)
        self.rotors[2].step()
        
        # Step middle rotor if right rotor was at notch
        if right_at_notch:
            self.rotors[1].step()
        
        # Step left rotor if middle rotor is at notch
        if middle_at_notch:
            self.rotors[0].step()
    
    def encrypt_char(self, char: str) -> str:
        """
        Encrypt a single character.
        
        Args:
            char: Input character (A-Z)
        
        Returns:
            Encrypted character (A-Z)
        """
        if not char.isalpha():
            return char
        
        # Convert to uppercase and to 0-25 range
        char = char.upper()
        char_idx = ord(char) - ord('A')
        
        # Step rotors
        self.step_rotors()
        
        # Plugboard
        char_idx = self.plugboard.encrypt(char_idx)
        
        # Forward through rotors
        for rotor in reversed(self.rotors):
            char_idx = rotor.encrypt_forward(char_idx)
        
        # Reflector
        char_idx = self.reflector.reflect(char_idx)
        
        # Reverse through rotors
        for rotor in self.rotors:
            char_idx = rotor.encrypt_reverse(char_idx)
        
        # Plugboard
        char_idx = self.plugboard.encrypt(char_idx)
        
        # Convert back to character
        return chr(char_idx + ord('A'))
    
    def encrypt_text(self, text: str) -> str:
        """
        Encrypt a text string.
        
        Args:
            text: Input text
        
        Returns:
            Encrypted text
        """
        result = ""
        for char in text:
            if char.isalpha():
                result += self.encrypt_char(char)
            else:
                result += char
        return result
    
    def decrypt_text(self, text: str) -> str:
        """
        Decrypt a text string (same as encryption for Enigma).
        
        Args:
            text: Input text
        
        Returns:
            Decrypted text
        """
        return self.encrypt_text(text)


def load_config(config_file: str) -> Dict:
    """
    Load configuration from a JSON file.
    
    Args:
        config_file: Path to configuration file
    
    Returns:
        Configuration dictionary
    """
    try:
        with open(config_file, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        raise FileNotFoundError(f"Configuration file not found: {config_file}")
    except json.JSONDecodeError:
        raise ValueError(f"Invalid JSON in configuration file: {config_file}")


def save_config(config: Dict, config_file: str):
    """
    Save configuration to a JSON file.
    
    Args:
        config: Configuration dictionary
        config_file: Path to configuration file
    """
    with open(config_file, 'w') as f:
        json.dump(config, f, indent=2)


def validate_input(text: str) -> str:
    """
    Validate and normalize input text.
    
    Args:
        text: Input text
    
    Returns:
        Normalized text (uppercase, letters only)
    """
    # Convert to uppercase and remove non-alphabetic characters
    normalized = ''.join(char.upper() for char in text if char.isalpha())
    
    if not normalized:
        raise ValueError("No valid alphabetic characters found in input")
    
    return normalized


def main():
    """Main function for command-line interface."""
    parser = argparse.ArgumentParser(
        description="Enigma Machine Implementation",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s -i "HELLO"
  %(prog)s -i "HELLO" -r "I,II,III" -p "A,B,C" --plugboard "AB,CD,EF"
  %(prog)s -i "HELLO" -o "output.txt" -d --positions "A,B,C"
        """
    )
    
    # Basic options
    parser.add_argument('-v', '--version', action='version', version='%(prog)s 1.0.0')
    parser.add_argument('-i', '--input', required=False, help='Input text or file path')
    parser.add_argument('-o', '--output', help='Output file path (defaults to stdout)')
    
    # Machine configuration
    parser.add_argument('-r', '--rotors', default='I,II,III', 
                       help='Rotor selection (default: I,II,III)')
    parser.add_argument('-p', '--positions', default='A,A,A',
                       help='Initial rotor positions (default: A,A,A)')
    parser.add_argument('-s', '--settings', default='1,1,1',
                       help='Ring settings (default: 1,1,1)')
    parser.add_argument('--plugboard', default='',
                       help='Plugboard pairs (e.g., AB,CD,EF)')
    parser.add_argument('--reflector', default='B', choices=['B'],
                       help='Reflector type (default: B)')
    
    # Operation modes
    parser.add_argument('-e', '--encrypt', action='store_true', default=True,
                       help='Encrypt mode (default)')
    parser.add_argument('-d', '--decrypt', action='store_true',
                       help='Decrypt mode')
    parser.add_argument('-c', '--config', help='Configuration file path')
    
    # Additional options
    parser.add_argument('--verbose', action='store_true', help='Verbose output')
    parser.add_argument('--test', action='store_true', help='Run unit tests')
    parser.add_argument('--validate', action='store_true', help='Validate configuration only')
    
    args = parser.parse_args()
    
    # Set up logging
    if args.verbose:
        logging.getLogger().setLevel(logging.DEBUG)
    
    try:
        # Load configuration if provided
        config = {}
        if args.config:
            config = load_config(args.config)
        
        # Parse rotor configuration
        rotors = args.rotors.split(',')
        positions = args.positions.split(',')
        ring_settings = [int(s) for s in args.settings.split(',')]
        plugboard_pairs = args.plugboard.split(',') if args.plugboard else []
        
        # Remove empty plugboard pairs
        plugboard_pairs = [pair for pair in plugboard_pairs if pair]
        
        # Validate configuration
        if args.validate:
            # Test machine creation
            machine = EnigmaMachine(rotors, positions, ring_settings, 
                                  plugboard_pairs, args.reflector)
            print("Configuration is valid!")
            return
        
        # Create Enigma machine
        machine = EnigmaMachine(rotors, positions, ring_settings, 
                              plugboard_pairs, args.reflector)
        
        # Handle validation mode
        if args.validate:
            print("Configuration is valid!")
            return
        
        # Check if input is provided
        if not args.input:
            print("Error: Input text or file path is required (use -i/--input)")
            sys.exit(1)
        
        # Read input
        if args.input.endswith('.txt') or '/' in args.input:
            try:
                with open(args.input, 'r') as f:
                    input_text = f.read().strip()
            except FileNotFoundError:
                print(f"Error: Input file not found: {args.input}")
                sys.exit(1)
        else:
            input_text = args.input
        
        # Validate and normalize input
        try:
            normalized_input = validate_input(input_text)
        except ValueError as e:
            print(f"Error: {e}")
            sys.exit(1)
        
        # Process text
        if args.decrypt:
            result = machine.decrypt_text(normalized_input)
        else:
            result = machine.encrypt_text(normalized_input)
        
        # Output result
        if args.output:
            with open(args.output, 'w') as f:
                f.write(result)
            print(f"Result written to: {args.output}")
        else:
            print(result)
    
    except Exception as e:
        print(f"Error: {e}")
        if args.verbose:
            import traceback
            traceback.print_exc()
        sys.exit(1)


if __name__ == '__main__':
    main()
