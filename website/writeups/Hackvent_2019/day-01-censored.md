---
layout: writeup
title: 'Day 01: Censored'
level:
difficulty:
points:
categories: []
tags: []
flag: HV19{just-4-PREview!}
---
## Challenge

I got this little image, but it looks like the best part got censored on
the way. Even the tiny preview icon looks clearer than this! Maybe they
missed something that would let you restore the original content?

![](writeupfiles/dec01/f182d5f0-1d10-4f0f-a0c1-7cba0981b6da.jpg)

## Solution

The description sounds like it has something to do with the thumbnail
image. Indeed if we download the image we see a QR code in the thumbnail
image in our file explorer. We can use exiftool to extract the thumbnail
image embedded in the JPEG format:

    exiftool -b -ThumbnailImage f182d5f0-1d10-4f0f-a0c1-7cba0981b6da.jpg > thumbnail.jpg

Then we read this with QR scanner to get our flag

![](writeupfiles/dec01/thumbnail.jpg)

