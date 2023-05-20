---
layout: writeup
title: Known Plaintext1
level: 
difficulty: 
points: 
categories: []
tags: []
flag: |-
  Test 1 wrong.

  Program input:
  "e Qqb:u6%<'\tm\x0b!OE=5;\n`N{PNm?.x*}$R"
  Expected output:
  '5272633b7637263d280:6e0c224g463e363c0b614f7c514f6e3g2f792b7e2553'
  Your program output:
  "e Qqb:u6%<'\tm\x0b!OE=5;\n"
---
**Challenge**  
Solve the problem using the programming interface. The input file is
knownplaintext1.in.

The input is formatted \[d/e\] <string>. The first character specifies
whether the assignment is to encrypt or to decrypt, and the string is
either the plaintext or the ciphertext, depending on the first
letter.</string>

**Solution**  
No idea what kind of encryption/decryption they want, but when we simply
output the input we get:

     Test 1 wrong.
    
    Program input:
    "e Qqb:u6%<'\tm\x0b!OE=5;\n`N{PNm?.x*}$R"
    Expected output:
    '5272633b7637263d280:6e0c224g463e363c0b614f7c514f6e3g2f792b7e2553'
    Your program output:
    "e Qqb:u6%<'\tm\x0b!OE=5;\n"

