---
layout: writeup
title: 'Spotlight'
level: 1
difficulty:
points:
categories: [web]
tags: []
flag: IceCTF{5tup1d_d3v5_w1th_th31r_l095}
---
## Challenge

Someone turned out the lights and now we can't find anything. Send halp!

## Solution

They built a really nice page with a "spotlight" feature that follows
your  
mouse around. However the flag is in a console.log statement in the
associated  
[javascript file](./writeupfiles/spotlight.js).

    console.log("DEBUG: IceCTF{5tup1d_d3v5_w1th_th31r_l095}");
