# PicoCTF 2017

## Overview


```
Title                            Category       Points  Flag
-------------------------------- -------------- ------  -----------------------------
Level 1
Digital Camouflage               Forensics      50      KN1Z6PXVy9
Special Agent User               Forensics      50
keyz                             Cryptography   20
Substitute                       Cryptography   40
Hash101                          Cryptography   50
ComputeAES                       Cryptography   50
ComputeRSA                       Cryptography   50
Hex2Raw                          Reversing      20
Raw2Hex                          Reversing      20
What is Web                      Web exploit    20
Bash loop                        Binary exploit 20
Just No                          Binary exploit 40
Internet Kitties                 Misc           10      fba9c41f9f0326b53919a2ab1ff20a69
Piazza                           Misc           10
Leaf of the Tree                 Misc           20
loooooong                        Misc           20
Leaf of the Forest               Misc           30
WorldChat                        Misc           30
Master Challenge                                50

Level 2

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

