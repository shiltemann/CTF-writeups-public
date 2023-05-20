---
layout: writeup
title: 'Dec 11: Crypt-o-Math 2.0'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-XtDw-0DzO-YRgB-2b2e-UWNz

---

## Challenge

You bruteforced last years math lessons? This time you cant escape!

    c = (a * b) % p
    c=0x423EDCDCDCD928DD43EAEEBFE210E694303C695C20F42A27F10284215E90
    p=0xB1FF12FF85A3E45F722B01BF3135ED70A552251030B114B422E390471633
    b=0x88589F79D4129AB83923722E4FB6DD5E20C88FDD283AE5724F6A3697DD97

find `a` to get your flag.

## Solution

All we need to do is solve the modular equation, the `divm` function in
`gmpy2` does exactly that:

    import codecs
    import gmpy2


    def int_to_text(number):
        return codecs.decode(format(number, 'x'), 'hex').decode('utf-8')


    # c = (a * b) % p
    c = 0x559C8077EE6C7990AF727955B744425D3CC2D4D7D0E46F015C8958B34783
    p = 0x9451A6D9C114898235148F1BC7AA32901DCAE445BC3C08BA6325968F92DB
    b = 0xCDB5E946CB9913616FA257418590EBCACB76FD4840FA90DE0FA78F095873

    # solve the equation
    a = gmpy2.divm(c, b, p)

    print(int_to_text(a))
{: .language-python}

