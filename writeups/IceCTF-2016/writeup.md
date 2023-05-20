# IceCTF 2016

2-Week-long Icelandic CTF in August 2016

Team: Galaxians

## Overview
```
Title                          Category       Points  Flag
------------------------------ -------------- ------- -----------------------------
STAGE 1
Hello World                    Misc           1       IceCTF{h3l10_wr0ld}
Spotlight                      Web            10      IceCTF{5tup1d_d3v5_w1th_th31r_l095}
All your Base are belong to us Misc           15      IceCTF{al1_my_bases_are_yours_and_all_y0ur_bases_are_mine}
Rotated                        Cryptography   20      IceCTF{wait_one_plus_1_is_3?}
Scavenger Hunt                 Misc           20      IceCTF{Y0u_c4n7_533_ME_iM_h1Din9}
Move Along                     Web            30      IceCTF{tH3_c4t_15_Ou7_oF_THe_b49}
Substituted                    Cryptography   30      IceCTF{always_listen_to_your_substitute_flags}
IRC I                          Misc           35      IceCTF{pL3AsE_D0n7_5h4re_fL495_JUsT_doNT}
Alien Message                  Cryptography   40      icectf{good_n3wz_3veryon3_1_l1k3_fu7ur4ma_4nd_th3ir_4maz1ng_3as7er_39g5}
Time Traveller                 Forensics      45      IceCTF{Th3y'11_n3v4r_f1|\|d_m4h_fl3g_1n_th3_p45t}

STAGE 2
Complacent                     Recon          40      IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
Search                         Misc           40      IceCTF{flag5_all_0v3r_the_Plac3}
Hidden in plain sight          RE             45      IceCTF{look_mom_I_found_it}
Toke                           Web            45      IceCTF{jW7_t0K3ns_4Re_nO_p14CE_fOR_53CrE7S}
Flag Storage                   Web            50      IceCTF{why_would_you_even_do_anything_client_side}
RSA?                           Cryptography   50      IceCTF{falls_apart_so_easily_and_reassembled_so_crudely}
Demo                           Pwn            55
Thor's a hacker now            Misc           55      IceCTF{h3XduMp1N9_l1K3_A_r341_B14Ckh47}
Dear Diary                     Pwn            60
Exposed!                       Web            60      IceCTF{secure_y0ur_g1t_repos_pe0ple}
IRC II                         Misc           60
RSA                            Cryptography   60      IceCTF{rsa_is_awesome_when_used_correctly_but_horrible_when_not}
Smashing Profit                Pwn            60
Miners!                        Web            65
Over the Hill                  Cryptography   65      IceCTF{linear_algebra_plus_led_zeppelin_are_a_beautiful_m1xture}
Kitty                          Web            70      IceCTF{i_guess_hashing_isnt_everything_in_this_world}

STAGE 3
Audio Problems                 Forensics      50      IceCTF{y0U_b3t7Er_l15TeN_cL053LY}
Corrupt Transmission           Forensics      50      IceCTF{t1s_but_4_5cr4tch}
Vape Nation                    Stego          50      IceCTF{420_CuR35_c4NCEr}
Blue Monday                    Misc           60      IceCTF{HAck1n9_mU5Ic_w17h_mID15_L3t5_H4vE_a_r4v3}
R.I.P. Transmission            Forensics      65
Round Rabins!                  Cryptography   70
ChainedIn                      Web            75
Drumpf Hotels                  Pwn            75
ROPi                           Pwn            75
A Strong Feeling               RE             80
Pretty Pixels                  Stego          80
Matrix                         Misc           85      IceCTF{1F_y0U_l0oK_c1Os3lY_EV3rY7h1N9_i5_1s_4nD_0s}
So Close                       Pwn            85
l33tcrypt                      Cryptography   90
Quine                          Pwn            90
RSA 2                          Cryptography   90      IceCTF{next_time_check_your_keys_arent_factorable}
Poke-A-Mango                   RE             95
Geocities                      Web            100
Intercepted Conv. Pt 1         Forensics      110     IceCTF{Wh0_l1K3S_qw3R7Y_4NYw4y5}
Intercepted Conv. Pt 2         Forensics      125
Contract                       Cryptography   130
Sand Castles on the Beach      Cryptography   140

STAGE 4
```

## Challenge 42: Title
**Challenge**
**Solution**
**Flag**
```
flag
```

## Stage 1 Misc: Hello Wold!

**Challenge**

In this capture the flag competiton you're hunting for these strings, we call them "flags". These flags always begin with "IceCTF{" followed by a message in 1337sp34k and end with "}". Here's an example flag "IceCTF{h3l10_wr0ld}". Try submitting it through the text box down below!

**Solution**

Dummy challenge, just enter flag

**Flag**
```
IceCTF{h3l10_wr0ld}
```

## Stage 1 Web: Spotlight
**Challenge**

Someone turned out the lights and now we can't find anything. Send halp!

**Solution**

They built a really nice page with a "spotlight" feature that follows your
mouse around. However the flag is in a console.log statement in the associated
[javascript file](./writeupfiles/spotlight.js).

```
console.log("DEBUG: IceCTF{5tup1d_d3v5_w1th_th31r_l095}");
```

**Flag**

```
IceCTF{5tup1d_d3v5_w1th_th31r_l095}
```

## Stage 1 Misc: All your Base are belong to us

**Challenge**
What a mess... we got a raw flag but now what do we do...

```
01001001 01100011 01100101 01000011 01010100 01000110 01111011 01100001 01101100 00110001 01011111
01101101 01111001 01011111 01100010 01100001 01110011 01100101 01110011 01011111 01100001 01110010
01100101 01011111 01111001 01101111 01110101 01110010 01110011 01011111 01100001 01101110 01100100
01011111 01100001 01101100 01101100 01011111 01111001 00110000 01110101 01110010 01011111 01100010
01100001 01110011 01100101 01110011 01011111 01100001 01110010 01100101 01011111 01101101 01101001
01101110 01100101 01111101
```

**Solution**

convert binary to ascii to get flag

**Flag**
```
IceCTF{al1_my_bases_are_yours_and_all_y0ur_bases_are_mine}
```

## Stage 1 Cryptography: Rotated!
**Challenge**

They went and ROTated the flag by 5 and then ROTated it by 8! The scoundrels! Anyway once they were done this was all that was left `VprPGS{jnvg_bar_cyhf_1_vf_3?}`

**Solution**

