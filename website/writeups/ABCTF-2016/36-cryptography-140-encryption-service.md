---
layout: writeup
title: 'Cryptography 140: Encryption Service'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

See if you can break this!!

You can connect with `nc 107.170.122.6 7765` and the source can be found
here:

    #/usr/bin/env python
    from Crypto.Cipher.AES import AESCipher
    
    import SocketServer,threading,os,time
    import signal
    
    from secret2 import FLAG, KEY
    
    PORT = 7765
    
    def pad(s):
      l = len(s)
      needed = 16 - (l % 16)
      return s + (chr(needed) * needed)
    
    def encrypt(s):
      return AESCipher(KEY).encrypt(pad('ENCRYPT:' + s.decode('hex') + FLAG))
    
    class incoming(SocketServer.BaseRequestHandler):
        def handle(self):
            atfork()
            req = self.request
    
            def recvline():
                buf = ""
                while not buf.endswith("\n"):
                    buf += req.recv(1)
                return buf
            signal.alarm(5)
    
            req.sendall("Send me some hex-encoded data to encrypt:\n")
            data = recvline()
            req.sendall("Here you go:")
            req.sendall(encrypt(data).encode('hex') + '\n')
            req.close()
    
    class ReusableTCPServer(SocketServer.ForkingMixIn, SocketServer.TCPServer):
      pass
    
    SocketServer.TCPServer.allow_reuse_address = True
    server = ReusableTCPServer(("0.0.0.0", PORT), incoming)
    
    print "Server listening on port %d" % PORT
    server.serve_forever()
{: .language-python}

## Solution

