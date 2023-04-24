---
layout: writeup

title: Going Round
level: 5 # optional, for events that use levels
difficulty: medium
categories: [crypto]
tags: []

flag: he2023{fl1p_n_r0t4t3_in_p4irs}

---

## Challenge

I got a flag, but it's encrypted somehow:
`ip0232j{1t_x_v0z4b3bm__v4xvq}a`

It was created using the following service:

http://ch.hackyeaster.com:2305

Note: The service is restarted every hour at x:00.


## Solution

We get a service that takes our input and shows the encrypted flag.

It's clear its doing a rotation cipher (alternating between a rotation of 4 and 8 character), and also swapping the positions of pairs of letters.

It was easy enough to fiddle with our string in the service until we get the encrypted flag we were given, and thus know the real flag.


