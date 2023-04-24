if [ "$#" -ne 1 ]; then
    echo "usage: create-writeup.sh <filename>"
fi

echo "creating writeup in $1"

echo "---
layout: writeup

title:
level:          # optional, for events that use levels (like HackyEaster)
difficulty:     # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

## Solution

" > $1
