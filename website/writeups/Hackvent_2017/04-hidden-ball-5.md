---
layout: writeup
title: 'Hidden ball 5:'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-UH4X-PPLE-ANND-IH4X-T1ME

---

## Solution

we scan the challenge server for open ports

    $ nmap challenges.hackvent.hacking-lab.com

    Starting Nmap 7.01 ( https://nmap.org ) at 2017-12-06 22:59 CET
    Nmap scan report for challenges.hackvent.hacking-lab.com (80.74.140.188)
    Host is up (0.56s latency).
    rDNS record for 80.74.140.188: urb80-74-140-188.ch-meta.net
    Not shown: 996 filtered ports
    PORT    STATE  SERVICE
    22/tcp  open   ssh
    23/tcp  open   telnet
    80/tcp  closed http
    443/tcp closed https

    Nmap done: 1 IP address (1 host up) scanned in 67.94 seconds
{: .language-bash}

so, there's a telnet service running, we connect, and are greeted by
Santa:

    $ telnet challenges.hackvent.hacking-lab.com

    __.----.
                  _.'        '-.
                 /    _____     '-.
                /_.-""     ""-._   \                 HO, HO, HO...
               ."   _......._   ".  \
               ; .-' _ ))) _ '-. ;   |
               '/  ." _   _ ".  \'.  /
               _|  .-.^ ) ^.-.  |_ \/-.
               \ '"==-.(_).-=="' //    \
                '.____.-^-.____.' \    /
                 |    ( - )   |   '--'
                  \           /
          _________\_________/_______________________________________________
{: .language-bash}

He keeps talking for a minute, and then gives us the flag

