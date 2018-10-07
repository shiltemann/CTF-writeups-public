import argparse
import gmpy2
from Crypto.PublicKey import RSA
from fractions import gcd


def modexp(a, b, N):
    """ modular exponentiation a^b % N
    find ct given pt,e,N ( pt^e mod N )
    find pt given ct,d,N ( ct^d mod N )
    """
    return gmpy2.powmod(a, b, N)


def modexp2(a, b, N):
    """ modular exponentiation without using libs """
    r = 1
    while 1:
        if b % 2 == 1:
            r = r * a % N
        b //= 2
        if b == 0:
            break
        a = a * a % N
    return r


def moddiv(a, b, N):
    """ modular division
    find d given e, p, q -> gmpy2.divm(1,e,totient(p,q))
    """
    gmpy2.divm(a, b, N)


def totient(p, q):
    """totient
    find r given p,q
    """
    return (p-1)*(q-1)


def factor(N):
    """ factorization
    TODO: try factordb first: https://github.com/ryosan-470/factordb-pycli
    if failes, try to compute ?
    """
    pass


# helpers
def modulus_from_factors(p, q):
    """ multiplication
    find N given p, q
    """
    return p * q


def factor_from_modulus_and_factor(f, N):
    """ division
    find p given N, q
    find q given N, p
    """
    return N // f


def test():
    assert (modexp(2, 3, 10) == 8), "problem with modexp function"
    assert (modexp(3, 2, 10) == 9), "problem with modexp function"
    assert (modexp(3, 3, 10) == 7), "problem with modexp function"
    assert (modulus_from_factors(93187, 94603) == 8815769761), "problem with modulus from factors function"


# pip install gmpy2
#   - sudo apt-get install libmpfr-dev
#   - sudo apt-get install libmpc-dev
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

    """
    N = p*q
    p = N // q
    q = N // p

    r = (q-1)*(p-1)

    d = gmpy2.divm(1,e,r)
    e = gmpy2.divm(1,d,r)

    ct = gmpy2.powmod(pt,e,N)
    pt = gmpy2.powmod(ct,d,N)
    """
    test()
