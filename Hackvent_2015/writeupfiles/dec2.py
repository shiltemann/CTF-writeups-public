import binascii

klingon = "pagh wa'vatlh netlh wa'maH wa'maH wa' SaD wa' SaD wa'vatlh wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'maH wa'maH netlh pagh wa'maH wa' wa'vatlh wa' wa'vatlh SaD SaD wa' wa'vatlh netlh wa'maH wa' wa'maH wa'maH wa'vatlh wa' wa'maH wa'maH wa' wa' wa'vatlh SaD wa' wa'maH wa'maH wa'maH wa'vatlh wa'maH SaD wa'maH wa' wa'maH wa'maH wa' wa' wa' wa'maH wa'vatlh wa' wa'vatlh wa' SaD wa' SaD wa'maH wa' wa' wa'maH wa' SaD  wa'maH wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'vatlh wa' wa'maH wa' wa' wa'maH wa' netlh wa'maH wa'vatlh wa' wa' wa' wa'vatlh wa'maH wa' wa'maH wa'maH SaD wa' wa'vatlh wa'maH SaD wa'vatlh wa' wa'maH SaD wa' wa'maH SaD"
klingon2binary = {'netlh':'10000', 'SaD':'1000', "wa'vatlh":'100', "wa'maH":'10', "wa' ":'1', 'pagh':'0' }

binary=klingon
for key in klingon2binary:
    binary=binary.replace(key,klingon2binary[key])    
 
binary = binary.replace(' ','')      
n = int(binary, 2)
nugget = binascii.unhexlify('%x' % n)

print binary 
print nugget



klingon = "pagh wa'vatlh netlh wa'maH wa'maH wa' SaD wa' SaD wa'vatlh wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'maH wa'maH netlh pagh wa'maH wa' wa'vatlh wa' wa'vatlh SaD SaD wa' wa'vatlh netlh wa'maH wa' wa'maH wa'maH wa'vatlh wa' wa'maH wa'maH wa' wa' wa'vatlh SaD wa' wa'maH wa'maH wa'maH wa'vatlh wa'maH SaD wa'maH wa' wa'maH wa'maH wa' wa' wa' wa'maH wa'vatlh wa' wa'vatlh wa' SaD wa' SaD wa'maH wa' wa' wa'maH wa' SaD  wa'maH wa' wa'maH wa'maH wa'vatlh wa'maH wa' wa'vatlh wa' wa'maH wa' wa' wa'maH wa' netlh wa'maH wa'vatlh wa' wa' wa' wa'vatlh wa'maH wa' wa'maH wa'maH SaD wa' wa'vatlh wa'maH SaD wa'vatlh wa' wa'maH SaD wa' wa'maH SaD"
klingon2english = {'netlh':'tenthousand', 'SaD':'thousand', "wa'vatlh":'onehundred', "wa'maH":'ten', "wa' ":'one ', 'pagh':'zero' }
english=klingon

for key in klingon2english:
    english=english.replace(key,klingon2english[key])    

print english