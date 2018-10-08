import argparse
import binascii
from pathlib import Path
from PIL import Image


def text_from_bits(bits):
    n = int(bits, 2)
    return int2bytes(n)


def int2bytes(i):
    hex_string = '%x' % i
    n = len(hex_string)
    return binascii.unhexlify(hex_string.zfill(n + (n & 1)))


def extract_lsb(inputimage, text, invert, decode, resize, interlace):
    outfilename = Path(inputimage).resolve().stem
    img = Image.open(inputimage)
    img = img.convert('RGB')  # TODO: add support for alpha channel?
    pixels = img.load()

    (w, h) = img.size
    print(w, h)

    background = "white" if invert else "black"
    foreground = (0, 0, 0) if invert else (255, 255, 255)

    outimg_r = Image.new('RGB', (w, h), background)
    outimg_g = Image.new('RGB', (w, h), background)
    outimg_b = Image.new('RGB', (w, h), background)

    pixels_r = outimg_r.load()
    pixels_g = outimg_g.load()
    pixels_b = outimg_b.load()

    if text or decode:
        pixels_txt = ['', '', '']

    for j in range(0, h):
        if text or decode:
            for i in range(0, 3):
                pixels_txt[i] += '\n'
        for i in range(0, w):
            (r, g, b) = pixels[i, j]
            if r & 1:
                pixels_r[i, j] = foreground
            if g & 1:
                pixels_g[i, j] = foreground
            if b & 1:
                pixels_b[i, j] = foreground
            if text or decode:
                pixels_txt[0] += str(r & 1) if not invert else str((r & 1) ^ 1)
                pixels_txt[1] += str(g & 1) if not invert else str((g & 1) ^ 1)
                pixels_txt[2] += str(b & 1) if not invert else str((b & 1) ^ 1)

    if resize:
        r=int(resize)
        outimg_r = outimg_r.resize((r * w, r * h))
        outimg_g = outimg_g.resize((r * w, r * h))
        outimg_b = outimg_b.resize((r * w, r * h))

    outimg_r.save(outfilename + "_lsb_r.png")
    outimg_g.save(outfilename + "_lsb_g.png")
    outimg_b.save(outfilename + "_lsb_b.png")

    if text:
        with open(outfilename + '_lsb_r.txt', 'w') as fr, open(outfilename + '_lsb_g.txt', 'w') as fg, open(outfilename + '_lsb_b.txt', 'w') as fb:
            fr.write(pixels_txt[0].lstrip())
            fg.write(pixels_txt[1].lstrip())
            fb.write(pixels_txt[2].lstrip())
        if interlace:
            with open(outfilename + '_lsb_rgb.txt', 'w') as fi:
                for i in range(0, len(pixels_txt[0])):
                    fi.write(pixels_txt[0][i].lstrip())
                    fi.write(pixels_txt[1][i].lstrip())
                    fi.write(pixels_txt[2][i].lstrip())


    # convert the bitstring to bytes and save
    # TODO needs work on: padding, encodings
    if decode:
        #padsize = 8 - len(pixels_txt[0].replace('\n', '')) % 8
        pixels_txt[0] = pixels_txt[0].replace('\n', '')
        pixels_txt[1] = pixels_txt[1].replace('\n', '')
        pixels_txt[2] = pixels_txt[2].replace('\n', '')
        with open(outfilename + '_lsb_r.bin', 'wb') as fr, open(outfilename + '_lsb_g.bin', 'wb') as fg, open(outfilename + '_lsb_b.bin', 'wb') as fb:
            fr.write(text_from_bits(pixels_txt[0]))
            fg.write(text_from_bits(pixels_txt[1]))
            fb.write(text_from_bits(pixels_txt[2]))
        if interlace:
            pixels_interlaced = ''
            for i in range(0, len(pixels_txt[0])):
                pixels_interlaced += pixels_txt[0][i].lstrip() + pixels_txt[1][i].lstrip() + pixels_txt[2][i].lstrip()
            print("oi: "+ pixels_interlaced)
            with open(outfilename + '_lsb_rgb.bin', 'wb') as fi:
                fi.write(text_from_bits(pixels_interlaced)[:])


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Extract LSB from each channel of an image')
    parser.add_argument('-i', '--input', help='Input image', required=True)
    parser.add_argument('--text', action='store_true', help='also output textual format')
    parser.add_argument('--interlace', action='store_true', help='interlace channels for text/bin output')
    parser.add_argument('--invert', action='store_true', help='invert colours')
    parser.add_argument('--decode', action='store_true', help='try to decode bitstring')
    parser.add_argument('--resize', help='resize output image by this factor')
    args = vars(parser.parse_args())

    extract_lsb(args['input'], args['text'], args['invert'], args['decode'], args['resize'], args['interlace'])
