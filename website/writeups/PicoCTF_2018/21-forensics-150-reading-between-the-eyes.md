---
layout: writeup
title: 'Forensics 150: Reading between the Eyes'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{r34d1ng_b37w33n_7h3_by73s}
---
## Challenge

Stego-Saurus hid a message for you in this image, can you retreive it?

![](writeupfiles/husky.png)

## Solution

If we extract the LSB of each channel, we see something hidden in the
first row of pixels

![](writeupfiles/husky_lsb_r.png)  
![](writeupfiles/husky_lsb_g.png)  
![](writeupfiles/husky_lsb_b.png)

in textual format:

    r: 01011110 00110101 00111110 01100011 01011011 01110010 01110110 01111001 01111010 01110010 01100110 0100000000000000000..
    g: 10010001 11100010 10010011 00111000 00111001 11110011 00111011 00111011 00110011 11110010 00110011 1110000000000000000..
    b: 10000101 11001100 01011100 10010100 10001101 01001001 11011001 10001111 11000001 01001111 11011101 1100000000000000000..

hmm, this doesnt look like it encodes ascii ..what now?

Let's think about what we know about the text that is hidden:

    picoCTF{ : 01110000 01101001 01100011 01101111 01000011 01010100 01000110 01111011

Aha! so the three channels are interlaced! the full string becomes:

    0111000001101001011000110110111101000011010101000100011001111011011100100011001100110100011
    0010000110001011011100110011101011111011000100011001100110111011101110011001100110011011011
    100101111100110111011010000011001101011111011000100111100100110111001100110111001101111101

and translates to our flag

