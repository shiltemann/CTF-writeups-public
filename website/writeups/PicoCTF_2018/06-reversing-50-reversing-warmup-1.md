---
layout: writeup
title: 'Reversing 50: Reversing Warmup 1'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{welc0m3_t0_r3VeRs1nG}
---
## Challenge

Throughout your journey you will have to run many programs. Can you
navigate to  
`/problems/reversing-warmup-1_0_f99f89de33522c93964bdec49fb2b838` on the
shell server  
and run [this program](writeupfiles/run) to retreive the flag?

## Solution

    $ ssh ysje@2018shell1.picoctf.com
    picoCTF{who_n33ds_p4ssw0rds_38dj21}
    Welcome ysje!
    Your shell server account has been created.
    Please press enter and reconnect.
{: .language-bash}

We see a flag there but its not for this challenge

    $ cd /problems/reversing-warmup-1_0_f99f89de33522c93964bdec49fb2b838
    $ ./run
    picoCTF{welc0m3_t0_r3VeRs1nG}
{: .language-bash}

or

    $ strings run | grep picoCTF
    picoCTF{welc0m3_t0_r3VeRs1nG}
{: .language-bash}

