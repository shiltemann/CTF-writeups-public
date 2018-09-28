# PicoCTF 2018

## Overview


```
Title                            Category       Points  Flag
-------------------------------- -------------- ------  -----------------------------

Forensics Warmup 1 - Points: 50 - (Solves: 458)
Forensics - Unsolved
Forensics Warmup 2 - Points: 50 - (Solves: 398)
Forensics - Unsolved
General Warmup 1 - Points: 50 - (Solves: 688)
General Skills - Unsolved
General Warmup 2 - Points: 50 - (Solves: 673)
General Skills - Unsolved
General Warmup 3 - Points: 50 - (Solves: 676)
General Skills - Unsolved
Resources - Points: 50 - (Solves: 586)
General Skills - Unsolved
Reversing Warmup 1 - Points: 50 - (Solves: 234)
Reversing - Unsolved
Reversing Warmup 2 - Points: 50 - (Solves: 328)
Reversing - Unsolved
Crypto Warmup 1 - Points: 75 - (Solves: 73)
Cryptography - Unsolved
Crypto Warmup 2 - Points: 75 - (Solves: 286)
Cryptography - Unsolved
grep 1 - Points: 75 - (Solves: 329)
General Skills - Unsolved
net cat - Points: 75 - (Solves: 296)
General Skills - Unsolved
HEEEEEEERE'S Johnny! - Points: 100 - (Solves: 41)
Cryptography - Unsolved
Inspect Me - Points: 125 - (Solves: 194)
Web Exploitation - Unsolved
Aca-Shell-A - Points: 150 - (Solves: 35)
General Skills - Unsolved
Desrouleaux - Points: 150 - (Solves: 8)
Forensics - Unsolved
Logon - Points: 150 - (Solves: 35)
Web Exploitation - Unsolved
admin panel - Points: 150 - (Solves: 37)
Forensics - Unsolved
assembly-0 - Points: 150 - (Solves: 19)
Reversing - Unsolved
buffer overflow 0 - Points: 150 - (Solves: 22)
Binary Exploitation - Unsolved
caesar cipher 1 - Points: 150 - (Solves: 47)
Cryptography - Unsolved
hertz - Points: 150 - (Solves: 15)
Cryptography - Unsolved
hex editor - Points: 150 - (Solves: 27)
Forensics - Unsolved
ssh-keyz - Points: 150 - (Solves: 36)
General Skills - Unsolved
Irish Name Repo - Points: 200 - (Solves: 35)
Web Exploitation - Unsolved
Truly an Artist - Points: 200 - (Solves: 18)
Forensics - Unsolved
be-quick-or-be-dead-1 - Points: 200 - (Solves: 5)
Reversing - Unsolved
leak-me - Points: 200 - (Solves: 8)
Binary Exploitation - Unsolved
now you don't - Points: 200 - (Solves: 20)
Forensics - Unsolved
shellcode - Points: 200 - (Solves: 4)
Binary Exploitation - Unsolved
what base is this? - Points: 200 - (Solves: 24)
General Skills - Unsolved
Buttons - Points: 250 - (Solves: 18)
Web Exploitation - Unsolved
rsa-madlibs - Points: 250 - (Solves: 6)
Cryptography - Unsolved
echooo - Points: 300 - (Solves: 7)
Binary Exploitation - Unsolved

```

## General


## Forensics 50: Digital Camouflage

**Challenge**

We need to gain access to some routers. Let's try and see if we can find the password in the captured network data: [data.pcap](writeupfiles/data.pcap).


**Solution**

Open pcap with wireshark, `file -> export objects -> http` find a POST of a form to main.html, this contains the base64 encoded password:

```
Frame 112: 107 bytes on wire (856 bits), 107 bytes captured (856 bits)
Ethernet II, Src: PcsCompu_38:2c:5c (08:00:27:38:2c:5c), Dst: PcsCompu_3d:47:5d (08:00:27:3d:47:5d)
Internet Protocol Version 4, Src: 10.0.0.5, Dst: 10.0.0.1
Transmission Control Protocol, Src Port: 59163, Dst Port: 8080, Seq: 388, Ack: 1, Len: 41
    Source Port: 59163
    Destination Port: 8080
    [Stream index: 5]
    [TCP Segment Len: 41]
    Sequence number: 388    (relative sequence number)
    [Next sequence number: 429    (relative sequence number)]
    Acknowledgment number: 1    (relative ack number)
    1000 .... = Header Length: 32 bytes (8)
    Flags: 0x018 (PSH, ACK)
    Window size value: 457
    [Calculated window size: 29248]
    [Window size scaling factor: 64]
    Checksum: 0xd23f [unverified]
    [Checksum Status: Unverified]
    Urgent pointer: 0
    Options: (12 bytes), No-Operation (NOP), No-Operation (NOP), Timestamps
    [SEQ/ACK analysis]
    TCP payload (41 bytes)
    TCP segment data (41 bytes)
[2 Reassembled TCP Segments (428 bytes): #110(387), #112(41)]
Hypertext Transfer Protocol
HTML Form URL Encoded: application/x-www-form-urlencoded
    Form item: "userid" = "spiveyp"
    Form item: "pswrd" = "S04xWjZQWFZ5OQ=="
        Key: pswrd
        Value: S04xWjZQWFZ5OQ==
```

we base64 decode the `pswrd` value to get the flag

**Flag**

```
KN1Z6PXVy9
```

## Misc 10: Internet Kitties

**Challenge**

I was told there was something at IP shell2017.picoctf.com with port 40660. How do I get there? Do I need a ship for the port?

**Solution**

```
$ nc shell2017.picoctf.com 40660
Yay! You made it!
Take a flag!
fba9c41f9f0326b53919a2ab1ff20a69
```

**Flag**

```
fba9c41f9f0326b53919a2ab1ff20a69
```

## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**


## Challenge

**Challenge**

**Solution**

**Flag**

