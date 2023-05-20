---
layout: writeup
title: 'General Skills 100: strings'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{sTrIngS_sAVeS_Time_d3ffa29c}
---
## Challenge

Can you find the flag in [this file](writeupfiles/strings) without
actually running it? You can also find the file  
in `/problems/strings_4_40d221755b4a0b134c2a7a2e825ef95f` on the shell
server.

## Solution

    $ strings strings | grep picoCTF
    picoCTF{sTrIngS_sAVeS_Time_d3ffa29c}
{: .language-bash}

