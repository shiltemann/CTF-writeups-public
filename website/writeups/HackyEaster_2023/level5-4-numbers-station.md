---
layout: writeup

title: Number's Station
level: 5 # optional, for events that use levels
difficulty: medium
points: 200
categories: [misc]
tags: []

flag: he2023{L1stening_to_spy_c0mmunicat1ons}

---

## Challenge

"Testing, testing, one, two, one, zero.." - the bunnies found a strange radio station when looking for uplifting BunnyBop; can you find out what the nice Spanish lady is saying?

Hint:

> There are 10 kinds of people in this world.
>
> Those who understand binary, and those who don't.

[numbers.mp3](writeupfiles/numbers.mp3)

## Solution

We transcribe it with Whisper which is really annoying since it constantly loses it's plot and I've ended up manually transcribing about half of the audio as a result:

```
0 4 B 6 1 4 1 5 0 4 1 3 0 4 0 7 0 9 0 7 1 7 1 6 0 3 0 9 1 7 0 9 1 8 0 6 0 6 1 6 1 6 0 3 0 4 1 4 0 2
0 4 0 3 1 4 1 7 0 4 0 3 0 6 0 9 0 6 0 2 1 8 1 7 0 9 0 4 1 3 0 3 0 3 0 7 1 8 1 5 0 3 0 4 1 7 1 2 0 9
1 7 1 2 1 3 1 7 0 7 1 9 1 6 0 4 1 8 0 4 0 2 1 8 1 7 0 6 0 8 0 5 0 4 1 5 1 4 0 6 0 9 0 5 1 9 0 2 1 4
1 8 1 3 0 4 0 7 1 6 1 4 0 7 1 6 1 6 1 2 0 5 1 2 0 9 0 8 0 7 1 8 1 5 0 8 0 3 1 2 0 4 1 4 0 6 1 4 1 5
0 6 1 4 1 2 1 7 0 8 0 3 1 2 1 9 0 7 1 8 0 2 0 4 1 3 0 5 1 5 1 3 0 2 1 3 1 2 1 8 0 2 0 2 1 2 1 8 0 5
0 4 1 2 1 3 1 6 0 9 1 2 0 3 1 4 1 5 1 8 1 4 1 6 0 3 1 6 1 5 1 8 0 6 1 9 0 7 0 3 0 8 1 7 1 7 0 9 1 8
1 4 1 3 1 7 0 4 1 9 0 5 1 6 1 3 1 2 1 2 1 8 0 7 1 8 1 4 1 2 0 2 0 6 1 7 1 7 0 4 1 4 1 9 1 3 0 7 0 4
0 3 0 6 0 2 1 4 1 6 1 9 1 9 0 9 0 4 1 6 0 7 1 7 0 8 1 2 1 8 1 3 1 7 1 8 0 2 1 7 1 9 0 4 0 2 0 2 1 3
1 2 0 9 0 5 1 2 1 9 0 9 0 5 0 4 0 9 0 5 1 8 1 7 0 4 1 5 1 6 0 5 1 7 0 3 1 6 1 3 0 9 1 8 1 4 0 4 1 6
0 2 1 3 1 3 1 9 0 5 1 8 0 5 1 8 0 6 1 5 1 8 0 9 1 2 1 2 1 7 0 3 0 5 1 3 1 3 0 4 1 7 0 7 0 6 1 2 0 4
1 5 1 2 0 3 0 8 0 6 1 9 1 6 0 2 1 3 1 3 0 7 0 5 0 9 0 2 1 3 0 2 1 7 1 9 1 3 0 9 1 6 0 5 0 9 0 4 0 4
1 4 1 7 0 6 0 3 0 4 1 7 0 4 1 8 1 6 0 4 1 4 1 6 1 6 1 2 0 6 1 4 1 2 0 5 1 7 1 8 1 3 0 6 0 3 1 3 1 7
1 4 0 4 0 7 1 3 1 8 0 9 1 2 1 7 1 7 1 2 1 2 0 5 1 7
```

the even columns are all either 0 or 1 (with the exception of the first 'b' I'm guessing for 'binary'). The newlines were inserted around roughly the pauses in the audio.

So let's pull out those two columns:

```
4645434797763979866663442
4347436962879433378534729
7237796484287685454695924
8347647662529878583244645
6427832978243553232822285
4236923458463658697387798
4374956322878422677449374
3624699946778283782794223
2952995495874565736398446
2339585865892273533477624
5238696233759232793965944
4763474864466264257836337
4473892772257
```

And the binary ones:

```
0B11010000110010100110010
0011000000110010001100110
1111011010011000011000101
1100110111010001100101011
0111001101001011011100110
0111010111110111010001101
1110101111101110011011100
0001111001010111110110001
1001100000110110101101101
0111010101101110011010010
1100011011000010111010000
1100010110111101101110011
1001101111101
```

which turn up… nothing on [ascii2hex](https://www.asciitohex.com/). Odd. Could be transcription errors, but, still.

Saskia points out that if you add a zero at the start, then it decodes completely properly.
