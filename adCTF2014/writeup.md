# Overview

```
Date      Title             Category      Flag
---       ----------------  ----------    ----------------------------------------
Dec. 1    Warmup            misc          ADCTF_W3LC0M3_70_ADC7F2014
Dec. 2    Alert man         web           ADCTF_I_4M_4l3Rt_M4n
Dec. 3    Listen            misc          ADCTF_SOUNDS_GOOD
Dec. 4    Easyone           reversing     ADCTF_7H15_15_7oO_345y_FOR_M3
Dec. 5    Shooting          web           ADCTF_1mP05518L3_STG
Dec. 6    Paths             reversing     ADCTF_G0_go_5hOr7E57_PaTh
Dec. 7    Reader            PPC           ADCTF_4R3_y0U_B4rC0d3_R34D3r
Dec. 8    Rotate            crypto        ADCTF_TR0t4T3_f4C3
Dec. 9    QRgarden          PPC           ADCTF_re4d1n9_Qrc0de_15_FuN
Dec. 10   XOR               crypto        ADCTF_51mpl3_X0R_R3v3r51n6
Dec. 11   Blacklist         web           ADCTF_d0_NoT_Us3_FUcK1N_8l4ckL1sT
Dec. 12   Bruteforce        reversing     ADCTF_179424673
Dec. 13   Loginpage         web           ADCTF_L0v3ry_p3rl_c0N73x7
Dec. 14   Secret table      web           ADCTF_ERR0r_hELP5_8L1nd_5Ql1
Dec. 15   Password checker  reversing     ADCTF_0BFusC4Ti0n_PeRl_In_bIN4Ry
Dec. 16   Blind shell       pwnable       ADCTF_y0u_C4N_533_y0U_c4N_br34tH
Dec. 17   Oh my scanf       pwnable       ADCTF_Sc4NF_IS_PRe77Y_niCE
Dec. 18   Strangekey        crypto        ADCTF_W13n3r5_4774ck_15_V3RY_fun
Dec. 19   Guesskey          reversing     ADCTF_G00dGu3SS1ng
Dec. 20   Easypwn           pwnable       ADCTF_175_345y_7o_cON7ROL_5Y5c4LL
Dec. 21   OTP               web           ADCTF_all_Y0ur_5CH3ma_ar3_83L0N9_t0_u5
Dec. 22   VTFregexp         reversing     ADCTF_l091C4L_r39Ul4r_3xpR3ss10N
Dec. 23   Shellcodeme       pwnable       ADCTF_I_l0v3_tH15_4W350M3_m15T4K3
Dec. 24   Regexp quiz       mixed         ADCTF_Regul4R_eXPRe5510n_R0CkS
Dec. 25   Xmas              bonus         ADCTF_m3RRy_ChR157m42
```

Below are the writeups for each day (WIP)


# December 1st: Warmup


**Category**

Misc

**Hint**

*Today is warmup.*

**Challenge**

```
0x41444354465f57334c43304d335f37305f414443374632303134
```

**Solution**

Simple hex-to-ascii conversion gives flag

**Flag**

```
ADCTF_W3LC0M3_70_ADC7F2014
```


# December 2nd: Alert Man


**Category**

Web

**Hint**

*Can you alert('XSS')?*

**Challenge**

