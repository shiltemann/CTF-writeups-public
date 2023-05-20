---
layout: writeup
title: Hijacked!
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{c0mp1et3ly_r3kt}
---
## Challenge

Someone planted a file on our computer (the shell server), but we don't
know what it is! The only clue that we have is that it's owned by a user
called l33t\_haxx0r. Can you figure out the flag?

## Solution

    $ find / -user l33t_haxx0r 2>/dev/null
    /var/www/html/index.html

This gives an html file with the flag hidden inside it:

    [..]
    <!--
    t
    h
    e
    
    f
    l
    a
    g
    
    i
    s
    
    e
    a
    s
    y
    c
    t
    f
    {
    c
    0
    m
    p
    1
    e
    t
    3
    l
    y
    _
    r
    3
    k
    t
    }
    -->
    
    [..]

