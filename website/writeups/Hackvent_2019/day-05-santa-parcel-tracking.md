---
layout: writeup
title: 'Day 05: Santa Parcel Tracking'
level:
difficulty:
points:
categories: []
tags: []
flag: HV19{D1fficult_to_g3t_a_SPT_R3ader}
---
## Description

To handle the huge load of parcels Santa introduced this year a parcel
tracking system. He didn't like the black and white barcode, so he
invented a more solemn barcode. Unfortunately the common barcode readers
can't read it anymore, it only works with the pimped models santa owns.
Can you read the barcode?

![](./writeupfiles/dec05/157de28f-2190-4c6d-a1dc-02ce9e385b5c.png)

## Solution

Going pixel by pixel from left to right across a single stripe, we
extracted the RGB value of every stripe. They were all in ascii range so
we converted them to ascii.

    from PIL import Image
    import sys
    
    img = Image.open("157de28f-2190-4c6d-a1dc-02ce9e385b5c-line.png")
    pixels = img.load()
    (w, h) = img.size
    
    c = None
    for i in range(w):
        q = pixels[i, 0]
        if q != c:
            if q != (255, 255, 255):
                # sys.stdout.write(chr(q[0]))
                # sys.stdout.write(chr(q[1]))
                sys.stdout.write(chr(q[2]))
        c = q
{: .language-python}

and wrote out only the blue channel since visual inspection showed that
the blue channel had some `{}`s

    $ python3 tmp.py
    X8YIOF0ZP4S8HV19{D1fficult_to_g3t_a_SPT_R3ader}S1090OMZE0E3NFP6E%

