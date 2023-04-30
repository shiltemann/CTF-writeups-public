---
layout: writeup

title: Thumper's PWN 2
level: 7     # optional, for events that use levels (like HackyEaster)
difficulty: hard     # easy/medium/hard etc, if applicable
points: 300        # if used
categories: [pwn]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

Thumper got one step closer to Dr. Evil but there's still a lot he has to learn. That's why he's practicing the ancient
art of ROP. Help him solve this challenge by reading the file FLAG, so he can be on his way.

Target: `nc ch.hackyeaster.com 2314`

Note: The service is restarted every hour at x:00.

[thumperspwn2.zip](writeupfiles/thumperspwn2.zip)

## Solution

We get a `main.c` file:

```c
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>

#include "seccomp-bpf.h"

bool sec_done = false;

void activate_seccomp()
{
    struct sock_filter filter[] = {
        VALIDATE_ARCHITECTURE,
        EXAMINE_SYSCALL,

        ALLOW_SYSCALL(mprotect),
        ALLOW_SYSCALL(mmap),
        ALLOW_SYSCALL(munmap),
        ALLOW_SYSCALL(exit_group),
        ALLOW_SYSCALL(read),
        ALLOW_SYSCALL(write),
        ALLOW_SYSCALL(open),
        ALLOW_SYSCALL(close),
        ALLOW_SYSCALL(openat),
        ALLOW_SYSCALL(brk),
        ALLOW_SYSCALL(newfstatat),
        ALLOW_SYSCALL(fstat),
        ALLOW_SYSCALL(ioctl),
        ALLOW_SYSCALL(lseek),
        KILL_PROCESS,
    };

    struct sock_fprog prog = {
        .len = (unsigned short)(sizeof(filter) / sizeof(struct sock_filter)),
        .filter = filter,
    };

    prctl(PR_SET_NO_NEW_PRIVS, 1, 0, 0, 0);
    prctl(PR_SET_SECCOMP, SECCOMP_MODE_FILTER, &prog);
    sec_done = true;
}

void vuln() {
  char buf[32];
  printf("Are you a master of ROP?\n");
  printf("Show me what you can do: ");
  gets(buf);
}

void main() {
  setbuf(stdout, NULL);
  setbuf(stdin, NULL);

  if (!sec_done) {
    activate_seccomp();
  }

  vuln();
}
```
{: file=main.c}


When googling, one can find pretty much [pre-composed ROP exploits to read a file named 'flag'](https://gist.github.com/rverton/42340ee4bd3482c6262db2bc9bbb9ef5) (32 bit), we just need to know how to adapt it to our situation. Additionally this article is quite instructive (and leads to python at the end)



We can then combine this, hopefully with `ROPgadget` to find apporpriate gadgets:


```
user@p-ctf:~/Downloads/thumperspwn2$ ropgadget --binary main --only "pop|ret"
Gadgets information
============================================================
0x00000000004007fc : pop r12 ; pop r13 ; pop r14 ; pop r15 ; ret
0x00000000004007fe : pop r13 ; pop r14 ; pop r15 ; ret
0x0000000000400800 : pop r14 ; pop r15 ; ret
0x0000000000400802 : pop r15 ; ret
0x00000000004007fb : pop rbp ; pop r12 ; pop r13 ; pop r14 ; pop r15 ; ret
0x00000000004007ff : pop rbp ; pop r14 ; pop r15 ; ret
0x0000000000400608 : pop rbp ; ret
0x0000000000400803 : pop rdi ; ret
0x0000000000400801 : pop rsi ; pop r15 ; ret
0x00000000004007fd : pop rsp ; pop r13 ; pop r14 ; pop r15 ; ret
0x0000000000400536 : ret
0x0000000000400542 : ret 0x200a
0x00000000004006f2 : ret 0x2be

Unique gadgets found: 13
```
