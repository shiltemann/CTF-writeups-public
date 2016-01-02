import itertools
import string
from collections import Counter
import pickle


message='iw, hu, fv, lu, dv, cy, og, lc, gy, fq, od, lo, fq, is, ig, gu, hs, hi, ds, cy, oo, os, iu, fs, gu, lh, dq, lv, gu, iw, hv, gu, di, hs, cy, oc, iw, gc'



'''
bytwycju + yzvyjjdy ^ vugljtyn + ugdztnwv | xbfziozy = bzuwtwol
    ^         ^          ^          ^          ^
wwnnnqbw - uclfqvdu & oncycbxh | oqcnwbsd ^ cgyoyfjg = vyhyjivb
    &         &          &          &          &
yzdgotby | oigsjgoj | ttligxut - dhcqxtfw & szblgodf = sfgsoxdd
    +         +          +          +          +
yjjowdqh & niiqztgs + ctvtwysu & diffhlnl - thhwohwn = xsvuojtx
    -         -           -         -          -
nttuhlnq ^ oqbctlzh - nshtztns ^ htwizvwi + udluvhcz = syhjizjq
    =         =           =         =          =         
fjivucti   zoljwdfl   sugvqgww   uxztiywn   jqxizzxq

operator precedence:
  +/-
  &
  ^
  |


yn ^ (xh & (ut + su - ns)) = ww
'''

def getDigit(letters,trans):
    return int(letters.translate(trans))
    
    
letters = 'bcdfghijlnoqstuvwxyz'
digits = list('01234567890123456789')

missing = 'aekmpr'

perms = itertools.permutations(digits)

b,c,d,f,g,h,i,j,l,n,o,q,s,t,u,v,w,x,y,z=(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)

def check():

   return True
    
    
'''
u ^ (w & (y + h - q) = i
y ^ (u & (j + s - h) = l
n ^ (h & (t + u - s) = w
v ^ (d & (w + l - i) = n
y ^ (g & (f + n - z) = q


[(u + y) ^ (n + v)] | y = l
[(w - u) & h] | (d ^ g) = b
y | j | [(t - w) & f]   = d
h & (s + u) & (l - n)   = x
q ^ (h - s) ^ (i + z)   = q   => (h-s) ^ (i+z) == 0 => h-s == i+z

----------

q ^ (h - s) ^ (i + z)   = q   => (h-s) ^ (i+z) == 0 => h-s == i+z
y ^ (u & (j + s - h) = l
[(u + y) ^ (n + v)] | y = l
h & (s + u) & (l - n)   = x

u ^ (w & (y + h - q) = i88
'''
savefile='hiszjulynxvwt.pkl2'
dumpfile = 'bladump3'
try: 
    with open(dumpfile, 'rb') as f:
        hiszjulynxvwtdbf = pickle.load(f)

except:
    # q ^ (h - s) ^ (i + z)   = q   => (h-s) ^ (i+z) == 0 => h-s == i+z
    hisz=[]
    for h in range(0,10):
        for i in range(0,10):
            for s in range(0,10):
                for z in range(0,10):
                    for q in range(1,10):
                        if (q ^ (h-s) ^(i+z))&7 == q&7 :
                            counter = Counter([h,i,s,z])
                            if counter.most_common(1)[0][1] <= 2 and check():
                                hisz.append([h,i,s,z])
    
    
    print len(hisz)
    
    
    
    # y ^ (u & (j + s - h) = l
    hiszjuly = []
    for tryme in hisz:
        h,i,s,z = tryme
        for j in range(0,10):
            for l in range(0,10):
                for y in range(0,10):
                    for u in range(0,10):
<<<<<<< HEAD
                        if (y ^ (u & (j+s-h) ))&15 == l:
=======
                        if (y ^ (u & (j+s-h) ))&7 == l&7:
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                            counter = Counter([h,i,s,z,j,u,l,y])
                            if counter.most_common(1)[0][1] <= 2 and check():
                                hiszjuly.append([h,i,s,z,j,u,l,y])
                            
                            
    print len(hiszjuly)                      
    
    
    # h & (s + u) & (l - n)   = x       
    hiszjulynx=[]
    for tryme in hiszjuly:
        h,i,s,z,j,u,l,y = tryme
        for x in range(0,10):
            for n in range(0,10):
