import binascii
import gmpy2

def int_to_text(i):
    hex_string = '%x' % i
    n = len(hex_string)
    return binascii.unhexlify(hex_string.zfill(n + (n & 1)))


c=0x7E65D68F84862CEA3FCC15B966767CCAED530B87FC4061517A1497A03D2
p=0xDD8E05FF296C792D2855DB6B5331AF9D112876B41D43F73CEF3AC7425F9
b=0x7BBE3A50F28B2BA511A860A0A32AD71D4B5B93A8AE295E83350E68B57E5

a = gmpy2.divm(c,b,p)

found = False
while not found:
    pt = int_to_text(a)
    if pt.startswith('HV18'):
        print(pt)
        found = True
    a += p
