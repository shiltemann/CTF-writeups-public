---
layout: writeup

title: Jigsaw
level:          # optional, for events that use levels (like HackyEaster)
difficulty: easy    # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-jFsP-AXNB-GUXu-dkms

---

## Challenge

Thumper was probably under time pressure and jumped around a bit too wild. As a result, his picture has broken.

Can you write a program to put it back together

![](writeupfiles/chall07/jigsaw.png)

## Solution

This tool is pure magic and solved this challenge for us using a genetic algorithm: https://github.com/nemanja-m/gaps

```bash
$ gaps --image=../jigsaw.png --generations=30 --population=600 --save
```

![](writeupfiles/chall07/solved.jpg)

```
goodsheepdontalwayswearwhite
```

We put this phrase into the egg-o-matic to get our egg


![](writeupfiles/chall07/egg.png)


