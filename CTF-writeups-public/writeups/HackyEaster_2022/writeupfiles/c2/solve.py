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

# # cmd = decrypt(cipher, j['rpc'])
# # return {
    # # 'return': encrypt(cipher, subprocess.check_output(cmd))
# # }

# # c2utils.start_listener(handle)
