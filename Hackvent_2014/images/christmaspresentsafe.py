import hashlib
from Crypto.Cipher import AES
from base64 import b64decode, b64encode

def fail():
    print 'Hey, wait for christmas.'
    exit(1)


print 'This present has a high-security lock to prevent any unauthorized unwrapping before christmas!'
try:
    pins = [ int(pin) for pin in raw_input('Enter your PIN codes: ').split(' ') ]
except:
    fail()

if len(pins) != 4:
    fail()
for pin in pins:
    if pin < 100000 or pin > 999999:
        fail()

if (pins[0] ^ pins[1]) + pins[2] != pins[3]:
    fail()
if pins[0] & 57005 | pins[1] >> 10 != 54399:
    fail()
if pins[3] + pins[2] - pins[0] ^ pins[1] != 702564:
    fail()
if (pins[1] * 23 - 1234567 ^ pins[3]) - 1199399 != 42:
    fail()
if pins[2] & pins[1] & pins[3] != 8448:
    fail()
if [ pin >> 16 for pin in pins ] != [3, 1, 6, 8]:
    fail()
if pins[1] & 543210 != 18752:
    fail()
if (pins[0] - 23 ^ pins[3]) % pins[2] >> 8 != 1275:
    fail()
if pins[2] / 10000 != 42 or pins[2] % 100 != 42:
    fail()
if pins[3] | pins[0] != 783695:
    fail()
if [ pin & 3 for pin in pins ] != [2, 1, 2, 1]:
    fail()
if pins[3] % pins[1] & pins[2] != 10544:
    fail()
if pins[0] ^ 12648430 | pins[1] != 12845045:
    fail()
if 507707 != (pins[1] | pins[2]) ^ pins[0] - 234242:
    fail()
if pins[1] & pins[2] != 30992:
    fail()
wrappedPresent = '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'
cipher = AES.new(hashlib.sha256('M'.join([ str(pin) for pin in pins ])).digest(), AES.MODE_CFB, 'AnIVofHighDegree')
open('present.gif', 'w').write(cipher.decrypt(b64decode(wrappedPresent)))
print "Your present was unwrapped to 'present.gif'. Go get it!"
