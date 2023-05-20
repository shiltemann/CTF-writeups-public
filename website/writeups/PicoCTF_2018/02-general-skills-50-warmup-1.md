---
layout: writeup
title: 'General Skills 50: Warmup 1'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{A}
---
## Challenge

If I told you your grade was `0x41` in hexadecimal, what would it be in
ASCII?

## Solution

    >>> chr(int('41',16))
    'A'
{: .language-python}

