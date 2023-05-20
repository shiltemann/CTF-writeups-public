---
layout: writeup
title: 'Dec 8: True 1337s'
level:
difficulty:
points:
categories: []
tags: [obfuscation]
flag: HV17-th1s-ju5t-l1k3-j5sf-uck!

---
... can read this instantly

## Challenge

I found this obfuscated code on a public FTP-Server. But I don't
understand what it's doing...

[File](writeupfiles/True.1337)

## Solution

The file looks like (truncated):

     exec(chr(True+True+True+True+True+True+True+True+True+True)+chr(True+True+
     __1337(_1337(1337+1337+1337+1337+1337+1337+1337+1337+1337+1337)+_1337(1337+1337+

Obfuscated python. The following snippet cleans up the code
significantly:

    for i in `seq 1 200`; do
        echo $i;
        q=$(python -c "print('+'.join(['True'] * $i))")
        sed -i "s/($q)/($i)/g" True.1337
        q=$(python -c "print('+'.join(['1337'] * $i))")
        sed -i "s/($q)/($i)/g" True.1337
    done
{: .language-bash}

This leaves us with two functions, one on each line, calling a bunch of
chrs.

    exec(chr(10)+chr(65)+chr(61)+chr(99)+chr(104)+chr(114)+
    __1337(_1337(10)+_1337(67)+_1337(61)+_1337(83)+_1337(65)+

Replacing that with a `print()` call on each, and cleaning up more, we
can see the original code:

    sed -i "s/exec/print/g" True.1337
    sed -i "s/__1337/print/g" True.1337
    sed -i "s/_1337/chr/g" True.1337

    python True.1337 > True.1338
{: .language-bash}

which now looks like:

    A=chr;__1337=exec;SANTA=input;FUN=print
    def _1337(B):return A(B//1337)

    C=SANTA("?")
    if C=="1787569":FUN(''.join(chr(ord(a) ^ ord(b)) for a,b in zip("{gMZF_MC_X\ERF[X","31415926535897932384626433832")))
{: .language-python}

Running it (or the original) with py3k + inputting the magic number
`1787569` results in our flag:

    $ python3 True.1338
    ?1787569
    HV17-th1s-ju5t-l1k3-j5sf-uck!
{: .language-bash}

