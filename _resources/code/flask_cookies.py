import argparse
import sys
import ast
import zlib

from flask.sessions import SecureCookieSessionInterface
from itsdangerous import base64_decode


class MockApp(object):

    def __init__(self, secret_key):
        self.secret_key = secret_key


def flask_cookie_encode(app, cookie):
    session_cookie_structure = dict(ast.literal_eval(cookie))
    si = SecureCookieSessionInterface()
    s = si.get_signing_serializer(app)
    return s.dumps(session_cookie_structure)

def flask_cookie_decode(app, cookie):
    if not app.secret_key:
        compressed = False
        payload = cookie

        if payload.startswith('.'):
            compressed = True
            payload = payload[1:]

        data = payload.split(".")[0]

        data = base64_decode(data)
        if compressed:
            data = zlib.decompress(data)

        return data
    else:
        si = SecureCookieSessionInterface()
        s = si.get_signing_serializer(app)
        return s.loads(cookie)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='RSA related computations')
    parser.add_argument('action', choices=['encode', 'decode'])
    parser.add_argument('-s', '--secret-key', help='secret key')
    args = parser.parse_args()

    cookie = sys.stdin.read().strip()
    app = MockApp(args.secret_key)

    if args.action == 'encode':
        print(flask_cookie_encode(app, cookie))
    elif args.action == 'decode':
        print(flask_cookie_decode(app, cookie))
