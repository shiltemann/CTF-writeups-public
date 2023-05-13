---
layout: writeup
title: 'Game, Set, and Hash'
level:
difficulty:
points:
categories: []
tags: []
flag: fl3uB75EGl1sadBNycvx

---
## Challenge

Can you beat the tennis master?

    hackyeaster.hacking-lab.com:8888

## Solution

We connect to the service and see the following:

    $ nc hackyeaster.hacking-lab.com 8888

    Ready for the game?
    y
    Let's go!
    5cc3f82838ba7260203e4590ce03d00e1663d41f6a5167144f5c95d6be2166a0
    y
    Wrong! Point for me.
    ----------------------
    Player > 0 0
    Master   0 15
    ----------------------
    75fba329cb6ee8907a1f009b69353d8c0e9a8228b25a1095c7976efc1eaa259d
    k
    Wrong! Point for me.
    ----------------------
    Player > 0 0
    Master   0 30
    ----------------------
    ed968e840d10d2d313a870bc131a4e2c311d7ad09bdf32b3418147221f51a6e2
    h
    Wrong! Point for me.
    ----------------------
    Player > 0 0
    Master   0 40
    ----------------------
    1a8d9de9490302d2d234f9c92bc2c854910ebf70c5eae016b86f03427fef03b7
    h
    Wrong! Point for me.
    ----------------------
    Player   0 0
    Master > 1 0
    ----------------------
    4ac2ffdb1428fe6572cb751b5b4b861563dc15978e7b140a7f5212cf69ccd35e
    h
    Wrong! Point for me.
    ----------------------
    Player   0 0
    Master > 1 15
    ----------------------
    5867183bb6a671e056f39df1b528221c66b7d7cd999e6660537f95fc95f8487d

..looks like we're playing hash tennis :)

They all appear to be hashes of the same length, and using
[crackstation][1] we find that:

    5cc3f82838ba7260203e4590ce03d00e1663d41f6a5167144f5c95d6be2166a0	sha256	office
    75fba329cb6ee8907a1f009b69353d8c0e9a8228b25a1095c7976efc1eaa259d	sha256	enjoy
    ed968e840d10d2d313a870bc131a4e2c311d7ad09bdf32b3418147221f51a6e2	sha256	aaaaa
    1a8d9de9490302d2d234f9c92bc2c854910ebf70c5eae016b86f03427fef03b7	sha256	blazer
    4ac2ffdb1428fe6572cb751b5b4b861563dc15978e7b140a7f5212cf69ccd35e	sha256	sharapova
    5867183bb6a671e056f39df1b528221c66b7d7cd999e6660537f95fc95f8487d	sha256	m080617

ok, so they're SHA-256 hashes, and they look to be mostly strings
commonly found on wordlists ..this shouldn't be too bad.

To speed this process up, we use this python implementation of
crackstation's hash lookup tables
https://github.com/moloch--/hashlookup.

We use a wordlist (hashkiller), index it, sort the index:

    $ git clone git clone git@github.com:moloch--/hashlookup.git

    $ cd hashlookup

    # we used hashkiller's list as wordlist.txt here
    $ ./createidx.py -a sha2-256 -w wordlist.txt

    [*] Creating Secure Hashing Algorithm 2 (256 bit) index ...
    [*] Reading wordlist.txt ...
    [*] Completed index file hashkiller.com-sha2-256.idx
    [$] All Done.

    $ make

    $ ./sortidx -r 2048 wordlist-sha2-256.idx
    Sorting file hashkiller.com-sha2-256.idx ...

    # let's test one
    $ ./LookupTable.py -a sha2-256 -i wordlist-sha2-256.idx -w wordlist.txt -c 5cc3f82838ba7260203e4590ce03d00e1663d41f6a5167144f5c95d6be2166a0

    		*** Results ***

    0)  5cc3f82838ba7260203e4590ce03d00e1663d41f6a5167144f5c95d6be2166a0 -> office
    [*] Total lookup time: 0.000882
    [$] Cracked 1 of 1 (100.00%)
{: .language-bash}

awesome, this works, and is fast, let's use it to play some tennis:

    import telnetlib
    import hashlib
    import sys
    sys.path.append('hashlookup')
    from LookupTable import LookupTable

    server = "hackyeaster.hacking-lab.com"
    port = 8888

    # start the match
    tn = telnetlib.Telnet(server, port)
    print tn.read_until("Ready for the game?")
    tn.write("y\n" )
    print tn.read_until("Let's go!\n")


    l = LookupTable('sha2-256', 'wordlist-sha2-256.idx', 'wordlist.txt')

    # play the game
    while True:
        # get it
        h = tn.read_until('\n').strip()
        print "h: "+h

        # crack it
        t = l.get_all([h])
        t = str(t[h])

        # return it
        print "Sending answer: " + t
        tn.write(t+"\n" )

        # print scoreboard
        print tn.read_until("----------------------")
        print tn.read_until("----------------------")
        print tn.read_until('\n')
{: .language-python}

we run this and win the game!

    $ python chall22_tennis.py
    Ready for the game?

    Let's go!

    h: a1bd1312d23002be258c9bb4642bbea77580353869a8ee8844e6940b7e0278b7
    Sending answer: qweasd
    Correct! Point for you.
    ----------------------

    Player > 0 15
    Master   0 0
    ----------------------


    h: 1f8b6d5a89242a0984816601b1a527ed239e16e213182481301d2706f4396703
    Sending answer: 4444444
    Correct! Point for you.
    ----------------------

    Player > 0 30
    Master   0 0
    ----------------------


    h: c90d569a30a14c7da4fcd1347792813a87d982c995f8376411d361289f161e09
    Sending answer: None
    Wrong! Point for me.
    ----------------------

    Player > 0 30
    Master   0 15
    ----------------------


    h: f527721c54550e8a0a7aac8da3116976e0b8f3083d67b614d169e15f3df1a76a
    Sending answer: estefania
    Correct! Point for you.
    ----------------------

    Player > 0 40
    Master   0 15
    ----------------------


    [..]

    h: c9bf553d456365516965e2977ec01d38ae7d30d57fae2c99aee02bea6f61ec32
    Sending answer: teamo2.
    Correct! Point for you.
    ----------------------

    Player   6 6 5 30
    Master > 0 0 0 0
    ----------------------


    h: 08a5c5fd12ecee53f4217d0b44369537c27389f7a75711028f5a1ad94845cccf
    Sending answer: bangfist
    Correct! Point for you.
    ----------------------

    Player   6 6 5 40
    Master > 0 0 0 0
    ----------------------


    h: 62fff1a28571a19d7a3126cbf190bc33e9115511d2ee4d6d5e8111410f0e93be
    Sending answer: tre2ike
    Correct! Point for you.
    ----------------------

    Player > 6 6 6
    Master   0 0 0
    ----------------------


    h: You win! Solution is: !stan-the_marth0n$m4n

we put the string `!stan-the_marth0n$m4n` into the egg-o-matic to get
our egg:

![](writeupfiles/egg22.png)



[1]: https://crackstation.net/
