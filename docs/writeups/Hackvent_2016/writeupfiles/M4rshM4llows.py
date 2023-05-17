#!/usr/bin/env python3

import yaml
import uuid
import base64
from ctypes import *

libc = CDLL('libc.so.6')

printf = libc.printf
exit = libc.exit

getenv = libc.getenv
setenv = libc.setenv

getenv.argtypes = [c_char_p]
getenv.restype = c_char_p

setenv.argtypes = [c_char_p, c_char_p, c_int]
setenv.restype = c_int

safe_gets = input
load_yml = yaml.load

setenv(c_char_p(b'MARSHMALLOW_TOKEN'), c_char_p(str(uuid.uuid4()).encode('ascii')), 1)

if __name__ == '__main__':
    printf(b'''[+] WELCOME!

MuffinX presents...

                /^\/^\\
              _|__|  O|
     \/     /~     \_/ \\
      \____|__________/  \\
             \_______      \\
                     `\     \                 \\
                       |     |                  \\
                      /      /                    \\
                     /     /                       \\
                   /      /                         \ \\
                  /     /                            \  \\
                /     /             _----_            \   \\
               /     /           _-~      ~-_         |   |
              (      (        _-~    _--_    ~-_     _/   |
               \      ~-____-~    _-~    ~-_    ~-_-~    /
                 ~-_           _-~          ~-_       _-~
                    ~--______-~                ~-___-~        ... Marshmallows! <3\n\n''')



    while True:
        printf(b'[+] Menu\n\n1 - Play chubby bunny.\n2 - Exit.\n\n')
        user_input = safe_gets('> ')

        if user_input == '1': printf((safe_gets('[+] Please give me some marshmallows: ').replace('marshmallow', 'chubbybunny!') + '\n').encode('ascii'))
        elif user_input == '2': exit(0)
        elif user_input == 'send_secret_marshmallows':
            if safe_gets('[?] Token: ') == getenv(c_char_p(b'MARSHMALLOW_TOKEN')).decode('ascii'):
                printf(b'[+] Good token.\n')
                secret_marshmallows = load_yml(base64.b64decode(safe_gets('[?] Your secret marshmallows: ')).decode('ascii'))
            else: printf(b'[-] Wrong token!\n')

        else: printf(b'[-] Operation not found\n')
