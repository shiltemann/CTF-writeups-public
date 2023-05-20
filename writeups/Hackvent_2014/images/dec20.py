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