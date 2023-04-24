import itertools
import hashlib

def all_casings(input_string):
    if not input_string:
        yield ""
    else:
        first = input_string[:1]
        if first.lower() == first.upper():
            for sub_casing in all_casings(input_string[1:]):
                yield first + sub_casing
        else:
            for sub_casing in all_casings(input_string[1:]):
                yield first.lower() + sub_casing
                yield first.upper() + sub_casing
                
target_lc='B39ECFBC2C64ADBB7C7A9292EEE31794D28FE224'.lower()
target_cs='0D353038908AD0FC8C51A5312BB3E2FEE1CDDF83'.lower()

charset='abcdefghijklmnopqrstuvwxyz0123456789'


for i in itertools.product(charset, repeat=3):
    nugget = 'hv15-g'+i[0]+'uj-1yq7-'+i[1]+'dyc-2wlr-e6'+i[2]+'j'  
    h = hashlib.sha1()
    h.update(nugget)   
    h_hex = h.hexdigest()
    if h_hex == target_lc:
        print 'Found lowercase match!'
        print ' --> nugget: '+ nugget +' (sha1 '+h.hexdigest()+')'
        break
        
print 'Searching for the correct case..'
for i in all_casings(nugget):
    h = hashlib.sha1()
    h.update(i)   
    h_hex = h.hexdigest()
    if h_hex == target_cs:
        print 'Found case-sensitive match!'
        print ' --> nugget: '+ i + ' (sha1 '+h.hexdigest()+')'
        break
