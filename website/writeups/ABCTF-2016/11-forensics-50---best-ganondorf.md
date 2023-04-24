---
layout: writeup
title:  Best Ganondorf
level:
difficulty:
points: 50
categories: [forensics]
tags: []
flag: abctf{tfw_kage_r3kt_nyway}
---
**Challenge**   
You know the deal. Find a flag in [this](writeupfiles/ezmonay.jpg) file?

## Solution

File has `.jpg` extension but is not a valid jpeg. We open in a hex
editor and it looks like it could be a jpeg file, but the header is
invalid

we change the first two bytes of the header to `FFD8` and now we can
view the image (which contains the flag)

![](writeupfiles/ezmonay_fixed.jpg)
