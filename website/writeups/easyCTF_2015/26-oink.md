---
layout: writeup
title: Oink
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{atinl4y_easyyay_3noughyay_orfay_ayay_1gpay!}
---
## Challenge

Use the programming interface to solve this problem.

Pig Latin is a "secret" language in which English words are changed to
sound foreign. Its rules are pretty simple:

If the English word begins with a vowel, add "yay" to the end.  
If the English word begins with a consonant, move the first letter to
the end and then add "ay".  
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

    words=open("piglatin1.in","r").readline().rstrip().split()
    piglatin=[]
    for w in words:
       if w[0] in ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']:
          piglatin.append(w+'yay')
       else:
          piglatin.append(w[1:]+w[0]+'ay')
    
    open("piglatin1.out","w").write(' '.join(piglatin)+'\n')
{: .language-python}

