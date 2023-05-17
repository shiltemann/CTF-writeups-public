---
layout: writeup

title: Custom Keyboard
level: 7         # optional, for events that use levels (like HackyEaster)
difficulty: hard     # easy/medium/hard etc, if applicable
points: 300         # if used
categories: [misc,reversing]  # e.g. crypto, pwn, reversing
tags: [ELF]        # anything notable about challenge/solution, vuln/tools/etc

flag: he2023{leds_light_the_way}

---

## Challenge

Thumper built his first custom keyboard. He chose all the parts separately and in the end even adjusted the firmware.

Apparently, there's a flag hidden inside it. Can you find it?

[custom_keyboard.elf](writeupfiles/custom_keyboard.elf)

🚩 Flag

- lowercase and _ only
- example: `he2023{example_flag_only}`


## Solution

```bash
$ file custom_keyboard.elf
custom_keyboard.elf: ELF 32-bit LSB executable, Atmel AVR 8-bit, version 1 (SYSV), statically linked, with debug_info, not stripped
```

is we run `strings` on the file, we find the string `/home/hacker/qmk_firmware` ..

so we suspect this is [Quantum Mechanical Keyboard firmware](https://docs.qmk.fm/#/)

```bash
$ objdump -dS custom_keyboard.elf

custom_keyboard.elf:     file format elf32-little

objdump: can't disassemble for architecture UNKNOWN!

```

So we find a useful Docker container containing binutils and gdb for various architectures ([here](https://hub.docker.com/r/blukat29/cross/))

and inside this docker we can run commands like objdump for our file, and find:

```bash
$ avr-elf-objdump custom_keyboard.elf -dS --section=.data

[..]
00800216 <flag_leds.8>:
  800216:       22 18 0b 03 0b 0a 10 1f 18 25 26 02 1f 13 23 22     "........%&...#"
  800226:       16 02 16 22 18 02 19 27 15 0f                       ..."...'..
[..]
```

which looks kindof suspicious .. are these codes for keys on the keyboard?

.. this definitely fits with being the flag, `22 18 0b 03 0b 0a` would be `he2023`, and the `2`s are the same code, `3` is one differnce with `2`, etc (interestingly 3 is lower code than 2, the difference between our supposed `h` and `e` is not their difference in ASCII but, still this seems like the flag, just need to figure out the encoding

so the theory is that the flag is something like:

```
22 18 0b 03 0b 0a 10 1f 18 25 26 02 1f 13 23 22 16 02 16 22 18 02 19 27 15 0f
 h  e  2  0  2  3  {     e                    h           h  e              }
```

so what if this isn't ascii based encoding, but keyboard based, 2 and 3 are one apart on the keyboard too (if you list them by row), let's see if we
can get this to work for the rest of the keys/letters..

We just make a list of codes, fill in what we (think we) know to be the mapping, and find out that we can fill in the missing mappings based on how each row on a qwerty keyboard is laid out! codes seem to correspond with keys row by row from top to bottom, with each row listed rom right to left:

```
00 BACKSPACE
01 +
02 _
03 0 // known
04 9
05 8
06 7
07 6
08 5
09 4
0a 3 // known
0b 2 // known
0c 1
0d `
0e ENTER (?)
0f {  // known
10 }  // known
11 p
12 o
13 i  // used
14 u
15 y  // used
16 t  // used
17 r
18 e  // known
19 w  // used
1a q
1b TAB
1c |
1d "
1e ;
1f l // used
20 k
21 j
22 h // known
23 g // used
24 f
25 d // used
26 s // used
27 a // used
28 CAPS
29
..
[ bottom row of keyboard, but not used in flag so we don't write it out]
..

```

Filling in the letters based on this pattern gets us the flag!!

```
22 18 0b 03 0b 0a 10 1f 18 25 26 02 1f 13 23 22 16 02 16 22 18 02 19 27 15 0f
 h  e  2  0  2  3  {  l  e  d  s  _  l  i  g   h  t _  t  h  e  _  w  a  y  }
```

Whoo!

I really thought this was going to be the end of my HackyEaster journey here, had everything solved besides this one and the other hard challenge of this level, which were both binary/pwn challenges which is not my strong suit, and needed to solve at least on of them to advance to the next level, but here we are! Wonder if there was a nicer/better way to get there, but some guesswork and pattern recognition got me there I guess.

