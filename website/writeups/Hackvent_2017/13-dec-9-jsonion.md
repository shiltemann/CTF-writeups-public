---
layout: writeup
title: 'Dec 9: JSONion'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-Ip11-9CaB-JvCf-d5Nq-ffyi

---

*hint*

## Challenge

... is not really an onion. Peel it and find the flag.

[file](writeupfiles/jsonion.json)

## Solution

JSON file with

    [{"op":"map","mapTo":"[{\"op:gzi,cnteH4sIAD

    <bunch of data>

    ,"mapFrom":"\/8ge+uqP5lz:K2Fis\"MonJUp\\{S6NvmOk]ZI}hLRYXBCW4fD=jT0V[b3xaGQ9yEw,A1cdHrt7"}]

..looks like we need to do a character mapping, we do that, then we get
a similar string, but this
time it starts with `[{"op":"gzip", ..`. we decompress this data, and
find a `b64` operation, and as
we keep peeling layers off this jsonion also `nul`, `xor`, `rev`.

We handle them all until we get a `flag` operation:

    import json
    import gzip
    import base64

    def process(data_orig):
        data = json.loads(data_orig)
        for q in data:
            print(len(data_orig), len(data), q['op'])
            if q['op'] == 'map':
                process(q['content'].translate(str.maketrans(q['mapFrom'], q['mapTo'])))
            elif q['op'] == 'gzip':
                process(gzip.decompress(base64.b64decode(q['content'])).decode('utf-8'))
            elif q['op'] == 'b64':
                process(base64.b64decode(q['content']).decode('utf-8'))
            elif q['op'] == 'nul':
                process(q['content'])
            elif q['op'] == 'xor':
                bout = base64.b64decode(q['content'])
                bout2 = base64.b64decode(q['mask'])
                rep = len(bout) // len(bout2)
                process( ''.join([chr(a ^ b) for a,b in zip(bout, bout2*rep)]) )
            elif q['op'] == 'rev':
                process(q['content'][::-1])
            elif q['op'] == 'flag':
                print(q['content'])
            else:
                raise Exception

    with open('jsonion.json', 'r') as handle:
        print(process(handle.read()))
{: .language-python}

which outputs:

    1502755 1 map
    1461121 1 gzip
    1511839 1 b64
    1133858 1 gzip
    3101412 1 map
    3087167 1 map
    2982613 1 nul
    2043890 1 nul
    1574519 1 nul
    1339824 1 nul
    1222467 1 map
    1163619 1 map
    1160571 1 b64
    870407 1 b64
    652783 1 b64
    489565 1 map
    467807 1 gzip
    477847 1 map
    457875 1 map
    438367 1 nul
    424116 1 gzip
    427535 1 nul
    411468 1 gzip
    507106 1 xor
    380298 1 nul
    353459 1 map
    339864 1 map
    326228 1 nul
    304905 1 rev
    294234 1 gzip
    286448 1 gzip
    479112 1 map
    461643 1 map
    446098 1 xor
    331427 1 b64
    248548 1 map
    241961 1 map
    238248 1 nul
    209329 1 rev
    194860 1 xor
    140701 1 nul
    138730 1 map
    137575 1 b64
    103160 1 rev
    97581 1 nul
    94782 1 gzip
    147537 1 rev
    140474 1 rev
    136933 1 map
    134995 1 b64
    101224 1 map
    100487 1 b64
    75343 1 b64
    56485 1 map
    54670 1 rev
    48207 1 nul
    44966 1 xor
    32491 1 gzip
    37182 1 rev
    37147 1 b64
    27839 1 b64
    20859 1 map
    19929 1 xor
    14324 1 gzip
    20656 1 rev
    20337 1 xor
    15115 1 nul
    12880 1 rev
    11753 1 xor
    8374 1 rev
    8339 1 b64
    6233 1 nul
    6136 1 map
    5914 2 xor
    671 1 b64
    482 1 rev
    419 1 nul
    378 1 xor
    251 1 b64
    167 1 b64
    103 1 b64
    57 1 flag
    THIS-ISNO-THEF-LAGR-EALL-Y...
    5914 2 nul
    4891 1 xor
    3621 1 rev
    3506 1 nul
    3439 1 xor
    2537 1 rev
    2486 1 rev
    2451 1 b64
    1818 1 rev
    1783 1 b64
    1315 1 b64
    964 1 nul
    881 1 rev
    830 1 rev
    795 1 b64
    574 1 map
    383 1 b64
    266 1 xor
    167 1 b64
    103 1 b64
    57 1 flag
    HV17-Ip11-9CaB-JvCf-d5Nq-ffyi
    None

