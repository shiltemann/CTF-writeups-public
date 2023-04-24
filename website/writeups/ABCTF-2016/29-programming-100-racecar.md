---
layout: writeup
title: 'RacecaR'
level:
difficulty:
points: 100
categories: [programming]
tags: []
flag:
---
## Challenge

Aren't Palindromes cool? I certainly think so, which is why I want you
to find the longest palindrome in [this](writeupfiles/palindrome.txt)
file.

## Solution

This is the longest palindrome substring problem

    def longestPalindrome(s):
            l = len(s)
            if l <= 2:
                if (s[0] != s[l-1]): return ''
                else: return s
    
            result = ''
            for i in range(0,l):
                palindrome = SearchPalindrome(s, i, i)
                if len(palindrome) > len(result): result = palindrome
                palindrome = SearchPalindrome(s, i, i+1)
                if len(palindrome) > len(result): result = palindrome
            return result
    
    def SearchPalindrome(string, start, end):
            while(start>=0 and end < len(string) and string[start]==string[end]):
                start -= 1
                end += 1
            return string[start+1:end]
    
    
    text = ''
    with open('palindrome.txt') as f:
        for line in f:
            text += line.rstrip()
    
    print longestPalindrome(text)
{: .language-python}

output

    DbrMrbD
