---
layout: writeup
title: 'Dec 5: Boolean Fun'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-2wGq-wOX3-T2oe-n8si-hZ0A

---

## Challenge

Santa found a paper with some strange logical stuff on it. On the back
of it there is the hint: "use 32 bit".

He has no clue what this means - can you show him, what "???" should be?

![](writeupfiles/dec5_chall.png)

## Solution

Just some bitwise logic operators, we can calculate answer in python as
follows:

    $ python
    >>> ~((4 | 7) ^ (1337 & 424242)) | 0xb055
    -291

Put `-291` in Ball-o-matic to get the bauble with the nugget

![](writeupfiles/ball_dec5.png)


