---
layout: writeup
title: 'Egg 20: Lots of Bots '
level:
difficulty:
points:
categories: []
tags: []
flag: PRKuX3CklkoZWwfOHbpK

---

## Challenge:

*Robots have placed an egg on this web server. If you wanna find it, you
need to think and act like a bot.*

![](images/robot_small.jpg)

## Solution:

go to `hackyeaster.hacking-lab.com/robots.txt`:

    User-agent: EasterBot
    Disallow: /
    Allow: /hackyeaster/bots/bots.

    User-agent: *
    Disallow: /

we try some file extensions for the allowed path, and find that if we go
to `hackyeaster.hacking-lab.com/hackyeaster/bots/bots.html` we are
redirected to wikipedia page for C3PO.

we wget the page instead:

    <html>
      <head>
    	<title>Bots</title>
    	<script type="text/javascript">
          eval(String.fromCharCode(105, 102, 32, 40, 33, 40, 110, 97, 118, 105, 103, 97, 116, 111, 114, 46, 117, 115, 101, 114, 65, 103, 101, 110, 116, 32, 61, 61, 61, 32, 39, 69, 97, 115, 116, 101, 114, 66, 111, 116, 39, 41, 41, 32, 123, 32, 108, 111, 99, 97, 116, 105, 111, 110, 46, 114, 101, 112, 108, 97, 99, 101, 40, 39, 104, 116, 116, 112, 58, 47, 47, 101, 110, 46, 119, 105, 107, 105, 112, 101, 100, 105, 97, 46, 111, 114, 103, 47, 119, 105, 107, 105, 47, 67, 45, 51, 80, 79, 39, 41, 59, 125));
        </script>
      </head>
      <body style="background: white; border: 20px solid white;">
        <div style="widht: 100%; height: 100%; background: url('./robotbg.jpg') no-repeat center center fixed; -webkit-background-size: contain; -moz-background-size: contain; -o-background-size: contain; background-size: contain;">&#160;</div>
      </body>
    </html>z

The javascript is the redirect to wikipedia (which we could avoid by
setting our useragent to `EasterBot` in our request header)
`if (!(navigator.userAgent === 'EasterBot')) {
location.replace('http://en.wikipedia.org/wiki/C-3PO');}`

The background image is:

![](images/robotbg_small.jpg)

The text on the image reads:

    bama waboki pisal fatatu fomu
    wosebi seju sowu seju - bamas
    mufe wafub fomu mowewe

Googling some of these words lead us to a page on Asimov's laws of
robotics, and in particular ROILA, robot interaction language.
List of vocuabulary [here][1].

Translation of the message:

    you must make word of
    addition two and two - this
    be name of page

so we get page `hackyeaster.hacking-lab.com/hackyeaster/bots/four.html`:

    <html>
      <head>
    	<title>Bots</title>
    	<meta name="description" content="Robots talk in ROILA language: eman egap eht esrever tsum">
        <meta name="keywords" content="secret, page, robots, fun, hacky easter, blrt, five, beep">
    	<script type="text/javascript">
          eval(String.fromCharCode(105, 102, 32, 40, 33, 40, 110, 97, 118, 105, 103, 97, 116, 111, 114, 46, 117, 115, 101, 114, 65, 103, 101, 110, 116, 32, 61, 61, 61, 32, 39, 69, 97, 115, 116, 101, 114, 66, 111, 116, 39, 41, 41, 32, 123, 32, 108, 111, 99, 97, 116, 105, 111, 110, 46, 114, 101, 112, 108, 97, 99, 101, 40, 39, 104, 116, 116, 112, 58, 47, 47, 101, 110, 46, 119, 105, 107, 105, 112, 101, 100, 105, 97, 46, 111, 114, 103, 47, 119, 105, 107, 105, 47, 67, 45, 51, 80, 79, 39, 41, 59, 125));
        </script>
      </head>
      <body style="background: white; border: 20px solid white;">
        <div style="widht: 100%; height: 100%; background: url('./robotbg2.jpg') no-repeat center center fixed; -webkit-background-size: contain; -moz-background-size: contain; -o-background-size: contain; background-size: contain;">&#160;</div>
      </body>
    </html>

and the new background image:

![](images/robotbg2_small.png)

The line `eman egap eht esrever tsum` in the HTML is reversed for: `must
reverse the page name`, so we try the page `ruof.html`:

    <html>
      <head>
    	<title>Bots</title>
    	<script type="text/javascript">
          eval(String.fromCharCode(105, 102, 32, 40, 33, 40, 110, 97, 118, 105, 103, 97, 116, 111, 114, 46, 117, 115, 101, 114, 65, 103, 101, 110, 116, 32, 61, 61, 61, 32, 39, 69, 97, 115, 116, 101, 114, 66, 111, 116, 39, 41, 41, 32, 123, 32, 108, 111, 99, 97, 116, 105, 111, 110, 46, 114, 101, 112, 108, 97, 99, 101, 40, 39, 104, 116, 116, 112, 58, 47, 47, 101, 110, 46, 119, 105, 107, 105, 112, 101, 100, 105, 97, 46, 111, 114, 103, 47, 119, 105, 107, 105, 47, 67, 45, 51, 80, 79, 39, 41, 59, 125));
        </script>
      </head>
      <body style="background: white; border: 20px solid white;">
        <div style="position: absolute; left:50%; top: 50%; margin-left: -187px; margin-top: -187px; width: 375px; height: 375px; background: url('./egg_20_j5fir8U6g8.png'); background-size: 375px 375px; background-repeat: no-repeat;">&#160;</div>
      </body>
    </html>

This time the background image is our egg:

![](images/egg_20_qrcode_small.png)



[1]: http://roila.org/language-guide/vocabulary/
