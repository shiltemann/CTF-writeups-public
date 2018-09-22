# D-CTF: DefCamp Qualifiers 2018

Team: Galaxians


## Overview
```
Title                          Category       Points Flag
------------------------------ -------------- ------ -----------------------------
XORnigma                       Junior         1
Multiple Flags                 Junior         1
World of Internet              Junior         1
Sniff                          Junior         1
SimplePassword                 Junior         1
EagleEye                       Junior         1
PasswordPolicy                 Junior         1
RobotsVSHumans                 Junior         1      DCTF{1091d2144edbffaf5dd265cb7c93e799c4659eb16ee79735b3bd6e09dd6e791f}
Security through obscurity     Junior         1
Passport                       Junior         1
chat                           Web            330
Get Admin                      Web
secops                         Web
Vulture                        Web
Ransomware                     Reversing
Memsome                        Reversing
KitKat                         Reversing
Validator                      Reversing
Lucky?                         Exploit
Even more lucky?               Exploit
Online linter                  Exploit
Broken TV                      Misc
Message                        Misc
Voices                         Misc
Exfil                          Misc
```

## Junior: XORnigma

**Challenge**

Obtain the flag from the given [file](writeupfiles/xornigma.py).

**Solution**

**Flag**

## Junior: Multiple Flags

**Challenge**

Look, flags everywhere!

![](writeupfiles/multiple-flags.png)

**Solution**

This is [semaphore flag signalling system](https://www.anbg.gov.au/flags/semaphore.html)

![](../_resources/sema.jpg)

```
JDCTFSP
ECIHLZL
AG?KKJA
A?KKJAA
?KKIIAC
CGJDCTF
```

apart from the two `DCTF`s in there not sure what to make of it

## Junior: RobotsVSHumans

**Challenge**

Find your flag on this website.
Target: https://robots-vs-humans.dctfq18.def.camp/

**Solution**

We check out robots.txt file:

```
Did you know that robots.txt is not the only .txt file in a website? BTW: I am against humans!
```


ok, so we try humans.txt:

```
/* TEAM */


Your title: RobotsVSHumans


Location: Bcharest, Romania


/* THANKS */


```

**Flag**
```
DCTF{1091d2144edbffaf5dd265cb7c93e799c4659eb16ee79735b3bd6e09dd6e791f}
```
