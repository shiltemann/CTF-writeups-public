---
layout: writeup
title: 'General Skills 125: grep 2'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{grep_r_and_you_will_find_8eb84049}
---
## Challenge

This one is a little bit harder. Can you find the flag in
`/problems/grep-2_2_413a577106278d0711d28a98f4f6ac28/files`  
on the shell server? Remember, grep is your friend.

## Solution

    $ cd /problems/grep-2_2_413a577106278d0711d28a98f4f6ac28/files
    $ ls
    files0  files1  files2  files3  files4  files5  files6  files7  files8  files9
    $ grep -r "picoCTF"
    files2/file16:picoCTF{grep_r_and_you_will_find_8eb84049}
{: .language-bash}

