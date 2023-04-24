---
layout: writeup
title: 'Day 05: Image DNA'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{s4m3s4m3bu7diff3r3nt}
---
## Description

Santa has thousands of Christmas balls in stock. They all look the same,
but he can still tell them apart. Can you see the difference?

![](writeupfiles/dec5-1.jpg)  
![](writeupfiles/dec5-2.jpg)

## Solution

running `strings` on the images reveals a DNA sequence, hint?

    $ strings dec5-1.jpg
    JFIF
    $3br
    [..]
    f?*~
    kh[F;-8B
    VW}L
    CTGTCGCGAGCGGATACATTCAAACAATCCTGGGTACAAAGAATAAAACCTGGGCAATAATTCACCCAAACAAGGAAAGTAGCGAAAAAGTTCCAGAGGCCAAA
    
    
    $ strings dec5-2.jpg
    JFIF
    ICC_PROFILE
    lcms
    mntrRGB XYZ
    [..]
    Iwh]r
    =nr_
    ATATATAAACCAGTTAATCAATATCTCTATATGCTTATATGTCTCGTCCGTCTACGCACCTAATATAACGTCCATGCGTCACCCCTAGACTAATTACCTCATTC
{: .language-bash}

Aha! we see two DNA strings, of equal length, and looks about the right
length for a flag..

The challenge description refers to differences, so lets subtract these
two strings from each other  
We first convert to binary from this base-4 string

        bin base4
    A = 00  0
    C = 01  1
    G = 10  2
    T = 11  3

In this scheme, each group of 4 characters maps to 8 bits, and therefore
one ascii character.

Knowing that the result should start with `HV20{`, we experiment with
the first few characters, and find  
out that if we XOR the result of the above mapping, we get the right
answer:

    CTGT CGCG: 01111011 01100110
    ATAT ATAA: 00110011 00110000
    xor:       01001000 01010110
    ascii:     H        V

ok, this looks good! Let's automate the rest in Python:

    import binascii
    
    dna1="ATATATAAACCAGTTAATCAATATCTCTATATGCTTATATGTCTCGTCCGTCTACGCACCTAATATAACGTCCATGCGTCACCCCTAGACTAATTACCTCATTC"
    dna2="CTGTCGCGAGCGGATACATTCAAACAATCCTGGGTACAAAGAATAAAACCTGGGCAATAATTCACCCAAACAAGGAAAGTAGCGAAAAAGTTCCAGAGGCCAAA"
    
    # translate DNA strings to base-4 numbers
    map={"A": "0", "C": "1", "G":"2", "T":"3"}
    dna1b = dna1.translate(dna1.maketrans(map))
    dna2b = dna2.translate(dna2.maketrans(map))
    
    # xor the two numbers
    flag = int(dna1b,4)^int(dna2b,4)
    
    # convert back to ascii
    print(binascii.unhexlify('%x' % flag))
{: .language-python}

This outputs the flag :)

