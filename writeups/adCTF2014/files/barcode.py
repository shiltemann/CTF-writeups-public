#!/usr/bin/python

import sys
import socket               # Import socket module


#nc adctf2014.katsudon.org 43010
s = socket.socket()         # Create a socket object
host = 'adctf2014.katsudon.org' # Get local machine name
port = 43010                # Reserve a port for your service.

s.connect((host, port))
barcode=''
barcode+=s.recv(4096)
barcode+=s.recv(4096)

print barcode
s.close                     # Close the socket when done



# thinnest section of space or line signifies width of 1
# barcodes always start with 101
# next in units of width 7, can determine number by looking at width of groups
# of line or space:
# 0 - 3211
# 1 - 2221
# 2 - 2122
# 3 - 1411
# 4 - 1132
# 5 - 1231
# 6 - 1114
# 7 - 1312
# 8 - 1213
# 9 - 3112


# characters in our barcodes:
# e2 96 88 = 11
# e2 96 8c = 10
# e2 96 90 = 01
# 20       = 00


# get barcode
#f = open('barcodelist')
#barcodes = f.readlines()
#f.close()
barcodes=[]
barcodes.append(barcode)

for barcode in barcodes:
  # print raw value
  tmp="".join("{:02x}".format(ord(c)) for c in barcode)
  print tmp

  tmp=tmp.replace('e29688','11')
  tmp=tmp.replace('e2968c','10')
  tmp=tmp.replace('e29690','01')
  tmp=tmp.replace('2020','00')
  #tmp=tmp.replace('20','!')
  tmp=tmp.replace('0a','')

  print tmp,
  print len(tmp)
  
  grouplen=8
  i=0
  
  #while i<=len(tmp)-grouplen:
  #  print tmp[i:i+grouplen],
  #  i+=grouplen


  final=''
  
  # print ""
  '''
  i=0
  final=''
  finalascii=''

  grouplen=8
  
  while i<=len(tmp)-grouplen:
    group=tmp[i:i+grouplen]
    num=0
    multiplier=1
    for k in range(0,grouplen):
      num += int(group[7-k])*multiplier
      multiplier = multiplier*2
    
    finalascii += chr(num)
    i+= grouplen
    
  print finalascii

  print "sending "+finalascii
  s.send(finalascii)
  print s.recv(4096)
  print s.recv(4096)
  '''
  
  #sys.exit()
  while i<=len(tmp)-grouplen:
    group=tmp[i:i+grouplen]
    print group,
    code=''
    last=''
    streak=0
    for j in range(0,grouplen):
            
      if last=='':
	last=group[j]
	streak=1
      elif group[j] != last:
	code += str(streak)
	last =  group[j]
	streak = 1
      else:
	streak+=1    
      
      # if end of group of zeven, print    
      if j==6:
	code+=str(streak)
	streak=0
	  
    #print code,
    
    if code == "3211":
      final+='0'
    elif code == "2221":
      final+='1'   
    elif code == "2122":
      final+='2' 
    elif code == "1411":
      final+='3' 
    elif code == "1132":
      final+='4' 
    elif code == "1231":
      final+='5' 
    elif code == "1114":
      final+='6' 
    elif code == "1312":
      final+='7' 
    elif code == "1213":
      final+='8'     
    elif code == "3112":
      final+='9'  
    else: 
      final += '?'  
      
    i+=grouplen
    
    

