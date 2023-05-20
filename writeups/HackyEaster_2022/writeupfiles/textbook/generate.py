from Crypto.Util.number import getPrime, long_to_bytes
# from secrets import FLAG
FLAG = 'he2022{test}'

assert FLAG.startswith("he2022")

p = getPrime(512)
q = getPrime(512)
print(p)
print(q)
e = 65537
n = p * q

def encrypt(content):
    ct = []
    for c in content:
        ct.append(pow(ord(c), e, n))
    return ct

print(encrypt(FLAG))
