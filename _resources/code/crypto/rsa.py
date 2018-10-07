import argparse
import gmpy2
import primefac
from Crypto.PublicKey import RSA
from fractions import gcd

def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('modular inverse does not exist')
    else:
        return x % m


### modular exponentiation a^b % N
# find ct given pt,e,N ( pt^e mod N )
# find pt given ct,d,N ( ct^d mod N )
def modexp(a, b, N):
    return gmpy2.powmod(a, b, N)


# without using libs
def modexp2(a, b, N):
    r = 1
    while 1:
        if b % 2 == 1:
            r = r * a % N
        b //= 2
        if b == 0:
            break
        a = a * a % N
    return r

### modular division
# find
def moddiv(a, b, N):
    gmpy2.lo

def compute_totient(p, q):
    return (p-1)*(q-1)

def factor(N):
    return primefac.primefac(N)

### helpers
def modulus_from_factors(p, q):
    return p * q

### division
# find p given N, q
# find q given N, p
def factor_from_modulus_and_factor(f, N):
    return int(N / f)



def test():
    assert (modexp(2, 3, 10) == 8), "problem with modexp function"
    assert (modexp(3, 2, 10) == 9), "problem with modexp function"
    assert (modexp(3, 3, 10) == 7), "problem with modexp function"

    assert (modulus_from_factors(93187,94603) == 8815769761), "problem with modulus from factors function"


# pip install gmpy2
#   - sudo apt-get install libmpfr-dev
#   - sudo apt-get install libmpc-dev
# pip install primefac
# if no target selected, all calculatable variables will be printed to stdout
if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='RSA related computations')
    parser.add_argument('--target', '-t', choices=['N', 'p', 'q', 'e', 'd', 'r', 'm', 'c'], help='variable you are after')
    parser.add_argument('-n', help='N - modulus (keylength)')
    parser.add_argument('-p', help='p - prime factor 1')
    parser.add_argument('-q', help='q - prime factor 2')
    parser.add_argument('-e', help='e - encryption key')
    parser.add_argument('-d', help='d - decryption key')
    parser.add_argument('-r', help='r - totient')
    parser.add_argument('-m', help='m - messsage (plaintext)')
    parser.add_argument('-c', help='c - ciphertext')

    args = vars(parser.parse_args())

    test()

