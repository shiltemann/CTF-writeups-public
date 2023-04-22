---
layout: writeup

title: Chemical Code
level: 3
difficulty: easy
categories: [misc]
tags: []

flag: he2023{flagenergyatomcosmos}

---

## Challenge

Our crazy chemistry professor wrote a secret code on the blackboard:

```
9 57 32 10 111 39 85 8 115 8 16 42 16
```

He also mumbled something like "essential and elementary knowledge".


## Solution

This sounds like we have to convert atomic numbers to their corresponding sybols to get the flag

We find a python package to help us, [PyAstronomy](https://pyastronomy.readthedocs.io/en/latest/pyaslDoc/aslDoc/atomicNo.html, )and use it to decode the flag

```python3
from PyAstronomy import pyasl

an = pyasl.AtomicNo()
ct =[9,57,32,10,111,39,85,8,115,8,16,42,16]

flag = "".join(an.getElSymbol(ct[i]) for i in range(0,len(ct)))

print(flag)  # outputs FLaGeNeRgYAtOMcOSMoS

```


