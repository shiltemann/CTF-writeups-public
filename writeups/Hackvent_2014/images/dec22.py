def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('modular inverse does not exist')
    else:
        return x % m
        
# mdular pow
def modexp_rl(a, b, n):
    r = 1
    while 1:
        if b % 2 == 1:
            r = r * a % n
        b /= 2
        if b == 0:
            break
        a = a * a % n
    return r
    

# ElGamal parameters   
p=0xCFF3829FE2BC008D  
g=0x2367CA6FE33CF1A9  
y=0x42F357F7636AA02F  
a=0x7D3BDC843CE75CD3  
b=0x275E625204563FAC  

# secret key
x=3888521305394705767    
    
# decrypt message,p using x
pt=(b* modinv( modexp_rl(a,x,p),p))%p

# print message
print "decrypted message: "+str(pt)
pt=hex(pt).rstrip("L").lstrip("0x")
print "hex value:         "+ pt
print "ascii :            "+''.join(chr(int(pt[i:i+2], 16)) for i in range(0, len(pt), 2))
 



