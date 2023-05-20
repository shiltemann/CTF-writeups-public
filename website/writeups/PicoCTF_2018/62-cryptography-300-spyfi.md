---
layout: writeup
title: 'Cryptography 300: SpyFi'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{@g3nt6_1$_th3_c00l3$t_3355197}
---
**Challenge**  
 James Brahm, James Bond's less-franchised cousin, has left his secure
communication with HQ running, but we couldn't find a way to steal his
agent identification code. Can you? Conect with `nc
2018shell1.picoctf.com 31123`.
[Source.](writeupfiles/spy_terminal_no_flag.py)

## Solution

When we connect, we are asked for our situation report, and are given
and encrypted string (different depending on our input)

    $ nc 2018shell1.picoctf.com 31123
    Welcome, Agent 006!
    Please enter your situation report: bla
    4d6276d172d79a9b7da9098f69e9b403024a64082b1f2cdfa32d27d78ca83236e30cf5302c3f000ff2cb7b8a7106351c3fbf30cac64b016bb3fd112a28ed5478da62ea7c24a6c2956c96fbaaa3097dee75e268403189f06f1250226d6d557f9dcbadccc3485faa80ca8c04ff845819b5fb49b0414a48d3cb4993e15d5c9a05a7935360d079133605136cd91c3a85559adf26219527ad17f80a6fece062d71d6f
    
    $ nc 2018shell1.picoctf.com 31123
    Welcome, Agent 006!
    Please enter your situation report: zzzzzzzzzzzzzzz
    4d6276d172d79a9b7da9098f69e9b403024a64082b1f2cdfa32d27d78ca83236e30cf5302c3f000ff2cb7b8a7106351cc71a0b099baa79288b88380b6e1176093062f7a7751b1cd658a5bd1068c1e66da61b6aa76648777223b15da96a1f249e4ea96647c833ab65e5ec4ed409fe3414af4759f13ad15cc038b0084cdd2f7440456b962fdff9ae64e7a083561b1fda1a8e456864e71843a630207321a2728d26d94dd9b0159dce7368382298d639b818
{: .language-bash}

Looks like the beginning is always the same.. After some
experimentation, 11z's pads the first bit of text nicely to 64
characters, then every other section are 16 character sections.

    $ python -c 'print("z" * 11 + "a" * 64 + "b" * 64)' | nc 2018shell1.picoctf.com 31123 | sed 's/Please enter your situation report: //g' | fold -w 32
    Welcome, Agent 006!
    4d6276d172d79a9b7da9098f69e9b403
    024a64082b1f2cdfa32d27d78ca83236
    e30cf5302c3f000ff2cb7b8a7106351c
    c71a0b099baa79288b88380b6e117609
    99908ad37adef3fb5a94680c5a64c6ca # aaaaaaaaaaaaaaaa
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    5e6605d02027026603b6f00863d32bc5 # bbbbbbbbbbbbbbbb
    5e6605d02027026603b6f00863d32bc5
    5e6605d02027026603b6f00863d32bc5
    5e6605d02027026603b6f00863d32bc5
    843f702396b187b716fdfd818e0ebff1
    20de53dad133d6a28195473711d21861
    7577b2866bbc75dcb721fc57c1cc51ad
    ad9aa6494e74fdfa8d484b6a0a895ea6
    30ad4028ee72122451ffdbdad0e04c37
    efc6c5598b5e33fee6d2c043ed8032e7
    292a0b28f8fb2f711e633fd63f526f09

So obviously prefixed/postfixed with something and we'll need to figure
out the contents. Using `$` for newline, we can get this mapping

    Agent,$Greetings   4d6276d172d79a9b7da9098f69e9b403
    . My situation r   024a64082b1f2cdfa32d27d78ca83236
    eport is as foll   e30cf5302c3f000ff2cb7b8a7106351c
    ows:$zzzzzzzzzzz   c71a0b099baa79288b88380b6e117609

And that means the end parts maps:

    ----------------   ================================
    
    $My agent identi   843f702396b187b716fdfd818e0ebff1
    fying code is: ?   20de53dad133d6a28195473711d21861
    ????????????????   7577b2866bbc75dcb721fc57c1cc51ad
    ????????????????   ad9aa6494e74fdfa8d484b6a0a895ea6
    .$Down with the    30ad4028ee72122451ffdbdad0e04c37
    Soviets,$006$000   efc6c5598b5e33fee6d2c043ed8032e7

Making a total guess about the last line. So let's see if we can find
the right  
number of 0s to pad with.

Let's at least confirm we're on the right path?

    $ python -c 'print("z" * 11 + "a" * 64 +  "fying code is: p" +"a" * 64)' | nc 2018shell1.picoctf.com 31123 | sed 's/Please enter your situation report: //g' | fold -w 32
    Welcome, Agent 006!
    4d6276d172d79a9b7da9098f69e9b403
    024a64082b1f2cdfa32d27d78ca83236
    e30cf5302c3f000ff2cb7b8a7106351c
    c71a0b099baa79288b88380b6e117609
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    20de53dad133d6a28195473711d21861 # fying code is: p
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    99908ad37adef3fb5a94680c5a64c6ca
    843f702396b187b716fdfd818e0ebff1
    20de53dad133d6a28195473711d21861 # MATCH!!!!
    7577b2866bbc75dcb721fc57c1cc51ad
    ad9aa6494e74fdfa8d484b6a0a895ea6
    30ad4028ee72122451ffdbdad0e04c37
    efc6c5598b5e33fee6d2c043ed8032e7
    292a0b28f8fb2f711e633fd63f526f09

So now we need to push it around so we can guess the characters in the
middle  
and check against the hash lower down.

This was semi-automated in

    import string
    import sys
    import time
    import socket
    
    for z in range(0, 95, 20):
        nc = Netcat('2018shell1.picoctf.com', 31123)
        nc.read() # Hello
        nc.read() # enter report
        wrap_start = 'z' * 11 + 'a' * 16
    
        wrap_end = 'a' * (16 + 11)  +'\n'
    
        # picoCTF{@g3nt6_1$_th3_c00l3$t_3355197}
        inputs = [
            'c00l3$t_3355197' + y
            for y in ['_'] + list(string.printable[z:min(z + 20, 95)])
        ]
    
    
        nc.write(wrap_start + ''.join(inputs) + wrap_end)
    
        resp = nc.read() # output
    
    
        def splitn(line, n=32):
            return [line[i:i+n] for i in range(0, len(line), n)]
    
    
        # split on the 'a' * 16
        # print('\n'.join(splitn(resp)))
    
        prefix, queries, postfix = resp.split('99908ad37adef3fb5a94680c5a64c6ca')
    
        pm = list(splitn(postfix))
    
        # ignore prefix
    
        for (q, i) in zip(splitn(queries), inputs):
            print(i, q in pm)
            if q in pm:
                sys.exit()
    
        time.sleep(1)
{: .language-python}

This would pass 20 'query' strings at a time (part of the string + 1 new

letter), since the script seemed to limit the volume of input it would
actually  
attempt. This actualy worked surprisingly well. We started with the
input string  
`ode is: picoCTF` and have it find the `{`, and then just remove the
left most  
character, decrease the counter for the postfix padding of `a`s, and add
the  
new letter to the end of the known input string.

