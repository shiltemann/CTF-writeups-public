---
layout: writeup

title: Coney Island Hackers 2
level: 7    # optional, for events that use levels (like HackyEaster)
difficulty: medium     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [web]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

Coney Island Hackers are back!

They changed the passphrase of their secret web portal to: coneʸisland.

However, they implemented some protection:

    letters and some special characters are not allowed
    maximum length of the string entered is 75

http://ch.hackyeaster.com:2302

Note: The service is restarted every hour at x:00.

**Hint:** `eval`

## Solution


