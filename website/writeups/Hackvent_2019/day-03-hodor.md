---
layout: writeup
title: 'Day 03: Hodor'
level:
difficulty:
points:
categories: []
tags: []
flag: HV19{h01d-th3-d00r-4204-ld4Y}
---
## Description

![](writeupfiles/dec03/hodor.jpg)

    $HODOR: hhodor. Hodor. Hodor!?  = `hodor?!? HODOR!? hodor? Hodor oHodor. hodor? , HODOR!?! ohodor!?  dhodor? hodor odhodor? d HodorHodor  Hodor!? HODOR HODOR? hodor! hodor!? HODOR hodor! hodor? !
    
    hodor?!? Hodor  Hodor Hodor? Hodor  HODOR  rhodor? HODOR Hodor!?  h4Hodor?!? Hodor?!? 0r hhodor?  Hodor!? oHodor?! hodor? Hodor  Hodor! HODOR Hodor hodor? 64 HODOR Hodor  HODOR!? hodor? Hodor!? Hodor!? .
    
    HODOR?!? hodor- hodorHoOodoOor Hodor?!? OHoOodoOorHooodorrHODOR hodor. oHODOR... Dhodor- hodor?! HooodorrHODOR HoOodoOorHooodorrHODOR RoHODOR... HODOR!?! 1hodor?! HODOR... DHODOR- HODOR!?! HooodorrHODOR Hodor- HODORHoOodoOor HODOR!?! HODOR... DHODORHoOodoOor hodor. Hodor! HoOodoOorHodor HODORHoOodoOor 0Hooodorrhodor HoOodoOorHooodorrHODOR 0=`;
    hodor.hod(hhodor. Hodor. Hodor!? );

(also in [hodor.md](writeupfiles/dec03/hodor.md))

## Solution

This is the esoteric programming language Hodor
([http://www.hodor-lang.org/][1])

We can install hodor language

    npm install -g hodor-lang
{: .language-bash}

then run our script

    $ hodor hodor.hd
    HODOR: \-> hodor.hd
    Awesome, you decoded Hodors language!
    
    As sis a real h4xx0r he loves base64 as well.
    
    SFYxOXtoMDFkLXRoMy1kMDByLTQyMDQtbGQ0WX0=
{: .language-bash}

We base64 decode this string to get our flag



[1]: http://www.hodor-lang.org/
