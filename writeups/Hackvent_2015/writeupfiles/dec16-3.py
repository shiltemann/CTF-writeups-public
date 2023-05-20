from ctypes import *

def encipher(v, k):
    y=c_uint32(v[0]);
    z=c_uint32(v[1]);
    sum=c_uint32(0);
    delta=0x9E3779B9;
    n=32
    w=[0,0]

    while(n>0):
        sum.value += delta
        y.value += ( z.value << 4 ) + k[0] ^ z.value + sum.value ^ ( z.value >> 5 ) + k[1]
        z.value += ( y.value << 4 ) + k[2] ^ y.value + sum.value ^ ( y.value >> 5 ) + k[3]
        n -= 1

    w[0]=y.value
    w[1]=z.value
    return w

def decipher(v, k):
    y=c_uint32(v[0])
    z=c_uint32(v[1])
    sum=c_uint32(0xC6EF3720)
    delta=0x9E3779B9
    n=32
    w=[0,0]

    while(n>0):
        z.value -= ( y.value << 4 ) + k[2] ^ y.value + sum.value ^ ( y.value >> 5 ) + k[3]
        y.value -= ( z.value << 4 ) + k[0] ^ z.value + sum.value ^ ( z.value >> 5 ) + k[1]
        sum.value -= delta
        n -= 1

    w[0]=y.value
    w[1]=z.value
    return w
    



# set ciphertext to decode
ct=''
with open('key.txt') as f:
    encryptedkey=f.read()
    for c in encryptedkey:
        ct += hex(ord(c))[2:]
    print ct

#split ciphertext into 32 bit blocks
ciphertext = [int(ct[:6],16),int(ct[6:14],16), int(ct[14:22],16), int(ct[22:30],16), int(ct[30:38],16), int(ct[38:46],16), int(ct[46:54],16), int(ct[54:62],16)] 

#print key
#v =[1385482522, 639876499]
#decipher(encipher(v,key),key)
#print v
    
    
# try with number of different keys initialized with PID
for pid in range(1, 999999):
    # set key
    key1 = pid  * int('12345678',16)
    key2 = key1 ^ int('0babef00d',16)
    key3 = key2 - int('1337c0de',16)
    key4 = key3 + int('42424242',16)
    key=[key1,key2,key3,key4]

    # decrypt
    ct=[ciphertext[0],ciphertext[1]]
    pt = decipher(ct,key)
    pt_hex = map(hex,pt)
    for b in pt_hex:
        msg= b[2:-1].zfill(8).decode('hex')
        if 'H' in msg and 'V' in msg and '1' in msg:
            print msg
            print pid
            print pt_hex
            
