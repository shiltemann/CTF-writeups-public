---
layout: writeup
title: 'Forensics 150: Modern Picasso'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{wow_fast}
---
## Challenge

Here's a rendition of some modern digital abstract art. Is it more than
art though?

![](writeupfiles/picasso.gif)

## Solution

Using imagemagick to convert the white background in each frame to
transparant:

    convert picasso.gif -transparent white picasso_transparent.gif

gives a gif that slowly builds up the flag:

![](writeupfiles/picasso_transparent.gif)

## Flag

    IceCTF{wow_fast}

