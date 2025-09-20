# Create a file `COMPLEXITY.md` 

and describe how many possible keys we have to crack vigenere depending the lenght of key.

make it a markdown table. 

key length, number of possible keys 

The cracking performance on my Macbook Pro M1 Max: 168291 keys/sec

# Implement brute-force cracking

Update script `crack_vigenere.py` to include a brute-force cracking method for the Vigenère cipher.

Options: 

- `--brute-force` to enable brute-force cracking
- `--key-length` to specify the max key length for brute-force (default: 5)

Only use alphabetic characters for the key, ignoring case.

also update `test_vigenere.sh` to include tests for the brute-force cracking method for the case when the key length is 1 to 5 characters.

help me create a new cipher text file to test the brute-force cracking method `output-ciphertext/test_brute_force-key-len-5.txt`.

# add new tests for crack case: 

- when the key is not in dictionary (not common)
- when the key is 8, 10, 12 chars long

# update `readme.md` 

and include the run results of our .py scripts (cipher, decrypt and crack)

# Now we have created `crack_vigenere.py` 

- update the test script `test_vigenere.sh` to run the crack 
- update `readme.md` to mention the crack tool and algorithm to crack 

# Crack vigenere cipher 

script name: `crack_vigenere.py`:

Give the cipher text `output-ciphertext/test_encrypted.txt` (and `output-ciphertext/*encrypted.txt`)

Known that the cipher text is encrypted using some vigenere cipher algorithm.

Find a way to crack and help me find the the key (the correct key is: `vigenere.txt` and plantext is `inputs-plaintext/test_message.txt`)

# Create test data 

create more test data and save under folder `inputs-plaintext` 
cipher the files you've created, and save under folder `output-ciphertext`

# Implement `test_vigenere.sh` 

- run vigenere_cipher.py
- then run vigenere_decrypt.py

test using main options for the scripts above. 

# Implement `Vigenère Decryption` script

Scriptname: `vigenere_decrypt.py`

options: 
- `--key` to specify the key file
- `--encrypt` to encrypt a message
- `-f, --file` to read input from a file. Default is standard input.
- `-o, --output` to write output to a file. Default is standard output.

# Implement `Vigenère Cipher`

Scriptname: `vigenere_cipher.py`

key file: `vigenere.txt`
content:
```plaintext
# Vigenère Cipher Key
# This file contains the key for the Vigenère cipher.
# The key is a sequence of letters that will be used to encrypt and decrypt messages.
K = "security"
```

options: 
- `--key` to specify the key file
- `--encrypt` to encrypt a message
- `-f, --file` to read input from a file. Default is standard input.
- `-o, --output` to write output to a file. Default is standard output.

The algorithm:
```plaintext
• Simplest poly-alphabetic substitution cipher is the Vigenère
Cipher
• Effectively multiple Caesar ciphers
• Key is multiple letters long:
K = k1 k2 ... kd
ith letter specifies ith alphabet to use
• Use each alphabet in turn
• Repeat from start after d letters in message
```