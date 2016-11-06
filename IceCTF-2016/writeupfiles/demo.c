#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <libgen.h>
#include <string.h>

void give_shell() {
    gid_t gid = getegid();
    setresgid(gid, gid, gid);
    system("/bin/sh");
}

int main(int argc, char *argv[]) {
    if(strncmp(basename(getenv("_")), "icesh", 6) == 0){
        give_shell();
    }
    else {
        printf("I'm sorry, your free trial has ended.\n");
    }
    return 0;
}
