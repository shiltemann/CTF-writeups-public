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
Challenge 05: sloppy & Paste             Easy
Challenge 06: Cooking for Hackers        Easy
Challenge 07: Jigsaw                     Easy
Challenge 08: Disco Egg                  Easy
Challenge 09: Dial Trial                 Easy
Challenge 10: Level Two                  Medium
Challenge 11: De Egg you must            Medium
Challenge 12: Patience                   Medium
Challenge 13: Sagittarius...             Medium
Challenge 14: Same same...               Medium
Challenge 15: Manile greetings           Medium
Challenge 16: git cloak --hard           Medium
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

!()[writeupfiles/chall04/cover.jpg]

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

!()[writeupfiles/chall04/completed.png]

**Egg**

![](writeupfiles/chall04/egg.png)

```
he18-cGoS-a2tz-BD2w-zXH8
```


## Challenge 05:

**Challenge**

**Solution**

**Egg**

## Challenge 06:

**Challenge**

**Solution**

**Egg**

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

**Solution**

**Egg**

## Challenge 10:

**Challenge**

**Solution**

**Egg**

## Challenge 11:

**Challenge**

**Solution**

**Egg**

## Challenge 12:

**Challenge**

**Solution**

**Egg**

## Challenge 13:

**Challenge**

**Solution**

**Egg**

## Challenge 14:

**Challenge**

**Solution**

**Egg**

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

**Egg**

## Challenge 17:

**Challenge**

**Solution**

**Egg**

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

**Egg**

## Challenge 21:

**Challenge**

**Solution**

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
