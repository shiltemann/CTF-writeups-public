import base64

orig = "El Psy Congroo"
c1 = "IFhiPhZNYi0KWiUcCls="
c2 = "I3gDKVh1Lh4EVyMDBFo="

key = ''.join(chr(ord(a) ^ ord(b)) for a,b in zip(orig,base64.b64decode(c1)))
flag = ''.join(chr(ord(a) ^ ord(b)) for a,b in zip(key,base64.b64decode(c2)))

print(flag)
