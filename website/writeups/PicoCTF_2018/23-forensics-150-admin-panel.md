---
layout: writeup
title: 'Forensics 150: admin panel'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{n0ts3cur3_894a6546}
---
## Challenge

We captured some traffic logging into the admin panel, can you find the
password?

## Solution

They've provided a [pcap file](./writeupfiles/data.pcap), there's a POST
to  
/login which looks obvious. Following the stream as HTTP shows the
password  
quite clearly.

