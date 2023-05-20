---
layout: writeup
title: 'General Skills 200: what base is this?'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{delusions_about_finding_values_602fd280}
---
## Challenge

To be successful on your mission, you must be able read data represented
in different ways, such as hexadecimal or binary. Can you get the flag
from this program to prove you are ready? Connect with `nc
2018shell1.picoctf.com 14390`.

## Solution

we connect and are asked to convert some numbers to ascii:

    $ nc 2018shell1.picoctf.com 14390                                                                                                  [07-10-18 18:39:56]
    We are going to start at the very beginning and make sure you understand how data is stored.
    turtle
    Please give me the 01110100 01110101 01110010 01110100 01101100 01100101 as a word.
    To make things interesting, you have 30 seconds.
    Input:
    turtle
    Please give me the 636f756368 as a word.
    Input:
    couch
    Please give me the  154 141 155 160 160 157 163 164 as a word.
    Input:
    lamppost
    You got it! You're super quick!
    Flag: picoCTF{delusions_about_finding_values_602fd280}
{: .language-bash}

