---
layout: writeup
title: Looking for Letters
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{filtering_the_#s_out}
---
**Challenge**  
Use the programming interface to complete this task.

Input: A string containing alphanumeric characters.  
Output: A string containing only the letters of the input.

Read the input from a file called looking-for-letters.in that's in the
current working directory, and then write your output to a file called
looking-for-letters.out.

## Solution

    inputstring= open("looking-for-letters.in").readline().rstrip('\n')
    
    result = ''.join([i for i in inputstring if not i.isdigit()])
    
    open("looking-for-letters.out",'w').write(result+'\n')
{: .language-python}

