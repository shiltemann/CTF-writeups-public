---
layout: writeup
title: 'Programming 120: The Big Kahuna'
level:
difficulty:
points:
categories: []
tags: []
flag: abctf{28}
---
## Challenge

What's the smallest amount of steps (additions, deletions, and
replacing) it would take to make the string
"massivegargantuanhugeepicginormous" into
"tinysmallmicroscopicinvisible"? Don't guess too many times or we will
disqualify you. Remember to wrap your answer in abctf\{}.

## Solution

This is just the edit distance of two strings.

    # pip install python-Levenshtein
    import Levenshtein as L
    L.distance('massivegargantuanhugeepicginormous', 'tinysmallmicroscopicinvisible')
{: .language-python}

output

    28

