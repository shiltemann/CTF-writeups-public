# encoding=utf-8
import random
import itertools

def caesar_cipher(a, b):
    b = b * (len(a) / len(b) + 1)
    return ('').join((chr(ord(x) ^ ord(y)) for x, y in itertools.izip(a, b)))

with open('./youfool!.exe', 'r') as handle:
    buf = handle.read()

# alnum
password = """:P-@uSL"Y1K$[X)fg[|".45Yq9i>eV)<0C:('q4nP[hGd/EeX+E7,2O"+:[2"""
sureity =  """xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"""

#%PDF-1.4

# print(caesar_cipher(buf[0:10], '%PDF-1.4'))
w = 120 * 80
for i in range(0, len(buf), 60):
    q = caesar_cipher(buf[i:i + 60], password)
    # if True:
    # if 'EOF' in q:
        # print(i)
    if w - 120 <= i <= w + 120:
        print('i', buf[i:i + 60])
        print('p', password)
        print('o', q)
        print('s', sureity)
        print("")

print(float(sureity.count('x')) / 60)

with open('./FlagDCTF.pdf', 'w') as handle:
    handle.write(caesar_cipher(buf, password))
