---
layout: writeup
title: Hardwood Floors
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{fl00r_d1visi0n}
---
## Challenge

Our intelligence tells us that this function was used to encrypt a
message. They also managed to capture a spy while in the field.
Unfortunately, our interrogators only managed to find the ciphertext of
a message on his phone. Can you help us recover the secret message?

function:

    message = "<redacted>"
    key = 3
    encrypted = ' '.join([str(ord(c)//key) for c in message])
    print(encrypted)
{: .language-python}

ciphertext:

    27 39 33 34 10 36 32 33 35 10 37 34 10 38 35 34 37 38 15 15 15 10 33 32 38 40 33 38 34 41 34 36 16 16 38 31 33 16 39 35 38 35 16 36 41

## Solution

All characters were integer-divided by 3 during encryption. So for every
ciphertext character, there are threee possible plaintext characters
(x3, x3+1 x3+2)

    floors=open("floors.txt").readline().rstrip().split()
    
    for fl in floors:
        val=int(fl)*3
        print chr(val)+"-"+chr(val+1)+"-"+chr(val+2)
{: .language-python}

output:

    Q-R-S
    u-v-w
    c-d-e
    f-g-h
    --
    l-m-n
    `-a-b
    c-d-e
    i-j-k
    --
    o-p-q
    f-g-h
    --
    r-s-t
    i-j-k
    f-g-h
    o-p-q
    r-s-t
    --.-/
    --.-/
    --.-/
    --
    c-d-e
    `-a-b
    r-s-t
    x-y-z
    c-d-e
    r-s-t
    f-g-h
    {-|-}
    f-g-h
    l-m-n
    0-1-2
    0-1-2
    r-s-t
    ]-^-_
    c-d-e
    0-1-2
    u-v-w
    i-j-k
    r-s-t
    i-j-k
    0-1-2
    l-m-n
    {-|-}

For each position, choose one of the letters to find the flag,

    Such lack of rigor... easyctf{fl00r_d1visi0n}

