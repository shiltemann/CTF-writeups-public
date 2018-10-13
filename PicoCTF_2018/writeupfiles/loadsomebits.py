import binascii


r = '0010010000111100011111100000101000001110101000101100111001001110101011100110001001001110101111001000011010000100100011100'
g = '0001100111011111010111000001111111011000110101111001010111010100100010001101011110010100110110101001100111011011100101110'
b = '0001001110001000110010101101001111010010001110111011000111010001001100101100101110010010001010100101001100111010000100100'

def rotate(s, offset):
    q = '0b' + ('0' * i) + s[0:-i]
    q = "{0:088x}".format(int(q, 2))
    return q

# for j in (r, g, b):
    # for i in range(1, 18):
        # q = rotate(j, i)
        # print("{0:02d} {1} {2}".format(i, q, binascii.unhexlify(q).replace('\n', '').replace('\r', '')))

import itertools

def interleave(a, b, c):
    for i in range(len(a)):
        yield a[i]
        yield b[i]
        yield c[i]

for (a_, b_, c_) in itertools.permutations(list('rgb')):
    a = locals()[a_]
    b = locals()[b_]
    c = locals()[c_]
    d = ''.join(interleave(a, b, c))[0:360]

    for i in range(1, 18):
        q = rotate(d, i)[0:88]
        w = binascii.unhexlify(q).replace('\n', '').replace('\r', '')
        print("{0:02d} {1} {2}".format(i, q, w))
