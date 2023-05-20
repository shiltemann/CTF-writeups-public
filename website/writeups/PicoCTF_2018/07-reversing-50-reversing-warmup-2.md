---
layout: writeup
title: 'Reversing 50: Reversing Warmup 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{th4t_w4s_s1mpL3}
---
## Challenge

Can you decode the following string `dGg0dF93NHNfczFtcEwz` from base64
format to ASCII?

## Solution

    >>> import base64
    >>> base64.b64decode('dGg0dF93NHNfczFtcEwz')
    'th4t_w4s_s1mpL3'
{: .language-python}

