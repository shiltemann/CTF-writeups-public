---
layout: writeup
title: 'Zippy'
level:
difficulty:
points: 120
categories: [forensics]
tags: []
flag:
---
**Challenge**   
If your could fix this mess I am sure there would be a flag waiting for
you.

**Solution**   
The top level is a zip file with 53 files inside. They're all named  
`chunk0..chunk53.zip` so we concatenate them together to be a single
.zip file.

Attempting to open that indicates that a password is required.
Additionally it  
is indicated that there are 6890 bytes of junk in front. Not sure what
this means.

This is interesting, but not sure what to make of it:

    $ unzip -P 'abctf' tmp.zip
    Archive:  tmp.zip
    warning [tmp.zip]:  6890 extra bytes at beginning or within zipfile
      (attempting to process anyway)
       skipping: data.txt                incorrect password
    
    
    $ unzip -P 'ABCTF' tmp.zip
    Archive:  tmp.zip
    warning [tmp.zip]:  6890 extra bytes at beginning or within zipfile
      (attempting to process anyway)
     extracting: data.txt                 bad CRC 6517427e  (should be c2be35e6)
        (may instead be incorrect password)
    
    $ cat data.txt | od -tx2 -a
    0000000    fbec    de00
              l   { nul   ^
    0000004
