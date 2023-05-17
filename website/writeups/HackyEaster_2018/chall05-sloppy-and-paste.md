---
layout: writeup

title: Slopppy and Paste
level:          # optional, for events that use levels (like HackyEaster)
difficulty: easy    # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-2yTc-bJ1f-raIQ-gKc6

---

## Challenge

This was a mobille challenge.

![](writeupfiles/chall05/screenshot.jpg)


## Solution

When we try to copy the text shown, it copies a different text


so we get the apk of the mobile app and decode it

```
apktool decode HackyEaster_9_5.0.1.apk
```

and find the string we are looking for in `assets/www/challenge05.html`


![](writeupfiles/chall05/egg.png)



