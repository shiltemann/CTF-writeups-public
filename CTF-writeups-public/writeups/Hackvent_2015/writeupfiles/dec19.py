from suds.client import Client
import itertools
import math

def factors(n):
	fact=[1,n]
	check=2
	rootn=math.sqrt(n)
	while check<rootn:
		if n%check==0:
			fact.append(check)
			fact.append(n/check)
		check+=1
	if rootn==check:
		fact.append(check)
        fact.sort()
	return fact


url = 'http://hackvent.org/DailyS04p/server.php?wsdl'
client = Client(url)
print client

session = client.service.getSession()



# submit 20 solutions:
for _ in range(0,20):
    quest = client.service.getQuest(session)   
    
    print 'Quest: '+quest
    equation=quest.split(' = ')
    target= int(equation[1])
    
    ft= factors(target)
        
    for c in itertools.combinations(ft,3):
        if c[0]*c[1]*c[2]==target and 1 not in c:
            solstring = str('*'.join(map(str,sorted(c))))
            print 'Our solution: '+solstring
            response = client.service.submitSolution(session,solstring )
            print 'Response: '+response