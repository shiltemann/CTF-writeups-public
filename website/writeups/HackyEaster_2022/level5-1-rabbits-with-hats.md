---
layout: writeup

title: Rabbits with Hats
level: 5         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [misc]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{jackrabbitflatwildlifesanctuary}

---

## Challenge

I'm looking for a friend of mine who had to flee from his evil owner.

He must have found a shelter for wildlife, but didn't tell me where it is. He just said he would go join rabbits with hats. What the heck do these three words mean??

🚩 Flag

- he2022{nameoftheplace}
- all lowercase, no spaces
- first letter is j, last one is y
- e.g. he2022{junglezooaviary}

## Solution

once you read "*What* the heck do these *three words* mean?" and remember that `what3words` exists as a geocoding service, simply go to

[https://what3words.com/rabbits.with.hats](https://what3words.com/rabbits.with.hats) and see that "jackrabbit flat wildlife sanctuary" is nearby

