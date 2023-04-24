---
layout: writeup

title: Jupiter One
level: 5       # optional, for events that use levels (like HackyEaster)
difficulty: easy    # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [forensics]  # e.g. crypto, pwn, reversing
tags: [LSB]        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{jim_jupiter_the_healthiest_man_in_chicago!!}

---

## Challenge

Jupiter is hiding something.

Can you find it?


![](writeupfiles/jupiter-one.png)

## Solution

Extracting the LSB plane (e.g. with [stegonline](https://stegonline.georgeom.net)) gives us the flag

![](writeupfiles/jupiter-lsb.png)
