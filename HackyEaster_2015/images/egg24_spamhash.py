# -*- coding: utf-8 -*-
"""
Created on Wed Apr  8 17:17:32 2015

@author: zazkia
"""

import itertools
from Crypto.Hash import MD2,MD5,SHA,SHA256,SHA512

charset = 'abcdefghijklmnopqrstuvwxyz0123456789'

for i in itertools.product(charset, repeat=6): 
    MD2hash = MD2.new(''.join(i)).hexdigest()
    MD5hash = MD5.new(''.join(i)).hexdigest()
    SHA1hash = SHA.new(''.join(i)).hexdigest()
    SHA256hash = SHA256.new(''.join(i)).hexdigest()
    SHA512hash = SHA512.new(''.join(i)).hexdigest()
    
    if MD2hash.startswith('757c47'):
        print 'MD2: '+''.join(i)+' '+MD2hash 
        with open("egg_24_MD2.txt", "a") as myfile:
            myfile.write('MD2: '+''.join(i)+' '+MD2hash)
    
    if MD5hash[6:].startswith('9895d6'):
        print 'MD5: '+''.join(i)+' '+MD5hash 
        with open("egg_24_MD5.txt", "a") as myfile2:
            myfile2.write('MD5: '+''.join(i)+' '+MD5hash)
        
    if SHA1hash[12:].startswith('845b2b'):
        print 'SHA1: '+''.join(i)+' '+SHA1hash 
        with open("egg_24_SHA1.txt", "a") as myfile3:
            myfile3.write('SHA1: '+''.join(i)+' '+SHA1hash)

    if SHA256hash[18:].startswith('0530cd'):
        print 'SHA256: '+''.join(i)+' '+SHA256hash 
        with open("egg_24_SHA256.txt", "a") as myfile4:
            myfile4.write('SHA256: '+''.join(i)+' '+SHA256hash)

    if SHA512hash[24:].startswith('9a2b11'):
        print 'SHA512: '+''.join(i)+' '+SHA512hash 
        with open("egg_24_SHA512.txt", "a") as myfile5:
            myfile5.write('SHA512: '+''.join(i)+' '+SHA512hash)
        
