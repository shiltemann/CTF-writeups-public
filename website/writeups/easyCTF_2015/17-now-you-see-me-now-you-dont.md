---
layout: writeup
title: Now You See Me, Now You Don’t
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{just_playing_h1de_and_seek_lel}
---
**Challenge**  
Now you see me, now you don't? There's a file in /problems/elusive, but
I can't seem to find it. Find it and print its contents!  
**Solution**  
just a hidden file

    $ ls -la /problems/elusive
    total 12
    drwxr-xr-x  2 root root 4096 Nov  4 01:04 .
    drwxr-x--x 10 root root 4096 Nov  4 23:56 ..
    -rwxr-xr-x  1 root root   40 Nov  4 00:59 .hidden_file
    
    $ cat /problems/elusive/.hidden_file
    easyctf{just_playing_h1de_and_seek_lel}

