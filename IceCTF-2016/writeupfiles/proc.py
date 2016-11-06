#!/usr/bin/python

data = """
 value="2000000000000000"
 value="20000a0000000000"
 value="2000000000000000"
 value="00000c0000000000"
 value="0000070000000000"
 value="0200000000000000"
 value="02000c0000000000"
 value="0200000000000000"
 value="02000e0000000000"
 value="0200000000000000"
 value="02001c0000000000"
 value="0200000000000000"
 value="0200000000000000"
 value="02002f0000000000"
 value="0200000000000000"
 value="0200000000000000"
 value="0200360000000000"
 value="0200000000000000"
 value="00000d0000000000"
 value="0000270000000000"
 value="0200000000000000"
 value="02002d0000000000"
 value="0200000000000000"
 value="0000130000000000"
 value="00001e0000000000"
 value="2000000000000000"
 value="2000190000000000"
 value="2000000000000000"
 value="0000200000000000"
 value="0200000000000000"
 value="0200330000000000"
 value="0200000000000000"
 value="02002d0000000000"
 value="0200000000000000"
 value="00001b0000000000"
 value="0000360000000000"
 value="0000200000000000"
 value="0200000000000000"
 value="0200120000000000"
 value="0200000000000000"
 value="0000240000000000"
 value="2000000000000000"
 value="2000170000000000"
 value="2000000000000000"
 value="0200000000000000"
 value="02002d0000000000"
 value="0200000000000000"
 value="0000210000000000"
 value="0200000000000000"
 value="02000f0000000000"
 value="0200000000000000"
 value="0200170000000000"
 value="0200000000000000"
 value="0000360000000000"
 value="0000210000000000"
 value="0000170000000000"
 value="0000220000000000"
 value="0200000000000000"
 value="0200300000000000"
 value="0200000000000000"
"""

# Lower case are known positively, upper case are unknowns. {} and - are
# correct. ! are completely unknown
#
# I believe the keyboard reported a en_US layout during the capture, but I do
# not see that now. Maybe different layout?
#  - Not QWERTYUIOP, spacing between E + I is wrong.
#  - Not Dvorak, (aoeui) is the home row, wrong spacing
#  - Icelandic keyboards are standard qwerty.
#  - Not alphabetical, e + i is off, f is at the end.
#
# Other keyboard data: PI Engineering, Inc. Kinesis Advantage PRO MPC/USB Keyboard.
alpha = "ABCeEFiHcJtLMNOPQRSTUVWXfZ1234567890!!!\n -={}!!!!!!"
offset = 4

KEY_CODES = {x + offset: alpha[x] for x in range(len(alpha))}

output = []
for line in data.strip().split('\n'):
    shifted = line[8:10] != "00"
    x = int(line[12:14], 16)
    if x != 0:
        c = KEY_CODES.get(x, '')
        if c == '-' and shifted:
            output.append('_')
        else:
            if shifted:
                output.append(c.upper())
            else:
                output.append(c.lower())
print ''.join(output)
