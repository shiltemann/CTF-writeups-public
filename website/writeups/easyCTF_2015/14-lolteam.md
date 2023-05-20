---
layout: writeup
title: lolteam
level: 
difficulty: 
points: 
categories: []
tags: []
flag: flag{no,_lolteam_is_not_an_admin_account}
---
**Challenge**  
There's a suspicious team out there called lolteam, I got my eyes on
them for a while and I managed to wiretap their browser as they were
changing their password. What did they change their password to?

[lolteam.pcapng](writeupfiles/lolteam.pcapng)

## Solution

Open in Wireshark, export HTTP objects POST request

    teamname=lolteam&school=lolschool&password=flag%7Bno%2C_lolteam_is_not_an_admin_account%7D&confirm=lolpassword

