---
layout: writeup
title: Hidden 1 - It’s a secret!
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{it_is_always_worth_checking_everywhere_and_congratulations,_you_have_found_a_hidden_flag}
---
## Description

Who knows where this could be hidden... Only the best of the best shall
find it!

## Solution

In the [day 3 challenge](), there were 100 files in the zipfile that we
didn't use, so we went back to see if there was more to be found there..

We decrypt all these files, and see they are b64 strings

    $ for i in {00..99};
    do
      bkcrack -C dec3_package2.zip -c "00${i}.bin" -k 2445b967 cfb14967 dceb769b -d 00${i}_decrypt.bin;
      bkcrack-tools/inflate.py < 00${i}_decrypt.bin > 00${i}_deflate.bin;
      cat 00${i}_deflate.bin | base64 -d > 00${i}_b64.bin;
    done
    
    $ grep -r HV20 00*_b64.bin
    Binary file 0042_b64.bin matches
    
    $ cat 0042_b64.bin
    ;>>>>   HV20{it_is_always_worth_checking_everywhere_and_congratulations,_you_have_found_a_hidden_flag}   <<<<
{: .language-bash}

