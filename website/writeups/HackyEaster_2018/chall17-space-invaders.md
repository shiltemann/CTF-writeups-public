---
layout: writeup

title: Space Invaders
level:          # optional, for events that use levels (like HackyEaster)
difficulty: medium     # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-D76U-PvxZ-7Icy-mkF

---

## Challenge

Alien space invaders left a secret message. Luckily, you know that they used codemoji.org for the encryption.

Decrypt the message, and save the planet!!

> ⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢


## Solution

- Go to https://codemoji.org/#/encrypt
- create a random message
- "share", they give you a shortlink that resolves into "https://codemoji.org/share.html?data=...." where data is an html encoded, base64 encoded blob of json like:

   ```json
   {
     "message": "⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢",
     "key": "👾"
   }
    ```

- replace the message with the given input: "⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢"
- and get [this url](https://codemoji.org/share.html?data=eyJtZXNzYWdlIjoi4pq%2B4q2Q8J%2BTr/CfkrXwn46o8J%2BTovCfk5jwn5Kq4piA8J%2BMhvCfkqrwn5C48J%2BOqPCfkKbwn5OiXG4iLCJrZXkiOiLwn5G%2BIn0%3D)
- which decodes with the invader emoji to `invad3rsmustd13`


![](writeupfiles/chall17/egg.png)

