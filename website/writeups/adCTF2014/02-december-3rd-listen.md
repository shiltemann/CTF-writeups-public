---
layout: writeup
title: 'December 3rd: Listen'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_SOUNDS_GOOD
---
## Category

Misc

## Hint

*I couldn't listen it, can you?*

## Challenge

We are given a .wav file: [listen.wav](files/listen.wav)

## Solution

Opening the file in audacity reveals a normal sound pattern, but the
length is calculated to be 42 hours.

Opening the file as raw format, the time corrects to a 3.7 seconds. This
is a little fast, but sounds like speech. We slow this down by half and
listen to the message:

    The flag is in all capital letters A-D-C-T-F underscore SOUNDS underscore GOOD

