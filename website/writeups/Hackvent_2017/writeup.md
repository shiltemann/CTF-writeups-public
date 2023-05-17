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
Dec  5: Only One hint                    Medium    3/2     HV17-7pKs-whyz-o6wF-h4rp-Qlt6
Dec  6: Santa's journey                  Medium    3/2     HV17-eCFw-J4xX-buy3-8pzG-kd3M
Dec  7: I know..                         Medium    3/2     HV17-UCyz-0yEU-d90O-vSqS-Sd64
Dec  8: True 1337s                       Medium    3/2     HV17-th1s-ju5t-l1k3-j5sf-uck!
Dec  9: JSONion                          Medium    3/2     HV17-Ip11-9CaB-JvCf-d5Nq-ffyi
Dec 10: Just play the game               Medium    3/2     HV17-y0ue-kn0w-7h4t-g4me-sure
Dec 11: Crypt-o-Math                     Hard      4/3     HV17-XtDw-0DzO-YRgB-2b2e-UWNz
Dec 12: giftlogistics                    Hard      4/3     HV17-eUOF-mPJY-ruga-fUFq-EhOx
Dec 13: muffin_asm                       Hard      4/3     HV17-mUff!n-4sm-!s-cr4zY
Dec 14: Happy Cryptmas                   Hard      4/3     HV17-5BMu-mgD0-G7Su-EYsp-Mg0b
Dec 15: Unsafe Gallery                   Hard      4/3     HV17-el2S-0Td5-XcFi-6Wjg-J5aB
Dec 16: Try to escape ..                 Hard      4/3     
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
Hidden 5:  Telnet                         Hidden    1       HV17-UH4X-PPLE-ANND-IH4X-T1ME

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

**Flag**

```
HV17-4llw-aysL-00ki-nTh3-H34d
```

## Hidden ball 2:

**Solution**

**Flag**

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

**Flag**

```
HV17-bz7q-zrfD-XnGz-fQos-wr2A
```

## Hidden ball 4:

**Solution**

This one was hiding in the css folder `/css/egg.png`, it's an egg from Hacky Easter!

![](writeupfiles/egg.png)

**Flag**

```
HE17-W3ll-T00E-arly-forT-his!
```

## Hidden ball 5:

**Solution**

we scan the challenge server for open ports

```bash
$ nmap challenges.hackvent.hacking-lab.com

Starting Nmap 7.01 ( https://nmap.org ) at 2017-12-06 22:59 CET
Nmap scan report for challenges.hackvent.hacking-lab.com (80.74.140.188)
Host is up (0.56s latency).
rDNS record for 80.74.140.188: urb80-74-140-188.ch-meta.net
Not shown: 996 filtered ports
PORT    STATE  SERVICE
22/tcp  open   ssh
23/tcp  open   telnet
80/tcp  closed http
443/tcp closed https

Nmap done: 1 IP address (1 host up) scanned in 67.94 seconds
```

so, there's a telnet service running, we connect, and are greeted by Santa:


```bash
$ telnet challenges.hackvent.hacking-lab.com

__.----.                                                
              _.'        '-.                                             
             /    _____     '-.                                          
            /_.-""     ""-._   \                 HO, HO, HO...           
           ."   _......._   ".  \                                        
           ; .-' _ ))) _ '-. ;   |                                       
           '/  ." _   _ ".  \'.  /                                       
           _|  .-.^ ) ^.-.  |_ \/-.                                      
           \ '"==-.(_).-=="' //    \                                     
            '.____.-^-.____.' \    /                                     
             |    ( - )   |   '--'                                       
              \           /                                              
      _________\_________/_______________________________________________

```

He keeps talking for a minute, and then gives us the flag

**Flag**

```
HV17-UH4X-PPLE-ANND-IH4X-T1ME
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

**Flag**

```
HV17-5YRS-4evr-IJHy-oXP1-c6Lw
```


## Dec 2: Wishlist  
*The fifth power of two*

**Challenge**  

Something happened to my wishlist, please help me.

[Get the Wishlist](writeupfiles/Wishlist.txt)

**Solution**  

This is clearly base-64 encoded, we decode, and still looks base64 encoded. Taking the hint
into account, we decode 32 times:

```bash
$ cat Wishlist.txt | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d  | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d

HV17-Th3F-1fth-Pow3-r0f2-is32%   
```

**Flag**

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


**Flag**

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

**Flag**

```
HV17-RP7W-DU6t-Z3qA-jwBz-jItj
```

## Dec 5: Only One Hint  

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

Let's try to bruteforce the other parts:

```python
import binascii
import itertools
import string

alphabet=string.printable

ct = [0x69355f71, 0xc2c8c11c, 0xdf45873c, 0x9d26aaff, 0xb1b827f4, 0x97d1acf4]

perms = list(itertools.product(alphabet, repeat=4))

for p in perms:
    out = binascii.crc32( ''.join(p) ) & 0xffffffff # & 0xffffffff only needed in python2

    if out in ct:
        print("bingo! "+ tst +" ("+hex(out)+")")

```

which gives the following output:

```
bingo! 7pKs (0xc2c8c11c)
bingo! h4rp (0xb1b827f4)
bingo! o6wF (0x9d26aaff)
bingo! whyz (0xdf45873c)
bingo! HV17 (0x69355f71)
bingo! Qlt6 (0x97d1acf4)
```

whoo!

**Flag**

```
HV17-7pKs-whyz-o6wF-h4rp-Qlt6
```

## Dec 6: Santa's Journey
*Make sure Santa visits every country*

**Challenge**  

Follow Santa Claus as he makes his journey around the world.

http://challenges.hackvent.hacking-lab.com:4200/

**Solution**  

When we click on the link we get a QR code image, it decodes to `Iceland`. If we refresh we get `Angola`.
Ok, let's automate this, hope we get the flag if we try often enough:

```python
import requests
from qrtools import QR


while True:
    # download image
    url = "http://challenges.hackvent.hacking-lab.com:4200/"
    r = requests.get(url)

    with open("qrcode.png", "wb") as qrimage:
        qrimage.write(r.content)

    # read QR code
    myCode = QR(filename='qrcode.png')
    if myCode.decode():
        print(myCode.data_to_string())
    if 'HV17' in myCode.data_to_string():
        break;
```

This outputs:

```
Saint-Martin
Lebanon
Anguilla
Nepal
New Zealand
Marshall Islands
Western Sahara
Chile
Yemen
Antarctica
Lithuania
Czech Republic
Panama
Saudi Arabia
Mali

[..]

