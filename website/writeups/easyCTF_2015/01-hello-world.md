---
layout: writeup
title: Hello World!
level:
difficulty:
points:
categories: []
tags: []
flag: easyctf{welc0me_two_easyCtf}

---

## Challenge
Use the programming interface to complete this task. Print the line
*Hello, EasyCTF!* to a file called *hello-world.out*. For this problem
and for future problems, make sure your program ends with a newline.
Good luck!

## Solution

    open("hello-world.out","w").write("Hello, EasyCTF!\n")
{: .language-python}

