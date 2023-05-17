import binascii,md5
from Crypto.Cipher import XOR

target = "617B7E0A0870637F710E42B44A3B0647433442441B4E4F1D4B471F29475C5D62"
targetdec = target.decode("hex")

# generate set of possible keys
keys = []
for i in range(10,20):
	for j in range(10,20):
		m = md5.new(str(i*j))
		digest = m.hexdigest()
		keys.append(digest.upper())


# try decrypting with each of the keys
for k in keys:
        crypt = XOR.new(k)
        kxor = crypt.encrypt(targetdec)
        
        result = ''
        l=len(kxor)
        for i in range(0, l):                
                value = kxor[l-i-1:l-i]
                value = ord(value)-i-1
                try :
                        result += chr(value)
                except:
                        break
        print result


