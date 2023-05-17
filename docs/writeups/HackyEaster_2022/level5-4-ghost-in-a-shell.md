---
layout: writeup

title: Ghost in a Shell 3
level: 5         # optional, for events that use levels (like HackyEaster)
difficulty: easy    # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [forensics]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{0p3n-35-35-37-f0r-pr0fit}

---

## Challenge

```
  _, _,_  _,  _, ___   _ _, _    _,    _, _,_ __, _,  _,    ,  ,  ,
 / _ |_| / \ (_   |    | |\ |   /_\   (_  |_| |_  |   |     |  |  |
 \ / | | \ / , )  |    | | \|   | |   , ) | | |   | , | ,   |  |  |
  ~  ~ ~  ~   ~   ~    ~ ~  ~   ~ ~    ~  ~ ~ ~~~ ~~~ ~~~   ~  ~  ~
______________________________________________________________________
 ,--.     ,--.     ,--.
| oo |   | oo |   | oo |
| ~~ |   | ~~ |   | ~~ |   o  o  o  o  o  o  o  o  o  o  o  o  o  o  o
|/\/\|   |/\/\|   |/\/\|
______________________________________________________________________
```

Connect to the server, snoop around, and find the flag!

- ssh __DOCKER_HOST__ -p 2203 -l pinky
- password is: !speedy!


## Solution

```
276c642cc2e4:/opt/bannerkoder$ cat /var/spool/cron/crontabs/root
* * * * * /opt/bannerkoder/cipher.sh > /dev/null 2>&1
```

Found this (via the crontab, weirdly)

```
276c642cc2e4:/opt/bannerkoder$ cat cipher.sh
#!/bin/bash
date +%s | md5sum | base64 | head -c 32 > /tmp/7367111C2875730D00686C13B98E7F36
openssl enc -aes-256-cbc -e -in /home/pinky/flag.txt -out /home/pinky/flag.enc -kfile /tmp/7367111C2875730D00686C13B98E7F36276c642cc2e4:/opt/bannerkoder$
```

Ok, so we just need to loop over the +%s for that minute to decrypt

```
while true; do openssl enc -aes-256-cbc -d -in flag.enc -k "$(date +%s | md5sum | base64 | head -c 32)"; sleep 0.5; done;
```

and bingo!

