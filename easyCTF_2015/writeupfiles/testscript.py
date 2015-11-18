f=open("if-logic.in","r")
fout=open("if-logic.out","w+")
  
numbers=f.readline().rstrip().split(",")

for n in numbers:
    n=int(n)    
    if n <= 50 and n >=0:
        fout.write("hi")
    elif n > 50 and n <=100:
        fout.write("hey")
    else:
        fout.write("hello")
    fout.write("\n")    

f.close()
fout.close()
