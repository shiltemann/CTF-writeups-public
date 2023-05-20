from PIL import Image
from pyzbar.pyzbar import decode
import glob, itertools

def concat_h(im1, im2):
    dst = Image.new('RGB', (im1.width + im2.width, im1.height))
    dst.paste(im1, (0, 0))
    dst.paste(im2, (im1.width, 0))
    return dst

def concat_v(im1, im2):
    dst = Image.new('RGB', (im1.width, im1.height + im2.height))
    dst.paste(im1, (0, 0))
    dst.paste(im2, (0, im1.height))
    return dst


# load image parts
qrblocks = [Image.open(a) for a in glob.glob("dec6-qrcodes/good_tr/*.png")]
qrbr = [Image.open(a) for a in glob.glob("dec6-qrcodes/br/*.png")]
# get all combos
perm = itertools.permutations(qrblocks,3)

found = 0
for i in perm:
  tl = i[0].rotate(90)
  tr = i[1]
  bl = i[2].rotate(180)
  for br in qrbr:
    top = concat_h(tl,tr)
    bottom = concat_h(bl,br)
    final = concat_v(top,bottom)
    dec = decode(final)
    if dec != []:
      print(dec[0].data)
      final.save("dec6_qrcode"+str(found)+".png")
      found+=1

