---
layout: writeup
title: 'Egg 19: Cut n Place '
level:
difficulty:
points:
categories: []
tags: []
flag: zDU6QXeWUfvjtZJxbEnC

---
## Challenge:

*Time for paper and scissors! The following PDF file contains some paper
strips. Your task is to combine them in such a way that a passphrase
appears. Once found, enter the passphrase in the Egg-O-Matic below.*

*Hint: The passphrase does not use all characters available, and it has
no spaces.*

![](images/egg_19_paperstrips_small.png)

## Solution:

We have 5 dark strips with letters printed vertically, and 5 white
strips with letters printed horizontally. There are some symbols as
well. We print out the strips and play around with them a bit,
and get the idea of interlacing the strips to form a 5x5 square of
letters, hopefully containing the password. This way we can bury the
symbols under letters on the other strip.
With this constraint and noticing we can start the square with the word
`paper` with the letters of of one of the horizontal strips and the top
letters of two of the vertical strips,
we get a good start. After some playing around we get the following
solution:

![](images/egg_19_interlaced_small.png)

We read the password from this

    paperstripsmadebyshredder

entering this into the egg-o-matic give us our easter egg:

![](images/egg_19_qrcode_small.png)

