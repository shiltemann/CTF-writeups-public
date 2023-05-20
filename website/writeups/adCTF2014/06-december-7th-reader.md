---
layout: writeup
title: 'December 7th: Reader'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_4R3_y0U_B4rC0d3_R34D3r
---
## Category

PPC

## Hint

*read 10 times*

## Challenge

nc adctf2014.katsudon.org 43010

## Solution

We connect to a service which sends us a set of characters resembling a
barcode.
The barcode is different every time, and seems like we need to decode 10
barcodes in row to get the flag.

We created a small python program to do this

