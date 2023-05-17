import binascii,base64

with open('dec22/Hackvent-cd/part5_uu') as f:
    data=f.read()
    
print data

data2 = binascii.a2b_uu(data)
data3 = base64.b64decode(data2)

print "uu_encoded:  "+data
print "uu_decoded:  "+data2
print "b64 decoded: "+data3