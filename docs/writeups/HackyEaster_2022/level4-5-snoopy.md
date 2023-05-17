---
layout: writeup

title: Snoopy
level: 4        # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [crypto]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{ctx1_41nt_3nKryp710n!}

---

## Challenge

Snoopy dog found something interesting.

Can you get something interesting out of the 256 bytes he found?

```
IKIANJKDPKKAPJIDNKKAPNBHELCBHMGGDLOBLIPCKNAHFOEEBNFHALLBOMPGKJADFKDAGMNGIIGCDPEFBINCIPNFIMKGPPLFOMLGOKFAAIECBPJFM</Password>
<Domain type="NT">CORP</Domain>
</Credentials>
<ClientName>THUMPERSDESK7</ClientName>
<ClientType>ica30</ClientType>
<ClientAddress>10.1
```


## Solution

Looks like a CITRIX password, Cyberchef can decode those!

It complains about not being the right length, so we are probably missing some characters at the start of the snippet, so we add `A`s to the beginning until it decrypts

```
궥.2022{ctx1_41nt_3nKryp710n!}
```

luckily we can guess the start of the string ;)


