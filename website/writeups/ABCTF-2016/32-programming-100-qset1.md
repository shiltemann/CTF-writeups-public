---
layout: writeup
title: 'Programming 100: Qset1'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
**Challenge**   
I created my own programming language and wrote an interpreter for it!
[Here](writeupfiles/qset_interpreter.py) it is.

Can you create a program to multiply 2 inputs?

`nc 107.170.122.6 7771`

*Hint: Here's a program to add 2 inputs. `o0/i0,o0/i1`*

## Solution

    i0,i1,..,in   are inputs
    o0,o1,..,on   are outputs
    
    o0/i0,o0/i1         --> add two inputs
    o0/i0,i0/i1         --> add two inputs
    o0/a,a/i0,a/i1      --> add two inputs using temp variable
    o0/i0 i0            --> divided input by two
    o0/i0 i0 i0         --> divided input by three
    o0 a/i0             --> assign value of i0 to two variables at once (o0 and a)
    o0 o0/i0            --> multiply input by two
    o0 o0 o0/i0         --> multiply input by three

