---
layout: writeup

title: Coney Island Hackers
level: 5         # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [web]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{el_dorado_arkade}

---

## Challenge

Coney Island Hackers have a secret web portal.

Using advanced social engineering techniques, you found out their secret passphrase: eat,sleep,hack,repeat. However, it seems to take more than just entering the passphrase as-is. Can you find out what?

http://46.101.107.117:2202


## Solution

```
if (req.query.passphrase == 'eat,sleep,hack,repeat')
```

This hint was super unhelpful at first, eventually I fetched the HEAD of the website

```
$ curl -I http://46.101.107.117:2202/?passphrase=eat,sleep,hack,repeat
HTTP/1.1 200 OK
X-Powered-By: Express
Content-Type: text/html; charset=utf-8
Content-Length: 609
ETag: W/"261-eAa/QxeLx6CjmYOc9KnGojwfNKY"
Date: Thu, 05 May 2022 16:20:04 GMT
Connection: keep-alive
Keep-Alive: timeout=5
```

Ahh express JS. After some super sleuthing (i.e. googling `express nodejs form password check ctf hack`), I [found this article which had a URL with split up password fields](https://www.doyler.net/security-not-included/nodejs-code-injection). This answered it for me, we need to pass in the passphrase as multiple elements like a list. In the old php days I remember seeing that quite often with `?param[]=value&param[]=value2`

Which, given the javascript behaviour of stringifying lists for comparison, by helpfully adding `,`s:

```
[1,2,3,4].toString()
"1,2,3,4"
```

means that's how we solve it without commas:

```
http://46.101.107.117:2202/?passphrase[]=eat&passphrase[]=sleep&passphrase[]=hack&passphrase[]=repeat
```


