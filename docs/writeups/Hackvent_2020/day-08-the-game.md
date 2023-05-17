---
layout: writeup
title: 'Day 08: The Game'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{}
---
## Description

Let's play another little game this year. Once again, as every year, I
promise it is hardly obfuscated.

[Game](writeupfiles/dec8.txt)

## Solution

    perl -Mre=eval -MO=Deparse dec8.txt > dec8_deparse.pl

makes it a bit more readable.

We can run the code in perl, and see it is a tetris game

    $ sudo apt install libterm-readkey-perl
    $ perl dec8.txt
{: .language-bash}

There is a flag in there that leads to a Rick roll youtube video, hmm.. 

We can fiddle with the code a bit to make it easier to play (slower
drops, and we make all shapes squares,
[dec8\_tidy.pl](writeupfiles/dec8_tidy.p.)

The letter that drop do not follow exactly the rick roll video link,

`HV20{https://www.youtube.com/watch?v=dlh4hs0chjl0}`

but this other link it gives also doesnt lead anywhere, hmm, did we emss
with some setting that affect the outcome?

