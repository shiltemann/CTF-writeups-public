#!/usr/bin/env python
import sys
import argparse
from pathlib import Path
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True


def doit(inputimage, width):
    outfilename = Path(inputimage).resolve().stem
    img = Image.open(inputimage)
    img = img.convert('RGB')  # TODO: add support for alpha channel?
    pixels = img.load()

    (w, h) = img.size
    print(w, h)

    wo = w / width
    ho = h / width

    for j in range(width):
        for i in range(width):
            ii = int(i * wo + wo / 2)
            jj = int(j * ho + ho / 2)

            (r, g, b) = pixels[ii, jj]

            if (r, g, b) == (0, 0, 0):
                sys.stdout.write('0')
            elif (r, g, b) == (255, 255, 255):
                sys.stdout.write('1')
            else:
                sys.stdout.write('?')
        sys.stdout.write('\n')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Extract LSB from each channel of an image')
    parser.add_argument('image', help='Input image')
    parser.add_argument('width', type=int, help='number of expected boxes in X')
    args = parser.parse_args()

    doit(args.image, args.width)
