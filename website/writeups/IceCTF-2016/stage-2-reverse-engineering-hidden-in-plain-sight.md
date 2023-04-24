---
layout: writeup
title: 'Stage 2 Reverse Engineering: Hidden in Plain Sight'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{look_mom_I_found_it}
---
## Challenge

Make sure you take a real close look at it, it should be right there!
/home/plain\_sight/ or download it [here](writeupfiles/hidden)

## Solution

We get an ELF binary, when we run it we get

    Make sure you pay close attention!
    Okey, ready? (y/n)
    Sorry, too late!
    ....
    Dang, that was fast! Did you get it?

We disassemble the file and find this segment of asm code

    [..]
    8048512:	50                   	push   eax
    8048513:	e8 38 fe ff ff       	call   8048350 <fflush@plt>
    8048518:	83 c4 10             	add    esp,0x10
    804851b:	b0 49                	mov    al,0x49
    804851d:	b0 63                	mov    al,0x63
    804851f:	b0 65                	mov    al,0x65
    8048521:	b0 43                	mov    al,0x43
    8048523:	b0 54                	mov    al,0x54
    8048525:	b0 46                	mov    al,0x46
    8048527:	b0 7b                	mov    al,0x7b
    8048529:	b0 6c                	mov    al,0x6c
    804852b:	b0 6f                	mov    al,0x6f
    804852d:	b0 6f                	mov    al,0x6f
    804852f:	b0 6b                	mov    al,0x6b
    8048531:	b0 5f                	mov    al,0x5f
    8048533:	b0 6d                	mov    al,0x6d
    8048535:	b0 6f                	mov    al,0x6f
    8048537:	b0 6d                	mov    al,0x6d
    8048539:	b0 5f                	mov    al,0x5f
    804853b:	b0 49                	mov    al,0x49
    804853d:	b0 5f                	mov    al,0x5f
    804853f:	b0 66                	mov    al,0x66
    8048541:	b0 6f                	mov    al,0x6f
    8048543:	b0 75                	mov    al,0x75
    8048545:	b0 6e                	mov    al,0x6e
    8048547:	b0 64                	mov    al,0x64
    8048549:	b0 5f                	mov    al,0x5f
    804854b:	b0 69                	mov    al,0x69
    804854d:	b0 74                	mov    al,0x74
    804854f:	b0 7d                	mov    al,0x7d
    8048551:	c7 45 f4 00 00 00 00 	mov    DWORD PTR [ebp-0xc],0x0
    8048558:	eb 2f                	jmp    8048589 <main+0xde>
    [..]
{: .language-asm}

converting these hex values to characters gives the flag

## Flag

    IceCTF{look_mom_I_found_it}

