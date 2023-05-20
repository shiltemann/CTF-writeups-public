---
layout: writeup
title: 'Cryptography 450: Magic Padding Oracle'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
## Challenge

Can you help us retreive the flag from this crypto service?

Connect with `nc 2018shell1.picoctf.com 4966`.

We were able to recover some [Source Code](writeupfiles/pkcs7.py).

    #!/usr/bin/python2
    import os
    import json
    import sys
    import time
    
    from Crypto.Cipher import AES
    
    cookiefile = open("cookie", "r").read().strip()
    flag = open("flag", "r").read().strip()
    key = open("key", "r").read().strip()
    
    welcome = """
    Welcome to Secure Encryption Service version 1.1
    """
    def pad(s):
      return s + (16 - len(s) % 16) * chr(16 - len(s) % 16)
    
    def isvalidpad(s):
      return ord(s[-1])*s[-1:]==s[-ord(s[-1]):]
    
    def unpad(s):
      return s[:-ord(s[len(s)-1:])]
    
    def encrypt(m):
      IV="This is an IV456"
      cipher = AES.new(key.decode('hex'), AES.MODE_CBC, IV)
      return IV.encode("hex")+cipher.encrypt(pad(m)).encode("hex")
    
    def decrypt(m):
      cipher = AES.new(key.decode('hex'), AES.MODE_CBC, m[0:32].decode("hex"))
      return cipher.decrypt(m[32:].decode("hex"))
    
    
    # flush output immediately
    sys.stdout = os.fdopen(sys.stdout.fileno(), 'w', 0)
    print welcome
    print "Here is a sample cookie: " + encrypt(cookiefile)
    
    # Get their cookie
    print "What is your cookie?"
    cookie2 = sys.stdin.readline()
    # decrypt, but remove the trailing newline first
    cookie2decoded = decrypt(cookie2[:-1])
    
    if isvalidpad(cookie2decoded):
       d=json.loads(unpad(cookie2decoded))
       print "username: " + d["username"]
       print "Admin? " + d["is_admin"]
       exptime=time.strptime(d["expires"],"%Y-%m-%d")
       if exptime > time.localtime():
          print "Cookie is not expired"
       else:
          print "Cookie is expired"
       if d["is_admin"]=="true" and exptime > time.localtime():
          print "The flag is: " + flag
    else:
       print "invalid padding"
{: .language-python}

## Solution

