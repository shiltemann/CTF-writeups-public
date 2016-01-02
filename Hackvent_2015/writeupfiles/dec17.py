from PIL import Image
from qrtools import QR
import itertools

img = Image.open('S4nt4s_Quick-R3sp0ns3.png')
pixels_orig = img.load() 
(w,h)=img.size

outimg = Image.new( 'RGB', (23,23), "white") 
pixels_out = outimg.load() 

for i in range(0,h,6):
    for j in range(0,w,6):
      (r,g,b) = pixels_orig[j,i] 
      if((r,g,b)==(255,255,255)):
          pixels_out[j/13,i/13]=(255,255,255)
          
      elif((r,g,b)==(0,0,0) or r>0 ):
          pixels_out[j/13,i/13]=(0,0,0)



# bruteforce missing section until readable
count=0
for i in itertools.product('01', repeat=60):
    count+=1
    if count%1000 ==0:
        print count
    
    for x in range(1,12):
        for y in range (10,14):
            if( i[((y-10)%4)*11 + x] == '0' ):
                pixels_out[x,y]=(0,0,0)
    
        
    
    
    outimg=outimg.resize((300,300))
    outimg.save("dec17_out.png","png") 
    
    
    
    # read qr code 
    nugget=''
    
    myCode = QR(filename="dec17_out.png")
    if myCode.decode():
      nugget=myCode.data_to_string()
      print nugget
      break
    #else:
    #    print "could not decode"
    #break