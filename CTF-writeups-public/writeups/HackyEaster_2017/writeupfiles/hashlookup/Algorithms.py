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
import hashlib

try:
    import passlib
    #  from passlib.utils.handlers import MAX_PASSWORD_SIZE
    from passlib.hash import nthash, lmhash, mysql41, oracle10, mysql323, \
        msdcc, msdcc2, postgres_md5
except ImportError:
    err = "\nFailed to import passlib"
    sys.stderr.write(err)
    sys.stderr.flush()
    passlib = None

try:
    import sha3
except ImportError:
    sys.stderr.write("\nFailed to import SHA3")
    sys.stderr.flush()
    sha3 = None

try:
    import whirlpool
except ImportError:
    sys.stderr.write("\nFailed to import whirlpool")
    sys.stderr.flush()
    whirlpool = None


class BaseAlgorithm(object):
    '''
    Gives us a single interface to passlib and hashlib
    '''

    _data = None

    def __init__(self, data=None):
        self._data = data

    def update(self, data):
        if self._data is None:
            self._data = data
        else:
            self._data += data

    def digest(self):
        ''' Overload this method with your algorithm '''
        raise NotImplementedError()

    def hexdigest(self):
        return self.digest().encode('hex')


##########################################################
# > HASHLIB
##########################################################
class Md4(BaseAlgorithm):

    name = 'Message Digest 4'
    key = 'md4'
    hex_length = 32

    def digest(self):
        return hashlib.new('md4', self._data).digest()


class Md5(BaseAlgorithm):

    name = 'Message Digest 5'
    key = 'md5'
    hex_length = 32

    def digest(self):
        return hashlib.md5(self._data).digest()


