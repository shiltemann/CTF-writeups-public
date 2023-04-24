

with open('HVenc/key-original.txt') as f:
    encryptedkey=f.read()
    for c in encryptedkey:
        print hex(ord(c))
        
