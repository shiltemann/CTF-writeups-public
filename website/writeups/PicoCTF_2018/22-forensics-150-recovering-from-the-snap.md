---
layout: writeup
title: 'Forensics 150: Recovering from the snap'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{th3_5n4p_happ3n3d}
---
## Challenge

There used to be a bunch of [animals](writeupfiles/animals) here, what
did Dr. Xernon do to them?

## Solution

    $ binwalk animals.dd
    
    DECIMAL       HEXADECIMAL     DESCRIPTION
    --------------------------------------------------------------------------------
    39424         0x9A00          JPEG image data, JFIF standard 1.01
    39454         0x9A1E          TIFF image data, big-endian, offset of first image directory: 8
    672256        0xA4200         JPEG image data, JFIF standard 1.01
    1165824       0x11CA00        JPEG image data, JFIF standard 1.01
    1556992       0x17C200        JPEG image data, JFIF standard 1.01
    1812992       0x1BAA00        JPEG image data, JFIF standard 1.01
    1813022       0x1BAA1E        TIFF image data, big-endian, offset of first image directory: 8
    2136576       0x209A00        JPEG image data, JFIF standard 1.01
    2136606       0x209A1E        TIFF image data, big-endian, offset of first image directory: 8
    2607616       0x27CA00        JPEG image data, JFIF standard 1.01
    2607646       0x27CA1E        TIFF image data, big-endian, offset of first image directory: 8
    3000832       0x2DCA00        JPEG image data, JFIF standard 1.01
    3000862       0x2DCA1E        TIFF image data, big-endian, offset of first image directory: 8
    
    $ binwalk --dd='jpeg:jpg' animals.dd
{: .language-bash}

this gave us a bunch of animal images, and one image with the flag:

![](writeupfiles/animalsflag.jpg)

