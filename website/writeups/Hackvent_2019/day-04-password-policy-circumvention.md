---
layout: writeup
title: 'Day 04: password policy circumvention'
level:
difficulty:
points:
categories: []
tags: []
flag: HV19{R3memb3r, rem3mber - the 24th 0f December}
---
## Description

Santa released a new password policy (more than 40 characters, upper,
lower, digit, special).

The elves can't remember such long passwords, so they found a way to
continue to use their old (bad) password:

    merry christmas geeks

![HV19-PPC.ahk](writeupfiles/dec04/HV19-PPC.ahk)

## Solution

This is a Windows [AutoHotkey][1] Script

    tTime , x,, MM MMMM yyyy
    SendInput, %x%{left 4}{del 2}+{right 2}^c{end}{home}^v{home}V{right 2}{ASC 00123}
    return
    
    ::christmas::
    SendInput HV19-pass-w0rd
    return
    
    :*?:is::
    Send - {del}{right}4h
    
    :*?:as::
    Send {left 8}rmmbr{end}{ASC 00125}{home}{right 10}
    return
    
    :*?:ee::
    Send {left}{left}{del}{del}{left},{right}e{right}3{right 2}e{right}{del 5}{home}H{right 4}
    return
    
    :*?:ks::
    Send {del}R3{right}e{right 2}3{right 2} {right 8} {right} the{right 3}t{right} 0f{right 3}{del}c{end}{left 5}{del 4}
    return
    
    ::xmas::
    SendInput, -Hack-Vent-Xmas
    return
    
    ::geeks::
    Send -1337-hack
    return
{: .language-ahk}

We start a Windows VM, install AutoHotKey, add the script. Then open
notepad, and type `merry christmas geeks` and get our flag



[1]: https://www.autohotkey.com/
