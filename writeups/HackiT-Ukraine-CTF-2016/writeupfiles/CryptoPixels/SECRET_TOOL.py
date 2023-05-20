from PIL import Image
import random

FLAG = '^__^'

img = Image.open('original.png')
img_pix = img.convert('RGB')

x = random.randint(1,255)
y = random.randint(1,255)

img_pix.putpixel((0,0),(len(FLAG),x,y))

for l in FLAG:
    x1 = random.randint(1,255)
    y1 = random.randint(1,255)
    img_pix.putpixel((x,y),(ord(l),x1,y1))
    x = x1
    y = y1

img_pix.save('encrypted.png')