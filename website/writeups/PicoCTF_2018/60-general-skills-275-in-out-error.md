---
layout: writeup
title: 'General Skills 275: in out error'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{p1p1ng_1S_4_7h1ng_b6f5a788}
---
## Challenge

Can you utlize stdin, stdout, and stderr to get the flag from this
program?  
You can also find it in
`/problems/in-out-error_2_c33e2a987fbd0f75e78481b14bfd15f4` on the shell
server

## Solution

log into shell and:

    $ echo "Please may I have the flag?" | ./in-out-error | grep picoCTF

