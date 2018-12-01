#!/usr/bin/env python
import argparse
from pathlib import Path
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True


def extract_lsb(inputimage):
    outfilename = Path(inputimage).resolve().stem
    img = Image.open(inputimage)
    img = img.convert('RGB')  # TODO: add support for alpha channel?
    pixels = img.load()

    (w, h) = img.size
    print(w, h)

    outimg_r = Image.new('RGB', (w, h), "white")
    outimg_g = Image.new('RGB', (w, h), "white")
    outimg_b = Image.new('RGB', (w, h), "white")
    outimg_y = Image.new('RGB', (w, h), "white")
    outimg_c = Image.new('RGB', (w, h), "white")
    outimg_m = Image.new('RGB', (w, h), "white")

    pixels_r = outimg_r.load()
    pixels_g = outimg_g.load()
    pixels_b = outimg_b.load()
    pixels_y = outimg_y.load()
    pixels_c = outimg_c.load()
    pixels_m = outimg_m.load()

    for j in range(0, h):
        for i in range(0, w):
            (r, g, b) = pixels[i, j]
            pixels_r[i, j] = (r, 0, 0)
            pixels_g[i, j] = (0, g, 0)
            pixels_b[i, j] = (0, 0, b)

            pixels_y[i, j] = (r, g, 0)
            pixels_c[i, j] = (0, g, b)
            pixels_m[i, j] = (r, 0, b)

    print(outfilename)
    outimg_r.save(outfilename + "_r.png")
    outimg_g.save(outfilename + "_g.png")
    outimg_b.save(outfilename + "_b.png")
    outimg_y.save(outfilename + "_y.png")
    outimg_c.save(outfilename + "_c.png")
    outimg_m.save(outfilename + "_m.png")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Extract LSB from each channel of an image')
    parser.add_argument('image', help='Input image')
    args = parser.parse_args()

    extract_lsb(args.image)