class Sha1(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 1'
    key = 'sha1'
    hex_length = 40

    def digest(self):
        return hashlib.sha1(self._data).digest()


class Sha224(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 2 (224 bit)'
    key = 'sha2-224'
    hex_length = 56

    def digest(self):
        return hashlib.sha224(self._data).digest()


class Sha256(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 2 (256 bit)'
    key = 'sha2-256'
    hex_length = 64

    def digest(self):
        return hashlib.sha256(self._data).digest()


class Sha384(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 2 (384 bit)'
    key = 'sha2-384'
    hex_length = 96

    def digest(self):
        return hashlib.sha384(self._data).digest()


class Sha512(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 2 (512 bit)'
    key = 'sha2-512'
    hex_length = 128

    def digest(self):
        return hashlib.sha512(self._data).digest()


class Ripemd160(BaseAlgorithm):

    name = "RACE Integrity Primitives Evaluation Message Digest (160 bit)"
    key = "ripemd160"
    hex_length = 40

    def digest(self):
        md = hashlib.new('ripemd160')
        md.update(self._data)
        return md.digest()


##########################################################
# > SHA3
##########################################################
class Sha3_224(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 3 (224 bit)'
    key = 'sha3-224'
    hex_length = 56

    def digest(self):
        return sha3.sha3_224(self._data).digest()


class Sha3_256(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 3 (256 bit)'
    key = 'sha3-256'
    hex_length = 64

    def digest(self):
        return sha3.sha3_256(self._data).digest()


class Sha3_384(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 3 (384 bit)'
    key = 'sha3-384'
    hex_length = 96

    def digest(self):
        return sha3.sha3_384(self._data).digest()


class Sha3_512(BaseAlgorithm):

    name = 'Secure Hashing Algorithm 3 (512 bit)'
    key = 'sha3-512'
    hex_length = 128

    def digest(self):
        return sha3.sha3_512(self._data).digest()


##########################################################
# > PASSLIB
##########################################################
class Lm(BaseAlgorithm):

    name = 'LM'
    key = 'lm'
    hex_length = 32

    def digest(self):
        return lmhash.encrypt(self._data[:15]).decode('hex')


class Ntlm(BaseAlgorithm):

    name = 'NTLM'
    key = 'ntlm'
    hex_length = 32

    def digest(self):
        return nthash.encrypt(self._data[:127]).decode('hex')


class MySql323(BaseAlgorithm):

    name = 'MySQL v3.2.3'
    key = 'mysql323'
    hex_length = 16

    def digest(self):
        return mysql323.encrypt(self._data[:64]).decode('hex')


class MySql41(BaseAlgorithm):
    ''' Ignore the preceeding "*" symbol '''

    name = 'MySQL v4.1'
    key = 'mysql41'
    hex_length = 40

    def digest(self):
        return mysql41.encrypt(self._data[:64])[1:].decode('hex')


class Oracle10(BaseAlgorithm):
    '''
    Base Oracle 10g algorithm, this algorithm is salted with a username.
    Subclasses contain common usernames.
    '''
    hex_length = 16

    def digest(self):
        return oracle10.encrypt(self._data[:64], user=self._user).decode('hex')


class Oracle10_Sys(Oracle10):

    name = 'Oracle 10g (SYS)'
    key = 'oracle10g-sys'
    _user = 'SYS'


class Oracle10_System(Oracle10):

    name = 'Oracle 10g (SYSTEM)'
    key = 'oracle10g-system'
    _user = 'SYSTEM'


class PostgresMd5(BaseAlgorithm):

    hex_length = 32

    def digest(self):
        ''' Removes the "md5" prefix '''
        return postgres_md5.encrypt(self._data[:64],
                                    user=self._user)[3:].decode('hex')


class PostgresMd5_Root(PostgresMd5):

    name = 'Postgres MD5 (root)'
    key = 'postgres_md5-root'
    _user = 'root'


class PostgresMd5_Postgres(PostgresMd5):

    name = 'Postgres MD5 (postgres)'
    key = 'postgres_md5-postgres'
    _user = 'postgres'


class PostgresMd5_Admin(PostgresMd5):

    name = 'Postgres MD5 (admin)'
    key = 'postgres_md5-admin'
    _user = 'admin'


class Msdcc_Administrator(BaseAlgorithm):

    name = 'MS Domain Cached Credentials'
    key = 'msdcc-administrator'
    hex_length = 32
    _user = "administrator"

    def digest(self):
        return msdcc.encrypt(self._data[:64], user=self._user).decode('hex')


class Msdcc2_Administrator(BaseAlgorithm):

    name = 'MS Domain Cached Credentials v2'
    key = 'msdcc2-administrator'
    hex_length = 32
    _user = "administrator"

    def digest(self):
        return msdcc2.encrypt(self._data[:64], user=self._user).decode('hex')


##########################################################
# > Whirlpool
##########################################################
class Whirlpool(BaseAlgorithm):

    name = "Whirlpool"
    key = "whirlpool"
    hex_length = 128

    def digest(self):
        return whirlpool.new(self._data).digest()


# Base algorithms
algorithms = {
    Md4.key: Md4,
    Md5.key: Md5,
    Sha1.key: Sha1,
    Sha224.key: Sha224,
    Sha256.key: Sha256,
    Sha384.key: Sha384,
    Sha512.key: Sha512,
}

if hasattr(hashlib, "algorithms_available"):
    if 'ripemd160' in hashlib.algorithms_available:
        algorithms[Ripemd160.key] = Ripemd160

if sha3 is not None:
    algorithms[Sha3_224.key] = Sha3_224
    algorithms[Sha3_256.key] = Sha3_256
    algorithms[Sha3_384.key] = Sha3_384
    algorithms[Sha3_512.key] = Sha3_512

if passlib is not None:
    algorithms[Lm.key] = Lm
    algorithms[Ntlm.key] = Ntlm
    algorithms[MySql323.key] = MySql323
    algorithms[MySql41.key] = MySql41
    algorithms[Oracle10_Sys.key] = Oracle10_Sys
    algorithms[Oracle10_System.key] = Oracle10_System
    algorithms[Msdcc_Administrator.key] = Msdcc_Administrator
    algorithms[Msdcc2_Administrator.key] = Msdcc2_Administrator
    algorithms[PostgresMd5_Admin.key] = PostgresMd5_Admin
    algorithms[PostgresMd5_Postgres.key] = PostgresMd5_Postgres
    algorithms[PostgresMd5_Root.key] = PostgresMd5_Root

if whirlpool is not None:
    algorithms[Whirlpool.key] = Whirlpool