<<<<<<< HEAD
                if ( h& (s+u) & (l-n) )&15 == x:
=======
                if ( h& (s+u) & (l-n) )&7 == x&7:
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                    counter = Counter([h,i,s,z,j,u,l,y,n,x])
                    if counter.most_common(1)[0][1] <= 2 :
                        hiszjulynx.append([h,i,s,z,j,u,l,y,n,x])
                
    print len(hiszjulynx)    
    
    
    
    #yn ^ (xh & (ut + su - ns)) = ww
    hiszjulynx2=[]
    for tryme in hiszjulynx:
        h,i,s,z,j,u,l,y,n,x = tryme
        if ((y*10+n) ^ ( (x*10+h) & ((u*10+t)+(s*10+u)-(n*10+s)) )  )&15  == (w*10+w)&15:
                    counter = Counter([h,i,s,z,j,u,l,y,n,x])
                    if counter.most_common(1)[0][1] <= 2 and check():
                        hiszjulynx2.append([h,i,s,z,j,u,l,y,n,x])
                        
    print len(hiszjulynx2)
                    
    # [(u + y) ^ (n + v)] | y = l
    hiszjulynxv=[]
    for tryme in hiszjulynx2:
        h,i,s,z,j,u,l,y,n,x = tryme
<<<<<<< HEAD
        for x in range(0,10):
                if (((u+y) ^ (n+v)) | y)&15 == l:
=======
        for v in range(0,10):
                if (((u+y) ^ (n+v)) | y)&7 == l&7:
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                    counter = Counter([h,i,s,z,j,u,l,y,n,x,v])
                    if counter.most_common(1)[0][1] <= 2 and check():
                        hiszjulynxv.append([h,i,s,z,j,u,l,y,n,x,v])
                
    print len(hiszjulynxv)         
      
    #n ^ (h & (t + u - s) = w
    hiszjulynxvwt=[]
    for tryme in hiszjulynxv:
        h,i,s,z,j,u,l,y,n,x,v = tryme
        for w in range(0,10):
            for t in range(1,10):
<<<<<<< HEAD
                if ( n ^(h&(t+u-s)) )&15 == w :
=======
                if ( n ^(h&(t+u-s)) )&7 == w&7 :
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                    #and ( y ^(x&(u+x-n)) )%10 == w and ( y ^(w&(z+j-t)) )%10 == j and ( t ^(w&(x+h-z)) )%10 == i and ( j ^(q&(j+z-t)) )%10 == w :
                    counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t])
                    if counter.most_common(1)[0][1] <= 2 and check():
                        hiszjulynxvwt.append([h,i,s,z,j,u,l,y,n,x,v,w,t])
                
    print len(hiszjulynxvwt)   
    
    #with open(savefile,'wb') as f:
    #    pickle.dump(hiszjulynxvwt, f , pickle.HIGHEST_PROTOCOL)


    print len(hiszjulynxvwt)   
    
    
    #v ^ (d & (w + l - i) = n
    hiszjulynxvwtd=[]
    for tryme in hiszjulynxvwt:
        h,i,s,z,j,u,l,y,n,x,v,w,t = tryme
        for d in range(0,10):
<<<<<<< HEAD
            if ( v ^(d&(w+l-i)) )&15 == n  :
=======
            if ( v ^(d&(w+l-i)) )&7 == n&7  :
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d])
                if counter.most_common(1)[0][1] <= 2 and check():
                    hiszjulynxvwtd.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d])
                    
    print len(hiszjulynxvwtd)   
    
    
    
    #[(w - u) & h] | (d ^ g) = b
    hiszjulynxvwtdb=[]
    for tryme in hiszjulynxvwtd:
        h,i,s,z,j,u,l,y,n,x,v,w,t,d = tryme
        for b in range(0,10):
<<<<<<< HEAD
            if ( ((w-u) &h)|(d^g) )&15 == b  :
