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
    outimg_rg = Image.new('RGB', (w, h), "white")
    outimg_gb = Image.new('RGB', (w, h), "white")
    outimg_br = Image.new('RGB', (w, h), "white")

    pixels_r = outimg_r.load()
    pixels_g = outimg_g.load()
    pixels_b = outimg_b.load()
    pixels_rg = outimg_rg.load()
    pixels_gb = outimg_gb.load()
    pixels_br = outimg_br.load()

    for j in range(0, h):
        for i in range(0, w):
            (r, g, b) = pixels[i, j]
            pixels_r[i, j] = (r, 0, 0)
            pixels_g[i, j] = (0, g, 0)
            pixels_b[i, j] = (0, 0, b)

            pixels_rg[i, j] = (r, g, 0)
            pixels_gb[i, j] = (0, g, b)
            pixels_br[i, j] = (r, 0, b)

    print(outfilename)
    outimg_r.save(outfilename + "_r.png")
    outimg_g.save(outfilename + "_g.png")
    outimg_b.save(outfilename + "_b.png")
    outimg_rg.save(outfilename + "_rg.png")
    outimg_gb.save(outfilename + "_gb.png")
    outimg_br.save(outfilename + "_br.png")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Extract LSB from each channel of an image')
    parser.add_argument('image', help='Input image')
    args = parser.parse_args()

    extract_lsb(args.image)
