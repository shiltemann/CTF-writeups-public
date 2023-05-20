---
layout: writeup
title: 'December 4th: Easy One'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_7H15_15_7oO_345y_FOR_M3
---
## Category

Reversing

## Hint

*This is very easy crackme.*

## Challenge

We are given a binary: [easyone](files/easyone)

## Solution

Disassemble with objdump

    objdump -d -M intel easyone

We see this section that looks like it copies a series of characters to
memory which is compared to user input

    400625:	e8 c6 fe ff ff       	call   4004f0 <__isoc99_scanf@plt>
    40062a:	c6 45 d6 37          	mov    BYTE PTR [rbp-0x2a],0x37
    40062e:	c6 45 e2 33          	mov    BYTE PTR [rbp-0x1e],0x33
    400632:	c6 45 db 31          	mov    BYTE PTR [rbp-0x25],0x31
    400636:	c6 45 d7 48          	mov    BYTE PTR [rbp-0x29],0x48
    40063a:	c6 45 de 37          	mov    BYTE PTR [rbp-0x22],0x37
    40063e:	c6 45 e8 4f          	mov    BYTE PTR [rbp-0x18],0x4f
    400642:	c6 45 e4 35          	mov    BYTE PTR [rbp-0x1c],0x35
    400646:	c6 45 d9 35          	mov    BYTE PTR [rbp-0x27],0x35
    40064a:	c6 45 e0 4f          	mov    BYTE PTR [rbp-0x20],0x4f
    40064e:	c6 45 e6 5f          	mov    BYTE PTR [rbp-0x1a],0x5f
    400652:	c6 45 e5 79          	mov    BYTE PTR [rbp-0x1b],0x79
    400656:	c6 45 ec 33          	mov    BYTE PTR [rbp-0x14],0x33
    40065a:	c6 45 d2 43          	mov    BYTE PTR [rbp-0x2e],0x43
    40065e:	c6 45 e9 52          	mov    BYTE PTR [rbp-0x17],0x52
    400662:	c6 45 d8 31          	mov    BYTE PTR [rbp-0x28],0x31
    400666:	c6 45 d3 54          	mov    BYTE PTR [rbp-0x2d],0x54
    40066a:	c6 45 dc 35          	mov    BYTE PTR [rbp-0x24],0x35
    40066e:	c6 45 e7 46          	mov    BYTE PTR [rbp-0x19],0x46
    400672:	c6 45 d0 41          	mov    BYTE PTR [rbp-0x30],0x41
    400676:	c6 45 ea 5f          	mov    BYTE PTR [rbp-0x16],0x5f
    40067a:	c6 45 eb 4d          	mov    BYTE PTR [rbp-0x15],0x4d
    40067e:	c6 45 dd 5f          	mov    BYTE PTR [rbp-0x23],0x5f
    400682:	c6 45 da 5f          	mov    BYTE PTR [rbp-0x26],0x5f
    400686:	c6 45 df 6f          	mov    BYTE PTR [rbp-0x21],0x6f
    40068a:	c6 45 d5 5f          	mov    BYTE PTR [rbp-0x2b],0x5f
    40068e:	c6 45 e3 34          	mov    BYTE PTR [rbp-0x1d],0x34
    400692:	c6 45 d4 46          	mov    BYTE PTR [rbp-0x2c],0x46
    400696:	c6 45 e1 5f          	mov    BYTE PTR [rbp-0x1f],0x5f
    40069a:	c6 45 d1 44          	mov    BYTE PTR [rbp-0x2f],0x44
    40069e:	c6 45 ed 00          	mov    BYTE PTR [rbp-0x13],0x0
    4006a2:	48 8b 45 f8          	mov    rax,QWORD PTR [rbp-0x8]
    4006a6:	0f b6 10             	movzx  edx,BYTE PTR [rax]
    4006a9:	48 8b 45 f0          	mov    rax,QWORD PTR [rbp-0x10]
    4006ad:	0f b6 00             	movzx  eax,BYTE PTR [rax]
    4006b0:	38 c2                	cmp    dl,al
    4006b2:	74 11                	je     4006c5 <main+0xd8>

Run the program in gdb and set breakpoint at a location after all
characters have been copied (4006a2)

    (gdb) break *0x4006a2
    Breakpoint 1 at 0x4006a2
    (gdb) run
    Starting program: /media/AnanasHD/CTF/adctf2014/files/easyone
    password: bla

    Breakpoint 1, 0x00000000004006a2 in main ()

Now inspect memory at location indicated by register rbp

    (gdb) x/30c $rbp-0x30
    0x7fffffffdde0:	65 'A'	68 'D'	67 'C'	84 'T'	70 'F'	95 '_'	55 '7'	72 'H'
    0x7fffffffdde8:	49 '1'	53 '5'	95 '_'	49 '1'	53 '5'	95 '_'	55 '7'	111 'o'
    0x7fffffffddf0:	79 'O'	95 '_'	51 '3'	52 '4'	53 '5'	121 'y'	95 '_'	70 'F'
    0x7fffffffddf8:	79 'O'	82 'R'	95 '_'	77 'M'	51 '3'	0 '\000'

