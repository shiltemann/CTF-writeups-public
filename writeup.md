# HackiT CTF Ukraine

## Overview

```
Title                         Country?       Points  Category   Flag
----------------------------- -------------- ------  ---------- -----------------------------
Crypt0P1xels                  Algeria        250     $tego
Interceptor                   Portugal       95      Crypt0
Ninja Scheme                  Chad           195     Crypt0
T3legr4m                      United States  50      J0y
Crypt00perator                Ethiopia       95      Rever$e
Hex0gator                     Paraguay       250     PPC
PhParanoid                    Malaysia       225     Rever$e
Fucking russian programmers   North Korey    100     Web
Remote pentest                Mexico         150     Web
FullyD00M3D                   Ukraine        50      Web
ph0t0b00th                    Japan          50      J0y
F1r3d&H4ck3d                  Egypt          200     Admin
Suspicious AVI                               450     Stego
QRb00k                        Russia         400     W3b
1magePr1son                   Nozambique     150     Stego
RTFspy                        China          150     Stego
Handmade encryption standard  Guena          250     Crypto
C4nY0u533                     Chile          100     PWN
p13c3 0f c4k3                 Brazil         100     Forensics
p13c3 0f c4k3                 Argentina      100     Network
```


## Challenge Template

**Challenge**
```
```

**Solution**

**Flag**
```
```



## Challenge Template

**Challenge**
We have received pictures from the enemy companion of the unknown before
planet. And we haven't thought up anything better, than to construct
DeathStarV3 (the general was a fan of "Star Wars") and to absorb energy of the
whole planet! And again we are pursued by problems: that we don't know
coordinate! Your task is to determine coordinates of this unique planet (which
according to our spy are ciphered in the image). Also he could steal one of the
scripts intended for embedding of coordinates. All hope only for you!
tasks.zip:CryptoPixels_473e51e6e53cfc47b8f87b8a65a8d542.zip

**Solution**
They give you the python code used to encrypt it, pretty simple to reverse the
process and decrypt the key

```python
from PIL import Image

img = Image.open('encrypted.png')
i = img.convert('RGB')

(flag_length, x, y) = i.getpixel((0, 0))

flag = ''

while True:
    (flag_char, new_x, new_y) = i.getpixel((x, y))
    flag += chr(flag_char)
    x = new_x
    y = new_y

    if len(flag) >= flag_length:
        print flag
        break
```

**Flag**
```
1NF0RM$T10N_1$_N0T_$3CUR3_4NYM0R3
```



