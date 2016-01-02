from PIL import Image

img = Image.open('ball_3h6SOemwRR_PmQhXh2AM.png')
pixels_orig = img.load() 
(w,h)=img.size

outimg = Image.new( 'RGB', (w,h), "white") 
pixels_out = outimg.load() 

for i in range(0,h):
    for j in range(0,w):
      (r,g,b) = pixels_orig[j,i] 
      if(b%2==0):      
          pixels_out[j,i]=(0,0,0)

outimg.save("dec13_evenblueval.png","png") 