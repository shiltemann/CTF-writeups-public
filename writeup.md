# HackVent 2017

Another edition of Hacking-Lab's annual advent calender CTF. Every day between December 1 and Christmas, a new challenge is released. Solve it on the day of release for maximum points, solve it later (but before the new year) for one point less.

## Overview


```
Title                                    Category  Points  Flag
---------------------------------------- --------- ------- -----------------------------
Dec  1: 5th Anniversary                  Easy      2/1     HV17-5YRS-4evr-IJHy-oXP1-c6Lw
Dec  2: Wishlist                         Easy      2/1     HV17-Th3F-1fth-Pow3-r0f2-is32
Dec  3: Strange Logcat Entry             Easy      2/1     HV17-th1s-isol-dsch-00lm-agic
Dec  4: HoHoHo                           Medium    3/2     HV17-RP7W-DU6t-Z3qA-jwBz-jItj
Dec  5: Only One hint                    Medium    3/3
Dec  6:
Dec  7:
Dec  8:
Dec  9:
Dec 10:
Dec 11:
Dec 12:
Dec 13:
Dec 14:
Dec 15:
Dec 16:
Dec 17:
Dec 18:
Dec 19:
Dec 20:
Dec 21:
Dec 22:
Dec 23:
Dec 24:

Hidden 1:  Header                         Hidden    1       HV17-4llw-aysL-00ki-nTh3-H34d
Hidden 2:
Hidden 3:  Robots                         Hidden    1       HV17-bz7q-zrfD-XnGz-fQos-wr2A
Hidden 4:  CSS                            Hidden    1       HE17-W3ll-T00E-arly-forT-his!
Hidden 5:

```


There were 5  hidden balls this year.

## Hidden ball 1:

**Solution**

Challenges are accessed by url like `https://hackvent.hacking-lab.com/challenge.php?day=2`

Let's see what happens when we try to skip ahead to Christmas `?day=25`

We get:

```
The resource (#1959) you are trying to access, is not (yet) for your eyes.
```

ok, weird, what about `?day=26`

```
The resource (#1958) you are trying to access, is not (yet) for your eyes.
```

day and resource number seem to add up to 1984 every time, so let's see what happens when we fill in `?day=1984`

```
The resource you are trying to access, is hidden in the header.
```

whoo! let's check the headers:

```
HTTP/1.1 200 OK
Date: Sat, 02 Dec 2017 21:14:21 GMT
Server: Merry Christmas & Hacky New Year
Strict-Transport-Security: max-age=15768000
Flag: HV17-4llw-aysL-00ki-nTh3-H34d
Keep-Alive: timeout=5, max=99
Connection: Keep-Alive
Transfer-Encoding: chunked
Content-Type: text/html; charset=UTF-8
```

There is our flag!

**Nugget**

```
HV17-4llw-aysL-00ki-nTh3-H34d
```

## Hidden ball 2:

**Solution**

**Nugget**

```
HV17-
```

## Hidden ball 3:

**Solution**  

we check `robots.txt` and see the following message: `We are people, not machines`

so then we check `people.txt`: `What's about akronyms?`

so then we check `humans.txt` and see:

```
All credits go to the following incredibly awesome HUMANS (in alphabetic order):
avarx
DanMcFly
HaRdLoCk
inik
Lukasz
M.
Morpheuz
MuffinX
PS
pyth0n33

HV17-bz7q-zrfD-XnGz-fQos-wr2A

```

whoo, theres a flag!

**Nugget**

```
HV17-bz7q-zrfD-XnGz-fQos-wr2A
```

## Hidden ball 4:

**Solution**

This one was hiding in the css folder `/css/egg.png`, it's an egg from Hacky Easter!

![](writeupfiles/egg.png)

**Nugget**

```
HE17-W3ll-T00E-arly-forT-his!
```

## Hidden ball 5:

**Solution**

**Nugget**

```
HV17-
```


## Dec 1: 5th Anniversary  
*time to have a look back*

**Challenge**  

![](writeupfiles/HV17-hv16-hv15-hv14.svg)

**Solution**  

Looks like we need the solutions from previous years, good thing I kept writeups

```
2014: HV14-BAAJ-6ZtK-IJHy-bABB-YoMw
2015: HV15-Tz9K-4JIJ-EowK-oXP1-NUYL
2016: HV16-t8Kd-38aY-QxL5-bn4K-c6Lw
```

Putting the fragments together gives our nugget

**Nugget**

```
HV17-5YRS-4evr-IJHy-oXP1-c6Lw
```


## Dec 2: Wishlist  
*The fifth power of two*

**Challenge**  

Something happened to my wishlist, please help me.

