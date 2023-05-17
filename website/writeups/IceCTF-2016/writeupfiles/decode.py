# Python bytecode 3.5 (decompiled from Python 2.7)
# Embedded file name: encode.py
# Compiled at: 2016-08-07 22:10:09
import base64
P = [27, 35, 50, 11, 8, 20, 44, 30, 6, 1, 5, 2, 33, 16, 36, 64, 3, 61, 54, 25,
     12, 21, 26, 10, 57, 53, 38, 56, 58, 37, 43, 17, 42, 47, 4, 14, 7, 46, 34,
     19, 23, 40, 63, 18, 45, 60, 13, 15, 22, 9, 62, 51, 32, 55, 29, 24, 41, 39,
     49, 52, 48, 28, 31, 59]
S = [68, 172, 225, 210, 148, 172, 72, 38, 208, 227, 0, 240, 193, 67, 122, 108,
     252, 57, 174, 197, 83, 236, 16, 226, 133, 94, 104, 228, 135, 251, 150, 52,
     85, 56, 174, 105, 215, 251, 111, 77, 44, 116, 128, 196, 43, 210, 214, 203,
     109, 65, 157, 222, 93, 74, 209, 50, 11, 172, 247, 111, 80, 143, 70, 89]

# "Some secret message"
# ans = base64.b64decode('SBBdv3RC+LRe9zdn+I3LqF7HXR8PmktZ3huXkeKJVWsudBvt/XjSjx5ks60tCjtlDk7ic3oTTgOyq2fpoKqT3w==')
# "Some secret message" * like 6
import sys
# inp = sys.stdin.read()
# The real secret
inp = 'Wmkvw680HDzDqMK6UBXChDXCtC7CosKmw7R9w7JLwr/CoT44UcKNwp7DllpPwo3DtsOID8OPTcOWwrzDpi3CtMOKw4PColrCpXUYRhXChMK9w6PDhxfDicOdwoAgwpgNw5/Cvw=='
ans = base64.b64decode(inp.encode('utf8')).decode('utf8')

# Must be padded to 64
a, b = divmod(len(ans), 64)
if b != 0:
    ans += (64 - b) * "a"

out = {}
# Encodes in blocks
for j in range(0, len(ans), 64):
    for i in range(64):
        # Index in answer
        idx = j + P[i] - 1
        # Get that character
        ans_c = ord(ans[idx])
        # Now that = (ord(inp[j + i]) + S[i]) % 256, so we add S[i] and 256 repeatedly until > 0
        tmp = ans_c - S[i]
        while tmp < 0:
            tmp += 256
        # This should leave us with ord(inp[j + i]), so we take chr() of
        # that and get the value at index inp[j+i]
        out[j+i] = chr(tmp)

print(''.join([out[x] for x in range(len(ans))]))
