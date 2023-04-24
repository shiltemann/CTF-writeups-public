---
layout: writeup
title: 'Intercepted Conversations Pt. 2'
level: 3
difficulty:
points:
categories: [forensics]
tags: []
flag: IceCTF{4Lw4y5_US3_5s1_AnD_n3VR4r_mAKe_Y0ur_0wN_cRyp70}
---
## Challenge

We managed to intercept more of the hacker's traffic, unfortunately
since our last encounter they have figured out that they're being
watched. They've gotten more clever in their communication so we need
you to try to make sense of this traffic.

## Solution

The pcap file has two tcp streams (in wireshark I normally click "follow
TCP  
stream" and then it inserts a nice `tcp.stream eq 0` and then I  
increment/decrement to find all streams)

The first stream is IRC chat

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

The DCC looks someone sends a python file which we find in the next
stream.  
This was extracted to [encode.pyc](writeupfiles/encode.pyc). Uncompyle
will extract the original python from this:

    $ uncompyle6 encode.pyc > encode.py

We [reversed](writeupfiles/decode.py) the
[algorithm](writeupfiles/encode.py),  
when run with the base64 string found in the IRC chat, it prints out the
flag:

    $ python decode.py
    IceCTF{4Lw4y5_US3_5s1_AnD_n3VR4r_mAKe_Y0ur_0wN_cRyp70}
