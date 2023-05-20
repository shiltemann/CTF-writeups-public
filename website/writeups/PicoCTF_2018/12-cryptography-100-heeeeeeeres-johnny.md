---
layout: writeup
title: 'Cryptography 100: HEEEEEEERE’S Johnny!'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{J0hn_1$_R1pp3d_1b25af80}
---
## Challenge

Okay, so we found some important looking files on a linux computer.
Maybe they can be used to get a password to the process.  
Connect with `nc 2018shell1.picoctf.com 40157`. Files can be found here:
[passwd](writeupfiles/passwd) [shadow](writeupfiles/shadow).

## Solution

We use a combination of unshadow and john the ripper to find the
password

    $ unshadow passwd shadow > crackme
    $ john crackme
    Created directory: /home/saskia/.john
    Loaded 1 password hash (crypt, generic crypt(3) [?/64])
    Press 'q' or Ctrl-C to abort, almost any other key for status
    password1        (root)
    1g 0:00:00:01 100% 2/3 0.5102g/s 469.3p/s 469.3c/s 469.3C/s 123456..pepper
    Use the "--show" option to display all of the cracked passwords reliably
    Session completed

So we know the password for the root user is `password1`. We use that to
log into the server

    $ nc 2018shell1.picoctf.com 40157
    Username: root
    Password: password1
    picoCTF{J0hn_1$_R1pp3d_1b25af80}
{: .language-bash}

