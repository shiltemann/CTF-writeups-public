---
layout: writeup
title: 'General Skills 150: environ'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{eNv1r0nM3nT_v4r14Bl3_fL4g_3758492}
---
## Challenge

Sometimes you have to configure environment variables before executing a
program. Can you find the flag we've hidden in an environment variable
on the shell server?

## Solution

logging into the shell:

    hxr@pico-2018-shell-1:~$ env | grep pico
    SECRET_FLAG=picoCTF{eNv1r0nM3nT_v4r14Bl3_fL4g_3758492}

