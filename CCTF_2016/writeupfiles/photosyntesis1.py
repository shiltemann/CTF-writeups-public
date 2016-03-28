from PIL import Image

def divisors(number):
    divs = []
    n = 1
    while(n<number):
        if(number%n==0):
            divs.append(n)
        else:
            pass
        n += 1
    divs.append(number)
    return divs
    
d= divisors(55440)
print d
 
img = Image.open('photosynthesis1.png')
pixels_orig = img.load()
(w,h)=img.size
print(w,h)

#try all diffferent aspect ratios for image
count=-1
for divisor in range(1,len(d)):
    count += 1
    width = d[divisor]
    height = 55440/width

    outimg = Image.new( 'RGB', (width,height), "white") 
    pixels_out = outimg.load()     
    
    for j in range(0,w):
        row = j/width
        column = j%width
        (r,g,b) = pixels_orig[j,0]
        pixels_out[column,row] = (r,g,b)
          
    
    outimg.save("photosynthesis_out"+str(count)+".png","png") 