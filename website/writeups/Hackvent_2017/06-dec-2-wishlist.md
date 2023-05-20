---
layout: writeup
title: 'Dec 2: Wishlist'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-Th3F-1fth-Pow3-r0f2-is32

---

## Challenge

*The fifth power of two*

Something happened to my wishlist, please help me.

[Get the Wishlist](writeupfiles/Wishlist.txt)

## Solution

This is clearly base-64 encoded, we decode, and still looks base64
encoded. Taking the hint
into account, we decode 32 times:

    $ cat Wishlist.txt | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
    base64 -d | base64 -d  | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
    base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
    base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
    base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d

    HV17-Th3F-1fth-Pow3-r0f2-is32%
{: .language-bash}

