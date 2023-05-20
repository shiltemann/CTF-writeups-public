---
layout: writeup
title: 'Hidden ball 1:'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-4llw-aysL-00ki-nTh3-H34d

---
## Solution

Challenges are accessed by url like
`https://hackvent.hacking-lab.com/challenge.php?day=2`

Let's see what happens when we try to skip ahead to Christmas `?day=25`

We get:

    The resource (#1959) you are trying to access, is not (yet) for your eyes.

ok, weird, what about `?day=26`

    The resource (#1958) you are trying to access, is not (yet) for your eyes.

day and resource number seem to add up to 1984 every time, so let's see
what happens when we fill in `?day=1984`

    The resource you are trying to access, is hidden in the header.

whoo! let's check the headers:

    HTTP/1.1 200 OK
    Date: Sat, 02 Dec 2017 21:14:21 GMT
    Server: Merry Christmas & Hacky New Year
    Strict-Transport-Security: max-age=15768000
    Flag: HV17-4llw-aysL-00ki-nTh3-H34d
    Keep-Alive: timeout=5, max=99
    Connection: Keep-Alive
    Transfer-Encoding: chunked
    Content-Type: text/html; charset=UTF-8

There is our flag!

