# Python bytecode 3.5 (decompiled from Python 2.7)
# Embedded file name: encode.py
# Compiled at: 2016-08-07 22:10:09
import random
import base64
P = [27, 35, 50, 11, 8, 20, 44, 30, 6, 1, 5, 2, 33, 16, 36, 64, 3, 61, 54, 25,
     12, 21, 26, 10, 57, 53, 38, 56, 58, 37, 43, 17, 42, 47, 4, 14, 7, 46, 34,
     19, 23, 40, 63, 18, 45, 60, 13, 15, 22, 9, 62, 51, 32, 55, 29, 24, 41, 39,
     49, 52, 48, 28, 31, 59]
S = [68, 172, 225, 210, 148, 172, 72, 38, 208, 227, 0, 240, 193, 67, 122, 108,
     252, 57, 174, 197, 83, 236, 16, 226, 133, 94, 104, 228, 135, 251, 150, 52,
     85, 56, 174, 105, 215, 251, 111, 77, 44, 116, 128, 196, 43, 210, 214, 203,
     109, 65, 157, 222, 93, 74, 209, 50, 11, 172, 247, 111, 80, 143, 70, 89]
import sys
inp = sys.stdin.read()
inp += ''.join((chr(random.randint(0, 47)) for _ in range(64 - len(inp) % 64)))
ans = ['' for i in range(len(inp))]
for j in range(0, len(inp), 64):
    for i in range(64):
        ans[j + P[i] - 1] = chr((ord(inp[j + i]) + S[i]) % 256)

ans = ''.join(ans)
print(base64.b64encode(ans.encode('utf8')).decode('utf8'))
# okay decompiling encode.pyc
