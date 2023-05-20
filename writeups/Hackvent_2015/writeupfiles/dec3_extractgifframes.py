import os, re
from PIL import Image
from qrtools import QR

def extractFrames(giffile, outputdir):
    with Image.open(giffile) as frame:
        nframes = 0
        while frame:
            frame.save( '%s/%s-%s.gif' % (outputdir, os.path.basename(giffile), nframes ) , 'GIF')
            nframes += 1
            try:
                frame.seek( nframes )
            except EOFError:
                break;
        return True    


# extract every fram
extractFrames('fast_response_code.gif', 'dec3_frames')

# read qr code in each extracted image
path="./dec3_frames/"

nugget=''
for filename in sorted(os.listdir(path), key=lambda x: int(re.findall(r'\d+', x)[0])):
    myCode = QR(filename=path+filename)
    if myCode.decode():
      nugget+=myCode.data_to_string()
      
print nugget