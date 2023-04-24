---
layout: writeup
title: 'Stage 2 Cryptography: Over The Hill'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{linear_algebra_plus_led_zeppelin_are_a_beautiful_m1xture}
---
## Challenge

Over the hills and far away... many times I've gazed, many times been
bitten. Many dreams come true and some have silver linings, I live for
my dream of a decrypted flag

    alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_{}"
    
    matrix = [[54, 53, 28, 20, 54, 15, 12, 7],
              [32, 14, 24, 5, 63, 12, 50, 52],
              [63, 59, 40, 18, 55, 33, 17, 3],
              [63, 34, 5, 4, 56, 10, 53, 16],
              [35, 43, 45, 53, 12, 42, 35, 37],
              [20, 59, 42, 10, 46, 56, 12, 61],
              [26, 39, 27, 59, 44, 54, 23, 56],
              [32, 31, 56, 47, 31, 2, 29, 41]]
    
    ciphertext = "7Nv7}dI9hD9qGmP}CR_5wJDdkj4CKxd45rko1cj51DpHPnNDb__EXDotSRCP8ZCQ"

## Solution

This looks like a [Hill Cipher][1]. We use `sympy` to decode this in
python:

    from sympy.crypto.crypto import decipher_hill
    from sympy import Matrix
    
    alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_{}"
    key = Matrix([[54, 53, 28, 20, 54, 15, 12, 7],
              [32, 14, 24, 5, 63, 12, 50, 52],
              [63, 59, 40, 18, 55, 33, 17, 3],
              [63, 34, 5, 4, 56, 10, 53, 16],
              [35, 43, 45, 53, 12, 42, 35, 37],
              [20, 59, 42, 10, 46, 56, 12, 61],
              [26, 39, 27, 59, 44, 54, 23, 56],
              [32, 31, 56, 47, 31, 2, 29, 41]])
    
    ciphertext = "7Nv7}dI9hD9qGmP}CR_5wJDdkj4CKxd45rko1cj51DpHPnNDb__EXDotSRCP8ZCQ"
    
    print decipher_hill(ciphertext, key, alphabet)
{: .language-python}

## Flag

    IceCTF{linear_algebra_plus_led_zeppelin_are_a_beautiful_m1xture}



[1]: https://en.wikipedia.org/wiki/Hill_cipher
