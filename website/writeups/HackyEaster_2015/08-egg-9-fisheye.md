---
layout: writeup
title: 'Egg 9: Fisheye'
level:
difficulty:
points:
categories: []
tags: []
flag: K3sUBDOu5BtGDtcJ9lgc

---

## Challenge
\[mobile challenge\]

*Egg number nine is hidden in the app. You've already seen it, haven't
you?*

*Go catch it and squint like a fish*

## Solution

The (distorted) image of the egg is the splash screen of the app. In the
[apk]() we find the image:

![](images/egg_09_distorted_small.png)

we open the screenshot in gimp, crop to give only the QR code, and
distort until it becomes readable

in Gimp we used Filters->Distorts->Lens Distortion,

![](images/egg_09_screenshot.png)

This leads to the following image which the app can read

![](images/egg_09_undistorted.png)

