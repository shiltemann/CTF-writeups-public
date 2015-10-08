from PIL import Image

img1 = Image.open("egg_17_difference1.bmp")
img2 = Image.open("egg_17_difference2.bmp") 

pixels1 = img1.load() # create the pixel map
pixels2 = img2.load() # create the pixel map

(w,h)=img1.size

# create new image to which we will write hidden image
outimg = Image.new( 'RGB', (w,h), "white") # create a new black iage
pixels_out = outimg.load() # create the pixel map

# for each pixel, calculate !r & (g^b) as hinted. 
# If 1, create white pixel, otherwise create black pixel
for i in range(0,h):
    for j in range(0,w):
      (r1,g1,b1) = pixels1[j,i] 
      (r2,g2,b2) = pixels2[j,i] 
      
      #bit=  (~r & (g^b))&1
      if r1==r2:
          (r,g,b)=(255,255,255)
      else:
          (r,g,b)=(0,0,0)

      #pixels_out[j,i] = (r,g,b) 
      pixels_out[j,i] = ((r1^r2)&1,(g1^g2)&1,(b1^b2)&1) 
      #print str(r1)+str(g1)+str(b1)+"-"+str(r2)+str(g2)+str(b2)

outimg.save("egg_17_result_xor.png","png") 
