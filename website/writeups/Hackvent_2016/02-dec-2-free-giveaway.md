---
layout: writeup
title: 'Dec 2: Free Giveaway'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-SDhs-qqpf-zQLp-OQH4-2Xmg

---

## Challenge

*the keys are the key*

Today, Santa has a free giveaway for you:

`DK16[OEdo[''lu[;"Nl[R"D4[2Qmi`

## Solution

Seems clear that that string represents the flag, vigenere cipher?

`D` is 4th letter in alphabet, if you shift 4 further you get `H`, `K`
is eleventh letter
and if you shift 11 further you get `V` ..but how to handle special
characters? `[` needs
to be shifted to `-`

This almost works..? (but seems too random to just exclude number and
not keep ascii
ordering of chars ..I dunno, it's day 2, probably overthinking?)

hint is *the key is in the keys* ..maybe only shift those symbols that
you need
the shift key to type? (so leave lowercase and numbers as is, shift
uppercase and
some other symbols?)

    import string

    flag="DK16[OEdo[''lu[;\"Nl[R\"D4[2Qmi"
    alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789 !\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~ "

    pt=''
    count=0
    for c in flag:
        if c in string.digits or c in string.lowercase:
            pt+=c
        else:
            pt += alphabet[((alphabet.find(c)+alphabet.find(flag[count]))+1)%len(alphabet)]
        count +=1

    print pt
{: .language-python}

Turns out it is just a translation from qwerty to dvorak. Inverting that
gives us the answer and explains the hint mentioning "keys":

    from string import maketrans

    QWERTY = '''-=qwertyuiop[]sdfghjkl;'zxcvbn,./_+QWERTYUIOP{}SDFGHJKL:"ZXCVBN<>?'''
    DVORAK = '''[]',.pyfgcrl/=oeuidhtns-;qjkxbwvz{}"<>PYFGCRL?+OEUIDHTNS_:QJKXBWVZ'''
    TRANS = maketrans(DVORAK, QWERTY)

    flag="DK16[OEdo[''lu[;\"Nl[R\"D4[2Qmi"

    print ''.join([x.translate(TRANS) for x in list(flag)])
{: .language-python}


