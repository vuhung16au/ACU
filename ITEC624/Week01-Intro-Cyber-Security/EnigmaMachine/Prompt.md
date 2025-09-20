# Step 1: Help me with a plan to crack engima machine 

``` 
Cracking the Enigma Code
A major flaw with the Enigma code was that a letter could never be encoded as itself. In other words, an “M” would never be encoded as an “M.” This was a huge flaw in the Enigma code because it gave codebreakers a piece of information they could use to decrypt messages. If the codebreakers could guess a word or phrase that would probably appear in the message, they could use this information to start breaking the code. Because the Germans always sent a weather report at the beginning of the message, and usually included the phrase “Heil Hitler” at the end of the message, there were phrases decrypters knew to look for. [8]Decoders could compare a given phrase to the letters in the code, and if a letter in the phrase matched up with a letter in the code, they knew that that part of the code did not contain the phrase. The decoders could then begin cracking the code with a process of elimination approach.
``` 

refer to the link below: 
https://brilliant.org/wiki/enigma-machine/

Write the plan to `CRACK_ENIGMA_MACHINE.md`

# Step 0: Implement Enigma Machine using Python

- Python implementation of the Enigma machine, a cipher device used in World War II.
- This is a command-line tool that simulates the operation of the Enigma machine.



## Inputs:
  - Text to be encrypted or decrypted.
  - Configuration settings for the machine (rotors, reflector, plugboard).

## Outputs:
  - Encrypted or decrypted text.

The algorithm 
- Use "Model M3 (Navy; Army) as implemented in Javascript" from the reference links (palloks.2ix.de/enigma/enigma-u_v262_en.html)

## Other Requirements:

- This is a command-line tool with clear help/usage, written in Python.
- Unit test coverage >90%
- Configuration file support?
- Implement the Enigma machine with a user-friendly interface.
- Allow users to select different rotor configurations and plugboard settings.
- The code includes classes for the Enigma machine, rotors, reflector, and plugboard.
- Ensure the code is well-documented and follows Python best practices.
- Include unit tests to verify the functionality of the Enigma machine.
- Provide a README file with instructions on how to run the code and use the Enigma machine.
- License the code under the MIT License.
- Character set handling: Only A-Z characters are processed, ignoring spaces and punctuation.
- Input validation requirements: uppercase only, no numbers/symbols. Convert lowercase to uppercase.
- Which reflectors to support: B
- Support for multiple rotors: I, II, III

- Python version compatibility: 3.9+

# CLI Options 

## Basic Options
-h, --help - Show help and usage information
-v, --version - Display version information
-i, --input - Input text or file path
-o, --output - Output file path (optional, defaults to stdout)

## Machine Configuration
-r, --rotors - Rotor selection (e.g., "I,II,III")
-p, --positions - Initial rotor positions (e.g., "A,A,A")
-s, --settings - Ring settings (e.g., "1,1,1")
--plugboard - Plugboard pairs (e.g., "AB,CD,EF")
--reflector - Reflector type (default: "B")

## Operation Modes
-e, --encrypt - Encrypt mode (default)
-d, --decrypt - Decrypt mode
-c, --config - Configuration file path

## Additional Options
--verbose - Verbose output
--test - Run unit tests
--validate - Validate configuration only

## Example usage scenarios

./enigma-encrypt.py -i "HELLO"
./enigma-encrypt.py -i "HELLO" -r "I,II,III" -p "A,B,C,D,E,F,G,H,I,J,K" --plugboard "AB,CD,EF" --reflector "B"
./enigma-encrypt.py -i "HELLO" -o "output.txt" -d --positions "A,B,C" 

## Configuration Details

- Default rotor configuration:
  - Rotor I: Notch at N
  - Rotor II: Notch at V
  - Rotor III: Notch at H

Plugboard pair limits (typically 0-10 pairs)

## References
- https://palloks.2ix.de/enigma/enigma-u_v262_en.html 
