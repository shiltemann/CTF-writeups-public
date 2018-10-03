# PicoCTF 2018

Two-week long competition in Sept/October 2018

Had a fun little game environment for the challenges as well as text based

![](writeupfiles/screenshot_categories.png)

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
net cat                      General Skills   75      picoCTF{NEtcat_iS_a_NEcESSiTy_8b6a1fbc}
HEEEEEEERE'S Johnny!         Cryptography     100     picoCTF{J0hn_1$_R1pp3d_1b25af80}
strings 1                    General Skills   100     picoCTF{sTrIngS_sAVeS_Time_d3ffa29c}
pipe                         General Skills   110     picoCTF{almost_like_mario_b797f2b3}
Inspect Me                   Web              125     picoCTF{ur_4_real_1nspect0r_g4dget_b4887011}
grep 2                       General Skills   125     picoCTF{grep_r_and_you_will_find_8eb84049}
Aca-Shell-A                  General Skills   150     picoCTF{CrUsHeD_It_4e355279}
Client Side is still Bad     Web              150     picoCTF{client_is_bad_040594}
Desrouleaux                  Forensics        150     picoCTF{J4y_s0n_d3rUUUULo_b6cacd6c}
Logon                        Web              150     picoCTF{l0g1ns_ar3nt_r34l_2a968c11}
Reading between the eyes     Forensics        150
Recovering from the snap     Forensics        150     picoCTF{th3_5n4p_happ3n3d}
admin panel                  Forensics        150     picoCTF{n0ts3cur3_894a6546}
assembly-0                   Reversing        150
buffer overflow 0            Binary Exploit   150
caesar cipher 1              Cryptography     150     picoCTF{justagoodoldcaesarcipherwoyolfpu}
environ                      General Skills   150     picoCTF{eNv1r0nM3nT_v4r14Bl3_fL4g_3758492}
hertz                        Cryptography     150
hex editor                   Forensics        150
ssh-keyz                     General Skills   150     picoCTF{who_n33ds_p4ssw0rds_38dj21}
Irish Name Repo              Web              200     picoCTF{con4n_r3411y_1snt_1r1sh_f58843c5}
Mr. Robots                   Web              200     picoCTF{th3_w0rld_1s_4_danger0us_pl4c3_3lli0t_30de1}
No Login                     Web              200     picoCTF{n0l0g0n_n0_pr0bl3m_50e16a5c}
Secret Agent                 Web              200     picoCTF{s3cr3t_ag3nt_m4n_134ecd62}
Truly an Artist              Forensics        200
be-quick-or-be-dead-1        Reversing        200
blaise's cipher              Cryptography     200     picoCTF{v1gn3r3_c1ph3rs_ar3n7_bad_cdf08bf0}
leak-me                      Binary Exploit   200
now you don't                Forensics        200     picoCTF{n0w_y0u_533_m3}
shellcode                    Binary Exploit   200
what base is this?           General Skills   200
you can't see me             General Skills   200     picoCTF{j0hn_c3na_paparapaaaaaaa_paparapaaaaaa_22f627d9}
Buttons                      Web              250     picoCTF{button_button_whose_got_the_button_ed306c10}
Ext Super Magic              Forensics        250
Lying Out                    Forensics        250     picoCTF{w4y_0ut_ff5bd19c}
The Vault                    Web              250     picoCTF{w3lc0m3_t0_th3_vau1t_e4ca2258}
absolutely relative          General Skills   250
caesar cipher 2              Cryptography     250
got-2-learn-libc             Binary Exploit   250
rsa-madlibs                  Cryptography     250
in out error                 General Skills   275     picoCTF{p1p1ng_1S_4_7h1ng_b6f5a788}
Artisinal Handcrafted HTTP   Web              300
echooo                       Binary           300
learn gdb                    General Skills   300
Flaskcards                   Web              350
got-shell?                   Binary           350
roulette                     General Skills   350
Malware Shops                Forensics        400
fancy-alive-monitoring       Web              400
store                        General Skills   400
Secure Logon                 Web              500
script me                    General Skills   500
LoadSomeBits                 Forensics        550
Help Me Reset                Web              600
A Simple Question            Web              650
LambDash 3                   Web              800
Dog or Frog                  General Skills   900
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
picoCTF{this_is_crypto!}
```

## General Skills 75: grep 1

**Challenge**

Can you find the flag in [file](writeupfiles/file)? This would be really obnoxious to look through by hand, see if you can find a faster way.

**Solution**

```bash
$ grep "picoCTF" file
picoCTF{grep_and_you_will_find_c709fa94}
```

**Flag**
```
picoCTF{grep_and_you_will_find_c709fa94}
```

## General Skills 75: net cat

**Challenge**

Using netcat (nc) will be a necessity throughout your adventure. Can you connect to `2018shell1.picoctf.com` at port `49387` to get the flag?

**Solution**

```bash
$ nc 2018shell1.picoctf.com 49387
That wasn't so hard was it?
picoCTF{NEtcat_iS_a_NEcESSiTy_8b6a1fbc}
```

**Flag**
```
picoCTF{NEtcat_iS_a_NEcESSiTy_8b6a1fbc}
```

## Cryptography 100: HEEEEEEERE'S Johnny!

**Challenge**

Okay, so we found some important looking files on a linux computer. Maybe they can be used to get a password to the process.
Connect with `nc 2018shell1.picoctf.com 40157`. Files can be found here: [passwd](writeupfiles/passwd) [shadow](writeupfiles/shadow).

**Solution**

We use a combination of unshadow and john the ripper to find the password

```
$ unshadow passwd shadow > crackme
$ john crackme
Created directory: /home/saskia/.john
Loaded 1 password hash (crypt, generic crypt(3) [?/64])
Press 'q' or Ctrl-C to abort, almost any other key for status
password1        (root)
1g 0:00:00:01 100% 2/3 0.5102g/s 469.3p/s 469.3c/s 469.3C/s 123456..pepper
Use the "--show" option to display all of the cracked passwords reliably
Session completed
```

So we know the password for the root user is `password1`. We use that to log into the server

```bash
$ nc 2018shell1.picoctf.com 40157
Username: root
Password: password1
picoCTF{J0hn_1$_R1pp3d_1b25af80}

```

**Flag**
```
picoCTF{J0hn_1$_R1pp3d_1b25af80}
```

## General Skills 100: strings

**Challenge**

Can you find the flag in [this file](writeupfiles/strings) without actually running it? You can also find the file
in `/problems/strings_4_40d221755b4a0b134c2a7a2e825ef95f` on the shell server.

**Solution**

```bash
$ strings strings | grep picoCTF
picoCTF{sTrIngS_sAVeS_Time_d3ffa29c}
```

**Flag**
```
picoCTF{sTrIngS_sAVeS_Time_d3ffa29c}
```

## GEneral Skills 110: pipe

**Challenge**

During your dventure, you will likely encounter a situation where you need to process
data that you receive over the network rather than through a file. Can you find a way
to save the output from this program and search for the flag?
Connect with `2018shell1.picoctf.com 34532`.

**Solution**

We connect via netcat and are flooded with messages

```bash
$ nc 2018shell1.picoctf.com 34532
Unfortunately this is also not a flag
This is not a flag
This is not a flag
I'm sorry you're going to have to look at another line
I'm sorry you're going to have to look at another line
I'm sorry you're going to have to look at another line
Unfortunately this is also not a flag
I'm sorry you're going to have to look at another line
I'm sorry you're going to have to look at another line
I'm sorry you're going to have to look at another line
Unfortunately this is also not a flag
I'm sorry you're going to have to look at another line
Unfortunately this is also not a flag
This is not a flag
[..]
```

So we do a grep:

```bash
$ nc 2018shell1.picoctf.com 34532 | grep picoCTF
picoCTF{almost_like_mario_b797f2b3}
```

**Flag**
```
picoCTF{almost_like_mario_b797f2b3}
```


## Web Exploitation 125: Inspect Me

**Challenge**

**Solution**

We check the source:

```html
<!doctype html>
<html>
  <head>
    <title>My First Website :)</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans|Roboto" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="mycss.css">
    <script type="application/javascript" src="myjs.js"></script>
  </head>

  <body>
    <div class="container">
      <header>
	<h1>My First Website</h1>
      </header>

      <button class="tablink" onclick="openTab('tabintro', this, '#222')" id="defaultOpen">Intro</button>
      <button class="tablink" onclick="openTab('tababout', this, '#222')">About</button>

      <div id="tabintro" class="tabcontent">
	<h3>Intro</h3>
	<p>This is my first website!</p>
      </div>

      <div id="tababout" class="tabcontent">
	<h3>About</h3>
	<p>These are the web skills I've been practicing: <br/>
	  HTML <br/>
	  CSS <br/>
	  JS (JavaScript)
	</p>
	<!-- I learned HTML! Here's part 1/3 of the flag: picoCTF{ur_4_real_1nspe -->
      </div>

    </div>

  </body>
