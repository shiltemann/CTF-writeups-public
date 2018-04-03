# HackyEaster 2018

## Overview


```
Title                                    Difficulty  Flag
---------------------------------------- ---------- -----------------------------
Teaser
Challenge 01: Prison Break               Easy       he18-gx8L-AJUw-DSMH-6aUI
Challenge 02: Babylon                    Easy
Challenge 03: Pony Coder                 Easy
Challenge 04: Memeory                    Easy       he18-cGoS-a2tz-BD2w-zXH8
Challenge 05: sloppy & Paste             Easy       he18-2yTc-bJ1f-raIQ-gKc6
Challenge 06: Cooking for Hackers        Easy       he18-MdVx-nSgb-wzYT-TtoE 
Challenge 07: Jigsaw                     Easy
Challenge 08: Disco Egg                  Easy
Challenge 09: Dial Trial                 Easy
Challenge 10: Level Two                  Medium
Challenge 11: De Egg you must            Medium
Challenge 12: Patience                   Medium
Challenge 13: Sagittarius...             Medium
Challenge 14: Same same...               Medium     he18-D76U-PvxZ-7Icy-mkF1
Challenge 15: Manile greetings           Medium
Challenge 16: git cloak --hard           Medium     he18-k4oU-OEar-n9Sr-ULi0
Challenge 17: Space Invaders             Medium
Challenge 18: Egg Factory                Medium
Challenge 19: Virtual Hen                Hard
Challenge 20: Artist: No Name Yet        Hard
Challenge 21: Hot Dog                    Hard
Challenge 22: Block Jane                 Hard
Challenge 23: Rapbid Learning            Hard
Challenge 24: ELF                        Hard
Hidden Egg #1
Hidden Egg #2
Hidden Egg #3
```

## General

Some challenges are mobile challenges that need to be solved with the Hacky Easter app

We get the [apk](writeupfiles/HackyEaster_9_5.0.1.apk) for the app (e.g. with the GetAPK app)
and decode it using `apktool`

```bash
$ sudo apt-get install apktool
$ apktool decode HackyEaster_9_5.0.1.ap
```


## Teaser

**Challenge**

**Solution**

**Egg**

## Hidden Egg #1:

**Challenge**

**Solution**

**Egg**

## Hidden Egg #2:

**Challenge**

**Solution**

**Egg**

## Hidden Egg #3:

**Challenge**

**Solution**

**Egg**


## Challenge 01: Prison Break

**Challenge**

Your fellow inmate secretly passed you an old cell phone and a weird origami. The only thing on the phone are two stored numbers.

```
555-7747663 Link
555-7475464 Sara
```

Find the password and enter it in the Egg-o-Matic below. lowercase only, no spaces!

![](writeupfiles/chall01/origami.png)

**Solution**

Combine the telephone numbers with the dots on the origami and a T9 pad:

![](writeupfiles/chall01/t9.png)

```
7747663 (Link)
1334322 (number of dots)
prisone

7475464 (Sara)
3342321 (number of dots)
risking

```

this reads `prisonerisking`, enter this into egg-o-matic to get our egg


**Egg**


![](writeupfiles/chall01/egg.png)

```
he18-gx8L-AJUw-DSMH-6aUI
```

## Challenge 02:

**Challenge**

**Solution**

**Egg**

## Challenge 03:

**Challenge**

**Solution**

**Egg**

## Challenge 04: Memeory

**Challenge**

Fancy a round of memeory?

![](writeupfiles/chall04/cover.jpg)

Click here to play.

**Solution**

We inspect the html, and can see the images on each of the cards:

```html
<div class="moduleLegespiel">
<figure id="legespiel_card_63" class="">
	<a href="#card_63">
      <img class="boxFront" src="./lib/32.jpg">
	  <img class="boxWhite" src="./lib/shadow_card.png">
	  <img class="boxBack" src="./lib/back.jpg">
	 </a>
	 <img class="boxStretch" src="./lib/shim.gif">
</figure><figure id="legespiel_card_58" class="">
	<a href="#card_58">
	  <img class="boxFront" src="./lib/30.jpg">
	  <img class="boxWhite" src="./lib/shadow_card.png">
	  <img class="boxBack" src="./lib/back.jpg">
	</a>
	<img class="boxStretch" src="./lib/shim.gif">
</figure><figure id="legespiel_card_84">
	<a href="#card_84">
	  <img class="boxFront" src="./lib/43.jpg">
	  <img class="boxWhite" src="./lib/shadow_card.png">
	  <img class="boxBack" src="./lib/back.jpg">
   </a>
	  <img class="boxStretch" src="./lib/shim.gif">
</figure><figure id="legespiel_card_73">
	<a href="#card_73">
	  <img class="boxFront" src="./lib/37.jpg">
	  <img class="boxWhite" src="./lib/shadow_card.png">
	  <img class="boxBack" src="./lib/back.jpg">
   </a>
   <img class="boxStretch" src="./lib/shim.gif">
</figure><figure id="legespiel_card_19">

[..]
```

