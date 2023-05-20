---
layout: writeup
title: Mrrow?????
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{leonidas:sparta}
---
## Challenge

A lonely little text file wants to play a game: /problems/owner.

## Solution

    $ ls /problems/owner/
    file.txt
    
    $ cat /problems/owner/file.txt
    Help! I was wandering unfamiliar lands when I was suddenly taken hostage!
    
    Please tell me who's my owner, and what his group is!
    easyctf{<owner>:<group>}
    
    $ ls -la /problems/owner/file.txt
    -rwxr-xr-x 1 leonidas sparta 165 Nov  4 01:04 /problems/owner/file.txt

