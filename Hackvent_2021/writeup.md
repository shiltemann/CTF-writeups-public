# HACKvent 2021


The annual advent calender from Hacking-lab

![]()

## Overview


Title                                             | Category    | Points | Flag
------------------------------------------------- | ----------- | ------ | ------------------------------



## Solutions


##

## Day 3: Too Much GlItTer

**Challenge**
Well, well, well, what's that I see there?

**Solution**

I can't tell what the word is due to the font so I ran it through `uniname` (or just used a serif font)

```
$ echo GlItTer | uniname
No LINES variable in environment so unable to determine lines per page.
Using default of 24.
character  byte       UTF-32   encoded as     glyph   name
        0          0  000047   47             G      LATIN CAPITAL LETTER G
        1          1  00006C   6C             l      LATIN SMALL LETTER L
        2          2  000049   49             I      LATIN CAPITAL LETTER I
        3          3  000074   74             t      LATIN SMALL LETTER T
        4          4  000054   54             T      LATIN CAPITAL LETTER T
        5          5  000065   65             e      LATIN SMALL LETTER E
        6          6  000072   72             r      LATIN SMALL LETTER R
        7          7  00000A   0A                     LINE FEED (LF)
```

Clearly GIT relatd and indeed a `.git` directory exists. `wget -r` with the git directory and suddenly we have a working git repo! There's a `flag.html` file that's been deleted but it's empty currently and has some text about it not being deployed yet, so we check the other branches and `feature/flag` has what we're looking for!

**Flag**

```
HV{n3V3r_Sh0w_Y0uR_.git}
```


## Day 4: Christmas in Babylon

**Challenge**

**Solution**

The file is a zip file with a single `code.txt` file inside.

```
$ head code.txt
using System;using System.Text;using static System.Console;void Rev(string s){var chars=Encoding.
ASCII.GetString(Convert.FromBase64String(s)).ToCharArray();Array.Reverse(chars);WriteLine(new string
(chars));}
Rev("KzgrKitoKysrKysreysrKysraSsvPiswK3krKz4oKysrKysrICsrPlQrKysrKysrKyt9KysrPlsrVCsrK3grKysr");
Rev("K2srICsrKysyKyt9KysrKysrPisrKytVKysrKysrKysrPisrNSsrKysrK3wrKysrNT4rKysrRCs2KytyKysqKz4r");
Rev("K0MrKysrKysrMj4rKytqKyArKysrKytMKysrICsrbys+KysrKysrKysrKys+KysgKytNKysraStQKysrditxKys+");
Rev("KysqKyArKysrK2c+KysrKysrKyt4KysrKyt1Kz4rKytTKytEK0UrKytpKysrdj5XKytnKysrKysrcisjKytCPkcr");
Rev("Kz4rKysrcCspK1E+KysrRisrKysrbCsrPitKKytpKysrKys+KysrK1QrKytXKysrPisrKysrKz5nKyorKyArUCsr");
Rev("KysrK3U+KysrKysrICsrK2orKysrPjErK3MrKysrKCsrK1UrMSsrKy8+UisrKysgKysrKytBKysrKytxKz4rK3Yr");
Rev("KysrKysrKysrKzg+K1orKysrKz5MKyttK1IrSCsrICsrKz4rQisrKysrKz4rICsgKysrKysrK2QrKysrICsrPjIr");
```

apparently that's c# but we can just

```
cat writeupfiles/04/code.txt | grep '^Rev' | sed 's/Rev("//g;s/".*//g' > tmp
for line in `cat tmp`; do echo "$line" | base64 -d | tac >> decode; echo >> decode; done;
```

to mimic what the C# is doing and we get

```
$ head decode
+8+*+h++++++{+++++i+/>+0+y++>(++++++ ++>T+++++++++}+++>[+T+++x++++
+k+ ++++2++}++++++>++++U+++++++++>++5++++++|++++5>++++D+6++r++*+>+
+C+++++++2>+++j+ ++++++L+++ ++o+>+++++++++++>++ ++M+++i+P+++v+q++>
++*+ +++++g>++++++++x+++++u+>+++S++D+E+++i+++v>W++g++++++r+#++B>G+
+>++++p+)+Q>+++F+++++l++>+J++i+++++>++++T+++W+++>++++++>g+*++ +P++
++++u>++++++ +++j++++>1++s++++(+++U+1+++/>R++++ +++++A+++++q+>++v+
++++++++++8>+Z+++++>L++m+R+H++ +++>+B++++++>+ + +++++++d++++ ++>2+
v++ +&>9+ ++++++++R++++x+>++(+++>+++++++>++e++U+{++++>+++5+++> +Y+
+h+++n+ ++++w>+#+++s+K+t++l++>+}++++E++q++++>+++w+>+++n++j+j++>+++
++++Q++Y>+++8++ ++V+#+u+++a+>F++W+++A++d++E>z++++++++B++++>+}+L++W
```

surely that's brainfuck, right?

But `beef` fails:

```
$ beef writeupfiles/04/decode

beef: writeupfiles/04/decode: Unbalanced brackets
```

And so does `bf` on ubuntu. It looks like there's extra weird characters but removing those also doesn't help.



**Flag**


## Day 6: Snowcube

**Challenge**

The ester bunny sent a gift to Santa - what is usually a crystal sphere seemed a bit too boring, so it's a cube!

The snow seems to be falling somewhat strangely, is it possible that there's a message hidden somewhere?

**Solution**

I played around with it at first, and judging by the cone of snow there's clearly a correct viewpoint to view from but it's inaccessible. Checking the source code if you press `s` it will unbind the camera and you can rotate it fully to the correct viewpoint. Then all you have to do is read the text.

**Flag**

```
hv21{m3ssage_out_of_flakes}
```


## Day 15: Christmas Bauble

**Challenge**

The elves have started taking 3D modeling classes and have presented Santa with a gift. What a nice gesture! But the ball feels heavier than it should; what does that even mean for digital assets???

**Solution**

I found an stl viewer and zoomed in, it's a 3d qr code (so cube with qrs on 3 faces) internally and the bauble wrapped around, complementing the last time they had a bauble with just a boring qr inside it. But for decent perspective it'll help to rip off the bauble.

Not knowing how to blender, I just slowly selected groups of verticies and deleted them until I was left with what was close enough to a cube. Turning on orthographic projection (`5`) and then moving to the various faces I could scan the QR codes.

Cleaned up stl file included here [writeupfiles/15/obj.stl](writeupfiles/15/obj.stl)

![](writeupfiles/15/obj.stl)


**Flag**

```
HV21{1st_P4rt_0f_th3_fl4g_with_the_2nd_P4rt_c0mb1ned_w17h_th4t}
```

