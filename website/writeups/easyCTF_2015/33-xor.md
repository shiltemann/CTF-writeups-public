---
layout: writeup
title: XOR
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{yo_dawg_i_heard_you_liked_xor}
---
## Challenge

This string has been encrypted using XOR!

    message = " $6<&1#><*\x1a!$2\x22\x1a,\x1a- $7!\x1a<*0\x1a),. !\x1a=*78"

## Solution

We perform all possible decryptions given that this is a simple XOR
encryption and see if we get anything resembling a flag.

    ct = " $6<&1#><*\x1a!$2\x22\x1a,\x1a- $7!\x1a<*0\x1a),. !\x1a=*78"
    
    for x in range (0,255):
        pt = ''
        for c in ct:
            pt += chr(ord(c) ^ x );
        print pt+" (x="+str(x)+")"
{: .language-python}

In this snippet of the output we see that when every character was XORed
with value 69, we got the flag:

    [..]
    d`rxbugzxn^e`vf^h^id`se^xnt^mhjde^yns| (x=68)
    easyctf{yo_dawg_i_heard_you_liked_xor} (x=69)
    fbpz`wexzl\gbtd\j\kfbqg\zlv\ojhfg\{lq~ (x=70)
    gcq{avdy{m]fcue]k]jgcpf]{mw]nkigf]zmp (x=71)
    [..]

