---
layout: writeup
title: 'Reversing 200: quackme'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{qu4ckm3_9bcb819e}
---
**Challenge**  
Can you deal with the Duck Web? Get us the flag from [this
program](./writeupfiles/quackme)  
You can also find the program in
/problems/quackme\_0\_29c1eeadf7509d3b370e5d76c6fa54e5.

**Hints**  
Objdump or something similar is probably a good place to start.

## Solution

    $ objdump -s -d -j .rodata writeupfiles/quackme
    
    writeupfiles/quackme:     file format elf32-i386
    
    Contents of section .rodata:
     80487e8 03000000 01000200 596f7520 68617665  ........You have
     80487f8 206e6f77 20656e74 65726564 20746865   now entered the
     8048808 20447563 6b205765 622c2061 6e642079   Duck Web, and y
     8048818 6f752772 6520696e 20666f72 20612068  ou're in for a h
     8048828 6f6e6b69 6e272067 6f6f6420 74696d65  onkin' good time
     8048838 2e0a4361 6e20796f 75206669 67757265  ..Can you figure
     8048848 206f7574 206d7920 74726963 6b3f0000   out my trick?..
     8048858 2906164f 2b35301e 511b5b14 4b085d2b  )..O+50.Q.[.K.]+
     8048868 5c100606 18455100 5d004e6f 206c696e  \....EQ.].No lin
     8048878 65207265 61642e2e 2e000000 6d616c6c  e read......mall
     8048888 6f632829 20726574 75726e65 64204e55  oc() returned NU
     8048898 4c4c2e20 4f757420 6f66204d 656d6f72  LL. Out of Memor
     80488a8 790a0059 6f752061 72652077 696e6e65  y..You are winne
     80488b8 72210054 68617427 7320616c 6c20666f  r!.That's all fo
     80488c8 6c6b732e 00                          lks..
    
    Disassembly of section .rodata:
    
    080487e8 <_fp_hw>:
     80487e8:       03 00 00 00                                         ....
    
    080487ec <_IO_stdin_used>:
     80487ec:       01 00 02 00 59 6f 75 20 68 61 76 65 20 6e 6f 77     ....You have now
     80487fc:       20 65 6e 74 65 72 65 64 20 74 68 65 20 44 75 63      entered the Duc
     804880c:       6b 20 57 65 62 2c 20 61 6e 64 20 79 6f 75 27 72     k Web, and you'r
     804881c:       65 20 69 6e 20 66 6f 72 20 61 20 68 6f 6e 6b 69     e in for a honki
     804882c:       6e 27 20 67 6f 6f 64 20 74 69 6d 65 2e 0a 43 61     n' good time..Ca
     804883c:       6e 20 79 6f 75 20 66 69 67 75 72 65 20 6f 75 74     n you figure out
     804884c:       20 6d 79 20 74 72 69 63 6b 3f 00 00                  my trick?..
    
    08048858 <sekrutBuffer>:
     8048858:       29 06 16 4f 2b 35 30 1e 51 1b 5b 14 4b 08 5d 2b     )..O+50.Q.[.K.]+
     8048868:       5c 10 06 06 18 45 51 00 5d 00 4e 6f 20 6c 69 6e     \....EQ.].No lin
     8048878:       65 20 72 65 61 64 2e 2e 2e 00 00 00 6d 61 6c 6c     e read......mall
     8048888:       6f 63 28 29 20 72 65 74 75 72 6e 65 64 20 4e 55     oc() returned NU
     8048898:       4c 4c 2e 20 4f 75 74 20 6f 66 20 4d 65 6d 6f 72     LL. Out of Memor
     80488a8:       79 0a 00 59 6f 75 20 61 72 65 20 77 69 6e 6e 65     y..You are winne
     80488b8:       72 21 00 54 68 61 74 27 73 20 61 6c 6c 20 66 6f     r!.That's all fo
     80488c8:       6c 6b 73 2e 00                                      lks..

We see a very suspicious "sekrutBuffer". We extracted the bytes from
this buffer and trying XORing with 'picoCTF' in case that shows anything
useful:

    $ python
    Python 2.7.12 (default, Dec  4 2017, 14:50:18)
    [GCC 5.4.0 20160609] on linux2
    Type "help", "copyright", "credits" or "license" for more information.
    >>> bytes = [0x29, 0x06, 0x16, 0x4f, 0x2b, 0x35, 0x30, 0x1e, 0x51, 0x1b, 0x5b, 0x14, 0x4b, 0x08, 0x5d, 0x2b, 0x5c, 0x10, 0x06, 0x06, 0x18, 0x45, 0x51, 0x00, 0x5d]
    >>> pico = map(ord, 'picoCTF{}')
    >>> for x, y in zip(bytes, pico):
    ...     print(chr(x^y))
    ...
    Y
    o
    u
    
    h
    a
    v
    e
    ,
{: .language-python}

Which looks like the phrase we see provided in the message. We try
XORing that phrase against the bytes of the `sekrutBuffer` and:

    bytes = [0x29, 0x06, 0x16, 0x4f, 0x2b, 0x35, 0x30, 0x1e, 0x51, 0x1b, 0x5b, 0x14, 0x4b, 0x08, 0x5d, 0x2b, 0x5c, 0x10, 0x06, 0x06, 0x18, 0x45, 0x51, 0x00, 0x5d]
    pico = map(ord, "You have now entered the Duck Web, and you're in for a honkin' good time.")
    
    buf = ''
    for x, y in zip(bytes, pico):
        buf += chr(x ^ y)
    
    print(''.join(buf))

Outputs our flag