[Get the Wishlist](writeupfiles/Wishlist.txt)

**Solution**  

This is clearly base-64 encoded, we decode, and still looks base64 endoded. Taking the hint
into account, we decode 32 times:

```bash
$ cat Wishlist.txt | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d  | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d

HV17-Th3F-1fth-Pow3-r0f2-is32%   
```

**Nugget**

```
HV17-Th3F-1fth-Pow3-r0f2-is32
```

## Dec 3: Strange Logcat Entry  
*Lost in messages*

**Challenge**  

I found those strange entries in my Android logcat, but I don't know what it's all about... I just want to read my messages!

[Get the logcat](writeupfiles/logcat.txt)

**Solution**  

This is a long logcat file, but we notice that only two lines have raw tabs,
suggesting they were manually added for the challenge:

![](writeupfiles/logcat1.jpg)

![](writeupfiles/logcat2.jpg)

which are the following lines:

```
11-13 20:40:13.542	 137   137 I DEBUG	 : 			FAILED TO SEND RAW PDU MESSAGE

[..]

11-13 20:40:24.044	137	  137  DEBUG: I 07914400000000F001000B913173317331F300003AC7F79B0C52BEC52190F37D07D1C3EB32888E2E838CECF05907425A63B7161D1D9BB7D2F337BB459E8FD12D188CDD6E85CFE931
```

This seems to be a raw SMS format, which we can decoded here:

https://www.diafaan.com/sms-tutorials/gsm-modem-tutorial/online-sms-pdu-decoder/

or using a python script:

```bash
$ pip install python-gsmmodem
```

```python
import gsmmodem
import json

PDU='07914400000000F001000B913173317331F300003AC7F79B0C52BEC52190F37D07D1C3EB32888E2E838CECF05907425A63B7161D1D9BB7D2F337BB459E8FD12D188CDD6E85CFE931'

decoded = gsmmodem.pdu.decodeSmsPdu(PDU)
print json.dumps(decoded, indent=4)

```

```
{
    "reference": 0,
    "protocol_id": 0,
    "text": "Good Job! Now take the Flag: HV17-th1s-isol-dsch-00lm-agic",
    "smsc": "+44000000000",
    "number": "+13371337133",
    "type": "SMS-SUBMIT",
    "tpdu_length": 64
}
```

So the flag is in the SMS!


**Nugget**

```
HV17-th1s-isol-dsch-00lm-agic
```

## Dec 4: HoHoHo  
*hint*

**Challenge**  

Santa has hidden something for you [here](writeupfiles/HoHoHo_medium.pdf)

**Solution**  

It's a pdf file, opening in okular popped up that ther was an embedded font file, named [DroidSans-HACKvent.sfd](DroidSans-HACKvent.sfd) ..with hackvent in the name, that's got to be hiding our flag!

We used [fontforge](https://fontforge.github.io/overview.html) to extract the font from the pdf file and view it:

![](writeupfiles/dec4-fontforge-before.png)

hmm, we don't see any characters in the boxes, so we select `view->fit to bounding box`:

![](writeupfiles/dec4-fontforge.png)

And there is our flag! ..looks like the characters were just tiny and being selectively enlarged in the pdf to create the visible text.

**Nugget**

```
HV17-RP7W-DU6t-Z3qA-jwBz-jItj
```

## Dec 5: Only One Hint  
*hint*

**Challenge**  

Here is your flag:

```
0x69355f71
0xc2c8c11c
0xdf45873c
0x9d26aaff
0xb1b827f4
0x97d1acf4
```

and the one and only hint:

```
0xFE8F9017 XOR 0x13371337
```    

**Solution**  

`0xFE8F9017 XOR 0x13371337` is `0xedb88320` which is a polynomial involved in CRC32, and indeed

`crc32('HV17')` is `0x69355f71`

try to bruteforce?

```python
import binascii
import itertools
import string

alphabet=string.ascii_letters + string.digits

ct=[0x69355f71,
0xc2c8c11c,
0xdf45873c,
0x9d26aaff,
0xb1b827f4,
0x97d1acf4]

perms = list(itertools.permutations(alphabet, 4))

for p in perms:
    tst = ''.join(p)
    if binascii.crc32(tst) in ct:
        print ("bingo! "+ tst +" ("+hex(binascii.crc32(tst))+")")

```

..hmm, only comes up with the solution to the first part, the `HV17`, there is a bit more to it but must be on the right track

**Nugget**

```
HV17-
```

## Dec 6: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 7: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 8: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 9: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 10: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 11: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 12: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 13: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 14: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 15: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 16: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 17: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 18: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 19: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 20: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 21: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 22: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 23: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 24: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```
