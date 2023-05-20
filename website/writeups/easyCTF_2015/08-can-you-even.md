---
layout: writeup
title: Can You Even??
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{?v=8ruJBKFrRCk}
---
**Challenge**  
Use the programming interface to complete this task. You'll be given a
list of numbers.

Input: A list of numbers, separated by commas.  
Output: The number of even numbers.

Read the input from a file called can-you-even.in that's in the current
working directory, and then write your output to a file called
can-you-even.out.  
## Solution

    numbers= open("can-you-even.in").readline().rstrip('\n').split(',')
    
    evencount=0
    for n in numbers:
        if not int(n)%2:
            evencount += 1
    
    open("can-you-even.out",'w').write(str(evencount)+'\n')
{: .language-python}

