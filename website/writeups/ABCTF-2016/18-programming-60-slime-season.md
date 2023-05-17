---
layout: writeup
title: 'Slime Season'
level:
difficulty:
points: 60
categories: [programming]
tags: []
flag:
---
## Challenge

I only pay in coins because I'm hipster, but I forgot to bring my
nickels today! But I really want to buy this elite gaming computer.
What's the smallest amount of coins you need to make $1,827.43 using
quarters, dimes, and pennies.

## Solution

this is the change making problem, we can solve it with a dynamic
programming approach

    def _get_change_making_matrix(set_of_coins, r):
         m = [[0 for _ in range(r + 1)] for _ in range(len(set_of_coins) + 1)]
         for i in range(r + 1):
             m[0][i] = i
         return m
    
    def change_making(coins, n):
    
         m = _get_change_making_matrix(coins, n)
    
         for c in range(1, len(coins) + 1):
             for r in range(1, n + 1):
                 # Just use the coin coins[c - 1].
                 if coins[c - 1] == r:
                     m[c][r] = 1
                 elif coins[c - 1] > r:
                     m[c][r] = m[c - 1][r]
                 else:
                     m[c][r] = min(m[c - 1][r], 1 + m[c][r - coins[c - 1]])
         return m[-1][-1]
    
    print change_making((1,10,25),182743)
{: .language-python}

output:

    7315
