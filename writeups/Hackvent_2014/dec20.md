# December 20th: http://riddler.php

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=20](http://hackvent.hacking-lab.com/challenge.php?day=20)


**Hint**

*Completely automated riddling to tell Computers and Humans apart*

**Challenge**

Follow this link and do some math: [link](http://hackvent.hacking-lab.com/1JnjqflWseX_29Ow-YXH/)


**Solution**

The link gives us a .gif image containing a math problem to solve. When we refresh te page we get the statement:

```
Wrong answer, hobo!
```

Refreshing again gives us a new images, etc. Examples of images are:


![](images/example_captcha1.gif)  
![](images/example_captcha2.gif)  
![](images/example_captcha3.gif)  


So it would seem we need to read the images, compute the answer, and post the results back somehow.

When we do a get request in python, we can read the response headers, and they contain instructions on how to solve the challenge:

```
CaseInsensitiveDict({
  'content-length': '473', 
  'x-powered-by': 'PHP/5.4.16', 
  'expires': 'Thu, 19 Nov 1981 08:52:00 GMT', 

  'x-riddler-howto': "I will reward you with a nice christmas ball if you solve 30 of 
  my riddles in just a minute.   Just send me back my cookies and POST your answer
  as 'result'.   However, if one of your answers is wrong, you'll have to start over.", 

  'server': 'Apache/2.4.6 (CentOS) PHP/5.4.16', 
  'x-riddler': 'INFO', 
  'pragma': 'no-cache', 
  'cache-control': 'no-store, 
  no-cache, must-revalidate, 
  post-check=0, 
  pre-check=0', 
  'date': 'Sat, 20 Dec 2014 16:07:50 GMT', 
  'content-type': 'image/gif'
})

```

So we need to solve 30 equations in a minute, and POST our answer as a parameter named `result`. 
We use [PyTesser](https://code.google.com/p/pytesser/) OCR to read the images ([script](images/dec20.py)).

NOTE: in order for this script to work, the pytesser folder needs to be in the same directory as the script (zip file [here](images/pytesser_v0.0.1.tar.gz))

```python
from PIL import Image
import requests
from StringIO import StringIO
import sys

# import pytesser code
sys.path.append('pytesser_v0.0.1')
from pytesser import *

# the url 
url="http://hackvent.hacking-lab.com/1JnjqflWseX_29Ow-YXH/"

# pass our cookie
cookies=dict(PHPSESSID="myyummiecookie")

# get first image, response header also contains hint on how to post answer
r=requests.get(url,cookies=cookies)
print str(r.headers)+"\n"


# the OCR is not perfect, so sometimes we make a mistake, keep trying
# until we get 30 in a row!
finished=False
while not finished:
    for i in range(0,30):             
        image = Image.open(StringIO(r.content))
        #image.show()
        
        # convert the image into a string with the OCR
        mathstr = image_to_string(image)
        
        # fix some of the math symbols and the occasional 5
        mathstr = mathstr.replace(" 7 "," - ")
        mathstr = mathstr.replace(" 4 "," * ")
        mathstr = mathstr.replace(" 3 "," * ")
        mathstr = mathstr.replace(" 1 "," * ")
        mathstr = mathstr.replace(" x "," * ")
        mathstr = mathstr.replace(" 6 "," * ")
        mathstr = mathstr.replace(" 9 "," * ")
        mathstr = mathstr.replace(" 5 "," * ")
        mathstr = mathstr.replace(" 8 "," * ")
        mathstr = mathstr.replace(" 2 "," * ")
        mathstr = mathstr.replace("s","5")
        
        # calculate answer    
        answer= eval(mathstr)        
        print "round "+str(i).ljust(2)+": "+str(answer).ljust(20),
            
        # post answer
        params={'result':str(answer)}
        r=requests.post(url,cookies=cookies,data=params)
        
        # get response, if incorrect we need to start over
        if "Wrong answer, hobo!" in r.text:
            print r.text
            print "\nShoot! :( starting over..\n"
            r=requests.get(url,cookies=cookies)
            break
        else:
            print "correct! \o/"
            
        # if we got the 30th correct answer, response will contain image with our bauble    
        if i==29:
            finalimage = Image.open(StringIO(r.content))
            finalimage.save("dec20bauble.png","PNG")
            print "\nYAY, we got it!, check out the bauble in: dec20bauble.png\n"
            finished=True
```

We run this script and get the following output:

```
CaseInsensitiveDict({'content-length': '473', 'x-powered-by': 'PHP/5.4.16', 'expires': 
'Thu, 19 Nov 1981 08:52:00 GMT', 'x-riddler-howto': "I will reward you with a nice christmas 
ball if you solve 30 of my riddles in just a minute. Just send me back my cookies and POST
your answer as 'result'. However, if one of your answers is wrong, you'll have to start over.", 
'server': 'Apache/2.4.6 (CentOS) PHP/5.4.16', 'x-riddler': 'INFO', 'pragma': 'no-cache', 
'cache-control': 'no-store, no-cache, must-revalidate, post-check=0, pre-check=0', 'date': 
'Sat, 20 Dec 2014 16:07:50 GMT', 'content-type': 'image/gif'})

round 0 : 1112008435           correct! \o/
round 1 : 344452020            correct! \o/
round 2 : -2133168179          correct! \o/
round 3 : -2917487703          correct! \o/
round 4 : 1463465504           correct! \o/
round 5 : 115205409            Wrong answer, hobo!

Shoot! :( starting over..

round 0 : 1681567448           correct! \o/
round 1 : 49327                correct! \o/
round 2 : 133829519            correct! \o/
round 3 : 414743133063405312   correct! \o/
round 4 : 140137               correct! \o/
round 5 : 10273960177493       correct! \o/
round 6 : 70622                correct! \o/
round 7 : 19887119631353       correct! \o/
round 8 : -74940               correct! \o/
round 9 : 596705193            correct! \o/
round 10: 7895827807392        correct! \o/
round 11: 963071847            correct! \o/
round 12: 67256                correct! \o/
round 13: 81442822             correct! \o/
round 14: -112211              correct! \o/
round 15: -935632634           correct! \o/
round 16: -65282               correct! \o/
round 17: -78930               correct! \o/
round 18: 40008688490471       correct! \o/
round 19: 9257598122713        correct! \o/
round 20: -741145798           correct! \o/
round 21: 175598643959200      correct! \o/
round 22: -25853033464312      correct! \o/
round 23: 49988                correct! \o/
round 24: -39804               correct! \o/
round 25: 1417577074           correct! \o/
round 26: 818225117110         correct! \o/
round 27: 196092               correct! \o/
round 28: -520164229           correct! \o/
round 29: 1775442550           correct! \o/

YAY, we got it!, check out the bauble in: dec20bauble.png

```

Since our OCR is not perfect, the script may need to start the whole process over a couple times, 
but sooner or later it will work 30 times in a row.

Our bauble was written to a file:

![](images/dec20bauble.png)


**Flag**

```
HV14-hyUZ-Km4a-jE6u-G1K0-RPT1
```

