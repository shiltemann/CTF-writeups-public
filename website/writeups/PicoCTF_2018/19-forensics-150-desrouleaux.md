---
layout: writeup
title: 'Forensics 150: Desrouleaux'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{J4y_s0n_d3rUUUULo_b6cacd6c}
---
**Challenge**  
 Our network administrator is having some trouble handling the tickets
for all of of our incidents. Can you help him out by answering all the
questions? Connect with nc 2018shell1.picoctf.com 40952.

**Solution**  
The [incidents.json](./writeupfiles/incidents.json) is a pretty small
file surprisingly, everything is accomplished with `jq`:

    $ nc 2018shell1.picoctf.com 40952
    You'll need to consult the file `incidents.json` to answer the following questions.
    What is the most common source IP address? If there is more than one IP address that is the most common, you may give any of the most common ones.
    99.32.28.173
    
    Correct!
    
    How many unique destination IP addresses were targeted by the source IP address 99.32.28.173?
    3
    
    Correct!
    
    What is the average number of unique destination IP addresses that were sent a file with the same hash? Your answer needs to be correct to 2 decimal places.
    1.67
    
    Correct!
    
    Great job. You've earned the flag: picoCTF{J4y_s0n_d3rUUUULo_b6cacd6c}

## Question2

    $ cat incidents.json | jq .tickets[].src_ip -r | sort | uniq -c | sort -n | tail -n 1
    $ cat incidents.json | jq '.tickets[] | select(.src_ip == "99.32.28.173")'
    $ cat incidents.json | jq -r '.tickets[] | [.file_hash, .dst_ip] | @tsv' | sort
    336033417a7364f0        230.124.77.62
    336033417a7364f0        231.208.216.227
    65a8826931637d74        230.124.77.62
    65a8826931637d74        23.245.63.105
    811f58a6e15c0643        120.119.119.83
    811f58a6e15c0643        215.51.6.131
    b03dee2273112d13        107.111.202.130
    b03dee2273112d13        230.124.77.62
    bbd65e44921b880c        247.145.101.4
    dfd6f5d416878f69        231.208.216.227

## Answer

2+2+2+2+1+1 / 6 = 1.67 (6 different hashes, 4 of them got sent to 2
unique IPs, 2 of them just 1, average is 1.67)

