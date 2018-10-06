import string
import sys
import time
import socket

class Netcat:
    """
    Python 'netcat like' module
    https://gist.github.com/leonjza/f35a7252babdf77c8421
    """

    def __init__(self, ip, port):

        self.buff = ""
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.connect((ip, port))

    def read(self, length = 1024):

        """ Read 1024 bytes off the socket """

        return self.socket.recv(length)

    def read_until(self, data):

        """ Read data into the buffer until we have data """

        while not data in self.buff:
            self.buff += self.socket.recv(1024)

        pos = self.buff.find(data)
        rval = self.buff[:pos + len(data)]
        self.buff = self.buff[pos + len(data):]

        return rval

    def write(self, data):

        self.socket.send(data)

    def close(self):
        self.socket.close()


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
