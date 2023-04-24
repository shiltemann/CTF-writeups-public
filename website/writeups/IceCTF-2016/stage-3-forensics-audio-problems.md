---
layout: writeup
title: 'Audio Problems'
level: 3
difficulty:
points:
categories: [forensics]
tags: []
flag: IceCTF{y0U_b3t7Er_l15TeN_cL053LY}
---
## Challenge

We intercepted [this](writeupfiles/audio_problems.wav) audio signal, it
sounds like there could be something hidden in it. Can you take a look
and see if you can find anything?

## Solution

We open the file in audacity, nothing interesting to hear, but the
spectrogram looks interesting, we clean it up using following settings
to get the flag from spectorgram

    scale: linear
    algorithm: Frequencies
    window size: 2048
    window type: Hanning

![](writeupfiles/spectogram.png)

