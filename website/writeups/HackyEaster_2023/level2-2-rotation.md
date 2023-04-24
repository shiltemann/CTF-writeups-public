---
layout: writeup

title: Rotation
level: 2
difficulty: noob
points: 50
categories: [misc]
tags: [rotation cipher]

flag: he2023{0n3_c4n_r34d_r0t0r_b4ckw4rds}

---

## Challenge

My new rotor messed up the flag!

```
96a_abL_?b04c?0Cbc50C_E_C03c4<HcC5DN
```

I tried to decode it, but it didn't work. The rotor must have been too fast!

## Solution

We suspect a rotation cipher because of the cipher, and assuming the given string starts with `he2023`, this indeed checks out (`h` and `e` are 3 apart in the ASCII table, so are `9` and `6`). It would appear to be a rotation of 47, though sometimes it is +47, sometimes -47, so we write a short script to find the direction of rotation

```python
import string

ct="96a_abL_?b04c?0Cbc50C_E_C03c4<HcC5DN"
flag = ""

for i in range(0,len(ct)):
  pt = chr ( ord(ct[i])+47 )
  if pt not in string.printable:
     pt = chr ( ord(ct[i])-47 )

  flag += pt

print(flag)
```

which gives us the flag!


