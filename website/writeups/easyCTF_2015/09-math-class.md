---
layout: writeup
title: Math Class
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{have_y0u_had_enough_of_math_in_sk0ol_yet}
---
**Challenge**  
Use the programming interface to complete this task. You'll be given a
math expression, such as add 1 2 or subtract 5 3, where you will perform
the operations 1+2 and 5-3, respectively.

ID: math-class  
Input: An expression in the form of operation operand1 operand2,
separated by spaces. Read input from math-class.in.  
Output: The absolute value of the evaluated expression. Your output
should always be a positive integer.

There are only 2 different possible operations, addition and
subtraction, and all operands will be integer values between 1 and 1000.
As always, remember to end your program with a newline.  
## Solution

    fout=open("math-class.out",'w+')
    expressions= open("math-class.in").readline().rstrip('\n').split(',')
    
    count=0
    for e in expressions:
        ex=e.split(' ')
        if count:
            fout.write(',')
        if ex[0]=="add":
            fout.write(str(abs(int(ex[1])+int(ex[2]))))
        else:
            fout.write(str(abs(int(ex[1])-int(ex[2]))))
        count+=1
    
    fout.write('\n')
    fout.close()
{: .language-python}

