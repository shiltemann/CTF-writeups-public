#!/usr/bin/env python
# encoding: utf-8
import csv
import sys


REPEAT = 1000

keys = []

with open(sys.argv[1], 'rb') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',')

    firsttime = 0
    prev_key = None

    for row in spamreader:
        ts = int(row[0])
        ch = int(row[1])

        if firsttime == 0:
            firsttime = ts
            prev_key = ch

            keys.append([ch])
        else:
            if prev_key == ch:
                if ts - firsttime > REPEAT:
                    keys.append([ch])
                else:
                    keys[-1] += [ch]
            else:
                keys.append([ch])

        firsttime = ts
        prev_key = ch



chars = {
    0: " 0",
    1: ".,'?!\"1-()@/:",
    2: "abc2",
    3: "def3",
    4: "ghi4",
    5: "jkl5",
    6: "mno6",
    7: "pqrs7",
    8: "tuv8",
    9: "wxyz9",
    '*': "@/:_;+&%*[]{}",
    '#': ['t9', 'T9', 'abc', 'ABC'],
}


def decode(ch, count):
    if ch == 10:
        ch = '*'
    elif ch == 11:
        ch = '#'

    try:
        sys.stdout.write(chars.get(ch)[count - 1])
    except:
        sys.stdout.write('¿')


def decodeMeta(group):
    return {
        100: 'left',
        101: 'right',
        102: 'up',
        103: 'down',
        104: 'accept',
        105: 'reject',
    }.get(group)

for group in keys:
    if sum(group) >= 100:
        print([decodeMeta(x) for x in group])
        continue

    decode(group[0], len(group))
