---
layout: writeup
title: 'Egg 24: Spam Hash'
level:
difficulty:
points:
categories: []
tags: []
flag: pm4y5TfxKoeIl0RnifSc

---

## Challenge:

*Crypto Chiefs Ltd. developed a new hash function, which takes a 'divide
and conquer' approach and combines several well-known hash functions
("Split, Hash, And Merge"). The inventors claim that with this approach,
their function becomes more secure. Can you prove they are wrong?*

*Create a string which produces the following hash:*
`757c479895d6845b2b0530cd9a2b11`

*Specification:*

![](images/egg_24_specification_small.png)

## Solution:

From the specification we see we need to find a 30-character string such
that:

    MD2( inputString[0-5] ) = targetString[0-5] (= 757c47)
    MD5( inputString[6-11] ) = targetString[6-11] (= 9895d6)
    SHA1(  inputString[12-27] ) = targetString[12-17] (= 845b2b)
    SHA256( inputString[18-23] ) = targetString[18-23] (= 0530cd)
    SHA512( inputString[24-29] ) = targetString[24-29] (= 9a2b11)

So we need to find 5 6-character strings which when hashed produce the
desired outcome, and then concatenate them.

We bruteforce 6-character strings until we find a matching input for
each hash type:

    import itertools
    from Crypto.Hash import MD2,MD5,SHA,SHA256,SHA512

    charset = 'abcdefghijklmnopqrstuvwxyz0123456789'

    for i in itertools.product(charset, repeat=6):
        MD2hash = MD2.new(''.join(i)).hexdigest()
        MD5hash = MD5.new(''.join(i)).hexdigest()
        SHA1hash = SHA.new(''.join(i)).hexdigest()
        SHA256hash = SHA256.new(''.join(i)).hexdigest()
        SHA512hash = SHA512.new(''.join(i)).hexdigest()

        if MD2hash.startswith('757c47'):
            print 'MD2: '+''.join(i)+' '+MD2hash

        if MD5hash[6:].startswith('9895d6'):
            print 'MD5: '+''.join(i)+' '+MD5hash

        if SHA1hash[12:].startswith('845b2b'):
            print 'SHA1: '+''.join(i)+' '+SHA1hash

        if SHA256hash[18:].startswith('0530cd'):
            print 'SHA256: '+''.join(i)+' '+SHA256hash

        if SHA512hash[24:].startswith('9a2b11'):
            print 'SHA512: '+''.join(i)+' '+SHA512hash
{: .language-python}

We let this run for a couple minutes until one hit for each hash
algorithm is found:

    output:
    MD5: aaqidt 46e0109895d6278b858d2161a53d6163
    MD2: afpmqt 757c47d05a0d3effdd7102323912fed7
    SHA512: af9pt3 112cace752c20eaa6cd66ab29a2b1195ffd375a7a0f80a7cf0aa5bb295119d6e9f251f61be772dc848f9a21fdaeabf122128b54d13b2f200fb513a3c1c69caf7
    MD5: ahaelu 6b02a89895d63a26836b34d9e041520d
    SHA512: aick61 fe59ce23a7860e0f87d898849a2b111d938010054ac505bc428eb1ed134f5a337025cfe47be2272585a239fe85d4390e1b86fd1393ddcdd0ec32c549058129cb
    MD2: albkyq 757c47edf09c10f213f8a8028d8b2fa1
    MD5: amlp1i 5546e09895d684095e0993da825520cd
    SHA256: angeca e77a4a14b3946679280530cd08ed8843265b07d7828d411c06359bfc7113bc1c
    MD5: aor7e3 6db1079895d69d81efc0fb41200a8731
    MD2: ao45aq 757c4778d66b2936ec81b46f6f0a41bd
    SHA256: apt877 8084b4b49e86b5b5ab0530cd320762fc21ef339b2d66c1df72ebaf6c6086ed30
    SHA256: arlh5t ca6d3501b11a2b6e910530cd17c3c47a97980097e2ae87f3ceea52af504a6ae7
    MD5: awbzbv 71e2d99895d6492b26585cb718228f82
    SHA1: aww8nt c9f3a96a6c2f845b2b24a61cad2ade37528bca39
    ...

we concatenate an answer for each hash type
`MD2-MD5-SHA1-SHA256-SHA512`, for example:
`afpmqtaaqidtaww8ntangecaaick61`. We enter this in the password box and
get our egg:

![](images/egg_24_qrcode.png)

