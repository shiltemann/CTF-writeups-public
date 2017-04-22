#!/usr/bin/env python
'''
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
---------------------------------------------------------------------
The first step in creating a cryptographic hash lookup table.
Creates a file of the following format:

    [HASH_PART][WORDLIST_OFFSET][HASH_PART][WORDLIST_OFFSET]...

HASH_PART is the first 64 BITS of the hash, right-padded with zeroes if
necessary.  WORDLIST_OFFSET is the position of the first character of the
word in the dictionary encoded as a 48-bit LITTLE ENDIAN integer.
'''


import sys
import time
import struct
import hashlib
import argparse
import threading
import platform

from os import _exit, getcwd, path

try:
    from Algorithms import algorithms
except ImportError:
    sys.stderr.write("Missing file Algorithms.py")
    _exit(2)

if platform.system().lower() in ['linux', 'darwin']:
    W = "\033[0m"  # default/white
    R = "\033[31m"  # red
    P = "\033[35m"  # purple
    C = "\033[36m"  # cyan
    O = "\033[33m"
    bold = "\033[1m"
    clear = chr(27) + '[2K\r'
else:
    bold = W = R = P = C = O = ""
    clear = '\n'

INFO = bold + C + "[*] " + W
WARN = bold + R + "[!] " + W
MONEY = bold + O + "[$] " + W
PROMPT = bold + P + "[?] " + W


def create_index(fword, fout, algorithm, flock):
    ''' Create an index and write to file '''
    position = fword.tell()
    line = fword.readline()
    while line:
        word = line.strip('\r\n')
        try:
            fdigest = algorithm(word).digest()[:8]  # Only take first 64bits of hash
            fpos = struct.pack('<Q', position)[:6]  # Get 48bit int in little endian
            flock.acquire()
            fout.write("%s%s" % (fdigest, fpos))
            flock.release()
        except KeyboardInterrupt:
            return
        except UnicodeDecodeError:
            pass  # Ignore decode errors, just keep going
        finally:
            position = fword.tell()
            line = fword.readline()


def display_status(fword, fout, flock):
    ''' Display status / progress '''
    try:
        megabyte = (1024.0 ** 2.0)
        fpath = path.abspath(fword.name)
        size = path.getsize(fpath) / megabyte
        sys.stdout.write(INFO + 'Reading %s ...\n' % fpath)
        while not fword.closed and not fout.closed:
            flock.acquire()
            fword_pos = float(fword.tell() / megabyte)
            fout_pos = fout.tell()
            flock.release()
            sys.stdout.write(clear)
            sys.stdout.write(INFO + '%.2f Mb of %.2f Mb' % (fword_pos, size))
            sys.stdout.write(' (%3.2f%s) ->' % ((100.0 * (fword_pos / size)), '%',))
            sys.stdout.write(' "%s" (%.2f Mb)' % (fout.name, float(fout_pos / megabyte)))
            sys.stdout.flush()
            time.sleep(0.25)
    except Exception as error:
        raise error


def get_algorithms(args):
    ''' Returns a list of valid algorithms passed in by the cli '''
    if 'all' in args.algorithms:
        return [algorithms[name] for name in algorithms]
    else:
        names = filter(lambda algo: algo in algorithms, args.algorithms)
        return [algorithms[name] for name in names]


def index_wordlist(fword, fout, algorithm, flock):
    try:
        thread = threading.Thread(target=display_status, args=(fword, fout, flock))
        thread.start()
        create_index(fword, fout, algorithm, flock)
    except KeyboardInterrupt:
        sys.stdout.write(clear + WARN + 'User requested stop ...\n')
        return
    finally:
        fout.close()
        fword.close()
        thread.join()


def main(args):
    flock = threading.Lock()
    for algo in get_algorithms(args):
        fword = open(args.wordlist, 'rb')
        fname = path.basename(args.wordlist)
        fout_path = args.output + '%s-%s.idx' % (fname[:fname.rfind('.')], algo.key)
        mode = 'wb'
        if path.exists(fout_path) and path.isfile(fout_path):
            prompt = raw_input(PROMPT+'File already exists %s [w/a/skip]: ' % fout_path)
            if prompt.lower() == 'a':
                mode = 'ab'
            elif prompt.lower() != 'w':
                mode = None
        if mode is not None:
            fout = open(fout_path, mode)
            sys.stdout.write(clear + INFO + "Creating %s index ...\n" % algo.name)
            sys.stdout.flush()
            index_wordlist(fword, fout, algo, flock)
            sys.stdout.write(clear + INFO + "Completed index file %s\n" % fout_path)
    sys.stdout.write(clear + MONEY + 'All Done.\n')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='Create unsorted IDX files',
    )
    parser.add_argument('-v', '--version',
        action='version',
        version='Create IDX 0.1.1',
    )
    parser.add_argument('-w',
        dest='wordlist',
        help='index passwords from text file',
        required=True,
    )
    parser.add_argument('-a',
        nargs='*',
        dest='algorithms',
        help='hashing algorithm to use: %s' % (['all']+ sorted(algorithms.keys())),
        required=True,
    )
    parser.add_argument('-o',
        dest='output',
        default=getcwd(),
        help='output directory to write data to',
    )
    args = parser.parse_args()
    if path.exists(args.wordlist) and path.isfile(args.wordlist):
        args.output = path.abspath(args.output)
        if not args.output.endswith('/'):
            args.output += '/'
        if path.exists(args.output) and not path.isfile(args.output):
            main(args)
        else:
            sys.stderr.write('Output directory "%s" does not exist' % args.output)
    else:
        sys.stderr.write('Wordlist does not exist, or is not file')
