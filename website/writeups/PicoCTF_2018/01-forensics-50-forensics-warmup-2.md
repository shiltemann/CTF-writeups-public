---
layout: writeup
title: 'Forensics 50: Forensics Warmup 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{extensions_are_a_lie}
---
## Challenge

Hmm for some reason I can't open [this PNG](writeupfiles/flag.png)? Any
ideas?

## Solution

Turns out the file isn't actually a png file (though gimp will open it
even with  
the wrong extension)

    $ file flag.png
    flag.png: JPEG image data, JFIF standard 1.01, resolution (DPI), density 75x75, segment length 16, baseline, precision 8, 909x190, frames 3
{: .language-bash}

![](writeupfiles/flag2.jpg)

