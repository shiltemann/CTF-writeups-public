---
layout: writeup
title: 'Day 12: Wiener Waltz'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{}
---
## Description

During their yearly season opening party our super-smart elves developed
an improved usage of the well known RSA crypto algorithm. Under the
"Green IT" initiative they decided to save computing horsepower (or
rather reindeer power?) on their side. To achieve this they chose a
pretty large private exponent, around 1/4 of the length of the modulus -
impossible to guess. The reduction of 75% should save a lot of computing
effort while still being safe. Shouldn't it?  
Mission

Your SIGINT team captured some communication containing key exchange and
encrypted data. Can you recover the original message?

[dec12.pcap](writeupfiles/dec12.pcap)

*Hints*

* Don't waste time with the attempt to brute-force the private key

## Solution

