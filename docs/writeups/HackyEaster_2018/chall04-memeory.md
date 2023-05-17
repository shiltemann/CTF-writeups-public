---
layout: writeup

title: Memeory
level:          # optional, for events that use levels (like HackyEaster)
difficulty: easy  # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he18-cGoS-a2tz-BD2w-zXH8

---

## Challenge

Fancy a round of memeory?

![](writeupfiles/chall04/cover.jpg)

Click here to play.

## Solution

nspect the html, and can see the images on each of the cards:

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

![](writeupfiles/chall04/egg.png)


