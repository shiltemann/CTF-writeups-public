---
layout: writeup
title: 'Dec 5: PDF'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-bkPb-tPEM-Fh3n-wvOi-5ZgD

---

## Challenge

*... for fun and profit*

It's in the Chuchichäschtli, not a Chäschüechli !

[Chuchichaeschtli.pdf](writeupfiles/Chuchichaeschtli.pdf)

## Solution

The pdf contains an image of a bauble with a QR code on it. It says

    Oooops !

We see if there are any hidden files in the pdf (e.g. underneath the
bauble image). We use http://www.extractpdf.com and find the following
files:

![](writeupfiles/Chuchichaeschtli/Chuchichaeschtli-000.jpg)
![](writeupfiles/Chuchichaeschtli/Chuchichaeschtli-001.png)
![](writeupfiles/Chuchichaeschtli/Chuchichaeschtli-002.png)

We see that the first image was the one visible in the pdf. But it was
covering the real QR code in the bauble of the third image. We scan this
QR code to get the nugget.



