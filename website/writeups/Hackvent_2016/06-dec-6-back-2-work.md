---
layout: writeup
title: 'Dec 6: Back 2 Work'
level:
difficulty:
points:
categories: []
tags: []
flag:  HV16-y9YO-sDo1-Vi7O-RWq1-V7hN

---

## Challenge

Greetings from Thumper, he has an order for you:

1.  unzip: the password is confidential
2.  find the flag
3.  look at my holiday pictures

Comment: Be aware, the pictures are only supplement.

## Solution

We get a zip file with 25 images in them, the images are of hacky easter
bunny
but the hint says the images are not important. So we focus on the zip
file itself

We can extract it with password `confidential` but see nothing strange.

we get some information about the zip file

    $ unzip -v dec6_original.zip                                                                                                           [06-12-16 15:51:45]
    Archive:  dec6_original.zip
     Length   Method    Size  Cmpr    Date    Time   CRC-32   Name
    --------  ------  ------- ---- ---------- ----- --------  ----
      822192  Defl:N   821475   0% 2016-09-07 22:30 9ac20439  image_0004.jpg
                                                        -
      649281  Defl:N   648888   0% 2016-09-07 22:30 34b1e9c3  image_0005.jpg
                                                                       -
      774896  Defl:N   774435   0% 2016-09-07 22:30 764cdcab  image_0017.jpg
                                                                 -
      658523  Defl:N   657723   0% 2016-09-07 22:30 17038f5a  image_0002.jpg
                                                        -
      584984  Defl:N   584386   0% 2016-09-07 22:30 a2a99aad  image_0013.jpg
                                                                 -
      639489  Defl:N   639014   0% 2016-09-07 22:30 8a3db508  image_0012.jpg
                                                                          -
      916283  Defl:N   915157   0% 2016-09-07 22:30 14a76079  image_0016.jpg
                                               -
      684503  Defl:N   684052   0% 2016-09-07 22:30 1de58966  image_0010.jpg
                                                                                         -
      608586  Defl:N   607823   0% 2016-09-07 22:30 2e7804b3  image_0009.jpg
                                                  -
      963456  Defl:N   962651   0% 2016-09-07 22:30 fdd724e0  image_0020.jpg
                                                                    -
      695994  Defl:N   695208   0% 2016-09-07 22:30 b370cb8a  image_0018.jpg
                                                                 -
      760741  Defl:N   759912   0% 2016-09-07 22:30 3e75e394  image_0001.jpg
                                                                    -
      716152  Defl:N   715332   0% 2016-09-07 22:30 9735af02  image_0003.jpg
                                                              -
      772244  Defl:N   771129   0% 2016-09-07 22:30 8c123698  image_0006.jpg
                                                                    -
      885418  Defl:N   884858   0% 2016-09-07 22:30 cc732eaa  image_0015.jpg
                                                  -
      484512  Defl:N   482635   0% 2016-09-07 22:30 6aa90f1a  image_0011.jpg
                                                                    -
      819398  Defl:N   818968   0% 2016-09-07 22:30 696689ab  image_0024.jpg
                                                           -
     1104273  Defl:N  1102447   0% 2016-09-07 22:30 6fb877ce  image_0007.jpg
                                                                                -
     1163165  Defl:N  1162154   0% 2016-09-07 22:30 8774fd48  image_0021.jpg
                                                        -
      719580  Defl:N   718391   0% 2016-09-07 22:30 447933be  image_0023.jpg
                                                                                -
      766740  Defl:N   765316   0% 2016-09-07 22:30 38213aab  image_0019.jpg
                                               -
      829079  Defl:N   828267   0% 2016-09-07 22:30 35e8aea8  image_0025.jpg
                                                  -
     1004991  Defl:N  1003782   0% 2016-09-07 22:30 16cfccc6  image_0022.jpg
                                                                    -
      792701  Defl:N   790543   0% 2016-09-07 22:30 87b0e0b0  image_0008.jpg
                                                                 -
     1362659  Defl:N  1361655   0% 2016-09-07 22:30 01264369  image_0014.jpg
                                                           -
    --------          -------  ---                            -------
    20179840         20156201   0%                            25 files

We see that there is whitespace (spaces and tabs) after each file name
here.

