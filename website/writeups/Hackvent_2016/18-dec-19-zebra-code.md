---
layout: writeup
title: 'Dec 19: Zebra Code'
level:
difficulty:
points:
categories: []
tags: []
flag:
---

## Challenge

*Get it straight*

Get the key and the encrypted message.

## Solution

We're given a .png file with a black and white zebra, as well as an SVG
file
containing points. Naively assumed that points in SVG file map to
positions on
the zebra, and attempted to extract those pixels. The results are very
black/white like one would expect. However extracting those pixel values
doesn't
produce legible data. Additionally the number of points is different
from what we
would expect if that was simply the encoding (4 chars too long)

I wonder what it means to "get it straight". Maybe determine angles
between
points? No luck there either, still looks like garbage data.

Maybe an extra level of encryption between the two? One is a binary key
and the
other a binary message? Argh.


