import telnetlib
import string 
from string import ascii_lowercase as lc, ascii_uppercase as uc
import enchant    

 
    
def rot(n):   
    lookup = string.maketrans(lc + uc, lc[n:] + lc[:n] + uc[n:] + uc[:n])
    return lambda s: s.translate(lookup)
    
    
server="web.lasactf.com"
port=4056


f = open('mywords.txt','a')
    

besttrans=''
done=False
while not done:
        # get query from server
    f.flush()
    d=enchant.DictWithPWL("en_US","mywords.txt")   
    
    
    try:
        tn = telnetlib.Telnet(server, port)
        
        num = 0
        while True:
            num +=1
            if num%10 ==0:
                print num
            ct = tn.read_until("\n")
            #print "\nChallenge"+str(num)+": "
            #print ct
            
            if "Incorrect" in ct:
                print ":'("
                break
            #elif num>1:
            #    f.write('\n'.join(besttrans.split(" ")))
                
    
            # find rotation amount resulting in best translation
            maxcount=0
            done2=False
            besttrans="dummy"
            for i in range(0,26):
                pt = rot(i)(ct)
                count=0
                badcount=0
                for word in pt.split(' '):
                    if d.check(word):
                        count+=1
                    else:
                        badcount+=1
                        if badcount > 9:
                            break
                      
                    if count >1:
                        besttrans = pt.strip()
                        tn.write(besttrans)
                        tn.write('\n')
                        done2=True
                        break

                if done2:
                    break
                        
            
            # send this back to server
            #print "Our Solution: "
            #print besttrans   
                
            #tn.write(besttrans)
            if count <=1:            
                tn.write('\n')
    
        print num 
        if num >=100:
            done=True
        
    except:
        print ct
print ct        
