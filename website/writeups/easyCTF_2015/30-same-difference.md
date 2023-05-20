---
layout: writeup
title: Same Difference
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{60a57b3974029aa012e66b05f122748b}
---
## Challenge

Solve this problem by connecting to the EasyCTF shell, either in your
browser or through some other TTY.

We've noticed that a list of passwords has been modified. Compare the
original master\_copy.txt to the suspicious.txt and tell us what the
password was changed to! The files are on the shell server at
/problems/same\_difference.

This can be solved only with the tools available in the shell. No
scripting languages are required.

## Solution

    $ ll /problems/same_difference
    total 832
    drwxr-xr-x  2 root root   4096 Nov  4 23:50 ./
    drwxr-x--x 10 root root   4096 Nov  4 23:56 ../
    -rw-r--r--  1 root root 420000 Nov  4 23:50 master_copy.txt
    -rw-r--r--  1 root root 420000 Nov  4 23:50 suspicious.txt
    
    $ wc -l master_copy.txt
    10000 master_copy.txt
    
    $ diff master_copy.txt suspicious.txt
    8834c8834
    < easyctf{17c85a939e5ee1b0b0e00ed7187d11f7}
    ---
    > easyctf{60a57b3974029aa012e66b05f122748b}

