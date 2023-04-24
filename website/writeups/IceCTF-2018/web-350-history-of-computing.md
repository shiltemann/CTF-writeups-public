---
layout: writeup
title: 'Web 350: History of Computing'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

One of the authors of IceCTF made this page but I don't think it's very
accurate. Can you take hack it before the IceCTF team gets sued?

## Solution

A blogging website with registration/login forms and comment submission

![](writeupfiles/historyofcomputing_screenshot.png)

If we log in we get a cookie

    token:   eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0.eyJ1c2VybmFtZSI6InRlc3R1c2VyIiwiZmxhZyI6IkljZUNURntob3BlIHlvdSBkb24ndCB0aGluayB0aGlzIGlzIGEgcmVhbCBmbGFnfSJ9.
    session: eyJ1c2VyIjozfQ.DnrHzA.T60QwnNSuvq2HH0VSnNqqzFZ-24

which base64 decode to:

    token: {"typ":"JWT","alg":"none"}.{"username":"testuser","flag":"IceCTF{hope you don't think this is a real flag}"}
    session: {"user":3}.?.?

## Flag

