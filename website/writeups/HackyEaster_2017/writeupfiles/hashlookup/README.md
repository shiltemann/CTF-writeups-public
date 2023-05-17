A Python Implementation of [CrackStation.net](http://crackstation.net/)'s Lookup Tables
============================================================

Introduction
------------

There are three components to this system:

1. The indexing script (createidx.py), which takes a wordlist and builds
   a lookup table index for a hash function and the words in the list.

2. The indexing sorter program (sortidx.c), which sorts an index created by the
   indexing script, so that the lookup script can use a binary search on the
   index to crack hashes.

3. The lookup script (LookupTable.py), which uses the wordlist and index to
   crack hashes.

Suported Algorithms:
 * lm
 * md4
 * md5
 * msdcc
 * msdcc2
 * mssql41
 * mysql323
 * ntlm
 * oracle10g-sys
 * oracle10g-system
 * whirlpool
 * ripemd160
 * sha1
 * sha2-224
 * sha2-256
 * sha2-384
 * sha2-512
 * sha3-224
 * sha3-256
 * sha3-384
 * sha3-512

Indexing a Dictionary
---------------------

Suppose you have a password dictionary in the file `words.txt` and you would
like to index it for MD5 and SHA1 cracking.

First, create the MD5 and SHA1 indexes:

    $ ./createidx.py -a md5 -w words.txt
    $ ./createidx.py -a sha1 -w words.txt

Next, build the sortidx program and run it on the indexes.

    $ make
    $ ./sortidx -r 1024 words-md5.idx
    $ ./sortidx -r 1024 words-sha256.idx

The -r parameter is the maximum amount of memory sortidx is allowed to use in
Mb. The more memory you let it use, the faster it will go.

Cracking Hashes
---------------

Once you have generated and sorted the index, you can use the LookupTable class
to crack hashes.  It can be used as a library or a standalone app.

    $ ./LookupTable.py -a md5 -i words-md5.idx -w words.txt -c 1f3870be274f6c49b3e31a0c6728957f

Downloads
----------

You can download my versions of the crackstion wordlist, with pre-computed and sorted indexes ready for cracking.  Includes the wordlist, as well as indexes for MD5, SHA1 and SHA256:

[BTSync key](http://labs.bittorrent.com/experiments/sync.html) (42.5Gb): BYKIAW5MVA52ZJ6C7FNTDNX5TAB2ML4U2

Adding Words
------------

Once a wordlist has been indexed, you can not modify the wordlist file without
breaking the indexes. Appending to the wordlist is safe in that it will not
break the indexes, but the words you append (obviously) won't be indexed,
unless you re-generate the index.

There is currently no way to add words to an index without re-generating the
entire index.
