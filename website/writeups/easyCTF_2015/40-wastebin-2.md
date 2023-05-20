---
layout: writeup
title: Wastebin 2
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{looks_like_my_robot_proof_protection_isn't_very_human_proof}
---
## Challenge

So after the previous fiasco, I decided to generate a random admin
password, and hide it in a file that no one will ever find. And don't
try Googling it either, cuz Google can't find it either! Hahaha >:) Link

## Solution

check robots.txt

    User-agent: *
    Disallow: /2/password_Tj9WBkFpORmHYaYBG5GR7VZzgDaEM2e2aWeeCRtJ.txt

content of the disallowed file:

    11FutLBObDdAnSIyEo9LF6TLiWuG8GpHSLnRBAYD4jUGM0O4Jbt8KPasU5CpAGmZW2dX97HX4xHau8asmrN5CzIiM6Xb51plWa3q

log in as admin with this password gives us the key

    Nice. The flag is easyctf{looks_like_my_robot_proof_protection_isn't_very_human_proof}

