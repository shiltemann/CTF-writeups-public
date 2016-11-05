# Hack the Vote 2016

48 hour Election themed CTF in November 2016

**Team:** Galaxians

![](writeupfiles/challenges.png)


## Overview

```
Title                   Category       Points  Flag
----------------------- -------------- ------- ----------------------------          
Sanity                  Vote           1       flag{th3r3_1s_0nly_on3_ch0ic3}
Insanity                Vote           -2      flag{d0nt_w4st3_y0ur_v0t3!}
Sanders Fan Club        Web            100
Voter Registration      Web            200
Just in Time            Web            300
Trump Watch             Web            300
Politician Hosting      Web            500
IRS                     Exploitation   100
Primaries               Exploitation   150
Ballot Return           Exploitation   200
FOX voting simulator    Exploitation   300
Old Tech                Exploitation   300
Tetrump Ultimate        Exploitation   400
ctrscript               Exploitation   500
OracleVote              Exploitation   500
Warp Speed              Forensics      150     flag{1337_ph0t0_5k1ll5}
Electioneering          Forensics      250
Trump like colors       Forensics      250
More Suspicious Traffic Forensics      300
Lockpicking             Forensics      350
Hillary's Emails        Forensics      500
Consul                  Reversing      100
Frontdoor               Reversing      200
The Wall                Reversing      200
APTeaser                Reversing      300
Slowdown                Reversing      300
Trumpervisor            Reversing      400
TOPKEK                  Crypto         50      flag{T0o0o0o0o0P______1m_h4V1nG_FuN_r1gHt_n0W_4R3_y0u_h4v1ng_fun______K3K!!!}
Trump Trump             Crypto         100
Vermatix Supreme        Crypto         100
Boxes of Ballots        Crypto         200
The Best RSA            Crypto         250
BabyHands               Crypto         300
SMTPresident            Crypto         400
```


## Challenge 42: Title
**Challenge**
**Solution**
**Flag**
```
flag
```


## Vote 1: Sanity
**Challenge**  

The flag is flag{th3r3_1s_0nly_on3_ch0ic3}

**Solution**  
Dummy challenge, just enter flag

**Flag**  

```
flag{th3r3_1s_0nly_on3_ch0ic3}
```

## Vote -2: Insanity
**Challenge**
THIS WILL REALLY SUBTRACT TWO POINTS FROM YOUR SCORE
The flag is flag{d0nt_w4st3_y0ur_v0t3!}

**Solution**

Never Trump

**Flag**
```
unsolved
```


## Crypto 50: TOPKEK
**Challenge**

A CNN reporter had only one question that she couldn't get off her mind

    Do we even know, who is this 4 CHAN???

So she set out to find who this 400lb hacker is. During her investigation, she came across this cryptic message on some politically incorrect forum online, can you figure out what it means?

```
KEK! TOP!! KEK!! TOP!! KEK!! TOP!! KEK! TOP!! KEK!!! TOP!! KEK!!!! TOP! KEK! TOP!! KEK!! TOP!!! KEK! TOP!!!! KEK! TOP!! KEK! TOP! KEK! TOP! KEK! TOP! KEK!!!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP! KEK! TOP! KEK!!!!! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!! KEK! TOP! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP! KEK!!!!! TOP!! KEK! TOP! KEK!!! TOP! KEK! TOP! KEK! TOP!! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP! KEK!!! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK!!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP! KEK!! TOP!!! KEK!! TOP! KEK! TOP!!!!! KEK! TOP!!! KEK!! TOP! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK!! TOP!!! KEK! TOP! KEK!! TOP! KEK!!!! TOP!!! KEK! TOP! KEK!!! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP!!! KEK!!! TOP!! KEK!!!!! TOP! KEK! TOP! KEK! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK!! TOP!! KEK! TOP! KEK!!! TOP! KEK! TOP! KEK!! TOP! KEK!!! TOP!! KEK!! TOP!! KEK! TOP! KEK! TOP!!!!! KEK! TOP!!!! KEK!! TOP! KEK!! TOP!! KEK!!!!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP! KEK!!!!! TOP!! KEK! TOP! KEK!!! TOP!!! KEK! TOP!! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK!! TOP! KEK! TOP!! KEK!! TOP!! KEK!! TOP!! KEK! TOP! KEK!! TOP! KEK! TOP!! KEK!! TOP! KEK!!!! TOP! KEK!! TOP! KEK!!!! TOP! KEK!! TOP! KEK!!!! TOP! KEK! TOP!!!!! KEK! TOP!
```
**Solution**

After some exploration, we discover this is binary with

