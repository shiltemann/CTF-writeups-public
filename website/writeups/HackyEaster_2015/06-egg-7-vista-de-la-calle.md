---
layout: writeup
title: 'Egg 7: Vista de la Calle'
level:
difficulty: easy
points:
categories: []
tags: []
flag: vUkFUmo4jola9izLhdS8

---
## Challenge:

*This egg is hidden in a street-view like viewer. Peek around the area
to find it!*

## Solution:

We look in the [apk](images/HackyEaster_2.0.apk) of the HackyEaster app,
and find the images used in the streetview app (in `/res/raw/`). Among
it the one containing the QRcode in the sky:

![](images/egg_07_streetviewimage_small.png)

The QRcode needs some enhancing before it can be scanned. We edit it in
gimp, (increase contrast, substitute colors with black/white, and draw a
white border around it). This gives us a scannable QR code:

![](images/egg_07_qrcode_large.png)

