import binascii
import sys


def removenonhex(d):
    q = []
    for x in list(d.lower()):
        if 48 <= ord(x) <= 57 or 97 <= ord(x) <= 102:
            q.append(x)
    return ''.join(q)


for line in sys.stdin.read().split('\n'):
    if line.startswith('0x'):
        try:
            q = binascii.unhexlify(removenonhex(line[2:]))
        except:
            q = ''

        print('%s %s' % (line, q))
    else:
        print('%s' % line)
