---
layout: writeup

title: This One Goes To 11
level: 8         # optional, for events that use levels (like HackyEaster)
difficulty: hard     # easy/medium/hard etc, if applicable
points: 300        # if used
categories: [reversing]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

So tell me, how do I escape hell, wise man?

Connect to the server.

`nc ch.hackyeaster.com 2309`

[hell](writeupfiles/hell)


***Hint:** Non est facilis labor fugere infernum, quia est fundamentum omnis mali ac nequitiarum. Solum ultima tua clamor te liberabit ex hoc loco. Sed ante te volvendus campis ad sinistram et ad dexteram et evadendus insidias mali Xorxis. Sed ne putes id facile fore, qui victoriam quaerit, oportet ei mentem habere quid in hoc labore vero est momenti.*

## Solution

this is found in the strings, and, unsurprisingly, is not it.

```
HE23{H3ll_a1nt_a_b@d_pl@c3!h377_i$_fr0m_h343_t0_3t3erni7y}
```

But maybe the online version of the program has the real flag there, we just need to figure out how to make the program give it to us?
