---
layout: writeup
title: 'General Skills 75: grep 1'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{grep_and_you_will_find_c709fa94}
---
## Challenge

Can you find the flag in [file](writeupfiles/file)? This would be really
obnoxious to look through by hand, see if you can find a faster way.

## Solution

    $ grep "picoCTF" file
    picoCTF{grep_and_you_will_find_c709fa94}
{: .language-bash}

