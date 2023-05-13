---
layout: writeup

title: Prison Break
level:          # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-gx8L-AJUw-DSMH-6aUI

---

## Challenge

Your fellow inmate secretly passed you an old cell phone and a weird origami. The only thing on the phone are two stored numbers.

```
555-7747663 Link
555-7475464 Sara
```

Find the password and enter it in the Egg-o-Matic below. lowercase only, no spaces!

![](writeupfiles/chall01/origami.png)


## Solution

Combine the telephone numbers with the dots on the origami and a T9 pad:

![](writeupfiles/chall01/t9.png)

```
7747663 (Link)
1334322 (number of dots)
prisone (1 times '7' key = p, 3 times '7' key = r,  3 times '4' key = i,  etc)

7475464 (Sara)
3342321 (number of dots)
risking

```

this reads `prisonerisking`, enter this into egg-o-matic to get our egg

![](writeupfiles/chall01/egg.png)