</html>
```

Looks like only 1/3 of the flag, we keep looking

mycss.css

```css
div.container {
    width: 100%;
}

header {
    background-color: #c9d8ef;
    padding: 1em;
    color: white;
    clear: left;
    text-align: center;
}

body {
    font-family: Roboto;
}

h1 {
    color: #222;
}

p {
    font-family: "Open Sans";
}

.tablink {
    background-color: #555;
    color: white;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    font-size: 17px;
    width: 50%;
}

.tablink:hover {
    background-color: #777;
}

.tabcontent {
    color: #111;
    display: none;
    padding: 50px;
    text-align: center;
}

#tabintro { background-color: #ccc; }
#tababout { background-color: #ccc; }

/* I learned CSS! Here's part 2/3 of the flag: ct0r_g4dget_b4887011} */
```

`myjs.js`:

```js
function openTab(tabName,elmnt,color) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
	tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
	tablinks[i].style.backgroundColor = "";
    }
    document.getElementById(tabName).style.display = "block";
    if(elmnt.style != null) {
	elmnt.style.backgroundColor = color;
    }
}

window.onload = function() {
    openTab('tabintro', this, '#222');
}

/* I learned JavaScript! Here's part 3/3 of the flag:  */
```

**Flag**
```
picoCTF{ur_4_real_1nspect0r_g4dget_b4887011}
```

## General Skills 125: grep 2

**Challenge**

This one is a little bit harder. Can you find the flag in `/problems/grep-2_2_413a577106278d0711d28a98f4f6ac28/files`
on the shell server? Remember, grep is your friend.

**Solution**

```bash
$ cd /problems/grep-2_2_413a577106278d0711d28a98f4f6ac28/files
$ ls
files0  files1  files2  files3  files4  files5  files6  files7  files8  files9
$ grep -r "picoCTF"
files2/file16:picoCTF{grep_r_and_you_will_find_8eb84049}
```

**Flag**
```
picoCTF{grep_r_and_you_will_find_8eb84049}
```


## General Skills 150: Aca-Shell-A

**Challenge**
It's never a bad idea to brush up on those linux skills or even learn some new ones
before you set off on this adventure! Connect with `nc 2018shell1.picoctf.com 58422`.

**Solution**


```bash
$ nc 2018shell1.picoctf.com 58422
Sweet! We have gotten access into the system but we aren't root.
It's some sort of restricted shell! I can't see what you are typing
but I can see your output. I'll be here to help you along.
If you need help, type "echo 'Help Me!'" and I'll see what I can do
There is not much time left!

~/$ ls
blackmail
executables
passwords
photos
secret

~/$ cd secret
Now we are cookin'! Take a look around there and tell me what you find!
~/secret$ ls
intel_1
intel_2
intel_3
intel_4
intel_5
profile_AipieG5Ua9aewei5ieSoh7aph
profile_Xei2uu5suwangohceedaifohs
profile_ahShaighaxahMooshuP1johgo
profile_ahqueith5aekongieP4ahzugi
profile_aik4hah9ilie9foru0Phoaph0
profile_bah9Ech9oa4xaicohphahfaiG
profile_ie7sheiP7su2At2ahw6iRikoe
profile_of0Nee4laith8odaeLachoonu
profile_poh9eij4Choophaweiwev6eev

Sabatoge them! Get rid of all their intel files!

~/secret$ rm intel*

Nice! Once they are all gone, I think I can drop you a file of an exploit!
Just type "echo 'Drop it in!' " and we can give it a whirl!

~/secret$ echo "Drop it in!"
Drop it in!
I placed a file in the executables folder as it looks like the only place we can execute from!

Run the script I wrotec to have a little more impact on the system!
~/secret$ cd ..
~/$ cd executables
~/executables$ ./dontLookHere
 0f5d ae3d 4183 37ae 3184 3f11 68e0 53af dbfd 7cd4 41fe 2b13 a3ef d071 54a9 f6b8 6535 5ebf a899 2136 4bbb 21cb 5088 031c f7e7
 818c ee94 3199 0366 b774 3452 13ea fa0a ae8f 9ec7 ad8b 34e5 7c6b 3ad2 e2a1 6aa6 4460 c455 2373 dcda 70b3 9c2b 0129 81e1 1c7e
 156e 74da 51f4 7a24 0ac8 6cc8 f3e3 b474 aa69 2e04 0373 1b16 ab8d ca36 4d48 9b36 cb31 bab3 b6f6 a59f 57ed 6d7a 0431 a520 c983
 343d c6e3 885f 9b32 d60d 4b67 cb7f 451d 230f 0e4d 5ed7 d58b cd59 cc8e d173 4adc 272a 3dc5 05f6 a076 9697 1949 d825 7821 b36e
 9fc4 58e8 a075 c5be 3517 dc17 fbd4 1fe2 5ece b992 b8e4 3618 bff6 3cb5 8fcd 3815 4364 055a 6143 be13 f771 e2aa 4766 e72c 19a3
 849c 26a4 c62d 8064 3691 9182 f357 8dd6 99b3 6d31 cda8 86cb 7eed 235c dd33 a4e6 8763 674d ea07 fdd9 b84a 32b5 7280 99f2 e920
 156b a1b3 d695 ffb2 25da e2bb dae5 6ec2 7990 a94e b6a4 c8d2 47ab 3c4b d945 834c cb19 d49d 4bf0 34a9 f79a 4a4f d87f 6c4d 6c2f
 79da a7d1 ce79 ec8d b33e 5a3d 5fcf 2ef4 3bbc eb2c 55cb 0278 6eb6 63af 063a 82cb ce55 7841 904b aabe 291c c69f 1311 e985 e8ee
 eee0 22bc 41e9 df14 d174 e9c2 7930 686e 6691 73c4 6ff9 2d37 821c 3d4c 7b6a 0321 a243 edc5 1c73 f4a0 4417 5d45 a59a 027f db2c
 88c2 2cca ca38 2361 9a06 5e73 b532 902d b485 f711 4856 c428 ff30 a40c 27ac 2849 ac67 b7a0 5cc2 3f7b a716 3cb1 eb28 a1ec 49c9
 ef0a e38f 7c8a a1b3 dddd 26cf 56bf b8f2 e0c9 d37d 6849 c6bf 2a6a d5b3 91ba 3b18 f522 7857 7393 5124 3c18 abd3 30ed 4766 9b82
 523f 5524 5243 1c79 3e4c 82ea f4d2 e420 2d13 8571 a4dd 4bb7 4eb1 0d27 7a00 e2d0 2440 b821 b576 778f c6eb 27b8 d95d c2a9 e7b1
 187a 1a10 dcbd d8c1 d1b8 6881 92e1 b9d9 6194 1e41 c9a7 080a 031c eba7 83f0 5e11 3d86 41a2 4c56 4726 ac14 ffbe 697c 086c 5bd7
 ff07 68ee 1b14 e8ad 9306 d353 4b3d f5b4 36cb 720a 8bd3 5d6c fc78 2330 3cb8 2b2d bf47 229f 9cc9 9bba 1e02 eb5f 2cf0 1974 d987
 0193 7884 482c 0f53 b2bf 4390 c4de df00 8a1d 71cb 5413 3172 a90e aee1 9ac2 a5e2 3912 183d 2856 7d55 ec4b 117f 0e1f 3ad5 dd6e
 5064 98d6 a3bd 2b92 c8b7 9f34 1f9d 8fa8 9dff 1af9 435d 4c3f a30e 3dc1 2137 56c2 8454 a0eb 6cc8 48a1 0b9a af06 3a83 f38d bbe0
 dea0 4374 118b b79f 2487 1086 1ca8 6fee 7187 e70c 697b a5bb d0ee b0a3 125e 7a99 1dc1 cc1e 98cc ce69 0519 4b67 7723 ef3b 8cc1
 d7f1 1480 8564 43d3 78f0 c705 5af1 1d77 b52e 681c 9f34 e967 6861 fb47 a502 b8d1 8057 5b6f db48 2731 4d0e 3bcc 5492 a03e 544d
 1d78 a5bc e79e 0489 c718 419d c182 28cc 86c2 2d11 d48a c372 fe62 13a4 0948 61aa 1d10 0fa1 4ebd 31ab e3b2 e4e0 b047 b002 9311
 d3ea ae07 7043 bd32 f4f3 58ca 841e 1c08 41e1 8dc3 e535 66e6 61fd 2004 085b 6258 58bc 12d4 5faa 31a4 b615 992a 2fc7 2950 8834
 77c5 4efb 1a1e 90b2 f9ca 27c4 8880 a6b0 a984 7b76 272c 64dd 4751 0ab9 ddb6 0fad ec5f 6a86 2f63 f69a ab62 9119 44ca acdd 7e12
 9020 e795 0096 4168 c7ae ecb0 0b40 ba3a fc2c 8d9f ee51 60b0 9720 6d4d a073 d9d2 cd41 70e3 36b1 e746 54a8 6493 11b1 9482 ac0a
 d7dd c7d2 49bc 4f81 48db 9975 788f a27e 371f 9102 df89 7215 b28f b18f d8f2 78ef db1b 05b7 5f2a 37b3 37e1 07d4 5ff0 677d b4b4
 8c05 f00a f01b d99a a29d 96a4 0028 b5b2 6c2c f20b 84e7 06cf 7c02 0ac8 cbf4 dcfa 6cf6 18f2 ca72 2def 232b 34f1 1416 d00c f337
 30ac 37de 97c0 c751 516d 01f0 9d33 0782 22ad 31dc 0775 697f 9956 58d0 dd2f 9158 4aa8 05d2 5a99 71a4 94ad a3b9 f782 e9c3 d93e
 0b35 578d 6707 d2b8 da62 f119 0af6 4579 37e7 90a6 0215 3b41 5984 7644 af07 111d a872 f09c 5bd3 2f92 4568 e6ef 1521 d977 42e4
 7342 5288 afa2 8884 ad9f 5768 70a8 7f9c b9e5 b5fe 1c8c 6872 fbad 6c2b f2b7 a36c ef0f ef92 56c1 9fea e070 d8d8 df14 09b7 4fc4
 16d4 ae3e 7f34 c054 28ac 7d2d f9b6 e324 ef3f dbc7 4dd9 6cd8 85bd 90f5 e70d 7830 4847 3a73 c450 a4e7 dea3 eaba 5f83 de6a bd65
 b061 9ac8 f5b1 d832 f4ad a162 56ad 6b1b 4d25 c2e8 e56c d10b c559 19ad dc32 877d d558 ee31 e430 94d4 b673 3a53 6689 8bad 98b3
 4536 f64c 53cf a6e4 d321 50b8 7545 081a 45ef 9727 5775 79b3 a3a4 624d 8afa 83dc 4aed 18f5 f358 ebb6 c573 24d8 5df3 17a7 2d6d
 9364 2a31 18ad dadc 7cfb e1f5 227d e199 dcc7 b4b5 9abf 4883 aced 7ce3 a6ef a5b4 0ba0 5689 8e2f 6a1b 508d 2347 ba7f 7fee debf
 6db5 e442 9a0e 3429 fc88 2584 51d3 3cac 2599 a20a d00d 9e2b 4a4b 3bd6 fc35 3bd2 f934 75d3 c1c2 aa40 79f8 46c0 9b53 e076 9ea6
 f0ef a335 1427 17e4 fb71 cf0c d459 a1f7 68f7 2981 82d1 b8a9 169f 9dee bcc3 a074 c4e3 e700 c6f7 bb67 baef 2d0a ebe4 2036 ffc5
