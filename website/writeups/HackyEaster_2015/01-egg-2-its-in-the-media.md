---
layout: writeup
title: "Egg 2: It's in the Media"
level:
difficulty: easy
points:
categories: []
tags: []
flag: Gc1lEc7j4y89sTiLWK6m

---

## Challenge:

*New York Times, March 12 2015*
*An Easter Egg of the famous Hacky Easter white-hat hacking competition,
was leaked last Tuesday by the*
*famous hacker group "Bunnonymous". Experts confirmed its authenticity,
but could not crack the code hidden it in yet.*

Can you do better?

## Solution:

The QR code is made in HTML ([source code](images/challenge02.html)),
but is invalid

![](images/egg_02_qrcode_bad.png)

(notice the block with NO overlaid on the QRcode)

In the source we see:

    .page { background-color: white !important;}
    .h { display:none;}
    .i3 { height: 10px; width: 10px; background: #fff;}
    .o2 { height: 10px; width: 10px; background: #000;}
    .l1 { height: 10px; width: 10px; background: #000;}
    .x5 { height: 10px; width: 10px; background: #fff;}
    @media print {	body {-webkit-print-color-adjust: exact;}
    		.h { display:block;}
    		.l1 { height: 10px; width: 10px; background: #000;}
    		.x5 { height: 10px; width: 10px; background: #000;}
    	      }"

If we change style of element class `.x5` (first occurrence) to white,
we can read the egg:

![](images/egg_02_qrcode.png)

