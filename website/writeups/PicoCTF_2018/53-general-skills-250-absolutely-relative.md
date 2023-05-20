---
layout: writeup
title: 'General Skills 250: absolutely relative'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{3v3r1ng_1$_r3l3t1v3_a97be50e}
---
## Challenge

In a filesystem, everything is relative ¯\_(ツ)\_/¯. Can you find a way
to get a flag from this program? You can find it in
/problems/absolutely-relative\_1\_15eb86fcf5d05ec169cc417d24e02c87 on
the shell server. Source.

## Solution

They provide the [source](./writeupfiles/absoluterelative.c) of
absolutely-relative, it checks for a file named `./permission.txt` with
the contents `yes`.

    hxr@pico-2018-shell-1:~$ echo -n 'yes'  > permission.txt
    hxr@pico-2018-shell-1:~$ /problems/absolutely-relative_1_15eb86fcf5d05ec169cc417d24e02c87/absolutely-relative
    You have the write permissions.
    picoCTF{3v3r1ng_1$_r3l3t1v3_a97be50e}

