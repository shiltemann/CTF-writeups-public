---
layout: writeup
title: 'Cryptography 200: hertz 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{substitution_ciphers_are_too_easy_sgsgtnpibo}
---
## Challenge

This flag has been encrypted with some kind of cipher, can you decrypt
it? Connect with `nc 2018shell1.picoctf.com 12521`.

## Solution

When we connect we are given a ciphertext

    Yln mvsfi ugbxe abj tvkow bcng yln qrzd pbh. S fre'y unqsncn ylsw sw wvfl re nrwd ogbuqnk se Osfb. Sy'w rqkbwy rw sa S wbqcnp r ogbuqnk rqgnrpd! Bird, asen. Lngn'w yln aqrh: osfbFYA{wvuwysyvysbe_fsolngw_rgn_ybb_nrwd_whwhyeosub}

we input this to https://quipqiup.com/ and it decodes to

    The quick brown fox jumps over the lazy dog. I can't believe this is such an easy problem in Pico. It's almost as if I solved a problem already! Okay, fine. Here's the flag: picoCTF{substitution_ciphers_are_too_easy_sgsgtnpibo}

