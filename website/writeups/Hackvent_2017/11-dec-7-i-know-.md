---
layout: writeup
title: 'Dec 7: I know …'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-UCyz-0yEU-d90O-vSqS-Sd64

---
*... what you did last xmas*

## Challenge

We were able to steal a file from santas computer. We are sure, he
prepared a gift and there are traces for it in this file.

Please help us to recover it: [SANTA.FILE](writeupfiles/SANTA.FILE)

## Solution

I have a feeling this wasn't intended to be this easy, but..

    $ file SANTA.FILE
    SANTA.FILE: Zip archive data, at least v1.0 to extract

    $ unzip SANTA.FILE
    Archive:  SANTA.FILE
      inflating: SANTA.IMA

    $ file SANTA.IMA
    SANTA.IMA: DOS/MBR boot sector, code offset 0x58+2, OEM-ID "WINIMAGE", sectors/cluster 4, root entries 16, sectors 3360 (volumes <=32 MB) , sectors/FAT 3, sectors/track 21, serial number 0x2b523d5, label: "           ", FAT (12 bit), followed by FAT

    $ strings SANTA.IMA | grep HV17
    Y*C:\Hackvent\HV17-UCyz-0yEU-d90O-vSqS-Sd64.exe
{: .language-bash}

Note: There was also a ROT-13 version of the flag, which is probably the
way the
challenge was designed to be solved:

    $ strings SANTA.IMA |grep -4 HV17
    -+/D
    &xNsb
    GameDVR_GameGUID
    TitleIdr
    Y*C:\Hackvent\HV17-UCyz-0yEU-d90O-vSqS-Sd64.exe
    Typey=
    Revision
    P:\Unpxirag\UI17-HPlm-0lRH-q90B-iFdF-Fq64.rkr
    969343ecc7b246e8426e573c30fd94c4ffa050c2
{: .language-bash}

