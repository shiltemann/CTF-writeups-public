---
layout: writeup
title: Obfuscated
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

How good are you at reading Python? Put your Python skills to the test
by finding the password that passes this check: obfuscated.py

Note: easyctf\{} formatting is not required for this problem.

    from string import ascii_uppercase as v, ascii_lowercase as k
    def check_flag(s):
    	if len(s) != 0x19:
    		return False
    	s = list(s)
    	if int(`[s.pop(r) for r in ([2] + [i for i in range(11, 18, 2)] + [20])[::-1]][::-1]`[2::5]) != 0x61a83:
    		return False
    	if len(list(set([s.pop(r) for r in map(lambda p: int(p, 2), [("1"*5)[:2], ("1"*5)[2:]])[::-1]]))) != s.index("h"):
    		return False
    	y, z = [], []
    	u = len(list(set([repr(y.append(s.pop(-1))) + repr(z.append(s.pop(-1))) for w in range(2)]))) - 1
    	if u != len(list(set(y))) ^ len(list(set(z))):
    		return False
    	if (ord(y[u]) ^ ord(z[u])) ^ 0x1e != 0:
    		return False
    	if v.index(s.pop()) ^ len(s) ^ 0x1e != 0:
    		return False
    	a, i = filter(lambda c: c in v, s), filter(lambda c: c in k, s)
    	if map(lambda a: a + 0x50, [7, 2, 4, -8]) + [0x4f] * 4 != map(ord, a):
    		return False
    	i[1:3] = i[2:0:-1]
    	if i != list("hate"):
    		return False
    	return True
{: .language-python}

**Solution**  
## Flag

