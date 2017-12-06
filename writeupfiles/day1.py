import requests
import re

url="http://adventofcode.com/2017/day/1/input"
url2="http://adventofcode.com/2017/day/1/answer"

cookies = {'session': '53616c7465645f5f9b4fa58a8dd0de4217fdc7ee993d93ea7685b7f370745a902c565ea941dac2c73fc5b71da6546b4a'}

r = requests.get(url, cookies=cookies)
numbers = r.text.rstrip()
l = len(numbers)

# calculate the answer
answer = 0
for i in range(0,l):
    if numbers[i] == numbers[ (i+int(l/2))%l ]:
        answer += (ord(numbers[i])-ord('0'))
    print(answer)

# send answer
print('Sending answer: '+ str(answer))
r2 = requests.post(url2, cookies=cookies, data={'level':'2', 'answer': str(answer)})

print(r2.text)
