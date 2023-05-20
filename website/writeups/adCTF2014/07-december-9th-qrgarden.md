---
layout: writeup
title: 'December 9th: QRgarden'
level:
difficulty:
points:
categories: []
tags: []
flag:  ADCTF_re4d1n9_Qrc0de_15_FuN
---
## Category

PPC

## Hint

*Read a lot, and the flag begins with "ADCTF\_".*

## Challenge

We are given a large image filled with QR codes.

![](files/qrgarden.png)

## Solution

We use zbar to read QR codes:

    sudo apt-get install zbar-tools

We split the image into the individual qr codes using imagemagick, then
read each QR code until we find one
starting with ADCTF\_

The full image is 8700x8700 pixels, and each qr code is 87x87 pixels. We
split this the large image into tiles:

    convert -crop 87x87 qrgarden.png qrcodes/tile_%d.png

Next we read all the images until we find the flag using zbar:

    $ for i in qrcodes/*; do zbarimg $i 2>&1; done |grep ADCTF
    QR-Code:ADCTF_re4d1n9_Qrc0de_15_FuN

