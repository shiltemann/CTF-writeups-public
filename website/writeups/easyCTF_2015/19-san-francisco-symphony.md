---
layout: writeup
title: San Francisco Symphony
level: 
difficulty: 
points: 
categories: []
tags: []
flag: |-
  $ strings sfs | grep easyctf
  easyctf{w0aw_stor1ng_fl4gs_in_pla1nt3xt_i5_s0oper_s3cure}
---
**Challenge**  
Who knew musicians could program? They put a flag inside
/problems/sfs/sfs! But when I run the program, it's not printing out the
flag. Find the flag!

## Solution

    $ ls -la /problems/sfs
    drwxr-xr-x  2 root root 4096 Nov  4 01:02 .
    drwxr-x--x 10 root root 4096 Nov  4 23:56 ..
    -rwxr-xr-x  1 root root 8586 Nov  4 01:02 sfs
    --wx--x--x  1 root root  166 Nov  4 01:02 sfs.c

Only root can see the source file, but strings helps us output

    $ strings sfs | grep easyctf
    easyctf{w0aw_stor1ng_fl4gs_in_pla1nt3xt_i5_s0oper_s3cure}

