---
layout: writeup
title: 'Matrix'
level: 3
difficulty:
points:
categories: [misc]
tags: []
flag: IceCTF{1F_y0U_l0oK_c1Os3lY_EV3rY7h1N9_i5_1s_4nD_0s}
---
## Challenge

I like to approach problems with a fresh perspective and try to
visualize the problem at hand.

    0x00000000
    0xff71fefe
    0x83480082
    0xbb4140ba
    0xbb6848ba
    0xbb4a80ba
    0x83213082
    0xff5556fe
    0xff5556fe
    0x00582e00
    0x576fb9be
    0x707ef09e
    0xe74b41d6
    0xa82c0f16
    0x27a15690
    0x8c643628
    0xbfcbf976
    0x4cd959aa
    0x2f43d73a
    0x5462300a
    0x57290106
    0xb02ace5a
    0xef53f7fc
    0xef53f7fc
    0x00402e36
    0xff01b6a8
    0x83657e3a
    0xbb3b27fa
    0xbb5eaeac
    0xbb1017a0
    0x8362672c
    0xff02a650
    0x00000000

## Solution

The description suggests we need to visualize this information, we
convert to binary and see it looks like a QR code:

    ........ ........ ........ ........
    11111111 .111...1 1111111. 1111111.
    1.....11 .1..1... ........ 1.....1.
    1.111.11 .1.....1 .1...... 1.111.1.
    1.111.11 .11.1... .1..1... 1.111.1.
    1.111.11 .1..1.1. 1....... 1.111.1.
    1.....11 ..1....1 ..11.... 1.....1.
    11111111 .1.1.1.1 .1.1.11. 1111111.
    11111111 .1.1.1.1 .1.1.11. 1111111.
    ........ .1.11... ..1.111. ........
    .1.1.111 .11.1111 1.111..1 1.11111.
    .111.... .111111. 1111.... 1..1111.
    111..111 .1..1.11 .1.....1 11.1.11.
    1.1.1... ..1.11.. ....1111 ...1.11.
    ..1..111 1.1....1 .1.1.11. 1..1....
    1...11.. .11..1.. ..11.11. ..1.1...
    1.111111 11..1.11 11111..1 .111.11.
    .1..11.. 11.11..1 .1.11..1 1.1.1.1.
    ..1.1111 .1....11 11.1.111 ..111.1.
    .1.1.1.. .11...1. ..11.... ....1.1.
    .1.1.111 ..1.1..1 .......1 .....11.
    1.11.... ..1.1.1. 11..111. .1.11.1.
    111.1111 .1.1..11 1111.111 111111..
    111.1111 .1.1..11 1111.111 111111..
    ........ .1...... ..1.111. ..11.11.
    11111111 .......1 1.11.11. 1.1.1...
    1.....11 .11..1.1 .111111. ..111.1.
    1.111.11 ..111.11 ..1..111 11111.1.
    1.111.11 .1.1111. 1.1.111. 1.1.11..
    1.111.11 ...1.... ...1.111 1.1.....
    1.....11 .11...1. .11..111 ..1.11..
    11111111 ......1. 1.1..11. .1.1....
    ........ ........ ........ ........

Let's make a prober image out of it and decode the QR code

    from PIL import Image
    import qrtools
    
    w=31
    h=31
    outimg = Image.new( 'RGB', (w,h), "white")
    pixels_out = outimg.load()
    
    
    m=[
    0x00000000,0xff71fefe,0x83480082,0xbb4140ba,
    0xbb6848ba,0xbb4a80ba,0x83213082,0xff5556fe,
    0xff5556fe,0x00582e00,0x576fb9be,0x707ef09e,
    0xe74b41d6,0xa82c0f16,0x27a15690,0x8c643628,
    0xbfcbf976,0x4cd959aa,0x2f43d73a,0x5462300a,
    0x57290106,0xb02ace5a,0xef53f7fc,0xef53f7fc,
    0x00402e36,0xff01b6a8,0x83657e3a,0xbb3b27fa,
    0xbb5eaeac,0xbb1017a0,0x8362672c,0xff02a650,
    0x00000000]
    
    m2 = [ list(bin(m[i])[2:].zfill(33)) for i in range(0,len(m)) ]
    
    # remove row and column 7 and 22 to fix our QR code
    m2.pop(7)
    m2.pop(21)
    for i in m2:
        i.pop(7)
        i.pop(21)
    
    # create image
    for i in range(0,h):
        for j in range(0,w):
          if m2[i][j] == '1':
              pixels_out[i,j] = (0,0,0)
    
    outimg = outimg.resize((256,256))
    outimg.save("matrix_out.png","png")
    
    # decode QR code
    qr = qrtools.QR()
    qr.decode(filename="matrix_out.png")
    print qr.data_to_string()
{: .language-python}

this gives us the following QR code and prints the flag for us.

![](writeupfiles/matrix_out.png)
