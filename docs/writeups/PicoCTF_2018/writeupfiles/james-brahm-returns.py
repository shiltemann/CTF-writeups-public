#!/usr/bin/python2 -u
from Crypto.Cipher import AES
import reuse
import random
from string import digits
import hashlib

agent_code = """flag"""
key = """key"""


def pad(message):
    if len(message) % 16 == 0:
        message = message + chr(16)*16
    elif len(message) % 16 != 0:
        message = message + chr(16 - len(message)%16)*(16 - len(message)%16)
    return message

def encrypt(key, plain, IV):
    cipher = AES.new( key.decode('hex'), AES.MODE_CBC, IV.decode('hex') )
    return IV + cipher.encrypt(plain).encode('hex')

def decrypt(key, ciphertext, iv):
    cipher = AES.new(key.decode('hex'), AES.MODE_CBC, iv.decode('hex'))
    return cipher.decrypt(ciphertext.decode('hex')).encode('hex')

def verify_mac(message):
    h = hashlib.sha1()    
    mac = message[-40:].decode('hex')
    message = message[:-40].decode('hex')
    h.update(message)
    if h.digest() == mac:
        return True
    return False
    
def check_padding(message):
    check_char = ord(message[-2:].decode('hex'))
    if (check_char < 17) and (check_char > 0): #bud
        return message[:-check_char*2]
    else:
        return False

welcome = "Welcome, Agent 006!"
print welcome
options = """Select an option:
Encrypt message (E)
Send & verify (S)
"""
while True:
    encrypt_or_send = raw_input(options)
    if "e" in encrypt_or_send.lower():
        
        sitrep = raw_input("Please enter your situation report: ")
        message = """Agent,
Greetings. My situation report is as follows:
{0}
My agent identifying code is: {1}.
Down with the Soviets,
006
""".format( sitrep, agent_code )
        PS = raw_input("Anything else? ")
        h = hashlib.sha1()
        message = message+PS
        h.update(message)
        message = pad(message+ h.digest())

        IV = ''.join(random.choice(digits + 'abcdef') for _ in range(32))
        print "encrypted: {}".format(encrypt(key, message, IV ))
    elif "s" in encrypt_or_send.lower():
        sitrep = raw_input("Please input the encrypted message: ")
        iv = sitrep[:32]
        c = sitrep[32:]
        if reuse.check(iv):
            message = decrypt(key, c, iv)
            message = check_padding(message)
            if message:
                if verify_mac(message):
                    print("Successful decryption.")
                else:
                    print("Ooops! Did not decrypt successfully. Please send again.")
            else:
                print("Ooops! Did not decrypt successfully. Please send again.")
        else:
            print("Cannot reuse IVs!")
            
    
    

