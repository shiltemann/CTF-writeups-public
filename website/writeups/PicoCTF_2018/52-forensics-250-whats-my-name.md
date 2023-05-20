---
layout: writeup
title: 'Forensics 250: What’s My Name?'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{w4lt3r_wh1t3_2d6d3c6c75aa3be7f42debed8ad16e3b}
---
**Chalenge**  
 Say my name, say [my name.](./writeupfiles/myname.pcap)

**Solution**  
Open the pcap file in wireshark, use `dns` as a packet filter, we look
in the response section and there's a TXT record with the flag.

