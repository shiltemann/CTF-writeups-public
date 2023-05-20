---
layout: writeup
title: 'Cryptography 250: caesar cipher 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{cAesaR_CiPhErS_juST_aREnT_sEcUrE}
---
## Challenge

Can you help us decrypt [this message](writeupfiles/ciphertext2)? We
believe it is a form of a caesar cipher.

You can find the ciphertext in
`/problems/caesar-cipher-2_3_4a1aa2a4d0f79a1f8e9a29319250740a` on the
shell server.

    4-'3evh?'c)7%t#e-r,g6u#.9uv#%tg2v#7g'w6gA

## Solution

Looks like caesar shift cipher but with a larger alphabet. Since we know
the flag  
format, we can deduce the shift amount (e.g. `4` should become `p`). The
most likely candidate is the ascii table

    import string
    
    alphabet=""
    
    for i in range(32,126):
        alphabet += chr(i)
    
    print(alphabet)
    
    shift=alphabet.find('p')-alphabet.find('4')
    print(shift)
    
    ct="4-'3evh?'c)7%t#e-r,g6u#.9uv#%tg2v#7g'w6gA"
    
    pt=''
    for c in ct:
        pt += alphabet[ (alphabet.find(c)+shift)%len(alphabet) ]
    
    print(pt)
{: .language-python}

And this outputs the flag for us

