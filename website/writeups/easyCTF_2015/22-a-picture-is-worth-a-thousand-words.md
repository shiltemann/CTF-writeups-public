---
layout: writeup
title: A Picture is Worth a Thousand Words
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{it_must_be_pretty_hard_reading_this}
---
## Challenge

A picture is worth a thousand words. Can you find the (JPEG) picture
among a thousand files? Connect to the EasyCTF SSH server and find the
files in /problems/1000words. The files are also available for download
[here](writeupfiles/data.zip).

## Solution

    $ cd /problems/1000words
    
    $ file * |grep image
    hOQU3ZIhDXEfFijv: Netpbm PAM image file
    OmGJGyLejXS3Olrm: Netpbm PPM image, ASCII text, with very long lines
    UgeVjTlmZjNFvULk: JPEG image data, JFIF standard 1.01

![](writeupfiles/image.jpg)

