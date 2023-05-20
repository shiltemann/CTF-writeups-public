---
layout: writeup
title: 'Reversing 250: assembly-2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

What does `asm2(0x7,0x28)` return? Submit the flag as a hexadecimal
value (starting with `0x`).

NOTE: Your submission for this question will NOT be in the normal flag
format.  
Source located in the directory at
`/problems/assembly-2_4_f8bfecf223768f4cac035751390ea590`.

    .intel_syntax noprefix
    .bits 32
    
    .global asm2
    
    asm2:
    	push	ebp
    	mov	ebp,esp
    	sub	esp,0x10
    	mov	eax,DWORD PTR [ebp+0xc]
    	mov	DWORD PTR [ebp-0x4],eax
    	mov	eax,DWORD PTR [ebp+0x8]
    	mov	DWORD PTR [ebp-0x8],eax
    	jmp	part_b
    part_a:
    	add	DWORD PTR [ebp-0x4],0x1
    	add	DWORD PTR [ebp+0x8],0x76
    part_b:
    	cmp	DWORD PTR [ebp+0x8],0xa1de
    	jle	part_a
    	mov	eax,DWORD PTR [ebp-0x4]
    	mov	esp,ebp
    	pop	ebp
    	ret
{: .language-asm}

## Solution

Let's manually walk through the code and write down what happens:

    .intel_syntax noprefix
    .bits 32
    
    .global asm2
    
    ; call: asm2(0x7,0x28)
    
    asm2:
    	push	ebp
    	mov	ebp,esp
    	sub	esp,0x10
    	mov	eax,DWORD PTR [ebp+0xc]     ; eax = 0x28
    	mov	DWORD PTR [ebp-0x4],eax     ; var1 = 0x28
    	mov	eax,DWORD PTR [ebp+0x8]     ; eax = 0x7
    	mov	DWORD PTR [ebp-0x8],eax     ; var2 = 0x7
    	jmp	part_b                      ; jump to part_b
    part_a:
    	add	DWORD PTR [ebp-0x4],0x1         ; var1 += 1
    	add	DWORD PTR [ebp+0x8],0x76        ; var2 += 0x76
    part_b:
    	cmp	DWORD PTR [ebp+0x8],0xa1de  ; var2 > 0xa1de?  Y: return var1
    	jle	part_a                      ;                 N: add x076 to var2 and 1 to var1 (part_a)
    	mov	eax,DWORD PTR [ebp-0x4]     ; return var1
    	mov	esp,ebp
    	pop	ebp
    	ret
{: .language-asm}

so in pseudo code this is:

    count = 0x28
    num = 0x7
    
    while num < 0xa1de:
       count += 1
       num += 0x76
    
    return count
    
    # 0x28 + (0xa1de-0x7)/0x76 = 0x188
{: .language-python}

