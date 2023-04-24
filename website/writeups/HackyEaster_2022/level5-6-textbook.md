---
layout: writeup

title: Textbook
level: 5          # optional, for events that use levels (like HackyEaster)
difficulty: medium     # easy/medium/hard etc, if applicable
points: 200        # if used
categories: [crypto]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{!!t3xtb00k_crypt0!!}

---

## Challenge

I've got the source code and the output of a simple cipher.

Can you calculate the flag from it?

## Solution

Looking at [`writeupfiles/textbook/generate.py`](writeupfiles/textbook/generate.py), we see it's super simple, the numbers are all:

```python
ct.append(pow(ord(c), e, n))
```

We know `c`, we know `e` (65537) and we know `ct` (from the [`output`](writeupfiles/textbook/output.py) file).

Eventually we found [this answer on Crypto.SE](https://crypto.stackexchange.com/a/14352), the important bit reads:

>  Actually, it turns out that if the public exponent isn't too large, the attacker is in luck; if the attacker takes two known plaintext/ciphertext pairs P1,C1 and P2,C2, [they] can compute $$gcd(P^{e}_{1}−C_{1}, P^{e}_{2}−C_{2})$$

That's a trivial attack for us. Looking at the `output` file, we can even see the repetition of `2022` apparent clearly in the header, the repeated numbers makes it very easy to say it's the same input and likely we're looking at the encrypted values of `he2022` so let's pull that into python, pairs of PT + CT:

```python
import itertools
import string
import gmpy2
from output import CT

e = 65537

known = {
    'h': 18775795524598247683907618594648741761388590921639844605467180006396151189786215265758535337575193670309021582855264867028892978548414321559653798152761136051485505665603301349985640126614264715786618396324939151873322657200895440952009868552269929884443913952032345927889429757591197555362257974762300427077,
    'e': 26011351209175025206763581075024853552087390113459179407864027744918423757072640750702521967542438972832007354186124976428214658014339313691584211007102294219513772038953948006839749138993127974767870845453318323126145261576999544252516469455909654786162700125163666914824091205600834887595162995913379417502,
    '2': 31126027103773387351407738595758142992757290966422921906427583547807098239051608888510957245463260061378552690318743836051281072776395967616847139914308771289374849671126133828143242104999626354384556859251290533589200430407800561772545504440865161148516656248368860617679069912661054218632351283389360492636,
    '0': 33684185672169051982585355390624457073781125802593439786734555391921020764135794272438413202017837358694253342130763010770015611379636085226552836884143628891066368992267302059813243306707573393171165117187880314519923028262275396235438297673412439133301420995863550669342151850235528917701042594555186962024,
    '{': 43283051076672963140434066373128449128233454473574366866727769790690168423259916523176359868863407806090707305813302415073332716039344877397177362916244707139453074977025784117197746543408252406901718015246037520507913732166108314251738782237756346797656994325200715896452351937956613340716980498944591436851,
    '}': 3091633148363652646770092010443201598237353278811637957518304391171561834765093317629653127123047081493716413860116204322756599258146171304663380237017195643995093453756601675833814640677571021005978496814057808056012886878634413057457512996940948408306118798039657509816558179390529087357382252480124057030,
}

```

Then we can compute the requested value:

```python
possible = []

for pairs in itertools.combinations(known.keys(), 2):
    gcd = gmpy2.gcd(
        pow(ord(pairs[0]), e) - known[pairs[0]],
        pow(ord(pairs[1]), e) - known[pairs[1]]
    )

    if gcd not in possible:
        possible.append(gcd)
```

And looking at the `possible` values against factordb, we see they're all multiples of the very first value we find

```python
n = 48309952986767828810211116437346335010234966410717961253604004949499868025260127897876577906582426195177515813973602817599712854363293887621365505327948627549148720502559259505787493271247264526163068321300112038993135083719786793834890849093167509340135523281225587591461719272832908482103617007228902444181
```

So let's check that we have the correct N by using it to encryp:

```python
# Check if N is right.
for k, v in known.items():
    print('====')
    print(v)
    print(pow(ord(k), e, n))
```

And it defo is. So we'll load `output` and create a simple lookup table from `string.printable`

```python
lookup = {}

for i in string.printable:
    ct = pow(ord(i), e, n)
    lookup[ct] = i

print(''.join([lookup[x] for x in CT]))
```
