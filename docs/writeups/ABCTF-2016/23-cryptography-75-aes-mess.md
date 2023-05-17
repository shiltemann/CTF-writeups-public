---
layout: writeup
title: 'AES Mess'
level:
difficulty:
points: 75
categories: [crypto]
tags: []
flag: abctf{looks_like_you_can_break_aes}
---
## Challenge

We encrypted a flag with AES-ECB encryption using a secret key, and got
the hash:

    e220eb994c8fc16388dbd60a969d4953f042fc0bce25dbef573cf522636a1ba3fafa1a7c21ff824a5824c5dc4a376e75

However, we lost our plaintext flag and also lost our key and we can't
seem to decrypt the hash back :(.
Luckily we encrypted a bunch of other flags with the same key. Can you
recover the lost flag using [this](writeupfiles/aesmess.txt)?

## Solution

AES in ECB mode has the unfortunate characteristic that identical
plaintext, when encrypted with the same key always outputs the same
ciphertext,
since we are given a bunch of encryptions of different plaintext using
the same key, we can use this to find our decryption.

Each of the blocks in our given plaintext appears exactly in one of the
other encryption, so we can reconstruct the plaintext

    e220eb994c8fc16388dbd60a969d4953f042fc0bce25dbef573cf522636a1ba3fafa1a7c21ff824a5824c5dc4a376e75

    block1: e220eb994c8fc16388dbd60a969d4953
    block2: f042fc0bce25dbef573cf522636a1ba3
    block3: fafa1a7c21ff824a5824c5dc4a376e75


    abctf{looks_like_gospel_feebly}:
    e220eb994c8fc16388dbd60a969d4953 <-- // abctf{looks_like
    6d896bd7d6da9c4ce3eac5e4832c2f64     //_gospel_feebly}

    abctf{verism_evg_you_can_break_ajugas}:
    528c30c67c57968fa131684d07c1fa9c     // abctf{verism_evg
    f042fc0bce25dbef573cf522636a1ba3 <-- // _you_can_break_a
    c0bd6ceeec8e817f1be7b09a9a8b0fb8     // jugas}

    abctf{amidin_ogees}:
    5f0ec66748ad4e9c512616572dd9197b     // abctf{amidin_oge
    fafa1a7c21ff824a5824c5dc4a376e75 <-- // es}

so now we just put the three plaintext blocks together to obtain the
key:

    block1: e220eb994c8fc16388dbd60a969d4953   -   abctf{looks_like
    block2: f042fc0bce25dbef573cf522636a1ba3   -   _you_can_break_a
    block3: fafa1a7c21ff824a5824c5dc4a376e75   -   es}
