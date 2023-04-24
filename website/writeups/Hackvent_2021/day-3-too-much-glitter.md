---
layout: writeup
title: 'Day 3: Too Much GlItTer'
level:
difficulty:
points:
categories: []
tags: []
flag: HV{n3V3r_Sh0w_Y0uR_.git}
---
**Challenge**  
Well, well, well, what's that I see there?

## Solution

I can't tell what the word is due to the font so I ran it through
`uniname` (or just used a serif font)

    $ echo GlItTer | uniname
    No LINES variable in environment so unable to determine lines per page.
    Using default of 24.
    character  byte       UTF-32   encoded as     glyph   name
            0          0  000047   47             G      LATIN CAPITAL LETTER G
            1          1  00006C   6C             l      LATIN SMALL LETTER L
            2          2  000049   49             I      LATIN CAPITAL LETTER I
            3          3  000074   74             t      LATIN SMALL LETTER T
            4          4  000054   54             T      LATIN CAPITAL LETTER T
            5          5  000065   65             e      LATIN SMALL LETTER E
            6          6  000072   72             r      LATIN SMALL LETTER R
            7          7  00000A   0A                     LINE FEED (LF)

Clearly GIT relatd and indeed a `.git` directory exists. `wget -r` with
the git directory and suddenly we have a working git repo! There's a
`flag.html` file that's been deleted but it's empty currently and has
some text about it not being deployed yet, so we check the other
branches and `feature/flag` has what we're looking for!
