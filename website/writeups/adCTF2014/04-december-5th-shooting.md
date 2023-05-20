---
layout: writeup
title: 'December 5th: Shooting'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_1mP05518L3_STG
---
## Category

Web

## Hint

*Get 10000 pt. This game is really hard, and so you can crack it.*

## Challenge

The challenge consists of a javascript game
[zip](files/dec5/shooting.zip).

It appears we need to beat the game to get the flag.

## Solution

We make ourselves invincible by changing the 8 in the snippet from
*shooting.min.js* below to a 0:

     this.addEventListener($UPcs4hr8oKgbbqAesfT(1), function() {
        (player.within(this, 8)) ? (gamÃ©.end()) : 0;
     })

Next just play the game, when you reach 10000 points the flag appears in
an alert.

