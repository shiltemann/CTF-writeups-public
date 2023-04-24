---
layout: writeup
title: 'Toke Relaunch'
level:
difficulty:
points: 50
categories: [web]
tags: []
flag: IceCTF{what_are_these_robots_doing_here}
---
## Challenge

We've relaunched our famous website, Toke! Hopefully no one will hack it
again and take it down like the last time.

## Solution

The link leads to some marijuna website

![](writeupfiles/toke_screenshot.jpg)

Last edition the toke challenge had the flag hidden in a cookie, but no
cookies are set this time, so we have to look elsewhere

We check the robots.txt file and see:

    User-agent: *
    Disallow: /secret_xhrznylhiubjcdfpzfvejlnth.html

the disallowed file contains our flag.

## Flag

    IceCTF{what_are_these_robots_doing_here}

