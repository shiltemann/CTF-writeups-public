#!/usr/bin/env python
import sys
import argparse
from pathlib import Path
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True


maps = {
    'black0': {
        (0, 0, 0): 0,
        (255, 255, 255): 1,
    },
    'black1': {
        (0, 0, 0): 1,
        (255, 255, 255): 0,
    }
}


def doit(inputimage, width, cmap='black0'):
    img = Image.open(inputimage)
    img = img.convert('RGB')
    pixels = img.load()

    (w, h) = img.size

    wo = w / width
    ho = h / width

    for j in range(width):
        for i in range(width):
            ii = int(i * wo + wo / 2)
            jj = int(j * ho + ho / 2)

            rgb = pixels[ii, jj]

            # TODO: support approximate colours + grabbing a set of pixels
            # around and averaging them for better detection rates.
            q = maps[cmap].get(rgb, '?')
            sys.stdout.write(str(q))

        sys.stdout.write('\n')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Convert a square PNG that is a matrix of black/white squares into a 2d array of 1/0s')
    parser.add_argument('image', help='Input image')
    parser.add_argument('width', type=int, help='number of expected boxes in X')
    parser.add_argument('--cmap', choices=['black0', 'black1'], help='Decoding map', default='black1')
    args = parser.parse_args()

    doit(args.image, args.width, args.cmap)