We open the zip file in a hex editor and see the following character
after each
file name in the listing:

    20 20 20 20 20 20 20 09 09 09 09 09 20 09 20 09 09 09 20 20 20 20 20 20 20
    20 09 09 09 09 09 20 09 20 09 20 09 20 20 20 20 20 09 20 09 09 09 09 09 20
    20 09 20 20 20 09 20 09 09 09 09 20 09 09 20 20 09 09 20 09 20 20 20 09 20
    20 09 20 20 20 09 20 09 20 20 20 20 20 09 20 09 09 09 20 09 20 20 20 09 20
    20 09 20 20 20 09 20 09 09 20 09 09 09 20 09 09 20 09 20 09 20 20 20 09 20
    20 09 09 09 09 09 20 09 20 20 09 09 20 20 20 20 09 09 20 09 09 09 09 09 20
    20 20 20 20 20 20 20 09 20 09 20 09 20 09 20 09 20 09 20 20 20 20 20 20 20
    09 09 09 09 09 09 09 09 09 20 20 09 20 09 20 09 20 09 09 09 09 09 09 09 09
    20 20 20 20 20 09 20 20 20 20 09 20 20 20 09 20 20 20 09 20 09 20 09 20 09
    20 09 20 20 20 20 09 09 09 09 20 20 09 20 09 20 20 09 09 09 09 20 09 20 09
    20 09 20 20 09 09 20 09 20 09 20 09 09 09 20 20 20 20 09 20 20 09 09 09 20
    09 09 09 20 20 20 09 09 09 09 09 20 09 09 20 20 20 20 20 20 20 20 09 09 09
    09 09 09 20 20 20 20 20 20 20 20 09 09 09 20 09 09 20 20 09 09 09 20 20 20
    20 20 20 09 20 09 09 20 09 09 09 20 20 09 20 09 09 20 09 20 09 09 09 20 20
    20 09 20 09 20 20 20 20 20 20 09 09 20 20 09 20 20 09 20 20 09 20 20 20 20
    20 09 09 20 20 20 09 20 09 20 09 20 09 09 09 20 09 20 09 09 20 20 09 09 20
    20 09 09 20 09 20 20 09 09 20 20 09 09 09 20 20 20 20 20 20 20 20 20 09 09
    09 09 09 09 09 09 09 09 20 20 20 09 09 09 20 09 20 09 09 09 20 09 20 09 20
    20 20 20 20 20 20 20 09 20 09 20 09 09 20 20 09 20 09 20 09 20 09 09 20 20
    20 09 09 09 09 09 20 09 09 09 09 09 09 09 20 20 20 09 09 09 20 09 09 20 20
    20 09 20 20 20 09 20 09 20 09 20 20 20 20 20 20 20 20 20 20 20 09 20 20 09
    20 09 20 20 20 09 20 09 20 20 09 20 20 09 20 09 20 20 20 20 09 20 20 20 20
    20 09 20 20 20 09 20 09 20 20 20 09 09 09 09 09 20 09 20 20 09 09 09 09 20
    20 09 09 09 09 09 20 09 20 09 20 09 20 09 20 20 20 20 20 20 09 20 09 09 20
    20 20 20 20 20 20 20 09 20 20 09 09 09 20 09 09 09 09 20 09 20 09 20 20 20

When we replace these values with other characters, we notice it looks
like
it could represent a QR code

    0000000     0 0   0000000
    0     0 0 0 00000 0     0
    0 000 0    0  00  0 000 0
    0 000 0 00000 0   0 000 0
    0 000 0  0   0  0 0 000 0
    0     0 00  0000  0     0
    0000000 0 0 0 0 0 0000000
             00 0 0 0
    00000 0000 000 000 0 0 0
    0 0000    00 0 00    0 0
    0 00  0 0 0   0000 00   0
       000     0  00000000
       00000000   0  00   000
    000 0  0   00 0  0 0   00
    0 0 000000  00 00 00 0000
    0  000 0 0 0   0 0  00  0
    0  0 00  00   000000000
            000   0 0   0 0 0
    0000000 0 0  00 0 0 0  00
    0     0       000   0  00
    0 000 0 0 00000000000 00
    0 000 0 00 00 0 0000 0000
    0 000 0 000     0 00    0
    0     0 0 0 0 000000 0  0
    0000000 00   0    0 0 000

So we write a little script to convert theses values to a QR code

    from PIL import Image
    from qrtools import QR

    qrcode="0000000111110101110000000011111010101000001011111001000101111011001101000100100010100000101110100010010001011011101101010001001111101001100001101111100000000101010101010000000111111111001010101111111100000100001000100010101010100001111001010011110101010011010101110000100111011100011111011000000001111110000000011101100111000000101101110010110101110001010000001100100100100000110001010101110101100110011010011001110000000001111111111000111010111010100000000101011001010101100011111011111110001110110001000101010000000000010010100010100100101000010000010001010001111101001111001111101010101000000101100000000100111011110101000"
    outimgname = "dec6_qrcode.png"

    outimg = Image.new( 'RGB', (25,25), "white")
    pixels_out = outimg.load()

    for i in range(0,len(qrcode)):
        if qrcode[i] == '0':
            pixels_out[i%25,i/25]=(0,0,0)

    outimg=outimg.resize((250,250))
    outimg.save(outimgname,"png")

    myCode = QR(filename=outimgname)

    if myCode.decode():
        print myCode.data_to_string()
{: .language-python}

This outputs the following QR code and the nugget

![](writeupfiles/dec6_qrcode.png)


