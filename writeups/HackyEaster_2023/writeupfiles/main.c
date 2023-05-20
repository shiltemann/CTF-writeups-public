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

