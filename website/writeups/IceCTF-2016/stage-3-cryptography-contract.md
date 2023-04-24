---
layout: writeup
title: 'Stage 3 Cryptography: Contract'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

Our contractors stole the flag! They put it on their file server and
challenged us to get it back. Can you do it for us? nc
contract.vuln.icec.tf 6002 server.py. We did intercept someone
connecting to the server though, maybe it will help. contract.pcapng

## Solution

They have an RSA key, and any incoming request must be signed according
to that file. There is a pcap file associated which provides a couple of
useful packets we can replay:

    help:c0e1fc4e3858ac6334cc8798fdec40790d7ad361ffc691c26f2902c41f2b7c2fd1ca916de687858953a6405423fe156cfd7287caf75247c9a32e52ab8260e7ff1e46e55594aea88731bee163035f9ee31f2c2965ac7b2cdfca6100d10ba23826
    
    COMMANDS:
    * read [file]
     - prints contents of file
    * time
     - prints the current time
    * help
     - prints this message
^

    time:c0e1fc4e3858ac6334cc8798fdec40790d7ad361ffc691c26f2902c41f2b7c2fd1ca916de687858953a6405423fe156c0cbebcec222f83dc9dd5b0d4d8e698a08ddecb79e6c3b35fc2caaa4543d58a45603639647364983301565728b504015d
    2016-08-11 22:48:48

## Flag



