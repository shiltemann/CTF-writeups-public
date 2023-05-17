---
layout: writeup

title: Unicorn
level: 3         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [forensics]  # e.g. crypto, pwn, reversing
tags: [binwalk]        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{1_c_un1c0rns_3v3rywh3r3!}

---

## Challenge

![](writeupfiles/unicorn.png)

## Solution

```bash
$ binwalk unicorn.png

DECIMAL       HEXADECIMAL     DESCRIPTION
--------------------------------------------------------------------------------
0             0x0             PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
54            0x36            TIFF image data, big-endian, offset of first image directory: 8
106516        0x1A014         PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
106570        0x1A04A         TIFF image data, big-endian, offset of first image directory: 8
213032        0x34028         PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
213086        0x3405E         TIFF image data, big-endian, offset of first image directory: 8
319548        0x4E03C         PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
319686        0x4E0C6         Zlib compressed data, best compression
352486        0x560E6         PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
352540        0x5611C         TIFF image data, big-endian, offset of first image directory: 8
459002        0x700FA         PNG image, 512 x 512, 8-bit/color RGBA, non-interlaced
459056        0x70130         TIFF image data, big-endian, offset of first image directory: 8
```

We can extract the images with:

```bash
$ binwalk -e --dd=png unicorn.png
```

We get several copies of the unicorn image, but also one of an easter egg with a QR code, score!

![](writeupfiles/unicorn-egg.png)


