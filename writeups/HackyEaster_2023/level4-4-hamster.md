---
layout: writeup

title: Hamster
level: 4
difficulty: easy
categories: [web]
tags: []

flag: he2023{s1mpl3_h34d3r_t4mp3r1ng}

---

## Challenge

The Hamster has a flag for you.

http://ch.hackyeaster.com:2301

Note: The service is restarted every hour at x:00.

## Solution

We visit the url and get various responsed of how to alter our requests, so we use curl and follow instructions:


```bash
$ curl http://ch.hackyeaster.com:2301
Howdy, I am the hamster.Please go to /feed

# ok, let's go to /feed
$ curl http://ch.hackyeaster.com:2301/feed
only hamster-agent is allowed

# so let's set a user-agent
$ curl -A "hamster-agent"  http://ch.hackyeaster.com:2301/feed
⛳ GET invalid

# maybe POST? PUT? Yes, you want put
$ curl -A "hamster-agent" -X PUT http://ch.hackyeaster.com:2301/feed
🛑 request must come from hackyhamster.org

# ok, let's set a referrer
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" http://ch.hackyeaster.com:2301/feed
🍪 brownie not found

# want a cookie? here you go.
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" --cookie "brownie=brownie" http://ch.hackyeaster.com:2301/feed
🍪 brownie must be baked

# ok, set the value to baked
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" --cookie "brownie=baked" http://ch.hackyeaster.com:2301/feed
🚩 he2023{s1mpl3_h34d3r_t4mp3r1ng}

#whoo, we got it!
```

