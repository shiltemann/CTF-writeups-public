import argparse
from pathlib import Path
from PIL import Image, ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True


def extract_lsb(inputimage, invert, x, y, w, h):
    outfilename = Path(inputimage).resolve().stem
    img = Image.open(inputimage)
    img = img.convert('RGB')  # TODO: add support for alpha channel?
    pixels = img.load()

    (wi, hi) = img.size

    background = "white" if invert else "black"
    foreground = (0, 0, 0) if invert else (255, 255, 255)

    outimg_r = Image.new('RGB', (w, h), background)

    pixels_r = outimg_r.load()

    for j in range(0, h):
        for i in range(0, w):
            (r, g, b) = pixels[x + i, y + j]
            if r & 1 or g & 1 or b & 1:
                pixels_r[i, j] = foreground

    outimg_r.save(outfilename + "_crop.png")


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Perfect pixel-for-pixel cropping')
    parser.add_argument('-i', '--input', help='Input image', required=True)
    parser.add_argument('--invert', action='store_true', help='invert colours')
    parser.add_argument('x', type=int)
    parser.add_argument('y', type=int)
    parser.add_argument('w', type=int)
    parser.add_argument('h', type=int)
    args = parser.parse_args()

    extract_lsb(args.input, args.invert, args.x, args.y, args.w, args.h)
