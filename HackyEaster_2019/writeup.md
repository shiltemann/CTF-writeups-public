# HackyEaster 2019

The annual advent calender from Hacking-lab

![](writeupfiles/title.png)


## Overview

Title                                             | Difficulty | Egg
------------------------------------------------- | ---------- | ------------------------------
[Teaser     ](#teaser)                            |            | `he19-th1s-isju-5tAt-Eazr`
[01 - Twisted](#01_twisted)                       |            | `he19-`
[02 - Just Watch](#02_just_watch)                 |            | `he19-`

## Teaser

**Challenge**

In order to sweeten the waiting time, we are providing a teaser challenge in advance.
Download the [video file](writeupfiles/he2019_teaser.mp4), and find the teaser Easter egg.

**Solution**

The video seems to flash different colours. We use ffmpeg to find the number of frames:

```bash
$ ffprobe -v warning -show_frames he2019_teaser.mp4 | grep -c '\[/FRAME\]'
230400
```

so we find that it has 230400 frames, which all appear to be a single colour (mostly black and white, some purple).
230400 is exactly 480x480, so maybe each frame is a pixel and we need to convert this to an image, hopefully a QR code
let's try it:

```python3

import moviepy.editor as mpe
from PIL import Image

# load video
video = mpe.VideoFileClip('he2019_teaser.mp4')


outimg = Image.new( 'RGB', (480,480), "white")
pixels_out = outimg.load()

framenum = 0
for frame in video.iter_frames():

    # set pixel to colour of video frame
    pixels_out[framenum%480,framenum/480] = (frame[0][0][0],frame[0][0][1],frame[0][0][2])
    framenum += 1

# save image
outimg.save("teaser_out.png","png")

```

which gives us the following image:

![](writeupfiles/teaser_out.png)

**Egg**

```
he19-th1s-isju-5tAt-Eazr
```

## 01 Twisted

**Challenge**

**Solution**

**Egg**


## 02 Twisted

**Challenge**

**Solution**

**Egg**


## 03 Twisted

**Challenge**

**Solution**

**Egg**

## 04 Twisted

**Challenge**

**Solution**

**Egg**

## 05 Twisted

**Challenge**

**Solution**

**Egg**

## 06 Twisted

**Challenge**

**Solution**

**Egg**

## 07 Twisted

**Challenge**

**Solution**

**Egg**

## 08 Twisted

**Challenge**

**Solution**

**Egg**

## 09 Twisted

**Challenge**

**Solution**

**Egg**

## 10 Twisted

**Challenge**

**Solution**

**Egg**

## 11 Twisted

**Challenge**

**Solution**

**Egg**

## 12 Twisted

**Challenge**

**Solution**

**Egg**

## 13 Twisted

**Challenge**

**Solution**

**Egg**

## 14 Twisted

**Challenge**

**Solution**

**Egg**

## 15 Twisted

**Challenge**

**Solution**

**Egg**

## 16 Twisted

**Challenge**

**Solution**

**Egg**

## 17 Twisted

**Challenge**

**Solution**

**Egg**

## 18 Twisted

**Challenge**

**Solution**

**Egg**

## 19 Twisted

**Challenge**

**Solution**

**Egg**

## 20 Twisted

**Challenge**

**Solution**

**Egg**

## 21 Twisted

**Challenge**

**Solution**

**Egg**

## 22 Twisted

**Challenge**

**Solution**

**Egg**

## 23 Twisted

**Challenge**

**Solution**

**Egg**

## 24 Twisted

**Challenge**

**Solution**

**Egg**

## 25 Twisted

**Challenge**

**Solution**

**Egg**

## 26 Twisted

**Challenge**

**Solution**

**Egg**

## 27 Twisted

**Challenge**

**Solution**

**Egg**


