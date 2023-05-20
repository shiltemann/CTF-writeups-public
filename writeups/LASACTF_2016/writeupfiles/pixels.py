from PIL import Image

img1 = Image.open("QR1.png")
img2 = Image.open("QR2.png")

pixels1 = img1.load()
pixels2 = img2.load()

(w,h)=img1.size

outimg = Image.new( 'RGB', (w,h), "white") 
pixels_out = outimg.load() 

for i in range(0,h):
    for j in range(0,w):
      if pixels1[j,i] != pixels2[j,i]: 
          pixels_out[j,i]=(0,0,0)

outimg.save("pixels_outimg.png","png") 