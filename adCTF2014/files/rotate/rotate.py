import sys
import math
import struct

p = lambda x: struct.pack('f', x)
u = lambda x: struct.unpack('b', x)[0]

if len(sys.argv) != 3:
    sys.exit(1)

filename = sys.argv[1]
key = math.radians(int(sys.argv[2]))

bs = open(filename, 'rb').read()
enc = open(filename + '.enc', 'wb')

for i in range(0, len(bs), 2):
    x, y = u(bs[i]), u(bs[i+1])
    enc.write(p(x * math.cos(key) - y * math.sin(key)) + p(x * math.sin(key) + y * math.cos(key)))
