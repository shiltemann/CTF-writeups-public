---
layout: writeup
title: Sum It!
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{'twas_sum_EZ_programming,_am_I_rite?}
---
**Challenge**  
Use the programming interface to complete this task. You'll be given a
list of numbers.

Input: A list of numbers, separated by commas.  
Output: The sum of the numbers.

Read the input from a file called addition.in that's in the current
working directory, and then write your output to a file called
addition.out.

## Solution

    mytotal= open("addition.in").readline().rstrip('\n').replace(',','+')
    grandtotal = eval(mytotal)
    
    open("addition.out",'w').write(str(grandtotal)+'\n')
{: .language-python}

