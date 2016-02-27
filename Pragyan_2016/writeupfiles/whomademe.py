import pickle
from PIL import Image


f = open('pixels.jpg.pkl')
coords = pickle.load(f)

outimg = Image.new( 'RGB', (600,600), "white") 
pixels_out = outimg.load() 

for c in coords[1:]:   
    pixels_out[c[0],c[1]]=(0,0,0)
    

outimg.save("whomademe.png","png")     