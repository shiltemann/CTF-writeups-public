---
layout: writeup
title: 'Day 03: Packed Gifts'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{ZipCrypt0_w1th_kn0wn_pla1ntext_1s_easy_t0_decrypt}
---
## Description

One of the elves has unfortunately added a password to the last presents
delivery and we cannot open it. The elf has taken a few days off after
all the stress of the last weeks and is not available. Can you open the
package for us?

We found the following packages:

* [Package 1](writeupfiles/dec3_package1.zip)
* [Package 2](writeupfiles/dec3_package2.zip)

## Solution

We see that package2 is encrypted, but package1 is not. They both
contain the same 100 files (`0000.bin` .. `0099.bin`), so we try a known
plaintext attack

With [BKcrack][1] (and using their [tutorial][2]):

    $ bkcrack -C dec3_package2.zip -c 0000.bin -P dec3_package1.zip -p 0000.bin
{: .language-bash}

Which didn't find the solution, but we try it with all the common files,
which found the keys when using file `0053.bin`

    bkcrack 1.0.0 - 2020-11-11
    Generated 4194304 Z values.
    [22:52:28] Z reduction using 151 bytes of known plaintext
    100.0 % (151 / 151)
    53880 values remaining.
    [22:52:40] Attack on 53880 Z values at index 7
    Keys: 2445b967 cfb14967 dceb769b
    68.8 % (37077 / 53880)
    [23:16:34] Keys
    2445b967 cfb14967 dceb769b

now we can to recover `flag.bin` file using these found keys as follows:

    $ bkcrack -C dec3_package2.zip -c flag.bin -k 2445b967 cfb14967 dceb769b -d flagout.bin
    $ ../tools/inflate.py < flagout.bin > flagout2.bin
    $ base64 -d flagout2.bin
    HV20{ZipCrypt0_w1th_kn0wn_pla1ntext_1s_easy_t0_decrypt}
{: .language-bash}



[1]: https://github.com/kimci86/bkcrack
[2]: https://github.com/kimci86/bkcrack/blob/master/example/tutorial.md
