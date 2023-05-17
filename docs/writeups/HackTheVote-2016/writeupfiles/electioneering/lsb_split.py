from PIL import Image
import sys
i = sys.argv[1]

im = Image.open(i)
rgb_im = im.convert('RGB')

# for x in range(rgb_im.width):
    # for y in range(rgb_im.height):
        # print x, y


lsb_8 = Image.new('RGB', rgb_im.size, 'white')
lsb_7 = Image.new('RGB', rgb_im.size, 'white')
lsb_6 = Image.new('RGB', rgb_im.size, 'white')
lsb_5 = Image.new('RGB', rgb_im.size, 'white')
lsb_4 = Image.new('RGB', rgb_im.size, 'white')
lsb_3 = Image.new('RGB', rgb_im.size, 'white')
lsb_2 = Image.new('RGB', rgb_im.size, 'white')
lsb_1 = Image.new('RGB', rgb_im.size, 'white')

pix_lsb_8 = lsb_8.load()
pix_lsb_7 = lsb_7.load()
pix_lsb_6 = lsb_6.load()
pix_lsb_5 = lsb_5.load()
pix_lsb_4 = lsb_4.load()
pix_lsb_3 = lsb_3.load()
pix_lsb_2 = lsb_2.load()
pix_lsb_1 = lsb_1.load()

# l8 = 0b11111111
# l7 = 0b1111111
# l6 = 0b111111
# l5 = 0b11111
# l4 = 0b1111
# l3 = 0b111
# l2 = 0b11
# l1 = 0b1

l8 = 0b10000000
l7 = 0b1000000
l6 = 0b100000
l5 = 0b10000
l4 = 0b1000
l3 = 0b100
l2 = 0b10
l1 = 0b1

for x in range(rgb_im.width):
    for y in range(rgb_im.height):
        (r, g, b) = rgb_im.getpixel((x, y))
        pix_lsb_8[x, y] = ((r & l8) << 1, (g & l8) << 1, (b & l8) << 1)
        pix_lsb_7[x, y] = ((r & l7) << 2, (g & l7) << 2, (b & l7) << 2)
        pix_lsb_6[x, y] = ((r & l6) << 3, (g & l6) << 3, (b & l6) << 3)
        pix_lsb_5[x, y] = ((r & l5) << 4, (g & l5) << 4, (b & l5) << 4)
        pix_lsb_4[x, y] = ((r & l4) << 5, (g & l4) << 5, (b & l4) << 5)
        pix_lsb_3[x, y] = ((r & l3) << 6, (g & l3) << 6, (b & l3) << 6)
        pix_lsb_2[x, y] = ((r & l2) << 7, (g & l2) << 7, (b & l2) << 7)
        pix_lsb_1[x, y] = ((r & l1) << 8, (g & l1) << 8, (b & l1) << 8)

lsb_8.save('out_8.png')
lsb_7.save('out_7.png')
lsb_6.save('out_6.png')
lsb_5.save('out_5.png')
lsb_4.save('out_4.png')
lsb_3.save('out_3.png')
lsb_2.save('out_2.png')
lsb_1.save('out_1.png')



# red, green, blue = rgb_im.split()
# red.save(i + '_red.png')
# green.save(i + '_green.png')
# blue.save(i + '_blue.png')
