---
layout: writeup
title: 'Day 05: OSINT 1'
level:
difficulty: easy
points:
categories: []
tags: []
flag: HV18-0Sin-tI5S-R34l-lyC0-oo0L

---
*It's all about transparency*

## Challenge

Santa has hidden your daily present on his server, somewhere on port
443.

Start on https://www.hackvent.org and follow the OSINT traces.

## Solution

We go to the site and see this:

![](writeupfiles/day05-site.png)

Googling leads us to [certificate transparancy]()

And this site where we can find the other vhost referred to in the
challenge:

https://transparencyreport.google.com/https/certificates

![](writeupfiles/day05-screenshot.png)

when we visit `osintiscoolisntit.hackvent.org` we are greeted with the
flag:

![](writeupfiles/day05-flag.jpg)

