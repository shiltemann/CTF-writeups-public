---
layout: writeup

title: Copy Potection Pioneers
level: 4         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100         # if used
categories: [misc]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{J3t-53t-W1llY-f0r3v3R}

---

## Challenge

The copy protection pioneers were really creative and lived the jet set life.

http://46.101.107.117:2209

Note: The service is restarted every hour at x:00.

## Solution

Jet Set Willy was an archaic game with a copy protection card. Googling that finds a [really nice article on its history](https://intarch.ac.uk/journal/issue45/2/1.html)

![image of a colour code chart, a-z on left, 0-9 on top, and series of 4 red/green/blue/pink squares for each cell](https://intarch.ac.uk/journal/issue45/2/images/figure9th.jpg)


Helpfully this [available as a python script](https://github.com/aycock/jsw)

```python
$ python2 jswdecode.py | grep -e 'A 4' -e 'C 2' -e 'D 4' -e 'F 9' -e 'Q 2'
A 4 = 2 1 4 2
C 2 = 4 2 3 2
D 4 = 1 3 3 1
F 9 = 4 2 1 3
Q 2 = 1 2 4 3
```

**Egg**

```
he2022{J3t-53t-W1llY-f0r3v3R}
```

### Statues

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



**Solution**

These all appear to be statues in London, let's pull op a map!


![](writeupfiles/statues_map.png)

Where the lines between the statues intersect is exactly the location of the Achilles statue!


