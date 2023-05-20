---
layout: writeup
title: 'Dec 2: Say me your name'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-AfDd-Mr5J-zf1v-K7aO-FQ4h

---

## Challenge

*... and i say you your language*

    pagh wa'vatlh netlh wa'maH wa'maH wa' SaD wa' SaD wa'vatlh wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'maH wa'maH netlh pagh wa'maH wa' wa'vatlh wa' wa'vatlh SaD SaD wa' wa'vatlh netlh wa'maH wa' wa'maH wa'maH wa'vatlh wa' wa'maH wa'maH wa' wa' wa'vatlh SaD wa' wa'maH wa'maH wa'maH wa'vatlh wa'maH SaD wa'maH wa' wa'maH wa'maH wa' wa' wa' wa'maH wa'vatlh wa' wa'vatlh wa' SaD wa' SaD wa'maH wa' wa' wa'maH wa' SaD  wa'maH wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'vatlh wa' wa'maH wa' wa' wa'maH wa' netlh wa'maH wa'vatlh wa' wa' wa' wa'vatlh wa'maH wa' wa'maH wa'maH SaD wa' wa'vatlh wa'maH SaD wa'vatlh wa' wa'maH SaD wa' wa'maH SaD

## Solution

Looks like Klingon. We translate it (see here for Klingon number system
http://klingonska.org/ref/num.html) to get:

    klingon = "pagh wa'vatlh netlh wa'maH wa'maH wa' SaD wa' SaD wa'vatlh wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'maH wa'maH netlh pagh wa'maH wa' wa'vatlh wa' wa'vatlh SaD SaD wa' wa'vatlh netlh wa'maH wa' wa'maH wa'maH wa'vatlh wa' wa'maH wa'maH wa' wa' wa'vatlh SaD wa' wa'maH wa'maH wa'maH wa'vatlh wa'maH SaD wa'maH wa' wa'maH wa'maH wa' wa' wa' wa'maH wa'vatlh wa' wa'vatlh wa' SaD wa' SaD wa'maH wa' wa' wa'maH wa' SaD  wa'maH wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'vatlh wa' wa'maH wa' wa' wa'maH wa' netlh wa'maH wa'vatlh wa' wa' wa' wa'vatlh wa'maH wa' wa'maH wa'maH SaD wa' wa'vatlh wa'maH SaD wa'vatlh wa' wa'maH SaD wa' wa'maH SaD"

    klingon2english = {'netlh':'tenthousand',
                       'SaD':'thousand',
                       "wa'vatlh":'onehundred',
                       "wa'maH":'ten',
                       "wa' ":'one ',
                       'pagh':'zero' }
    english=klingon

    for key in klingon2english:
        english=english.replace(key,klingon2english[key])

    print english
{: .language-python}

which outputs:

    zero onehundred tenthousand ten ten one thousand one thousand onehundred one ten ten onehundred ten one ten
    ten tenthousand zero ten one onehundred one onehundred thousand thousand one onehundred tenthousand ten one
    ten ten onehundred one ten ten one one onehundred thousand one ten ten ten onehundred ten thousand ten one
    ten ten one one one ten onehundred one onehundred one thousand one thousand ten one one ten one thousand
    ten one ten ten onehundred ten one onehundred one ten one one ten one tenthousand ten onehundred one one
    one onehundred ten one ten ten thousand one onehundred ten thousand onehundred one ten thousand one ten
    thousand

This looks like it could be describing binary. We make a quick python
script to decode Klingon --> binary --> ascii :

    import binascii

    klingon = "pagh wa'vatlh netlh wa'maH wa'maH wa' SaD wa' SaD wa'vatlh wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'maH wa'maH netlh pagh wa'maH wa' wa'vatlh wa' wa'vatlh SaD SaD wa' wa'vatlh netlh wa'maH wa' wa'maH wa'maH wa'vatlh wa' wa'maH wa'maH wa' wa' wa'vatlh SaD wa' wa'maH wa'maH wa'maH wa'vatlh wa'maH SaD wa'maH wa' wa'maH wa'maH wa' wa' wa' wa'maH wa'vatlh wa' wa'vatlh wa' SaD wa' SaD wa'maH wa' wa' wa'maH wa' SaD  wa'maH wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'vatlh wa' wa'maH wa' wa' wa'maH wa' netlh wa'maH wa'vatlh wa' wa' wa' wa'vatlh wa'maH wa' wa'maH wa'maH SaD wa' wa'vatlh wa'maH SaD wa'vatlh wa' wa'maH SaD wa' wa'maH SaD"
    klingon2binary = {'netlh':'10000', 'SaD':'1000', "wa'vatlh":'100', "wa'maH":'10', "wa' ":'1', 'pagh':'0' }

    binary=klingon
    for key in klingon2binary:
        binary=binary.replace(key,klingon2binary[key])

    binary = binary.replace(' ','')
    n = int(binary, 2)
    nugget = binascii.unhexlify('%x' % n)

    print binary
    print nugget
{: .language-python}

which output the following:

    0100100001010110001100010011010100101101010000010110011001000100011001000010110101001101011100100011010101
    0010100010110101111010011001100011000101110110001011010100101100110111011000010100111100101101010001100101
    00010011010001101000

    HV15-AfDd-Mr5J-zf1v-K7aO-FQ4h


