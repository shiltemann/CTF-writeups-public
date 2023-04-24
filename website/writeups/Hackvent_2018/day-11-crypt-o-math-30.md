---
layout: writeup
title: 'Day 11: Crypt-o-math 3.0'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

Last year's challenge was too easy? Try to solve this one, you h4x0r!

    c = (a * b) % p
    c=0x7E65D68F84862CEA3FCC15B966767CCAED530B87FC4061517A1497A03D2
    p=0xDD8E05FF296C792D2855DB6B5331AF9D112876B41D43F73CEF3AC7425F9
    b=0x7BBE3A50F28B2BA511A860A0A32AD71D4B5B93A8AE295E83350E68B57E5

finding "a" will give you the flag.

## Solution

    >>> import gmpy2
    >>> c = 0x7E65D68F84862CEA3FCC15B966767CCAED530B87FC4061517A1497A03D2
    >>> p = 0xDD8E05FF296C792D2855DB6B5331AF9D112876B41D43F73CEF3AC7425F9
    >>> b = 0x7BBE3A50F28B2BA511A860A0A32AD71D4B5B93A8AE295E83350E68B57E5
    >>> hex(gmpy2.divm(c,b,p))
    '0x485631382d4288bb2cdf615fc4576b25ba2ee4c74f5e8598ba6bbdfae8f'
{: .language-python}

This looks to start with `HV18`, but the rest of the flag is not valid
ascii. In modular arithmetic however, `a + p % p = a` and `a + 2p % p =
a` etc, so the modulus may be added any number of times and still give
the same anwer. Therefore, we try adding `p` some number of times to the
answer to get the real flag:

    import binascii
    import gmpy2
    
    def int_to_text(i):
        hex_string = '%x' % i
        n = len(hex_string)
        return binascii.unhexlify(hex_string.zfill(n + (n & 1)))
    
    
    c=0x7E65D68F84862CEA3FCC15B966767CCAED530B87FC4061517A1497A03D2
    p=0xDD8E05FF296C792D2855DB6B5331AF9D112876B41D43F73CEF3AC7425F9
    b=0x7BBE3A50F28B2BA511A860A0A32AD71D4B5B93A8AE295E83350E68B57E5
    
    a = gmpy2.divm(c,b,p)
    
    while True:
        pt = int_to_text(a)
        if pt.startswith('HV18'):
            print(pt)
            break
        a += p
{: .language-python}

And this script outputs: `HV18-xLvY-TeNT-YgEh-wBuL-bFfz`

