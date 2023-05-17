---
layout: writeup
title: 'Day 15: Christmas Bauble'
level:
difficulty:
points:
categories: []
tags: []
flag: HV21{1st_P4rt_0f_th3_fl4g_with_the_2nd_P4rt_c0mb1ned_w17h_th4t}
---
## Challenge

The elves have started taking 3D modeling classes and have presented
Santa with a gift. What a nice gesture! But the ball feels heavier than
it should; what does that even mean for digital assets???

## Solution

I found an stl viewer and zoomed in, it's a 3d qr code (so cube with qrs
on 3 faces) internally and the bauble wrapped around, complementing the
last time they had a bauble with just a boring qr inside it. But for
decent perspective it'll help to rip off the bauble.

Not knowing how to blender, I just slowly selected groups of verticies
and deleted them until I was left with what was close enough to a cube.
Turning on orthographic projection (`5`) and then moving to the various
faces I could scan the QR codes.

Cleaned up stl file included here
[writeupfiles/15/obj.stl](writeupfiles/15/obj.stl)
