---
layout: writeup
title: 'Day 13: Twelve steps of christmas'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{}
---
## Description

On the ninth day of Christmas my true love sent to me...

nineties style xls,  
eighties style compression,  
seventies style crypto,  
and the rest has been said previously.

[dec13.xls](writeupfiles/dec13.xls)

*Hints*

* Wait, Bread is on the Nice list? Better check that comment again...

## Solution

The comment reads

> Not a loaf of bread which is mildly disappointing 1f 9d 8c 42 9a 38 41
> 24 01 80 41 83 8a 0e f2 39 78 42 80 c1 86 06 03 00 00 01 60 c0 41 62
> 87 0a 1e dc c8 71 23 Why was the loaf of bread upset? His plan were
> always going a rye. How does bread win over friends? “You can crust
> me.” Why does bread hate hot weather? It just feels too toasty.

Putting those bytes in a file:

    $ cat out.bin | od -tx2 -a
    0000000    9d1f    428c    389a    2441    8001    8341    0e8a    39f2
             us  gs  ff   B sub   8   A   $ soh nul   A etx  nl  so   r   9
    0000020    4278    c180    0686    0003    0100    c060    6241    0a87
              x   B nul   A ack ack etx nul nul soh   `   @   A   b bel  nl
    0000040    dc1e    71c8    0023
             rs   \   H   q   #
    0000045
    $ file out.bin
    out.bin: compress'd data 12 bits
    $ cat out.bin| gzip -d | od -tx2 -a
    0000000    4d42    884e    0012    0000    0000    008a    0000    007c
              B   M   N  bs dc2 nul nul nul nul nul  nl nul nul nul   | nul
    0000020    0000    0227    0000    0227    0000    0001    0020    0003
            nul nul   ' stx nul nul   ' stx nul nul soh nul  sp nul etx nul
    0000040    0000    87c4    0012    0000    0000    0000    0000    0000
            nul nul   D bel dc2 nul nul nul nul nul nul nul nul nul nul nul
    0000060    0000    0000    0000
            nul nul nul nul nul nul
    0000066

