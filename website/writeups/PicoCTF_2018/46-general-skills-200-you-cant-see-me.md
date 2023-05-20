---
layout: writeup
title: 'General Skills: 200: you can’t see me'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{j0hn_c3na_paparapaaaaaaa_paparapaaaaaa_22f627d9}
---
**Challenge**  
'...reading transmission... Y.O.U. .C.A.N.'.T. .S.E.E. .M.E.
...transmission ended...' Maybe something lies in
/problems/you-can-t-see-me\_4\_8bd1412e56df49a3c3757ebeb7ead77f.

## Solution

    hxr@pico-2018-shell-1:~$ ls -al /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f
    total 60
    drwxr-xr-x   2 root       root        4096 Sep 28 08:29 .
    -rw-rw-r--   1 hacksports hacksports    57 Sep 28 08:29 .
    drwxr-x--x 556 root       root       53248 Sep 28 08:29 ..

there's no easy way to access that

    hxr@pico-2018-shell-1:~$ cat /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/.*
    cat: /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/.: Is a directory
    picoCTF{j0hn_c3na_paparapaaaaaaa_paparapaaaaaa_22f627d9}
    cat: /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/..: Permission denied

