# December 2nd: **Back in time**

**URL:** 

[http://hackvent.hacking-lab.com/challenge.php?day=2](http://hackvent.hacking-lab.com/challenge.php?day=2)

**Hint:**  
  
knowledge

**Challenge:**  
  
*Get this to catch the next XMas-Ball*

```
aHR0cDovL2hhY2t2ZW50Lm9yZy8=
```

*but you have to be fast, perhaps somone will changed it*  
  

**Solution**  
  
The string looks like base64 encoding, so we decode it to get:  

```
http://hackvent.org/  
```

If we go to the URL, we get a ball with a message saying we weren't fast enough and should try building a time machine and watch the past.  
  
  
![](images/dec2ss.png)  
  
  
So we do a google search instead and look at a cached version of the website. This gives us the real image.

![](images/Ball_of-Santa.png)

Decoding this QR code gives us the key.

**Flag:**  

```
HV14-fkPc-ljq6-Ldwq-foD7-rnpH  
```

