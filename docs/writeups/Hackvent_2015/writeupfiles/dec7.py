from PIL import Image
from qrtools import QR
import textwrap

hexstring="01fc137f82a7a0dd05d76ebbcbb74815d82c720ff555fc018801f78baaf93c051d55e46346fd16dd457f54451df65fcec3a493768ffc00948aff4154e090627753ffebafa7ddd568860a87a3fd88eb"
data=int(hexstring,16)
binstring = bin(data)[2:]

# print bits in 25x25 square
print textwrap.fill(binstring,width=25)

# does look like a qr code, let's make an image from the bits!
outimg = Image.new( 'RGB', (25,25), "black") 
pixels_out = outimg.load() 

count=0 
for bit in binstring:    
    i=count%25
    j=count/25
    if bit == '0':
        pixels_out[(i,j)]=(255,255,255)
    count += 1
    
outimgname = "dec7_qrout.png"     
outimg = outimg.resize((250,250))
outimg.save(outimgname,"png")

myCode = QR(filename=outimgname)
if myCode.decode():
    print myCode.data_to_string()
