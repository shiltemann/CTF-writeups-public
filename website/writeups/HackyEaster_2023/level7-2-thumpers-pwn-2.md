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


When googling, one can find pretty much [pre-composed ROP exploits to read a file named 'flag'](https://gist.github.com/rverton/42340ee4bd3482c6262db2bc9bbb9ef5) (32 bit), we just need to know how to adapt it to our situation. Additionally [this article](https://codearcana.com/posts/2013/05/28/introduction-to-return-oriented-programming-rop.html) is quite instructive (and leads to python at the end)



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


## sas

good reading: https://devel0pment.de/?p=2282

We unzip the file and find an executable called `main`

When we execute it, we are asked to provide an input, an not much seems to happen after that:

```
 ./main
Are you a master of ROP?
Show me what you can do: Hello World
```

Let's see what happens if we give it a long string:

```
$ ./main
Are you a master of ROP?
Show me what you can do: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
[1]    266408 segmentation fault (core dumped)  ./main
```

We experiment a bit more, and see that if we give it 40 characters we get

```
$ ./main
Are you a master of ROP?
Show me what you can do: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
[1]    266372 invalid system call (core dumped)  ./main
```

invalid system call, interesting.

Let's look at the binary more closely:

```
$ file main
main: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), dynamically linked, interpreter /lib64/ld-linux-x86-64.so.2, for GNU/Linux 3.2.0, BuildID[sha1]=f60bf91ea714b5e0970b4f71f112d78ae4515b9e, not stripped
```

We examine it with Radare2:

```bash
$ r2 -A main
INFO: Analyze all flags starting with sym. and entry0 (aa)
INFO: Analyze all functions arguments/locals (afva@@@F)
INFO: Analyze function calls (aac)
INFO: Analyze len bytes of instructions for references (aar)
INFO: Finding and parsing C++ vtables (avrr)
INFO: Type matching analysis for all functions (aaft)
INFO: Propagate noreturn information (aanr)
INFO: Use -AA or aaaa to perform additional experimental analysis
 -- Bindiff two files with '$ radiff2 /bin/true /bin/false'
[0x004005a0]> iI
arch     x86
baddr    0x400000
binsz    6766
bintype  elf
bits     64
canary   false
class    ELF64
compiler GCC: (Ubuntu 7.5.0-3ubuntu1~18.04) 7.5.0
crypto   false
endian   little
havecode true
intrp    /lib64/ld-linux-x86-64.so.2
laddr    0x0
lang     c
linenum  true
lsyms    true
machine  AMD x86-64 architecture
nx       true
os       linux
pic      false
relocs   true
relro    partial
rpath    NONE
sanitize false
static   false
stripped false
subsys   linux
va       true
```

So it is a 64-bit ELF binary, which is dynamically linked, not stripped, without stack canaries, nx enabled, no pic and partial relro

```
[0x004005a0]> afl
0x004005a0    1     42 entry0
0x004005e0    4     37 sym.deregister_tm_clones
0x00400610    4     55 sym.register_tm_clones
0x00400650    3     29 sym.__do_global_dtors_aux
0x00400680    1      7 sym.frame_dummy
0x00400810    1      2 sym.__libc_csu_fini
0x00400711    1     57 sym.vuln
0x00400550    1      6 sym.imp.puts
0x00400570    1      6 sym.imp.printf
0x00400590    1      6 sym.imp.gets
0x00400814    1      9 sym._fini
0x00400687    1    138 sym.activate_seccomp
0x00400580    1      6 sym.imp.prctl
0x004007a0    4    101 sym.__libc_csu_init
0x004005d0    1      2 sym._dl_relocate_static_pie
0x0040074a    3     81 main
0x00400560    1      6 sym.imp.setbuf
0x00400520    3     23 sym._init

```

and a `vuln` function

```
0x004005a0]> pdf @ sym.vuln
            ; CALL XREF from main @ 0x400793(x)
┌ 57: sym.vuln ();
│           ; var char *s @ rbp-0x20
│           0x00400711      55             push rbp
│           0x00400712      4889e5         mov rbp, rsp
│           0x00400715      4883ec20       sub rsp, 0x20
│           0x00400719      488d3d280200.  lea rdi, str.Are_you_a_master_of_ROP_ ; 0x400948 ; "Are you a master of ROP?" ; const char *s
│           0x00400720      e82bfeffff     call sym.imp.puts           ; int puts(const char *s)
│           0x00400725      488d3d350200.  lea rdi, str.Show_me_what_you_can_do:_ ; 0x400961 ; "Show me what you can do: " ; const char *format
│           0x0040072c      b800000000     mov eax, 0
│           0x00400731      e83afeffff     call sym.imp.printf         ; int printf(const char *format)
│           0x00400736      488d45e0       lea rax, [s]
│           0x0040073a      4889c7         mov rdi, rax                ; char *s
│           0x0040073d      b800000000     mov eax, 0
│           0x00400742      e849feffff     call sym.imp.gets           ; char *gets(char *s)
│           0x00400747      90             nop
│           0x00400748      c9             leave
└           0x00400749      c3             ret
```

here the unsafe `gets` function is used, where we simply get to provide our input and the function return

We examine it with [gdb-peda](https://github.com/longld/peda)

```bash
$ gdb ./main
GNU gdb (Ubuntu 12.1-0ubuntu1~22.04) 12.1
Copyright (C) 2022 Free Software Foundation, Inc.
License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>
This is free software: you are free to change and redistribute it.
There is NO WARRANTY, to the extent permitted by law.
Type "show copying" and "show warranty" for details.
This GDB was configured as "x86_64-linux-gnu".
Type "show configuration" for configuration details.
For bug reporting instructions, please see:
<https://www.gnu.org/software/gdb/bugs/>.
Find the GDB manual and other documentation resources online at:
    <http://www.gnu.org/software/gdb/documentation/>.

For help, type "help".
Type "apropos word" to search for commands related to "word"...
Reading symbols from ./main...
(No debugging symbols found in ./main)
```

create pattern

```bash
gdb-peda$ pattern_create 200 ./pattern
Writing pattern of 200 chars to filename "./pattern"
```

run with pattern

```bash
gdb-peda$ r < ./pattern                                                                                                                                                            [35/1770]
Starting program: /home/saskia/code/github/shiltemann/CTF-writeups-galaxians/website/writeups/HackyEaster_2023/writeupfiles/thumper2/main < ./pattern
[Thread debugging using libthread_db enabled]
Using host libthread_db library "/lib/x86_64-linux-gnu/libthread_db.so.1".
Are you a master of ROP?
Show me what you can do:
Program received signal SIGSEGV, Segmentation fault.
Warning: 'set logging off', an alias for the command 'set logging enabled', is deprecated.
Use 'set logging enabled off'.

Warning: 'set logging on', an alias for the command 'set logging enabled', is deprecated.
Use 'set logging enabled on'.
[----------------------------------registers-----------------------------------]
RAX: 0x7fffffffdd20 ("AAA%AAsAABAA$AAnAACAA-AA(AADAA;AA)AAEAAaAA0AAFAAbAA1AAGAAcAA2AAHAAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAAr
AAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
RBX: 0x0
RCX: 0x7ffff7e19aa0 --> 0xfbad209b
RDX: 0x1
RSI: 0x1
RDI: 0x7ffff7e1ba80 --> 0x0
RBP: 0x6141414541412941 ('A)AAEAAa')
RSP: 0x7fffffffdd48 ("AA0AAFAAbAA1AAGAAcAA2AAHAAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
RIP: 0x400749 (<vuln+56>:       ret)
R8 : 0x0
R9 : 0x0
R10: 0x7ffff7c09c78 --> 0xf0022000043b3
R11: 0x246
R12: 0x7fffffffde68 --> 0x7fffffffe261 ("/home/saskia/code/github/shiltemann/CTF-writeups-galaxians/website/writeups/HackyEaster_2023/writeupfiles/thumper2/main")
R13: 0x40074a (<main>:  push   rbp)
R14: 0x0
R15: 0x7ffff7ffd040 --> 0x7ffff7ffe2e0 --> 0x0
EFLAGS: 0x10202 (carry parity adjust zero sign trap INTERRUPT direction overflow)
[-------------------------------------code-------------------------------------]
   0x400742 <vuln+49>:  call   0x400590 <gets@plt>
   0x400747 <vuln+54>:  nop
   0x400748 <vuln+55>:  leave
=> 0x400749 <vuln+56>:  ret
   0x40074a <main>:     push   rbp
   0x40074b <main+1>:   mov    rbp,rsp
   0x40074e <main+4>:   mov    rax,QWORD PTR [rip+0x2008fb]        # 0x601050 <stdout@@GLIBC_2.2.5>
   0x400755 <main+11>:  mov    esi,0x0
[------------------------------------stack-------------------------------------]
0000| 0x7fffffffdd48 ("AA0AAFAAbAA1AAGAAcAA2AAHAAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0008| 0x7fffffffdd50 ("bAA1AAGAAcAA2AAHAAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0016| 0x7fffffffdd58 ("AcAA2AAHAAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0024| 0x7fffffffdd60 ("AAdAA3AAIAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0032| 0x7fffffffdd68 ("IAAeAA4AAJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0040| 0x7fffffffdd70 ("AJAAfAA5AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0048| 0x7fffffffdd78 ("AAKAAgAA6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
0056| 0x7fffffffdd80 ("6AALAAhAA7AAMAAiAA8AANAAjAA9AAOAAkAAPAAlAAQAAmAARAAoAASAApAATAAqAAUAArAAVAAtAAWAAuAAXAAvAAYAAwAAZAAxAAyA")
[------------------------------------------------------------------------------]
Legend: code, data, rodata, value
Stopped reason: SIGSEGV
0x0000000000400749 in vuln ()
```

we take the top value off the stack to determine the pattern offset:

```
gdb-peda$ pattern_offset AA0AAFAAbAA1AAGAAcAA2AAHAAdAA3AAIAAe
AA0AAFAAbAA1AAGAAcAA2AAHAAdAA3AAIAAe found at offset: 40

```

We don't get a `system()` call in the code directly, but since printf is used, the entire libc library is loaded, we just need to find the right address?

```
gdb-peda$ print system
$1 = {int (const char *)} 0x7ffff7c50d60 <__libc_system>

```
