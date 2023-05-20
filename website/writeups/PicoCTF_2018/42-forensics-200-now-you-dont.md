---
layout: writeup
title: 'Forensics 200: now you don’t'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{n0w_y0u_533_m3}
---
## Challenge

We heard that there is something hidden in this picture. Can you find
it?

![](writeupfiles/nowYouDont.png)

## Solution

We extract the LSB of each of the colour channels using [this script][1]

and find the flag in the red channel:

    $ python3 extractlsb.py -i nowYouDont.png
{: .language-bash}

This script outputs 3 black-and-white images, each signifying the least
significant bit of each of the 3 RGB channels.

the outputfile for the red channel, `nowYouDont_lsb_r.png`, contained
the flag:

![](writeupfiles/nowyouseeme.png)



[1]: https://github.com/shiltemann/CTF-writeups-public/blob/master/_resources/code/stegano/extractlsb.py
