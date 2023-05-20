---
layout: writeup
title: 'Forensics 250: Lying out'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{w4y_0ut_ff5bd19c}
---
**Challenge**  
Some odd [traffic](./writeupfiles/traffic.png) has been detected on the
network, can you identify it? More info here. Connect with nc
2018shell1.picoctf.com 50875 to help us answer some questions.

## Solution

    You'll need to consult the file `traffic.png` to answer the following questions.
    Which of these logs have significantly higher traffic than is usual for their time of day? You can see usual traffic on the attached plot. There may be multiple logs with higher than usual traffic, so answer all of them! Give your answer as a list of `log_ID` values separated by spaces. For example, if you want to answer that logs 2 and 7 are the ones with higher than usual traffic, type 2 7.
        log_ID      time  num_IPs
    0        0  00:00:00     9552
    1        1  02:30:00    11573
    2        2  06:00:00    10381
    3        3  07:00:00    11674
    4        4  07:00:00    10224
    5        5  07:30:00    10966
    6        6  16:00:00     9685
    7        7  17:45:00    15875
    8        8  18:00:00    11889
    9        9  19:15:00    11935
    10      10  19:30:00    11191
    11      11  20:30:00     9952
    12      12  20:45:00     9898
    13      13  22:45:00    11609
    1 3 7 13
    Correct!
    Great job. You've earned the flag: picoCTF{w4y_0ut_ff5bd19c}

