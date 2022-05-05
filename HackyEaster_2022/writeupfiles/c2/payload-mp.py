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


from multiprocessing import Pool

p = base64_to_long(j['p'])
g = base64_to_long(j['g'])
A = base64_to_long(j['A'])

CORRECT_B= base64_to_long('Ph6IeA==')
print(CORRECT_B)

def f(i):
    if pow(g, i, p) == CORRECT_B:
        print(g, i, p)
        import sys
        sys.exit()

import tqdm
with Pool(5) as z:
    print(z.map(f, tqdm.tqdm(range(1, p))))
