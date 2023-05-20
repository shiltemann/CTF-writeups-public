---
layout: writeup
title: 'Dec 9: Sound Transmission'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-GnUj-1YQ7-vdYC-2wlr-E6xj

---

## Challenge

*Hear it, find it, brute it*

sadly we didnt receive todays code transmission properly and it seems
that a part of the information got lost. are you able to recover the
missing parts?

all we know is that the lowercase sha1 of the code gives:

    B39ECFBC2C64ADBB7C7A9292EEE31794D28FE224

and the sha1 of the case sensitive code should be:

    0D353038908AD0FC8C51A5312BB3E2FEE1CDDF83

[code\_transmission.mp3](wirtupfiles/code_transmission_g5Nzjnl_OMQs4RMdA6rU.mp3)

## Solution

In the sound file we hear a voice saying the following letters, question
marks indicate letters that were deliberatly left out.

    HV?5-G?UJ-1YQ7-?DYC-2WLR-E6?J

That looks like almost a nugget. There are 4 unknown characters, of
which we know the first one should be `1` to comply to the nugget
format.

That leaves 3 unknowns, and we know what the sha1 hash of the lowercase
version of the nugget should be. We can use that to find the 3 missing
characters, and then we can try all the different casing of the string
until we find the one that fits with the case-sensitive SHA1 hash. This
sound like a very reasonable brute-force problem, so we write a small
python script to solve it

    import itertools
    import hashlib

    def all_casings(input_string):
        if not input_string:
            yield ""
        else:
            first = input_string[:1]
            if first.lower() == first.upper():
                for sub_casing in all_casings(input_string[1:]):
                    yield first + sub_casing
            else:
                for sub_casing in all_casings(input_string[1:]):
                    yield first.lower() + sub_casing
                    yield first.upper() + sub_casing

    target_lc='B39ECFBC2C64ADBB7C7A9292EEE31794D28FE224'.lower()
    target_cs='0D353038908AD0FC8C51A5312BB3E2FEE1CDDF83'.lower()

    charset='abcdefghijklmnopqrstuvwxyz0123456789'


    for i in itertools.product(charset, repeat=3):
        nugget = 'hv15-g'+i[0]+'uj-1yq7-'+i[1]+'dyc-2wlr-e6'+i[2]+'j'
        h = hashlib.sha1()
        h.update(nugget)
        h_hex = h.hexdigest()
        if h_hex == target_lc:
            print 'Found lowercase match!'
            print ' --> nugget: '+ nugget +' (sha1 '+h.hexdigest()+')'
            break

    print 'Searching for the correct case..'
    for i in all_casings(nugget):
        h = hashlib.sha1()
        h.update(i)
        h_hex = h.hexdigest()
        if h_hex == target_cs:
            print 'Found case-sensitive match!'
            print ' --> nugget: '+ i + ' (sha1 '+h.hexdigest()+')'
            break
{: .language-python}

This outputs:

    Found lowercase match!
     --> nugget: hv15-gnuj-1yq7-vdyc-2wlr-e6xj (sha1 b39ecfbc2c64adbb7c7a9292eee31794d28fe224)
    Searching for the correct case..
    Found case-sensitive match!
     --> nugget: HV15-GnUj-1YQ7-vdYC-2wlr-E6xj (sha1 0d353038908ad0fc8c51a5312bb3e2fee1cddf83)



