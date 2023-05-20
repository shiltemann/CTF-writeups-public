---
layout: writeup
title: 'Dec 13: muffin_asm'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-mUff!n-4sm-!s-cr4zY

---

*As M. said, kind of a different architecture!*

## Challenge

ohai \\o/

How about some custom asm to obsfucate the codez?

[Link](writeupfiles/muffin_asm.py)

## Solution

It is a python program, running a simple asm-like program:

    #!/usr/bin/env python

    import sys, struct

    ip, r, f = 0x00, [0x00]*4, [False]

    def _add(r1, r2): r[r1] = ((r[r1] + r[r2]) & 0xFF)
    def _addv(r1, v): r[r1] = ((r[r1] + v) & 0xFF)
    def _sub(r1, r2): r[r1] = ((r[r1] - r[r2]) & 0xFF)
    def _subv(r1, v): r[r1] = ((r[r1] - v) & 0xFF)
    def _xor(r1, r2): r[r1] = (r[r1] ^ r[r2])
    def _xorv(r1, v): r[r1] = (r[r1] ^ v)
    def _cmp(r1, r2): f[0] = (r[r1] == r[r2])
    def _cmpv(r1, v): f[0] = (r[r1] == v)
    def _je(o): global ip; ip = (o if f[0] else ip)
    def _jne(o): global ip; ip = (o if not f[0] else ip)
    def _wchr(r1): sys.stdout.write(chr(r[r1]))
    def _rchr(r1): r[r1] = ord(sys.stdin.read(1))

    ins = [_add, _addv, _sub, _subv, _xor, _xorv, _cmp, _cmpv, _je, _jne, _wchr, _rchr]

    def run(codez):
        global ip
        while ip < len(codez):
            c_ins = ins[ord(codez[ip])]
            if c_ins in [_je, _jne]:
                old_ip = ip
                c_ins(struct.unpack('<I', codez[(ip+1):(ip+5)])[0])
                if old_ip == ip: ip += 5
                continue
            num_of_args = c_ins.func_code.co_argcount
            if num_of_args == 0: c_ins()
            elif num_of_args == 1: c_ins(ord(codez[ip+1]))
            else: c_ins(ord(codez[ip+1]), ord(codez[ip+2]))
            ip += (1 + num_of_args)

    print '[ muffin asm ]'
    print 'muffinx: Did you ever codez asm?'
    run('\x04\x00\x00\x04\x01\x01\x04\x02\x02\x04\x03\x03\x05\x02\xbd\x00\x02\x00\x00 [..] \x01\x03\x00\x01')
{: .language-python}

Interesting, ok, lets run it.. We are asked for a flag and are told
"nope" when we enter it incorrectly:

    $ python muffin_asm.py
    [ muffin asm ]
    muffinx: Did you ever codez asm?
    << flag_getter v1.0 >>
    ohai, gimmeh flag: tardis
    [-] nope!
{: .language-bash}

The program likely checks the string one character at a time with the
`cmp` function, so we simply check what it is
checking against by changing one line:

    # def _cmp(r1, r2): f[0] = (r[r1] == r[r2])  # old
    def _cmp(r1, r2): sys.stdout.write(chr(r[r2])); f[0] = (r[r2] == r[r2]);
{: .language-python}

Now we run the program, and give it a random string at least as long as
the flag, and it just prints out the flag for us :D

    $ python muffin_asm.py
    [ muffin asm ]
    muffinx: Did you ever codez asm?
    << flag_getter v1.0 >>
    ohai, gimmeh flag: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    HV17-mUff!n-4sm-!s-cr4zY[+] valid! by muffinx :D if you liked the challenge, troll me @ twitter.com/muffiniks =D

Whoo! we have our flag `HV17-mUff!n-4sm-!s-cr4zY`

* * *

NOTE: below is my initial, overly complex solution:

We could try to reverse the program, but if the program checks the flag
one character
at a time and exits upon the first mismatch, we might be able to
determine the flag
by brute-forcing each subsequent character and determine the correct one
from the number
of cycles it takes before failure.

We adjust the program a bit to count the number of instructions and
build the flag
one character at a time:

    #!/usr/bin/env python

    import sys, struct, string

    ip, r, f = 0x00, [0x00]*4, [False]

    def _add(r1, r2): r[r1] = ((r[r1] + r[r2]) & 0xFF)
    def _addv(r1, v): r[r1] = ((r[r1] + v) & 0xFF)
    def _sub(r1, r2): r[r1] = ((r[r1] - r[r2]) & 0xFF)
    def _subv(r1, v): r[r1] = ((r[r1] - v) & 0xFF)
    def _xor(r1, r2): r[r1] = (r[r1] ^ r[r2])
    def _xorv(r1, v): r[r1] = (r[r1] ^ v)
    def _cmp(r1, r2): f[0] = (r[r1] == r[r2])
    def _cmpv(r1, v): f[0] = (r[r1] == v)
    def _je(o): global ip; ip = (o if f[0] else ip)
    def _jne(o): global ip; ip = (o if not f[0] else ip)
    def _wchr(r1): pass #sys.stdout.write(chr(r[r1]))
    def _rchr(r1):
        #r[r1] = ord(sys.stdin.read(1))
        global nextchar
        try:
            r[r1] = ord(myinput[nextchar])
        except:
            pass
        nextchar += 1

    ins = [_add, _addv, _sub, _subv, _xor, _xorv, _cmp, _cmpv, _je, _jne, _wchr, _rchr]


    def run(codez):
        global ip
        instrcount = 0
        while ip < len(codez):
            instrcount += 1
            c_ins = ins[ord(codez[ip])]
            if c_ins in [_je, _jne]:
                old_ip = ip
                c_ins(struct.unpack('<I', codez[(ip+1):(ip+5)])[0])
                if old_ip == ip: ip += 5
                continue
            num_of_args = c_ins.func_code.co_argcount
            if num_of_args == 0: c_ins()
            elif num_of_args == 1:
                c_ins(ord(codez[ip+1])),
            else:
                c_ins(ord(codez[ip+1]), ord(codez[ip+2]))
            ip += (1 + num_of_args)
        return instrcount


    print '[ muffin asm ]'
    print 'muffinx: Did you ever codez asm?'
    program = '\x04\x00\x00\x04\x01\x01\x04\x02\x02\x04\x03\x03\x05\x02\xbd\x00\x02\x00\x00 [..] \x01\x03\x00\x01'

    # find the flag by looking at which subsequent letter increases the total instruction count
    # of the program
    print 'Looking for the flag..'
    maxinstrcount = 1520 #number of instructions when no correct characters
    flag = ''

    for i in range(0,29):
        for c in string.letters+string.digits+string.punctuation:
            myinput = flag + c
            ip = 0
            nextchar = 0
            numinstr = run(program)
            if numinstr > maxinstrcount:
                flag += c
                maxinstrcount = numinstr
                break

    print flag
{: .language-python}

[full program](writeupfiles/muffin_asm_bf.py)

This outputs:

    $ python muffin_asm_bf.py
    [ muffin asm ]
    muffinx: Did you ever codez asm?
    Looking for the flag..
    HV17-mUff!n-4sm-!s-cr4zY
{: .language-bash}

* * *

