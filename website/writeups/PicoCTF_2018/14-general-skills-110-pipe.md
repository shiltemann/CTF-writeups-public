---
layout: writeup
title: 'GEneral Skills 110: pipe'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{almost_like_mario_b797f2b3}
---
## Challenge

During your dventure, you will likely encounter a situation where you
need to process  
data that you receive over the network rather than through a file. Can
you find a way  
to save the output from this program and search for the flag?  
Connect with `2018shell1.picoctf.com 34532`.

## Solution

We connect via netcat and are flooded with messages

    $ nc 2018shell1.picoctf.com 34532
    Unfortunately this is also not a flag
    This is not a flag
    This is not a flag
    I'm sorry you're going to have to look at another line
    I'm sorry you're going to have to look at another line
    I'm sorry you're going to have to look at another line
    Unfortunately this is also not a flag
    I'm sorry you're going to have to look at another line
    I'm sorry you're going to have to look at another line
    I'm sorry you're going to have to look at another line
    Unfortunately this is also not a flag
    I'm sorry you're going to have to look at another line
    Unfortunately this is also not a flag
    This is not a flag
    [..]
{: .language-bash}

So we do a grep:

    $ nc 2018shell1.picoctf.com 34532 | grep picoCTF
    picoCTF{almost_like_mario_b797f2b3}
{: .language-bash}

