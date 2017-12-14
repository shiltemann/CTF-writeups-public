from Crypto.PublicKey import RSA, DSA
import gmpy2
import codecs
import struct

def int_to_text(number):
    return codecs.decode(format(number, 'x'), 'hex').decode('utf-8')

def int2Text(number, size):
    text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
    return text.lstrip("\x00")

def text_to_int(text, byteorder='big', signed=True):
    return int.from_bytes( text.encode('utf-8'), byteorder=byteorder, signed=signed )

def swap32(i):
    #return struct.unpack("<I", struct.pack(">I", i))[0]
    return int.from_bytes(i.to_bytes(4, byteorder='little'), byteorder='big', signed=False)

import binascii
import struct
import array
x = binascii.unhexlify('b62e000052e366667a66408d')
print(x)
y = array.array('h', x)
y.byteswap()
print(y)
s = struct.Struct('<Id')
print(s.unpack_from(y))

orig = 'b62e000052e366667a66408d'
swap =''.join(sum([(c,d,a,b) for a,b,c,d in zip(*[iter(orig)]*4)], ()))
print(swap)
# (46638, 943.2999999994321)

N = 0xF66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51
swap =''.join(sum([(c,d,a,b) for a,b,c,d in zip(*[iter(N)]*4)], ()))
print(swap)

def swap_order(d, wsz=4, gsz=2 ):
    return "".join(["".join([m[i:i+gsz] for i in range(wsz-gsz,-gsz,-gsz)]) for m in [d[i:i+wsz] for i in range(0,len(d),wsz)]])

N = swap32(N)
print(N)
"""
N = 0x
F6 6E B8 87 87b86ef6
F2 B8 A6 20 20a6b8f2
FD 03 C7 D0 d0c703fd
63 37 91 CB cb913763
48 04 73 9C 9c730448
E7 FE 00 1C 1c00fee7
81 E6 E0 27 27e0e681
83 73 7C A2 a27c7383
1D B2 A0 D8 d8a0b21d
AF 2D 10 B2 b2102daf
00 00 6D 10 106d0000
73 7A 08 72 72087a73
C6 67 AD 14 14ad67c6
2F 90 40 71 7140902f
32 EF AB F8 f8abef32
E5 D6 BD 51 51bdd6e5

7A 9F DC A5 a5dc9f7a
BB 06 1D 0D 0d1d06bb
63 8B E1 44 44e18b63
25 86 F3 48 48f38625
8B 53 63 99 9963538b
BA 05 A1 4F 4fa105ba
CA E3 F0 A2 a2f0e3ca
E5 F2 68 F2 f268f2e5
F3 14 2D 19 192d14f3
56 76 94 97 97947656
AE 67 7A 12 127a67ae
E4 D4 4E C7 c74ed4e4
27 E2 55 B3 b355e227
91 00 5B 9A 9a5b0091
DC F5 3B 4A 4a3bf5dc
74 FF C3 4C 4cc3ff74

a5dc9f7a0d1d06bb44e18b6348f386259963538b4fa105baa2f0e3caf268f2e5192d14f397947656127a67aec74ed4e4b355e2279a5b00914a3bf5dc4cc3ff74

87b86ef620a6b8f2d0c703fdcb9137639c7304481c00fee727e0e681a27c7383d8a0b21db2102daf106d000072087a7314ad67c67140902ff8abef3251bdd6e5
"""



C = 0x7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C
C= swap32(C)

p = 18132985757038135691
q = 711781150511215724435363874088486910075853913118425049972912826148221297483065007967192431613422409694054064755658564243
721555532535827
r=(p-1)*(q-1)
e = 65537
#e=95146L
d = long(gmpy2.divm(1, e, r))

rsa = RSA.construct((N,e,d,p,q))
pt = rsa.decrypt(C)

m = pow(C,d,N)

#print( text_to_int("7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C"))
#print(text_to_int("65537"))
#print pt  # returns 6872557977505747778161182217242712228364873860070580111494526546045
#print int_to_text(pt) #returns ABCTF{th1s_was_h4rd_in_1980}
print(int2Text(pt,1000)) #returns ABCTF{th1s_was_h4rd_in_1980}
print(int2Text(m,1000)) #returns ABCTF{th1s_was_h4rd_in_1980}
