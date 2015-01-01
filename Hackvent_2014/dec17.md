# December 17th: Public Key Encryption

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=17](http://hackvent.hacking-lab.com/challenge.php?day=17)


**Hint**

*better dont try this manually*

**Challenge**

We get a list of RSA variable sets:

```


PublicKey(n=4157341, e=5)
Encrypted: 4100698

PublicKey(n=1435807, e=5)
Encrypted: 1386406

PublicKey(n=7550821, e=5)
Encrypted: 2712996

PublicKey(n=2030989, e=3)
Encrypted: 32768

PublicKey(n=153707, e=11)
Encrypted: 38178

PublicKey(n=16068707, e=5)
Encrypted: 10576615

PublicKey(n=243379, e=3)
Encrypted: 141339

...

```

full list in this [file](images/day17.input)


**Solution**

We work through the first set of variables manually, then automate the process to get the answer. 
The RSA cryptosystem is explained [here](http://en.wikipedia.org/wiki/RSA_(cryptosystem)). And 
this [RSA caluculator](https://www.cs.drexel.edu/~introcs/Fa11/notes/10.1_Cryptography/RSA_Express_EncryptDecrypt.html) and 
[RSA worksheet](https://www.cs.drexel.edu/~introcs/Fa11/notes/10.1_Cryptography/RSAWorksheetv4d.html) are great resources 
to get a feel for what to do.

The recipe for solving this challenge (performed with first set of variables) is:

```

Given the modulus N (4157341), ciphertext ct (4100698) and encryption key e (5):

-> Find prime factorization (p,q) of N:
   p,q= 1069,3889
   
   
-> compute r = (p-1)*(q-1)
   r = (p-1)*(q-1) = 4152384

   
-> Find two numbers e and d whose product is a number equal to 1 mod r

  candidates K which are 1 mod r:
  4152385 8304769 12457153 16609537 20761921 24914305 
  29066689 33219073 37371457 41523841 45676225 49828609 
  53980993 58133377 62285761 66438145 70590529 74742913 
  78895297 83047681 87200065 91352449 95504833 99657217 
  103809601 107961985 112114369 116266753 120419137 124571521 

  (these candidates are r+1, 2r+1, 3r+1, .. etc)

  
-> find the candidate K which can be factored such that:
   e*d mod r = 1
   e and N relatively prime
   d and N relatively prime
   e and r relatively prime
   d and r relatively prime

   (for us: e is given, so d= K/e, then check constraints)

   d=830477  (K=4152385)
   
-> decrypt message using N and d

   ct^d mod N  (71, == 'G')

```

We automate this process in a python [script](images/dec17.py):

```python
def prime_factors(n):
    factors = []
    d = 2
    while n > 1:
        while n % d == 0:
            factors.append(d)
            n /= d
        d = d + 1
    return factors

def are_relatively_prime(a,b):
  while True:
    temp=a%b
    if(temp==0):
      return b
    a=b
    b=temp

Nlist=[ <list of N values> ]
elist=[ <list of e values> ]
ctlist=[ <list of ciphertexts> ]

plaintext=''
for count in range(0,len(Nlist)):
  N=Nlist[count]
  e=elist[count]
  ct=ctlist[count]
  
  # find prime factors p and q of N
  (p,q)=prime_factors(N)
    
  # compute r=(p-1)(q-1)
  r=(p-1)*(q-1)

  # for each candidate 1 mod r, K ( try r+1,2r+1,3r+1,..)
  i=0
  while True:
    i+=1
    K=r*i+1

    # divide by e to get d, check if e and N, d and N, e and r, d and r are all relatively prime
    d=K/e
    t1=are_relatively_prime(e,N)
    t2=are_relatively_prime(d,N)
    t3=are_relatively_prime(e,r)
    t4=are_relatively_prime(d,r)

    if t1==1 and t2==1 and t3==1 and t4==1 and (e*d)%r==1: # found good canidate
      pt=pow(ct, d, N)  # RSA decrypt message using decryption key d
      plaintext+= chr(pt)
      break
    
print plaintext


```

When we run this script, we get the following output:

```
God could create the world in six days because he didn't have to make it compatible with the 
previous version. If we're supposed to work in Hex, why have we only got 0xA fingers?
```

We put this into the ball-o-matic to get our ball:

![](images/qHWWOQA6587pyzIHbbHx.png)


**Flag**

```
HV14-XOnE-Wb18-I1Zl-VXJv-9wAA
```


