---
layout: writeup

title: Missing Gift
level:  # optional, for events that use levels
difficulty: easy
categories: []
tags: []

flag: HV22{this-is-a-w4ste-of-pl4stic}

---

## Challenge

Like every year the elves were busy all year long making the best toys in Santas workshop. This year they tried some new fabrication technology. They had fun using their new machine, but it turns out that the last gift is missing.

Unfortunately, Alabaster who was in charge of making this gift is not around, because he had to go and fulfill his scout elf duty as an elf on the shelf.

But due to some very lucky circumstances the IT-guy elf was capturing the network traffic during this exact same time.
Goal:

Can you help Santa and the elves to fabricate this toy and find the secret message?

[tcpdump.pcap](writeupfiles/dec5/tcpdump.pcap)


## Solution

We look around the pcap, filter for http in the top bar, notice a POST request with a big load:

![](writeupfiles/dec5/screenshot-wireshark.png)

So we right-click on this and Follow the TCP stream, we see a gcode file:

![](writeupfiles/dec5/screenshot-wireshark2.png)

This is a file for a 3D printer, that sounds promising!

We export this part of the file, name it [`hv22.gcode`](writeupfiles/dec5/hv22.gcode), and open it in Cura:

![](writeupfiles/dec5/screenshot-cura.jpg)

Whoo! this model is the flag!


