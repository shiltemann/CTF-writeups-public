---
layout: writeup
title: 'Stage 2 Web: Toke'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{jW7_t0K3ns_4Re_nO_p14CE_fOR_53CrE7S}
---
## Challenge

I have a feeling they were pretty high when they made this website...
http://toke.vuln.icec.tf/

## Solution

there was a registration and login option, which gave us a cookie
containing the base64 encoded flag

    jwt_token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmbGFnIjoiSWNlQ1RGe2pXN190MEszbnNfNFJlX25PX3AxNENFX2ZPUl81M0NyRTdTfSIsInVzZXIiOiInIn0.8dpZppOpfKijXcgbpzx0QtVU91xDvCwsRTzc5lCadlE

## Flag

    IceCTF{jW7_t0K3ns_4Re_nO_p14CE_fOR_53CrE7S}

