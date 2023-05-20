---
layout: writeup
title: 'Reversing 550: assembly-4'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{1_h0p3_y0u_c0mP1l3d_tH15_94698637933
---
**Challenge**  
Can you find the flag using the following [assembly
source](./writeupfiles/comp.nasm)? WARNING: It is VERY long...

## Solution

They give us enough to compile it, so, we just do that. Most online
compilers  
are x64 for some reason, so compiling locally is a bit easier:

    $ nasm -f elf32 comp.nasm
    $ gcc -m32 comp.o -o comp
    $ ./comp
    picoCTF{1_h0p3_y0u_c0mP1l3d_tH15_94698637933

There's some garbage at the end, just add a `}` and remove digits until
it goes  
through. I don't know when precisely it went through but it's something
like  
that.

