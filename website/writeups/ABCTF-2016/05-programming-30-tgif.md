---
layout: writeup
title: 'TGIF'
level:
difficulty:
points: 30
categories: [programming]
tags: []
flag: ABCTF{194}
---
## Challenge

Friday is the best day of the week, and so I really want to know how
many Fridays there are in [this](writeupfiles/date.txt) file. But, with
a twist. I want to know how many Fridays there are one year later than
each date.

## Solution

    import datetime
    count = 0
    with open("date.txt") as f:
        for line in f:
            l2=line.strip().split()
            l2[2]=str(int(l2[2])+1)
            line=' '.join(l2)
            try:
                day = datetime.datetime.strptime(line, '%B %d, %Y').strftime('%A')
                if day == "Friday":
                    count+=1
            except:
                pass
    print count
{: .language-python}

output: `194`
