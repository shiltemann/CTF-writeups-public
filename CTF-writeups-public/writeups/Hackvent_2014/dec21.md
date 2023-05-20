# December 21th: Fair Gambling

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=21](http://hackvent.hacking-lab.com/challenge.php?day=21)


**Hint**

*Break the bank*

**Challenge**

Follow the link and play that game: [http://hackvent.hacking-lab.com/iKlrDE49dCjFi17oDYzs/](http://hackvent.hacking-lab.com/iKlrDE49dCjFi17oDYzs/)


**Solution**

The link leads us to a gambling game


![](images/dec21ss.png)


So the game generates a set of pseudorandom numbers based on a linear congruential generator.
We are given some parameters of the generator and we start with $100, and each bet triples our 
money if we guess the correct number. The numbers range from 0 to 99. If we manage to get $10,000
we win the game and get our flag.

Reading the wikipedia link, we learn that the Linear congruential generator works as follows:


```
A linear congruential generator (LCG) is an algorithm that yields a sequence of pseudo-randomized 
numbers calculated with a discontinuous piecewise linear equation. The method represents one of 
the oldest and best-known pseudorandom number generator algorithms.[1] The theory behind them is 
relatively easy to understand, and they are easily implemented and fast, especially on computer 
hardware which can provide modulo arithmetic by storage-bit truncation.

The generator is defined by the recurrence relation:

    X(n+1) = (a X(n) + c) mod m

where X is the sequence of pseudorandom values, and

    m – the modulus
    a – the multiplier
    c – the increment
    X(0) – the seed (start value)
```

We have been given all parameters except the seed. When we play the game and choose a wrong number, 
we are told what the number was, so we simply play it a handful of times for $1 so we know what the initial
sequence of numbers needs to be, then just try different seeds until we find one with the same first 
numbers, and then generate the next several numbers after that to use to continue betting.

We do this with a small python [script](images/dec21.py):


```python
import math

# X(n+1) = (a X(n) + C ) mod m
# a = multiplier
# C = increment
# m = modulus
# X(0) = seed


a = 1664525
c = 1013904223
m = int(math.pow(2,31))

target=[67,6,61,68,79,54,53,32]

seed=0
while True:
    seed +=1
    candidate=True
    x = seed
    
    for i in range (0,len(target)):
        x = ((a* x+c) % m) 
        if x%100 != target[i]:
            candidate=False
            break
        
    if candidate == True:
         print "possible seed: "+str(seed)
         for i in range (0,25):
             x = ((a* x+c) % m) 
             print x%100

```

The numbers in the list named *target* are the numbers we obtained by manually betting on some numbers
and noting what they should have been. We then run the program to find the seed and generate the next 
set of numbers, after a minute orso we get this output from the program:


```
possible seed: 60440196
19
74
73
12
71
14
17
52
35
30
25
36
99
30
61
88
87
42
53
32
23
10
97
68
63

```

So we go back to the website, and keep betting with these numbers. When we reach 10,000 we 
stop playing and get the following screen:



```
Goodbye! Your final balance was $449599086.

We generated your random number sequence using 60440196 as the initial seed. You can easily 
verify that we didn't cheat you. Maybe you're up for another round?

Awesome! You did it, you really won this $10k! Are you going to buy <image of the bauble> now?
```

![](images/GFHosP0AU8qRxygypqFR.png)

**Flag**

```
HV14-Zbyb-Hv14-8Q77-8ltr-iPpr
```

