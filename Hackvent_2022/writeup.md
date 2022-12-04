# HACKvent 2022


The annual advent calender from Hacking-lab

## Overview

Title               | Category    | Points | Flag
------------------- | ----------- | ------ | ------------------------------
[December 1](#day-1-qr-means-quick-reactions-right)      | Easy        | 2/1    | `HV22{HV22{I_CaN_HaZ_Al_T3h_QRs_Plz}`
[December 2]()      | Easy        | 2/1    | `HV22{}`
[December 3]()      | Easy        | 2/1    | `HV22{}`
[December 4]()      | Easy        | 2/1    | `HV22{}`
[December 5]()      | Easy        | 2/1    | `HV22{}`
[December 6]()      | Easy        | 2/1    | `HV22{}`
[December 7]()      | Easy        | 2/1    | `HV22{}`
[December 8]()      | Easy        | 2/1    | `HV22{}`
[December 8]()      | Easy        | 2/1    | `HV22{}`
[December 10]()      | Easy        | 2/1    | `HV22{}`
[December 10]()      | Easy        | 2/1    | `HV22{}`
[December 12]()      | Easy        | 2/1    | `HV22{}`
[December 13]()      | Easy        | 2/1    | `HV22{}`
[December 14]()      | Easy        | 2/1    | `HV22{}`
[December 15]()      | Easy        | 2/1    | `HV22{}`
[December 16]()      | Easy        | 2/1    | `HV22{}`
[December 17]()      | Easy        | 2/1    | `HV22{}`
[December 18]()      | Easy        | 2/1    | `HV22{}`
[December 19]()      | Easy        | 2/1    | `HV22{}`
[December 20]()      | Easy        | 2/1    | `HV22{}`
[December 21]()      | Easy        | 2/1    | `HV22{}`
[December 22]()      | Easy        | 2/1    | `HV22{}`
[December 23]()      | Easy        | 2/1    | `HV22{}`
[December 24]()      | Easy        | 2/1    | `HV22{}`


## Day 1: QR means quick reactions, right?

**Description**

Santa's brother Father Musk just bought out a new decoration factory. He sacked all the developers and tried making his own QR code generator but something seems off with it. Can you try and see what he's done wrong?

![](writeupfiles/hackvent2022_01.gif)

**Solution**

The gif cycles through multiple QR codes quickly, let's read all of them:

First, we extract each frame using imagemagich:

```bash
$ mkdir img
$ convert hackvent2022_01.gif -coalesce img/xx_%05d.png
```

We get [30 images](writeupfile/img/), all with different QR codes. Let's read all of them using zbar-tools

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

**Flag**

```
HV22{I_CaN_HaZ_Al_T3h_QRs_Plz}
```
## Day X: Title

**Description**

**Solution**

**Flag**


## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**

## Day X: Title

**Description**

**Solution**

**Flag**


