---
layout: writeup
title: 'P Cap'
level:
difficulty:
points:
categories: []
tags: []
flag:
---

## Challenge

What about a little P cap?

## Solution
Lotsa SMB traffic, looks like a file named R05h4L.jpg is tranferred.
Dollars to doughnuts that's an egg.

opened with wireshark, extracted the objects transferred, seems like two
versions of that image, and two webpages:

[R05h4L.jpg](writeupfiles/pcap/R05h4L.jpg) (incomplete)

R05h4L(1).jpg:

![](writeupfiles/pcap/R05h4L_1.jpg)

perdu.com ([html file](writeupfiles/pcap/perdu.com.html)) ([site][1]):

    <html>
        <head>
            <title>Vous Etes Perdu ?</title>
        </head>
        <body>
            <h1>Perdu sur l'Internet ?</h1>
            <h2>Pas de panique, on va vous aider</h2>
            <strong><pre>    * <----- vous &ecirc;tes ici</pre></strong>
        </body>
    </html>
{: .language-html}

nothinghere.pl ([html file](writeupfiles/pcap/nothinghere.pl.html))
([site][2]):

--> find login credentials to this site in pcap and log in?

## Nugget



[1]: http://perdu.com
[2]: http://nothinghere.pl
