---
layout: writeup
title: Liar
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{troll3d}
---
## Challenge

I may or may not have illegally bought this file from someone who claims
that it contains secret pictures of my friend and her fiancé.
Unfortunately, I can't open it, and I already paid $4096 for it. Can you
help me find out if the seller was lying?

[file](writeupfiles/secret)

## Solution

The archive contains a file named secret.png, but the file is corrupted.
Opening it in a hexeditor we see the first four bytes of the PNG header
are missing

We fix the header to the correct values

    89  50  4e  47  0d  0a  1a  0a

Now we can view the file and see the key

![](writeupfiles/secret-fixed.png)

