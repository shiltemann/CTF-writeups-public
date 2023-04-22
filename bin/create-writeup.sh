if [ "$#" -ne 1 ]; then
    echo "usage: create-writeup.sh <filename>"
fi

echo "creating writeup in $1"

echo "---
layout: writeup

title:
level:  # optional, for events that use levels
difficulty:
categories: []
tags: []

flag:

---

## Challenge

## Solution

" > $1
