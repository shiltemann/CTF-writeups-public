---
layout: writeup
title: 'Reversing 400: assembly-3'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

What does `asm3(0xf238999b,0xda0f9ac5,0xcc85310c)` return?

Submit the flag as a hexadecimal value (starting with '0x').

NOTE: Your submission for this question will NOT be in the normal flag
format.  
Source located in the directory at
`/problems/assembly-3_2_504fe35f4236db611941d162e2abc6b9`.

    .intel_syntax noprefix
    .bits 32
    
    .global asm3
    
    asm3:
            push    ebp
            mov     ebp,esp
            mov     eax,0xb6
            xor     al,al
            mov     ah,BYTE PTR [ebp+0x8]
            sal     ax,0x10
            sub     al,BYTE PTR [ebp+0xf]
            add     ah,BYTE PTR [ebp+0xd]
            xor     ax,WORD PTR [ebp+0x12]
            mov     esp, ebp
            pop     ebp
            ret
{: .language-asm}

## Solution

We manually walk through the program:

    .intel_syntax noprefix
    .bits 32
    
    .global asm3
    
    # call: asm3(0xf238999b,    0xda0f9ac5,    0xcc85310c)
    #            0xf2 38 99 9b  0xda 0f 9a c5  0xcc 85 31 0c
    #   ebp+        b  a  9  8     f  e  d  c    13 12 11 10
    # OR:
    #         9b  99  38  f2  c5  9a  0f  da  0c  31  85  cc
    #   ebp+  8   9   a   b   c   d   e   f   10  11  12  13
    
    asm3:
            push    ebp
            mov     ebp,esp
            mov     eax,0xb6               ;
            xor     al,al                  ;
            mov     ah,BYTE PTR [ebp+0x8]  ; 0x9b
            sal     ax,0x10                ;
            sub     al,BYTE PTR [ebp+0xf]  ; 0xda
            add     ah,BYTE PTR [ebp+0xd]  ; 0x9a
            xor     ax,WORD PTR [ebp+0x12] ; 0xcc85
            mov     esp, ebp
            pop     ebp
            ret
{: .language-asm}

We can try to do this by hand, but much easier to use [this
emulator][1]. Since we cannot use that to calculate the values of `BYTE
PTR [ebp+...]` we need to be careful how we're doing that by hand.
Normally we see `DBL WORD PTR[ebp + 0x8/0xc/...]` which are nicely
aligned, but this one includes access that is not aligned to integer
boundaries. The numbers are stored in a little endian format in memory,
so when we're accessing them we need to be careful about that.

The eax/ax/al/ah registers are as in [this documentation][2].

           |-------------------|
    32-bit |        eax        |
           |-------------------|
    16-bit |         |   ax    |
           |-------------------|
     8-bit |    |    | ah | al |
           |-------------------|

We put the following program into the emulator and step through the
program and keep an eye on the  
value of eax (comments show value of eax at each step):

    asm3:
            push    ebp
            mov     ebp,esp
            mov     eax,0xb6        ; eax = 0x000000B6
            xor     al,al           ; eax = 0x00000000
            mov     ah,0x9b         ; eax = 0x00009b00
            sal     ax,0x10         ; eax = 0x00000000
            sub     al,0xda         ; eax = 0x00000026
            add     ah,0x9a         ; eax = 0x00009a26
            xor     ax,0xcc85       ; eax = 0x000056a3
            mov     esp, ebp
            pop     ebp
            ret                     ; return eax
{: .language-asm}

so final value is `0x56a3`, which is our flag



[1]: http://carlosrafaelgn.com.br/asm86/
[2]: https://wiki.skullsecurity.org/index.php?title=Registers
