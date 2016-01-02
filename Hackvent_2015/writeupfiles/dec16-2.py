import tea2

with open('key.txt') as f:
    ct=f.read() 

for c in ct:
    print hex(ord(c)),

def num_to_bytes(num):
    num = hex(num)[2:].rstrip('L')
    if len(num) % 2:
        return ('0%s' % num).decode('hex')
    return num.decode('hex')

def bytes_to_num(bytes):
    return int(bytes.encode('hex'), 16)
 
for iv in range(1, 9):
    key1 = iv * int('12345678',16)
    key2 = key1 ^ int('babef00d',16)
    key3 = key2 - int('1337f00d',16)
    key4 = key3 + int('42424242',16)
    key=0
    key +=  key1
    key=key<<96
    #print str(hex(key)).zfill(16)
    key+= key2<<64
    #print str(hex(key)).zfill(16)
    key+= key3<<32
    #print str(hex(key)).zfill(16)
    key+= key4
    #print str(hex(key)).zfill(16)
    '''
    print key
    teakey = tea.get_key(str(key))
    print teakey
    pt = tea.decrypt(ct, teakey)
      
    >>> r = encrypt('', b2a_hex('b537a06cf3bcb33206237d7149c27bc3'))
    >>> decrypt(r, b2a_hex('b537a06cf3bcb33206237d7149c27bc3'))
    ''
    >>> r = encrypt('abcdefghijklimabcdefghijklmn', b2a_hex('b537a06cf3bcb33206237d7149c27bc3'))
    >>> decrypt(r, b2a_hex('b537a06cf3bcb33206237d7149c27bc3'))
    'abcdefghijklimabcdefghijklmn'
    >>> import md5
    >>> key = md5.new(md5.new('python').digest()).digest()
    >>> data='8CE160B9F312AEC9AC8D8AEAB41A319EDF51FB4BB5E33820C77C48DFC53E2A48CD1C24B29490329D2285897A32E7B32E9830DC2D0695802EB1D9890A0223D0E36C35B24732CE12D06403975B0BC1280EA32B3EE98EAB858C40670C9E1A376AE6C7DCFADD4D45C1081571D2AF3D0F41B73BDC915C3AE542AF2C8B1364614861FC7272E33D90FA012620C18ABF76BE0B9EC0D24017C0C073C469B4376C7C08AA30'
    >>> data = a2b_hex(data)
    >>> b2a_hex(decrypt(data, key))
    '00553361637347436654695a354d7a51531c69f1f5dde81c4332097f0000011f4042c89732030aa4d290f9f941891ae3670bb9c21053397d05f35425c7bf80000000001f40da558a481f40000100004dc573dd2af3b28b6a13e8fa72ea138cd13aa145b0e62554fe8df4b11662a794000000000000000000000000dde81c4342c8966642c4df9142c3a4a9000a000a'
    '''
    
    hexval= hex(key).rstrip('L')[2:]
    pt = tea2.b2a_hex(tea2.decrypt(str(ct),tea2.b2a_hex(hexval)))
    print pt
    #x = new(hexval.decode('hex'), mode=MODE_ECB)
    #key = x.decrypt(ct)  
    if 'HV15' in str(pt):
        print pt






'''
CODE:004010B0                 call    GetCurrentProcessId
CODE:004010B5                 imul    eax, 12345678h
CODE:004010BB                 mov     ds:dword_402074, eax
CODE:004010C0                 xor     eax, 0BABEF00Dh
CODE:004010C5                 mov     ds:dword_402078, eax
CODE:004010CA                 sub     eax, 1EE7C0DEh
CODE:004010CF                 mov     ds:dword_40207C, eax
CODE:004010D4                 add     eax, 42424242h
CODE:004010D9                 mov     ds:dword_402080, eax
'''