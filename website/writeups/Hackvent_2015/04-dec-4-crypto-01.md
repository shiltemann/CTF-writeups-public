---
layout: writeup
title: 'Dec 4: Crypto 01'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-OWoV-lO6j-Aqq8-fV7M-Oduv

---

## Challenge

*a classic / simple one*

Unfortunately, no one can be told what this is. You have to see it for
yourself.

    HOlAfOVWOqVd1o6q7u5Vj8Mv-----

## Solution

The string is exactly the length of a nugget, all the characters for
HV15 are there as well as the 5 dashes. Maybe we just need to rearrange
the characters somehow?
We notice that the characters to form "HV15" are always 6 apart, so we
create a grid:

    HOlAfO
    VWOqVd
    1o6q7u
    5Vj8Mv
    -----

reading this top-to-bottom and left-to-right gives the nugget



