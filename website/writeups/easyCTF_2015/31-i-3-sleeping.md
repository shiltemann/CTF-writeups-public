---
layout: writeup
title: I &lt;3 SLEEPING
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

We received this string, but I have no idea what it means!

    3845281945283805284526053525260547380516453748164748478317454508

Hint: ARGH I CAN'T SLEEP PEOPLE KEEP STEALING MY ZZZZs . . . NO MORE
ZZZs /cries

P.S. You're overthinking it.

P.P.S. Blocks of 2

Final hint: sqrt(ABCDEFGHIJKLMNOPQRSTUVWXY). Where'd my Z's go?

## Solution

The "blocks of 2" hint combined with the hint that a letter is
"missing", leads us to suspect a Polybius cipher. Instead of comining I
and J into a single letter, we simply omit the Z.  
Also it seems like the first in each duo of letters is always 0-5, while
the second is always 5-10, so this leads to the following Polybius
square:

      0 1 2 3 4
    5 a b c d e
    6 f g h i j
    7 k l m n o
    8 p q r s t
    9 u v w x y

Using this to translate we getegid

    38 45 28 19 45 28 38 05 28 45 26 05 35 25 26 05 47 38 05 16 45 37 48 16 47 48 47 83 17 45 45 08
    s  e  r  v  e  r  s  a  r  e  h  a  d  c  h  a  o  s  a  g  e  n  t  g  o  t  o  s  l  e  e  p
    
    servers are had chaosagent go to sleep

