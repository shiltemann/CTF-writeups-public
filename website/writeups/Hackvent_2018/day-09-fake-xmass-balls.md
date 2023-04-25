---
layout: writeup
title: 'Day 09: fake xmass balls'
level:
difficulty: medium
points:
categories: []
tags: []
flag: HV18-PpTR-Qri5-3nOI-n51a-42gJ
---

## Challenge
A rogue manufacturer is flooding the market with counterfeit yellow xmas
balls.They are popping up like everywhere!

Can you tell them apart from the real ones? Perhaps there is some useful
information hidden in the fakes...

![](writeupfiles/medium-64.png)

## Solution

We're given only that ball and the one that's in the header image:

![](writeupfiles/medium-64.png)![](writeupfiles/medium_64.png)

Running `compare` on the two we see this useful image:

![](writeupfiles/medium-compare-small.png)

Scaling the input images up before re-comparing and inverting the
colours and adding a border to make the code readable:

![](writeupfiles/medium-compare-big.png)
![](writeupfiles/day09-qrcode.png)

