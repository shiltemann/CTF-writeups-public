---
layout: writeup
title: 'Complacent'
level: 2
difficulty:
points:
categories: [recon]
tags: []
flag: IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
---
## Challenge

These silly bankers have gotten pretty complacent with their self signed
SSL certificate. I wonder if there's anything in there.
complacent.vuln.icec.tf

## Solution

The flag was in the SSL certificate

    Issued To
    Common Name (CN)	complacent.icec.tf
    Organization (O)	Secret IceCTF Buisness Corp
    Organizational Unit (OU)	Flag: IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
    Serial Number	00:DF:8D:FC:51:A7:A2:00:7F
    
    Issued By
    Common Name (CN)	complacent.icec.tf
    Organization (O)	Secret IceCTF Buisness Corp
    Organizational Unit (OU)	Flag: IceCTF{this_1nformation_wasnt_h1dd3n_at_a11}
    Validity Period
    
    Issued On	Tuesday, August 2, 2016 at 9:59:11 PM
    Expires On	Thursday, July 9, 2116 at 9:59:11 PM
    Fingerprints
    
    SHA-256 Fingerprint	76 00 68 BF 5C 8F 04 90 D6 4E 1E 21 82 14 50 BC
    49 95 B1 FB F0 AC 95 5A 75 BD F8 6B 5C 93 20 7B
    SHA-1 Fingerprint	75 29 19 51 91 06 9E D5 D2 68 8C 94 EA 01 78 AB
    8C D1 76 33
