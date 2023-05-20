---
layout: writeup
title: 'Reversing 200: assembly-1'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

What does `asm1(0x76)` return? Submit the flag as a hexadecimal value
(starting with `0x`).

NOTE: Your submission for this question will NOT be in the normal flag
format.  
Source located in the directory at
`/problems/assembly-1_0_cfb59ef3b257335ee403035a6e42c2ed`.

[asm1](writeupfiles/eq_asm_rev.S)

## Solution

    intel_syntax noprefix
    .bits 32
    
    .global asm1
    
    asm1:
    	push	ebp
    	mov	ebp,esp
    	cmp	DWORD PTR [ebp+0x8],0x98
    	jg 	part_a
    	cmp	DWORD PTR [ebp+0x8],0x8
    	jne	part_b
    	mov	eax,DWORD PTR [ebp+0x8]
    	add	eax,0x3
    	jmp	part_d
    part_a:
    	cmp	DWORD PTR [ebp+0x8],0x16
    	jne	part_c
    	mov	eax,DWORD PTR [ebp+0x8]
    	sub	eax,0x3
    	jmp	part_d
    part_b:
    	mov	eax,DWORD PTR [ebp+0x8]
    	sub	eax,0x3
    	jmp	part_d
    	cmp	DWORD PTR [ebp+0x8],0xbc
    	jne	part_c
    	mov	eax,DWORD PTR [ebp+0x8]
    	sub	eax,0x3
    	jmp	part_d
    part_c:
    	mov	eax,DWORD PTR [ebp+0x8]
    	add	eax,0x3
    part_d:
    	pop	ebp
    	ret
{: .language-asm}

we manually parse this code:

    asm1:                              ; 1: we start here
    	push	ebp
    	mov	ebp,esp
    	cmp	DWORD PTR [ebp+0x8],0x98   ; 2: we compare our input value (`0x76`) to `0x98`
    	jg 	part_a	                   ; 3: not greater than `0x98` so we do not jump
    	cmp	DWORD PTR [ebp+0x8],0x8    ; 4: now compare to `0x8`
    	jne	part_b                     ; 5: not equal so we jump to part_b
    	mov	eax,DWORD PTR [ebp+0x8]
    	add	eax,0x3
    	jmp	part_d
    part_a:
    	cmp	DWORD PTR [ebp+0x8],0x16
    	jne	part_c
    	mov	eax,DWORD PTR [ebp+0x8]
    	sub	eax,0x3
    	jmp	part_d
    part_b:
    	mov	eax,DWORD PTR [ebp+0x8]   ; 6: load our input value (`0x76`) to eax
    	sub	eax,0x3                   ; 7: subtract 3, eax now contains `0x73`
    	jmp	part_d                    ; 8: we jump to part_d
    	cmp	DWORD PTR [ebp+0x8],0xbc
    	jne	part_c
    	mov	eax,DWORD PTR [ebp+0x8]
    	sub	eax,0x3
    	jmp	part_d
    part_c:
    	mov	eax,DWORD PTR [ebp+0x8]
    	add	eax,0x3
    part_d:
    	pop	ebp
    	ret                          ; 9: return value in eax (`0x73`)

