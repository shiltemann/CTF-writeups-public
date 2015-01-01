# December 12th: Another oracle says 

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=12](http://hackvent.hacking-lab.com/challenge.php?day=12)

**Hint**

*wrap it up!*

**Challenge**

John Scriptkiddy has created an awesome crypttool to store his passwords very securely  
on the database. Unfortunately, a few days later, he recognized that his script is really  
awesome: He isn't capable to decrypt his own password!  

Can you help him to reveal his original password from this stored cipher:  
  
  
```
617B7E0A0870637F710E42B44A3B0647433442441B4E4F1D4B471F29475C5D62
```

He remembers only that it is an easy-to-remember phrase, well apparently not that easy ...

His awesome script is also available here: [AwesomeCryptTool.pls](images/AwesomeCryptTool.pls)
  


**Solution**  

The contents of the file are:

```
CREATE OR REPLACE PACKAGE HACKvent wrapped 
a000000
b2
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
9
9e aa
WvN7G0Z97FJZxrcs6FN4mZS8BegwgwL/f54VaS9GOPauf2NhYSanELc0bMJC2BOPRH472079
5mfaCjcsInUbe5dndzKa0MGZwNrhc4rs3619e5RBRwVcR7f+NkctWhGNU27zR628a2pk0WgN
owpf8LWz0sIQk30xpCJbEj5a

/
CREATE OR REPLACE PACKAGE BODY HACKvent wrapped 
a000000
b2
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
abcd
b
372 270
BwGVYsJ7/qYMVlBQVrtLCEIPNvowg41eLiAFfHRVPfmUNJX8eu+9Swzwy8hsbG/gDDciws6O
jlQ30tMfTDw4Z6rkWtoux1Rt/990+PfPBoxBPHHYzgk1AQFbKvl6VaRKDhDsG20RolA8qWUV
8o3eA0hT3zV5HREd/bmi11VuN16oReqp5ftkjfyHS37fkGVTvDf6Gnbg3Dr+4AN41rp8LTuJ
2Yt+NkUMyiZ3Cf2KAAjlzGapA7OFWSs7mq1IGnltsiBR5oPPIgF0MjZtbpkXusj3eEOqp5+c
Y1QM7C0FBKtWkofnuWrRVJIcWH4N44e4q9UGYZMpaaCb1dffQJAo3BNsaM/WzVzGaSjM0dgd
Lh1PlOmMR2V3nNqvDi2f8N76fN9xunfRhocRkDpUqIYBn+JOiAtPKtbBwTj/GuqIrch04REL
yBQuEWGWZcWkn7oewvMu+WNKhVT53OHcQMwTSVxJcnCIYgxiX9HV+7+B5G5iFj36rOZk9kMi
iq+rMk+vr1ld7AMMQHy+Crn+MMG4aJq1RgcFxu/kKaqv+TMpy0oA5H1rJC+b53O7HYkPtBrP
PWcUjB8I6fLUycyh7Boa1nx7o/0C9E/54UwY6yM=

/
```



We unwrap the pl/sql online:  [http://www.codecrete.net/UnwrapIt/](http://www.codecrete.net/UnwrapIt/)

  
 
```
PACKAGE HACKvent AS
   FUNCTION ENCODE(INPLAINTEXT  IN VARCHAR2) RETURN VARCHAR2; 
   FUNCTION DECODE(INCIPHERTEXT IN VARCHAR2) RETURN VARCHAR2; 
END HACKVENT;
```

```
PACKAGE BODY HACKvent IS
 
  FUNCTION ENCODE(INPLAINTEXT IN VARCHAR2) RETURN VARCHAR2 IS
     KEY VARCHAR(100) := '';
     RES VARCHAR(100) := '';
     RES1 VARCHAR(100) := '';
     X NUMBER(2);
     Y NUMBER(2);
     BEGIN
 
        SELECT ROUND(DBMS_RANDOM.VALUE(10,20)) INTO X FROM DUAL;
        SELECT ROUND(DBMS_RANDOM.VALUE(10,20)) INTO Y FROM DUAL;
 
        KEY := UTL_RAW.CAST_TO_RAW(DBMS_OBFUSCATION_TOOLKIT.MD5(INPUT_STRING => X*Y));
 
 
        FOR I IN 1..LENGTH(INPLAINTEXT) LOOP
           RES1 := CHR(ASCII(SUBSTR(INPLAINTEXT,I,1))+I) || RES1;
        END LOOP;
 
        RES := 
           UTL_RAW.BIT_XOR(
           UTL_RAW.CAST_TO_RAW(RES1),
           UTL_RAW.CAST_TO_RAW(KEY)
        );
 
        RETURN RES;
     END ENCODE; 
   
   
   FUNCTION DECODE(INCIPHERTEXT IN VARCHAR2) RETURN VARCHAR2 IS
     HINT VARCHAR2(100);
   BEGIN
        HINT := 'Hohoho, this part you have to do on your own ;-)';
        RETURN HINT;
   END DECODE; 

 END HACKVENT;

```


So we have to write our own decode function.

Luckily, there are less than 100 possible keys. We simply use all possible keys  
to decrypt the message and see which one gives us a possible valid solution  
(we know solution should be a simple phrase)  


[dec12.py](images/dec12.py)

```python

import binascii,md5
from Crypto.Cipher import XOR

target = "617B7E0A0870637F710E42B44A3B0647433442441B4E4F1D4B471F29475C5D62"
targetdec = target.decode("hex")

# generate set of possible keys
keys = []
for i in range(10,20):
	for j in range(10,20):
		m = md5.new(str(i*j))
		digest = m.hexdigest()
		keys.append(digest.upper())


# try decrypting with each of the keys
for k in keys:
        crypt = XOR.new(k)
        kxor = crypt.encrypt(targetdec)
        
        result = ''
        l=len(kxor)
        for i in range(1, l):                
                value = kxor[l-i:l-i+1]
                value = ord(value)-i
                try :
                        result += chr(value)
                except:
                        break
        
		print result
		
```



This gives us all possible decryptions, most are meaningless, but one looks good:

```
There is no place like 127.0.0.1
```

We input this in the ball-o-matic to get the bauble:

![](images/7uRg7ILhpX7fi86scWMj.png)


**Flag**

```
HV14-5Gai-jrjC-Mzuq-0hbv-fSho
```