China
Christmas Island
Pitcairn
Slovakia
Pitcairn
Bahrain
Cape Verde
Angola
Malawi
Ecuador
Turkmenistan
Jamaica
Mozambique
American Samoa
HV17-eCFw-J4xX-buy3-8pzG-kd3M
```

whoo! we got our flag

![](writeupfiles/dec6_qrcode.png)

**Flag**

```
HV17-eCFw-J4xX-buy3-8pzG-kd3M
```

## Dec 7: I know ...
*... what you did last xmas*

**Challenge**  

We were able to steal a file from santas computer. We are sure, he prepared a gift and there are traces for it in this file.

Please help us to recover it: [SANTA.FILE](writeupfiles/SANTA.FILE)

**Solution**  

I have a feeling this wasn't intended to be this easy, but..

```bash
$ file SANTA.FILE
SANTA.FILE: Zip archive data, at least v1.0 to extract

$ unzip SANTA.FILE
Archive:  SANTA.FILE
  inflating: SANTA.IMA   

$ file SANTA.IMA
SANTA.IMA: DOS/MBR boot sector, code offset 0x58+2, OEM-ID "WINIMAGE", sectors/cluster 4, root entries 16, sectors 3360 (volumes <=32 MB) , sectors/FAT 3, sectors/track 21, serial number 0x2b523d5, label: "           ", FAT (12 bit), followed by FAT

