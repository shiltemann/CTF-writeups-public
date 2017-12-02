# HackVent 2017

Another edition of Hacking-Lab's annual advent calender CTF. Every day between December 1 and Christmas, a new challenge is released. Solve it on the day of release for maximum points, solve it later (but before the new year) for one point less.

## Overview


```
Title                                    Category  Points  Flag
---------------------------------------- --------- ------- -----------------------------
Dec 1: 5th Anniversary                    Easy      2/1     HV17-5YRS-4evr-IJHy-oXP1-c6Lw
Dec 2: Wishlist                           Easy      2/1     HV17-Th3F-1fth-Pow3-r0f2-is32


Hidden 1:
Hidden 2:
Hidden 3:  robots                         Hidden    1       HV17-bz7q-zrfD-XnGz-fQos-wr2A
Hidden 4:
Hidden 5:

```

There were 5  hidden balls this year.

## Hidden ball 1:

**Solution**

**Nugget**

```
HV17-
```

## Hidden ball 2:

**Solution**

**Nugget**

```
HV17-
```

## Hidden ball 3:

**Solution**  

we check `robots.txt` and see the following message: `We are people, not machines`

so then we check `people.txt`: `What's about akronyms?`

so then we check `humans.txt` and see:

```
All credits go to the following incredibly awesome HUMANS (in alphabetic order):
avarx
DanMcFly
HaRdLoCk
inik
Lukasz
M.
Morpheuz
MuffinX
PS
pyth0n33

HV17-bz7q-zrfD-XnGz-fQos-wr2A

```

whoo, theres a flag!

**Nugget**

```
HV17-bz7q-zrfD-XnGz-fQos-wr2A
```

## Hidden ball 4:

**Solution**

**Nugget**

```
HV17-
```

## Hidden ball 5:

**Solution**

**Nugget**

```
HV17-
```


## Dec 1: 5th Anniversary  
*time to have a look back*

**Challenge**  

![](writeupfiles/HV17-hv16-hv15-hv14.svg)

**Solution**  

Looks like we need the solutions from previous years, good thing I kept writeups

```
2014: HV14-BAAJ-6ZtK-IJHy-bABB-YoMw
2015: HV15-Tz9K-4JIJ-EowK-oXP1-NUYL
2016: HV16-t8Kd-38aY-QxL5-bn4K-c6Lw
```

Putting the fragments together gives our nugget

**Nugget**

```
HV17-5YRS-4evr-IJHy-oXP1-c6Lw
```


## Dec 2: Wishlist  
*The fifth power of two*

**Challenge**  

Something happened to my wishlist, please help me.

[Get the Wishlist](writeupfiles/Wishlist.txt)

**Solution**  

This appears to be repeatedly base-64 encoded

```bash
$ cat Wishlist.txt | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d  | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d |
base64 -d | base64 -d | base64 -d | base64 -d | base64 -d | base64 -d

HV17-Th3F-1fth-Pow3-r0f2-is32%   
```

**Nugget**

```
HV17-Th3F-1fth-Pow3-r0f2-is32
```

## Dec 3: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 4: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 5: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 6: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 7: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 8: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 9: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 10: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 11: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 12: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 13: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 14: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 15: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 16: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 17: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 18: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 19: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 20: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 21: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 22: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 23: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```

## Dec 24: Title  
*hint*

**Challenge**  

**Solution**  

**Nugget**

```
HV17-
```
