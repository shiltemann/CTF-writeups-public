---
layout: writeup

title: C2 Traffic
level: 6         # optional, for events that use levels (like HackyEaster)
difficulty: medium     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [crypto]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{wh4dy4_m3an_32_b1t5_1s_1n53cur3}

---

## Challenge

We have detected C2 payloads on one of our servers! The blue team have extracted its communications from the traffic logs, and Operations have dumped the payload code from the running process.

Find out what the actors have exfiltrated!


## Solution

There's a json file, and some encrypted contents inside. The companion python file shows us how the encryption was done, but there's a missing `B`, which is randomly chosen. The 'problem' to solve is the value of B, an integer in [1, 2272978429], and the only operation we need to calculate is `pow` so this probably means we can just trivially bruteforce it, the simplicity of the operation and small search space, it should be fast (see [writeupfiles/c2/payload-mp.py](writeupfiles/c2/payload-mp.py))

20 minutes later we've recovered the value

```
620620105
 60%|█████████████████████████████████████████████████████████████████████▌                                              | 1363765343/2272978428 [20:12<05:09, 2937691.27it/s]
```

And plugging that in we can reverse it and can decrypt the b64:

```python
# import c2utils
import json
from hashlib import sha256
from Crypto.Cipher import AES
import base64
from Crypto.Util.number import bytes_to_long, long_to_bytes
from Crypto.Random.random import randint
from Crypto.Util.Padding import pad, unpad
# import subprocess

def long_to_base64(n):
    return base64.standard_b64encode(long_to_bytes(n)).decode()

def encrypt(cipher, msg):
    return base64.standard_b64encode(cipher.encrypt(pad(msg, 16))).decode()

def base64_to_long(e):
    return bytes_to_long(base64.standard_b64decode(e))

def decrypt(cipher, e):
    return unpad(cipher.decrypt(base64.standard_b64decode(e)), 16)

cipher = None

with open('logs.json', 'r') as handle:
    j = json.load(handle)['incoming'][0]


p = base64_to_long(j['p'])
g = base64_to_long(j['g'])
A = base64_to_long(j['A'])

# 2 620620105 2272978429

print(g, '/', p)

b = 620620105
shared = pow(A, b, p)
shared = sha256(long_to_bytes(shared)).digest()
cipher = AES.new(shared, AES.MODE_ECB)

B = long_to_base64(pow(g, b, p))


print(decrypt( cipher, '6+lX9noxcSrRAnTbQMYdPg=='))
print(decrypt( cipher, '15AsYtxN//27mQ/lDUAJOjApyeXQx65dFso1oP7w8Qw='))
print(decrypt( cipher, 'GkSU2VwQyFe5Jt0Vd0cfxw=='))
print(decrypt( cipher, '3eWXhpQagWGMlfc71Qxd2QMvy4EVIyLfP54Jm6lpyHot6Qz+U7t3q2DdKnOxZBQf'))
```


```
b'ls'
b'cat sensitive.txt'
b'sensitive.txt\n'
b'he2022{wh4dy4_m3an_32_b1t5_1s_1n53cur3}\n'
```

