import requests

url="http://web.lasactf.com:45025/"
headers = {'user-agent': 'Google Ultron', 'SpecialAuth':'Kyle', 'referer':'kyleisacoolguy.org'}

r=requests.get(url,headers=headers)
print r.text


