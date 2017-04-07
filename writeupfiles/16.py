import requests

url = 'http://hackyeaster.hacking-lab.com:9999'

s = requests.Session()

r = s.get(url)
print r.text
# {"Answer":"I only talk to PathFinder!"}, so pretend we're pathfinder:


headers = {'User-Agent': 'PathFinder', 'path': [1]}
r = s.get(url, headers=headers)
print r.text
# {"Answer":"Follow one of the possible paths","paths":[1,3,5,8]}, try to pick one?:

# hmm, how to format our path string?
payload = {
    'path': '8'
}
r = s.post(url, headers=headers, data=payload, cookies = payload)
print r.text
print r.headers
print dict(r.cookies)