```
KEK=0
TOP=1
! = number of repetitions
```

```python
import binascii

def text_to_bits(text, encoding='utf-8', errors='surrogatepass'):
    bits = bin(int(binascii.hexlify(text.encode(encoding, errors)), 16))[2:]
    return bits.zfill(8 * ((len(bits) + 7) // 8))

def text_from_bits(bits, encoding='utf-8', errors='surrogatepass'):
    n = int(bits, 2)
    return int2bytes(n).decode(encoding, errors)

def int2bytes(i):
    hex_string = '%x' % i
    n = len(hex_string)
    return binascii.unhexlify(hex_string.zfill(n + (n & 1)))

ct="KEK! TOP!! KEK!! TOP!! KEK!! TOP!! KEK! TOP!! KEK!!! TOP!! KEK!!!! TOP! KEK! TOP!! KEK!! TOP!!! KEK! TOP!!!! KEK! TOP!! KEK! TOP! KEK! TOP! KEK! TOP! KEK!!!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP!! KEK! TOP!!!! KEK!! TOP!! KEK!!!!! TOP! KEK! TOP! KEK!!!!! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!! KEK! TOP! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP! KEK!!!!! TOP!! KEK! TOP! KEK!!! TOP! KEK! TOP! KEK! TOP!! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP! KEK!!! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK!!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP! KEK!! TOP!!! KEK!! TOP! KEK! TOP!!!!! KEK! TOP!!! KEK!! TOP! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK!! TOP!!! KEK! TOP! KEK!! TOP! KEK!!!! TOP!!! KEK! TOP! KEK!!! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP!!! KEK!!! TOP!! KEK!!!!! TOP! KEK! TOP! KEK! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK!! TOP!! KEK! TOP! KEK!!! TOP! KEK! TOP! KEK!! TOP! KEK!!! TOP!! KEK!! TOP!! KEK! TOP! KEK! TOP!!!!! KEK! TOP!!!! KEK!! TOP! KEK!! TOP!! KEK!!!!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK! TOP! KEK!!!!! TOP!! KEK! TOP! KEK!!! TOP!!! KEK! TOP!! KEK!!! TOP!! KEK!!! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP!! KEK!! TOP!! KEK!! TOP!!! KEK! TOP! KEK! TOP! KEK! TOP!! KEK! TOP!!! KEK!! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK! TOP!!!!! KEK! TOP! KEK!! TOP! KEK! TOP!! KEK!! TOP!! KEK!! TOP!! KEK! TOP! KEK!! TOP! KEK! TOP!! KEK!! TOP! KEK!!!! TOP! KEK!! TOP! KEK!!!! TOP! KEK!! TOP! KEK!!!! TOP! KEK! TOP!!!!! KEK! TOP!"

pt=''
for i in ct.split():
    n = i.count('!')
    bit='1'
    if('KEK') in i:
        bit='0'
    pt+=bit*n

print pt
print text_from_bits(pt)   
```

This outputs:

```
0110011001101100011000010110011101111011010101000011000001101111001100000110111100110000011011110011000001101111001100000101000001011111010111110101111101011111010111110101111100110001011011010101111101101000001101000101011000110001011011100100011101011111010001100111010101001110010111110111001000110001011001110100100001110100010111110110111000110000010101110101111100110100010100100011001101011111011110010011000001110101010111110110100000110100011101100011000101101110011001110101111101100110011101010110111001011111010111110101111101011111010111110101111101001011001100110100101100100001001000010010000101111101
flag{T0o0o0o0o0P______1m_h4V1nG_FuN_r1gHt_n0W_4R3_y0u_h4v1ng_fun______K3K!!!}
```

**Flag**
```
flag{T0o0o0o0o0P______1m_h4V1nG_FuN_r1gHt_n0W_4R3_y0u_h4v1ng_fun______K3K!!!}
```


## Challenge 42: Title
**Challenge**
Our Trump advertising campaign is incredible, it's skyrocketing! It's astronomical! Wait stop!! SLOW DOWN!!!

**Solution**
The image was clearly chewed up and re-assembled with different offsets. Splitting into strips 8 pixels wide, and shifting them down + right 8 pixels (based on manually aligning the first two slices in gimp), made this clear:

![](./writeupfiles/warp_speed/doc-1.png)

Once this was done, it was only a matter of cutting the three sections, and overlaying them. The middle section needed a -8x0 offset though, in order to line up correctly:

![](./writeupfiles/warp_speed/out.png)

Obviously not perfect, but good-enough for our purposes.

**Flag**
```
flag{1337_ph0t0_5k1ll5}
```
