---
layout: writeup

title: Sagittarius
level:          # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

13 - Sagittarius...

... is playing with his pila again.

Can you find the Easter egg QR code he has hidden from you?


## Solution

I loaded pila.kmz into a KMZ viewer and saw this:

![](writeupfiles/chal13/Auswahl_398.png)

So obviously the wrong projection.
