---
layout: writeup

title: City Trip 2
level: 7         # optional, for events that use levels (like HackyEaster)
difficulty: hard    # easy/medium/hard etc, if applicable
points: 300        # if used
categories: [osint]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{53.482,-2.216}

---

## Challenge

Later that year, I was travelling again. Find out where I shot this picture! This time, I want GPS coordinates.

🚩 Flag

    GPS coordinates, rounded to three decimals
    , as separator
    . as decimal point
    example:
        40°46'30.3"N 73°57'59.8"W
        40.775082, -73.966599
        he2022{40.775,-73.967}


![](writeupfiles/citytrip2.jpg)

## Solution

We know it's in manchester based off of the phone number.

After fruitless attemts to automatically discover an intersection of tram + street + bike lane, combined with people commenting endlessly about how fast they did it, we saw the sign and tried reverse image searching it. It return a lot of crufixes in the current format. So we stretched it, and...still crucifixes and churches for some reason.

So we googled "manchester pub signs" (guessing it was a pub) and scrolled until we found [this page linked from the search results](https://www.geograph.org.uk/photo/1843484) which looked spot on, plus some ageing. That turned out to be annoying to google but between knowing it was in Ancoats and that it was on pollard street, eventually we found the right location and angle.

![link to google maps](https://www.google.com/maps/@53.4822203,-2.2165773,3a,75y,309.14h,99.02t/data=!3m6!1e1!3m4!1s3VmOIwuJAlRwqdhWtDRhRg!2e0!7i16384!8i8192)

