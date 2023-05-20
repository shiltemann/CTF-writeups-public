---
layout: writeup
title: 'Reversing 400: Radix’s Terminal'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{bAsE_64_eNCoDiNg_iS_EAsY_41799451}
---
**Challenge**  
 Can you find the password to [Radix's login?](./writeupfiles/radix) You
can also find the executable in
/problems/radix-s-terminal\_0\_b6b476e9952f39511155a2e64fb75248?

**Solution**  
Run strings on the binary and find the string:

    cGljb0NURntiQXNFXzY0X2VOQ29EaU5nX2lTX0VBc1lfNDE3OTk0NTF9

The hint suggests base64 encoding, base64 decode this for the flag.

