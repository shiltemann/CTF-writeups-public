from PIL import Image

img = Image.open('encrypted.png')
i = img.convert('RGB')

(flag_length, x, y) = i.getpixel((0, 0))

flag = ''

while True:
    (flag_char, new_x, new_y) = i.getpixel((x, y))
    flag += chr(flag_char)
    x = new_x
    y = new_y

    if len(flag) >= flag_length:
        print flag
        break
