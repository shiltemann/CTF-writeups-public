---
layout: writeup
title: 'Dec 5: Only One Hint'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-7pKs-whyz-o6wF-h4rp-Qlt6

---
## Challenge

Here is your flag:

    0x69355f71
    0xc2c8c11c
    0xdf45873c
    0x9d26aaff
    0xb1b827f4
    0x97d1acf4

and the one and only hint:

    0xFE8F9017 XOR 0x13371337

## Solution

`0xFE8F9017 XOR 0x13371337` is `0xedb88320` which is a polynomial
involved in CRC32, and indeed

`crc32('HV17')` is `0x69355f71`

Let's try to bruteforce the other parts:

    import binascii
    import itertools
    import string

    alphabet=string.printable

    ct = [0x69355f71, 0xc2c8c11c, 0xdf45873c, 0x9d26aaff, 0xb1b827f4, 0x97d1acf4]

    perms = list(itertools.product(alphabet, repeat=4))

    for p in perms:
        out = binascii.crc32( ''.join(p) ) & 0xffffffff # & 0xffffffff only needed in python2

        if out in ct:
            print("bingo! "+ tst +" ("+hex(out)+")")
{: .language-python}

which gives the following output:

    bingo! 7pKs (0xc2c8c11c)
    bingo! h4rp (0xb1b827f4)
    bingo! o6wF (0x9d26aaff)
    bingo! whyz (0xdf45873c)
    bingo! HV17 (0x69355f71)
    bingo! Qlt6 (0x97d1acf4)

whoo!

