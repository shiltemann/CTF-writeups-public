---
layout: writeup
title: 'Day 02: Triangulation'
level:
difficulty:
points:
categories: []
tags: []
flag: HV19{Cr4ck_Th3_B411!}
---
## Description

Today we give away decorations for your Christmas tree. But be careful
and do not break it.

[Triangulation.stl](writeupfiles/dec02/Triangulation.stl)

## Solution

This is a [STL file][1] containing a 3D model of a Christmas bauble:

![](writeupfiles/dec02/ball.png)

Nothing obvious there, but if we look at the wireframe view we see that
there is more inside:

![](writeupfiles/dec02/ball_wireframe.png)

Cutting through the ball in blender we see an aztec code inside. After
cleaning up the image, converting to b/w, removing noise, rotating, we
can [read the code][2]

![code](writeupfiles/dec02/code.png)



[1]: https://en.wikipedia.org/wiki/STL_(file_format)
[2]: https://www.onlinebarcodereader.com/
