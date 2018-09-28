# PicoCTF 2018

## Overview


```
Title                        Category         Points  Flag
---------------------------- ---------------- ------  -----------------------------
Forensics Warmup 1           Forensics        50      picoCTF{welcome_to_forensics}
Forensics Warmup 2           Forensics        50      picoCTF{extensions_are_a_lie}
General Skills Warmup 1      General Skills   50      picoCTF{A}
General Skills Warmup 2      General Skills   50      picoCTF{11011}
General Skills Warmup 3      General Skills   50      picoCTF{61}
Resources                    General Skills   50      picoCTF{xiexie_ni_lai_zheli}
Reversing Warmup 1           Reversing        50      picoCTF{welc0m3_t0_r3VeRs1nG}
Reversing Warmup 2           Reversing        50      picoCTF{th4t_w4s_s1mpL3i}
Crypto Warmup 1              Cryptography     75      picoCTF{SECRETMESSAGE}
Crypto Warmup 2              Cryptography     75      picoCTF{this_is_crypto!}
grep 1                       General Skills   75      picoCTF{grep_and_you_will_find_c709fa94}
net cat                      General Skills   75
HEEEEEEERE'S Johnny!         Cryptography     100
Inspect Me                   Web              125
Desrouleaux                  Forensics        150
Logon                        Web              150
admin panel                  Forensics        150
buffer overflow 0            Binary Exploit   150
caesar cipher 1              Cryptography     150
hertz                        Cryptography     150
hex editor                   Forensics        150
Irish Name Repo              Web              200
Truly an Artist              Forensics        200
now you don't                Forensics        200
shellcode                    Binary Exploit   200
what base is this?           General Skills   200
Buttons                      Web              250
echooo                       Binary Exploit   300
```

## Forensics 50: Forensics Warmup 1

**Challenge**

Can you unzip [this file](writeupfiles/flag.zip) for me and retreive the flag?

**Solution**

we unzip to find an image:

![](writeupfiles/flag.jpg)

**Flag**
```
picoCTF{welcome_to_forensics}
```

## Forensics 50: Forensics Warmup 2

**Challenge**

Hmm for some reason I can't open [this PNG](writeupfiles/flag.png)? Any ideas?

**Solution**

Turns out the file isn't actually a png file (though gimp will open it even with
the wrong extension)

```bash
$ file flag.png
flag.png: JPEG image data, JFIF standard 1.01, resolution (DPI), density 75x75, segment length 16, baseline, precision 8, 909x190, frames 3
```

![](writeupfiles/flag2.jpg)

**Flag**
```
picoCTF{extensions_are_a_lie}
```

## General Skills 50: Warmup 1

**Challenge**

If I told you your grade was `0x41` in hexadecimal, what would it be in ASCII?

**Solution**

```python
>>> chr(int('41',16))
'A'
```

**Flag**
```
picoCTF{A}
```

## General Skills 50: Warmup 2

**Challenge**

**Solution**

```python
>>> bin(27)
'0b11011'
```

**Flag**
```
picoCTF{11011}
```


## General Skills 50: Warmup 3

**Challenge**

What is 0x3D (base 16) in decimal (base 10).

**Solution**

```python
>>> int('3D',16)
61
```

**Flag**
```
picoCTF{61}
```

## General Skills 50: Resources

**Challenge**

We put together a bunch of resources to help you out on our website! If you go over there,
you might even find a flag! https://picoctf.com/resources

**Solution**

flag was just written on the page

**Flag**
```
picoCTF{xiexie_ni_lai_zheli}
```

## Reversing 50: Reversing Warmup 1

**Challenge**

 Throughout your journey you will have to run many programs. Can you navigate to
`/problems/reversing-warmup-1_0_f99f89de33522c93964bdec49fb2b838` on the shell server
and run [this program](writeupfiles/run) to retreive the flag?



**Solution**

```bash
$ ssh ysje@2018shell1.picoctf.com
picoCTF{who_n33ds_p4ssw0rds_38dj21}
Welcome ysje!
Your shell server account has been created.
Please press enter and reconnect.
```

We see a flag there but its not for this challenge

```bash
$ cd /problems/reversing-warmup-1_0_f99f89de33522c93964bdec49fb2b838
$ ./run
picoCTF{welc0m3_t0_r3VeRs1nG}
```

or

```bash
$ strings run | grep picoCTF
picoCTF{welc0m3_t0_r3VeRs1nG}
```


**Flag**
```
picoCTF{welc0m3_t0_r3VeRs1nG}
```

## Reversing 50: Reversing Warmup 2

**Challenge**

Can you decode the following string `dGg0dF93NHNfczFtcEwz` from base64 format to ASCII?

**Solution**

```python
>>> import base64
>>> base64.b64decode('dGg0dF93NHNfczFtcEwz')
'th4t_w4s_s1mpL3'
```


**Flag**
```
picoCTF{th4t_w4s_s1mpL3}
```

