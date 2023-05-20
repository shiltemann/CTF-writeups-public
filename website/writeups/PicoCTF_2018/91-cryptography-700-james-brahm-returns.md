---
layout: writeup
title: 'Cryptography 700: James Brahm Returns'
level:
difficulty:
points: 700
categories: [crypto]
tags: []
flag:
---

## Challenge
Dr. Xernon has finally approved an update to James Brahm's spy terminal.
(Someone finally told them that ECB isn't secure.) Fortunately, CBC mode
is safe! Right? Connect with nc 2018shell1.picoctf.com 15596.
[Source.](writeupfiles/james-brahm-returns.py)

**Hints**
What killed SSL3?

## Solution

> In 2014, SSL 3.0 was found to be vulnerable to the [POODLE attack][1]
> that affects all block ciphers in SSL; RC4, the only non-block cipher
> supported by SSL 3.0, is also feasibly broken as used in SSL
> 3.0.\[17\]



[1]: https://en.wikipedia.org/wiki/POODLE
