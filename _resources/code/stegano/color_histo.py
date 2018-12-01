#!/usr/bin/env python
from collections import Counter
import argparse
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True


def extract_lsb(inputimage):
    img = Image.open(inputimage)
    img = img.convert('RGB')  # TODO: add support for alpha channel?
    pixels = img.load()

    (w, h) = img.size

    c = Counter()

    for j in range(0, h):
        for i in range(0, w):
            rgb = pixels[i, j]
            c[rgb] += 1

    for k, v in c.most_common(20):
        print("%03d %03d %03d %d" % (*k, v))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Extract LSB from each channel of an image')
    parser.add_argument('image', help='Input image')
    args = parser.parse_args()

    extract_lsb(args.image)