=======
            if ( ((w-u) &h)|(d^g) )&7 == b&7  :
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b])
                if counter.most_common(1)[0][1] <= 2 and check():
                    hiszjulynxvwtdb.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b])
                    
    print len(hiszjulynxvwtdb) 
    
    
    #y | j | [(t - w) & f]   = d
    hiszjulynxvwtdbf=[]
    for tryme in hiszjulynxvwtdb:
        h,i,s,z,j,u,l,y,n,x,v,w,t,d,b = tryme
        for f in range(0,10):
<<<<<<< HEAD
            if ( y|j|((t-w)&f) )&15 == d  :
=======
            if ( y|j|((t-w)&f) )&7 == d&7  :
>>>>>>> f3dbfbbe58325e2568f478ea640c34a3077fbaad
                counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f])
                if counter.most_common(1)[0][1] <= 2 and check():
                    hiszjulynxvwtdbf.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f])
                    
    print len(hiszjulynxvwtdbf) 
    
    with open(dumpfile,'wb') as f:
            pickle.dump(hiszjulynxvwtdbf, f , pickle.HIGHEST_PROTOCOL)
    #print hiszjulynxvwtdbf


print len(hiszjulynxvwtdbf) 

#u ^ (w & (y + h - q) = i
hiszjulynxvwtdbfq=[]
for tryme in hiszjulynxvwtdbf:
    h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f = tryme
    for q in range(1,10):
        if ( u ^(w&(y+h-q)) )&7 == i&7  :
            counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q])
            if counter.most_common(1)[0][1] <= 2 and check():
                hiszjulynxvwtdbfq.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q])
                    
print len(hiszjulynxvwtdbfq) 



#y ^ (g & (f + n - z) = q
hiszjulynxvwtdbfqg=[]
for tryme in hiszjulynxvwtdbfq:
    h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q = tryme
    for g in range(0,10):
        if ( y^(g&(f+n-z)) )&7 == q&7  :
            counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g])
            if counter.most_common(1)[0][1] <= 2 and check():
                hiszjulynxvwtdbfqg.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g])
                    
print len(hiszjulynxvwtdbfqg) 

def getDecimal(strng,tryme,c,o):
    mynames='hiszjulynxvwtdbfqg'    
    h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g = tryme

    mydict=dict(zip(list(mynames), list(tryme)))
    mydict['c']=c
    mydict['o']=o
    power=7
    total = 0    
    for letter in strng:
        total += mydict[letter]*pow(10,power)
    return total

possibilities=[]
for tryme in hiszjulynxvwtdbfqg:
    h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g = tryme
    for c in range(0,10):
        for o in range(0,10):
            counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g,c,o])
            if counter.most_common(1)[0][1] <= 2 and check():
                d1 = getDecimal('bytwycju',tryme,c,o)
                d2 = getDecimal('wwnnnqbw',tryme,c,o)
                d3 = getDecimal('yzdgotby',tryme,c,o)
                d4 = getDecimal('yjjowdqh',tryme,c,o)
                d5 = getDecimal('nttuhlnq',tryme,c,o)
                d6 = getDecimal('fjivucti',tryme,c,o)
                
                e1 = getDecimal('yzvyjjdy',tryme,c,o)
                e2 = getDecimal('uclfqvdu',tryme,c,o)
                e3 = getDecimal('oigsjgoj',tryme,c,o)
                e4 = getDecimal('niiqztgs',tryme,c,o)
                e5 = getDecimal('oqbctlzh',tryme,c,o)
                e6 = getDecimal('zoljwdfl',tryme,c,o)
                
                if str( (d1 ^ (d2 & (d3 + d4 - d5))) ).endswith(str(d6)) and str( (e1 ^ (e2 & (e3 + e4 - e5))) ).endswith(str(e6)):  
                    possibilities.append([h,i,s,z,j,u,l,y,n,x,v,w,t,d,b,f,q,g,c,o])
                    
                    
        
print len(possibilities)   
    
for p in possibilities:
    mynames = 'hiszjulynxvwtdbfqgco'  
    mydict=dict(zip(list(mynames), list(p)))
    out=''
    for m in message:
        if m == ',': 
            pass
        elif m == ' ':
            out+= m
        else:
            out+= str(mydict[m])
    print out

