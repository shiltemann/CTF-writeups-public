---
layout: writeup
title: 'Dec 7: imagination'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-aFsf-4ea1-2eGg-Llr4-pB5A

---
## Challenge

*... is the eye of the soul*

Imagine your quick response for today.

    0x1fc137f82a7a0dd05d76ebbcbb74815d82c720ff555fc018801f78baaf93c051d55e46346fd16dd457f54451df65fcec3a493768ffc00948aff4154e090627753ffebafa7ddd568860a87a3fd88eb

(filename M4pMy8it5.txt)

## Solution

"Imagine your quick response" and "map my bits" seem to suggest we need
to create a QR code from the bits of the given hexstring

After some manual investigation we see that the hex string (with a zero
prepended for padding) results in a binary string of exactly 625
characters, which is exactly 25 squared. This could represent a 25x25 QR
code image!
we convert the bits to pixels with a small python script:

    from PIL import Image
    from qrtools import QR
    import textwrap

    hexstring="01fc137f82a7a0dd05d76ebbcbb74815d82c720ff555fc018801f78baaf93c051d55e46346fd16dd457f54451df65fcec3a493768ffc00948aff4154e090627753ffebafa7ddd568860a87a3fd88eb"
    data=int(hexstring,16)
    binstring = bin(data)[2:]

    # print bits in 25x25 square
    print textwrap.fill(binstring,width=25)

    # does look like a qr code, let's make an image from the bits!
    outimg = Image.new( 'RGB', (25,25), "black")
    pixels_out = outimg.load()

    count=0
    for bit in binstring:
        i=count%25
        j=count/25
        if bit == '0':
            pixels_out[(i,j)]=(255,255,255)
        count += 1

    outimgname = "dec7_qrout.png"
    outimg = outimg.resize((250,250))
    outimg.save(outimgname,"png")

    # read the QR code and output the encoded text
    myCode = QR(filename=outimgname)
    if myCode.decode():
        print myCode.data_to_string()
{: .language-python}

Which outputs the following

    1111111000001001101111111
    1000001010100111101000001
    1011101000001011101011101
    1011101011101111001011101
    1011101001000000101011101
    1000001011000111001000001
    1111111010101010101111111
    0000000001100010000000000
    1111101111000101110101010
    1111100100111100000001010
    0011101010101011110010001
    1000110100011011111101000
    1011011011101010001010111
    1111010101000100010100011
    1011111011001011111110011
    1011000011101001001001001
    1011101101000111111111100
    0000000010010100100010101
    1111111010000010101010011
    1000001001000001100010011
    1011101010011111111111110
    1011101011111010011111011
    1011101010101101000100001
    1000001010100001111010001
    1111111011000100011101011

    HV15-aFsf-4ea1-2eGg-Llr4-pB5A

![](writeupfiles/dec7_qrout.png)



