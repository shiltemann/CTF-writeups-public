---
layout: writeup
title: 'Old RSA'
level:
difficulty:
points: 70
categories: [cryptography]
tags: []
flag: ABCTF{th1s_was_h4rd_in_1980}
---
## Challenge

I'm sure you can retrieve the flag from this file.

file:

    I recovered an RSA encrypted message from the 1980's. can you decrypt it?
    
    c = 29846947519214575162497413725060412546119233216851184246267357770082463030225
    n = 70736025239265239976315088690174594021646654881626421461009089480870633400973
    e = 3

*Hint: Some good math skills may help.*

**Solution**   
Followed a  
[previous][1]  
descrpition of how to solve this type of problem.

    N=70736025239265239976315088690174594021646654881626421461009089480870633400973
    C=29846947519214575162497413725060412546119233216851184246267357770082463030225
    e=3

[factordb.com][2] tells us the p's and q's.

    p=238324208831434331628131715304428889871
    q=296805874594538235115008173244022912163
^

    from Crypto.PublicKey import RSA
    import gmpy2
    
    def int2Text(number, size):
        text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
        return text.lstrip("\x00")
    
    N = 70736025239265239976315088690174594021646654881626421461009089480870633400973
    C = 29846947519214575162497413725060412546119233216851184246267357770082463030225
    p = 238324208831434331628131715304428889871L
    q = 296805874594538235115008173244022912163L
    r=(p-1)*(q-1)
    e = 3L
    d = long(gmpy2.divm(1, e, r))
    
    rsa = RSA.construct((N,e,d,p,q))
    pt = rsa.decrypt(C)
    
    print pt  # returns 6872557977505747778161182217242712228364873860070580111494526546045
    print int2Text(pt,100) #returns ABCTF{th1s_was_h4rd_in_1980}
{: .language-python}



[1]: https://github.com/shiltemann/CTF-writeups-public/blob/master/Hackvent_2015/writeup.md
[2]: http://factordb.com/index.php?query=70736025239265239976315088690174594021646654881626421461009089480870633400973
