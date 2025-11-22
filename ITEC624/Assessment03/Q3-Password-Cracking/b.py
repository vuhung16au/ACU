#!/usr/bin/env python3
"""
PDF Password Cracker using pdfcrack
Cracks a 6-digit numeric password for Act2.pdf
"""

import subprocess
import sys
import os
import time

def run_pdfcrack_brute_force(pdf_file):
    """
    Use pdfcrack's built-in brute force for 6-digit numeric passwords
    """
    print("Starting pdfcrack brute force attack...")
    print("Using pdfcrack's optimized brute force for 6-digit numeric passwords")
    print("-" * 60)
    
    try:
        # Use pdfcrack's built-in brute force for numeric passwords
        # -n sets minimum password length to 6
        # -m sets maximum password length to 6
        # -c '0123456789' uses only digits
        cmd = ['/opt/homebrew/bin/pdfcrack', '-f', pdf_file, '-n', '6', '-m', '6', '-c', '0123456789']
        
        print(f"Running command: {' '.join(cmd)}")
        print("Progress will be shown below:")
        print("-" * 60)
        
        # Run pdfcrack with real-time output
        process = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, 
                                 text=True, bufsize=1, universal_newlines=True)
        
        output_lines = []
        password_found = False
        found_password = None
        
        while True:
            output = process.stdout.readline()
            if output == '' and process.poll() is not None:
                break
            if output:
                line = output.strip()
                print(line)
                output_lines.append(line)
                
                # Check if password was found
                if "found user-password" in line.lower() or "password found" in line.lower():
                    password_found = True
                    # Extract password from the line
                    if ":" in line:
                        found_password = line.split(":")[-1].strip()
                    break
        
        process.wait()
        
        if password_found and found_password:
            print(f"\n{'='*60}")
            print(f"SUCCESS! Password found: {found_password}")
            print(f"{'='*60}")
            return found_password, "\n".join(output_lines)
        else:
            return None, "\n".join(output_lines)
            
    except Exception as e:
        return None, f"Error: {str(e)}"

def run_pdfcrack_wordlist(pdf_file):
    """
    Alternative method: Generate wordlist and use pdfcrack with wordlist
    """
    print("Trying alternative method with generated wordlist...")
    
    # Generate a wordlist of 6-digit numbers
    wordlist_file = "6digit_wordlist.txt"
    
    print(f"Generating wordlist: {wordlist_file}")
    with open(wordlist_file, 'w') as f:
        for i in range(1000000):
            f.write(f"{i:06d}\n")
    
    print(f"Wordlist generated with 1,000,000 entries")
    
    try:
        # Use pdfcrack with wordlist
        cmd = ['/opt/homebrew/bin/pdfcrack', '-f', pdf_file, '-w', wordlist_file]
        print(f"Running: {' '.join(cmd)}")
        
        result = subprocess.run(cmd, capture_output=True, text=True, timeout=300)  # 5 min timeout
        
        # Clean up wordlist
        os.remove(wordlist_file)
        
        if "found user-password" in result.stdout.lower():
            # Extract password
            lines = result.stdout.split('\n')
            for line in lines:
                if "found user-password" in line.lower():
                    password = line.split(":")[-1].strip()
                    return password, result.stdout
        
        return None, result.stdout
        
    except subprocess.TimeoutExpired:
        os.remove(wordlist_file)
        return None, "Timeout - password not found in reasonable time"
    except Exception as e:
        if os.path.exists(wordlist_file):
            os.remove(wordlist_file)
        return None, f"Error: {str(e)}"

def main():
    pdf_file = "Act2.pdf"
    
    if not os.path.exists(pdf_file):
        print(f"Error: {pdf_file} not found!")
        return
    
    print(f"Cracking password for {pdf_file}")
    print("Hint: 6-digit numeric password")
    print("=" * 60)
    
    # Try method 1: pdfcrack built-in numeric brute force
    print("METHOD 1: Using pdfcrack's built-in numeric brute force")
    password, output = run_pdfcrack_brute_force(pdf_file)
    
    if not password:
        print("\nMETHOD 1 failed. Trying METHOD 2...")
        print("METHOD 2: Using wordlist approach")
        password, output = run_pdfcrack_wordlist(pdf_file)
    
    if password:
        print(f"\n{'='*60}")
        print(f"CRACKED! Password: {password}")
        print(f"{'='*60}")
        
        # Save results to file
        with open("b.txt", "w") as f:
            f.write(f"Password: {password}\n")
            f.write(f"Output:\n{output}\n")
        
        print("Results saved to b.txt")
        
        # Try to extract content from the PDF
        print("\nAttempting to extract content from the PDF...")
        try:
            # Use pdftotext to extract content
            extract_cmd = ['pdftotext', '-layout', pdf_file, 'extracted_content.txt']
            subprocess.run(extract_cmd, check=True)
            
            if os.path.exists('extracted_content.txt'):
                with open('extracted_content.txt', 'r') as f:
                    content = f.read()
                    print("Content extracted successfully!")
                    print("Secret message:")
                    print("-" * 40)
                    print(content)
                    print("-" * 40)
                    
                    # Save content to b.txt
                    with open("b.txt", "a") as f:
                        f.write(f"\nSecret Message:\n{content}\n")
                
                # Clean up
                os.remove('extracted_content.txt')
        except Exception as e:
            print(f"Could not extract content: {e}")
    else:
        print("Failed to crack the password")
        print("Output:", output)

if __name__ == "__main__":
    main()
