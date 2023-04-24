---
layout: writeup

title: Global Egg Delivery
level: 3
difficulty: easy
categories: [misc]
tags: [unicode BOM]

flag: he2023{u7ƒ_b0m5s_8rᗱ_n07_8ㅣway5_1gn0rᗱd}

---

## Challenge

Thumper has taken great strides with the digitization of the business of distributing eggs and assorted goodies. Globalizing such a service is not without its pains and requires the additional effort to account for local customs.

Now Thumper has his message all prepared, fed through a block-chain enabled, micro-service driven, AI enhanced, zero trust translation service all that comes back is this...

Can you help Thumper decode the message?

[message.txt](writeupfiles/message.txt)

## Solution

By `cat`ing message.txt to the terminal we see:


Looking at the bytes,

```
$ cat message.txt | od -tx2 -a
0000000    feff    0068    fffe    6500    feff    0032    fffe    3000
        del   ~   h nul   ~ del nul   e del   ~   2 nul   ~ del nul   0
0000020    feff    0032    fffe    3300    feff    007b    fffe    7500
        del   ~   2 nul   ~ del nul   3 del   ~   { nul   ~ del nul   u
0000040    feff    0037    fffe    9201    feff    005f    fffe    6200
        del   ~   7 nul   ~ del soh dc2 del   ~   _ nul   ~ del nul   b
0000060    feff    0030    fffe    6d00    feff    0035    fffe    7300
        del   ~   0 nul   ~ del nul   m del   ~   5 nul   ~ del nul   s
0000100    feff    005f    fffe    3800    feff    0072    fffe    f115
        del   ~   _ nul   ~ del nul   8 del   ~   r nul   ~ del nak   q
0000120    feff    005f    fffe    6e00    feff    0030    fffe    3700
        del   ~   _ nul   ~ del nul   n del   ~   0 nul   ~ del nul   7
0000140    feff    005f    fffe    3800    feff    3163    fffe    7700
        del   ~   _ nul   ~ del nul   8 del   ~   c   1   ~ del nul   w
0000160    feff    0061    fffe    7900    feff    0035    fffe    5f00
        del   ~   a nul   ~ del nul   y del   ~   5 nul   ~ del nul   _
0000200    feff    0031    fffe    6700    feff    006e    fffe    3000
        del   ~   1 nul   ~ del nul   g del   ~   n nul   ~ del nul   0
0000220    feff    0072    fffe    f115    feff    0064    fffe    7d00
        del   ~   r nul   ~ del nak   q del   ~   d nul   ~ del nul   }
0000240
```

`0xfeff` and `0xffe` are [Unicode BOMs](https://en.wikipedia.org/wiki/Byte_order_mark). (Namely they should appear ONLY at the start)

> The BOM character is, simply, the Unicode codepoint U+FEFF ZERO WIDTH NO-BREAK SPACE, encoded in the current encoding. Traditionally, this codepoint is just a zero-width non-breaking space that inhibits line-breaking between word-glyphs. As such, if the BOM character appears in the middle of a data stream, Unicode says it should be interpreted as a normal codepoint, not as a BOM. Since Unicode 3.2, this usage has been deprecated in favor of U+2060 WORD JOINER.[1] - https://en.wikipedia.org/wiki/Byte_order_mark


So this is ,, incredibly invalid utf16. Fun! We can decode this manually by regexing the above into something like:

```
printf '\xfe\xff\x00\x68\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x65\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x32\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x30\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x32\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x33\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x7b\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x75\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x37\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x92\x01\x00' | iconv -f utf-16 -t utf-8 | uniname
```

And then seeing the result

```
16:55:38|(main) [user@p:~/projects/ctf-writeups-galaxians/HackyEaster_2023]$ bash message.sh 2>/dev/null | grep 0
        0          0  000068   68             h      LATIN SMALL LETTER H
        0          0  000065   65             e      LATIN SMALL LETTER E
        0          0  000032   32             2      DIGIT TWO
        0          0  000030   30             0      DIGIT ZERO
        0          0  000032   32             2      DIGIT TWO
        0          0  000033   33             3      DIGIT THREE
        0          0  00007B   7B             {      LEFT CURLY BRACKET
        0          0  000075   75             u      LATIN SMALL LETTER U
        0          0  000037   37             7      DIGIT SEVEN
        0          0  000192   C6 92          ƒ      LATIN SMALL LETTER F WITH HOOK
        0          0  00005F   5F             _      LOW LINE
        0          0  000062   62             b      LATIN SMALL LETTER B
        0          0  000030   30             0      DIGIT ZERO
        0          0  00006D   6D             m      LATIN SMALL LETTER M
        0          0  000035   35             5      DIGIT FIVE
        0          0  000073   73             s      LATIN SMALL LETTER S
        0          0  00005F   5F             _      LOW LINE
        0          0  000038   38             8      DIGIT EIGHT
        0          0  000072   72             r      LATIN SMALL LETTER R
        0          0  0015F1   E1 97 B1       ᗱ      CANADIAN SYLLABICS CARRIER GE
```

Pulling out that middle column manually (sorry) we get the flag:


