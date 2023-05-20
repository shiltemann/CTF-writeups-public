---
layout: writeup
title: If Logic
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{is_it_hi_or_hey_or_something_else}
---
## Challenge

    Use the programming interface to complete this task. You'll be given a list of numbers.
    
    Input: A list of numbers, separated by commas.
    Output: Print hi if the number is 0-50 (inclusive), hey if the number is 51-100 (inclusive), and hello if anything else. Each greeting should have a linebreak after it.
    
    Read the input from a file called if-logic.in that's in the current working directory, and then write your output to a file called if-logic.out.

## Solution

    numbers=open("if-logic.in","r").readline().rstrip().split(",")
    fout=open("if-logic.out","w+")
    
    for n in numbers:
        n=int(n)
        if n <= 50 and n >=0:
            fout.write("hi")
        elif n > 50 and n <=100:
            fout.write("hey")
        else:
            fout.write("hello")
        fout.write("\n")
    
    fout.close()
{: .language-python}

