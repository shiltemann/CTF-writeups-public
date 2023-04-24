---
layout: writeup
title: 'Yummi'
level:
difficulty:
points: 60
categories: [cryptography]
tags: []
flag:
---
Well [this](writeupfiles/baconian.bmp) image means something and we need
you to figure it out!

![](writeupfiles/baconian.bmp)

*Hint: Water -> Fish, Mud -> ???*

**Solution**   
All pixels were either black or white, convert to bitstring

    from PIL import Image
    
    img = Image.open('baconian.bmp').convert('RGB')
    pixels_orig = img.load()
    
    (w,h)=img.size
    
    s=[]
    for j in range(0,w):
        for i in range(0,h):    
          (r,g,b) = pixels_orig[j,i]
          s.append(str(r&1))
    
    print ''.join(s)
{: .language-python}

output

    000000000100010100110010101011011101010100100100100000100000000100111001101010000000001101

File was named `baconian.bmp` so we suspect a bacon cipher, and indeed
when we decode we get the flag
