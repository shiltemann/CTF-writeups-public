from PIL import Image
import sys

img = Image.open("157de28f-2190-4c6d-a1dc-02ce9e385b5c-line.png")
pixels = img.load()
(w, h) = img.size


c = None
for i in range(w):
    q = pixels[i, 0]
    if q != c:
        if q != (255, 255, 255):
            # sys.stdout.write(chr(q[0]))
            # sys.stdout.write(chr(q[1]))
            sys.stdout.write(chr(q[2]))
    c = q
