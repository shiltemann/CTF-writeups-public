---
layout: writeup
title: 'Spaghetti hash'
level:
difficulty:
points:
categories: []
tags: []
flag: ICBXMH6GJqMqYDsjkaCn

---

## Challenge
Lazy Larry needs to improve the security of his password hashing
implementation. He decides to use SHA-512 as a new hashing algorithm in
order to be super secure. Unfortunately, the database column for the
hash can only hold 128 bit. As Bob is too lazy to extend the column and
all the code related to it, he decides to shrink the output of the
SHA-512 operation, to 128 bit. For this purpose he picks certain
characters from the SHA-512 output for producing the new value.

You got hold of four password hashes, calculated with Bob's new
implementation. Can you find the corresponding passwords?

    hash 1: 87017a3ffc7bdd5dc5d5c9c348ca21c5
    hash 2: ff17891414f7d15aa4719689c44ea039
    hash 3: 5b9ea4569ad68b85c7230321ecda3780
    hash 4: 6ad211c3f933df6e5569adf21d261637

Lucky you, you know that the following web service is calculating Bob's
algorithm. However, the web service only accepts strings of length 4 or
less - brute-forcing a password list thus is no option, since the
passwords you are looking for are all longer.

    https://hackyeaster.hacking-lab.com/hackyeaster/hash?string=abcd

## Solution

First we use the web service to determine how the smaller hashes are
obtained from the full hashes, next we use a dictionary to test whether
the smaller hashes
match any of the four we are looking for

    import requests
    import hashlib

    url="https://hackyeaster.hacking-lab.com/hackyeaster/hash?string="

    def all_occurrences(s, ch):
        return [i for i, ltr in enumerate(s) if ltr == ch]

    # first, determine where each character in new hashes came from in original
    # by looking at multiple ones
    pairs = []
    for c in "abcde":
        m = hashlib.sha512()
        m.update(c)
        r = requests.get(url+c)
        pairs.append([m.hexdigest(), r.text])

    origins = []
    for i in range(0, len(pairs[0][1])):
        #print "pos: "+str(i)
        possibilities = []
        for j in range(0, len(pairs)):
            n = all_occurrences(pairs[j][0], pairs[j][1][i])
            #print n
            if possibilities == []:
                possibilities = set(n)
            else:
                possibilities = possibilities.intersection(n)
        origins += list(possibilities)

    print "\nOrigin of each position in small hash:"
    print origins
    # [65, 17, 115, 31, 45, 11, 67, 92, 0, 7, 123, 37, 5, 22, 87, 124, 25, 89, 38, 61, 90, 109, 63, 28, 102, 12, 47, 59, 110, 86, 24, 18]

    def get_mini_hash(pt, positions):
        m = hashlib.sha512()
        m.update(pt)
        longhash = m.hexdigest()
        smallhash = ''
        for i in positions:
            smallhash += longhash[i]

        return smallhash


    # now we know how smaller hashes are derived from larger ones, try word from
    # a dictionary to see which match hashes we are looking for
    targets = ['87017a3ffc7bdd5dc5d5c9c348ca21c5',
               'ff17891414f7d15aa4719689c44ea039',
               '5b9ea4569ad68b85c7230321ecda3780',
               '6ad211c3f933df6e5569adf21d261637']

    found = 0
    with open('wordlist.txt') as f:
        while found < 4:
            w = f.readline().strip()
            smallh = get_mini_hash(w, origins)
            if smallh in targets:
                print "\nFound!"
                print w
                print smallh
                found += 1
{: .language-python}

This outputs the following solutions:

    Origin of each position in small hash:
    [65, 17, 115, 31, 45, 11, 67, 92, 0, 7, 123, 37, 5, 22, 87, 124, 25, 89, 38, 61, 90, 109, 63, 28, 102, 12, 47, 59, 110, 86, 24, 18]

    Found!
    12345678
    6ad211c3f933df6e5569adf21d261637

    Found!
    benchmark
    5b9ea4569ad68b85c7230321ecda3780

    Found!
    Cleveland
    ff17891414f7d15aa4719689c44ea039

    Found!
    Prodigy
    87017a3ffc7bdd5dc5d5c9c348ca21c5

we fill these four solutions into the egg-o-matic to get our egg:

![](writeupfiles/egg20.png)

