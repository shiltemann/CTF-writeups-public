from PIL import Image

img = Image.open('10111001.png')
pixels_orig = img.load()
(w,h)=img.size

outimg = Image.new( 'RGB', (w,h), "white") 
pixels_out = outimg.load() 

for i in range(0,h):
    for j in range(0,w):
      (r,g,b) = pixels_orig[j,i]
      if g&1:
          pixels_out[j,i]=(0,0,0)
      if i==0:
          print b&1,
      

outimg.save("justabit_out_g.png","png") 