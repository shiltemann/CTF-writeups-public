import string
import sys
import time
from netcat import Netcat


# This could def be more automated buuuuuut no.
for z in range(0, 95, 20):
    nc = Netcat('2018shell1.picoctf.com', 31123)
    nc.read() # Hello
    nc.read() # enter report
    wrap_start = 'z' * 11 + 'a' * 16

    wrap_end = 'a' * (16 + 11)  +'\n'

    # picoCTF{@g3nt6_1$_th3_c00l3$t_3355197}
    inputs = [
        'c00l3$t_3355197' + y
        for y in ['_'] + list(string.printable[z:min(z + 20, 95)])
    ]


    nc.write(wrap_start + ''.join(inputs) + wrap_end)

    resp = nc.read() # output


    def splitn(line, n=32):
        return [line[i:i+n] for i in range(0, len(line), n)]


    # split on the 'a' * 16
    # print('\n'.join(splitn(resp)))

    prefix, queries, postfix = resp.split('99908ad37adef3fb5a94680c5a64c6ca')

    pm = list(splitn(postfix))

    # ignore prefix

    for (q, i) in zip(splitn(queries), inputs):
        print(i, q in pm)
        if q in pm:
            sys.exit()

    time.sleep(1)
