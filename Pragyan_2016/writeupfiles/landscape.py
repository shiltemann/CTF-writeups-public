from PIL import Image

img = Image.open('landscape.png').convert('RGB') 
pixels_orig = img.load()

(w,h)=img.size

for i in range(45,46):
    for j in range(0,w,7):
      (r,g,b) = pixels_orig[j,i]
      if r==b and b==g: #grey values
          print r,