[rot13](https://duckduckgo.com/?q=rot13+VprPGS%7Bjnvg_bar_cyhf_1_vf_3%3F%7D&t=canonical)

**Flag**

```
IceCTF{wait_one_plus_1_is_3?}
```

## Stage 1 Misc: Scavenger Hunt

**Challenge**

There is a flag hidden somewhere on our website, do you think you can find it? Good luck!

**Solution**

Found this via their GitHub page, looked at recent commits and saw one with commit message `aoeuMyhandsaretypingwords` looked suspicious, saw they edited the sponsors.html page (https://github.com/IceCTF/ctf-landing/commit/fe9c68d06680156f32f9963fa8490810e28ebdd2)

**Flag**
```
IceCTF{Y0u_c4n7_533_ME_iM_h1Din9}
```

## Stage 1 Web: Move Along
**Challenge**

[This](http://move-along.vuln.icec.tf/) site seems awfully suspicious, do you think you can figure out what they're hiding?

**Solution**

We get to a web page with just an image

```html
<!DOCTYPE html>
<html>
    <head>
        <title>IceCTF 2016 - Move Along</title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
    </head>
    <body>
        <img src="move_along/nothing-to-see-here.jpg"></img>
    </body>
</html>
```

we explore a bit and find that the directory `move_along` has another image in it

![](writeupfiles/secret.jpg)

which contains the flag

**Flag**

```
IceCTF{tH3_c4t_15_Ou7_oF_THe_b49}
```

## Stage 1 Cryptography: Substituted
**Challenge**

We got a substitute flag, I hear they are pretty lax on the rules...

```
Lw!

Gyzvecy ke WvyVKT!

W'zz by reso dsbdkwksky tzjq teo kly ujr. Teo keujr, gy joy dksurwmq bjdwv vorakeqojalr jmu wkd jaazwvjkwemd.
Vorakeqojalr ljd j zemq lwdkeor, jzklesql gwkl kly juxymk et vecaskyod wk ljd qekkym oyjzzr vecazwvjkyu.
Decy dwcazy ezu vwalyod joy kly Vjydjo vwalyo, kly Xwqymyoy vwalyo, kly dsbdkwkskwem vwalyo, glwvl wd klwd
emy, jmu de em. Jzcedk jzz et klydy vwalyod joy yjdwzr boeiym keujr gwkl kly lyza et vecaskyod. Decy myg
ymvorakwem cykleud joy JYD, kly vsooymk dkjmujou teo ymvorakwem, jzemq gwkl ODJ. Vorakeqojalr wd j xjdk
twyzu jmu wd xyor wmkyoydkwmq klesql. De iwvi bjvi, oyju sa em decy veez vwalyod jmu ljxy tsm!

El jmu teo reso oyveoud cr mjcy wd WvyVKT{jzgjrd_zwdkym_ke_reso_dsbdkwksky_tzjqd}.
```

**Solution**

Substitution cipher, decode to get

```
Hi!

Welcome to IceCTF!  

I'll be your substitute flag for the day. For today, we are studying basic cryptography and its applications.
Cryptography has a long history, although with the advent of computers it has gotten really complicated.
Some simple old ciphers are the Caesar cipher, the Vigenere cipher, the substitution cipher, which is this
one, and so on. Almost all of these ciphers are easily broken today with the help of computers. Some new
encryption methods are AES, the current standard for encryption, along with RSA. Cryptography is a vast
field and is very interesting though. So kick back, read up on some cool ciphers and have fun!  

Oh and for your records my name is IceCTF{always_listen_to_your_substitute_flags}.
```

**Flag**

```
IceCTF{always_listen_to_your_substitute_flags}
```

## Stage 1 Misc: IRC 1

**Challenge**

connecting to the irc server and doing a `/list` will print

```
16:30:21 glitch.is  -- | #e1fa981276_flagshare(7): [+nt] FlagShare! You want flags? I got 'em! IceCTF{pL3AsE_D0n7_5h4re_fL495_JUsT_doNT}
```

**Flag**

```
IceCTF{pL3AsE_D0n7_5h4re_fL495_JUsT_doNT}
```

## Stage 1 Forensics: Time Traveller
**Challenge**

I can assure you that the flag was on [this website](http://time-traveler.icec.tf/) at some point in time.

**Solution**

https://web.archive.org/web/20160601212948/http://time-traveler.icec.tf/

**Flag**

```
IceCTF{Th3y'11_n3v4r_f1|\|d_m4h_fl3g_1n_th3_p45t}
```

## Stage 1 Cryptography: Alien Messsage
**Challenge**

We found this suspicous image online and it looked like it had been planted there by an alien life form. Can you see if you can figure out what they're trying to tell us?

![](writeupfiles/alien_message.png)

**Solution**

Some googling reveals this to be a fictional alphabet used in futurama

![](writeupfiles/futurama.gif)


**Flag**

```
icectf{good_n3wz_3veryon3_1_l1k3_fu7ur4ma_4nd_th3ir_4maz1ng_3as7er_39g5}
```

## Stage 2 Recon: Complacent
**Challenge**

These silly bankers have gotten pretty complacent with their self signed SSL certificate. I wonder if there's anything in there. complacent.vuln.icec.tf

**Solution**

The flag was in the SSL certificate

```
Issued To
Common Name (CN)	complacent.icec.tf
Organization (O)	Secret IceCTF Buisness Corp
Organizational Unit (OU)	Flag: IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
Serial Number	00:DF:8D:FC:51:A7:A2:00:7F

Issued By
Common Name (CN)	complacent.icec.tf
Organization (O)	Secret IceCTF Buisness Corp
Organizational Unit (OU)	Flag: IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
Validity Period

Issued On	Tuesday, August 2, 2016 at 9:59:11 PM
Expires On	Thursday, July 9, 2116 at 9:59:11 PM
Fingerprints

SHA-256 Fingerprint	76 00 68 BF 5C 8F 04 90 D6 4E 1E 21 82 14 50 BC
49 95 B1 FB F0 AC 95 5A 75 BD F8 6B 5C 93 20 7B
SHA-1 Fingerprint	75 29 19 51 91 06 9E D5 D2 68 8C 94 EA 01 78 AB
8C D1 76 33
```

**Flag**

```
IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
```

## Stage 2 Misc: Search
**Challenge**

There's something about this domain... search.icec.tf, I don't see anything, but maybe its all about the conTEXT.

**Solution**

Opening the link in the browser shows an error for DNS name not resolved.
Interesting... and the comment says `conTEXT` which suggests a DNS TXT record.

```
$ dig search.icec.tf TXT

; <<>> DiG 9.9.5-11ubuntu1.3-Ubuntu <<>> search.icec.tf TXT
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 43529
;; flags: qr rd ra; QUERY: 1, ANSWER: 1, AUTHORITY: 0, ADDITIONAL: 1

;; OPT PSEUDOSECTION:
; EDNS: version: 0, flags:; udp: 512
;; QUESTION SECTION:
;search.icec.tf.                        IN      TXT

;; ANSWER SECTION:
search.icec.tf.         299     IN      TXT     "IceCTF{flag5_all_0v3r_the_Plac3}"

;; Query time: 32 msec
;; SERVER: 8.8.8.8#53(8.8.8.8)
;; WHEN: Fri Aug 12 19:42:20 UTC 2016
;; MSG SIZE  rcvd: 88
```


**Flag**

```
IceCTF{flag5_all_0v3r_the_Plac3}
```

## Stage 2 Reverse Engineering: Hidden in Plain Sight

**Challenge**

Make sure you take a real close look at it, it should be right there! /home/plain_sight/ or download it [here](writeupfiles/hidden)

**Solution**

We get an ELF binary, when we run it we get

```
Make sure you pay close attention!
Okey, ready? (y/n)
Sorry, too late!
....
Dang, that was fast! Did you get it?
```

We disassemble the file and find this segment of asm code

```asm
[..]
8048512:	50                   	push   eax
8048513:	e8 38 fe ff ff       	call   8048350 <fflush@plt>
8048518:	83 c4 10             	add    esp,0x10
804851b:	b0 49                	mov    al,0x49
804851d:	b0 63                	mov    al,0x63
804851f:	b0 65                	mov    al,0x65
8048521:	b0 43                	mov    al,0x43
8048523:	b0 54                	mov    al,0x54
8048525:	b0 46                	mov    al,0x46
8048527:	b0 7b                	mov    al,0x7b
8048529:	b0 6c                	mov    al,0x6c
804852b:	b0 6f                	mov    al,0x6f
804852d:	b0 6f                	mov    al,0x6f
804852f:	b0 6b                	mov    al,0x6b
8048531:	b0 5f                	mov    al,0x5f
8048533:	b0 6d                	mov    al,0x6d
8048535:	b0 6f                	mov    al,0x6f
8048537:	b0 6d                	mov    al,0x6d
8048539:	b0 5f                	mov    al,0x5f
804853b:	b0 49                	mov    al,0x49
804853d:	b0 5f                	mov    al,0x5f
804853f:	b0 66                	mov    al,0x66
8048541:	b0 6f                	mov    al,0x6f
8048543:	b0 75                	mov    al,0x75
8048545:	b0 6e                	mov    al,0x6e
8048547:	b0 64                	mov    al,0x64
8048549:	b0 5f                	mov    al,0x5f
804854b:	b0 69                	mov    al,0x69
804854d:	b0 74                	mov    al,0x74
804854f:	b0 7d                	mov    al,0x7d
8048551:	c7 45 f4 00 00 00 00 	mov    DWORD PTR [ebp-0xc],0x0
8048558:	eb 2f                	jmp    8048589 <main+0xde>
[..]
```

converting these hex values to characters gives the flag

**Flag**

```
IceCTF{look_mom_I_found_it}
```

## Stage 2 Web: Toke

**Challenge**

I have a feeling they were pretty high when they made this website... http://toke.vuln.icec.tf/

**Solution**

there was a registration and login option, which gave us a cookie containing the base64 encoded flag

```
jwt_token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmbGFnIjoiSWNlQ1RGe2pXN190MEszbnNfNFJlX25PX3AxNENFX2ZPUl81M0NyRTdTfSIsInVzZXIiOiInIn0.8dpZppOpfKijXcgbpzx0QtVU91xDvCwsRTzc5lCadlE
```

**Flag**
```
IceCTF{jW7_t0K3ns_4Re_nO_p14CE_fOR_53CrE7S}
```


## Stage 2 Web: Flag Storage

**Challenge**

What a cheat, I was promised a flag and I can't even log in. Can you get in for me? flagstorage.vuln.icec.tf. They seem to hash their passwords, but I think the problem is somehow related to [this](https://en.wikipedia.org/wiki/SQL_injection).

**Solution**

We have to bypass the login system using SQL injections, page looks as follows:

```html
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Log In</title>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" />
</head>
<body>
    <div class="container">
 <form method="post" action="login.php" id="form">
<label for="username">Username: </label>
<input class="u-full-width" type="text" name="username" placeholder="Username" />
<label for="password">Password: </label>
<input id="password_plain" class="u-full-width" type="password" name="password_plain" placeholder="Password" />
<input id="password" type="hidden" name="password"/>
<input type="submit" value="Log In">
</form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jsSHA/2.2.0/sha.js"></script>
<script>
$(function(){
    var updatePassword = function(e){
        // hash client side for better security, never leak the pw over the wire
        var sha = new jsSHA("SHA-256", "TEXT");
        sha.update($(this).val());
        $("#password").val(sha.getHash("HEX"));
    };
    $("#password_plain").on("change", updatePassword);
    $("#form").on("submit", updatePassword);
});
</script>
</body>
</html>
```

The password is hashed client side, so we just disable that script, and enter `' or 'x'='x` for both username and password to log in and get our flag:

```
Logged in!
Your flag is: IceCTF{why_would_you_even_do_anything_client_side}
```

**Flag**
```
IceCTF{why_would_you_even_do_anything_client_side}
```

## Stage 2 Cryptography: RSA?

**Challenge**

John was messing with RSA again... he encrypted our flag! I have a strong feeling he had no idea what he was doing however, can you get the flag for us?

```
N=0x180be86dc898a3c3a710e52b31de460f8f350610bf63e6b2203c08fddad44601d96eb454a34dab7684589bc32b19eb27cffff8c07179e349ddb62898ae896f8c681796052ae1598bd41f35491175c9b60ae2260d0d4ebac05b4b6f2677a7609c2fe6194fe7b63841cec632e3a2f55d0cb09df08eacea34394ad473577dea5131552b0b30efac31c59087bfe603d2b13bed7d14967bfd489157aa01b14b4e1bd08d9b92ec0c319aeb8fedd535c56770aac95247d116d59cae2f99c3b51f43093fd39c10f93830c1ece75ee37e5fcdc5b174052eccadcadeda2f1b3a4a87184041d5c1a6a0b2eeaa3c3a1227bc27e130e67ac397b375ffe7c873e9b1c649812edcd

e=0x1

c=0x4963654354467b66616c6c735f61706172745f736f5f656173696c795f616e645f7265617373656d626c65645f736f5f63727564656c797d
```

**Solution**

when you use `e=1` you are not encrypting anything (`m^1 mod N = m`) so the ciphertext was same as plaintext, just convert hex to ascii to get the flag.

**Flag**

```
IceCTF{falls_apart_so_easily_and_reassembled_so_crudely}
```


## Stage 2 Pwn: Demo
**Challenge**

I found this awesome premium shell, but my demo version just ran out... can you help me crack it? `/home/demo/` on the shell

**Solution**

The [c source code](writeupfiles/demo.c) is provided alongside. It checks
the environment variable for `_` which is the name of the executable being
run, takes the basename of that, and compares against "icesh", exiting if
not equal, otherwise giving root permissions which allow access to `/home/demo/flag.txt`

So, we create a symlink to the file with a new name (in order to inherit
permissions of that file), and then run it which pops open a shell.

```
[ctf-98836@icectf-shell-2016 ~]$ ln -s /home/demo/demo icesh
[ctf-98836@icectf-shell-2016 ~]$ ./icesh
$ bash
ctf-98836@icectf-shell-2016:~$ cat /home/demo/flag.txt
IceCTF{wH0_WoU1d_3vr_7Ru5t_4rgV}
```

**Flag**

```
IceCTF{wH0_WoU1d_3vr_7Ru5t_4rgV}
```


## Stage 2 Misc: Thor's a hacker now
**Challenge**

Thor has been staring at this for hours and he can't make any sense out of it, can you help him figure out what it is? [thor.txt](./writeupfiles/thor_7101f3b9690d5dc6c3afefa49d82e0526b278ec1c564139369ad22c28721d4cf.txt)

**Solution**

Looking at the file it looks like the output of `od -tx2` or `xxd`. A bit
of googling reveals that `xxd` has a flag `-r` which reverses the output.
(I.e. `cat a | xxd | xxd -r > b`, `a == b`)

Once this is done

```console
$ cat writeupfiles/thor_7101f3b9690d5dc6c3afefa49d82e0526b278ec1c564139369ad22c28721d4cf.txt | xxd -r > tmp
$ file tmp
tmp: lzip compressed data, version: 1
# Apparently I need "lzip" and the file to be named tmp.lz to decompress nicely. There's a special circle of hell for software which cares about file extensions.
$ cat tmp | lzip -d > tmp2
$ file tmp2
tmp2: JPEG image data, JFIF standard 1.01, resolution (DPI), density 72x72, segment length 16, Exif Standard: [TIFF image data, little-endian, direntries=5, xresolution=74, yresolution=82, resolutionunit=2, software=GIMP 2.8.18], progressive, precision 8, 1600x680, frames 3
```

![](./writeupfiles/thor.jpg)

**Flag**

```
IceCTF{h3XduMp1N9_l1K3_A_r341_B14Ckh47}
```

## Stage 2 Web: Exposed!
**Challenge**

John is pretty happy with himself, he just made his first [website](http://exposed.vuln.icec.tf/)! He used all the hip and cool systems, like NginX, PHP and Git! Everyone is so happy for him, but can you get him to give you the flag?

**Solution**

Git is a pretty big hint here, it seemed safe to assume that if he was deploying with git, then the web directory was git.

```console
$ git clone http://exposed.vuln.icec.tf/
Cloning into 'exposed.vuln.icec.tf'...
fatal: repository 'http://exposed.vuln.icec.tf/' not found

$ git clone http://exposed.vuln.icec.tf/.git
Cloning into 'exposed.vuln.icec.tf'...
Checking connectivity... done.

# Success!

$ git log -p | grep IceCTF
-IceCTF{this_isnt_the_flag_either}
-                    echo 'IceCTF{not_this_flag}';
+IceCTF{this_isnt_the_flag_either}
+                    echo 'IceCTF{not_this_flag}';
-                echo 'Hello World! IceCTF{secure_y0ur_g1t_repos_pe0ple}';
+                echo 'Hello World! IceCTF{secure_y0ur_g1t_repos_pe0ple}';
```

**Flag**

```
IceCTF{secure_y0ur_g1t_repos_pe0ple}
```

## Stage 2 Cryptography: RSA
**Challenge**

This time John managed to use RSA " correctly "&ellipsis; I think he still made some mistakes though.

```
N=0x1564aade6f1b9f169dcc94c9787411984cd3878bcd6236c5ce00b4aad6ca7cb0ca8a0334d9fe0726f8b057c4412cfbff75967a91a370a1c1bd185212d46b581676cf750c05bbd349d3586e78b33477a9254f6155576573911d2356931b98fe4fec387da3e9680053e95a4709934289dc0bc5cdc2aa97ce62a6ca6ba25fca6ae38c0b9b55c16be0982b596ef929b7c71da3783c1f20557e4803de7d2a91b5a6e85df64249f48b4cf32aec01c12d3e88e014579982ecd046042af370045f09678c9029f8fc38ebaea564c29115e19c7030f245ebb2130cbf9dc1c340e2cf17a625376ca52ad8163cfb2e33b6ecaf55353bc1ff19f8f4dc7551dc5ba36235af9758b

e=0x10001

phi=0x1564aade6f1b9f169dcc94c9787411984cd3878bcd6236c5ce00b4aad6ca7cb0ca8a0334d9fe0726f8b057c4412cfbff75967a91a370a1c1bd185212d46b581676cf750c05bbd349d3586e78b33477a9254f6155576573911d2356931b98fe4fec387da3e9680053e95a4709934289dc0bc5cdc2aa97ce62a6ca6ba25fca6ae366e86eed95d330ffad22705d24e20f9806ce501dda9768d860c8da465370fc70757227e729b9171b9402ead8275bf55d42000d51e16133fec3ba7393b1ced5024ab3e86b79b95ad061828861ebb71d35309559a179c6be8697f8a4f314c9e94c37cbbb46cef5879131958333897532fea4c4ecd24234d4260f54c4e37cb2db1a0

d=0x12314d6d6327261ee18a7c6ce8562c304c05069bc8c8e0b34e0023a3b48cf5849278d3493aa86004b02fa6336b098a3330180b9b9655cdf927896b22402a18fae186828efac14368e0a5af2c4d992cb956d52e7c9899d9b16a0a07318aa28c8202ebf74c50ccf49a6733327dde111393611f915f1e1b82933a2ba164aff93ef4ab2ab64aacc2b0447d437032858f089bcc0ddeebc45c45f8dc357209a423cd49055752bfae278c93134777d6e181be22d4619ef226abb6bfcc4adec696cac131f5bd10c574fa3f543dd7f78aee1d0665992f28cdbcf55a48b32beb7a1c0fa8a9fc38f0c5c271e21b83031653d96d25348f8237b28642ceb69f0b0374413308481

c=0x126c24e146ae36d203bef21fcd88fdeefff50375434f64052c5473ed2d5d2e7ac376707d76601840c6aa9af27df6845733b9e53982a8f8119c455c9c3d5df1488721194a8392b8a97ce6e783e4ca3b715918041465bb2132a1d22f5ae29dd2526093aa505fcb689d8df5780fa1748ea4d632caed82ca923758eb60c3947d2261c17f3a19d276c2054b6bf87dcd0c46acf79bff2947e1294a6131a7d8c786bed4a1c0b92a4dd457e54df577fb625ee394ea92b992a2c22e3603bf4568b53cceb451e5daca52c4e7bea7f20dd9075ccfd0af97f931c0703ba8d1a7e00bb010437bb4397ae802750875ae19297a7d8e1a0a367a2d6d9dd03a47d404b36d7defe8469
```

**Solution**

We are given `N` and `d`, which is all we need to decrypt (`m = c^d mod N`)

```python
def int2Text(number, size):
    text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
    return text.lstrip("\x00")

N=0x1564aade6f1b9f169dcc94c9787411984cd3878bcd6236c5ce00b4aad6ca7cb0ca8a0334d9fe0726f8b057c4412cfbff75967a91a370a1c1bd185212d46b581676cf750c05bbd349d3586e78b33477a9254f6155576573911d2356931b98fe4fec387da3e9680053e95a4709934289dc0bc5cdc2aa97ce62a6ca6ba25fca6ae38c0b9b55c16be0982b596ef929b7c71da3783c1f20557e4803de7d2a91b5a6e85df64249f48b4cf32aec01c12d3e88e014579982ecd046042af370045f09678c9029f8fc38ebaea564c29115e19c7030f245ebb2130cbf9dc1c340e2cf17a625376ca52ad8163cfb2e33b6ecaf55353bc1ff19f8f4dc7551dc5ba36235af9758b
d=0x12314d6d6327261ee18a7c6ce8562c304c05069bc8c8e0b34e0023a3b48cf5849278d3493aa86004b02fa6336b098a3330180b9b9655cdf927896b22402a18fae186828efac14368e0a5af2c4d992cb956d52e7c9899d9b16a0a07318aa28c8202ebf74c50ccf49a6733327dde111393611f915f1e1b82933a2ba164aff93ef4ab2ab64aacc2b0447d437032858f089bcc0ddeebc45c45f8dc357209a423cd49055752bfae278c93134777d6e181be22d4619ef226abb6bfcc4adec696cac131f5bd10c574fa3f543dd7f78aee1d0665992f28cdbcf55a48b32beb7a1c0fa8a9fc38f0c5c271e21b83031653d96d25348f8237b28642ceb69f0b0374413308481
c=0x126c24e146ae36d203bef21fcd88fdeefff50375434f64052c5473ed2d5d2e7ac376707d76601840c6aa9af27df6845733b9e53982a8f8119c455c9c3d5df1488721194a8392b8a97ce6e783e4ca3b715918041465bb2132a1d22f5ae29dd2526093aa505fcb689d8df5780fa1748ea4d632caed82ca923758eb60c3947d2261c17f3a19d276c2054b6bf87dcd0c46acf79bff2947e1294a6131a7d8c786bed4a1c0b92a4dd457e54df577fb625ee394ea92b992a2c22e3603bf4568b53cceb451e5daca52c4e7bea7f20dd9075ccfd0af97f931c0703ba8d1a7e00bb010437bb4397ae802750875ae19297a7d8e1a0a367a2d6d9dd03a47d404b36d7defe8469

m = pow(c,d,N)

print m
print int2Text(m,1000)
```

this gives as output:

```
3843655260524402023604596518050334491485822435243281383499136834535067384556161639265107050668678281151778547364113350618891028501821403003350717660361853
IceCTF{rsa_is_awesome_when_used_correctly_but_horrible_when_not}
```

**Flag**
```
IceCTF{rsa_is_awesome_when_used_correctly_but_horrible_when_not}
```

## Stage 2 Cryptography: Over The Hill

**Challenge**

Over the hills and far away... many times I've gazed, many times been bitten. Many dreams come true and some have silver linings, I live for my dream of a decrypted flag

```
alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_{}"

matrix = [[54, 53, 28, 20, 54, 15, 12, 7],
          [32, 14, 24, 5, 63, 12, 50, 52],
          [63, 59, 40, 18, 55, 33, 17, 3],
          [63, 34, 5, 4, 56, 10, 53, 16],
          [35, 43, 45, 53, 12, 42, 35, 37],
          [20, 59, 42, 10, 46, 56, 12, 61],
          [26, 39, 27, 59, 44, 54, 23, 56],
          [32, 31, 56, 47, 31, 2, 29, 41]]

ciphertext = "7Nv7}dI9hD9qGmP}CR_5wJDdkj4CKxd45rko1cj51DpHPnNDb__EXDotSRCP8ZCQ"
```

**Solution**

This looks like a [Hill Cipher](https://en.wikipedia.org/wiki/Hill_cipher). We use `sympy` to decode this in python:

```python
from sympy.crypto.crypto import decipher_hill
from sympy import Matrix

alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_{}"
key = Matrix([[54, 53, 28, 20, 54, 15, 12, 7],
          [32, 14, 24, 5, 63, 12, 50, 52],
          [63, 59, 40, 18, 55, 33, 17, 3],
          [63, 34, 5, 4, 56, 10, 53, 16],
          [35, 43, 45, 53, 12, 42, 35, 37],
          [20, 59, 42, 10, 46, 56, 12, 61],
          [26, 39, 27, 59, 44, 54, 23, 56],
          [32, 31, 56, 47, 31, 2, 29, 41]])

ciphertext = "7Nv7}dI9hD9qGmP}CR_5wJDdkj4CKxd45rko1cj51DpHPnNDb__EXDotSRCP8ZCQ"

print decipher_hill(ciphertext, key, alphabet)
```

**Flag**
```
IceCTF{linear_algebra_plus_led_zeppelin_are_a_beautiful_m1xture}
```

## Stage 2 Web: Kitty

**Challenge**

They managed to secure their website this time and moved the hashing to the server :(. We managed to leak this hash of the admin's password though! `c7e83c01ed3ef54812673569b2d79c4e1f6554ffeb27706e98c067de9ab12d1a`. Can you get the flag? kitty.vuln.icec.tf

**Solution**

We examine the source

```html
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Log In</title>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" />
</head>
<body>
    <div class="container">
        <form method="post" action="login.php">
            <label for="username">Username: </label>
            <input class="u-full-width" type="text" name="username" placeholder="Username" required minlength="5" />
            <label for="password">Password: </label>
            <input id="password" class="u-full-width" type="password" name="password" placeholder="Password" required pattern="[A-Z][a-z][0-9][0-9][\?%$@#\^\*\(\)\[\];:]" />
            <input type="submit" value="Log In" />
        </form>
    </div>
</body>
</html>
```

and see that the password is 5 characters following very specific pattern, we can bruteforce this! It seems to refer to a previous challenge where they did SHA-256 hashing client side, so we assume that is what they use here too.

```python
import hashlib
import string

target="c7e83c01ed3ef54812673569b2d79c4e1f6554ffeb27706e98c067de9ab12d1a"

for i in string.uppercase:
    for j in string.lowercase:
        for k in string.digits:
            for l in string.digits:
                for m in list("\?%$@#^*()[];:"):
                    h = hashlib.sha256()
                    h.update(i+j+k+l+m)
                    if h.hexdigest() == target:
                        print i+j+k+l+m
```

This output password `Vo83*` and if we enter this on site with username `admin`, we are given our flag

**Flag**
```
IceCTF{i_guess_hashing_isnt_everything_in_this_world}
```

## Stage 3 Forensics: Audio Problems
**Challenge**

We intercepted [this](writeupfiles/audio_problems.wav) audio signal, it sounds like there could be something hidden in it. Can you take a look and see if you can find anything?

**Solution**

We open the file in audacity, nothing interesting to hear, but the spectrogram looks interesting, we clean it up using following settings to get the flag from spectorgram

```
scale: linear
algorithm: Frequencies
window size: 2048
window type: Hanning
```

![](writeupfiles/spectogram.png)



**Flag**
```
IceCTF{y0U_b3t7Er_l15TeN_cL053LY}
```

## Stage 2 Forensics: Corrupt Transmission

**Challenge**

We intercepted this image, but it must have gotten corrupted during the transmission. Can you try and fix it? corrupt.png

**Solution**

```
$ pngcheck -v corrupt.png
File: corrupt.png (469363 bytes)
  File is CORRUPTED.  It seems to have suffered EOL conversion.
  It was probably transmitted in text mode.
ERRORS DETECTED in corrupt.png
```

We open it in a hex editor and see that the [header is corrupted](https://en.wikipedia.org/wiki/Portable_Network_Graphics), and only thing we had to do was fix this header to fix the file

![](writeupfiles/corrupt_fixed.png)

**Flag**

```
IceCTF{t1s_but_4_5cr4tch}
```

## Stage 2 Stego: Vape Nation

**Challenge**

Go Green!

![](writeupfiles/vape_nation.png)

**Solution**

The hint makes us suspect that we need to look at the green channel, manual inspection show it contains mostly even values, but some uneven ones, so we try outputting where the uneven ones are

```python
from PIL import Image

img = Image.open('vape_nation.png').convert('RGB')
pixels_orig = img.load()

(w,h)=img.size

outimg = Image.new( 'RGB', (w,h), "white")
pixels_out = outimg.load()

for i in range(0,h):
    for j in range(0,w):
      (r,g,b) = pixels_orig[i,j]
      if g%2 != 0:
          pixels_out[i,j] = (0,0,0)

outimg.save("vape_nation_out.png","png")
```

which outputs the following image containing our flag:

![](writeupfiles/vape_nation_out.png)

**Flag**
```
IceCTF{420_CuR35_c4NCEr}
```

## Stage 3 Misc: Blue Monday
**Challenge**

Those who came before me lived through their vocations From the past until
completion, they'll turn away no more And still I find it so hard to say what I
need to say But I'm quite sure that you'll tell me just how I should feel
today.

**Solution**

The attached file was a MIDI file. When imported into Audacity, it displays:

![](writeupfiles/bluemonday.png)

We notice that the number of keys between the first note and the second note is exactly the difference between `I` and `c` in ASCII, so we work out the rest as well:

![](writeupfiles/midiflag.png)

**Flag**
```
IceCTF{HAck1n9_mU5Ic_w17h_mID15_L3t5_H4vE_a_r4v3}
```


## Stage 3 Misc: Matrix

**Challenge**

I like to approach problems with a fresh perspective and try to visualize the problem at hand.

```
0x00000000
0xff71fefe
0x83480082
0xbb4140ba
0xbb6848ba
0xbb4a80ba
0x83213082
0xff5556fe
0xff5556fe
0x00582e00
0x576fb9be
0x707ef09e
0xe74b41d6
0xa82c0f16
0x27a15690
0x8c643628
0xbfcbf976
0x4cd959aa
0x2f43d73a
0x5462300a
0x57290106
0xb02ace5a
0xef53f7fc
0xef53f7fc
0x00402e36
0xff01b6a8
0x83657e3a
0xbb3b27fa
0xbb5eaeac
0xbb1017a0
0x8362672c
0xff02a650
0x00000000
```

**Solution**

The description suggests we need to visualize this information, we convert to binary and see it looks like a QR code:

```
........ ........ ........ ........
11111111 .111...1 1111111. 1111111.
1.....11 .1..1... ........ 1.....1.
1.111.11 .1.....1 .1...... 1.111.1.
1.111.11 .11.1... .1..1... 1.111.1.
1.111.11 .1..1.1. 1....... 1.111.1.
1.....11 ..1....1 ..11.... 1.....1.
11111111 .1.1.1.1 .1.1.11. 1111111.
11111111 .1.1.1.1 .1.1.11. 1111111.
........ .1.11... ..1.111. ........
.1.1.111 .11.1111 1.111..1 1.11111.
.111.... .111111. 1111.... 1..1111.
111..111 .1..1.11 .1.....1 11.1.11.
1.1.1... ..1.11.. ....1111 ...1.11.
..1..111 1.1....1 .1.1.11. 1..1....
1...11.. .11..1.. ..11.11. ..1.1...
1.111111 11..1.11 11111..1 .111.11.
.1..11.. 11.11..1 .1.11..1 1.1.1.1.
..1.1111 .1....11 11.1.111 ..111.1.
.1.1.1.. .11...1. ..11.... ....1.1.
.1.1.111 ..1.1..1 .......1 .....11.
1.11.... ..1.1.1. 11..111. .1.11.1.
111.1111 .1.1..11 1111.111 111111..
111.1111 .1.1..11 1111.111 111111..
........ .1...... ..1.111. ..11.11.
11111111 .......1 1.11.11. 1.1.1...
1.....11 .11..1.1 .111111. ..111.1.
1.111.11 ..111.11 ..1..111 11111.1.
1.111.11 .1.1111. 1.1.111. 1.1.11..
1.111.11 ...1.... ...1.111 1.1.....
1.....11 .11...1. .11..111 ..1.11..
11111111 ......1. 1.1..11. .1.1....
........ ........ ........ ........
```

Let's make a prober image out of it and decode the QR code

```python
from PIL import Image
import qrtools

w=31
h=31
outimg = Image.new( 'RGB', (w,h), "white")
pixels_out = outimg.load()


m=[
0x00000000,0xff71fefe,0x83480082,0xbb4140ba,
0xbb6848ba,0xbb4a80ba,0x83213082,0xff5556fe,
0xff5556fe,0x00582e00,0x576fb9be,0x707ef09e,
0xe74b41d6,0xa82c0f16,0x27a15690,0x8c643628,
0xbfcbf976,0x4cd959aa,0x2f43d73a,0x5462300a,
0x57290106,0xb02ace5a,0xef53f7fc,0xef53f7fc,
0x00402e36,0xff01b6a8,0x83657e3a,0xbb3b27fa,
0xbb5eaeac,0xbb1017a0,0x8362672c,0xff02a650,
0x00000000]

m2 = [ list(bin(m[i])[2:].zfill(33)) for i in range(0,len(m)) ]

# remove row and column 7 and 22 to fix our QR code
m2.pop(7)
m2.pop(21)
for i in m2:
    i.pop(7)
    i.pop(21)

# create image
for i in range(0,h):
    for j in range(0,w):
      if m2[i][j] == '1':
          pixels_out[i,j] = (0,0,0)

outimg = outimg.resize((256,256))
outimg.save("matrix_out.png","png")

# decode QR code
qr = qrtools.QR()
qr.decode(filename="matrix_out.png")
print qr.data_to_string()
```

this gives us the following QR code and prints the flag for us.

![](writeupfiles/matrix_out.png)

**Flag**
```
IceCTF{1F_y0U_l0oK_c1Os3lY_EV3rY7h1N9_i5_1s_4nD_0s}
```


## Stage 3: Forensics: Intercepted Conversations Pt. 1
**Challenge**

This traffic was picked up by one of our agents. We think this might be a conversation between two elite hackers that we are investigating. Can you see if you can analyze the data? intercept.pcapng

**Solution**

The pcap file consists solely of the logs of a USB hub being plugged in and a
single phrase typed. Once the pcap file was opened with wireshark, based on [a
suggestion on the
internet](https://ask.wireshark.org/questions/11054/analysing-usb-traffic)
I filtered with

```
usb.data_flag == "present (0)" && usb.device_address == 21
```

which seemed to filter our enough of just what I wanted, USB interrupts from
the device to the host. These could be exported to XML where we can look at the
"Leftover Capture Data" by extracting that into a separate file:

```console
xpath -e '//field[@name="usb.capdata"]/@value' -q cap.xml | grep -v 0000000000000000 > keyboard.data
```

This file starts to have some semblance of looking like keypresses. E.g.:

```
 value="2000000000000000"
 value="20000a0000000000"
 value="2000000000000000"
 value="00000c0000000000"
 value="0000070000000000"
 value="0200000000000000"
 value="02000c0000000000"
 value="0200000000000000"
 value="02000e0000000000"
 value="0200000000000000"
 value="02001c0000000000"
```

I believe the 20/02 in the leading byte is the shift key, but not sure about
the difference between 0x02 and 0x20. You can see the shift key being pressed,
another letter being typed (0x0a), the shift key is still held, then released,
two more letters are typed (0x0c, 0x07), shift key is depressed again, and
three individual letters are typed. This looks a LOT like "IceCTF"

Using the `KEY_CODES` provided by the [helpful person](https://webstersprodigy.net/2012/11/09/csaw-2012-quals-tutorialwriteup/),
it was possible to dump this as-is, which yielded

```
GIDIKY{ J0-P1V3-X3O7T-4LT4T5}
```

 Shoot. But! The braces are in the right place, and dashes are shifted to
underscores. so this looks quite promising. Adding the shift key and doing some
re-mapping, we can get to

```
IceCTF{!j0_p1V3!_x!3O7T_4LT!4t5}
```

Now just a mystery to figure out the rest. I would somewhat believe that they
numbers are in the right places, since those are the numerals normally used in
leetspeak. The rest of the alphabet shows no discernable pattern. The other
guy's keyboard was helpfully done sequentially, this does not seem to be the
same for this keyboard.

Process in [proc.py](writeupfiles/proc.py)

Turns out we needed to translate qwerty keys to dvorak to get the flag

```python
from string import maketrans

#QWERTY = '''-=qwertyuiop[]sdfghjkl;'zxcvbn,./_+QWERTYUIOP{}SDFGHJKL:"ZXCVBN<>?'''
#DVORAK = '''[]',.pyfgcrl/=oeuidhtns-;qjkxbwvz{}"<>PYFGCRL?+OEUIDHTNS_:QJKXBWVZ'''

# adjust translation slightly for {}_ symbols
QWERTY = '''-=qwertyuiop[]sdfghjkl;'zxcvbn,./_+QWERTYUIOP{}SDFGHJKL:"ZXCVBN<>?'''
DVORAK = '''_]',.pyfgcrl/=oeuidhtns-;qjkxbwvz{}"<>PYFGCRL{}OEUIDHTNS_:QJKXBWVZ'''
TRANS = maketrans(QWERTY, DVORAK)
KEY_CODES = {
4:"a", 5:"b", 6:"c", 7:"d", 8:"e", 9:"f", 10:"g", 11:"h",
12:"i", 13:"j", 14:"k", 15:"l", 16:"m", 17:"n", 18:"o", 19:"p",
20:"q", 21:"r", 22:"s", 23:"t", 24:"u", 25:"v", 26:"w", 27:"x",
28:"y", 29:"z", 30:"1", 31:"2", 32:"3", 33:"4", 34:"5", 35:"6",
36:"7", 37:"8", 38:"9", 39:"0", 40:"\n", 44:" ", 45:"-", 46:"=",
47:"{", 48:"}", 49:"|", 51:";", 54:","
}

# read in the "Leftover Capture Data" from text file
with open("intercept2.txt") as f:
    data=f.readlines()

flag=''
for packet in data:    
    key = int(packet[4:6],16)
    if key != 0:
        if packet.startswith("20") or packet.startswith("02"):
            flag += KEY_CODES[key].translate(TRANS).upper()
        else:
            flag += KEY_CODES[key].translate(TRANS)

print flag
```

**Flag**

```
IceCTF{Wh0_l1K3S_qw3R7Y_4NYw4y5}
```

## Stage 3 Cryptography: RSA2

**Challenge**

I guess the 3rd time is the charm? Or not..

```
N=0xee290c7a603fc23300eb3f0e5868d056b7deb1af33b5112a6da1edc9612c5eeb4ab07d838a3b4397d8e6b6844065d98543a977ed40ccd8f57ac5bc2daee2dec301aac508f9befc27fae4a2665e82f13b1ddd17d3a0c85740bed8d53eeda665a5fc1bed35fbbcedd4279d04aa747ac1f996f724b14f0228366aeae34305152e1f430221f9594497686c9f49021d833144962c2a53dbb47bdbfd19785ad8da6e7b59be24d34ed201384d3b0f34267df4ba8b53f0f4481f9bd2e26c4a3e95cd1a47f806a1f16b86a9fc5e8a0756898f63f5c9144f51b401ba0dd5ad58fb0e97ebac9a41dc3fb4a378707f7210e64c131bca19bd54e39bbfa0d7a0e7c89d955b1c9f

e=0x10001

c=0x3dbf00a02f924a70f44bdd69e73c46241e9f036bfa49a0c92659d8eb0fe47e42068eaf156a9b3ee81651bc0576a91ffed48610c158dc8d2fb1719c7242704f0d965f8798304925a322c121904b91e5fc5eb3dc960b03eb8635be53b995217d4c317126e0ec6e9a9acfd5d915265634a22a612de962cfaa2e0443b78bdf841ff901423ef765e3d98b38bcce114fede1f13e223b9bd8155e913c8670d8b85b1f3bcb99353053cdb4aef1bf16fa74fd81e42325209c0953a694636c0ce0a19949f343dc229b2b7d80c3c43ebe80e89cbe3a3f7c867fd7cee06943886b0718a4a3584c9d9f9a66c9de29fda7cfee30ad3db061981855555eeac01940b1924eb4c301
```

**Solution**

we can find the prime factorisation of N on [factordb.com](http://factordb.com/index.php?query=30064958471180141352963255964320727764941087854957385562672821662319854021395100968823341108075020928542437446993994119863902565874355296188498304761389336438421889636409561936141985786801002923752627293790265351723795968412774268086467114263767947693310444934316205390814185802517514694528501333851255084653925181726978734804806707740444755908398751964899143494522781405457103697373868972836201511424363601490903086488506985489526910314474245106338585623571369549388434865567951986866445306840505397268281889886738015891982162371413136885989746931929787765617838750381226036784122498143172854419447324975505933540511)

using this we can decrypt the message:

```python
from Crypto.PublicKey import RSA
import gmpy2
import base64

def int2Text(number, size):
    text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
    return text.lstrip("\x00")


N = 0xee290c7a603fc23300eb3f0e5868d056b7deb1af33b5112a6da1edc9612c5eeb4ab07d838a3b4397d8e6b6844065d98543a977ed40ccd8f57ac5bc2daee2dec301aac508f9befc27fae4a2665e82f13b1ddd17d3a0c85740bed8d53eeda665a5fc1bed35fbbcedd4279d04aa747ac1f996f724b14f0228366aeae34305152e1f430221f9594497686c9f49021d833144962c2a53dbb47bdbfd19785ad8da6e7b59be24d34ed201384d3b0f34267df4ba8b53f0f4481f9bd2e26c4a3e95cd1a47f806a1f16b86a9fc5e8a0756898f63f5c9144f51b401ba0dd5ad58fb0e97ebac9a41dc3fb4a378707f7210e64c131bca19bd54e39bbfa0d7a0e7c89d955b1c9f
e = 0x10001L

ct = 'Pob7AQZZSml618nMwTpx3V74N45x/rTimUQeTl0yHq8F0dsekZgOT385Jls1HUzWCx6ZRFPFMJ1RNYR2Yh7AkQtFLVx9lYDfb/Q+SkinBIBX59ER3/fDhrVKxIN4S6h2QmMSRblh4KdVhyY6cOxu+g48Jh7TkQ2Ig93/nCpAnYQ='
ct = base64.b64decode(ct)

ct=0x3dbf00a02f924a70f44bdd69e73c46241e9f036bfa49a0c92659d8eb0fe47e42068eaf156a9b3ee81651bc0576a91ffed48610c158dc8d2fb1719c7242704f0d965f8798304925a322c121904b91e5fc5eb3dc960b03eb8635be53b995217d4c317126e0ec6e9a9acfd5d915265634a22a612de962cfaa2e0443b78bdf841ff901423ef765e3d98b38bcce114fede1f13e223b9bd8155e913c8670d8b85b1f3bcb99353053cdb4aef1bf16fa74fd81e42325209c0953a694636c0ce0a19949f343dc229b2b7d80c3c43ebe80e89cbe3a3f7c867fd7cee06943886b0718a4a3584c9d9f9a66c9de29fda7cfee30ad3db061981855555eeac01940b1924eb4c301

p = 57970027L
q = 518629368090170828331048663550229634444384299751272939077168648935075604180676006392464524953128293842996441022771890719731811852948684950388211907532651941639114462313594608747413310447500790775078081191686616804987790818396104388332734677935684723647108960882771460341293023764117182393730838418468480006985768382115446225422781116531906323045161803441960506496275763429558238732127362521949515590606221409745127192859630468854653290302491063292735496286233738504010613373838035073995140744724948933839238851600638652315655508861728439180988253324943039367876070687033249730660337593825389358874152757864093
r=(p-1)*(q-1)

d = long(gmpy2.divm(1, e, r))

rsa = RSA.construct((N,e,d,p,q))
pt = rsa.decrypt(ct)

print pt
print int2Text(pt,1000)
```

**Flag**
```
IceCTF{next_time_check_your_keys_arent_factorable}
```

## Stage 3 Pwn: Quine
**Challenge**

This service is really strange... It seems to accept only C programs. Can you get us the flag? nc quine.vuln.icec.tf 5500


**Solution**

Found [a quine](writeupfiles/quine.c) on the [internet](https://www.nyx.net/~gthompso/self_c.txt):

```
main(){char *c="main(){char *c=%c%s%c;printf(c,34,c,34);}";printf(c,34,c,34);}
.
```

```console
$ cat tmp.c | nc quine.vuln.icec.tf 5500
Please provide source file (up to 2048 bytes), ending with a dot by itself on a line
[INFO]  server.c:161  (2439) Fri 12 Aug 2016 17:10:32 File upload complete!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:32 Survived iteration #1!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:32 Survived iteration #2!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #3!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #4!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #5!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #6!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #7!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #8!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #9!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #10!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #11!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #12!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #13!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #14!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #15!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #16!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #17!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #18!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #19!
[INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #20!
[INFO]  server.c:438  (2439) Fri 12 Aug 2016 17:10:33 Echoing surviving source back
main(){char *c="main(){char *c=%c%s%c;printf(c,34,c,34);}";printf(c,34,c,34);}
```

**Flag**

```
```


## Stage 3: Forensics: Intercepted Conversations Pt. 2
**Challenge**

We managed to intercept more of the hacker's traffic, unfortunately since our last encounter they have figured out that they're being watched. They've gotten more clever in their communication so we need you to try to make sense of this traffic.

**Solution**

The pcap file has two tcp streams (in wireshark I normally click "follow TCP
stream" and then it inserts a nice `tcp.stream eq 0` and then I
increment/decrement to find all streams)

The first stream is IRC chat

```
PRIVMSG Cold_Storm :Hi
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :It's not safe here
PRIVMSG Cold_Storm :What do you mean?
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :Someone is listening
PRIVMSG Cold_Storm :What?!
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :Yea, someone intercepted our last encounter
PING LAG1470558285526987
:irc.glitch.is PONG irc.glitch.is :LAG1470558285526987
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :We need to be careful about what we say on open channels.
PRIVMSG Cold_Storm :Oh 0k, so what do w3 do?
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :I made som3thing...
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :S0mething that will allow us to exchange s3crets secure1y
PING LAG1470558315569084
:irc.glitch.is PONG irc.glitch.is :LAG1470558315569084
:Cold_Storm!~finalC@localhost PRIVMSG Ice_Venom :.DCC SEND encode.pyc 1494322064 1117 1737.
PING LAG1470558345608305
:irc.glitch.is PONG irc.glitch.is :LAG1470558345608305
PRIVMSG Cold_Storm :Ok
PING LAG1470558375659432
:irc.glitch.is PONG irc.glitch.is :LAG1470558375659432
PRIVMSG Cold_Storm :Wmkvw680HDzDqMK6UBXChDXCtC7CosKmw7R9w7JLwr/CoT44UcKNwp7DllpPwo3DtsOID8OPTcOWwrzDpi3CtMOKw4PColrCpXUYRhXChMK9w6PDhxfDicOdwoAgwpgNw5/Cvw==
```

The DCC looks someone sends a python file which we find in the next stream.
This was extracted to [encode.pyc](writeupfiles/encode.pyc). Uncompyle will extract the original python from this:

```
$ uncompyle6 encode.pyc > encode.py
```

We [reversed](writeupfiles/decode.py) the [algorithm](writeupfiles/encode.py),
when run with the base64 string found in the IRC chat, it prints out the flag:

```
$ python decode.py
IceCTF{4Lw4y5_US3_5s1_AnD_n3VR4r_mAKe_Y0ur_0wN_cRyp70}
```

**Flag**

```
IceCTF{4Lw4y5_US3_5s1_AnD_n3VR4r_mAKe_Y0ur_0wN_cRyp70}
```

## Stage 3 Cryptography: Contract
**Challenge**

Our contractors stole the flag! They put it on their file server and challenged us to get it back. Can you do it for us? nc contract.vuln.icec.tf 6002 server.py. We did intercept someone connecting to the server though, maybe it will help. contract.pcapng


**Solution**

They have an RSA key, and any incoming request must be signed according to that file. There is a pcap file associated which provides a couple of useful packets we can replay:

```
help:c0e1fc4e3858ac6334cc8798fdec40790d7ad361ffc691c26f2902c41f2b7c2fd1ca916de687858953a6405423fe156cfd7287caf75247c9a32e52ab8260e7ff1e46e55594aea88731bee163035f9ee31f2c2965ac7b2cdfca6100d10ba23826

COMMANDS:
* read [file]
 - prints contents of file
* time
 - prints the current time
* help
 - prints this message
```

```
time:c0e1fc4e3858ac6334cc8798fdec40790d7ad361ffc691c26f2902c41f2b7c2fd1ca916de687858953a6405423fe156c0cbebcec222f83dc9dd5b0d4d8e698a08ddecb79e6c3b35fc2caaa4543d58a45603639647364983301565728b504015d
2016-08-11 22:48:48
```

**Flag**

```
```

## Stage 3 Cryptography: Sand Castles on the Beach

**Challenge**

We found this very mysterious image, it doesn't look complete and there seems to be something hidden on it... does this mean anything to you? sandcastle.png. This flag is not in the standard flag format, add IceCTF{} to the message when you've found it. The flag is all lowercase, and contains no special characters.

![](writeupfiles/sandcastle.png)

**Solution**

```
$ exiftool sandcastle.png                               [15-08-16 14:04:42]
ExifTool Version Number         : 10.10
File Name                       : sandcastle.png
Directory                       : .
File Size                       : 381 kB
File Modification Date/Time     : 2016:08:12 22:24:08+02:00
File Access Date/Time           : 2016:08:12 22:24:08+02:00
File Inode Change Date/Time     : 2016:08:12 22:24:08+02:00
File Permissions                : rwxrwxrwx
File Type                       : PNG
File Type Extension             : png
MIME Type                       : image/png
Image Width                     : 616
Image Height                    : 428
Bit Depth                       : 8
Color Type                      : RGB
Compression                     : Deflate/Inflate
Filter                          : Adaptive
Interlace                       : Noninterlaced
Pixels Per Unit X               : 3780
Pixels Per Unit Y               : 3780
Pixel Units                     : meters
Modify Date                     : 2016:06:19 11:14:52
Comment                         : 58-7-9.210-13-2.67-3-16.85-17-15.305-18-4.83-12-18.75-8-15.47-4-2.83-20-11.208-6-6.85-11-6.75-7-3.106-9-14
Image Size                      : 616x428
Megapixels                      : 0.264
```

the comment in the exifdata looks like it could be a book cipher, we identified the image to be part of the cover to the fourth edition of *Cryptography and Network Security* by William Stallings.

![](http://williamstallings.com/Images/Crypto4e.jpg)

```
58-7-9.210-13-2.67-3-16.85-17-15.305-18-4.83-12-18.75-8-15.47-4-2.83-20-11.208-6-6.85-11-6.75-7-3.106-9-14

page-line-character?
page-line-word (and then first letter of word)?

58-7-9
210-13-2
67-3-16
85-17-15
305-18-4
83-12-18
75-8-15
47-4-2
83-20-11
208-6-6
85-11-6
75-7-3
106-9-14
```

**Flag**
```
flag
```
