---
layout: writeup
title: 'Egg 18: Sharks on a Wire'
level:
difficulty:
points:
categories: []
tags: []
flag: 83OHadUPAeWRfd6YBv6t

---

## Challenge:

*In this challenge, you need to get access to a web site.*

*Inspect the following capture files, in order to get the credentials
needed.*

[website][1]
[pcap file](images/sharks.pcapng)

## Solution:

The website requires HTTP authentication

![](images/egg_18_login_screenshot.png)

in wireshark we can find the auth credentials

![](images/egg_18_wireshark_screenshot.png)

    sharkman:sharks_have_j4ws

we enter these and get to a website where we have to fill in another set
of credentials

![](images/egg_18_website_screenshot_small.png)

but we can find these in the pcap file as well:

    user=supershark&pass=hashed%21%21%21&hash=b3f3ca462d3fa58b74d6982af14d8841b074994a

HTML source for the page:

    <!DOCTYPE HTML>
    <html>
      <head>
        <title>Sharks on Wire</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <link rel="icon" type="image/ico" href="../images/favicon.ico">
        <link rel="stylesheet" type="text/css" href="sharks.css">
        <script src="../js/jquery.min.js"></script>
        <script type="text/javascript" src="../js/crypto-js/sha1.js"></script>
        <script type="text/javascript" src="../js/crypto-js/core-min.js"></script>
        <script type="text/javascript" src="../js/crypto-js/enc-base64-min.js"></script>
      <body>
        <div class="title">Sharks on Wire</div>
        <div class="panel">
          <form action="auth" method="post" onsubmit="$('#hash').val(CryptoJS.SHA1($('#pass').val()));$('#pass').val('hashed!!!');">
            <input class="input" type="text" name="user" placeholder="User"/>
            <input class="input" type="password" name="pass" id="pass" placeholder="Password"/>
            <input class="input" type="hidden" name="hash" id="hash" />
            <input class="button" type="submit" value="Dive in"/>
          </form>
        </div>
      </body>
    </html>

we do not know the password, but we do know the hash and see that the
hash is calculated client-side, so we do not need to know the password.

In the HTML we change the hash input type from `hidden` to `text` and
enter the value `b3f3ca462d3fa58b74d6982af14d8841b074994a`,

![](images/egg_18_website_screenshot2_small.png)

we also change the `onsubmit` action to
`$('#hash').val($('#hash').val());$('#pass').val('hashed!!!');` so that
we do not calculate the hash from the password but use it directly.

This leads us to the egg:

![](images/egg_18_qrcode_small.png)



[1]: http://hackyeaster.hacking-lab.com/hackyeaster/sharks/sharks.html
