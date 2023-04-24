---
layout: writeup

title: City Trip
level: 3         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [osint]  # e.g. crypto, pwn, reversing
tags: [maps]        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{bayardst}

---

## Challenge

I made a nice city trip. Find out where I was!

🚩 Flag

- street's name in lowercase and without spaces
- district or city name is not enough, we need the street
- example: Main Rd -> he2022{mainrd}

![](writeupfiles/citytrip.jpg)


## Solution

Seems like we have to find out the location of this image, but it does not appear to be geotagged, hmm..

Helena's first thought was manhattan, and looking at the shop front in bottom right corner it looks like "Manhattan Flo" Florist?

Googling Manhattan florist chinatown gets us to manhattanflorist.com, on Bayard street

