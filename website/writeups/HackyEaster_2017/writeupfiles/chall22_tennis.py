import telnetlib
import hashlib
import sys
sys.path.append('hashlookup')
from LookupTable import LookupTable

server = "hackyeaster.hacking-lab.com"
port = 8888

# start the match
tn = telnetlib.Telnet(server, port)
print tn.read_until("Ready for the game?")
tn.write("y\n" )
print tn.read_until("Let's go!\n")


l = LookupTable('sha2-256', 'wordlist-sha2-256.idx', 'wordlist.txt')

# play the game
while True:
    # get it
    h = tn.read_until('\n').strip()
    print "h: "+h

    # crack it
    t = l.get_all([h])
    t = str(t[h])

    # return it
    print "Sending answer: " + t
    tn.write(t+"\n" )

    # print scoreboard
    print tn.read_until("----------------------")
    print tn.read_until("----------------------")
    print tn.read_until('\n')
