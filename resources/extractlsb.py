from PIL import Image
import sys

def extract_lsb(inputimage):
    img = Image.open(inputimage)
    pixels = img.load()

    (w,h) = img.size
    print(w,h)

    outimg_r = Image.new('RGB', (w,h), "white")
    outimg_g = Image.new('RGB', (w,h), "white")
    outimg_b = Image.new('RGB', (w,h), "white")

    pixels_r = outimg_r.load()
    pixels_g = outimg_g.load()
    pixels_b = outimg_b.load()

    for i in range(0,w):
      for j in range(0,h):
        (r,g,b) = pixels[i,j]
        if not r&1:
            pixels_r[i,j] = (0,0,0)
        if not g&1:
            pixels_g[i,j] = (0,0,0)
        if not b&1:
            pixels_b[i,j] = (0,0,0)

    outimg_r.save("outimg_r.png")
    outimg_g.save("outimg_g.png")
    outimg_b.save("outimg_b.png")

if __name__ == '__main__':
    extract_lsb(sys.argv[1])
