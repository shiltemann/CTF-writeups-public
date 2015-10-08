# -*- coding: utf-8 -*-
"""
Created on Sun Apr 12 18:01:57 2015

@author: zazkia
"""

import telnetlib
import random

comebacks={ "Go 127.0.0.1 to your mummy.":"Won't work. I only support IPv6.",
            "I bet you don't even understand binary.":"Sure I do. Me and you, we are 10 different kind of persons.",
            "This fight is like a hash function - it works in one direction only.":"Too bad you picked  hashing.",
            "Pna lbh ernq guvf?":"EBG13 vf sbe ynzref.",
            "You're so slow, you must have been written in BASIC.":"At least I don't have memory leaks like you.",
            "You'll be 0xdeadbeef soon.":"Not as long as I have my 0xcafebabe.",
            "After loosing to me, your life won't be the same anymore.":"A Life? Cool! Where can I download one of those?",
            "You must be jealous when seeing my phone's display.":"Not really - Your pixels are so big, some of them have their own region code!",
            "Af7ter th1s f1gh7, I w1ll pwn ur b0x3n.":"Check your settings - you seem to have chosen the Klingon keyboard layout.",
            "Ping! Anybody there?":"ICMP type 3, code 13: Communication Administratively Prohibited",                
            "1f u c4n r34d th1s u r s70p1d.":"You better check your spelling. Stoopid has two 'o's.",
            "Tell me your name, hobo. I need to check your records.":"My name is bob'; DROP TABLE VALJ;--",
            "I have more friends than you.":"Yeah, but only until you update your Facebook profile with a real picture of you!",            
            "format C:":"Specified drive does not exist.",    
            "You should leave your cave and socialize a bit.":"I'm not anti-social. I'm just not user friendly.",
            "I'll check you out - any last words?":"svn:ignore"}

# remember which insults we have already seen
seen={}
for i in comebacks.iterkeys():
    seen[i]=0

#connect to server
server="hackyeaster.hacking-lab.com"
port=1400

def comeback(insult):
    print comebacks[insult]
    tn.write(comebacks[insult]+"\n")

def insult():
    alreadyseen=1
    while alreadyseen:    
        insult = random.choice(comebacks.keys())
        alreadyseen=seen[insult]
    
    print insult
    tn.write(insult+"\n")
    print tn.read_until("---- MY TURN ----\n").strip("\n")
    seen[insult]=1

tn = telnetlib.Telnet(server, port)
print tn.read_until("Do you feel brave enough to challenge the mighty nerdmaster? (y|n)")
tn.write("y\n" )


while True:
    # we start. make an insult
    print tn.read_until("---- YOUR TURN ----")
    insult()
    
    #get their insult
    ins = tn.read_until("\n").strip("\n")
    print ins
    comeback(ins)
    seen[ins]=1




