---
layout: writeup
title: 'An egg or not …'
level:
difficulty: Medium
points:
categories: []
tags: []
flag:  UALYyPlhy2aYfYpzcJHA

---

## Challenge
... an egg, that's the question!

Are you able to answer this question and find the (real) egg?

![](writeupfiles/10.png)

(original svg verson [here](writeupfies/10.svg))

## Solution
The QR code is made of individual dots. Some are doubled up, I think we
remove the doubles.

I stripped out the `<use ...>` statements and then transformed into a
TSV:

    data = open('tmp', 'r').read()
    q = [x.split('\t') for x in data.split('\n')]
    haveSeen = {}

    for i in q:
            k = "%s,%s" % (i[0], i[1])
            if k not in haveSeen:
                    print """<use x="%s" y="%s" xlink:href="#%s"/>""" % (*i)
            haveSeen[k] = True
{: .language-python}

![](writeupfiles/10.b.png)

(original svg verson [here](writeupfies/10.b.svg))

