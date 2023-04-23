---
layout: writeup

title: QR means quick reactions, right?
difficulty: easy
categories: []
tags: []

flag: HV22{I_CaN_HaZ_Al_T3h_QRs_Plz}

---

## Challenge

Santa's brother Father Musk just bought out a new decoration factory. He sacked all the developers and tried making his own QR code generator but something seems off with it. Can you try and see what he's done wrong?

![](writeupfiles/dec1/hackvent2022_01.gif)

## Solution

The gif cycles through multiple QR codes quickly, let's read all of them:

First, we extract each frame using imagemagich:

```bash
$ mkdir img
$ convert hackvent2022_01.gif -coalesce img/xx_%05d.png
```

We get [30 images](writeupfile/dec1/img/), all with different QR codes. Let's read all of them using zbar-tools

```bash
$ cd img
$ zbarimg .
QR-Code:H
QR-Code:V
QR-Code:2
QR-Code:2
QR-Code:{
QR-Code:I
QR-Code:_
QR-Code:C
QR-Code:a
QR-Code:N
QR-Code:_
QR-Code:H
QR-Code:a
QR-Code:Z
QR-Code:_
QR-Code:A
QR-Code:l
QR-Code:_
QR-Code:T
QR-Code:3
QR-Code:h
QR-Code:_
QR-Code:Q
QR-Code:R
QR-Code:s
QR-Code:_
QR-Code:P
QR-Code:l
QR-Code:z
QR-Code:}
scanned 30 barcode symbols from 30 images in 3.1 seconds

```

So each frame gives us one letter of the flag, putting them all togeher we get


