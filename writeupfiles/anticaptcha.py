from bs4 import BeautifulSoup
import requests
from fractions import gcd as _gcd
import math
import re
import sys

URL = sys.argv[1]



def isprime(n):
    n = int(n)
    if n % 2 == 0 and n > 2:
        return False
    return all([n % i for i in range(3, int(math.sqrt(n)) + 1, 2)])


def gcd(a, b):
    return _gcd(int(a), int(b))


def nthword(a, b):
    return b.split(' ')[int(a) - 1]


asdf = {
    'What is the greatest common divisor of (?P<a>[0-9]+) and (?P<b>[0-9]+)?': gcd,
    'Is (?P<a>[0-9]+) a prime number?': isprime,
    'What is the (?P<a>[0-9]+).. word in the following line:(?P<b>.*)': nthword,


    'What is the tallest mountain on Earth?': lambda: "Everest",
    'What is the capital of Hawaii?': lambda: "Honolulu",
    'What color is the sky?': lambda: "blue",
    'What year is it?': lambda: "2018",
    'Who directed the movie Jaws?': lambda: "Steven Spielberg",
    'What is the capital of Germany?': lambda: "berlin",
    'Which planet is closest to the sun?': lambda: "mercury",
    'How many strings does a violin have?': lambda: "4",
    'How many planets are between Earth and the Sun?': lambda: "2",

}


data = requests.get(URL)

answers = []

html_doc = open('index.html', 'r').read()
soup = BeautifulSoup(html_doc, 'html.parser')
for idx, td in enumerate(soup.find_all('td')):
    if idx % 2 == 1:
        continue

    text = td.text.replace('\n', '')

    matched = False
    for (m, func) in asdf.items():
        match = re.match(m, text)
        if match:
            matched = True
            answers.append(str(func(*match.groups())))

    if not matched:
        print("> %s <" % text)



r = requests.post(URL, headers={'Content-type': 'application/x-www-form-urlencoded'}, data={'answers': answers})
print(r.text)
