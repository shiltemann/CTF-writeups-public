# TAMUctf 2017

CTF hosted by Texas A&M university.

## Overview


```
Title                                    Category  Points  Flag
---------------------------------------- --------- ------- -----------------------------
Howdy                                    Intro      1      gigem{Howdy!}
Reading                                  Web        20     gigem{F!nD_a_F!AG!}
Veggies                                  Web        30     gigem{CrAzzYY_4_CO0k!es}
Bender                                   Web        40     gigem{craw1ing_bot$!}
Bubbles                                  Web        50     gigem{ScRuB7h3InpU7}
readyXORnot                              Crypto     15     FLAG=Alpacaman
Image'n That                             Crypto     25
Enigma Too Far                           Crypto     75
XORbytes                                 Crypto     100
simpleDES                                Crypto     125
breadsticks2                             Misc       20
you can run, you can hide                Misc       25
emum                                     Misc       150
Stop and Listen                          Network    15
Stuck in the Middle                      Network    25
Straw House                              Network    35
Stick House                              Network    50
Brick House                              Network    100
Segal's Law                              Network    175
pwn1                                     Pwn        25
pwn2                                     Pwn        50
pwn3                                     Pwn        75
pwn4                                     Pwn        125
pwn5                                     Pwn        200
Band Aid                                 Reversing  75
Hashbrowns                               Reversing  100
wibbly wobbly timey wimey                Reversing  150
DESpicableMe                             Reversing  200
Move it!                                 Reversing  250

Scenario HSO

Scenario NotSoAwesomeInc

Scenario BadBob

Scenario MCCU

Scenario ClandestineEnforced

```


## Intro 1: Howdy!  

**Challenge**  

Welcome to TAMUctf!
Most flags for traditional ctf problems will be in the format of `gigem{flag}`.
The flags for most scenarios will be specific artifacts and not in the `gigem{flag}` format.

The flag for this challenge is: `gigem{Howdy!}``

**Solution**  

copy & paste

**Flag**

```
gigem{Howdy!}
```



## Web 20: Reading  

**Challenge**  


I just love reading!

http://web1.ctf.tamu.edu


**Solution**  

flag hidden in html comment in source

```html
<style>
.default {
	max-width: 200x;
	margin: auto;
        text-align: center;
}
</style>

