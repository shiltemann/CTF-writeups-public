---
layout: writeup
title: 'Egg 23: Beat the Nerd Master'
level:
difficulty:
points:
categories: []
tags: []
flag: e9fg6eeEncsouIZGM88g

---

## Challenge:

*Did you beat the Swordmaster in Monkey Island? Even if, it ain't gonna
help you this time. Get to know the mighty Nerd Master!*

*Connect to port 1400 of hackyeaster.hacking-lab.com, and start the
battle.*

*Here's an insult to start with: Go 127.0.0.1 to your mummy.*

## Solution:

If you telnet in to the server a insult/comeback battle start. It is our
turn first and we start with the insult in the hint. The server responds
`Won't work. I only support IPv6`.
Then the opponent comes with a new insult, but we don't know how to
respond and lose the battle. Next time we start with this new insult and
remember how the opponent responded.
Repeating this we get a list of all insults and comebacks. Whenever we
have a good comeback we knock a point of health off our opponent, but if
we repeat an insult already used
we lose a point of health. First player to run out of health loses.

We make a script to play for us because there is a short timeout

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
{: .language-python}

We run this code and get the following battle:

    Do you feel brave enough to challenge the mighty nerdmaster? (y|n)

    OK, let's start, greenhorn. You won't have a chance. You may start!

    ---- YOUR TURN ----
    Af7ter th1s f1gh7, I w1ll pwn ur b0x3n.
    Check your settings - you seem to have chosen the Klingon keyboard layout.

    ---- MY TURN ----
    format C:
    Specified drive does not exist.
    Arrgh! That's right.
    Point for you! My health: 7, your health: 4

    ---- YOUR TURN ----
    You must be jealous when seeing my phone's display.
    Not really - Your pixels are so big, some of them have their own region code!

    ---- MY TURN ----
    I have more friends than you.
    Yeah, but only until you update your Facebook profile with a real picture of you!
    Arrgh! That's right.
    Point for you! My health: 6, your health: 4

    ---- YOUR TURN ----
    This fight is like a hash function - it works in one direction only.
    Too bad you picked LM hashing.

    ---- MY TURN ----
    Ping! Anybody there?
    ICMP type 3, code 13: Communication Administratively Prohibited
    Arrgh! That's right.
    Point for you! My health: 5, your health: 4

    ---- YOUR TURN ----
    I bet you don't even understand binary.
    Sure I do. Me and you, we are 10 different kind of persons.

    ---- MY TURN ----
    You'll be 0xdeadbeef soon.
    Not as long as I have my 0xcafebabe.
    Arrgh! That's right.
    Point for you! My health: 4, your health: 4

    ---- YOUR TURN ----
    Tell me your name, hobo. I need to check your records.
    My name is bob'; DROP TABLE VALJ;--

    ---- MY TURN ----
    Go 127.0.0.1 to your mummy.
    Won't work. I only support IPv6.
    Arrgh! That's right.
    Point for you! My health: 3, your health: 4

    ---- YOUR TURN ----
    After loosing to me, your life won't be the same anymore.
    A Life? Cool! Where can I download one of those?

    ---- MY TURN ----
    You should leave your cave and socialize a bit.
    I'm not anti-social. I'm just not user friendly.
    Arrgh! That's right.
    Point for you! My health: 2, your health: 4

    ---- YOUR TURN ----
    I'll check you out - any last words?
    svn:ignore

    ---- MY TURN ----
    You're so slow, you must have been written in BASIC.
    At least I don't have memory leaks like you.
    Arrgh! That's right.
    Point for you! My health: 1, your health: 4

    ---- YOUR TURN ----
    1f u c4n r34d th1s u r s70p1d.
    You better check your spelling. Stoopid has two 'o's.

    ---- MY TURN ----
    Pna lbh ernq guvf?
    EBG13 vf sbe ynzref.
    Arrgh! That's right.

    Respect! you've beaten the mighty nerd master! Here's your egg:
    http://hackyeaster.hacking-lab.com/hackyeaster/images/egg_23_j7vzfUzftszdf754fXDS.png

When we finally win we get the link to our easter egg:

![](images/egg_23_qrcode_small.png)

