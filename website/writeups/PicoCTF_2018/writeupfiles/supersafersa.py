import string
import sys
import time
import socket
from netcat import Netcat

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



if __name__ == '__main__':
    nc = Netcat('2018shell1.picoctf.com', 24039)
    c, n, e = nc.read().strip().split('\n')
    c = c[c.index(': ') + 2:]
    n = n[n.index(': ') + 2:]
    e = e[e.index(': ') + 2:]
    print(c, n, e)
