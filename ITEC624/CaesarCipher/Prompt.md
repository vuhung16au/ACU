# modify so that .py files can take files as input

- caesar_cipher.py
- caesar_decrypt.py

so that it takes input as files, or text.

Syntax: 

caesar_cipher.py "input text" 3 
or 
caesar_cipher.py -f /path/to/file/name.ext 3
or 
caesar_cipher.py --file /path/to/file/name.ext 3

encrypted text will be saved as /path/to/file/name.ext.cae

Syntax: 

caesar_decrypt.py "ciphered" 3 
or 
caesar_decrypt.py -f /path/to/file/name.ext.cae 3
or 
caesar_decrypt.py --file /path/to/file/name.ext.cae 3

also modify 

`brute_force_caesar.py`
`frequency_analysis_caesar.py`
so they can take file as input 

# Create test data 

Help me create some test text data for the project and save it under `test-data` folder

# Talk with the AI: 

How to use frequencies of alphabet letters to brute force the caesar cipher? 

## Letter Frequency Analysis Method

✅ **COMPLETED**: Implemented in `frequency_analysis_caesar.py`

The script uses chi-squared statistical analysis to compare letter frequencies in decrypted text against expected English letter frequencies. Lower chi-squared scores indicate better matches to English text patterns.

Sample usage:
```bash
python3 frequency_analysis_caesar.py "VqgwkhprqhwqDolfh"
```with the AI: 


How to use frequencies of alphabet letters to brute force the caesar cipher? 

## Letter Frequency Analysis Method
Pls implement the `Letter Frequency Analysis Method` in Python, save it to `frequency_analysis_caesar.py`
# Implement a script to brute-force the Caesar cipher

Script name: `brute_force_caesar.py`

Sample usage:

```bash
python brute_force_caesar.py "Vqg wkh prqh wq Dolfh"
```

This script will attempt to decrypt the given cipher text by trying all possible keys (from 1 to 25) and printing the results. It will help in understanding how the Caesar cipher can be brute-forced.

# Implement test script 

Script name: `test_caesar_cipher_decrypt.sh`

- First, encrypt sample text "Send the money to Alice" using the `caesar_cipher.py` script with a key of 3.
- Then, decrypt the resulting cipher text using the `caesar_decrypt.py` script with the same key.
- Finally, compare the decrypted text with the original plaintext to ensure they match.

# modify `caesar_cipher.py`:

- def caesar_cipher(text, key=3):

so that: 
- all non-alphabet characters in caesar_cipher() will be delete, including spaces

# Implement Caesar Decryption

## `caesar_decrypt.py`

Sample usage:

```bash
python caesar_decrypt.py "Vqg wkh prqh wq Dolfh"
python caesar_decrypt.py "Vqg wkh prqh wq Dolfh" 5
```

This script implements the decryption of the Caesar cipher. It takes a string input and an optional integer key (default is 3) and outputs the decrypted string.

# Implement Caesar Cipher

## `caesar_cipher.py`

Implement the Caesar cipher algorithm in Python. 
The script should take a string input and an integer key, then output the encrypted string.

The algorithm should handle both uppercase and lowercase letters, while ignoring spaces and non-alphabetic characters.

The algorithm is described in `README.md`

Sample usage:

```bash
python caesar_cipher.py "Send the money to Alice" (default key is 3)
python caesar_cipher.py "Send the money to Alice" 5
```
