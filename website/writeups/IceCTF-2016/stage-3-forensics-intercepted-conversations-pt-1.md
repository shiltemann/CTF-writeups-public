---
layout: writeup
title: 'Intercepted Conversations Pt. 1'
level: 3
difficulty:
points: 115
categories: [forensics]
tags: [pcap]
flag: IceCTF{Wh0_l1K3S_qw3R7Y_4NYw4y5}
---
## Challenge

This traffic was picked up by one of our agents. We think this might be
a conversation between two elite hackers that we are investigating. Can
you see if you can analyze the data? intercept.pcapng

## Solution

The pcap file consists solely of the logs of a USB hub being plugged in
and a
single phrase typed. Once the pcap file was opened with wireshark, based
on [a
suggestion on the
internet][1]
I filtered with

    usb.data_flag == "present (0)" && usb.device_address == 21

which seemed to filter our enough of just what I wanted, USB interrupts
from
the device to the host. These could be exported to XML where we can look
at the
"Leftover Capture Data" by extracting that into a separate file:

    xpath -e '//field[@name="usb.capdata"]/@value' -q cap.xml | grep -v 0000000000000000 > keyboard.data
{: .language-console}

This file starts to have some semblance of looking like keypresses.
E.g.:

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

I believe the 20/02 in the leading byte is the shift key, but not sure
about
the difference between 0x02 and 0x20. You can see the shift key being
pressed,
another letter being typed (0x0a), the shift key is still held, then
released,
two more letters are typed (0x0c, 0x07), shift key is depressed again,
and
three individual letters are typed. This looks a LOT like "IceCTF"

Using the `KEY_CODES` provided by the [helpful person][2],
it was possible to dump this as-is, which yielded

    GIDIKY{ J0-P1V3-X3O7T-4LT4T5}

Shoot. But! The braces are in the right place, and dashes are shifted to

underscores. so this looks quite promising. Adding the shift key and
doing some
re-mapping, we can get to

    IceCTF{!j0_p1V3!_x!3O7T_4LT!4t5}

Now just a mystery to figure out the rest. I would somewhat believe that
they
numbers are in the right places, since those are the numerals normally
used in
leetspeak. The rest of the alphabet shows no discernable pattern. The
other
guy's keyboard was helpfully done sequentially, this does not seem to be
the
same for this keyboard.

Process in [proc.py](writeupfiles/proc.py)

Turns out we needed to translate qwerty keys to dvorak to get the flag

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
{: .language-python}


[1]: https://ask.wireshark.org/questions/11054/analysing-usb-traffic
[2]: https://webstersprodigy.net/2012/11/09/csaw-2012-quals-tutorialwriteup/