$ strings SANTA.IMA | grep HV17
Y*C:\Hackvent\HV17-UCyz-0yEU-d90O-vSqS-Sd64.exe
```

Note: There was also a ROT-13 version of the flag, which is probably the way the
challenge was designed to be solved:

```bash
$ strings SANTA.IMA |grep -4 HV17
-+/D
&xNsb
GameDVR_GameGUID
TitleIdr
Y*C:\Hackvent\HV17-UCyz-0yEU-d90O-vSqS-Sd64.exe
Typey=
Revision
P:\Unpxirag\UI17-HPlm-0lRH-q90B-iFdF-Fq64.rkr
969343ecc7b246e8426e573c30fd94c4ffa050c2
```

**Flag**

```
HV17-UCyz-0yEU-d90O-vSqS-Sd64
```

## Dec 8: True 1337s
... can read this instantly

**Challenge**  

I found this obfuscated code on a public FTP-Server. But I don't understand what it's doing...

[File](writeupfiles/True.1337)

**Solution**  

The file looks like (truncated):

```
 exec(chr(True+True+True+True+True+True+True+True+True+True)+chr(True+True+        
 __1337(_1337(1337+1337+1337+1337+1337+1337+1337+1337+1337+1337)+_1337(1337+1337+  
```

Obfuscated python. The following snippet cleans up the code significantly:

```bash
for i in `seq 1 200`; do
    echo $i;
    q=$(python -c "print('+'.join(['True'] * $i))")
    sed -i "s/($q)/($i)/g" True.1337
    q=$(python -c "print('+'.join(['1337'] * $i))")
    sed -i "s/($q)/($i)/g" True.1337
done
```

This leaves us with two functions, one on each line, calling a bunch of chrs.

```
exec(chr(10)+chr(65)+chr(61)+chr(99)+chr(104)+chr(114)+    
__1337(_1337(10)+_1337(67)+_1337(61)+_1337(83)+_1337(65)+   
```

Replacing that with a `print()` call on each, and cleaning up more, we can see the original code:

```bash
sed -i "s/exec/print/g" True.1337
sed -i "s/__1337/print/g" True.1337
sed -i "s/_1337/chr/g" True.1337

python True.1337 > True.1338
```

which now looks like:

```python
A=chr;__1337=exec;SANTA=input;FUN=print
def _1337(B):return A(B//1337)

C=SANTA("?")
if C=="1787569":FUN(''.join(chr(ord(a) ^ ord(b)) for a,b in zip("{gMZF_MC_X\ERF[X","31415926535897932384626433832")))
```

Running it (or the original) with py3k + inputting the magic number `1787569` results in our flag:

```bash
$ python3 True.1338
?1787569
HV17-th1s-ju5t-l1k3-j5sf-uck!
```

**Flag**

```
HV17-th1s-ju5t-l1k3-j5sf-uck!
```

## Dec 9: JSONion  
*hint*

**Challenge**  

... is not really an onion. Peel it and find the flag.

[file](writeupfiles/jsonion.json)

**Solution**  

JSON file with

```
[{"op":"map","mapTo":"[{\"op:gzi,cnteH4sIAD

<bunch of data>

,"mapFrom":"\/8ge+uqP5lz:K2Fis\"MonJUp\\{S6NvmOk]ZI}hLRYXBCW4fD=jT0V[b3xaGQ9yEw,A1cdHrt7"}]
```
..looks like we need to do a character mapping, we do that, then we get a similar string, but this
time it starts with `[{"op":"gzip", ..`. we decompress this data, and find a `b64` operation, and as
we keep peeling layers off this jsonion also `nul`, `xor`, `rev`.

We handle them all until we get a `flag` operation:

```python
import json
import gzip
import base64

def process(data_orig):
    data = json.loads(data_orig)
    for q in data:
        print(len(data_orig), len(data), q['op'])
        if q['op'] == 'map':
            process(q['content'].translate(str.maketrans(q['mapFrom'], q['mapTo'])))
        elif q['op'] == 'gzip':
            process(gzip.decompress(base64.b64decode(q['content'])).decode('utf-8'))
        elif q['op'] == 'b64':
            process(base64.b64decode(q['content']).decode('utf-8'))
        elif q['op'] == 'nul':
            process(q['content'])
        elif q['op'] == 'xor':
            bout = base64.b64decode(q['content'])
            bout2 = base64.b64decode(q['mask'])
            rep = len(bout) // len(bout2)
            process( ''.join([chr(a ^ b) for a,b in zip(bout, bout2*rep)]) )
        elif q['op'] == 'rev':
            process(q['content'][::-1])
        elif q['op'] == 'flag':
            print(q['content'])
        else:
            raise Exception

with open('jsonion.json', 'r') as handle:
    print(process(handle.read()))

```

which outputs:

```
1502755 1 map
1461121 1 gzip
1511839 1 b64
1133858 1 gzip
3101412 1 map
3087167 1 map
2982613 1 nul
2043890 1 nul
1574519 1 nul
1339824 1 nul
1222467 1 map
1163619 1 map
1160571 1 b64
870407 1 b64
652783 1 b64
489565 1 map
467807 1 gzip
477847 1 map
457875 1 map
438367 1 nul
424116 1 gzip
427535 1 nul
411468 1 gzip
507106 1 xor
380298 1 nul
353459 1 map
339864 1 map
326228 1 nul
304905 1 rev
294234 1 gzip
286448 1 gzip
479112 1 map
461643 1 map
446098 1 xor
331427 1 b64
248548 1 map
241961 1 map
238248 1 nul
209329 1 rev
194860 1 xor
140701 1 nul
138730 1 map
137575 1 b64
103160 1 rev
97581 1 nul
94782 1 gzip
147537 1 rev
140474 1 rev
136933 1 map
134995 1 b64
101224 1 map
100487 1 b64
75343 1 b64
56485 1 map
54670 1 rev
48207 1 nul
44966 1 xor
32491 1 gzip
37182 1 rev
37147 1 b64
27839 1 b64
20859 1 map
19929 1 xor
14324 1 gzip
20656 1 rev
20337 1 xor
15115 1 nul
12880 1 rev
11753 1 xor
8374 1 rev
8339 1 b64
6233 1 nul
6136 1 map
5914 2 xor
671 1 b64
482 1 rev
419 1 nul
378 1 xor
251 1 b64
167 1 b64
103 1 b64
57 1 flag
THIS-ISNO-THEF-LAGR-EALL-Y...
5914 2 nul
4891 1 xor
3621 1 rev
3506 1 nul
3439 1 xor
2537 1 rev
2486 1 rev
2451 1 b64
1818 1 rev
1783 1 b64
1315 1 b64
964 1 nul
881 1 rev
830 1 rev
795 1 b64
574 1 map
383 1 b64
266 1 xor
167 1 b64
103 1 b64
57 1 flag
HV17-Ip11-9CaB-JvCf-d5Nq-ffyi
None
```


**Flag**

```
HV17-Ip11-9CaB-JvCf-d5Nq-ffyi
```

## Dec 10: Just play the game  
*Haven't you ever been bored at school?*

**Challenge**  

Santa is in trouble. He's elves are busy playing TicTacToe. Beat them and help Sata to save christmas!

`nc challenges.hackvent.hacking-lab.com 1037`

**Solution**  

```
---_ ......._-_--.
(|\ /      / /| \  \
/  /     .'  -=-'   `.
/  /    .'             )
_/  /   .'        _.)   /
/ o   o        _.-' /  .'
\          _.-'    / .'*|
\______.-'//    .'.' \*|
\|  \ | //   .'.' _ |*|
`   \|//  .'.'_ _ _|*|
.  .// .'.' | _ _ \*|
\`-|\_/ /    \ _ _ \*\
`/'\__/      \ _ _ \*\
/^|            \ _ _ \*
'  `             \ _ _ \
                \_
Challenge by pyth0n33. Have fun!

I think you know the game from school...Don't you? ;)

Press enter to start the game

-------------
| * | * | * |
-------------
-------------
| * | * | * |
-------------
-------------
| * | * | * |
-------------
Make your move. Type the number of the field you want to set! (1-9)

Field:

```

Just a game of tic-tac-toe. We need to win 100 times in a row.



```python
import telnetlib
import random

def isWin(board):
    """
    GIven a board checks if it is in a winning state.
    Arguments:
          board: a list containing X,O or *.
    Return Value:
           True if board in winning state. Else False
    """
    ### check if any of the rows has winning combination
    for i in range(3):
        if len(set(board[i*3:i*3+3])) is  1 and board[i*3] is not '*': return True
    ### check if any of the Columns has winning combination
    for i in range(3):
       if (board[i] is board[i+3]) and (board[i] is  board[i+6]) and board[i] is not '*':
           return True
    ### 2,4,6 and 0,4,8 cases
    if board[0] is board[4] and board[4] is board[8] and board[4] is not '*':
        return  True
    if board[2] is board[4] and board[4] is board[6] and board[4] is not '*':
        return  True
    return False

def nextMove(board,player):
    """
    Computes the next move for a player given the current board state and also
    computes if the player will win or not.
    Arguments:
        board: list containing X,- and O
        player: one character string 'X' or 'O'
    Return Value:
        willwin: 1 if 'X' is in winning state, 0 if the game is draw and -1 if 'O' is
                    winning
        nextmove: position where the player can play the next move so that the
                         player wins or draws or delays the loss
    """
    ### when board is '*********' evaluating next move takes some time since
    ### the tree has 9! nodes. But it is clear in that state, the result is a draw
    if len(set(board)) == 1: return 0,4

    nextplayer = 'X' if player=='O' else 'O'
    if isWin(board) :
        if player is 'X': return -1,-1
        else: return 1,-1
    res_list=[] # list for appending the result
    c= board.count('*')
    if  c is 0:
        return 0,-1
    _list=[] # list for storing the indexes where '-' appears
    for i in range(len(board)):
        if board[i] == '*':
            _list.append(i)

    for i in _list:
        board[i]=player
        ret,move=nextMove(board,nextplayer)
        res_list.append(ret)
        board[i]='*'
    if player is 'X':
        maxele=max(res_list)
        return maxele,_list[res_list.index(maxele)]
    else :
        minele=min(res_list)
    return minele,_list[res_list.index(minele)]

def parseboard(s):
    board = s.split('\n')
    newboard = [ board[-9][3],board[-9][7],board[-9][11],
                 board[-6][3],board[-6][7],board[-6][11],
                 board[-3][3],board[-3][7],board[-3][11]]
    return(nextMove(newboard,'X'))

def play():
    board = tn.read_until("Make your move. Type the number of the field you want to set! (1-9)",0.1)

    if "HV17"  in board:
        print board
        return

    if "Make your move. Type the number of the field you want to set! (1-9)" in board:
        nm = parseboard(board)
        tn.write(str(nm[1]+1)+"\n")
    elif "Congratulations" in board:
        print board

    if "Press enter to start" in board:
        tn.write("\n")


# Start the game
server = "challenges.hackvent.hacking-lab.com"
port = 1037
tn = telnetlib.Telnet(server, port)

while True:
    play()
```

we run this code, and after we win game 100 we get the flag:

```
-------------
| O | X | O |
-------------
-------------
| X | X | X |
-------------
-------------
| O | O | * |
-------------
Congratulations you won! 100/100

HV17-y0ue-kn0w-7h4t-g4me-sure
Press enter to start again
```

**Flag**

```
HV17-y0ue-kn0w-7h4t-g4me-sure
```

## Dec 11: Crypt-o-Math 2.0

**Challenge**  

You bruteforced last years math lessons? This time you cant escape!

```
c = (a * b) % p
c=0x423EDCDCDCD928DD43EAEEBFE210E694303C695C20F42A27F10284215E90
p=0xB1FF12FF85A3E45F722B01BF3135ED70A552251030B114B422E390471633
b=0x88589F79D4129AB83923722E4FB6DD5E20C88FDD283AE5724F6A3697DD97
```

find `a` to get your flag.

**Solution**  

All we need to do is solve the modular equation, the `divm` function in `gmpy2` does exactly that:

```python
import codecs
import gmpy2


def int_to_text(number):
    return codecs.decode(format(number, 'x'), 'hex').decode('utf-8')


# c = (a * b) % p
c = 0x559C8077EE6C7990AF727955B744425D3CC2D4D7D0E46F015C8958B34783
p = 0x9451A6D9C114898235148F1BC7AA32901DCAE445BC3C08BA6325968F92DB
b = 0xCDB5E946CB9913616FA257418590EBCACB76FD4840FA90DE0FA78F095873

# solve the equation
a = gmpy2.divm(c, b, p)

print(int_to_text(a))
```

**Flag**

```
HV17-XtDw-0DzO-YRgB-2b2e-UWNz
```

## Dec 12: giftlogistics  
*countercomplete inmeasure*

**Challenge**  

Most passwords of Santa GiftLogistics were stolen. You find an example of the traffic for Santa's
account with password and everything. The Elves CSIRT Team detected this and made sure that
everyone changed their password.

Unfortunately this was an incomplete countermeasure. It's still possible to retrieve the
protected user profile data where you will find the flag.

[Traffic](writeupfiles/giftlogistics.pcapng)

[Link]

**Solution**  

Link to a Gift Logistics website for Santa

![](writeupfiles/dec12-ss.png)

We open the pcap in wireshark, and find a jwt token:

```
eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJzYW50YSIsImF6cCI6ImE3NWI0NzIyLTE0MWQtNGMwMC1iNjVjLTVkYzI3OTE0NmI2MCIsImlzcyI6Imh0dHA6XC9cL2NoYWxsZW5nZXMuaGFja3ZlbnQuaGFja2luZy1sYWIuY29tOjcyNDBcL2dpZnRsb2dpc3RpY3NcLyIsImV4cCI6MTUyNjkzNjkzNiwiaWF0IjoxNTExMzg0OTM2LCJqdGkiOiI4MTlmNWYzZC1hN2M3LTQ0YTktYmI5Ni0wZmQ4MmY0YjdlNzUifQ.U9Hv66701DtUb8zeqOo45JVbzC3yhKJhsQ_q7N20rdLn5-uovYzMWjhxY8I9oPQkv3s5iDDsx1GIUbnOkC8l__oj_uqptG0BPbRfD2K1blKpbXQt3yxD1pB63aHw5LRAp10ia0MNe8_eo-qzi9d58CVYY_XOtTRH8Ic_tP5lpXVaImi8miYFY2XqR1TuFM-cUjIMUYT9Ik8rwZAEbLO_1UAWPuQUpi0_Z6N0r3hKoIRSlknmmg8A5PunL2I0qFyICUm0cqb4fieBZ34R4117LmyQY_XvzKogIaLegDIgbp22hTGHPAdziEloYYaP5uc_aEnfo0eNvY7QLPNy1dDs-Q
```

with an expiry date far in the future according to https://jwt.io/

We also find a reference to an openID configuration file (from tcp stream 20)

```
{
    "request_parameter_supported": true,
    "claims_parameter_supported": false,
    "introspection_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/introspect",
    "scopes_supported": ["openid", "profile", "email", "address", "phone", "offline_access"],
    "issuer": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/",
    "userinfo_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
    "id_token_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
    "authorization_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/authorize",
    "service_documentation": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
    "request_object_encryption_enc_values_supported": ["A256CBC+HS512", "A256GCM", "A192GCM", "A128GCM", "A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128CBC+HS256"],
    "userinfo_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
    "claims_supported": ["sub", "name", "preferred_username", "given_name", "family_name", "middle_name", "nickname", "profile", "picture", "website", "gender", "zoneinfo", "locale", "updated_at", "birthdate", "email", "email_verified", "phone_number", "phone_number_verified", "address"],
    "claim_types_supported": ["normal"],
    "op_policy_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
    "token_endpoint_auth_methods_supported": ["client_secret_post", "client_secret_basic", "client_secret_jwt", "private_key_jwt", "none"],
    "token_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/token",
    "response_types_supported": ["code", "token"],
    "request_uri_parameter_supported": false,
    "userinfo_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"],
    "grant_types_supported": ["authorization_code", "implicit", "urn:ietf:params:oauth:grant-type:jwt-bearer", "client_credentials", "urn:ietf:params:oauth:grant_type:redelegate"],
    "revocation_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/revoke",
    "userinfo_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/userinfo",
    "token_endpoint_auth_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
    "op_tos_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/about",
    "require_request_uri_registration": false,
    "id_token_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"],
    "jwks_uri": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/jwk",
    "subject_types_supported": ["public", "pairwise"],
    "id_token_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512", "none"],
    "registration_endpoint": "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/register",
    "request_object_signing_alg_values_supported": ["HS256", "HS384", "HS512", "RS256", "RS384", "RS512", "ES256", "ES384", "ES512", "PS256", "PS384", "PS512"],
    "request_object_encryption_alg_values_supported": ["RSA-OAEP", "RSA-OAEP-256", "RSA1_5"]
}
```

Description says we can find our flag in protected user profile data, and after some poking around we find:

```bash
curl "http://challenges.hackvent.hacking-lab.com:7240/giftlogistics/userinfo?access_token=eyJraWQiOiJyc2ExIiwi
YWxnIjoiUlMyNTYifQ.eyJzdWIiOiJzYW50YSIsImF6cCI6ImE3NWI0NzIyLTE0MWQtNGMwMC1iNjVjLTVkYzI3OTE0NmI2MCIsImlzcyI6Imh
0dHA6XC9cL2NoYWxsZW5nZXMuaGFja3ZlbnQuaGFja2luZy1sYWIuY29tOjcyNDBcL2dpZnRsb2dpc3RpY3NcLyIsImV4cCI6MTUyNjkzNjkzN
iwiaWF0IjoxNTExMzg0OTM2LCJqdGkiOiI4MTlmNWYzZC1hN2M3LTQ0YTktYmI5Ni0wZmQ4MmY0YjdlNzUifQ.U9Hv66701DtUb8zeqOo45JVb
zC3yhKJhsQ_q7N20rdLn5-uovYzMWjhxY8I9oPQkv3s5iDDsx1GIUbnOkC8l__oj_uqptG0BPbRfD2K1blKpbXQt3yxD1pB63aHw5LRAp10ia0
MNe8_eo-qzi9d58CVYY_XOtTRH8Ic_tP5lpXVaImi8miYFY2XqR1TuFM-cUjIMUYT9Ik8rwZAEbLO_1UAWPuQUpi0_Z6N0r3hKoIRSlknmmg8A
5PunL2I0qFyICUm0cqb4fieBZ34R4117LmyQY_XvzKogIaLegDIgbp22hTGHPAdziEloYYaP5uc_aEnfo0eNvY7QLPNy1dDs-Q" -i   
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Access-Control-Allow-Origin: *
Content-Type: application/json;charset=ISO-8859-1
Content-Language: en
Content-Length: 98
Date: Mon, 11 Dec 2017 23:34:09 GMT

{"sub":"HV17-eUOF-mPJY-ruga-fUFq-EhOx","name":"Reginald Thumblewood","preferred_username":"santa"}
```

**Flag**

```
HV17-eUOF-mPJY-ruga-fUFq-EhOx
```

## Dec 13: muffin_asm  
*As M. said, kind of a different architecture!*

**Challenge**  

ohai \o/

How about some custom asm to obsfucate the codez?

[Link](writeupfiles/muffin_asm.py)

**Solution**  

It is a python program, running a simple asm-like program:

```python
#!/usr/bin/env python

import sys, struct

ip, r, f = 0x00, [0x00]*4, [False]

def _add(r1, r2): r[r1] = ((r[r1] + r[r2]) & 0xFF)
def _addv(r1, v): r[r1] = ((r[r1] + v) & 0xFF)
def _sub(r1, r2): r[r1] = ((r[r1] - r[r2]) & 0xFF)
def _subv(r1, v): r[r1] = ((r[r1] - v) & 0xFF)
def _xor(r1, r2): r[r1] = (r[r1] ^ r[r2])
def _xorv(r1, v): r[r1] = (r[r1] ^ v)
def _cmp(r1, r2): f[0] = (r[r1] == r[r2])
def _cmpv(r1, v): f[0] = (r[r1] == v)
def _je(o): global ip; ip = (o if f[0] else ip)
def _jne(o): global ip; ip = (o if not f[0] else ip)
def _wchr(r1): sys.stdout.write(chr(r[r1]))
def _rchr(r1): r[r1] = ord(sys.stdin.read(1))

ins = [_add, _addv, _sub, _subv, _xor, _xorv, _cmp, _cmpv, _je, _jne, _wchr, _rchr]

def run(codez):
    global ip
    while ip < len(codez):
        c_ins = ins[ord(codez[ip])]
        if c_ins in [_je, _jne]:
            old_ip = ip
            c_ins(struct.unpack('<I', codez[(ip+1):(ip+5)])[0])
            if old_ip == ip: ip += 5
            continue
        num_of_args = c_ins.func_code.co_argcount
        if num_of_args == 0: c_ins()
        elif num_of_args == 1: c_ins(ord(codez[ip+1]))
        else: c_ins(ord(codez[ip+1]), ord(codez[ip+2]))
        ip += (1 + num_of_args)

print '[ muffin asm ]'
print 'muffinx: Did you ever codez asm?'
run('\x04\x00\x00\x04\x01\x01\x04\x02\x02\x04\x03\x03\x05\x02\xbd\x00\x02\x00\x00 [..] \x01\x03\x00\x01')
```

Interesting, ok, lets run it.. We are asked for a flag and are told "nope" when we enter it incorrectly:

```bash
$ python muffin_asm.py
[ muffin asm ]
muffinx: Did you ever codez asm?
<< flag_getter v1.0 >>
ohai, gimmeh flag: tardis
[-] nope!
```

The program likely checks the string one character at a time with the `cmp` function, so we simply check what it is
checking against by changing one line:

```python
# def _cmp(r1, r2): f[0] = (r[r1] == r[r2])  # old
def _cmp(r1, r2): sys.stdout.write(chr(r[r2])); f[0] = (r[r2] == r[r2]);
```

Now we run the program, and give it a random string at least as long as the flag, and it just prints out the flag for us :D

```
$ python muffin_asm.py
[ muffin asm ]
muffinx: Did you ever codez asm?
<< flag_getter v1.0 >>
ohai, gimmeh flag: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
HV17-mUff!n-4sm-!s-cr4zY[+] valid! by muffinx :D if you liked the challenge, troll me @ twitter.com/muffiniks =D
```

Whoo! we have our flag `HV17-mUff!n-4sm-!s-cr4zY`


------
NOTE: below is my initial, overly complex solution:

We could try to reverse the program, but if the program checks the flag one character
at a time and exits upon the first mismatch, we might be able to determine the flag
by brute-forcing each subsequent character and determine the correct one from the number
of cycles it takes before failure.

We adjust the program a bit to count the number of instructions and build the flag
one character at a time:


```python
#!/usr/bin/env python

import sys, struct, string

ip, r, f = 0x00, [0x00]*4, [False]

def _add(r1, r2): r[r1] = ((r[r1] + r[r2]) & 0xFF)
def _addv(r1, v): r[r1] = ((r[r1] + v) & 0xFF)
def _sub(r1, r2): r[r1] = ((r[r1] - r[r2]) & 0xFF)
def _subv(r1, v): r[r1] = ((r[r1] - v) & 0xFF)
def _xor(r1, r2): r[r1] = (r[r1] ^ r[r2])
def _xorv(r1, v): r[r1] = (r[r1] ^ v)
def _cmp(r1, r2): f[0] = (r[r1] == r[r2])
def _cmpv(r1, v): f[0] = (r[r1] == v)
def _je(o): global ip; ip = (o if f[0] else ip)
def _jne(o): global ip; ip = (o if not f[0] else ip)
def _wchr(r1): pass #sys.stdout.write(chr(r[r1]))
def _rchr(r1):
    #r[r1] = ord(sys.stdin.read(1))
    global nextchar
    try:
        r[r1] = ord(myinput[nextchar])
    except:
        pass
    nextchar += 1

ins = [_add, _addv, _sub, _subv, _xor, _xorv, _cmp, _cmpv, _je, _jne, _wchr, _rchr]


def run(codez):
    global ip
    instrcount = 0
    while ip < len(codez):
        instrcount += 1
        c_ins = ins[ord(codez[ip])]
        if c_ins in [_je, _jne]:
            old_ip = ip
            c_ins(struct.unpack('<I', codez[(ip+1):(ip+5)])[0])
            if old_ip == ip: ip += 5
            continue
        num_of_args = c_ins.func_code.co_argcount
        if num_of_args == 0: c_ins()
        elif num_of_args == 1:
            c_ins(ord(codez[ip+1])),
        else:
            c_ins(ord(codez[ip+1]), ord(codez[ip+2]))
        ip += (1 + num_of_args)
    return instrcount


print '[ muffin asm ]'
print 'muffinx: Did you ever codez asm?'
program = '\x04\x00\x00\x04\x01\x01\x04\x02\x02\x04\x03\x03\x05\x02\xbd\x00\x02\x00\x00 [..] \x01\x03\x00\x01'

# find the flag by looking at which subsequent letter increases the total instruction count
# of the program
print 'Looking for the flag..'
maxinstrcount = 1520 #number of instructions when no correct characters
flag = ''

for i in range(0,29):
    for c in string.letters+string.digits+string.punctuation:
        myinput = flag + c
        ip = 0
        nextchar = 0
        numinstr = run(program)
        if numinstr > maxinstrcount:
            flag += c
            maxinstrcount = numinstr
            break

print flag
```

[full program](writeupfiles/muffin_asm_bf.py)

This outputs:

```bash
$ python muffin_asm_bf.py
[ muffin asm ]
muffinx: Did you ever codez asm?
Looking for the flag..
HV17-mUff!n-4sm-!s-cr4zY
```

-----

**Flag**

```
HV17-mUff!n-4sm-!s-cr4zY
```

## Dec 14: Happy Cryptmas  

**Challenge**  

todays gift was encrypted with the attached program. try to unbox your xmas present.

```
Flag: 7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C
```

[Executable](writeupfiles/hackvent)

**Solution**  

This is a Mach-O executable which we disassemble the file with Hopper:

```
================ B E G I N N I N G   O F   P R O C E D U R E ================



                                       ;
                                       ; Section __text
                                       ;
                                       ; Range 0x100000cc0 - 0x100000e66 (422 bytes)
                                       ; File offset 3264 (422 bytes)
                                       ; Flags : 0x80000400
                                       ;
0000000100000cc1         mov        rbp, rsp
0000000100000cc4         sub        rsp, 0xc0
0000000100000ccb         mov        rax, qword [ds:imp___got____stack_chk_guard] ; imp___got____stack_chk_guard
0000000100000cd2         mov        rax, qword [ds:rax]
0000000100000cd5         mov        qword [ss:rbp+var_8], rax
0000000100000cd9         mov        dword [ss:rbp+var_64], 0x0
0000000100000ce0         mov        dword [ss:rbp+var_68], edi
0000000100000ce3         mov        qword [ss:rbp+var_70], rsi
0000000100000ce7         cmp        dword [ss:rbp+var_68], 0x1
0000000100000ceb         jne        0x100000cfd

0000000100000cf1         mov        dword [ss:rbp+var_64], 0x0
0000000100000cf8         jmp        0x100000e32

0000000100000cfd         lea        rdi, qword [ss:rbp+var_50]                  ; XREF=_main+43
0000000100000d01         call       imp___stubs____gmpz_init
0000000100000d06         lea        rdi, qword [ss:rbp+var_60]
0000000100000d0a         call       imp___stubs____gmpz_init
0000000100000d0f         lea        rsi, qword [ds:0x100000f18]                 ; "F66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51"
0000000100000d16         mov        edx, 0x10
0000000100000d1b         lea        rdi, qword [ss:rbp+var_20]
0000000100000d1f         call       imp___stubs____gmpz_init_set_str
0000000100000d24         lea        rsi, qword [ds:0x100000f99]                 ; "65537"
0000000100000d2b         mov        edx, 0xa
0000000100000d30         lea        rdi, qword [ss:rbp+var_40]
0000000100000d34         mov        dword [ss:rbp+var_74], eax
0000000100000d37         call       imp___stubs____gmpz_init_set_str
0000000100000d3c         mov        edx, 0x1
0000000100000d41         mov        ecx, 0x1
0000000100000d46         xor        r8d, r8d
0000000100000d49         xor        r9d, r9d
0000000100000d4c         lea        rdi, qword [ss:rbp+var_50]
0000000100000d50         mov        rsi, qword [ss:rbp+var_70]
0000000100000d54         mov        rsi, qword [ds:rsi+0x8]
0000000100000d58         mov        qword [ss:rbp+var_80], rdi
0000000100000d5c         mov        rdi, rsi                                    ; argument "s" for method imp___stubs__strlen
0000000100000d5f         mov        dword [ss:rbp+var_84], eax
0000000100000d65         mov        dword [ss:rbp+var_88], edx
0000000100000d6b         mov        qword [ss:rbp+var_90], rcx
0000000100000d72         mov        dword [ss:rbp+var_94], r8d
0000000100000d79         mov        qword [ss:rbp+var_A0], r9
0000000100000d80         call       imp___stubs__strlen
0000000100000d85         mov        rcx, qword [ss:rbp+var_70]
0000000100000d89         mov        rcx, qword [ds:rcx+0x8]
0000000100000d8d         mov        rdi, qword [ss:rbp+var_80]
0000000100000d91         mov        rsi, rax
0000000100000d94         mov        edx, dword [ss:rbp+var_88]
0000000100000d9a         mov        rax, qword [ss:rbp+var_90]
0000000100000da1         mov        qword [ss:rbp+var_A8], rcx
0000000100000da8         mov        rcx, rax
0000000100000dab         mov        r8d, dword [ss:rbp+var_94]
0000000100000db2         mov        r9, qword [ss:rbp+var_A0]
0000000100000db9         mov        r10, qword [ss:rbp+var_A8]
0000000100000dc0         mov        qword [ss:rsp], r10
0000000100000dc4         call       imp___stubs____gmpz_import
0000000100000dc9         lea        rsi, qword [ss:rbp+var_20]
0000000100000dcd         lea        rdi, qword [ss:rbp+var_50]
0000000100000dd1         call       imp___stubs____gmpz_cmp
0000000100000dd6         cmp        eax, 0x0
0000000100000dd9         jle        0x100000de4

0000000100000ddf         call       imp___stubs__abort

0000000100000de4         lea        rcx, qword [ss:rbp+var_20]                  ; XREF=_main+281
0000000100000de8         lea        rdx, qword [ss:rbp+var_40]
0000000100000dec         lea        rsi, qword [ss:rbp+var_50]
0000000100000df0         lea        rdi, qword [ss:rbp+var_60]
0000000100000df4         call       imp___stubs____gmpz_powm
0000000100000df9         lea        rdi, qword [ds:0x100000f9f]                 ; "Crypted: %ZX\\n"
0000000100000e00         lea        rsi, qword [ss:rbp+var_60]
0000000100000e04         mov        al, 0x0
0000000100000e06         call       imp___stubs____gmp_printf
0000000100000e0b         xor        r8d, r8d
0000000100000e0e         lea        rcx, qword [ss:rbp+var_40]
0000000100000e12         lea        rdx, qword [ss:rbp+var_20]
0000000100000e16         lea        rsi, qword [ss:rbp+var_60]
0000000100000e1a         lea        rdi, qword [ss:rbp+var_50]
0000000100000e1e         mov        dword [ss:rbp+var_AC], eax
0000000100000e24         mov        al, 0x0
0000000100000e26         call       imp___stubs____gmpz_clears
0000000100000e2b         mov        dword [ss:rbp+var_64], 0x0

0000000100000e32         mov        eax, dword [ss:rbp+var_64]                  ; XREF=_main+56
0000000100000e35         mov        rcx, qword [ds:imp___got____stack_chk_guard] ; imp___got____stack_chk_guard
0000000100000e3c         mov        rcx, qword [ds:rcx]
0000000100000e3f         mov        rdx, qword [ss:rbp+var_8]
0000000100000e43         cmp        rcx, rdx
0000000100000e46         mov        dword [ss:rbp+var_B0], eax
0000000100000e4c         jne        0x100000e61

0000000100000e52         mov        eax, dword [ss:rbp+var_B0]
0000000100000e58         add        rsp, 0xc0
0000000100000e5f         pop        rbp
0000000100000e60         ret        

0000000100000e61         call       imp___stubs____stack_chk_fail               ; XREF=_main+396
                        ; endp

```

pseudocode generated by Hopper disassembler:

```js
function _main {
    var_8 = **__stack_chk_guard;
    var_64 = 0x0;
    var_68 = LODWORD(arg0);
    var_70 = arg1;
    if (var_68 != 0x1) goto loc_100000cfd;
    goto loc_100000cf1;

loc_100000cfd:
    __gmpz_init();
    __gmpz_init();
    rax = __gmpz_init_set_str();
    var_74 = LODWORD(rax);
    rax = __gmpz_init_set_str();
    rsi = *(var_70 + 0x8);
    var_80 = var_50;
    var_84 = LODWORD(rax);
    var_88 = LODWORD(0x1);
    var_90 = 0x1;
    var_94 = LODWORD(0x0);
    var_A0 = 0x0;
    rax = strlen(rsi);
    rcx = *(var_70 + 0x8);
    var_A8 = rcx;
    __gmpz_import();
    if (LODWORD(__gmpz_cmp()) <= 0x0) goto loc_100000de4;
    goto loc_100000ddf;

loc_100000de4:
    __gmpz_powm();
    rax = __gmp_printf();
    var_AC = LODWORD(rax);
    __gmpz_clears();
    var_64 = 0x0;

loc_100000e32:
    rcx = *__stack_chk_guard;
    rcx = *rcx;
    var_B0 = LODWORD(var_64);
    if (rcx == var_8) {
            LODWORD(rax) = var_B0;
            return rax;
    }
    else {
            rax = __stack_chk_fail();
    }
    return rax;

loc_100000ddf:
    rax = abort();

loc_100000cf1:
    var_64 = 0x0;
    goto loc_100000e32;
}
```

or, more clearly:

```js
int _main(int arg0, int arg1) {
    var_8 = **___stack_chk_guard;
    var_70 = arg1;
    if (arg0 != 0x1) goto loc_100000cfd;

loc_100000e32:
    var_B0 = 0x0;
    if (**___stack_chk_guard == var_8) {
            rax = var_B0;
    }
    else {
            rax = __stack_chk_fail();
    }
    return rax;

loc_100000cfd:
    __gmpz_init(&var_50);
    __gmpz_init(&var_60);
    __gmpz_init_set_str(&var_20, "F66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51", 0x10);
    __gmpz_init_set_str(&var_40, "65537", 0xa);
    __gmpz_import(&var_50, strlen(*(var_70 + 0x8)), 0x1, 0x1, 0x0, 0x0, *(var_70 + 0x8));
    if (__gmpz_cmp(&var_50, &var_20) <= 0x0) goto loc_100000de4;

loc_100000ddf:
    rax = abort();
    return rax;

loc_100000de4:
    __gmpz_powm(&var_60, &var_50, &var_40, &var_20);
    __gmp_printf("Crypted: %ZX\n", &var_60);
    __gmpz_clears(&var_50, &var_60, &var_20, &var_40, 0x0);
    goto loc_100000e32;
}
```

This looks like RSA, with the commonly used value for exponent `e=65537` and `N=0xF66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51`

We get the prime factors of `N` (`p` and `q`) using [factordb](http://factordb.com)

```python
#!/usr/bin/python3
from Crypto.PublicKey import RSA
import gmpy2

def int2Text(number, size):
    text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
    return text.lstrip("\x00")

N = 0xF66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51
C = 0x7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C
p = 18132985757038135691
q = 711781150511215724435363874088486910075853913118425049972912826148221297483065007967192431613422409694054064755658564243721555532535827
e = 65537
r = (p-1)*(q-1)
d = int(gmpy2.divm(1, e, r))
rsa = RSA.construct((N,e,d,p,q))
pt = rsa.decrypt(C)

print(int2Text(pt,1000))
```

this prints our flag.


**Flag**

```
HV17-5BMu-mgD0-G7Su-EYsp-Mg0b
```

## Dec 15: Unsafe Gallery  
*See pictures you shouldn't see*

**Challenge**  


The List of all Users of the Unsafe Gallery was leaked (See account list).
With this list the URL to each gallery can be constructed. E.g. you find Danny's gallery here.

Now find the flag in Thumper's gallery.

[account list](writeupfiles/accounts.csv)

like to Danny's gallery: http://challenges.hackvent.hacking-lab.com:3958/gallery/bncqYuhdQVey9omKA6tAFi4rep1FDRtD4H8ftWiw

**Solution**  

The file is a csv file with user information:

```
id,prename,name,address,zip,city,email,crmId,memberType,pictureCount,galleryCount,mbUsed,logCorrelationId,advertisingId,state
0,Ethan,McCullough,1259 Arborwood Circle,27299,Manchester,Ethan.McCullough@mccullough.com,12739789,silver,26,1,77,19591907,1,disabled
1,Vivian,Parsons,1222 Basin Way,49195,Byromville,Vivian.Parsons@gmx.com,24818112,platin,25,1,71,14903484,7,active
2,Kaitlyn,Wells,1547 Kinzel Station,44404,Gibson,K.Wells@sunflower.org,2024240,platin,28,1,77,98402385,14,active
3,Dakota,Hayes,1709 Akron Trail,3063,Morrow,Dakota.Hayes@sunflower.org,98938964,gold,29,1,76,60973407,21,active
[..]
```

The gallery ID could be some hash of this information, lets extract all the Dannys and Thumpers:

```bash
$ cat accounts.csv | grep -i danny > danny.csv
$ cat accounts.csv | grep -i thumper > thumper.csv
```

There are several Dannys in the list:

```
28,Danny,Gomez,446 Richards Ridge,67398,Dudley,Danny.Gomez@sunflower.org,4334991,silver,16,1,47,37880231,139,active
981,Danny,Head,1783 Layton Crescent,10838,Hartwell,Danny.Head@head.com,8090609,platin,7,1,17,96695095,3988,active
1411,Danny,Brewer,827 Emily Way,85285,Ochlocknee,Danny.Brewer@brewer.com,64452336,platin,17,1,46,58734130,5718,active
2352,Danny,Snider,1642 Warner Ridge,56674,Sycamore,D.Snider@gmail.com,58043840,platin,23,1,68,2417293,9466,active
2406,Danny,Douglas,1497 Rondayle Ridge,27656,Gumbranch,Danny.Douglas@douglas.com,97224940,platin,10,1,28,78423462,9693,active

[..]

43038,Danny,Mack,1441 Brill Drive,59015,Osterfield,Danny.Mack@mack.com,56888336,platin,15,1,44,72043133,172729,disabled
43653,Danny,Parrish,509 Jackson Ln,24360,Mcintosh,Danny.Parrish@mail.com,91465682,silver,19,1,48,52326262,175200,active
43685,Danny,Hardy,1490 Wall Boulevard,83314,Twin Peaks,Danny.Hardy@mail.com,58905278,gold,15,1,41,35615663,175324,disabled
43736,Danny,Ayala,853 Marlo Heights,60736,Fargo,Danny.Ayala@ayala.com,73601184,gold,28,1,73,46364233,175558,active
```

And one of these would appear to translate to `bncqYuhdQVey9omKA6tAFi4rep1FDRtD4H8ftWiw` as the Gallery ID in some way.

If we go to `/gallery` we get the message `Please use the link you got in in the registration email or register first`
so it would make sense that the hash is based only on information present at registration time, email would make most sense

The hash looks base64 encode, if we decode it is `0x6e772a62e85d4157b2f6898a03ab40162e2b7a9d450d1b43e07f1fb568b0` in hex, so
let's see if one of the email addresses translates to this when hashed.. the hash is 60 hex digits long, hmm, an odd length..

After a LOT of trial and error, we discover that while none of the email addresses result in this hash, we see that one almost does
when SHA-256 hashed:

```python
>>> import hashlib
>>> email = 'Danny.Dixon@sunflower.org'
>>> m = hashlib.sha256()
>>> m.update(email)
>>> m.hexdigest()
'6e772a62e85d4157b2f6898a03ab40162e2b7a9d7e143f91b43e07ffc7ed5a2c'
>>> import base64
>>> base64.b64encode(m.digest())
'bncqYuhdQVey9omKA6tAFi4rep1+FD+RtD4H/8ftWiw='
```

Aha! so it is base64 encoded SHA-256 hash, but with non-alphanumeric characters removed..

Now that we know the recipe, let's repeat it for all the Thumpers in the list and see we can find
flags in their galleries.

```python
import base64
import csv
import hashlib
import requests


infile = 'thumper.csv'

with open(infile, 'rb') as csvfile:
     reader = csv.reader(csvfile)
     userinfo = list(reader)

for user in userinfo:
    email = user[6]

    # sha
    m = hashlib.sha256()
    m.update(email)
    sha = m.digest()

    # b64
    b = base64.b64encode(sha)
    # remove non-alphanumeric characters
    b2 = b.replace('=','').replace('+','').replace('/','')

    # test link
    url = 'http://challenges.hackvent.hacking-lab.com:3958/gallery/'+b2
    r = requests.get(url)
    if 'HV17' in r.text:
        print(r.text)
```

This finds us a hit at http://challenges.hackvent.hacking-lab.com:3958/gallery/37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw :

```html
[..]

<div class="col-sm-6 col-md-4">
    <div class="thumbnail">
        <a class="lightbox" href="37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw//timages/flag.jpg">
            <img src="37qKYVMANnIdJ2V2EDberGmMz9JzS1pfRLVWaIKuBDw//timages/flag.jpg" alt="C'YOU" />
        </a>
        <div class="caption">
            <h3>C'YOU</h3>
            <p>See you next spring at @HackyEaster. I count on you. HV17-el2S-0Td5-XcFi-6Wjg-J5aB</p>
        </div>
    </div>
</div>

[..]
```

![](writeupfiles/day15.png)

**Flag**

```
HV17-el2S-0Td5-XcFi-6Wjg-J5aB
```

## Dec 16: Try to escape ...  
*... from the snake cage*

**Challenge**  

Santa programmed a secure jail to give his elves access from remote. Sadly the jail is not as secure as expected.

`nc challenges.hackvent.hacking-lab.com 1034`

**Solution**  

```
$ nc challenges.hackvent.hacking-lab.com 1034
                        _____
                    .-'`     '.
                 __/  __       \\
                /  \ /  \       |    ___
               | /`\| /`\|      | .-'  /^\/^\\
               | \(/| \(/|      |/     |) |)|
              .-\__/ \__/       |      \_/\_/__..._
      _...---'-.                /   _              '.
     /,      ,             \   '|  `\                \\
    | ))     ))           /`|   \    `.       /)  /) |
    | `      `          .'       |     `-._         /
    \                 .'         |     ,_  `--....-'
     `.           __.' ,         |     / /`'''`
       `'-.____.-' /  /,         |    / /
           `. `-.-` .'  \        /   / |
             `-.__.'|    \      |   |  |-.
                _.._|     |     /   |  |  `'.
          .-''``    |     |     |   /  |     `-.
       .'`         /      /     /  |   |        '.
     /`           /      /     |   /   |\         \\
    /            |      |      |   |   /\          |
   ||            |      /      |   /     '.        |
   |\            \      |      /   |       '.      /
   \ `.           '.    /      |    \        '---'/
    \  '.           `-./        \    '.          /
     '.  `'.            `-._     '.__  '-._____.'--'''''--.
       '-.  `'--._          `.__     `';----`              \\
          `-.     `-.          `."'```                     ;
             `'-..,_ `-.         `'-.                     /
                    '.  '.           '.                 .'

Challenge by pyth0n33. Have fun!



The flag is stored super secure in the function SANTA!
>>> a =
```

Looks like a python jail. We poke around a bit

```
>>> a = SANTA()
name 'santa' is not defined
>>> a = 2
>>> a = print(a)
2
>>> a = 1
Denied
>>> a = eval('2+2')
>>> print(a)
4
>>> a = 'b'
Denied
>>> a = 'a'
>>> a =
```

..seems like certain characters are forbidden

possibly useful [link](https://wapiflapi.github.io/2013/04/22/plaidctf-pyjail-story-of-pythons-escape/)

We try inputting all printables to see which are allowed and which aren't:

```
allowed:
['0', '1', '2', '3', '7', '9', 'a', 'c', 'd', 'e', 'i', 'l', 'n', 'o', 'p', 'r',
's', 't', 'v', 'A', 'C', 'D', 'E', 'I', 'L', 'N', 'O', 'P', 'R', 'S', 'T', 'V',
'_','"', "'", '(', ')', '+', '.', '[', ']', '\n', '\r']
disallowed:
['4', '5', '6', '8', 'b', 'f', 'g', 'h', 'j', 'k', 'm', 'q', 'u', 'w', 'x', 'y',
'z', 'B', 'F', 'G', 'H', 'J', 'K', 'M', 'Q', 'U', 'W', 'X', 'Y', 'Z', '!', '#',
'$', '%', '&', '*', ',', '-', '/', ':', ';', '<', '=', '>', '?', '@', '\\', '^',
'`', '{', '|', '}', '~', ' ', '\t', '\x0b', '\x0c']
```

functions we can use:

```
eval()
all()
repr()
print()


disallowed:
['abs', 'any', 'apply', 'basestring', 'bin', 'bool', 'buffer', 'bytearray', 'bytes', 'callable', 'chr',
'classmethod', 'cmp', 'compile', 'complex', 'copyright', 'divmod', 'enumerate', 'execfile', 'exit',
'file', 'filter', 'float', 'format', 'frozenset', 'getattr', 'globals', 'hasattr', 'hash', 'help',
'hex', 'input', 'issubclass', 'long', 'map', 'max',  'memoryview', 'min', 'next', 'object', 'open',
'pow', 'property', 'quit', 'range', 'raw_input', 'reduce', 'round', 'staticmethod', 'sum', 'super',
'tuple', 'type', 'unichr', 'unicode', 'xrange', 'zip']
undefined:
['coerce', 'credits', 'delattr', 'dict', 'dir', 'id', 'int', 'intern', 'isinstance', 'iter', 'len',
'license', 'list', 'locals', 'oct', 'ord', 'reload', 'reversed', 'set', 'setattr', 'slice', 'sorted',
'str', 'vars']
other:
['all', 'eval', 'print', 'repr']

```


**Flag**

```
HV17-
```

## Dec 17: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 18: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 19: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 20: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 21: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 22: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 23: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```

## Dec 24: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
HV17-
```
