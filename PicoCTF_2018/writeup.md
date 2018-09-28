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