Looking through the text above, I think I have found the password. I am just having trouble with a username.

Oh drats! They are onto us! We could get kicked out soon!

Quick! Print the username to the screen so we can close are backdoor and log into the account directly!

You have to find another way other than echo!
~/executables$ whoami
l33th4x0r
Perfect! One second!

Okay, I think I have got what we are looking for. I just need to to copy the file to a place we can read.
Try copying the file called TopSecret in tmp directory into the passwords folder.
~/executables$ : command not found or invalid
~/executables$ ls
dontLookHere
~/executables$ cp /tmp/TopSecret /passwords
~/executables$ cd ../
../: directory not found or permitted
~/executables$ ls
dontLookHere
~/executables$ cd ..
~/$ cp /tmp/TopSecret passwords
Server shutdown in 10 seconds...
Quick! go read the file before we lose our connection!
~/$ cd passwords
~/passwords$ ls
TopSecret
~/passwords$ cat TopSecret
Major General John M. Schofield's graduation address to the graduating class of 1879 at West Point is as follows: The discipline which makes the soldiers of a free country reliable in battle is not to be gained by harsh or tyrannical treatment.On the contrary, such treatment is far more likely to destroy than to make an army.It is possible to impart instruction and give commands in such a manner and such a tone of voice as to inspire in the soldier no feeling butan intense desire to obey, while the opposite manner and tone of voice cannot fail to excite strong resentment and a desire to disobey.The one mode or other of dealing with subordinates springs from a corresponding spirit in the breast of the commander.He who feels the respect which is due to others, cannot fail to inspire in them respect for himself, while he who feels,and hence manifests disrespect towards others, especially his subordinates, cannot fail to inspire hatred against himself.
picoCTF{CrUsHeD_It_4e355279}

```

**Flag**
```
picoCTF{CrUsHeD_It_4e355279coCTF{CrUsHeD_It_4e355279}}
```

## Web Exploitation 150: Client Side is Still Bad

**Challenge**
I forgot my password again, but this time there doesn't seem to be a reset,
can you help me? http://2018shell1.picoctf.com:53990 (link)

**Solution**

we check the source

```html
<html>
<head>
<title>Super Secure Log In</title>
</head>
<body bgcolor="#000000">
<!-- standard MD5 implementation -->
<script type="text/javascript" src="md5.js"></script>

<script type="text/javascript">
  function verify() {
    checkpass = document.getElementById("pass").value;
    split = 4;
    if (checkpass.substring(split*7, split*8) == '}') {
      if (checkpass.substring(split*6, split*7) == '0594') {
        if (checkpass.substring(split*5, split*6) == 'd_04') {
         if (checkpass.substring(split*4, split*5) == 's_ba') {
          if (checkpass.substring(split*3, split*4) == 'nt_i') {
            if (checkpass.substring(split*2, split*3) == 'clie') {
              if (checkpass.substring(split, split*2) == 'CTF{') {
                if (checkpass.substring(0,split) == 'pico') {
                  alert("You got the flag!")
                  }
                }
              }

            }
          }
        }
      }
    }
    else {
      alert("Incorrect password");
    }
  }
</script>
<div style="position:relative; padding:5px;top:50px; left:38%; width:350px; height:140px; background-color:red">
<div style="text-align:center">
<p>Welcome to the Secure Login Server.</p>
<p>Please enter your credentials to proceed</p>
<form action="index.html" method="post">
<input type="password" id="pass" size="8" />
<br/>
<input type="submit" value="Log in" onclick="verify(); return false;" />
</form>
</div>
</div>
</body>
</html>
```

**Flag**
```
picoCTF{client_is_bad_040594}
```

## Forensics 150: Desrouleaux

**Challenge**
 Our network administrator is having some trouble handling the tickets for all of of our incidents. Can you help him out by answering all the questions? Connect with nc 2018shell1.picoctf.com 40952.

**Solution**
The [incidents.json](./writeupfiles/incidents.json) is a pretty small file surprisingly, everything is accomplished with `jq`:



```
$ nc 2018shell1.picoctf.com 40952
You'll need to consult the file `incidents.json` to answer the following questions.
What is the most common source IP address? If there is more than one IP address that is the most common, you may give any of the most common ones.
99.32.28.173

Correct!

How many unique destination IP addresses were targeted by the source IP address 99.32.28.173?
3

Correct!

What is the average number of unique destination IP addresses that were sent a file with the same hash? Your answer needs to be correct to 2 decimal places.
1.67

Correct!

Great job. You've earned the flag: picoCTF{J4y_s0n_d3rUUUULo_b6cacd6c}
```

**Question2**

```
$ cat incidents.json | jq .tickets[].src_ip -r | sort | uniq -c | sort -n | tail -n 1
$ cat incidents.json | jq '.tickets[] | select(.src_ip == "99.32.28.173")'
$ cat incidents.json | jq -r '.tickets[] | [.file_hash, .dst_ip] | @tsv' | sort
336033417a7364f0        230.124.77.62
336033417a7364f0        231.208.216.227
65a8826931637d74        230.124.77.62
65a8826931637d74        23.245.63.105
811f58a6e15c0643        120.119.119.83
811f58a6e15c0643        215.51.6.131
b03dee2273112d13        107.111.202.130
b03dee2273112d13        230.124.77.62
bbd65e44921b880c        247.145.101.4
dfd6f5d416878f69        231.208.216.227
```

**Answer**

2+2+2+2+1+1 / 6  = 1.67 (6 different hashes, 4 of them got sent to 2 unique IPs, 2 of them just 1, average is 1.67)



**Flag**
```
picoCTF{J4y_s0n_d3rUUUULo_b6cacd6c}
```

## Web Exploitation 150: Logon

**Challenge**

I made a website so now you can log on to! I don't seem to have the admin password. See if you can't get to the flag. http://2018shell1.picoctf.com:57252 (link)

**Solution**

It only checks password for user `admin`. We can log in as any other username, then get 3 cookies:


We change `admin` cookie to `True` and refresh the page to get the flag

**Flag**
```
picoCTF{l0g1ns_ar3nt_r34l_2a968c11}
```


## Forensics 150: Reading between the Eyes

**Challenge**

Stego-Saurus hid a message for you in this image, can you retreive it?

![](writeupfiles/husky.png)

**Solution**

**Flag**
```

