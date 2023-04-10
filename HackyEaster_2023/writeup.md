# HackyEaster 2022

The annual Easter CTF from Hacking-lab

![](writeupfiles/teaser_banner.jpg)


## Overview

Title                                            | Points     | Egg
-------------------------------------------------| ---------- | ------------------------------
Level 1: Welcome                                 |            |
[Sanity Check](#sanity-check)                    | Level 1    | `he2023{just_A_sanity_chEck}`
Level 2: Noob Zone                               |            |
[Word Cloud](#word-cloud)                        | Level 2    | `he2023{this_is_the_flag!}`
[Rotation](#roation)                             | Level 2    | `he2023{0n3_c4n_r34d_r0t0r_b4ckw4rds}`
[Birds on a Wire](#birds-on-a-wire)              | Level 2    | `he2023{birdwatchingisfun}`
[Bins](#bins)                                    | Level 2    | `he2023{}`
Level 3: It's so Easy                            |            |
[Chemical Code](#chemical-code)                  | Level 3    | `he2023{flagenergyatomcosmos}`
[Serving Things](#serving-things)                | Level 3    | `he2023{}`
[Cut Off](#cut-off)                              | Level 3    | `he2023{}`
[Global Egg Delivery](#global-egg-delivery)      | Level 3    | `he2023{}`
Level 4                                          |            |

## Level 1: Welcome

### Sanity Check

**Challenge**

This is your first flag!

Right here --> `he2023{             }`

🚩 Flags are in format `he2023{...}`, unless noted otherwise. Always check additional information given (uppercase, lowercase, spaces, etc.).

**Solution**

inspecting the empty looking space in the challenge text, we see

```html
<span style="color: black; background-color: black; opacity: 0;">just_A_sanity_chEck</span>
```

**Egg**

```
he2023{just_A_sanity_chEck}
```

## Level 2: Noob Zone


### Word Cloud

**Challenge**

I like Word Clouds, what about you?

Download the image below (he2023-wordcloud.jpg), sharpen your eyes, and find the right flag.


![](writeupfiles/he2023-wordcloud.jpg)

**Solution**

The wordcloud contains a lot of false flags, but also the correct one, so just read all the words until you find it!

![](writeupfiles/he2023-wordcloud-solution.jpg)

**Egg**

```
he2023{this_is_the_flag!}
```

### Rotation

**Challenge**

My new rotor messed up the flag!

```
96a_abL_?b04c?0Cbc50C_E_C03c4<HcC5DN
```

I tried to decode it, but it didn't work. The rotor must have been too fast!

**Solution**

We suspect a rotation cipher because of the cipher, and assuming the given string starts with `he2023`, this indeed checks out (`h` and `e` are 3 apart in the ASCII table, so are `9` and `6`). It would appear to be a rotation of 47, though sometimes it is +47, sometimes -47, so we write a short script to find the direction of rotation

```python
import string

ct="96a_abL_?b04c?0Cbc50C_E_C03c4<HcC5DN"
flag = ""

for i in range(0,len(ct)):
  pt = chr ( ord(ct[i])+47 )
  if pt not in string.printable:
     pt = chr ( ord(ct[i])-47 )

  flag += pt

print(flag)
```

which gives us the flag!

**Egg**

```
he2023{0n3_c4n_r34d_r0t0r_b4ckw4rds}
```

### Birds on a Wire

**Challenge**

Just some birds sitting on a wire.

Download the image and find the flag!

![](writeupfiles/birdsonawire.jpg)

**Solution**

Some Googling reveals that this is the ["Birds on a Wire" cipher](https://www.dcode.fr/birds-on-a-wire-cipher)

It's a simple substitution cipher so we just map the birds to their corresponding letters to find the flag!

**Egg**

```
he2023{birdwatchingisfun}
```

### Bins

**Challenge**

The rabbits left a mess in their cage.

```
  //    //                    //
 ('>   ('>    LX2gkn81        ('>
 /rr   /rr       carrots      /rr
*\))_ *\))_                  *\))_
```

If only I knew which bin to put the rubbish in.


**Solution**

**Egg**

```
he2023{}
```


## Level 3: It's so Easy

### Chemical Code

**Challenge**

Our crazy chemistry professor wrote a secret code on the blackboard:

```
9 57 32 10 111 39 85 8 115 8 16 42 16
```

He also mumbled something like "essential and elementary knowledge".


**Solution**

This sounds like we have to convert atomic numbers to their corresponding sybols to get the flag

We find a python package to help us, [PyAstronomy](https://pyastronomy.readthedocs.io/en/latest/pyaslDoc/aslDoc/atomicNo.html, )and use it to decode the flag

```python3
from PyAstronomy import pyasl

an = pyasl.AtomicNo()
ct =[9,57,32,10,111,39,85,8,115,8,16,42,16]

flag = "".join(an.getElSymbol(ct[i]) for i in range(0,len(ct)))

print(flag)  # outputs FLaGeNeRgYAtOMcOSMoS

```

**Egg**

```
he2023{flagenergyatomcosmos}
```

### Serving Things

**Challenge**

Get the 🚩 at /flag.

[http://ch.hackyeaster.com:2316]([http://ch.hackyeaster.com:2316)

Note: The service is restarted every hour at x:00.

**Solution**

We get a simle website

```html
<!DOCTYPE html>

<html>
<head>
<title>Serving Things</title>
    <link rel="stylesheet"
	    href="/static/app.css">
	<script	src="/static/jquery-3.6.3.min.js" language="javascript"></script>
    <script	src="/static/app.js" language="javascript"></script>
</head>

<body>
	<div id="menu">
        Get: <a id="quotes" href="#">Quotes</a> | <a id="colors" href="#">Colors</a> | <a id="stars" href="#">Stars</a> |
		<a id="cheese" href="#">Cheese</a> | <a id="wine" href="#">Wine</a> | <a id="meals" href="#">Swiss Meals</a> |
		<a id="trek" href="#">The Trek</a> | <a id="flag" href="#">Flag</a>
	</div>
	<div id="text">
	</div>
	<div id="footer">
		<div id="created">
			Created by inik / 2023
		</div>
	</div>
</body>
</html>
```

with `app.js`:

```javascript
function get(url) {
    u = encodeURI(window.location.protocol + "//" + window.location.host + "/get?url=" + url);
    $.get(u, function (data) {
        var color = Math.floor(Math.random() * 16777215).toString(16);
        $("#text").fadeOut(400);
        setTimeout(function () {
            $("#text").html(data);
            $("#text").css("color", "#" + color);
            $("#text").fadeIn(400);
        }, 400);
    });
}

$(document).ready(function () {
    $("#quotes").click(function () {
        get("http://quotes:1337/quote");
    })

    $("#colors").click(function () {
        get("http://colors:1337/color");
    })

    $("#stars").click(function () {
        get("http://stars:1337/star");
    })

    $("#cheese").click(function () {
        get("http://cheese:1337/cheese");
    })

    $("#flag").click(function () {
        get("http://flags:1337/flag");
    })

    $("#wine").click(function () {
        get("http://wine:1337/wine");
    })

    $("#meals").click(function () {
        get("http://meals:1337/meal");
    })

    $("#trek").click(function () {
        get("http://trek:1337/trek");
    })

    $('#quotes').trigger('click');
});
```

So there are a couple of words you can click on, which get

```
http://ch.hackyeaster.com:2316/get?url=http://flags:1337/flag
```

returns

```
Thank you hacker! But our flag is in another castle! ~ Bugs Bunny
```

hmm..

**Egg**

```
he2023{}
```


### Cut Off

**Challenge**

I had a secret Easter egg on my screenshot, but I cropped it, hehe!

Kudos to former Hacky Easter winner Retr0id - he's one of the researches who found the vulnerability in question!

![](writeupfiles/screenshot.png)

**Solution**

This sounds like the recen [aCROPalypse vulnerability](https://en.wikipedia.org/wiki/ACropalypse).

We use [acropalypse.app](https://acropalypse.app/) to recover the cropped part of the image.

We try some phone models until we have success with the "Google Pixel 6" setting

![](writeupfiles/screenshot-recovered.png)


**Egg**

```
he2023{}
```


### Global Egg Delivery

**Challenge**

**Solution**

**Egg**

```
he2023{}
```



## Level 4

### Title

**Challenge**

**Solution**

**Egg**

```
he2023{}
```
