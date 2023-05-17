---
layout: writeup
title: 'Ligths out'
level:
difficulty:
points: 75
categories: [web]
tags: []
flag: IceCTF{styles_turned_the_lights}
---
## Challenge

Help! We're scared of the dark!

https://static.icec.tf/lights\_out

## Solution

We see a black page

![](writeupfiles/lights_out_screenshot_before.png)

with source:

    <!doctype html>
    <html>
        <head>
            <meta charset="utf-8" />
            <title>Lights out!</title>
            <link rel="stylesheet" href="main.css" />
        </head>
        <body>
            <div class="alert alert-danger">Who turned out the lights?!?!</div>
            <summary>
            <div class="clearfix">
                <i data-hide="true"></i>
                <strong data-show="true">
                <small></small>
                </strong>
                <small></small>
            </div>
            </summary>
        </body>
    </html>
{: .language-html}

Some fiddling with the css yields the flag

![](writeupfiles/lights_out_screenshot.png)

## Flag

    IceCTF{styles_turned_the_lights}

