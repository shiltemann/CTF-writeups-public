#include <stdio.h>
#include <stdlib.h>

void get_flag()
{
    system("/bin/sh");
}

void get_input()
{
    char buff[128];
    gets(buff);
    printf("You said: %s\n", buff);
}

int main(int argc, char* argv[])
{
    gid_t gid = getegid();
    setresgid(gid, gid, gid);
    get_input();
    return 0;
}
