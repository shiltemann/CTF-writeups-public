# HackyEaster 2017

## Overview


```
Title                                    Category  Flag
---------------------------------------- --------- -----------------------------
Challenge 01: Puzzle This!               Easy      C5LHYOifJSLOnYmKjBmS
Challenge 02: Lots of Dots               Easy      pJ94m6jt3AYbogL2gv9i
Challenge 05: Key Strokes                Easy
Challenge 06: Message Ken                Easy      uktVsuNNyPVQarmXTuYU
Challenge 10: An egg or not...           Medium    UALYyPlhy2aYfYpzcJHA
```

## Challenge 01: Puzzle This!

**Challenge**

An easy one to start with.

![](./writeupfiles/egg01_shuffled.png)

**Solution**

Shuffled, looks like no rotations.

![](./writeupfiles/egg01_unshuffled.png)

## Challenge 02: Lots of Dots

**Challenge**

The dots in the following image contain a secret message. Can you find it?


![](./writeupfiles/dots.png)

**Solution**

Had a non-colourblind person look at it, he said nothing was there. Split out LSB and bingo:

![](./writeupfiles/dots_out.png)

Pop into the egg-o-matic:

![](./writeupfiles/dots_egg.png)


## Challenge 05: Key Strokes

**Challenge**  

05 - Key Strokes

esc i c e l a n d esc a y a n k e e space f o x
space esc o f l o w e r up esc $ esc i y esc e esc a
y esc / l a return esc r w esc right right right
right esc x i f r esc e esc X x x : s / c e / a g i
c / return esc down d d esc i m esc Z Z

**Solution**  

Oh my gosh this is VIM! That's awesome :D

Entering the keystrokes as they provide spells out: magicwandfrankfoxy

![](./writeupfiles/egg5.png)

## Challenge 06: Message to Ken

**Challenge**  

Barbie has written a secret message for her sweetheart Ken. Can you decrypt it?

Fabrgal JaeM Hsa faonah uiff;rnl tf btuxbrffuinhzoroyhitbM Fincta dd

Hint:
Shift+Lock+1

**Solution**  
Holy crap this is AWESOME. This relies on you knowing about the barbie typewriters made in the 90s that had a secret code mechanism.
http://www.cryptomuseum.com/crypto/mehano/barbie/#e118

So this is key 2 for coding.

```
norm: abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789
   1: torbiudfhgzcvanqyepskx¢1w; RC>GHAPND<VUBLIKJETOYXM2QF 63405789-¨
   2: icolapxstvybjeruknfhqg;dzw >FAUTCYOLVJDZINQKSEHG<.1PB 523406789-
   3: hrnctqlpsxwogiekzaufyd+b;¢ SARYO>QIUX<GFDLJVTHNP1Z3KC 7405689-¨§
   4: sneohkbufd;rxtaywiqpzl%c¢+ E>SPNRKLG1XYCUDV<HOIQ2B4JA 805679-¨§£
```

'Beloved KenM The secret password is lipglosspartycocktailM Barbie xx'


## Challenge 10: An egg or not ...


**Challenge**  
... an egg, that's the question!

Are you able to answer this question and find the (real) egg?

**Solution**  
The QR code is made of individual dots. Some are doubled up, I think we remove the doubles.

I stripped out the `<use ...>` statements and then transformed into a TSV:

```python
data = open('tmp', 'r').read()
q = [x.split('\t') for x in data.split('\n')]
haveSeen = {}

for i in q:
        k = "%s,%s" % (i[0], i[1])
        if k not in haveSeen:
                print """<use x="%s" y="%s" xlink:href="#%s"/>""" % (*i)
        haveSeen[k] = True
```

**Nugget**  
```
UALYyPlhy2aYfYpzcJHA
```

## Challenge 12: Once Upon a File

**Challenge**  
Once upon a file there was a hidden egg. It's still waiting to be saved by a noble prince or princess.

**Solution**  

Hmm. Zipped file containing a single file, nothing interesting in zipdetails.

```console
[hxr@leda:~/Downloads]$ file file
file: DOS/MBR boot sector, code offset 0x52+2, OEM-ID "NTFS    ", sectors/cluster 8, Media descriptor 0xf8, sectors/track 63, hidden sectors 1, dos < 4.0 BootSector (0x80), FAT (1Y bit by descriptor); NTFS, sectors/track 63, sectors 10239, $MFT start cluster 426, $MFTMirror start cluster 2, bytes/RecordSegment 2^(-1*246), clusters/index block 1, serial number 09850f88350f86a00
```

Ok, that's interesting.

```console
qemu-system-x86_64 -drive format=raw,file=disk.img
```

![](./writeupfiles/12.png)

Not sure where to go from here. tcpdump where it's pixieing to?

**Nugget**


## Challenge 14: Shards

**Challenge**  
Oh no! What a mess!

**Solution**  
Image reassembly software??

**Nugget**

## Challenge 15: P Cap

**Challenge**  
What about a little P cap?

**Solution**  
Lotsa SMB traffic, looks like a file named R05h4L.jpg is tranferred. Dollars to doughnuts that's an egg.

**Nugget**

## Challenge 16: Pathfinder

**Challenge**  
Can you find the right path?

**Solution**  

```
$ curl -i hackyeaster.hacking-lab.com:9999  
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 39
Date: Thu, 06 Apr 2017 04:19:00 GMT
Connection: keep-alive

{"Answer":"I only talk to PathFinder!"}
```

**Nugget**


## Challenge 20: Spaghetti hash

**Challenge**  
Lazy Larry needs to improve the security of his password hashing implementation. He decides to use SHA-512 as a new hashing algorithm in order to be super secure. Unfortunately, the database column for the hash can only hold 128 bit. As Bob is too lazy to extend the column and all the code related to it, he decides to shrink the output of the SHA-512 operation, to 128 bit. For this purpose he picks certain characters from the SHA-512 output for producing the new value.

You got hold of four password hashes, calculated with Bob's new implementation. Can you find the corresponding passwords?

```
hash 1: 87017a3ffc7bdd5dc5d5c9c348ca21c5
hash 2: ff17891414f7d15aa4719689c44ea039
hash 3: 5b9ea4569ad68b85c7230321ecda3780
hash 4: 6ad211c3f933df6e5569adf21d261637
```

Lucky you, you know that the following web service is calculating Bob's algorithm. However, the web service only accepts strings of length 4 or less - brute-forcing a password list thus is no option, since the passwords you are looking for are all longer.

```
https://hackyeaster.hacking-lab.com/hackyeaster/hash?string=abcd
```

**Solution**  
Doesn't look too bad

**Nugget**

**Nugget**


## Dec X: Title  

**Challenge**  
**Solution**  
**Nugget**

```
HV16-
```
