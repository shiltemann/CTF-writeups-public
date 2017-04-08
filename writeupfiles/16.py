import requests
import json
import pprint

count = 0

def findpath(current_url, mypaths):
    global count
    for p in mypaths:
        count += 1
        if count %100 == 0:
            print "currently trying: " + current_url
        new_url = current_url+str(p)
        r = s.get(new_url, headers=headers)
        response = json.loads(r.text)

        if response['Answer'] == "You've left the path!":
            print "error"
            exit()

        # recurse
        elif response['Answer'] == "Go on! Follow one of the possible paths":
            findpath(new_url, response['paths'])

        # if other response, we found it maybe?
        elif response['Answer'] != "This leads to nowhere, so turn around!":
            pprint.pprint(response)
            exit()

    # backtrack


url = 'http://hackyeaster.hacking-lab.com:9999/'
headers = {'User-Agent': 'PathFinder'}
s = requests.Session()

# recursively follow path until we get to an ending
r = s.get(url, headers=headers)
response = json.loads(r.text)
findpath(url, response['paths'])
