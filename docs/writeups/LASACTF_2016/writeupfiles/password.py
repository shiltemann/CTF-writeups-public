# read file with logins
logins = []
with open('Logins.txt') as infile:
    for line in infile.readlines():
        logins.append(line.rstrip()) 
    
# get set of unique characters in logins    
chars = set(''.join(logins))

# repeatedly find character that only ever appears as first
# then remove that character from all strings and repeat
password=''
for i in range(0,len(chars)):
    highestindex={c:-1 for c in chars}
    
    for l in logins:
        pos = -1
        for c in l:
            pos+=1
            if pos > highestindex[c]:
                highestindex[c]=pos
     
    for c in highestindex:
        if highestindex[c]==0:
            password += c
            logins =[l.replace(c,'') for l in logins]
   
    
print "password: "+ password
    
    
    