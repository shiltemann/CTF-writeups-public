---
layout: writeup
title: 'Stage 3 Pwn: Quine'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

This service is really strange... It seems to accept only C programs.
Can you get us the flag? nc quine.vuln.icec.tf 5500

## Solution

Found [a quine](writeupfiles/quine.c) on the [internet][1]:

    main(){char *c="main(){char *c=%c%s%c;printf(c,34,c,34);}";printf(c,34,c,34);}
    .
^

    $ cat tmp.c | nc quine.vuln.icec.tf 5500
    Please provide source file (up to 2048 bytes), ending with a dot by itself on a line
    [INFO]  server.c:161  (2439) Fri 12 Aug 2016 17:10:32 File upload complete!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:32 Survived iteration #1!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:32 Survived iteration #2!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #3!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #4!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #5!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #6!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #7!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #8!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #9!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #10!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #11!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #12!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #13!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #14!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #15!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #16!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #17!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #18!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #19!
    [INFO]  server.c:436  (2439) Fri 12 Aug 2016 17:10:33 Survived iteration #20!
    [INFO]  server.c:438  (2439) Fri 12 Aug 2016 17:10:33 Echoing surviving source back
    main(){char *c="main(){char *c=%c%s%c;printf(c,34,c,34);}";printf(c,34,c,34);}
{: .language-console}

## Flag





[1]: https://www.nyx.net/~gthompso/self_c.txt
