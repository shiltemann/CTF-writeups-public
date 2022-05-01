# HackyEaster 2022

The annual Easter CTF from Hacking-lab

![](writeupfiles/teaser_banner.jpg)


## Overview

Title                                            | Points     | Egg
-------------------------------------------------| ---------- | ------------------------------
[Welcome Flag](#welcome-flag)                    | Level 1    | `he2022{welcome_to_hacky_easter_2022}`
[Sp4c3 Inv4d3r5!](#sp4c3-inv4d3r5)               | Level 2    | `he2022{Inv4d3rs_fr0m_sp4c3!}`
[I Key, You Key, ASCII](i-key-you-key-ascii)     | Level 2    | `he2022{th1s_0n3_1s_r3333ly_s1mpl3}`
[Alpha Bravo Charlie](#alpha-bravo-charlie)      | Level 2    | `he2022{phonetic}`
[Fibonacci Rabbits](#fibonacci-rabbits)          | Level 3    |
[Knäck låset](#knäck-låset)                      | Level 3    | `he2022{807}`
[City Trip](#city-trip)                          | Level 3    |


## Level 1


### Welcome Flag

**Challenge**

Welcome to Hacky Easter 2022!

Open the file and catch your first flag!

🚩 Flag format: he2022{real_flag_here}

**Solution**

Just a practice level, open a textfile and find the flag:

```
WELCOME TO HACKY EASTER 2022
============================

Well, this is not a real challenge yet, just a quick intro. Some would say sanity check.

EVENT
- The event runs until May 17, 13:37 CET.
- Please do not publish write-ups before that.
- After May 17, all challenges will be opened for everyone, and remain online until end of May.
- There's a Discord server, in case you need support.

CHALLENGES
- Challenges have difficulty noob, easy, medium, or hard.
- Some challenges have a hint - opening the hint is FREE.

LEVELS
- With a certain amount of points scored in the CURRENT level, you level up.
- You can always go back to earlier levels, to solve remaining challenges.

That's it for now. Check the HowTo for more details.


Time to catch the first flag now! Look at the nice ASCII art below!

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxOxxOOOOOOOOOOOxxOxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxx&OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOx&xxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOxxxxxxxxxxxxxxxx
xxxxxxxxxxxxx&OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOxxxxxxxxxxxxx
xxxxxxxxxx&OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO&&O&OOOOOOOOOOOOOOOOOOxxxxxxxxxx
xxxxxxxx&OOOOOOOOOOOOOOO/      (OOOOOOOOOOOOOO        OOOOOOOOOOOOOOOOOOOxxxxxxx
xxxxxx&OOOOOOOOOOOOOOOO(        ,&OOOOOOOOOOO,        &x&OOOOOOOOOOOOOOOOOxxxxxx
xxxxxOOOOOOOOOOOOOOOOOOO         .&x&OOOOOOO&         xxxxxOOOOOOOOOOOOOOOOxxxxx
xxxxOOOOOOOOOOOOOOOOOOOOO          &xxxOOOOO         &xxxxxxxxOOOOOOOOOOOOOOOxxx
xxxOOOOOOOOOOOOOOOOOOOOOOO          &xxxx&O#        .&xxxxxxxxxxOOOOOOOOOOOOOOxx
xxOOOOOOOOOOOOOOOOOOOOOOOOO.         &xxxxx         xxxxxxxxxxxxxxxOOOOOOOOOOO&x
xOOOOOOOOOOOOOOOOOOOOOOOOOOO,                      /xxxxxxxxxxxxxxxxxOOOOOOOOOOx
xOOOOOOOOOOOOOOOOOOOOOOOOOOOO*                     xxxxxxxxxxxxxxxxxxxxxOOOOOOOO
OOOOOOOOOOOOOOOOOOOOOOOOOOOO/    &xx&.      xxx&.    &xxxxxxxxxxxxxxxxxxxxOOOOOO
OOOOOOOOOOOOOOOOOOOOOOOOOOOO    xxxxxx     xxxxx&    #xxxxxxxxxxxxxxxxxxxxxxxOOO
OOOOOOOOOOOOOOOOOOOOOOOOOOOO     &xx&       xxx&.    xxxxxxxxxxxxxxxxxxxxxxxxxxO
OOOOOOOOOOOOOOOOOOOOO&   &OOO/                      &xxxx#xxxxxxxxxxxxxxxxxxxxxx
OOOOOOOOOOOOOOOOOO#        &OOOO*                xxxxxx     #xxxxxxxxxxxxxxxxxxx
OOOOOOOOOOOOOOOOO#            OOOO&x&*  &.  &xxxxxxx           xxxxxxxxxxxxxxxx&
x&OOOOOOOOOOOOOOOOO&xx/           (OOx&&x&&&xxx*              .&xxxxxxxxxxxxxxxx
xxOOOOOOOOOOOOOOOOOOOOxxxxxx&x         xxx.         *x&&xxxxxxxxxxxxxxxxxxxxxxxx
xx&OOOOOOOOOOOOOOOOOOOOO&xxxxxxxxxx,       OO*x&xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxOOOOOOOOOOOOOOOOOOOOOOOx&&x.      ,x       .x&xxxxxxxxxxxxxxxxxxxxxxxxxx&xxx
xxxxxxOOOOOOOOOOO#                *xxxxxxxx&&              ,x&xxxxxxxxxxxxxxxxxx
xxxxxxxOOOOOOOOOOO&.         O&xxxxxxxxxxxxxxxxx&x            xxxxxxxxxxxxxxxxxx
xxxxxxxx&OOOOOOOOOOOO/   /&xxxxxxxxxxxxxxxxxxxxxxxxx&     /&xxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxOOOOOOOOOOOOOxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxx&OOOOOOOOOOOO&xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&xxxxxxxxxxxxx
xxxxxxxxxxxxxxxx&OOOOOOOOOOOOxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&xxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxOOOOOOOOOO&xxxxxxxxxxxxxxxxxxxxxxxxxxx&xxxxxxxxxxxxxxxxxxxx
xxxxxxxxxxxxxxxxxxxxxxxxxxOOOO FLAG HERE -> he2022{welcome_to_hacky_easter_2022}
```

**Egg**

```
he2022{welcome_to_hacky_easter_2022}
```


## Level 2

### Sp4c3 Inv4d3r5!

**Challenge**

My favourite game in the 80s was Space Invaders!

[PDF](writeupfiles/spaceinvaders.pdf)

**Solution**

Opening up the pdf we see the following:

![](writeupfiles/spaceinvaders.png)

Nothing to see with binwalk, so maybe the key is in the images itself.

A Quick google reveals the following space invaders alphabet

![](writeupfiles/space-invaders-code.png)

Simple transcription (or simply copying and pasting the text into a diffrent font, since it was text) gives us the key:

**Egg**

```
he2022{Inv4d3rs_fr0m_sp4c3!}
```

### Glitch

**Challenge**

I got a flag, but it's glitched somehow.

```
}ɥɔʇᴉしƃ_ǝしʇʇᴉし_ɐ_ʇ己几ɾ{ᄅᄅ０ᄅǝɥ
```

**Solution**
It just a weird upside down font? read backwards and upside down to get the flag

**Egg**

```
he2022{just_a_little_glitch}
```

### I Key, You Key, ASCII

**Challenge**

Look what I was drawing in my text editor!

```
.. .. .. 68 65 32 30 .. .. ..
.. .. 32 ██ ██ ██ ██ 32 .. ..
.. 7b ██ ██ ██ ██ ██ ██ 74 ..
.. 68 ██ ██ ██ ██ ██ ██ 31 ..
73 ██ ██ ██ ██ ██ ██ ██ ██ 5f
30 ██ ██ ██ ██ ██ ██ ██ ██ 6e
33 ██ ██ ██ ██ ██ ██ ██ ██ 5f
31 ██ ██ ██ ██ ██ ██ ██ ██ 73
5f ██ ██ ██ ██ ██ ██ ██ ██ 72
33 ██ ██ ██ ██ ██ ██ ██ ██ 33
33 ██ ██ ██ ██ ██ ██ ██ ██ 33
.. 6c ██ ██ ██ ██ ██ ██ 79 ..
.. 5f ██ ██ ██ ██ ██ ██ 73 ..
.. .. 31 ██ ██ ██ ██ 6d .. ..
.. .. .. 70 6c 33 7d .. .. ..
```

Hint: Cyber Chef

**Solution**

Ignoring everything but the hex values and converting to ascii gives us the flag

**Egg**

```
he2022{th1s_0n3_1s_r3333ly_s1mpl3}
```

### Alhpa Bravo Charlie

**Challenge**

I received a strange message on my walkie-talkie today:

hotel echo two zero two two{papa hotel oscar november echo tango india charlie}

**Solution**

Just [NATO phonetic alphabet](https://en.wikipedia.org/wiki/NATO_phonetic_alphabet)

**Egg**

```
he2022{phonetic}
```


## Level 3

### Fibonacci Rabbits

**Challenge**

Everyone loves rabbits!

http://46.101.107.117:2201

Note: The service is restarted every hour at x:00.

Hint: It is not about the rabbit names.


**Solution**

A website with pictures of bunnies..

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fibonacci Rabbits</title>
    <link rel="stylesheet" href="style.css" />
    <script>
        if (document.location.search.match(/type=embed/gi)) {
            window.parent.postMessage("resize", "*");
        }
    </script>
</head>
<body translate="no">
    <h1>Fibonacci Rabbits</h1>
    <div id="gallery">
        <div><img src="images/rabbit-17711.jpg" /><a href="#">Petal</a></div>
        <div><img src="images/rabbit-75025.jpg" /><a href="#">Harley</a></div>
        <div><img src="images/rabbit-34.jpg" /><a href="#">Rosie</a></div>
        <div><img src="images/rabbit-987.jpg" /><a href="#">Petunia</a></div>
        <div><img src="images/rabbit-8.jpg" /><a href="#">Mortimer</a></div>
        <div><img src="images/rabbit-1.jpg" /><a href="#">Henry</a></div>
        <div><img src="images/rabbit-144.jpg" /><a href="#">Miffy</a></div>
        <div><img src="images/rabbit-2584.jpg" /><a href="#">E.B.</a></div>
        <div><img src="images/rabbit-89.jpg" /><a href="#">Baxter</a></div>
        <div><img src="images/rabbit-55.jpg" /><a href="#">Archie</a></div>
        <div><img src="images/rabbit-5.jpg" /><a href="#">Murphy</a></div>
        <div><img src="images/rabbit-317811.jpg" /><a href="#">Doc</a></div>
        <div><img src="images/rabbit-2.jpg" /><a href="#">Hopper</a></div>
        <div><img src="images/rabbit-6765.jpg" /><a href="#">Fluffy</a></div>
        <div><img src="images/rabbit-46368.jpg" /><a href="#">Daffodil</a></div>
        <div><img src="images/rabbit-28657.jpg" /><a href="#">Buttons</a></div>
        <div><img src="images/rabbit-233.jpg" /><a href="#">Freddie</a></div>
        <div><img src="images/rabbit-1597.jpg" /><a href="#">Roger</a></div>
        <div><img src="images/rabbit-514229.jpg" /><a href="#">Bucky</a></div>
        <div><img src="images/rabbit-4181.jpg" /><a href="#">Oliver</a></div>
        <div><img src="images/rabbit-13.jpg" /><a href="#">Olive</a></div>
        <div><img src="images/rabbit-3.jpg" /><a href="#">Bugs</a></div>
        <div><img src="images/rabbit-377.jpg" /><a href="#">Flower</a></div>
        <div><img src="images/rabbit-10946.jpg" /><a href="#">Chester</a></div>
        <div><img src="images/rabbit-610.jpg" /><a href="#">Bubbles</a></div>
        <div><img src="images/rabbit-121393.jpg" /><a href="#">Coco</a></div>
        <div><img src="images/rabbit-21.jpg" /><a href="#">Clover</a></div>
    </div>
</body>
</html>
```

These are all fibonacci numbers, specifically the Nth:

```
22, 25, 9, 16, 6, 1, 12, 18, 11, 10, 5, 28, 3, 20, 24, 23, 13, 17, 29, 19, 7, 4, 14, 21, 15, 26, 8
```

now what?


**Egg**


### Knäck låset

**Challenge**

Knäck the cØde!

```
koda   ✅ 🔀 ❌
2-9-7  1  0  2
2-3-0  0  1  2
7-8-2  0  2  1
5-1-9  0  0  3
5-9-8  0  1  2
```

**Solution**

ok, so its mastermind


```
koda   ✅ 🔀 ❌
2-9-7  1  0  2   # 1: one correct, the 7 (not the 2 because of line 2, not the 9, because of line 4)
2-3-0  0  1  2   # 2: either a 3 or 0 in the code
7-8-2  0  2  1   # 3: 8 in the code (in addition to the 7), must be in first spot
5-1-9  0  0  3   # 4: 5, 1 and 9 not in code
5-9-8  0  1  2   # 5: 8 is in wrong spot

---------------------
8-0-7
```

**Egg**

```
he2022{807}
```


### City Trip

**Challenge**

I made a nice city trip. Find out where I was!

🚩 Flag

    street's name in lowercase and without spaces
    district or city name is not enough, we need the street
    example: Main Rd -> he2022{mainrd}

![](writeupfiles/citytrip.jpg)

**Solution**

Seems like we have to find out the location of this image, but it does not appear to be geotagged, hmm..

**Egg**


