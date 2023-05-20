---
layout: writeup
title: 'Dec 7: TrivialKRYPTO 1.42'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-bxuh-b3ep-1PCU-b9ft-CgVu

---

## Challenge

*You think you need the password?*

Today's present is encrypted. Luckily Santa did not use Kryptochef's
KRYPTO 2.0 so there might be a slight chance of recovering it?

## Solution

The button leads to a site with a password field and this in the source:

    <title>TrivialCrypt</title>

    <input type="text" id="pass" placeholder="password" /><input type="button" id="decrypt" value="decrypt" />
    <p id="out">enter password ...</p>
    <script>
    s3cr3t=[2155568001,3847164610,2684356740,2908571526,2557362074,2853440707,3849194977,3171764887];
    document.getElementById('decrypt').onclick = function() {
    var pass = document.getElementById('pass').value;

    var s="";
    for(var i=0;i<s3cr3t.length;i++) {
    var pp="";
    for(var p = (s3cr3t[i] ^ crc32(pass)); p>0; p>>=8) {
    pp = String.fromCharCode(p&0xFF)+pp;
    }
    s+=pp;
    }

    var out = document.getElementById('out');
    if(crc32(s) == 2343675265){
    out.className = "right";
    out.firstChild.nodeValue = s;
    }else{
    out.className = "wrong";
    out.firstChild.nodeValue = "wrong password ...";
    }
    }

    var makeCRCTable = function(){
        var c;
        var crcTable = [];
        for(var n =0; n < 256; n++){
            c = n;
            for(var k =0; k < 8; k++){
                c = ((c&1) ? (0xEDB88320 ^ (c >>> 1)) : (c >>> 1));
            }
            crcTable[n] = c;
        }
        return crcTable;
    }

    var crc32 = function(str) {
        var crcTable = window.crcTable || (window.crcTable = makeCRCTable());
        var crc = 0 ^ (-1);

        for (var i = 0; i < str.length; i++ ) {
            crc = (crc >>> 8) ^ crcTable[(crc ^ str.charCodeAt(i)) & 0xFF];
        }

        return ((crc&0xFFFFFFFF) ^ (-1)) >>> 0;
    }

    </script>
{: .language-html}

This was re-organised into a simplified javascript only file which can
be run with node:

    var makeCRCTable = function(){
        var c;
        var crcTable = [];
        for(var n =0; n < 256; n++){
            c = n;
            for(var k =0; k < 8; k++){
                c = ((c&1) ? (0xEDB88320 ^ (c >>> 1)) : (c >>> 1));
            }
            crcTable[n] = c;
        }
        return crcTable;
    }

    var crc32 = function(str) {
        var crcTable = crcTable || (crcTable = makeCRCTable());
        var crc = 0 ^ (-1);

        for (var i = 0; i < str.length; i++ ) {
            crc = (crc >>> 8) ^ crcTable[(crc ^ str.charCodeAt(i)) & 0xFF];
        }

        return ((crc&0xFFFFFFFF) ^ (-1)) >>> 0;
    }

    var secret = [
        2155568001,
        3847164610,
        2684356740,
        2908571526,

        2557362074,
        2853440707,
        3849194977,
        3171764887
    ];

    var pass = "aaa";

    var s="";
    for(var i=0; i<secret.length; i++) {
        var pp="";
        for(var p = (secret[i] ^ crc32(pass)); p>0; p>>=8) {
            pp = String.fromCharCode(p&0xFF)+pp;
        }
        console.log(pp);
        s+=pp;
    }

    if(crc32(s) == 2343675265){
        console.log("Right " + s);
    }else{
        console.log("Wrong");
    }
{: .language-javascript}

It seems very plausible that each of these numbers corresponds to some
portion
of the output key. When crc32(pass) is negative, it fails completely, so

crc32(pass) needs to be positive.

The `for(var p...)` portion, if crc32(pass) is positive, will loop
exactly 4
times. So this means that each of those numbers coresponds to 4
characters? We
just need to find cases when a single input value produces outputs all
within
ascii range?

Hmm, that simplifies it a bit. E.g. the first 8 bits XOR first 8 bits of

crc32(pass), for all values of secret, must be within ascii. I've taken
a stab
at this in 7th-solver.js, but I think there are bugs.

The hint suggests maybe we don't need to discover the password, we take
another
look and indeed, each part of the secret would appear to encode 4
characters of the nugget.
We know what the first four characters should be, so we have

`secret[0] ^ crc32(pass) = "HV16"`

which means

`crc32(pass) = secret[0] ^ "HV16"`

so we can compute the value `crc32(pass)` directly, and we just need to
xor this
with each part of the secret with this to get the rest of the nugget!

    import binascii

    def int2ascii(i):
        hex_string = '%x' % i
        n = len(hex_string)
        return binascii.unhexlify(hex_string.zfill(n + (n & 1)))


    secret= [2155568001,3847164610,2684356740,2908571526,2557362074,2853440707,3849194977,3171764887]

    crcpass = int("HV16".encode("hex"),16) ^ secret[0]

    # test that we indeed get the string "HV16" back
    nuggetpiece = secret[0] ^ crcpass
    print int2bytes(nuggetpiece)

    # yay! now do it for all parts of the secret
    nugget=''
    for s in secret:
        nugget+=int2ascii(s ^ crcpass)

    print nugget
{: .language-python}


