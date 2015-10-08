# -*- coding: utf-8 -*-
"""
Created on Mon Apr  6 19:34:42 2015

@author: zazkia
"""

import requests

url="http://hackyeaster.hacking-lab.com/hackyeaster/sharks/sharks.html?user=supershark&pass=hashed!!!&hash=b3f3ca462d3fa58b74d6982af14d8841b074994a"

s=requests.Session()
#s.get(url)

payload={'user':'supershark',
         'pass':'hashed!!!',
         'hash':'b3f3ca462d3fa58b74d6982af14d8841b074994a' }
         
r=s.get(url,auth=('sharkman','sharks_have_j4ws'), params=payload)
print r.text

r2=s.post(url,data=payload)      
print r2.text   