---
layout: writeup
title: 'Cryptography 75: Crypto Warmup 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{this_is_crypto!}
---
## Challenge

Cryptography doesn't have to be complicated, have you ever heard of
something called rot13? `cvpbPGS{guvf_vf_pelcgb!}`

## Solution

    >>> 'cvpbPGS{guvf_vf_pelcgb!}'.decode('rot13')
    u'picoCTF{this_is_crypto!}'
{: .language-python}

