---
layout: writeup
title: 'Dec 16: Marshmallows'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
type: "nomnomnom marhshmallow nomnomnom muffin%x was here"

**Challenge**
There's this guy Randy, he loves marshmallows and programming in python
and C.
Prove him by hacking his server, that it's not a good idea to code if
you had too many marshmallows.

nc challenges.hackvent.hacking-lab.com 1033

**Solution**
Following their hint, I connect and type the string:

    > 1
    [+] Please give me some marshmallows: nomnomnom marhshmallow nomnomnom muffin%x was here
    nomnomnom marhshmallow nomnomnom muffin968900 was here
    [+] Menu

Which naturally leads to asking:

    [+] Please give me some marshmallows: %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    %x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x%x
    9689001c9aed885c8a785c8a77532dd3ab929430b9293e01b21d40cb929378b92937cb929390010b9292e01c2a0340b9294300b9294e0b92923008b9
    2938cb929388b929390111b929430b929540b18a7400b929480b929430b9294e00b9294101b9295401b42f12d1b63883042b9294602b9294601b4317
    3c0206a120b9295401b42d56eb9294601b42f03f1b638830206a120206a140006e0b929460b9294501ea422001c2a034001101b9294501fef3b81b65
    0a481c60a7602b9293f01c894e5800b18a7400b9295c01b17643001b648c88001b666ee81b42f6a31c2a0340001ea4220001b6a9688206aa10696373
    611ca136c01ca1ac18206aa101b648c881b650a481f0dcf81c9aab591b648c881f0db781b1764301ea4220048a487000001c2d0f891c9aaa281f0dcf
    01c9fca981f0dcf001c93d3001c9401e0121f0dcf0101f0dcf01c9aaa561f0db781c961d8801ea42201f0dcf81f604101ea40701c9401e001c9401e0
    1c9401e0509d991c916ea01c9a73c881c9401e01f0db781f0dcf01ea42200048e45b1ea4070001edd5d0000001c9401e01f72a405595b31015238ce0
    1edd5d01c8a34881c9401e01c9a73c81c9a73c8011f72a4048f15b0000011f72a405597301c8a07c01ea40701f048d04793c5b92998001ea40701c84
    54d51c9a73c81c9a73c81c9674381c9a73c81c8a22901c9a64a81f048d01c8a22ae14797a2b929980ffffffffb9299800b9221231c8a22901f048d01
    c8a071001ea40e011c8a227005bfaa006e1f048d0001c3c9d0101c3c9df6fd04815b0f181c400840100085854103c3ad01a8e584e04b30584e06ca1f
    355ed1000031ea40401ea401001ea4070347d9f41ea40702b929aa00056d55cb929b80001c26df450b929b88047d8c0041b709aa56d55cb929b80007
    4d709aafc4d09aa0005c13f0b929b8830056d55cb929b80056d585b929b781c3b92ae93b92ae9bb92ae9e0b92aec1b92aed7b92aef3b92af0bb92af2
    0b92af3ab92af502062a80b92af82b92afc4b92afd4b92afda021b9bd00010fabfbff61000116434000404385971c82f00080956d55cb3e8c3e8d3e8
    e3e817019b929d491fb92afe7fb929d590008a74c3002920dbaa36387884000000000000000000000000000000000000000000000000000000000000
    000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
    0000000000000000000