weblink: [http://adctf2014.katsudon.org/dat/AlSDUDdTMssNKajr/alert_man.html](http://adctf2014.katsudon.org/dat/AlSDUDdTMssNKajr/alert_man.html)

This leads to a website where user can enter text in a box which is printed to the screen in a list.


**Source**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>alert man</title>
</head>
<body>
    <h1>alert man</h1>
    <form id="form">
        <input type="text" id="text" />
        <input type="submit" value="tweet" />
    </form>
    <p>Your tweet:</p>
    <ul id="tweets"></ul>
    <script>
function appendTweet(tweet) {
    t = tweet.replace(/['"]/g, '');
    li = document.createElement('li');
    li.innerHTML = t;
    document.getElementById('tweets').appendChild(li);
};
appendTweet('here is your tweet!');
_='var :=["HVHD>B>D91LI01ZDS7$LVDB0*89V0D&6Z4I4*H3#NDB31&49&15&2&W9&3&9492832W5??5613W780D$S5HC7*4BB33*674DE798SCY3BSCFYD#DBOPQ@X969963O6667@4A782/0C@/85QOM71Q%X53@TE141375H0833O4@DA5/39E%G84GDGC2N83%@7C78XA%X%TDG381%G3T4081PP447/6M0!9!70!29!1!4C27%PQ13"];eval(function (UJ2J3J4J5J6){while(3--){if(<{U=U[	6]]( new RegExp(	4]+3+	4],	5]),<KK;return UK(	0],10,49,	3][	2]](	1])));\\x500N53_0x3268xC76	:[22B435C5I56NM9D84","E1!9#D6V0$7%2&*E.6/2:_0x2915<4[3])>0EM?7W8@9TFG4H0I9J,K;} L.2M8N7O3P0QCS$TAU1VW5X6YE$ZI.';for(Y in $='ZYXWVUTSQPONMLKJIHGA@?><:/.*&%$#!	')with(_.split($[Y]))_=join(pop());eval(_);
document.getElementById('form').onsubmit = function() {
    tweet = document.getElementById('text').value;
    appendTweet(tweet);
    return false;
};
    </script>
</body>
</html>

```


**Solution**

It appears we need to inject an *alert('XSS')* statement.

We can trigger a simple alert by entering 

```
<img src=/ onerror=alert(1)>
```

Quotes are filtered, but we can use escape characters:

```
<img src=/ onerror=alert(&#39;XSS&#39;)>
```

A new tweet will appear in the list:
```
the flag is: ADCTF_I_4M_4l3Rt_M4n
```

**Flag**

```
ADCTF_I_4M_4l3Rt_M4n
```

# December 3rd: Listen

**Category**

Misc

**Hint**

*I couldn't listen it, can you?*


**Challenge**

We are given a .wav file: [listen.wav](files/listen.wav)


**Solution**

Opening the file in audacity reveals a normal sound pattern, but the length is calculated to be 42 hours.

Opening the file as raw format, the time corrects to a 3.7 seconds. This is a little fast, but sounds like speech. We slow this down by half and listen to the message:

```
The flag is in all capital letters A-D-C-T-F underscore SOUNDS underscore GOOD
```


**Flag**

```
ADCTF_SOUNDS_GOOD
```

# December 4th: Easy One

**Category**

Reversing

**Hint**

*This is very easy crackme.*

**Challenge**

We are given a binary: [easyone](files/easyone) 

**Solution**

Disassemble with objdump

```
objdump -d -M intel easyone

```

We see this section that looks like it copies a series of characters to memory which is compared to user input


```
400625:	e8 c6 fe ff ff       	call   4004f0 <__isoc99_scanf@plt>
40062a:	c6 45 d6 37          	mov    BYTE PTR [rbp-0x2a],0x37
40062e:	c6 45 e2 33          	mov    BYTE PTR [rbp-0x1e],0x33
400632:	c6 45 db 31          	mov    BYTE PTR [rbp-0x25],0x31
400636:	c6 45 d7 48          	mov    BYTE PTR [rbp-0x29],0x48
40063a:	c6 45 de 37          	mov    BYTE PTR [rbp-0x22],0x37
40063e:	c6 45 e8 4f          	mov    BYTE PTR [rbp-0x18],0x4f
400642:	c6 45 e4 35          	mov    BYTE PTR [rbp-0x1c],0x35
400646:	c6 45 d9 35          	mov    BYTE PTR [rbp-0x27],0x35
40064a:	c6 45 e0 4f          	mov    BYTE PTR [rbp-0x20],0x4f
40064e:	c6 45 e6 5f          	mov    BYTE PTR [rbp-0x1a],0x5f
400652:	c6 45 e5 79          	mov    BYTE PTR [rbp-0x1b],0x79
400656:	c6 45 ec 33          	mov    BYTE PTR [rbp-0x14],0x33
40065a:	c6 45 d2 43          	mov    BYTE PTR [rbp-0x2e],0x43
40065e:	c6 45 e9 52          	mov    BYTE PTR [rbp-0x17],0x52
400662:	c6 45 d8 31          	mov    BYTE PTR [rbp-0x28],0x31
400666:	c6 45 d3 54          	mov    BYTE PTR [rbp-0x2d],0x54
40066a:	c6 45 dc 35          	mov    BYTE PTR [rbp-0x24],0x35
40066e:	c6 45 e7 46          	mov    BYTE PTR [rbp-0x19],0x46
400672:	c6 45 d0 41          	mov    BYTE PTR [rbp-0x30],0x41
400676:	c6 45 ea 5f          	mov    BYTE PTR [rbp-0x16],0x5f
40067a:	c6 45 eb 4d          	mov    BYTE PTR [rbp-0x15],0x4d
40067e:	c6 45 dd 5f          	mov    BYTE PTR [rbp-0x23],0x5f
400682:	c6 45 da 5f          	mov    BYTE PTR [rbp-0x26],0x5f
400686:	c6 45 df 6f          	mov    BYTE PTR [rbp-0x21],0x6f
40068a:	c6 45 d5 5f          	mov    BYTE PTR [rbp-0x2b],0x5f
40068e:	c6 45 e3 34          	mov    BYTE PTR [rbp-0x1d],0x34
400692:	c6 45 d4 46          	mov    BYTE PTR [rbp-0x2c],0x46
400696:	c6 45 e1 5f          	mov    BYTE PTR [rbp-0x1f],0x5f
40069a:	c6 45 d1 44          	mov    BYTE PTR [rbp-0x2f],0x44
40069e:	c6 45 ed 00          	mov    BYTE PTR [rbp-0x13],0x0
4006a2:	48 8b 45 f8          	mov    rax,QWORD PTR [rbp-0x8]
4006a6:	0f b6 10             	movzx  edx,BYTE PTR [rax]
4006a9:	48 8b 45 f0          	mov    rax,QWORD PTR [rbp-0x10]
4006ad:	0f b6 00             	movzx  eax,BYTE PTR [rax]
4006b0:	38 c2                	cmp    dl,al
4006b2:	74 11                	je     4006c5 <main+0xd8>
```


Run the program in gdb and set breakpoint at a location after all characters have been copied (4006a2)

```
(gdb) break *0x4006a2
Breakpoint 1 at 0x4006a2
(gdb) run
Starting program: /media/AnanasHD/CTF/adctf2014/files/easyone 
password: bla

Breakpoint 1, 0x00000000004006a2 in main ()

```

Now inspect memory at location indicated by register rbp

```
(gdb) x/30c $rbp-0x30
0x7fffffffdde0:	65 'A'	68 'D'	67 'C'	84 'T'	70 'F'	95 '_'	55 '7'	72 'H'
0x7fffffffdde8:	49 '1'	53 '5'	95 '_'	49 '1'	53 '5'	95 '_'	55 '7'	111 'o'
0x7fffffffddf0:	79 'O'	95 '_'	51 '3'	52 '4'	53 '5'	121 'y'	95 '_'	70 'F'
0x7fffffffddf8:	79 'O'	82 'R'	95 '_'	77 'M'	51 '3'	0 '\000'
```


**Flag**

```
ADCTF_7H15_15_7oO_345y_FOR_M3
```

# December 5th: Shooting

**Category**

Web

**Hint**

*Get 10000 pt. This game is really hard, and so you can crack it.*


**Challenge**

The challenge consists of a javascript game [zip](files/dec5/shooting.zip). 

It appears we need to beat the game to get the flag.


**Solution**

We make ourselves invincible by changing the 8 in the snippet from *shooting.min.js* below to a 0:


```
 this.addEventListener($UPcs4hr8oKgbbqAesfT(1), function() {
    (player.within(this, 8)) ? (gamÃ©.end()) : 0;
 })
```

Next just play the game, when you reach 10000 points the flag appears in an alert.

**Flag**

```
ADCTF_1mP05518L3_STG
```

# December 6th: Paths

**Category**

Reverse

**Hint**

*There are many paths, and search for shortest path from start to goal.*

```
(to, cost)
```

**Challenge**

We are given a python script [paths.py](files/paths.py)

This contains a definition of a graph

```
E = [
[(96, 65)], 
[(64, 99), (82, 120), (3, 100), .. ], 
[(24, 88), (91, 67), (58, 112), .. ], 
[(75, 48), (21, 80), (32, 119), (61, 48) ..], 
[(63, 66), (49, 55), (80, 79), (31, 122), (1, 67), (6, 89), (86, 100), ..], 
[(38, 55), (5, 119), (97, 68), (10, 72), (11, 106), ..],
...
...

```

where node 0 has a connection to node 96 at a cost of 65  
node 1 has connection to node 64 at cost of 99, and a connection to node 82 at cost 120, etc..  


**Solution**

We need to find a path on this graph from node 0 to 99 of length <=2014. 

We wrote a small program to find this path using backtracking ([file](files/findpath.py)):


```
#E= <definition from original file>

start = 0
goal = 99
shortest = 2014

def travel(node, totalcost):
	global nodes

	for i in E[node]:
		# check if goal reached
		if i[0] == goal and totalcost+i[1] <= shortest:		
			print "yay!! \ntotal cost: "+str(totalcost+i[1])
			print "walk: ",
			nodes.append(i[0])
			print nodes			
			break

		# backtrack if cost is too high
		if totalcost+i[1] > shortest:
			break

		# go deeper
		nodes.append(i[0])
		travel(i[0],totalcost+i[1])	
	
	
	# tried all connections from current node, backtrack.
	nodes.pop()


nodes=[]
travel(0,0)
```

this give us the following output:

```
yay!! 
total cost: 2014
walk:  [96, 94, 72, 70, 69, 89, 18, 46, 22, 92, 79, 59, 74, 97, 58, 82, 35, 85, 30, 87, 25, 40, 41, 7, 99]

```


Next we feed the solution to the original program to get the flag:

```
$ python paths_original.py 96 94 72 70 69 89 18 46 22 92 79 59 74 97 58 82 35 85 30 87 25 40 41 7 99
the flag is: ADCTF_G0_go_5hOr7E57_PaTh

```

**Flag**

```
ADCTF_G0_go_5hOr7E57_PaTh
```

# December 7th: Reader

**Category**

PPC

**Hint**

*read 10 times*


**Challenge**

nc adctf2014.katsudon.org 43010

**Solution**

We connect to a service which sends us a set of characters resembling a barcode. 
The barcode is different every time, and seems like we need to decode 10 barcodes in row to get the flag.

We created a small python program to do this

```


``



**Flag**

```
ADCTF_
```

# December 9th: QRgarden

**Category**

PPC

**Hint**

*Read a lot, and the flag begins with "ADCTF_".*

**Challenge**

We are given a large image filled with QR codes.

![](files/qrgarden.png)


**Solution**

We use zbar to read QR codes:
```
sudo apt-get install zbar-tools
```

We split the image into the individual qr codes using imagemagick, then read each QR code until we find one 
starting with ADCTF_

The full image is 8700x8700 pixels, and each qr code is 87x87 pixels. We split this the large image into tiles:

```
convert -crop 87x87 qrgarden.png qrcodes/tile_%d.png
```

Next we read all the images until we find the flag using zbar:


```
$ for i in qrcodes/*; do zbarimg $i 2>&1; done |grep ADCTF
QR-Code:ADCTF_re4d1n9_Qrc0de_15_FuN
```

**Flag**

```
ADCTF_re4d1n9_Qrc0de_15_FuN
```

# December 10th

**Category**

**Challenge**

**Solution**


```
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main() {
  const char * flag = "712249146f241d31651a504a1a7372384d173f7f790c2b115f47";
  char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-"; 
  char *a;
  char an[]="";  
  int len,alen,t, fl, lastfl,orig;
  
  len=(int)(strlen(flag)/2);
  alen=strlen(alphabet);

  printf("reversing input..\n");
  for(int i=0; i<len; i++){
    strncpy(an,flag+i*2,2);
    t=strtol(an,&a,16);
    
    for(int j=0; j<alen; j++){
      fl=alphabet[j];
        orig=fl;
	    if (i > 0) fl ^= lastfl;
		fl ^= fl >> 4;
		fl ^= fl >> 3;
		fl ^= fl >> 2;
		fl ^= fl >> 1;

        if (t == fl){
	    printf("%c",orig);
            break;
	}
    }
    lastfl=fl;
  }

  printf("\ndone.\n");
  return 0;
}
```

**Flag**

```
ADCTF_51mpl3_X0R_R3v3r51n6