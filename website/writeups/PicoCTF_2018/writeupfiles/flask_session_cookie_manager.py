""" Flask Session Cookie Decoder/Encoder """
__author__ = 'Wilson Sumanang, Alexandre ZANNI'

# standard imports
import sys
import zlib
from itsdangerous import base64_decode
import ast

# Lib for argument parsing
import argparse

# Description for help
parser = argparse.ArgumentParser(
            description='Flask Session Cookie Decoder/Encoder',
            epilog="Author : Wilson Sumanang, Alexandre ZANNI")

# prepare sub commands
subparsers = parser.add_subparsers(help='sub-command help', dest='subcommand')

# create the parser for the encode command
parser_encode = subparsers.add_parser('encode', help='encode')
parser_encode.add_argument('-s', '--secret-key', metavar='<string>',
                            help='Secret key', required=True)
parser_encode.add_argument('-t', '--cookie-structure', metavar='<string>',
                            help='Session cookie structure', required=True)

# create the parser for the decode command
parser_decode = subparsers.add_parser('decode', help='decode')
parser_decode.add_argument('-s', '--secret-key', metavar='<string>',
                            help='Secret key', required=False)
parser_decode.add_argument('-c', '--cookie-value', metavar='<string>',
                            help='Session cookie value', required=True)

# get args
args = parser.parse_args()

# external Imports
from flask.sessions import SecureCookieSessionInterface


class MockApp(object):

    def __init__(self, secret_key):
        self.secret_key = secret_key


def session_cookie_encoder(secret_key, session_cookie_structure):
    """ Encode a Flask session cookie """
    try:
        app = MockApp(secret_key)

        session_cookie_structure = dict(ast.literal_eval(session_cookie_structure))
        si = SecureCookieSessionInterface()
        s = si.get_signing_serializer(app)

        return s.dumps(session_cookie_structure)
    except Exception as e:
        return "[Encoding error]{}".format(e)


def session_cookie_decoder(session_cookie_value, secret_key=None):
    """ Decode a Flask cookie  """
    try:
        if(secret_key==None):
            compressed = False
            payload = session_cookie_value

            if payload.startswith(b'.'):
                compressed = True
                payload = payload[1:]

            data = payload.split(".")[0]

            data = base64_decode(data)
            if compressed:
                data = zlib.decompress(data)

            return data
        else:
            app = MockApp(secret_key)

            si = SecureCookieSessionInterface()
            s = si.get_signing_serializer(app)

            return s.loads(session_cookie_value)
    except Exception as e:
        return "[Decoding error]{}".format(e)


if __name__ == "__main__":
    # find the option chosen
    if(args.subcommand == 'encode'):
        if(args.secret_key is not None and args.cookie_structure is not None):
            print(session_cookie_encoder(args.secret_key, args.cookie_structure))
    elif(args.subcommand == 'decode'):
        if(args.secret_key is not None and args.cookie_value is not None):
            print(session_cookie_decoder(args.cookie_value,args.secret_key))
        elif(args.cookie_value is not None):
            print(session_cookie_decoder(args.cookie_value))