```


## Forensics 150: Recovering from the snap

**Challenge**


There used to be a bunch of [animals](writeupfiles/animals) here, what did Dr. Xernon do to them?


**Solution**

```bash
$ binwalk animals.dd

DECIMAL       HEXADECIMAL     DESCRIPTION
--------------------------------------------------------------------------------
39424         0x9A00          JPEG image data, JFIF standard 1.01
39454         0x9A1E          TIFF image data, big-endian, offset of first image directory: 8
672256        0xA4200         JPEG image data, JFIF standard 1.01
1165824       0x11CA00        JPEG image data, JFIF standard 1.01
1556992       0x17C200        JPEG image data, JFIF standard 1.01
1812992       0x1BAA00        JPEG image data, JFIF standard 1.01
1813022       0x1BAA1E        TIFF image data, big-endian, offset of first image directory: 8
2136576       0x209A00        JPEG image data, JFIF standard 1.01
2136606       0x209A1E        TIFF image data, big-endian, offset of first image directory: 8
2607616       0x27CA00        JPEG image data, JFIF standard 1.01
2607646       0x27CA1E        TIFF image data, big-endian, offset of first image directory: 8
3000832       0x2DCA00        JPEG image data, JFIF standard 1.01
3000862       0x2DCA1E        TIFF image data, big-endian, offset of first image directory: 8

$ binwalk --dd='jpeg:jpg' animals.dd

```

this gave us a bunch of animal images, and one image with the flag:

![](writeupfiles/animalsflag.jpg)

**Flag**
```
picoCTF{th3_5n4p_happ3n3d}
```

## Forensics 150: admin panel

**Challenge**

We captured some traffic logging into the admin panel, can you find the password?

**Solution**

They've provided a [pcap file](./writeupfiles/data.pcap), there's a POST to
/login which looks obvious. Following the stream as HTTP shows the password
quite clearly.

**Flag**
```
picoCTF{n0ts3cur3_894a6546}
```

## Reversing 150: assembly-0

**Challenge**

What does `asm0(0xd8,0x7a)` return? Submit the flag as a hexadecimal value (starting with `0x`).

NOTE: Your submission for this question will NOT be in the normal flag format. [Source](writeupfiles/intro_asm_rev.S)
located in the directory at `/problems/assembly-0_1_fc43dbf0079fd5aab87236bf3bf4ac63`.

**Solution**

**Flag**
```

```

## Binary Exploitation 150: buffer overflow 0

**Challenge**

Let's start off simple, can you overflow the right buffer in this [program](writeupfiles/vuln) to get the flag?
You can also find it in `/problems/buffer-overflow-0_4_ab1efebbee9446039487c64b88d38631` on the shell server.

[Source](writeupfiles/vuln.c)

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>

#define FLAGSIZE_MAX 64

char flag[FLAGSIZE_MAX];

void sigsegv_handler(int sig) {
  fprintf(stderr, "%s\n", flag);
  fflush(stderr);
  exit(1);
}

void vuln(char *input){
  char buf[16];
  strcpy(buf, input);
}

int main(int argc, char **argv){

  FILE *f = fopen("flag.txt","r");
  if (f == NULL) {
    printf("Flag File is Missing. Problem is Misconfigured, please contact an Admin if you are running this on the shell server.\n");
    exit(0);
  }
  fgets(flag,FLAGSIZE_MAX,f);
  signal(SIGSEGV, sigsegv_handler);

  gid_t gid = getegid();
  setresgid(gid, gid, gid);

  if (argc > 1) {
    vuln(argv[1]);
    printf("Thanks! Received: %s", argv[1]);
  }
  else
    printf("This program takes 1 argument.\n");
  return 0;
}
```

**Solution**

**Flag**
```

```

## Cryptography 150: caesar cipher 1

**Challenge**

This is one of the older ciphers in the books, can you decrypt the message?

```
picoCTF{grpqxdllaliazxbpxozfmebotlvlicmrcoCTF{grpqxdllaliazxbpxozfmebotlvlicmr}}
```

**Solution**

ROT3 gives flag

**Flag**
```
picoCTF{justagoodoldcaesarcipherwoyolfpu}
```

## Cryptography 150: environ

**Challenge**

Sometimes you have to configure environment variables before executing a program. Can you find the flag we've hidden in an environment variable on the shell server?

**Solution**

logging into the shell:

```
hxr@pico-2018-shell-1:~$ env | grep pico
SECRET_FLAG=picoCTF{eNv1r0nM3nT_v4r14Bl3_fL4g_3758492}
```

**Flag**
```
picoCTF{eNv1r0nM3nT_v4r14Bl3_fL4g_3758492}
```

## Cryptography 150: hertz

**Challenge**

Here's another simple cipher for you where we made a bunch of substitutions. Can you decrypt it? Connect with `nc 2018shell1.picoctf.com 43324`.

**Solution**

**Flag**
```

```

## Forensics 150: hex editor

**Challenge**
This cat has a secret to teach you.

**Solution**
Flag was appended to end of [the jpeg](./writeupfiles/hex_editor.jpg)

**Flag**
```
picoCTF{and_thats_how_u_edit_hex_kittos_3E03e57d}
```

## General Skills 150: ssh-keyz

**Challenge**
As nice as it is to use our webshell, sometimes its helpful to connect directly to our machine. To do so, please add your own public key to ~/.ssh/authorized_keys, using the webshell. The flag is in the ssh banner which will be displayed when you login remotely with ssh to with your username.

**Solution**
Added a key to ~/.ssh/authorized_keys. Hardest part was finding the ip address with `curl icanhazip.com`

**Flag**
```
picoCTF{who_n33ds_p4ssw0rds_38dj21}
```

## Web Exploitation 200: Irish Name Repo

**Challenge**

There is a website running at http://2018shell1.picoctf.com:28402 (link). Do you think you can log us in? Try to see if you can login!


**Solution**

There is an admin login page vulneral to sql injection

```
username: admin
password: ' or 'x'='x
```

gives us the flag

**Flag**
```
picoCTF{con4n_r3411y_1snt_1r1sh_f58843c5}
```

## Web Exploitation 200: Mr. Robots

**Challenge**
Do you see the same things I see? The glimpses of the flag hidden away? http://2018shell1.picoctf.com:40064 (link)

**Solution**
Checking /robots.txt we see a disallow rule for /30de1.html which includes the flag.

**Flag**
```
picoCTF{th3_w0rld_1s_4_danger0us_pl4c3_3lli0t_30de1}
```

## Web 200: No login

**Challenge**

Looks like someone started making a website but never got around to making a login, but I heard there
was a flag if you were the admin. http://2018shell1.picoctf.com:39670

**Solution**

create a cooke named `admin` and set value to `True` gives the flag

**Flag**
```
picoCTF{n0l0g0n_n0_pr0bl3m_50e16a5c}
```

## Web Exploitation 200: Secret Agent

**Challenge**

Here's a little website that hasn't fully been finished. But I heard google gets all your info anyway. http://2018shell1.picoctf.com:53383

**Solution**

The website contains a big button with the word `Flag` on it. When we click it we get the message `You're not google! Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0`.

![](writeupfiles/secretagent.png)

