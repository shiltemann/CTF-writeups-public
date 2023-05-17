from PIL import Image

img = Image.open("stegbool.png") # open stego image
pixels_orig = img.load() # create the pixel map
(w,h)=img.size

# create new image to which we will write hidden image
outimg = Image.new( 'RGB', (w,h), "white") # create a new black image
pixels_out = outimg.load() # create the pixel map

# for each pixel, calculate !r & (g^b) as hinted. 
# If 1, create white pixel, otherwise create black pixel
for i in range(0,h):
    for j in range(0,w):
      (r,g,b) = pixels_orig[j,i] 
      bit=  (~r & (g^b))&1
      if bit:
          (r,g,b)=(255,255,255)
      else:
          (r,g,b)=(0,0,0)
          
      pixels_out[j,i] = (r,g,b) 

# save resulting image to file 
outimg.save("dec19.png","png") 
