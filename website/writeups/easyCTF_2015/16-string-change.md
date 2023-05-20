---
layout: writeup
title: String Change
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{changing_things_up_once_in_a_while_is_gooood_for_you}
---
## Challenge

Use the programming interface to complete this task. Given an array of 5
numbers, change every nth character, with n being the value of the first
number of the array and the first letter of the string as the 1st
character, of a string and move its value up by one (a turns into b, z
turns into a). Repeat this for the rest of the numbers of the array and
return the changed string. Do this for all the strings. Be careful to
keep the original capitalization!

For example: \[2,3,7,5,4\] and oTerNmIWxGqaaV would become
oTfsPnKXzHsadV

ID: string-change  
Input: Read the input from a file called string-change.in that contains
a string of: a list of 5 numbers separated by commas followed by a
linebreak, and then a string of random characters.  
Output: The string changed according to the values in the list, written
to a file called string-change.out.

You already know this, but don't forget to end your output with a
newline.

## Solution

    f=open("string-change.in")
    fout=open("string-change.out",'w+')
    
    numbers= f.readline().rstrip('\n').split(',')
    mystring= list(f.readline().rstrip('\n'))
    
    for num in numbers:
        n = int(num)-1
        for i in xrange(n,len(mystring),n+1):
            if mystring[i]=='z':
               mystring[i]='a'
            elif mystring[i]=='Z':
               mystring[i]='A'
            else:
               mystring[i]=chr(ord(mystring[i])+1)
    
    fout.write(''.join(mystring)+'\n')
    f.close()
    fout.close()
{: .language-python}

