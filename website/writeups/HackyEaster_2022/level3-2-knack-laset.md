---
layout: writeup

title: Knäck låset
level: 3         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [mics]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{807}

---

## Challenge

Knäck the cØde!

```
koda   ✅ 🔀 ❌
2-9-7  1  0  2
2-3-0  0  1  2
7-8-2  0  2  1
5-1-9  0  0  3
5-9-8  0  1  2
```

## Solution

ok, so its mastermind


```
koda   ✅ 🔀 ❌
2-9-7  1  0  2   # 1: one correct, the 7 (not the 2 because of line 2, not the 9, because of line 4)
2-3-0  0  1  2   # 2: either a 3 or 0 in the code
7-8-2  0  2  1   # 3: 8 in the code (in addition to the 7), must be in first spot
5-1-9  0  0  3   # 4: 5, 1 and 9 not in code
5-9-8  0  1  2   # 5: 8 is in wrong spot

---------------------
8-0-7
```

