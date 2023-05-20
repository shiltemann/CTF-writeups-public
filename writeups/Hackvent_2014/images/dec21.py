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