print len(possibilities) 
#for i in out.split(' '):
#    print chr(int(i)),
'''
bytwycju + yzvyjjdy ^ vugljtyn + ugdztnwv | xbfziozy = bzuwtwol
    ^         ^          ^          ^          ^
wwnnnqbw - uclfqvdu & oncycbxh | oqcnwbsd ^ cgyoyfjg = vyhyjivb
    &         &          &          &          &
yzdgotby | oigsjgoj | ttligxut - dhcqxtfw & szblgodf = sfgsoxdd
    +         +          +          +          +
yjjowdqh & niiqztgs + ctvtwysu & diffhlnl - thhwohwn = xsvuojtx
    -         -           -         -          -
nttuhlnq ^ oqbctlzh - nshtztns ^ htwizvwi + udluvhcz = syhjizjq
    =         =           =         =          =         
fjivucti   zoljwdfl   sugvqgww   uxztiywn   jqxizzxq

operator precedence:
  +/-
  &
  ^
  |

'''




'''  
j ^ (q & (j + z - t)) = w
t ^ (w & (x + h - z))  = i

y ^ (w & (z + j -t )) = j


y ^ (x & (u + s - n))  = w

#u ^ (w & (y + h - q) = i     
hiszjulynxvwq=[]
for tryme in hiszjulynxv:
    h,i,s,z,j,u,l,y,n,x,v = tryme
    for w in range(0,10):
        for q in range(1,10):
            if ( u ^ (w&(y+h-q)) )%10 == i:
                counter = Counter([h,i,s,z,j,u,l,y,n,x,v,w,q])
                if counter.most_common(1)[0][1] <= 2 and check():
                    hiszjulynxvwq.append([h,i,s,z,j,u,l,y,n,x,v,w,q])
            
print len(hiszjulynxvwq)        
'''



'''      
# h & (s + u) & (l - n)   = x       
hiszjulyvnx=[]
for tryme in hiszjulyvn:
    h,i,s,z,j,u,l,y,v,n = tryme
    for x in range(0,10):
        if ( h& (s+u) & (l-n) )%10 == x:
            counter = Counter([h,i,s,z,j,u,l,y,v,n,x])
            if counter.most_common(1)[0][1] <= 2 and check():
                hiszjulyvnx.append([h,i,s,z,j,u,l,y,v,n,x])
            
print len(hiszjulyvnx)          
     
#u ^ (w & (y + h - q) = i     
hiszjulyvnxwq=[]
for tryme in hiszjulyvnx:
    h,i,s,z,j,u,l,y,v,n,x = tryme
    for w in range(0,10):
        for q in range(1,10):
            if ( u ^ (w&(y+h-q)) )%10 == i:
                counter = Counter([h,i,s,z,j,u,l,y,v,n,x,w,q])
                if counter.most_common(1)[0][1] <= 2 and check():
                    hiszjulyvnxwq.append([h,i,s,z,j,u,l,y,v,n,x,w,q])
            
print len(hiszjulyvnxwq)         
        

count = 0
for p in set(perms):
    count+=1
    lt = string.maketrans(letters,''.join(p))
    
    
    if ( getDigit('q',lt) == '0'):
        continue
    
    # check first row
    result = (( ( getDigit('bytwycju',lt) + getDigit('yzvyjjdy',lt) )
              ^ ( getDigit('vugljtyn',lt) + getDigit('ugdztnwv',lt)  ))
              |   getDigit('xbfziozy',lt)
              ) 
    target = 'bzuwtwol'.translate(lt) 
    if not str(result).endswith(target):
        continue
    
    # check second row
    result = ( (( getDigit('wwnnnqbw',lt) - getDigit('uclfqvdu',lt) )
              &  getDigit('oncycbxh',lt) )
              |  (getDigit('oqcnwbsd',lt) ^ getDigit('cgyoyfjg',lt))
              ) 
    target = 'vyhyjivb'.translate(lt) 
    if not str(result).endswith(target):
        continue
    
    

    # print message translated
    print "made it through"
    lettertrans = string.maketrans(letters,''.join(p))
    print message.translate(lettertrans)
    print lt
    
    
    #letter2digit = dict(zip(letters,p))
    
    #if letter2digit['q'] == '0':
    #     break
'''  
    

    
    


