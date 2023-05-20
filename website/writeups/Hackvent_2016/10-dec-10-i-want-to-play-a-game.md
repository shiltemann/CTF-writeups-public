---
layout: writeup
title: 'Dec 10: I want to play a Game'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-Vm5y-NjgH-e7tW-PgMa-61JH

---

## Challenge

Reversing Day 1: we'll start with an easy one.

[Gimme that thing!](writeupfiles/ReGame_Part1.zip)

## Solution

we unzip the file and find and a playstation executable:

    $ strings HV16.EXE
    PS-X EXE
    Sony Computer Entertainment Inc. for Japan area
    0123456789ABCDEF
    $Id: sys.c,v 1.140 1998/01/12 07:52:27 noda Exp yos $

    [..]

    wwc4$
    decrypt the flag you n00b
    KR40*^d?r!CdhX<w$\`B;G
    T{6*,TW
    Library Programs (c) 1993-1997 Sony Computer Entertainment Inc., All Rights Reserved.
{: .language-bash}

Hmm, the string `KR40*^d?..` looks like it might be an encrypted flag,
with some trial
and error we find that

    >>> ord('K') ^ ord('H')
    3
    >>> ord('R') ^ ord('V')
    4
    >>> ord('4') ^ ord('1')
    5
    >>> ord('0') ^ ord('6')
    6
{: .language-python}

Ok, that's a clear pattern! Let's do it for the whole string!

    s="KR40*^d?r!CdhX<w$`B;G\x7fT{6*,TW"

    pt=''
    count = 3
    for c in s:
        pt += chr(ord(c) ^ count)
        count += 1

    print pt
{: .language-python}