<body>
<div class="default">
        <br><br>
	<img src=reading.gif alt="flag-flag-flag-flag-flag-flag-flag-flag-..."/>
        <br>
	<!--flag,_flag,flagflag,flag_,flagflag,flag,flagflag,flag,flagflag,flag,flagflag{flag_{flag}{flag{flag{flag{_flag}{flag{	flag{flag{flag_}{flag{	flag{flag{flag}{flag{	flag{flag{flag_}{flag	flag{flag{flag}{flag{	flag{flag{flag_}{flag{	Eds6sa5dg465sd4g5s416081 87re+t8se40g7+s87y n9+8tdyi7do;gk lshglksddh gklshgpaeiuytp98w347590834789&*&(^&*%^P$*&YUy98 eytw98eryt	EdgEdg	63se5r4tv13ser4y03sr40y53s 07r45ys04r5tv40s3e8r57ty3d5r7ysaf;oiarwuerow84376895wb37oinv83womuc9puwat bsueg sijdg lksu t;srit udrty53sb6rgigme{9y850
	gs 697tru1698bse7rt8as+6e98vr+ys1dr79 8tu1s+98e7y+ se8r6 7ys+gjselioutvnns;oiaywt aoiuwytoalwuehtjo;w8tin;woqupao9wupoq2u0189702738947ny uy^&%Q@&*$&P*gigem_{(W$TY *OT YIOYPNfruvaliweuytnvaiolyp YA&*^%O&$*&^N*pdyta oniusyfad fads68f17b+s8d9g7+1sr7g 1+s89h7/es9y/seb	seryEfrsytd	6erflag{flag{flag}{flag{5nu8r7iryvsaer
	nt	gigem{asldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn_,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71 _u+6s8e7t6sbe7rt69se1 awerasldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds_;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem{ad;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;_aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;_siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem_{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtia-->
	<!--bv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys _+bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uo_i;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{_d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;_gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdf_hn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhu_jds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 gigem{F!nD_a_F!AG!}y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71asldkfj;gigem{a;lsddkfj;alskdjfa;sdlkfjwopieiurakrhgn,msdfnfvlkjxcbbnv.,adsjnflkjahsdf;lj}{gigem{a;lskdjfa;weoiurpaowietyua;i;lksjfg;laskkdfhn,.mcnbx.gigem{,mcvhblzkdsuhf;aiseutoiadfhg,-->
	<!--adjnv.zx,vkjnlzkxcfjhvbk;szjdg;gigme{abksejrfha;siudghajdf,vbzxm,cbvlkzddshgpfiuasryugtiasdhbv,jzxcvn  j,gigem{xjmkscvlzddkhg;lisKjncklzxnv.zkjxbz.kjdbg;vhzkjsdhg;ilas uoi;aenvcioaweu;gigem{omvaeeiseu,tmboisdnpu tisgiaeu slitfyse;riyhujds;rxjofndf35gij1 7f68yg7u4h6 gigem{d52x18f63sz8d5 y3xlkdjfa;s ldjf;alsdu ;falskgsdh6ydn268t7yhg6h3dgigem{ynr1t7r868r67796t788710o1r68dmsne675b65swea75r657wae68t74ydr31857761w644757nw+b9r58y s+r97ys +bwr975yv69ew47er6a8wv17vb68ae71y gigem{69e7u68e71-->
        <br><br>
        <br>
</div>
</body>
```

**Flag**

```
gigem{F!nD_a_F!AG!}
```

## <category> <points>: Veggies  

**Challenge**  

So many choices...

http://web2.ctf.tamu.edu

![](writeupfiles/veggies.png)

**Solution**  

flag hidden in cookie

```bash
$ echo Z2lnZW17Q3JBenpZWV80X0NPMGshZXN9 | base64 -d
gigem{CrAzzYY_4_CO0k!es}
```

**Flag**

```
gigem{CrAzzYY_4_CO0k!es}
```

## Web 40: Bender

**Challenge**  

My story is a lot like yours, only more interesting ‘cause it involves robots.

http://web3.ctf.tamu.edu

website:

No Google Bot can help you now!

![](writeupfiles/bender.gif)


**Solution**  

check `/robots.txt`

```
User-agent: *
Disallow: oiuwerljk.html
```

the disallowed html page contained the flag

**Flag**

```
gigem{craw1ing_bot$!}
```


## Web 50: Bubbles  

**Challenge**  

I don't like taking baths.

http://web4.ctf.tamu.edu

**Solution**  

Website is a simple login form.

```html
<html>
	<head>
    <title>SQLi</title>
	</head>

	<body bgcolor="#800000">
			<h1 style="color:white">User Login</h1>
			<img align = "right" src = "/images/logo.png" width="50%">

		<div>
			<form action = "/web/login.php" method = "POST">
		   		<p align="left" style="color:white">
			Username: <br>
			<input type = "text" name = "username" align = "justify"/><br><br>
		   		Password: <br>
			<input type = "password" name = "password" align = "justify"/><br>
			</p>
		   		<input type = "submit" value="Login" />
			</form>
		</div>
	</body>
</html>
```

SQL injection of `x' or 'x'='x` in username and password fields gives the flag

```
Huh. We really should gigem{ScRuB7h3InpU7}!
```

**Flag**

```
gigem{ScRuB7h3InpU7}
```

## Crypto 15: readyXORnot  

**Challenge**  

```
original data: "El Psy Congroo"
encrypted data: "IFhiPhZNYi0KWiUcCls="
encrypted flag: "I3gDKVh1Lh4EVyMDBFo="
```

The flag is not in the traditional `gigem{flag}` format.

**Solution**  

```python
import base64

orig = "El Psy Congroo"
c1 = "IFhiPhZNYi0KWiUcCls="
c2 = "I3gDKVh1Lh4EVyMDBFo="

key = ''.join(chr(ord(a) ^ ord(b)) for a,b in zip(orig,base64.b64decode(c1)))
flag = ''.join(chr(ord(a) ^ ord(b)) for a,b in zip(key,base64.b64decode(c2)))

print(flag)
```

**Flag**

```
FLAG=Alpacaman
```

## <category> <points>: Title  

**Challenge**  

**Solution**  

**Flag**

```
gigem{}
```

## <category> <points>: Title  

**Challenge**  

**Solution**  

**Flag**

```
gigem{}
```

## <category> <points>: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
gigem{}
```

## <category> <points>: Title  
*hint*

**Challenge**  

**Solution**  

**Flag**

```
gigem{}
```