## Cryptography 75: Crypto Warmup 1

**Challenge**

Crpyto can often be done by hand, here's a message you got from a friend, `llkjmlmpadkkc` with the key of `thisisalilkey`. Can you use this table to solve it?.

```
    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
   +----------------------------------------------------
A | A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
B | B C D E F G H I J K L M N O P Q R S T U V W X Y Z A
C | C D E F G H I J K L M N O P Q R S T U V W X Y Z A B
D | D E F G H I J K L M N O P Q R S T U V W X Y Z A B C
E | E F G H I J K L M N O P Q R S T U V W X Y Z A B C D
F | F G H I J K L M N O P Q R S T U V W X Y Z A B C D E
G | G H I J K L M N O P Q R S T U V W X Y Z A B C D E F
H | H I J K L M N O P Q R S T U V W X Y Z A B C D E F G
I | I J K L M N O P Q R S T U V W X Y Z A B C D E F G H
J | J K L M N O P Q R S T U V W X Y Z A B C D E F G H I
K | K L M N O P Q R S T U V W X Y Z A B C D E F G H I J
L | L M N O P Q R S T U V W X Y Z A B C D E F G H I J K
M | M N O P Q R S T U V W X Y Z A B C D E F G H I J K L
N | N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
O | O P Q R S T U V W X Y Z A B C D E F G H I J K L M N
P | P Q R S T U V W X Y Z A B C D E F G H I J K L M N O
Q | Q R S T U V W X Y Z A B C D E F G H I J K L M N O P
R | R S T U V W X Y Z A B C D E F G H I J K L M N O P Q
S | S T U V W X Y Z A B C D E F G H I J K L M N O P Q R
T | T U V W X Y Z A B C D E F G H I J K L M N O P Q R S
U | U V W X Y Z A B C D E F G H I J K L M N O P Q R S T
V | V W X Y Z A B C D E F G H I J K L M N O P Q R S T U
W | W X Y Z A B C D E F G H I J K L M N O P Q R S T U V
X | X Y Z A B C D E F G H I J K L M N O P Q R S T U V W
Y | Y Z A B C D E F G H I J K L M N O P Q R S T U V W X
Z | Z A B C D E F G H I J K L M N O P Q R S T U V W X Y
```

**Solution**

Looks like vigenere,


**Flag**
```
picoCTF{SECRETMESSAGE}
```

## Cryptography 75: Crypto Warmup 2

**Challenge**

Cryptography doesn't have to be complicated, have you ever heard of something called rot13? `cvpbPGS{guvf_vf_pelcgb!}`

**Solution**

```python
>>> 'cvpbPGS{guvf_vf_pelcgb!}'.decode('rot13')
u'picoCTF{this_is_crypto!}'
```

**Flag**
```
picoCTF{this_is_crypto!coCTF{this_is_crypto!}}
```

## General Skills 75: grep 1

**Challenge**

Can you find the flag in [file](writeupfiles/file)? This would be really obnoxious to look through by hand, see if you can find a faster way.

**Solution**

```bash
$ grep "picoCTF" file                                                                                                        [28-09-18 23:15:19]
picoCTF{grep_and_you_will_find_c709fa94}
```

**Flag**
```

```

## General Skills 75: net cat

**Challenge**

**Solution**

**Flag**
```

```

## Cryptography 100: HEEEEEEERE'S Johnny!

**Challenge**

**Solution**

**Flag**
```

```

## Web Exploitation 125: Inspect Me

**Challenge**

**Solution**

**Flag**
```

```

## Forensics 150: Desrouleaux

**Challenge**

**Solution**

**Flag**
```

```

## Web Exploitation 150: Logon

**Challenge**

**Solution**

**Flag**
```

```

## Forensics 150: admin panel

**Challenge**

**Solution**

**Flag**
```

```

## Binary Exploitation 150: buffer overflow 0

**Challenge**

**Solution**

**Flag**
```

```

## Cryptography 150: caesar cipher 1

**Challenge**

**Solution**

**Flag**
```

```

## Cryptography 150: hertz

**Challenge**

**Solution**

**Flag**
```

```

## Forensics 150: hex editor

**Challenge**

**Solution**

**Flag**
```

```

## Web Exploitation 200: Irish Name Repo

**Challenge**

**Solution**

**Flag**
```

```

## Forensics 200: Truly an Artist

**Challenge**

**Solution**

**Flag**
```

```

## Forensics 200: now you don't

**Challenge**

**Solution**

**Flag**
```

```

## Binary Exploitation 200: shellcode

**Challenge**

**Solution**

**Flag**
```

```

## General Skills 200: what base is this?

**Challenge**

**Solution**

**Flag**
```

```

## Web Exploitation 250: Buttons

**Challenge**

**Solution**

**Flag**
```

```

## Binary Exploitation 300: echooo

**Challenge**

**Solution**

**Flag**
```

```

