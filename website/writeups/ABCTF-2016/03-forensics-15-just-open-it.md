---
layout: writeup
title: 'Forensics 15: Just open it'
level:
difficulty:
points: 15
categories: [forensics]
tags: []
flag: ABCTF{forensics_1_tooo_easy?}
---
**Challenge**   
I'm almost positive we put a flag in
[this](writeupfiles/forensics15.jpg) file. Can you find it for me?

*Hint: So many editors out there!*

**Solution**   
file was an image:

![](writeupfiles/forensics15.jpg)

    $ strings forensics15.jpg
    [..]
    f~ZF.~
    QEAaE
    P ABCTF{forensics_1_tooo_easy?}
    =0s^
    )8,=
    Wt;fR
    [..]