# Move sample files

The correct file structure is as follows:
- `input-plaintext/sample_input.txt`
- `output-ciphered/sample_ciphertext.txt`

Move `sample_ciphertext.txt` to `input-plaintext/`
Move `sample_ciphertext.txt` to `output-ciphered/`

update the .py scripts accordingly.

# Update `test-mono-alphabetic.sh` 

and include `crack-mono-alphabetic.py` to test the Mono-alphabetic Cipher.

# Create a set of test data 

- Plaintext: saved under `input-plaintext` folder
- Ciphered text: saved under `output-ciphered` folder, using `mono-alphabetic-cipher.py` and key `random-shuffle.txt`

# Crack using `Brute Force Key Generator` approach 

Script name: `crack-mono-alphabetic.py`:

- options: -f, --file to specify a file containing ciphertext (default: `sample_ciphertext.txt`)

- Input file `sample_ciphertext.txt` contains the ciphertext to be cracked.
- The goal is to generate all possible keys and test them against the ciphertext.

The algorithm will:
1. Read the ciphertext from the specified file.
2. Generate all possible mono-alphabetic substitution keys.
3. For each key, decrypt the ciphertext.
4. Check if the decrypted text matches a known English word list or has a high enough score based on letter frequency analysis.
5. Output the decrypted text and the corresponding key if a match is found.

# Talk to the AI 

Approaches to brute-force / crack the ciphered text `sample_ciphertext.txt` 

give me a plan, approaches only, not the code for the crack, and save it to `CRACK-MONO-ALPHABETIC.md`

# Create `README.md` 

The structure of the file 

- repo overview 
- the algorithm
- how to setup the project (python with .vevn )
- explain random-shuffle.txt
- explain cypher and decrypt scripts
- explain test-mono-alphabetic.sh
- how to run the script
- include the scripts execution examples (with input and output)

# update `test-mono-alphabetic.sh`

- First, encrypt `sample_input.txt` using `mono-alphabetic-cipher.py`.
- Then, decrypt the resulting ciphertext using `mono-alphabetic-decrypt.py`.

# Create `.gitignore` for this Python project 

# Implement `mono-alphabetic-decrypt.py`

Implement `mono-alphabetic-decrypt.py` to decrypt a Mono-alphabetic Cipher.
- It should accept a ciphertext string or a file containing ciphertext and output the plaintext.
- options: -f, --file to specify a file containing ciphertext.

Also update `mono-alphabetic-cipher.sh` to include the decryption functionality.

# Implement `mono-alphabetic-cipher.sh`
Implement `mono-alphabetic-cipher.sh` to test the Mono-alphabetic Cipher.
- It should accept a plaintext string or a file containing plaintext and output the ciphertext.

# Mono-alphabetic Cipher

Implement Mono-alphabetic Cipher (mono-alphabetic-cipher.py)

Input:
- A string of plaintext
- or a file (-f, --file option) containing plaintext

Output:
- Ciphertext

Syntax:
```bash
mono-alphabetic-cipher.py "SEND THE MONEY TO ALICE"
``` 

or
```bash
mono-alphabetic-cipher.py -f /path/to/input.txt
```

`random-shuffle.txt` contains a random mapping of the alphabet.
-> help me create this file

• Rather than just shifting the alphabet, we could shuffle (jumble)
the letters arbitrarily
• Each plaintext letter maps to a randomly selected letter
• Hence, key is 26 letters long:
- Plain: abcdefghijklmnopqrstuvwxyz
- Cipher: DKVQFIBJWPESCXHTMYAUOLRGZN
- Plaintext: SEND THE MONEY TO AliCE
- Ciphertext: AFXQ UJF CHXFZ UH DSWVF