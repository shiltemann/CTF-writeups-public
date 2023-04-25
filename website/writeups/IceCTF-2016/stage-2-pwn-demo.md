---
layout: writeup
title: 'Demo'
level: 2
difficulty:
points: 55
categories: [pwn]
tags: []
flag: IceCTF{wH0_WoU1d_3vr_7Ru5t_4rgV}
---
## Challenge

I found this awesome premium shell, but my demo version just ran out...
can you help me crack it? `/home/demo/` on the shell

## Solution

The [c source code](writeupfiles/demo.c) is provided alongside. It
checks
the environment variable for `_` which is the name of the executable
being
run, takes the basename of that, and compares against "icesh", exiting
if
not equal, otherwise giving root permissions which allow access to
`/home/demo/flag.txt`

So, we create a symlink to the file with a new name (in order to inherit

permissions of that file), and then run it which pops open a shell.

    [ctf-98836@icectf-shell-2016 ~]$ ln -s /home/demo/demo icesh
    [ctf-98836@icectf-shell-2016 ~]$ ./icesh
    $ bash
    ctf-98836@icectf-shell-2016:~$ cat /home/demo/flag.txt
    IceCTF{wH0_WoU1d_3vr_7Ru5t_4rgV}
