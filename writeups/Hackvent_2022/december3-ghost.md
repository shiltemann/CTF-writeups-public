---
layout: writeup

title: "Dec 3: gh0st"
level:  # optional, for events that use levels
difficulty: easy
categories: []
tags: []

flag: HV22{nUll_bytes_st0mp_cPy7h0n}

---

## Challenge

The elves found this Python script that Rudolph wrote for Santa, but it's behaving very strangely. It shouldn't even run at all, and yet it does! It's like there's some kind of ghost in the script! Can you figure out what's going on and recover the flag?

[gh0st.py](writeupfiles/dec3/gh0st.py)


## Solution

The code is:

```python
#!/usr/bin/env python3.7

import random
import sys


if len(sys.argv) != 2:
    print(f'''usage: {sys.argv[0]} flag''')
    sys.exit()
    print('''Things are not what they seem?''')
# only one in a million shall pass
if random.randrange(1000000):
   sys.exit()
# this isn't going to work
print(''')#%^$&*(#$%@^&*(#@!''')
print('''Nice job getting lucky there! But did you get the flag?''')

# Santa only wants every third line!
song =  """You know Dasher, and Dancer, and"""
#song += """#Prancer, and Vixen,"""
#song += """#Comet, and Cupid, and"""
song += """Donder and Blitzen"""
#song += """#But do you recall"""
#song += """#The most famous reindeer of all"""
song += """Rudolph, the red-nosed reindeer"""
#song += """#had a very shiny nose"""
#song += """#and if you ever saw it"""
song += """you would even say it glows."""
#song += """#All of the other reindeer"""
#song += """#used to laugh and call him names"""
song += """They never let poor Rudolph"""
#song += """#play in any reindeer games."""
#song += """#Then one foggy Christmas eve"""
song += """Santa came to say:"""
#song += """    #Rudolph with your nose so bright,"""
#song += """    #won't you guide my sleigh tonight?"""
song += """Then all the reindeer loved him"""
#song += """#as they shouted out with glee,"""
#song += """#Rudolph the red-nosed reindeer,"""
song += """you'll go down in history!"""

print(song)
flag = list(map(ord, sys.argv[1]))
correct = [17, 55, 18, 92, 91, 10, 38, 8, 76, 127, 17, 12, 17, 2, 20, 49, 3, 4, 16, 8, 3, 58, 67, 60, 10, 66, 31, 95, 1, 93]

for i,c in enumerate(flag):
    flag[i] ^= ord(song[i*10 % len(song)])
    #print(flag[i],"-", flag[i]^correct[i])

for i,c in enumerate(correct):
    correct[i] ^= ord(song[i*10 % len(song)])
    print(correct[i],"-", chr(correct[i]))

print("".join([chr(a) for a in correct]))

if all([c == f for c,f in zip(correct, flag)]):
    print('''Congrats!''')
else:
    print('''Try again!''')
```


So the the list `correct` encodes the flag. Luckily it is XOR based, so easy to reverse. We can just add a bit of code to make it give us the flag.

We also see some sneaky null bytes inside all the strigs when we open with Vim:

![](writeupfiles/dec3/nullbytes.png)

But these won't really affect us since we're making the program compute the answer for us.

Here is the code we add near the bottom of the file:


```python
#copy the existing function, just use correct instead of flag, since it's XOR
for i,c in enumerate(correct):
    correct[i] ^= ord(song[i*10 % len(song)])
    print(correct[i],"-", chr(correct[i]))

# print out the flag all at once
print("".join([chr(a) for a in correct]))
```

And this gives us the flag!

```bash
$ python3 gh0st.py aaa
Nice job getting lucky there! But did you get the flag?
72 - H
86 - V
50 - 2
50 - 2
123 - {
110 - n
85 - U
108 - l
108 - l
95 - _
98 - b
121 - y
116 - t
101 - e
115 - s
95 - _
115 - s
116 - t
48 - 0
109 - m
112 - p
95 - _
99 - c
80 - P
121 - y
55 - 7
104 - h
48 - 0
110 - n
125 - }
HV22{nUll_bytes_st0mp_cPy7h0n}
Try again!
```