so we just find the pairs and play the game to get our egg:

![](writeupfiles/chall04/completed.png)

**Egg**

![](writeupfiles/chall04/egg.png)

```
he18-cGoS-a2tz-BD2w-zXH8
```


## Challenge 05: Sloppy & Paste

**Challenge**

This was a mobille challenge.

![](writeupfiles/chall05/screenshot.jpg)

**Solution**

When we try to copy the text shown, it copies a different text



so we get the apk of the mobile app and decode it

```
apktool decode HackyEaster_9_5.0.1.apk
```

and find the string we are looking for in `assets/www/challenge05.html`


**Egg**

![](writeupfiles/chall05/egg.png)

```
he18-2yTc-bJ1f-raIQ-gKc6
```

## Challenge 06:

**Challenge**

```
You've found this recipe online:

    1 pinch: c2FsdA==

    2 tablesspoons: b2ls

    1 teaspoon: dDd3Mmc=

    50g: bnRkby4=

    2 medium, chopped: b25pb24=

But you need one more secret ingredient! Find it!
```

**Solution**

These b64 decode to

```
1 pinch: salt
2 tablesspoons: oil
1 teaspoon: t7w2g
50g: ntdo.
2 medium, chopped: onion

But you need one more secret ingredient! Find it!
```

The period at the end of the fourth part is the hint, this is a url!

```
saltoilt7w2gntdo.onion
```

open it with a tor browser to get the egg

**Egg**

![](writeupfiles/chall06/egg.png)

```
he18-MdVx-nSgb-wzYT-TtoE
```

## Challenge 07:

**Challenge**

**Solution**

**Egg**

## Challenge 08:

**Challenge**

**Solution**

**Egg**

## Challenge 09:

**Challenge**

Another mobile challenge
![](writeupfiles/chall09/cover.jpg)
![](writeupfiles/chall09/screenshot.jpg)


**Solution**

we find the [mp3 file](writeupfiles/chall09/dial.mp3) played by the app when hitting the button.

We convert it to [wav file](writeupfiles/chall09/dial.wav) and decode the DTMF tones using http://dialabc.com/sound/detect/index.html


```
472612252336262636253412
```

**Egg**

## Challenge 10:

**Challenge**

**Solution**

**Egg**

## Challenge 11: De egg you must

**Challenge**


Who was first, the cat or the egg?

![](writeupfiles/chall11/cover.jpg)

[basket.zip](writeupfiles/chall11/basket.zip)

**Solution**

