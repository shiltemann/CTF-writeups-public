---
layout: writeup
title: Teaser
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

In order to sweeten the waiting time, we are providing a teaser
challenge in advance.  
Download the [video file](writeupfiles/he2019_teaser.mp4), and find the
teaser Easter egg.

## Solution

The video seems to flash different colours. We use ffmpeg to find the
number of frames:

    $ ffprobe -v warning -show_frames he2019_teaser.mp4 | grep -c '\[/FRAME\]'
    230400
{: .language-bash}

so we find that it has 230400 frames, which all appear to be a single
colour (mostly black and white, some purple).  
230400 is exactly 480x480, so maybe each frame is a pixel and we need to
convert this to an image, hopefully a QR code  
let's try it:

    
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
{: .language-python3}

which gives us the following image:

![](writeupfiles/teaser_out.png)

## Egg

    he19-th1s-isju-5tAt-Eazr

