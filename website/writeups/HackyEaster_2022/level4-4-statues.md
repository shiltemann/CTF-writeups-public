---
layout: writeup

title: Statues
level: 4         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [osint]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{achilles}

---

## Challenge

Hope you like statues as much as I do!

I created a little tour in my favourite city for you:

    Richard I
    Peter Pan
    Albert
    James Cook

I will not reveal my favourite statue, though. Find it yourself!

🚩 Flag

    it's not the one on the challenge image (Joan of Arc)!
    name of the person represented by the statue
    all lowercase, no spaces, no special chars
    e.g. he2022{johnny}



## Solution

These all appear to be statues in London, let's pull op a map!


![](writeupfiles/statues_map.png)

Where the lines between the statues intersect is exactly the location of the Achilles statue!


