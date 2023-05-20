---
layout: writeup
title: 'Cryptography 350: Super Safe RSA'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{us3_l@rg3r_pr1m3$_1850}
---
## Challenge

Dr. Xernon made the mistake of rolling his own crypto.. Can you find the
bug and decrypt the message?

Connect with `nc 2018shell1.picoctf.com 24039`.

## Solution

we connect and get a set of RSA variables (always c,N,e but different
every time)

    c = 4610219302492866962570875523337872829970889535476946261497385462185929486416175
    N = 28715218932555751976417148777726594094995296548335428875818442252725314913842461
    e = 65537

We don't find hits in [factordb](factordb.com), but this N is small
enough to be factorized, but and indeed  
[alpertron.com][1] does the factorization for us in about 12 minutes.

    p = 4608502214130535431876746639351117393190
    q = 2734396875895981659754211798351705092981

so we get the plaintext:

    import gmpy2
    
    N = 28715218932555751976417148777726594094995296548335428875818442252725314913842461
    c = 4610219302492866962570875523337872829970889535476946261497385462185929486416175
    e = 65537
    p = 165282687785851090832160512809009789897
    q = 173733978538397765928176741806245283295413
    
    r = (p-1)*(q-1)
    
    d = gmpy2.divm(1,e,r)
    m = gmpy2.powmod(c,d,N)
    
    print(hex(m)[2:].decode('hex'))
{: .language-python}



[1]: https://www.alpertron.com.ar/ECM.HTM
