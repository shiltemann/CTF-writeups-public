---
layout: writeup
title: 'Lots of Dots'
level:
difficulty: easy
points:
categories: []
tags: []
flag: pJ94m6jt3AYbogL2gv9i

---

## Challenge

The dots in the following image contain a secret message. Can you find
it?

![](./writeupfiles/dots.png)

## Solution

Had a non-colourblind person look at it, he said nothing was there.
Split out LSB and bingo:

![](./writeupfiles/dots_out.png)

Pop into the egg-o-matic:

![](./writeupfiles/dots_egg.png)

