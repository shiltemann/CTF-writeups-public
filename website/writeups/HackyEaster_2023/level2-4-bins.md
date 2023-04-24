---
layout: writeup

title: Bins
level: 2
difficulty: noob
categories: [misc]
tags: [cipher]

flag: he2023{s0rting_th3_w4ste}

---

## Challenge

The rabbits left a mess in their cage.

```
  //    //                    //
 ('>   ('>    LX2gkn81        ('>
 /rr   /rr       carrots      /rr
*\))_ *\))_                  *\))_
```

If only I knew which bin to put the rubbish in.


## Solution

This one took way too long, we first thought of anything in `/bin` folder we might use on this, then finally realized we did not get a file to download, are there any "bins" on the website or online? OMG *paste*bin!

[pastebin.com/LX2gkn81](https://pastebin.com/LX2gkn81)

It exists, made just before the event, this is promising ..but it asks us for password, we try "carrots", and boom, there is our flag!


