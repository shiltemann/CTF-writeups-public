from PIL import Image
import sys
i = sys.argv[1]

im = Image.open(i)
rgb_im = im.convert('RGB')

red, green, blue = rgb_im.split()
red.save(i + '_r.png')
green.save(i + '_g.png')
blue.save(i + '_b.png')
