---
layout: writeup
title: iSpy
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{pcap_fun!??}
---
## Challenge

We intercepted some suspicious network activity. We think that the enemy
has been exchanging important data. Can you help us figure out what it
is? You can find a copy of the file here

\[ispy.pcapng\](writeupfiles/ispy.pcapng

## Solution

Open with wireshark, extract http objects, one of the extracted files
was this image:

![](writeupfiles/ispy.jpg)

