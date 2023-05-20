---
layout: writeup
title: 'Forensics 200: Truly an Artist'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{look_in_image_788a182e}
---
## Challenge

Can you help us find the flag in this
[Meta-Material](./writeupfiles/2018.png)?

## Solution

    $ exiftool writeupfiles/2018.png
    ExifTool Version Number         : 10.10
    File Name                       : 2018.png
    Directory                       : writeupfiles
    File Size                       : 13 kB
    File Modification Date/Time     : 2018:09:29 11:37:17+02:00
    File Access Date/Time           : 2018:09:29 11:37:17+02:00
    File Inode Change Date/Time     : 2018:09:29 11:37:33+02:00
    File Permissions                : rw-rw----
    File Type                       : PNG
    File Type Extension             : png
    MIME Type                       : image/png
    Image Width                     : 1200
    Image Height                    : 630
    Bit Depth                       : 8
    Color Type                      : RGB
    Compression                     : Deflate/Inflate
    Filter                          : Adaptive
    Interlace                       : Noninterlaced
    Artist                          : picoCTF{look_in_image_788a182e}
    Image Size                      : 1200x630
    Megapixels                      : 0.756

