import binascii


r = '0010010000111100011111100000101000001110101000101100111001001110101011100110001001001110101111001000011010000100100011100'
g = '0001100111011111010111000001111111011000110101111001010111010100100010001101011110010100110110101001100111011011100101110'
b = '0001001110001000110010101101001111010010001110111011000111010001001100101100101110010010001010100101001100111010000100100'

def rotate(s, offset):
    if offset == 0:
        q = '0b' + s
    else:
        q = '0b' + ('0' * offset) + s[0:-offset]
    q = "{0:088x}".format(int(q, 2))
    return q

def interleave(a, b, c):
    for i in range(len(a)):
        yield a[i]
        yield b[i]
        yield c[i]

# Convert a1a2a3, b1b2b3, c1c2c3 into a1b1c1a2b2c2a3b3c3
d = ''.join(interleave(b, g, r))

# Find a rotation (given the hint)
q = rotate(d, 0)[0:88]

# Convert to string and print out
w = binascii.unhexlify(q).replace('\n', '').replace('\r', '')
print(w)
