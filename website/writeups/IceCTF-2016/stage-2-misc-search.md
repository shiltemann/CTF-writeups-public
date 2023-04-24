---
layout: writeup
title: 'Search'
level: 2
difficulty:
points:
categories: [misc]
tags: []
flag: IceCTF{flag5_all_0v3r_the_Plac3}
---
## Challenge

There's something about this domain... search.icec.tf, I don't see
anything, but maybe its all about the conTEXT.

## Solution

Opening the link in the browser shows an error for DNS name not
resolved.  
Interesting... and the comment says `conTEXT` which suggests a DNS TXT
record.

    $ dig search.icec.tf TXT
    
    ; <<>> DiG 9.9.5-11ubuntu1.3-Ubuntu <<>> search.icec.tf TXT
    ;; global options: +cmd
    ;; Got answer:
    ;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 43529
    ;; flags: qr rd ra; QUERY: 1, ANSWER: 1, AUTHORITY: 0, ADDITIONAL: 1
    
    ;; OPT PSEUDOSECTION:
    ; EDNS: version: 0, flags:; udp: 512
    ;; QUESTION SECTION:
    ;search.icec.tf.                        IN      TXT
    
    ;; ANSWER SECTION:
    search.icec.tf.         299     IN      TXT     "IceCTF{flag5_all_0v3r_the_Plac3}"
    
    ;; Query time: 32 msec
    ;; SERVER: 8.8.8.8#53(8.8.8.8)
    ;; WHEN: Fri Aug 12 19:42:20 UTC 2016
    ;; MSG SIZE  rcvd: 88
