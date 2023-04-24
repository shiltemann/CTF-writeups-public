from PIL import Image
import sys

im = Image.open("outerspace.bmp") #Can be many different formats.
pix = im.load()


(w,h)=im.size
for i in range(0,w):
  (r,g,b) = pix[i,h-1] #Get the RGBA Value of the a pixel of an image
  if r == 0:
    sys.stdout.write("1")
  else:
    sys.stdout.write("0")

print("")

for i in range(0,w):
  (r,g,b) = pix[i,h-1] #Get the RGBA Value of the a pixel of an image
  if r == 0:
    sys.stdout.write("-")
  else:
    sys.stdout.write(".")