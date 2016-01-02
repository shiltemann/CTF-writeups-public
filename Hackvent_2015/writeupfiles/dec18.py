import base64
import md5
import itertools

targets=['fab3e420d6d8a17b53b23ca4bb01866b',
         '189f56eea9a9ba305dffa8425ba20048',
         '2335667c646346b38c8f0f47b13fab13',
         'f4709a7eef9d703920b910fc734b151c',
         'b74e57f21f5a315550a9e2f6869d4e44',
         '40abc257b6f0e0420dc9ae9ba19c8c8c']

alphabet='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'

# try all 4 letter fragments of the nugget, md5(base64(fragment)) must equal one of the targets
for fragment in itertools.product(alphabet,repeat=4):
    fragment = ''.join(fragment)
    m=md5.new()           
    m.update( base64.b64encode(fragment) )
    if m.hexdigest() in targets:
        print 'Found fragment: '+fragment+' ('+m.hexdigest()+')' 


# HV15-9aSY-BcJH-N8tK-AHzP-QmHY