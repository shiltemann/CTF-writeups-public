---
layout: writeup
title: 'Friðfinnur'
level:
difficulty:
points: 200
categories: [web]
tags: []
flag: IceCTF{you_found_debug}
---
## Challenge

Eve wants to make the hottest new website for job searching on the
market! An avid PHP developer she decided to use the hottest new
framework, Laravel! I don't think she knew how to deploy websites at
this scale however....

https://gg4ugw5xbsr2myw-fridfinnur.labs.icec.tf/

## Solution

Not sure if this was the intended solution, but requesting an url for a
nonexistant job listing lead to an error message containing the flag:

https://29nd70ux6kr7ala-fridfinnur.labs.icec.tf/jobs/galaxian

![](writeupfiles/errorpagewithflag.png)

## Flag

    IceCTF{you_found_debug}