Looks like it checks our user agent string to decide whether we get the flag. Changing the user agent string to something custom like `google` is not enough, so we look up the user agent strings used by the Google crawlers [link](https://support.google.com/webmasters/answer/1061943?hl=en)


```python
import requests

headers = {'User-Agent':'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)'}
r=requests.get("http://2018shell1.picoctf.com:53383/flag", headers=headers)

print r.text
```

which gives us the web page containing the flag:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>My New Website</title>
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/3.3/examples/jumbotron-narrow/jumbotron-narrow.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="header">
            <nav>
                <ul class="nav nav-pills pull-right">
                    <li role="presentation" class="active"><a href="/">Home</a>
                    </li>
                    <li role="presentation"><a href="/unimplemented">Sign In</a>
                    </li>
                    <li role="presentation"><a href="/unimplemented">Sign Out</a>
                    </li>
                </ul>
            </nav>
            <h3 class="text-muted">My New Website</h3>
        </div>

       <!-- Categories: success (green), info (blue), warning (yellow), danger (red) -->

       <div class="alert alert-success alert-dismissible" role="alert" id="myAlert">
         <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- <strong>Title</strong> --> Googlebot!
           </div>

        <div class="jumbotron">
            <p class="lead"></p>
            <p style="text-align:center; font-size:30px;"><b>Flag</b>: <code>picoCTF{s3cr3t_ag3nt_m4n_134ecd62}</code></p>
            <!-- <p><a class="btn btn-lg btn-success" href="admin" role="button">Click here for the flag!</a> -->
            <!-- </p> -->
        </div>
        <footer class="footer">
            <p>&copy; PicoCTF 2018</p>
        </footer>
    </div>
    <script>
    $(document).ready(function(){
        $(".close").click(function(){
            $("myAlert").alert("close");
        });
    });
    </script>
</body>
</html>
```


**Flag**
```
picoCTF{s3cr3t_ag3nt_m4n_134ecd62}
```

## Forensics 200: Truly an Artist

**Challenge**
Can you help us find the flag in this [Meta-Material](./writeupfiles/2018.png)?

**Solution**
```
$ exiftool writeupfiles/2018.png
ExifTool Version Number         : 10.10
File Name                       : 2018.png
Directory                       : writeupfiles
File Size                       : 13 kB
File Modification Date/Time     : 2018:09:29 11:37:17+02:00
File Access Date/Time           : 2018:09:29 11:37:17+02:00
File Inode Change Date/Time     : 2018:09:29 11:37:33+02:00
File Permissions                : rw-rw----
File Type                       : PNG
File Type Extension             : png
MIME Type                       : image/png
Image Width                     : 1200
Image Height                    : 630
Bit Depth                       : 8
Color Type                      : RGB
Compression                     : Deflate/Inflate
Filter                          : Adaptive
Interlace                       : Noninterlaced
Artist                          : picoCTF{look_in_image_788a182e}
Image Size                      : 1200x630
Megapixels                      : 0.756
```

**Flag**
```
picoCTF{look_in_image_788a182e}
```


## Reversing 200: be-quick-or-be-dead-1

**Challenge**

 You find [this](https://www.youtube.com/watch?v=CTt1vk9nM9c) when searching for some music, which leads you to [be-quick-or-be-dead-1](writeupfiles/be-quick-or-be-dead-1). Can you run it fast enough?

You can also find the executable in `/problems/be-quick-or-be-dead-1_3_aeb48854203a88fb1da963f41ae06a1c`.


**Solution**

**Flag**
```

```


## Cryptography 200: blaise's cipher

**Challenge**

My buddy Blaise told me he learned about this cool cipher invented by a guy also named Blaise! Can you figure out what it says?

Connect with `nc 2018shell1.picoctf.com 46966`


**Solution**

We connect and are greeted by this message:

```
Yse lncsz bplr-izcarpnzjo dkxnroueius zf g uzlefwpnfmeznn cousex bls ltcmaqltki my Rjzn Hfetoxea Gqmexyt axtfnj 1467 fyd axpd g rptgq nivmpr jndc zt dwoynh hjewkjy cousex fwpnfmezx. Llhjcto'x dyyypm uswy ybttimpd gqahggpty fqtkw debjcar bzrjx, lnj xhizhsey bprk nydohltki my cwttosr tnj wezypr uk ehk hzrxjdpusoitl llvmlbky tn zmp cousexypxz. Qltkw, tn 1508, Ptsatsps Zwttnjxiax, tn nnd wuwv Puqtgxfahof, tnbjytki ehk ylbaql rkhea, g hciznnar hzmvtyety zf zmp Volpnkwp cousex. Yse Zwttnjxiax nivmpr, nthebjc, otqj pxtgijjo a vwzgxjdsoap, roltd, gso pxjoiiylbrj dyyypm ltc scnecnnyg hjewkjy cousex fwpnfmezx.

Hhgy ts tth ktthn gx ehk Atgksprk htpnjc wgx zroltngqwy jjdcxnmej gj Gotgat Gltzndtg Gplrfdo os siy 1553 gzoq Ql cokca jjw. Sol. Riualn Hfetoxea Hjwlgxz. Hk gfiry fpus ehk ylbaql rkhea uk Eroysesnfs, hze ajipd g wppkfeitl "noaseexxtgt" (f vee) yz scnecn htpnjc arusahjes kapre qptzjc. Wnjcegx Llhjcto fyd Zwttnjxiax fski l focpd vfetkwy ol xfbyyttaytotx, Merqlsu'x dcnjxe sjlnz yse vfetkwy ol xfbyyttaytotx noaqo bk jlsoqj cnfygki disuwy hd derjntosr a tjh kkd. Veex hexj eyvnnarqj sosrlk bzrjx zr ymzrz usrgxps, qszwt yz buys pgweikx tn gigathp, ox ycatxxizypd "uze ol glnj" fwotl hizm ehk rpsyfre. Hjwlgxz's sjehui ehax cewztrki dtxtyg yjnuxney ltc otqj tnj vee. Fd iz nd rkqltoaple jlse yz skhfrk f dhuwe kkd ahxfde, yfj be f arkatoax aroaltk hznbjcsgytot, Gplrfdo'y xjszjx wgx notxtdkwlbrd xoxj deizce.

Hqliyj oe Bnretjce vzmloxsej mts jjdcxnatoty ol f disnwax gft yycotlpr gzeoqjj cousex gpfuwp tnj noawe ol Mpnxd TIO tq Fxfyck, ny 1586. Lgypr, os ehk 19ys ckseuxd, ehk nyvkseius zf Hjwlgxz's inahkw hay rtsgyerogftki eo Bnretjce. Jfgij Plht ny hox moup Ehk Hzdkgcegppry qlmkseej yse sndazycihzeius my yfjitl ehgy siyyzre mld "olyoxjo tnnd isuzrzfyt itytxnmuznzn gso itxeegi yasjo a xjrrkxdibj lnj jwesjytgwj cousex kzr nnx [Volpnkwp] tntfgn mp hgi yozmtnm yz du bttn ne". pohzCZK{g1gt3w3_n1pn3wd_ax3s7_maj_hof08hk0}

Ehk Atgksprk htpnjc ggnyej f cevzeaznzn ltc bknyg kcnevytotfwle xerusr. Nuypd gzehuw lnj rltnjxaznnigs Nhgwwey Qftcnogk Izdmxzn (Rjhiy Hlrxtwl) ifwlki ehk Atgksprk htpnjc utgcegplbrj tn nnd 1868 pojne "Zmp Arusahje Cousex" ny a imtljwpn'y rlggetnk. Ny 1917, Sinpnznqii Fxexnnat ipsiwtbki ehk Atgksprk htpnjc ay "nxpuxdihqp ol ycatxwaznzn". Zmts xjauzfeius hay szt jjdexapd. Imlrrjd Bggmamj ts qszwt yz hgap bxtvet f gaxnlnz tq tnj nivmpr gx paxqj ay 1854; mzwkapr, nj oijs'e pagwiym siy bzrq. Plsoxvi kseixjwy hwzkk yse inahkw lnj ufbrndhki ehk ypcnstqaj tn zmp 19tn hpnzzcy. Kapn hjqoxj ehox, ehuzrh, ytxe yptlrjo cxdatgsllexes itflj tncgxtotfwle gcegp ehk htpnjc it yse 16zm netyfre.

Hcyvyzgxfahoh dloip raqp uyjo ay f narhflgytot ftd hd ehk Xhiyx Lrsd mezbpet 1914 fyd 1940.
Zmp Volpnkwp cousex nd soralk jyoals tu gp a lnplj htpnjc il ne iy zdej ny cusuutheius hizm nivmpr jndky. Yse Ityfkiprgyp Szfeey tq Asjciif, qox jiasuwe, axpd g gcayx nivmpr jndk zt tmvqpmkse tnj Gimjyexj nivmpr jzcitl ehk Fxexnnat Htvoq Hax. Yse Ityfkiprghj's sjdsglps cjce lfc fxtx skhcez fyd zmp Utnzn xjrurfcle hcaippd zmpix rpsyfrey. Ysruzrhuze tnj hax, yse Ityfkiprgyp lkfoexxsiv ucisfcird cernpd auzn zmcek ppy vmcayjd, "Mgsnhkxeex Gwulk", "Nosuwezj Giiyzre" fyd, gx ehk blr ifxe zt l crtde, "Itxe Xjerogftoty".

Goqmexy Gexslm zwtej yz rkulix yse hwzkks nivmpr (iwpaznyg zmp Vkwyas–Atgksprk htpnjc it 1918), gft, tt xazypr cmlt nj oij, yse inahkw hay xeirq gursprggwe zt nreueatfwyynd. Vkwyas'x hoxp, socjgex, jgetyfarqj lki eo zmp otj-eisj aaj, f ehktceznnarqj utgcegplbrj nivmpr.
```

It is encrypted and we see something that clearly will be the flag. With *blaise* in the title, this has got to be Vigenere ciper.

We use [this site]() to find the key, which turns out to be `FLAG`. The decrypted message is:

```
The first well-documented description of a polyalphabetic cipher was formulated by Leon Battista Alberti around 1467 and used a metal cipher disc to switch between cipher alphabets. Alberti's system only switched alphabets after several words, and switches were indicated by writing the letter of the corresponding alphabet in the ciphertext. Later, in 1508, Johannes Trithemius, in his work Poligraphia, invented the tabula recta, a critical component of the Vigenere cipher. The Trithemius cipher, however, only provided a progressive, rigid, and predictable system for switching between cipher alphabets.

What is now known as the Vigenere cipher was originally described by Giovan Battista Bellaso in his 1553 book La cifra del. Sig. Giovan Battista Bellaso. He built upon the tabula recta of Trithemius, but added a repeating "countersign" (a key) to switch cipher alphabets every letter. Whereas Alberti and Trithemius used a fixed pattern of substitutions, Bellaso's scheme meant the pattern of substitutions could be easily changed simply by selecting a new key. Keys were typically single words or short phrases, known to both parties in advance, or transmitted "out of band" along with the message. Bellaso's method thus required strong security for only the key. As it is relatively easy to secure a short key phrase, say by a previous private conversation, Bellaso's system was considerably more secure.

Blaise de Vigenere published his description of a similar but stronger autokey cipher before the court of Henry III of France, in 1586. Later, in the 19th century, the invention of Bellaso's cipher was misattributed to Vigenere. David Kahn in his book The Codebreakers lamented the misattribution by saying that history had "ignored this important contribution and instead named a regressive and elementary cipher for him [Vigenere] though he had nothing to do with it". picoCTF{v1gn3r3_c1ph3rs_ar3n7_bad_cdf08bf0}

The Vigenere cipher gained a reputation for being exceptionally strong. Noted author and mathematician Charles Lutwidge Dodgson (Lewis Carroll) called the Vigenere cipher unbreakable in his 1868 piece "The Alphabet Cipher" in a children's magazine. In 1917, Scientific American described the Vigenere cipher as "impossible of translation". This reputation was not deserved. Charles Babbage is known to have broken a variant of the cipher as early as 1854; however, he didn't publish his work. Kasiski entirely broke the cipher and published the technique in the 19th century. Even before this, though, some skilled cryptanalysts could occasionally break the cipher in the 16th century.

Cryptographic slide rule used as a calculation aid by the Swiss Army between 1914 and 1940.
The Vigenere cipher is simple enough to be a field cipher if it is used in conjunction with cipher disks. The Confederate States of America, for example, used a brass cipher disk to implement the Vigenere cipher during the American Civil War. The Confederacy's messages were far from secret and the Union regularly cracked their messages. Throughout the war, the Confederate leadership primarily relied upon three key phrases, "Manchester Bluff", "Complete Victory" and, as the war came to a close, "Come Retribution".

Gilbert Vernam tried to repair the broken cipher (creating the Vernam–Vigenere cipher in 1918), but, no matter what he did, the cipher was still vulnerable to cryptanalysis. Vernam's work, however, eventually led to the one-time pad, a theoretically unbreakable cipher.
```

**Flag**
```
picoCTF{v1gn3r3_c1ph3rs_ar3n7_bad_cdf08bf0}
```

## Binary Exploitation 200: leak-me

**Challenge**

Can you authenticate to [this service](writeupfiles/auth) and get the flag? Connect with nc 2018shell1.picoctf.com 31045.

[Source](writeupfiles)

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

int flag() {
  char flag[48];
  FILE *file;
  file = fopen("flag.txt", "r");
  if (file == NULL) {
    printf("Flag File is Missing. Problem is Misconfigured, please contact an Admin if you are running this on the shell server.\n");
    exit(0);
  }

  fgets(flag, sizeof(flag), file);
  printf("%s", flag);
  return 0;
}


int main(int argc, char **argv){

  setvbuf(stdout, NULL, _IONBF, 0);

  // Set the gid to the effective gid
  gid_t gid = getegid();
  setresgid(gid, gid, gid);

  // real pw:
  FILE *file;
  char password[64];
  char name[256];
  char password_input[64];

  memset(password, 0, sizeof(password));
  memset(name, 0, sizeof(name));
  memset(password_input, 0, sizeof(password_input));

  printf("What is your name?\n");

  fgets(name, sizeof(name), stdin);
  char *end = strchr(name, '\n');
  if (end != NULL) {
    *end = '\x00';
  }

  strcat(name, ",\nPlease Enter the Password.");

  file = fopen("password.txt", "r");
  if (file == NULL) {
    printf("Password File is Missing. Problem is Misconfigured, please contact an Admin if you are running this on the shell server.\n");
    exit(0);
  }

  fgets(password, sizeof(password), file);

  printf("Hello ");
  puts(name);

  fgets(password_input, sizeof(password_input), stdin);
  password_input[sizeof(password_input)] = '\x00';

  if (!strcmp(password_input, password)) {
    flag();
  }
  else {
    printf("Incorrect Password!\n");
  }
  return 0;
}

```

**Solution**

**Flag**
```

```


## Forensics 200: now you don't

**Challenge**

We heard that there is something hidden in this picture. Can you find it?

![](writeupfiles/nowYouDont.png)


**Solution**

We extract the LSB of each of the colour channels using [this script](../../_resources/code/extractlsb.py)
and find the flag in the red channel:

![](writeupfiles/nowyouseeme.png)


**Flag**
```
picoCTF{n0w_y0u_533_m3}
```

## Binary Exploitation 200: shellcode

**Challenge**

This [program](writeupfiles/vuln2) executes any input you give it. Can you get a shell?

You can find the program in `/problems/shellcode_0_48532ce5a1829a772b64e4da6fa58eed` on the shell server.

[Source](writeupfiles/vuln2.c)

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

#define BUFSIZE 148
#define FLAGSIZE 128

void vuln(char *buf){
  gets(buf);
  puts(buf);
}

int main(int argc, char **argv){

  setvbuf(stdout, NULL, _IONBF, 0);

  // Set the gid to the effective gid
  // this prevents /bin/sh from dropping the privileges
  gid_t gid = getegid();
  setresgid(gid, gid, gid);

  char buf[BUFSIZE];

  puts("Enter a string!");
  vuln(buf);

  puts("Thanks! Executing now...");

  ((void (*)())buf)();

  return 0;
}
```

**Solution**

**Flag**
```

```


## General Skills 200: what base is this?

**Challenge**


To be successful on your mission, you must be able read data represented in different ways, such as hexadecimal or binary. Can you get the flag from this program to prove you are ready? Connect with `nc 2018shell1.picoctf.com 14390`.

**Solution**

**Flag**
```

```


## General Skills: 200: you can't see me

**Challenge**
'...reading transmission... Y.O.U. .C.A.N.'.T. .S.E.E. .M.E. ...transmission ended...' Maybe something lies in /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f.

**Solution**

```
hxr@pico-2018-shell-1:~$ ls -al /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f
total 60
drwxr-xr-x   2 root       root        4096 Sep 28 08:29 .
-rw-rw-r--   1 hacksports hacksports    57 Sep 28 08:29 .
drwxr-x--x 556 root       root       53248 Sep 28 08:29 ..
```

there's no easy way to access that

```
hxr@pico-2018-shell-1:~$ cat /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/.*
cat: /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/.: Is a directory
picoCTF{j0hn_c3na_paparapaaaaaaa_paparapaaaaaa_22f627d9}
cat: /problems/you-can-t-see-me_4_8bd1412e56df49a3c3757ebeb7ead77f/..: Permission denied
```

**Flag**
```
picoCTF{j0hn_c3na_paparapaaaaaaa_paparapaaaaaa_22f627d9}
```

## Web Exploitation 250: Buttons

**Challenge**
 There is a website running at http://2018shell1.picoctf.com:21579 (link). Try to see if you can push their buttons.

**Solution**
The first button is a form and the second is an `a href`. Clicking the second
link gives a message about being denied. Changing the second button to a form
(so it will POST) result in success.

**Flag**
```
picoCTF{button_button_whose_got_the_button_ed306c10}
```


## Forensics 250: Ext Super Magic

**Challenge**

We salvaged a ruined Ext SuperMagic II-class mech recently and pulled the [filesystem](writeupfiles/ext-super-magic.img) out of the black box. It looks a bit corrupted, but maybe there's something interesting in there.

You can also find it in `/problems/ext-super-magic_4_f196e59a80c3fdac37cc2f331692ef13` on the shell server.

**Solution**

**Flag**
```

```



## Forensics 250: Lying out

**Challenge**
Some odd [traffic](./writeupfiles/traffic.png) has been detected on the network, can you identify it? More info here. Connect with nc 2018shell1.picoctf.com 50875 to help us answer some questions.

**Solution**

```
You'll need to consult the file `traffic.png` to answer the following questions.
Which of these logs have significantly higher traffic than is usual for their time of day? You can see usual traffic on the attached plot. There may be multiple logs with higher than usual traffic, so answer all of them! Give your answer as a list of `log_ID` values separated by spaces. For example, if you want to answer that logs 2 and 7 are the ones with higher than usual traffic, type 2 7.
    log_ID      time  num_IPs
0        0  00:00:00     9552
1        1  02:30:00    11573
2        2  06:00:00    10381
3        3  07:00:00    11674
4        4  07:00:00    10224
5        5  07:30:00    10966
6        6  16:00:00     9685
7        7  17:45:00    15875
8        8  18:00:00    11889
9        9  19:15:00    11935
10      10  19:30:00    11191
11      11  20:30:00     9952
12      12  20:45:00     9898
13      13  22:45:00    11609
1 3 7 13
Correct!
Great job. You've earned the flag: picoCTF{w4y_0ut_ff5bd19c}
```

**Flag**
```
picoCTF{w4y_0ut_ff5bd19c}
```

### Web 250: The Vault

**Challenge**

 There is a website running at http://2018shell1.picoctf.com:64349 (link). Try to see if you can login!

**Solution**

we get the php source code

```php
<?php
  ini_set('error_reporting', E_ALL);
  ini_set('display_errors', 'On');

  include "config.php";
  $con = new SQLite3($database_file);

  $username = $_POST["username"];
  $password = $_POST["password"];
  $debug = $_POST["debug"];
  $query = "SELECT 1 FROM users WHERE name='$username' AND password='$password'";

  if (intval($debug)) {
    echo "<pre>";
    echo "username: ", htmlspecialchars($username), "\n";
    echo "password: ", htmlspecialchars($password), "\n";
    echo "SQL query: ", htmlspecialchars($query), "\n";
    echo "</pre>";
  }

  //validation check
  $pattern ="/.*['\"].*OR.*/i";
  $user_match = preg_match($pattern, $username);
  $password_match = preg_match($pattern, $username);
  if($user_match + $password_match > 0)  {
    echo "<h1>SQLi detected.</h1>";
  }
  else {
    $result = $con->query($query);
    $row = $result->fetchArray();

    if ($row) {
      echo "<h1>Logged in!</h1>";
      echo "<p>Your flag is: $FLAG</p>";
    } else {
      echo "<h1>Login failed.</h1>";
    }
  }

?>

```

we enter anything for the username and `' or 1 --` or `' or 'x'='x` for the password to get the flag

```
Logged in!

Your flag is: picoCTF{w3lc0m3_t0_th3_vau1t_e4ca2258}
```


**Flag**
```
picoCTF{w3lc0m3_t0_th3_vau1t_e4ca2258}
```

## General Skills 250: absolutely relative

**Challenge**
In a filesystem, everything is relative ¯\_(ツ)_/¯. Can you find a way to get a flag from this program? You can find it in /problems/absolutely-relative_1_15eb86fcf5d05ec169cc417d24e02c87 on the shell server. Source.

**Solution**
They provide the [source](./writeupfiles/absoluterelative.c) of absolutely-relative, it checks for a file named `./permission.txt` with the contents `yes`.

```
hxr@pico-2018-shell-1:~$ echo -n 'yes'  > permission.txt
hxr@pico-2018-shell-1:~$ /problems/absolutely-relative_1_15eb86fcf5d05ec169cc417d24e02c87/absolutely-relative
You have the write permissions.
picoCTF{3v3r1ng_1$_r3l3t1v3_a97be50e}
```


**Flag**
```
picoCTF{3v3r1ng_1$_r3l3t1v3_a97be50e}
```

## Cryptography 250: caesar cipher 2

**Challenge**

Can you help us decrypt [this message](writeupfiles/ciphertext2)? We believe it is a form of a caesar cipher.

You can find the ciphertext in `/problems/caesar-cipher-2_3_4a1aa2a4d0f79a1f8e9a29319250740a` on the shell server.

```
4-'3evh?'c)7%t#e-r,g6u#.9uv#%tg2v#7g'w6gA
```


**Solution**

**Flag**
```

```

## Binary Exploitation 250: got-2-learn-libc

**Challenge**

This [program](writeupfiles/vuln3) gives you the address of some system calls. Can you get a shell?

You can find the program in `/problems/got-2-learn-libc_2_2d4a9f3ed6bf71e90e938f1e020fb8ee` on the shell server.

[Source](writeupfiles)

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

#define BUFSIZE 148
#define FLAGSIZE 128

char useful_string[16] = "/bin/sh"; /* Maybe this can be used to spawn a shell? */


void vuln(){
  char buf[BUFSIZE];
  puts("Enter a string:");
  gets(buf);
  puts(buf);
  puts("Thanks! Exiting now...");
}

int main(int argc, char **argv){

  setvbuf(stdout, NULL, _IONBF, 0);

  // Set the gid to the effective gid
  // this prevents /bin/sh from dropping the privileges
  gid_t gid = getegid();
  setresgid(gid, gid, gid);


  puts("Here are some useful addresses:\n");

  printf("puts: %p\n", puts);
  printf("fflush %p\n", fflush);
  printf("read: %p\n", read);
  printf("write: %p\n", write);
  printf("useful_string: %p\n", useful_string);

  printf("\n");

  vuln();


  return 0;
}
```

**Solution**

**Flag**
```

```


## Cryptography 250: rsa-madlibs

**Challenge**

We ran into some weird puzzles we think may mean something, can you help me solve one?

Connect with `nc 2018shell1.picoctf.com 40440`

**Solution**

**Flag**
```

```



## General Skills 275: in out error

**Challenge**

Can you utlize stdin, stdout, and stderr to get the flag from this program?
You can also find it in `/problems/in-out-error_2_c33e2a987fbd0f75e78481b14bfd15f4` on the shell server

**Solution**

log into shell and:

```
$ echo "Please may I have the flag?" | ./in-out-error | grep picoCTF
```

**Flag**
```
picoCTF{p1p1ng_1S_4_7h1ng_b6f5a788}
```


## Web Exploitation 300: Artisinal Handcrafted HTTP 3

**Challenge**

We found a hidden flag server hiding behind a proxy, but the proxy has some... _interesting_ ideas of what qualifies someone to make HTTP requests. Looks like you'll have to do this one by hand.

Try connecting via `nc 2018shell1.picoctf.com 26431`, and use the proxy to send HTTP requests to `flag.local`. We've also recovered a username and a password for you to use on the login page: `realbusinessuser`/`potoooooooo`.

**Solution**

**Flag**
```

```

## Binary Exploitation 300: echooo

**Challenge**

This program prints any input you give it. Can you [leak](writeupfiles/leak) the flag? Connect with `nc 2018shell1.picoctf.com 23397`.

[Source](writeupfiles/echo.c)

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char **argv){

  setvbuf(stdout, NULL, _IONBF, 0);

  char buf[64];
  char flag[64];
  char *flag_ptr = flag;

  // Set the gid to the effective gid
  gid_t gid = getegid();
  setresgid(gid, gid, gid);

  memset(buf, 0, sizeof(flag));
  memset(buf, 0, sizeof(buf));

  puts("Time to learn about Format Strings!");
  puts("We will evaluate any format string you give us with printf().");
  puts("See if you can get the flag!");

  FILE *file = fopen("flag.txt", "r");
  if (file == NULL) {
    printf("Flag File is Missing. Problem is Misconfigured, please contact an Admin if you are running this on the shell server.\n");
    exit(0);
  }

  fgets(flag, sizeof(flag), file);

  while(1) {
    printf("> ");
    fgets(buf, sizeof(buf), stdin);
    printf(buf);
  }
  return 0;
}
```

**Solution**

**Flag**
```

```


## General Skills 300: learn gdb

**Challenge**

Using a debugging tool will be extremely useful on your missions. Can you run [this program](writeupfiles/run2) in gdb and find the flag?

You can find the file in `/problems/learn-gdb_2_32e08c18932eb88649e9b97f3020b9f5` on the shell server.

**Solution**



**Flag**
```

```


## Web Exploitation 350: Flaskcards

**Challenge**

We found [this fishy website](http://2018shell1.picoctf.com:51878/) for flashcards that we think may be sending secrets. Could you take a look?


**Solution**

**Flag**
```

```


## Binary Exploitation 350: got-shell?

**Challenge**

Can you authenticate to [this service](writeupfiles/auth2) and get the flag?

Connect to it with `nc 2018shell1.picoctf.com 54664`.

[Source](writeupfiles/auth2.c)

```c
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <sys/types.h>

void win() {
  system("/bin/sh");
}

int main(int argc, char **argv) {

  setvbuf(stdout, NULL, _IONBF, 0);

  char buf[256];

  unsigned int address;
  unsigned int value;

  puts("I'll let you write one 4 byte value to memory. Where would you like to write this 4 byte value?");

  scanf("%x", &address);

  sprintf(buf, "Okay, now what value would you like to write to 0x%x", address);
  puts(buf);

  scanf("%x", &value);

  sprintf(buf, "Okay, writing 0x%x to 0x%x", value, address);
  puts(buf);

  *(unsigned int *)address = value;

  puts("Okay, exiting now...\n");
  exit(1);

}
```

**Solution**

**Flag**
```

```


## General Skills 350: roulette

**Challenge**

This Online [Roulette Service](writeupfiles/roulette) is in Beta. Can you find a way to win $1,000,000,000 and get the flag? Source.

Connect with `nc 2018shell1.picoctf.com 48312`

**Solution**

**Flag**
```

```


## Forensics 400: Malware Shops

**Challenge**

There has been some malware detected, can you help with the analysis?

![](writeupfiles/plot.png)

Connect with `nc 2018shell1.picoctf.com 46168`.

More info:

```
You've been given a dataset of about 500 malware binary files that have
been found on your organization's computers. Whenever you find more malware,
you want to be able to tell if you've seen a file like this before.

Binary files are hard to understand. When code is written, there are several
more steps before it becomes software. Some parts of this process are:
i.  Compiling, which turns human-readable source code into assembly code.
    Assembly code is difficult for humans to read, but it closely mimics the most
    basic raw instructions that a computer needs in order to run a program.
ii. Assembling, which turns assembly code into machine code. Machine code is
    impossible for humans to read, but this representation is what a computer
    actually needs to execute.

The malware binary files that were given to you to analyze are all in machine
code, but luckily, you were able to run a program called a disassembler to
turn them back into assembly code.

Assembly code contains *instructions* which tell a computer how to update
its own internal memory, and its progress through reading the assembly code
itself. For instance, the `jmp` instruction means "jump to executing a
different instruction", and the `add` instruction means "add two numbers and
store the result in memory".

Your dataset contains data about all the malware files, including their
file hash, which serves as a name, and the counts of all of the `jmp` and `add`
instructions.

Malware attackers often release many slightly different versions of the same
malware over time. These different versions always have totally different
hashes, but they are likely to have similar numbers of `jmp` and `add`
instructions.
```

**Solution**

**Flag**
```

```


## Web Exploitation 400: fancy-alive-monitoring

**Challenge**

One of my school mate developed an alive monitoring tool. Can you get a flag from http://2018shell1.picoctf.com:56517 ?

**Solution**

**Flag**
```

```


## General Skills 400: Store

**Challenge**

We started a little store, can you buy the flag? [Source.](./writeupfiles/store.c) Connect with 2018shell1.picoctf.com 5795.

**Solution**

Playing around with a local copy, trying to buy 1000000000 imitation flags
overflows and wraps around, giving you a very positive balance. Then you can
just buy the real flag.

```
Welcome to the Store App V1.0
World's Most Secure Purchasing App
[1] Check Account Balance
[2] Buy Stuff
[3] Exit
 Enter a menu selection
2
Current Auctions1
[1] I Can't Believe its not a Flag!
[2] Real Flag
Imitation Flags cost 1000 each, how many would you like?
1000000000
Your total cost is: -727379968
Your new balance: 727381068
Welcome to the Store App V1.0
World's Most Secure Purchasing App
[1] Check Account Balance
[2] Buy Stuff
[3] Exit
 Enter a menu selection
2
Current Auctions
[1] I Can't Believe its not a Flag!
[2] Real Flag
2
A genuine Flag costs 100000 dollars, and we only have 1 in stock
Enter 1 to purchase1
YOUR FLAG IS: picoCTF{numb3r3_4r3nt_s4f3_dbd42a50}
```

**Flag**
```
picoCTF{numb3r3_4r3nt_s4f3_dbd42a50}
```



## Web Exploitation 500: Secure Logon

**Challenge**

Uh oh, the login page is more secure... I think. http://2018shell1.picoctf.com:46026 (link).

[Source](writeupfiles/server_noflag.py)


```python
from flask import Flask, render_template, request, url_for, redirect, make_response, flash
import json
from hashlib import md5
from base64 import b64decode
from base64 import b64encode
from Crypto import Random
from Crypto.Cipher import AES

app = Flask(__name__)
app.secret_key = 'seed removed'
flag_value = 'flag removed'

BLOCK_SIZE = 16  # Bytes
pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * \
                chr(BLOCK_SIZE - len(s) % BLOCK_SIZE)
unpad = lambda s: s[:-ord(s[len(s) - 1:])]


@app.route("/")
def main():
    return render_template('index.html')

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.form['user'] == 'admin':
        message = "I'm sorry the admin password is super secure. You're not getting in that way."
        category = 'danger'
        flash(message, category)
        return render_template('index.html')
    resp = make_response(redirect("/flag"))

    cookie = {}
    cookie['password'] = request.form['password']
    cookie['username'] = request.form['user']
    cookie['admin'] = 0
    print(cookie)
    cookie_data = json.dumps(cookie, sort_keys=True)
    encrypted = AESCipher(app.secret_key).encrypt(cookie_data)
    print(encrypted)
    resp.set_cookie('cookie', encrypted)
    return resp

@app.route('/logout')
def logout():
    resp = make_response(redirect("/"))
    resp.set_cookie('cookie', '', expires=0)
    return resp

@app.route('/flag', methods=['GET'])
def flag():
  try:
      encrypted = request.cookies['cookie']
  except KeyError:
      flash("Error: Please log-in again.")
      return redirect(url_for('main'))
  data = AESCipher(app.secret_key).decrypt(encrypted)
  data = json.loads(data)

  try:
     check = data['admin']
  except KeyError:
     check = 0
  if check == 1:
      return render_template('flag.html', value=flag_value)
  flash("Success: You logged in! Not sure you'll be able to see the flag though.", "success")
  return render_template('not-flag.html', cookie=data)

class AESCipher:
    """
    Usage:
        c = AESCipher('password').encrypt('message')
        m = AESCipher('password').decrypt(c)
    Tested under Python 3 and PyCrypto 2.6.1.
    """

    def __init__(self, key):
        self.key = md5(key.encode('utf8')).hexdigest()

    def encrypt(self, raw):
        raw = pad(raw)
        iv = Random.new().read(AES.block_size)
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return b64encode(iv + cipher.encrypt(raw))

    def decrypt(self, enc):
        enc = b64decode(enc)
        iv = enc[:16]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return unpad(cipher.decrypt(enc[16:])).decode('utf8')

if __name__ == "__main__":
    app.run()
```


**Solution**

**Flag**
```

```

## Genral Skills 500: script me

Can you understand the language and answer the questions to retrieve the flag?

Connect to the service with `nc 2018shell1.picoctf.com 1542`

**Solution**

**Flag**
```

```

## Forensics 550: LoadSomeBits

**Challenge**

Can you find the flag encoded inside this image? You can also find the file in /problems/loadsomebits_1_5ccf71e5726692c713405bb17da5cb37 on the shell server.

![](writeupfiles/pico2018-special-logo.bmp)

**Solution**

**Flag**
```

```

## Web Exploitation 600: Help Me Reset

**Challenge**

There is a website running at http://2018shell1.picoctf.com:54584 (link). We need to get into any user for a flag!

**Solution**

**Flag**
```

```


## Web Exploitation 650: A Simple Question

**Challenge**

There is a website running at http://2018shell1.picoctf.com:36052 (link). Try to see if you can answer its question.

**Solution**

**Flag**
```

```

## Web Exploitation 800: LambDash 3

**Challenge**

C? Who uses that anymore. If we really want to be secure, we should all start learning lambda calculus. http://2018shell1.picoctf.com:43607 (link)

**Solution**

**Flag**
```

```

## General Skills 900: Dog or Frog


**Challenge**

Dressing up dogs are kinda the new thing, see if you can get this lovely girl ready for her costume party.

[Dog Or Frog](http://2018shell1.picoctf.com:18466/)

**Solution**

**Flag**
```

```
