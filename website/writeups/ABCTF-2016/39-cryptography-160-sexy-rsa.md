---
layout: writeup
title: 'Sexy RSA'
level:
difficulty:
points: 160
categories: [crypto]
tags: []
flag: ABCTF{i_h4ve_an_RSA_fetish_;)}
---
## Challenge

Check this out!

    I recovered some RSA parameters. Can you decrypt the message?

    c = 293430917376708381243824815247228063605104303548720758108780880727974339086036691092136736806182713047603694090694712685069524383098129303183298249981051498714383399595430658107400768559066065231114145553134453396428041946588586604081230659780431638898871362957635105901091871385165354213544323931410599944377781013715195511539451275610913318909140275602013631077670399937733517949344579963174235423101450272762052806595645694091546721802246723616268373048438591
    n = 1209143407476550975641959824312993703149920344437422193042293131572745298662696284279928622412441255652391493241414170537319784298367821654726781089600780498369402167443363862621886943970468819656731959468058528787895569936536904387979815183897568006750131879851263753496120098205966442010445601534305483783759226510120860633770814540166419495817666312474484061885435295870436055727722073738662516644186716532891328742452198364825809508602208516407566578212780807
    e = 65537

## Solution

N is way too large to factor, and there are no hits in factordb. There
must be more to it ..after some time we
figured the title might be a hint, and learned there is such a thing as
[sexy primes][1]

so we try to find two primes that differ by six which when multiplied
equal `N`. This is basically the following quadratic equation:

    x*(x+6) = N

which we can easily solve:

    from sympy.solvers import solve
    from sympy import Symbol
    x = Symbol('x')
    solve (x**2+6*x-N,x)
{: .language-python}

this outputs:

     [-1099610570827941329700237866432657027914359798062896153406865588143725813368448278118977438921370935678732434831141304899886705498243884638860011461262640420256594271701812607875254999146529955445651530660964259381322198377196122399, 1099610570827941329700237866432657027914359798062896153406865588143725813368448278118977438921370935678732434831141304899886705498243884638860011461262640420256594271701812607875254999146529955445651530660964259381322198377196122393]

so now we know our pair of primes

    p = 1099610570827941329700237866432657027914359798062896153406865588143725813368448278118977438921370935678732434831141304899886705498243884638860011461262640420256594271701812607875254999146529955445651530660964259381322198377196122393
    q=p+6

and we can use this to decrypt the message

    from Crypto.PublicKey import RSA
    import gmpy2

    def int2Text(number, size):
        text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
        return text.lstrip("\x00")

    C = 293430917376708381243824815247228063605104303548720758108780880727974339086036691092136736806182713047603694090694712685069524383098129303183298249981051498714383399595430658107400768559066065231114145553134453396428041946588586604081230659780431638898871362957635105901091871385165354213544323931410599944377781013715195511539451275610913318909140275602013631077670399937733517949344579963174235423101450272762052806595645694091546721802246723616268373048438591
    p = 1099610570827941329700237866432657027914359798062896153406865588143725813368448278118977438921370935678732434831141304899886705498243884638860011461262640420256594271701812607875254999146529955445651530660964259381322198377196122393L
    q = p+6
    N = p*q
    r=(p-1)*(q-1)
    e = 65537L
    d = long(gmpy2.divm(1, e, r))

    rsa = RSA.construct((N,e,d,p,q))
    pt = rsa.decrypt(C)

    print pt
    print int2Text(pt,100)
{: .language-python}

which outputs

    450399959613816415828813303011378086519519456226341693974137269925915005
    ABCTF{i_h4ve_an_RSA_fetish_;)}



[1]: https://en.wikipedia.org/wiki/Sexy_prime