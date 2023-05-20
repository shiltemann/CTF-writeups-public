---
layout: writeup
title: 'special-pw'
level:
difficulty:
points: 600
categories: [reversing]
tags: []
flag:
---
**Challenge**
Can you figure out the right argument to this program to login? We
couldn't manage to get a copy of the binary but we did manage to
[dump](writeupfiles/special_pw.S) some machine code and memory from the
running process.

**Hints**
Hmmm maybe if we do the reverse of each operation we can get the
password?

## Solution

