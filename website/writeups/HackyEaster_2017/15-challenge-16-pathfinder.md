---
layout: writeup
title: 'Pathfinder'
level:
difficulty:
points:
categories: []
tags: []
flag: xdzEPrFsO8jZH0OHLweM

---

## Challenge

Can you find the right path?

## Solution

    $ curl -i hackyeaster.hacking-lab.com:9999
    HTTP/1.1 200 OK
    Content-Type: application/json; charset=utf-8
    Content-Length: 39
    Date: Thu, 06 Apr 2017 04:19:00 GMT
    Connection: keep-alive

    {"Answer":"I only talk to PathFinder!"}

aha, let's pretend we're PathFinder by setting our user-agent as such:

    curl hackyeaster.hacking-lab.com:9999 --user-agent 'PathFinder'

    {"Answer":"Follow one of the possible paths","paths":[1,3,5,8]}

Ok, looks like we need to find our way in a maze of some sort. After
trying many
things, it turned out to be url path, let's choose path `1` by adding
`/1` to url

    curl hackyeaster.hacking-lab.com:9999/1 --user-agent 'PathFinder'

    {"Answer":"Go on! Follow one of the possible paths","paths":[5]}

we are given new options, so we can now visit `/15` etc until we find
our flag:

we automate this in a [python script](writeupfiles/16.py):

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
{: .language-python}

this outputs:

    currently trying: http://hackyeaster.hacking-lab.com:9999/153274689269185
    currently trying: http://hackyeaster.hacking-lab.com:9999/153274689269385174847196523496
    currently trying: http://hackyeaster.hacking-lab.com:9999/1532746892698531748471965239265
    currently trying: http://hackyeaster.hacking-lab.com:9999/153284679269315
    currently trying: http://hackyeaster.hacking-lab.com:9999/15328467926975148384
    currently trying: http://hackyeaster.hacking-lab.com:9999/153284697269175
    currently trying: http://hackyeaster.hacking-lab.com:9999/1532846972697154838
    currently trying: http://hackyeaster.hacking-lab.com:9999/15329468724937
    currently trying: http://hackyeaster.hacking-lab.com:9999/153294687269385
    currently trying: http://hackyeaster.hacking-lab.com:9999/1572846932497
    currently trying: http://hackyeaster.hacking-lab.com:9999/15728469326931587484
    currently trying: http://hackyeaster.hacking-lab.com:9999/157284693269375184348196527926
    currently trying: http://hackyeaster.hacking-lab.com:9999/1572846932697
    currently trying: http://hackyeaster.hacking-lab.com:9999/157284693269753184843196527
    currently trying: http://hackyeaster.hacking-lab.com:9999/15729468326
    currently trying: http://hackyeaster.hacking-lab.com:9999/15729468326935817434817652992654
    currently trying: http://hackyeaster.hacking-lab.com:9999/15729468326935817434871652949658371282597134673
    currently trying: http://hackyeaster.hacking-lab.com:9999/1572946832693581743487165924
    currently trying: http://hackyeaster.hacking-lab.com:9999/157294683269358174843176592
    currently trying: http://hackyeaster.hacking-lab.com:9999/15729468326935817484371652949658371252897134673164289567

    {u'Answer': u'Thanks PathFinder you saved my life by giving me the solution to this sudoku!',
     u'Secret': u'https://hackyeaster.hacking-lab.com/hackyeaster/images/challenge/egg16_UYgXzJqpfc.png',
     u'sudoku': [[0, 0, 0, 2, 0, 4, 6, 0, 0],
                 [2, 0, 9, 0, 0, 0, 0, 0, 0],
                 [0, 0, 0, 0, 0, 6, 5, 0, 0],
                 [0, 0, 6, 5, 0, 0, 7, 1, 0],
                 [0, 0, 0, 9, 0, 0, 0, 4, 0],
                 [7, 3, 1, 0, 0, 0, 0, 0, 0],
                 [0, 7, 0, 0, 3, 0, 0, 0, 8],
                 [0, 8, 0, 0, 2, 7, 0, 3, 1],
                 [0, 1, 4, 0, 6, 0, 0, 0, 0]],
     u'your_solution': [[1, 5, 7, 2, 9, 4, 6, 8, 3],
                        [2, 6, 9, 3, 5, 8, 1, 7, 4],
                        [8, 4, 3, 7, 1, 6, 5, 2, 9],
                        [4, 9, 6, 5, 8, 3, 7, 1, 2],
                        [5, 2, 8, 9, 7, 1, 3, 4, 6],
                        [7, 3, 1, 6, 4, 2, 8, 9, 5],
                        [9, 7, 2, 1, 3, 5, 4, 6, 8],
                        [6, 8, 5, 4, 2, 7, 9, 3, 1],
                        [3, 1, 4, 8, 6, 9, 2, 5, 7]]}

Aha! we've reached the end and the `Secret` url give us our egg:

![](writeupfiles/egg16.png)

