---
layout: writeup
title: 'Dec 6: Santa’s Journey'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-eCFw-J4xX-buy3-8pzG-kd3M

---

*Make sure Santa visits every country*

## Challenge

Follow Santa Claus as he makes his journey around the world.

http://challenges.hackvent.hacking-lab.com:4200/

## Solution

When we click on the link we get a QR code image, it decodes to
`Iceland`. If we refresh we get `Angola`.
Ok, let's automate this, hope we get the flag if we try often enough:

    import requests
    from qrtools import QR


    while True:
        # download image
        url = "http://challenges.hackvent.hacking-lab.com:4200/"
        r = requests.get(url)

        with open("qrcode.png", "wb") as qrimage:
            qrimage.write(r.content)

        # read QR code
        myCode = QR(filename='qrcode.png')
        if myCode.decode():
            print(myCode.data_to_string())
        if 'HV17' in myCode.data_to_string():
            break;
{: .language-python}

This outputs:

    Saint-Martin
    Lebanon
    Anguilla
    Nepal
    New Zealand
    Marshall Islands
    Western Sahara
    Chile
    Yemen
    Antarctica
    Lithuania
    Czech Republic
    Panama
    Saudi Arabia
    Mali

    [..]

    China
    Christmas Island
    Pitcairn
    Slovakia
    Pitcairn
    Bahrain
    Cape Verde
    Angola
    Malawi
    Ecuador
    Turkmenistan
    Jamaica
    Mozambique
    American Samoa
    HV17-eCFw-J4xX-buy3-8pzG-kd3M

whoo! we got our flag

![](writeupfiles/dec6_qrcode.png)

