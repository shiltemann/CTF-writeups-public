---
layout: writeup
title: Oink Oink
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{th0se_pesky_capit4ls_were_a_pa1n,_weren't_they?}
---
## Challenge

Use the programming interface to solve this problem.

Pig Latin is a "secret" language in which English words are changed to
sound foreign. Its rules are pretty simple:

    If the English word begins with a vowel, add "yay" to the end.
    If the English word begins with a consonant, move the first letter to the end and then add "ay".

For example, "Apples are Delicious" becomes "Applesyay areyay
eliciousDay". Note: if you know a version of Pig Latin that uses a
variation of rules different from the above rules, please use the above
rules for this problem.

Your job is to translate English input to Pig Latin output. For this
exercise, keep capitalized letters capitalized, even when they are
moved.

ID: piglatin1  
Input: A sentence in English.  
Output: The translation in Pig Latin.

Read the input from a file called piglatin1.in that's in the current
working directory, and then write your output to a file called
piglatin1.out

## Solution

    words = open('piglatin2.in', 'r').readline().rstrip().split()
    english = []
    
    for w in words:
      if w[-3:] == 'yay':
        english.append(w[:-3])
      else:
        if w[0].isupper():
          english.append(chr(ord(w[-3:-2])-32) + w[:-3].lower())
        else:
          english.append(w[-3:-2] + w[:-3])
    open('piglatin2.out', 'w').write(' '.join(english)+'\n')
{: .language-python}

