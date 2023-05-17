---
layout: writeup

title: C0ns0n4nt Pl4n3t
level: 7         # optional, for events that use levels (like HackyEaster)
difficulty: medium     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [web]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{v0w3ls_4r3_f0r_n3rd5!}

---

## Challenge


Apollo wants his name printed on that fancy new site. He's constantly failing as vowels and some special characters are blocked when entered.

Can you help him?

http://46.101.107.117:2205

Note: The service is restarted every hour at x:00.

## Solution

If we enter a vowel, the site will not print our entry.

For example:

```
http://46.101.107.117:2205/?name=Apollo
```

Response: `f0rb1dd3n`

If we enter just a double-quote, we trigger an error:

```
Parse error: syntax error, unexpected '"' in /var/www/html/index.php(17) : eval()'d code on line 1
```

So we know this is PHP in the backend. Let's try escaping our vowels:


```
http://46.101.107.117:2205/?name=\x41p\x6fll\x6f
```

Bingo! We get the flag in the response:

```
Cngrts, hr's yr flg:
he2022{v0w3ls_4r3_f0r_n3rd5!}
```

