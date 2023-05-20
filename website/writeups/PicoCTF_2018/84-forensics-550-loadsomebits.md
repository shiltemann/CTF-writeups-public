---
layout: writeup
title: 'Forensics 550: LoadSomeBits'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{st0r3d_iN_tH3_l345t_s1gn1f1c4nT_b1t5_882756901}
---
## Challenge

Can you find the flag encoded inside this image? You can also find the
file in /problems/loadsomebits\_1\_5ccf71e5726692c713405bb17da5cb37 on
the shell server.

![](writeupfiles/pico2018-special-logo.bmp)

## Solution

    r: 0100100001111000111111000001010000011101010001011001110010011101010111001100010010011101011110010000110100001001000111000
    g: 0011001110111110101110000011111110110001101011110010101110101001000100011010111100101001101101010011001110110111001011100
    b: 0010011100010001100101011010011110100100011101110110001110100010011001011001011100100100010101001010011001110100001001000

target:

    picoCTF{ : 01110000 01101001 01100011 01101111 01000011 01010100 01000110 01111011

We don't know what the ordering or interpretation of these bits is.
Turning each string into ascii (with sliding windows) shows no useful
interpretation of any single string, therefore these are interleaved.

After trying to decode the various permutations we find that `bgr` is
the correct ordering to interleave the bits.

    import binascii
    
    # The bytes we found in the image
    r = '0010010000111100011111100000101000001110101000101100111001001110101011100110001001001110101111001000011010000100100011100'
    g = '0001100111011111010111000001111111011000110101111001010111010100100010001101011110010100110110101001100111011011100101110'
    b = '0001001110001000110010101101001111010010001110111011000111010001001100101100101110010010001010100101001100111010000100100'
    
    def rotate(s, offset):
        if offset == 0:
            q = '0b' + s
        else:
            q = '0b' + ('0' * offset) + s[0:-offset]
        q = "{0:088x}".format(int(q, 2))
        return q
    
    def interleave(a, b, c):
        for i in range(len(a)):
            yield a[i]
            yield b[i]
            yield c[i]
    
    # Convert a1a2a3, b1b2b3, c1c2c3 into a1b1c1a2b2c2a3b3c3
    d = ''.join(interleave(b, g, r))
    
    # Find a rotation (given the hint)
    q = rotate(d, 0)[0:88]
    
    # Convert to string and print out
    w = binascii.unhexlify(q).replace('\n', '').replace('\r', '')
    print(w)
{: .language-python}

We don't seem to have grabbed all of the bits of the flag for some
reason and only see

    r3d_iN_tH3_l345t_s1gn1f1c4nT_b1t5_882756901}

But we can guess that it's 'stored':

