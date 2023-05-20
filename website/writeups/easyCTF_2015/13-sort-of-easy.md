---
layout: writeup
title: Sort-of-Easy
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{sorting_is_as_easy_as_3_2_1!}
---
**Challenge**  
Use the programming interface to complete this task.

Input: A list of numbers, separated by commas. Ex: 3,28,9,17,5  
Output: The list sorted from largest to smallest, separated by commas.
Ex: 28,17,9,5,3

Read the input from a file called sorting-job.in that's in the current
working directory, and then write your output to a file called
sorting-job.out.

## Solution

    
    nums= open("sorting-job.in","r").readline().rstrip().split(",")
    nums= [int(n) for n in nums]
    nums = sorted(nums)
    nums = nums[::-1]
    
    open("sorting-job.out","w").write( ','.join(str(n) for n in nums)+'\n' )
{: .language-python}

