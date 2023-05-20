---
layout: writeup
title: 'Dec 1: Detours'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-t8Kd-38aY-QxL5-bn4K-c6Lw

---

## Challenge

Santa receives an email with links to three pictures, but every picture
is the same.
He talks with some of his elves and one says, that there is some weird
stuff happening
when loading these pictures. Can you identify it?

    http://ow.ly/unCT306N19f
    http://ow.ly/xW3h306N18f
    http://ow.ly/3wfc306N10K

## Solution

All three links lead to the same [image on wikipedia][1]:

![](writeupfiles/sint.jpg)

There must be something going on with the shortened urls. We get the
images with
`wget` for some more info:

    $ wget http://ow.ly/unCT306N19f

    Resolving ow.ly (ow.ly)... 54.183.131.91, 54.67.57.56, 54.67.62.204, ...
    Connecting to ow.ly (ow.ly)|54.183.131.91|:80... connected.
    HTTP request sent, awaiting response... 301 Moved Permanently
    Location: http://bit.do/HV16-t8Kd [following]
    --2016-12-05 22:51:21--  http://bit.do/HV16-t8Kd
    Resolving bit.do (bit.do)... 54.83.52.76
    Connecting to bit.do (bit.do)|54.83.52.76|:80... connected.
    HTTP request sent, awaiting response... 301 Moved Permanently
    Location: https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg/220px-Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg [following]
    --2016-12-05 22:51:22--  https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg/220px-Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg
    Resolving upload.wikimedia.org (upload.wikimedia.org)... 91.198.174.208, 2620:0:862:ed1a::2:b
    Connecting to upload.wikimedia.org (upload.wikimedia.org)|91.198.174.208|:443... connected.
    HTTP request sent, awaiting response... 200 OK
    Length: 21433 (21K) [image/jpeg]
    Saving to: ‘unCT306N19f’

    unCT306N19f         100%[===================>]  20,93K  --.-KB/s    in 0,05s

    2016-12-05 22:51:22 (391 KB/s) - ‘unCT306N19f’ saved [21433/21433]

Hmm, the `ow.ly` link resolves to `http://bit.do/HV16-t8Kd` this looks
like the
start of a nugget!

we do the same for the other two links and find

    http://bit.do/38aY-QxL5
    http://bit.do/bn4K-c6Lw

Putting it all together gives the nugget

## Nugget

    HV16-t8Kd-38aY-QxL5-bn4K-c6Lw



[1]: https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg/220px-Intocht_van_Sinterklaas_in_Schiedam_2009_%284102602499%29_%282%29.jpg