The zip file is password protected but easily cracked with fcrackzip and this [wordllist](http://mirrors.kernel.org/openwall/wordlists/passwords/)

```bash
$ fcrackzip -v --use-unzip -D -p dictionaries/password basket.zip
found file 'egg1', (size cp/uc 1389653/1433600, flags 9, chk 4f21)
found file 'egg2', (size cp/uc 1426168/1433600, flags 9, chk 4f21)
found file 'egg3', (size cp/uc 1425557/1433600, flags 9, chk 4f21)
found file 'egg4', (size cp/uc 1425787/1433600, flags 9, chk 4f21)
found file 'egg5', (size cp/uc 1423266/1433600, flags 9, chk 4f21)
found file 'egg6', (size cp/uc 362705/384584, flags 9, chk 4f21)


PASSWORD FOUND!!!!: pw == thumper

```

**Egg**

## Challenge 12:

**Challenge**

**Solution**

**Egg**

## Challenge 13:

**Challenge**

13 - Sagittarius...

... is playing with his pila again.

Can you find the Easter egg QR code he has hidden from you?

**Solution**

I loaded pila.kmz into a KMZ viewer and saw this:

![](writeupfiles/chal13/Auswahl_398.png)

So obviously the wrong projection.


**Egg**

## Challenge 14:

**Challenge**

Same same...
...but different!
Upload the right files and make the server return an Easter egg!

The PHP code seems to require that we upload two files which are QR codes with
the word 'Hackvent' in one and 'Hacky Easter' in the other, and that those
files should have identical sha1sums.

I just assumed it would accept PDFs without looking since the sha1 collision
for pdfs was pretty recently big news. Found
https://github.com/nneonneo/sha1collider as one of the top results in a search.

**Solution**

```
$ qrencode 'Hackvent' -o a.png
$ qrencode 'Hacky Easter' -o b.png
$ convert a.png a.pdf
$ convert b.png b.pdf
$ python3 sha1collider/collide.py a.pdf b.pdf --progressive
```

and got out two PDFs with identical hashes in under a second! Neat.

```
bdece875ca36c6505b0728cbeca7495db1a30246  out-a.pdf
bdece875ca36c6505b0728cbeca7495db1a30246  out-b.pdf
```

**Egg**

![](./writeupfiles/chall14/egg.png)

## Challenge 15:

**Challenge**

**Solution**

**Egg**

## Challenge 16: git cloak --hard

**Challenge**

This one requires your best Git-Fu! Find the hidden egg in the repository.

[repo.zip](writeupfiles/chall16/repo.zip)

**Solution**

The zip files contains a git repo with a number of images in it:

<table>
<img src="writeupfiles/chall16/images/01.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/02.png" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/03.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/05.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/06.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/07.png" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/08.png" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/09.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/10.jpg" alt="image" width="100px"/>
<img src="writeupfiles/chall16/images/11.png" alt="image" width="100px"/>

The egg with the QR code translates to `7dUDQDhMQkLYsQTMJq62`, but this is not a valid egg of course.

```bash
$ git log
commit b9e860f47fe6990cbda4ac5bb3d2829d2191f1eb (HEAD -> master)
Author: PS <ps@hacking-lab.com>
Date:   Tue Jan 23 05:43:16 2018 -0500

    even more funny images added

commit 3839c14d2863fd850794661677352305ea798eb6
Author: PS <ps@hacking-lab.com>
Date:   Tue Jan 23 05:43:15 2018 -0500

    more funny images added

commit 228b603ed45ddaf1b1d3fe502e168fa2508ee5ed
Author: PS <ps@hacking-lab.com>
Date:   Tue Jan 23 05:43:15 2018 -0500

    created the funny git meme repo
```

We rewind the commits to see if there is anything interesting

```bash
$ git reset HEAD~2
Unstaged changes after reset:
M       02.png
D       04.png
```

we see that image 2 was modified and another image deleted

![](writeupfiles/chall16/images/02-2.png)
![](writeupfiles/chall16/images/04.png)

but alas, this different egg (QRcode reads `qdUX0sgDVjWxiFNifHKE`) is still not what we are looking for..


```bash
$ git log --all --graph --oneline
* b9e860f (HEAD -> master) even more funny images added
| * 9a29769 (branch) branch created
|/
* 3839c14 more funny images added
* 228b603 created the funny git meme repo
```

So we see that a different branch was created at some point, but we don't find the egg here either.

```bash
$ cat .git/config
[core]
        repositoryformatversion = 0
        filemode = false
        bare = false
        logallrefupdates = true
        symlinks = false
        ignorecase = true
[user]
        name = PS
        email = ps@hacking-lab.com
```


```bash
$ cat .git/logs/HEAD                                                               [29-03-18 13:00:39]
0000000000000000000000000000000000000000 228b603ed45ddaf1b1d3fe502e168fa2508ee5ed PS <ps@hacking-lab.com> 1516704195 -0500      commit (initial): created the funny git meme repo
228b603ed45ddaf1b1d3fe502e168fa2508ee5ed 228b603ed45ddaf1b1d3fe502e168fa2508ee5ed PS <ps@hacking-lab.com> 1516704195 -0500      checkout: moving from master to temp
228b603ed45ddaf1b1d3fe502e168fa2508ee5ed b9820d55ce59799992648672a5a43fff4effd56b PS <ps@hacking-lab.com> 1516704195 -0500      commit: temp branch created
b9820d55ce59799992648672a5a43fff4effd56b 9d7c9b5a1c8773ea48caac90d05401679b0a8897 PS <ps@hacking-lab.com> 1516704195 -0500      commit: added one more image
9d7c9b5a1c8773ea48caac90d05401679b0a8897 228b603ed45ddaf1b1d3fe502e168fa2508ee5ed PS <ps@hacking-lab.com> 1516704195 -0500      checkout: moving from temp to master
228b603ed45ddaf1b1d3fe502e168fa2508ee5ed 3839c14d2863fd850794661677352305ea798eb6 PS <ps@hacking-lab.com> 1516704195 -0500      commit: more funny images added
3839c14d2863fd850794661677352305ea798eb6 3839c14d2863fd850794661677352305ea798eb6 PS <ps@hacking-lab.com> 1516704195 -0500      checkout: moving from master to branch
3839c14d2863fd850794661677352305ea798eb6 9a29769663d029f1b3ad83fec7e7f19ca1cf8e78 PS <ps@hacking-lab.com> 1516704195 -0500      commit: branch created
9a29769663d029f1b3ad83fec7e7f19ca1cf8e78 3839c14d2863fd850794661677352305ea798eb6 PS <ps@hacking-lab.com> 1516704196 -0500      checkout: moving from branch to master
3839c14d2863fd850794661677352305ea798eb6 b9e860f47fe6990cbda4ac5bb3d2829d2191f1eb PS <ps@hacking-lab.com> 1516704196 -0500      commit: even more funny images added

$ git log --all --branches --remotes --tags --reflog --oneline --graph
* b9e860f (HEAD -> master) even more funny images added
| * 9a29769 (branch) branch created
|/
* 3839c14 more funny images added
| * 9d7c9b5 added one more image
| * b9820d5 temp branch created
|/
* 228b603 created the funny git meme repo
```

```
git checkout 9d7c9b5a1c8773ea48caac90d05401679b0a8897
```

gives us another image, `tree.jpg` and yet another version of `02.png`:

![](writeupfiles/chall16/images/02-3.png)
![](writeupfiles/chall16/images/tree.jpg)

Eventually discovered `git fsck` notes a dangling blob which is apparently some
piece of data that was included at one point but the commit was later removed or backed out.

```console
$ git fsck
Prüfe Objekt-Verzeichnisse: 100% (256/256), Fertig.
dangling blob dbab6618f6dc00a18b4195fb1bec5353c51b256f
$ git cat-file -p dbab6618f6dc00a18b4195fb1bec5353c51b256f > tmp.png
$ file tmp.png
tmp: PNG image data, 480 x 480, 8-bit colormap, non-interlaced
```

**Egg**

![](writeupfiles/chall16/tmp.png)

## Challenge 17:

**Challenge**

Alien space invaders left a secret message. Luckily, you know that they used codemoji.org for the encryption.

Decrypt the message, and save the planet!!

> ⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢

**Solution**

- Go to https://codemoji.org/#/encrypt
- create a random message
- "share", they give you a shortlink that resolves into "https://codemoji.org/share.html?data=...." where data is an html encoded, base64 encoded blob of json like:

   ```json
   {
     "message": "⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢",
     "key": "👾"
   }
    ```

- replace the message with the given input: "⚾⭐📯💵🎨📢📘💪☀🌆💪🐸🎨🐦📢"
- and get [this url](https://codemoji.org/share.html?data=eyJtZXNzYWdlIjoi4pq%2B4q2Q8J%2BTr/CfkrXwn46o8J%2BTovCfk5jwn5Kq4piA8J%2BMhvCfkqrwn5C48J%2BOqPCfkKbwn5OiXG4iLCJrZXkiOiLwn5G%2BIn0%3D)
- which decodes with the invader emoji to `invad3rsmustd13`

**Egg**

![](writeupfiles/chall17/egg.png)

## Challenge 18:

**Challenge**

**Solution**

**Egg**

## Challenge 19:

**Challenge**

**Solution**

**Egg**

## Challenge 20:

**Challenge**

**Solution**

we find some hidden text in the pdf file using extractpdf.com:

```
Composition

No Name Yet

�Okay, let’s do the information exchange as we coordinated. First let me
tell you: hiding informations in a MIDI file will be popular soon! We should
only do it this way to stay covered. MIDI hiding is just next level – wow! So,
here are all informations you need to find the secret: Trackline: Can’t remember now,
but you’ll find it. It’s kinda quiet this time, because of the doubled protection
algorithm! Characters: 0 - 127 (by the way: we won‘t need the higher ones
ever…)Let’s go!�
I‘m very exited for the lyrics that you will create
for this masterpiece.
Best wishes, your friend

LuckyTail

```

**Egg**

## Challenge 21: Hot Dog

**Challenge**

or: how to solve this darn crypto challenge to get your sleep back.

Enter the flag found, into the Egg-o-Matic below, without brackets.

[hotdog.zip](writeupfiles/chall21/hotdog.zip)


**Solution**

The zipfile contains a tiff file

![](writeupfiles/chall21/hotdog.tiff)

with binwalk we find a png image embedded

![](writeupfiles/chall21/egg-almost.png)

```bash
$ zbarimg egg-almost.png
QR-Code:Arf3ThIY8VQg2GUd249wzDYi7CXqTST+9g4Q7bbT2eF+mD2KB+6oi3rVSY/eZ6/onNBNYPo2BPqIVEbL35G62pIHvabGcrYosGCpYhiz6EYnamnNPrHdzmEOs8lCRw1c2Pe8kl41FH0ud7tBn6qD/stnZfGkcbeIrjaSiIYSveHS

scanned 1 barcode symbols from 1 images in 0.02 seconds


```

**Egg**

## Challenge 22:

**Challenge**

**Solution**

**Egg**

## Challenge 23:

**Challenge**

**Solution**

**Egg**

## Challenge 24:

**Challenge**

**Solution**

**Egg**
