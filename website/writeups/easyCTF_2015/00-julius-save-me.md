---
layout: writeup
title: Julius Save Me
level:
difficulty:
points:
categories: []
tags: []
flag: easyctf{Nap0leon_vs_Ca3s4r}

---

## Challenge
This cipher involves shifty letters!

    Cnebnl Vtxltk bl max uxlm unm fr ykbxgw ebdxl Gtihexhg uxmmxk yhk patmxoxk kxtlhg. Ha pxee. Max yetz bl xtlrvmy{Gti0exhg_ol_Vt3l4k}

## Solution

Clearly need to apply a ROT cipher, this website convenieently
calculates all shifts for you http://planetcalc.com/1434/

Turns out ROT7 was what we needed:

    Julius Caesar is the best but my friend likes Napoleon better for whatever reason. Oh well. The flag is easyctf{Nap0leon_vs_Ca3s4r}

