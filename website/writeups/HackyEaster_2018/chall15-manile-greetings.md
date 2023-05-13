---
layout: writeup

title: Manile Greetings
level:          # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-ifiI-T6ZT-TNyX-9DZp

---

## Challenge

Randy Waterhouse receives a package from his friend Enoch Root containing a deck of cards and a letter:

Dear Randy,

even though our stay in Manila was not very pleasant, I fondly think of our discussions there:

```
GTIFL RVLEJ TAVEY ULDJO KCCOK P
```

Wishing you happy Easter

Enoch

[deck](writeupfiles/chall15/deck)


## Solution

The text is a hint to the book Cryptonomicon by Neal Stephenson. Combined with the cards image, we realize this is a [Solitaire Cipter](https://en.wikipedia.org/wiki/Solitaire_\(cipher\))

if we convert the notation of the deck slightly we can solve it online [here](https://ermarian.net/services/encryption/solitaire.php)

key:

```
8d 3s 7d 3d 2c 5s Ad 6c 7s 6d A Kd Qh Js Jc 7h 3h 9h 9s 8s 9c As 4h 8c 3c Kh Ah 6s 6h Ts Ks Ac Td Qd Qc B Qs 4s 9d 2s 5c Jh Th 4c Tc 5d 8h 2h 2d Jd 7c Kc 5h 4d
```

which gives output:

```
THEPA SSWOR DISCR YPTON OMICO N
```

So the password is `CRYPTONOMICON`, and we put that into the egg-o-matic to get our flag


![](writeupfiles/chall15/egg.png)


