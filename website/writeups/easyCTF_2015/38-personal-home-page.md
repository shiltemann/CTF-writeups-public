---
layout: writeup
title: Personal Home Page
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{file_get_contents_is_9_safe}
---
**Challenge**  
I hate PHP. (with link to website)

Website:

Welcome to my awesome site!

This is the first site I have made with PHP.

Instead of serving pages normally, all the pages are fetched with PHP
before written to the screen!

This is probably super secure, so I have no problem hiding passwords or
flags here.

## Solution

    http://web.easyctf.com:10200/?page=supersecretflag.txt

