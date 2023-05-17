#!/usr/bin/python
import sys
import base64
f= open(sys.argv[1],'r')
for l in f:
    u_p= l.split(':')
    b64hash= u_p[1]
    johnable = base64.b64decode(b64hash)
    print u_p[0] +':'+ johnable
