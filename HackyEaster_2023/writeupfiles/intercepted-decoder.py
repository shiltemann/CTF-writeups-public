#!/usr/bin/env python3
tr = {
    'a': "\n",
    'b': " ",
    'c': ":",
    'd': ".",
    'e': "0",
    'f': "1",
    'g': "2",
    'h': "3",
    'i': "a",
    'j': "b",
    'k': "c",
    'l': "d",
    'm': "e",
    'n': "f",
    'o': "g",
    'p': "h",
    'q': "i",
    'r': "k",
    's': "l",
    't': "m",
    'u': "n",
    'v': "o",
    'w': "p",
    'x': "r",
    'y': "s",
    'z': "t",
    'A': "u",
    'B': "v",
    'C': "w",
    'D': "y",
    'E': "_",
    'F': "A",
    'G': "B",
    'H': "E", #
    'I': "H", #
    'J': "I", #
    'K': "K",
    'L': "L",
    'M': "M",
    'N': "N",
    'O': "P",
    'P': "R",
    'Q': "S",
    'R': "T",
    'S': "U",
    'T': "{",
    'U': "}",
    'V': "?",
    'W': "?",
    'X': "?",
    'Y': "?",
    'Z': "?",
}
data = open('intercepted_message-a.txt', 'r').read().replace('\n', '').replace(' ', '')

intab = "".join(tr.keys())
outtab = "".join(tr.values())
trantab = str.maketrans(intab, outtab)

#print(data)
print("\n\n\n\n")
print(data.translate(trantab))



data = open('intercepted_message.txt', 'r').read().replace('\n', ' ').split(' ')
data = map(int, data[0:-1])
print(''.join([chr(x // 313) for x in data]))
