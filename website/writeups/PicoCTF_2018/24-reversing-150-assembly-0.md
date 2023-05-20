---
layout: writeup
title: 'Reversing 150: assembly-0'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

What does `asm0(0xd8,0x7a)` return? Submit the flag as a hexadecimal
value (starting with `0x`).

NOTE: Your submission for this question will NOT be in the normal flag
format. [Source](writeupfiles/intro_asm_rev.S)  
located in the directory at
`/problems/assembly-0_1_fc43dbf0079fd5aab87236bf3bf4ac63`.

## Solution

    .intel_syntax noprefix
    .bits 32
    
    .global asm0
    
    asm0:
    	push	ebp
    	mov	ebp,esp
    	mov	eax,DWORD PTR [ebp+0x8]
    	mov	ebx,DWORD PTR [ebp+0xc]
    	mov	eax,ebx
    	mov	esp,ebp
    	pop	ebp
    	ret
{: .language-asm}

we can deduce the output manually. `ret` will return the value of `eax`,
which was set to the value of `ebx` (`mov eax ebx`), and ebx was set do
the second argument we passed to the program (`mov	ebx,DWORD PTR
[ebp+0xc]`), which in this case was `0x7a`

