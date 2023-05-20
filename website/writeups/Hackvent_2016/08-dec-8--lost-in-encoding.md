---
layout: writeup
title: 'Dec 8:  Lost In Encoding'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-l0st-1n7r-4nsl-4710-n00b

---

## Challenge

*Multiple encodings = good encryption?*

Santa and his elves do not know good encryption, all they have heard
about are some basic encodings. Unfortunately they all are bungling and
forgotten the recipe.

It's now on you, who has to get it up.
[file](writeupfiles/l0st_1n_7ranslation.fun)

    =ybegin line=128 size=333 name=lost_in_translation_2.txt
    zr^\”„˜‚o„‘‚¤s^uZ€xt}¢l]’‹mžos£•œ{rl¤}_o„“[vka‚‚{•|”¢r|“cn~q_Z|ws[Z¡{~–—y£žo{•_xyoœ~£¤uZ£{n_mx“€pŽo{Ÿ—k¢{nžz¤€ov[’s
    u£žp€”™›|otzs•tq‚£x”wZ|uum••|x|¤šl€oš[t•|tƒ¡ {—žxyco}Z•“w•ka¤wš{nžm|‚xqu€l vol‚vq—uZxv”pl€}ƒ¡u•€”ƒo„n{n¡€“|l€otn|£cv„o—
    {——yqo „£—sZ‘”xZ™a{~kc|{¡„•xs”l|€”[—wq¢lq™ ~‚oY„ox“xZ™—wq€“w‚w¡}•’£”^g4
    =yend size=333 crc32=6389b315

## Solution

yEnc decode (http://www.webutils.pl/index.php?idx=yenc)

    PH42WjZnXEZgXzI4K0VNJSxBU3UhaCtEIykrQHBzSW5EZi1cLUA7XXQkRjxHRi9DTG5Wc0RMI10wQTlm
    OytEQk5NOEUrTyczK0UyQD5CNiVFdEQuUmAxQDtePzVEL1hIKytFVjoqREJPIkJGXyNjM0RKKCkkRWNs
    RzpBVEp1JkRJYWwvQmtNOW9ES0kiMkA7WzMpQDtCRXNGKVBvLEBXLGUmK0NULjFBVSYwKkVjYEZDQDsw
    ViRBVEJDRy9LZEsmQmsmOGEvZysmI0gjN0o7QTA9RUQwZkNWIjBRVj1mMGxBcGovTXE/ZENiN0omMGVi
    MXMwSkhyfj4=

base64 decode

    <~6Z6g\F`_28+EM%,ASu!h+D#)+@psInDf-\-@;]t$F<GF/CLnVsDL#]0A9f;+DBNM8E+O'3+E2@>B6%
    EtD.R`1@;^?5D/XH++EV:*DBO"BF_#c3DJ()$EclG:ATJu&DIal/BkM9oDKI"2@;[3)@;BEsF)Po,@W,
    e&+CT.1AU&0*Ec`FC@;0V$ATBCG/KdK&Bk&8a/g+&#H#7J;A0=ED0fCV"0QV=f0lApj/Mq?dCb7J&0eb
    1s0JHr~>

base85 decode
(https://www.tools4noobs.com/online\_tools/ascii85\_decode/)

    Computer science education cannot make anybody an expert programmer any more
    than studying brushes and pigment can make somebody an expert painter.
    - Eric S. Raymond HV16-l0st-1n7r-4nsl-4710-n00b


