# IceCTF 2016

2-Week-long Icelandic CTF in September 2018

Team: Galaxians

![](writeupfiles/screenshot.png)

## Overview
```
Title                          Category       Points Flag
------------------------------ -------------- ------ -----------------------------
Toke Relaunch                  Web            50
Lights out                     Web            75     IceCTF{styles_turned_the_lights}

Modern Picasso                 Forensics      150
Hard Shells                    Forensics      200
Lost in the Forest             Forensics      300

Drumbone                       Steganography  150
Rabbit Hole                    Steganography  250

garfield                       Cryptography   100
Think outside the key!         Cryptography   200
Ancient Foreign Communications Cryptography   300

Poke-A-Mango                   Reversing      250
Passworded!                    Reversing      400

Hello World!                   Misc           10      IceCTF{this_is_a_flag}
ilovebees                      Misc           200
Secret Recipe                  Misc           300
```

## Challenge 42: Title
**Challenge**
**Solution**
**Flag**
```
flag
```

## Misc 10: Hello World!

**Challenge**

Welcome to the competition! To get you started we decided to give you your first flag. The flags all start with the "IceCTF" and have some secret message contained with in curly braces "{" and "}".

Within this platform, the challenges will be shown inside a frame to the right. For example purposes the download interface is shown on the right now. For static challenges you will need to click the large button in order to receive your challenge. For non static challenges, the lab itself will be shown on the right.

To submit the flag you can click the blue flag button in the bottom right hand corner.

Your flag is `IceCTF{this_is_a_flag}`

**Solution**

`CTRL+C, CTRL+V`

**Flag**

```
IceCTF{this_is_a_flag}
```

## Web 75: Ligths out

**Challenge**

Help! We're scared of the dark!

https://static.icec.tf/lights_out

**Solution**

We see a black page

![](writeupfiles/lights_out_screenshot_before.png)

with source:

```html
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

```

Some fiddling with the css yields the flag

![](writeupfiles/lights_out_screenshot.png)

**Flag**

```
IceCTF{styles_turned_the_lights}
```
