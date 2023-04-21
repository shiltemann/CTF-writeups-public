from collections import Counter
import itertools
import sys

text_file = open('singular.txt', 'r')
text = text_file.read()
text_clean = text.replace("he2023{","").replace("}","").replace("_"," ")

# get list of all words and lines
words_all = text_clean.replace("\n"," ").split()
lines_all = text_clean.split("\n")[:-1]

print("total words: "+str(len(words_all)))
print("total lines: "+str(len(lines_all)))

# find frequencies
words_freq = Counter(words_all)
lines_freq = Counter(lines_all)
#print(sorted(freq.items(), key=lambda k: -k[1]))

# filter unique ones
words_unique = [wordcount[0] for wordcount in words_freq.items() if wordcount[1] == 1 ]
lines_unique = [linecount[0] for linecount in lines_freq.items() if linecount[1] == 1 ]
## there are 1016 lines that appear only once in the file

print("unique words: "+str(len(words_unique)))
print("unique lines: "+str(len(lines_unique)))

# look per column, which flags have a unique first word?
w0 = [w.split()[0] for w in lines_unique ]
w1 = [w.split()[1] for w in lines_unique ]
w2 = [w.split()[2] for w in lines_unique ]
w3 = [w.split()[3] for w in lines_unique ]
wall = w0+w1+w2+w3

f0 = Counter(w0)
f1 = Counter(w1)
f2 = Counter(w2)
f3 = Counter(w3)
fall = Counter(wall)

u0 = [word[0] for word in f0.items() if word[1] == 1 ]
u1 = [word[0] for word in f1.items() if word[1] == 1 ]
u2 = [word[0] for word in f2.items() if word[1] == 1 ]
u3 = [word[0] for word in f3.items() if word[1] == 1 ]
uall =  [word[0] for word in fall.items() if word[1] == 1 ]

print("unique words per column of unique lines: "+str(len(u0)) +", "+str(len(u1)) +", "+str(len(u2)) +", "+str(len(u3)))

print("unique words across all columns of unique lines: "+str(len(uall)))
print(uall)

# filter for all words unique in their column (yields 15)
uniqueall = [line for line in lines_unique if line.split()[0] in u0 and line.split()[1] in u1 and line.split()[2] in u2 and line.split()[3] in u3]
print(uniqueall)
print(len(uniqueall))

# filter for all words unique across all unique lines (yields none)
uniqueall2 = [line for line in lines_unique if line.split()[0] in uall and line.split()[1] in uall and line.split()[2] in uall and line.split()[3] in uall]
print(uniqueall2)

for flag in uniqueall:
    print("he2023{"+flag.split()[0]+"_"+flag.split()[1]+"_"+flag.split()[2]+"_"+flag.split()[3]+"}")

# filter progressively by column? no that would just give more answers cuz more words unique the more you filter.. what am i missing?

#unique_col1 = [line for line in lines_unique if len(line.split())>0 and line.split()[0] in u0]
#w1 = [w.split()[1] for w in unique_col1 if len(w)>0 ]
#f1 = Counter(w1)
#u1 = [word[0] for word in f1.items() if word[1] == 1 ]

#unique_col2 = [line for line in unique_col1 if len(line.split())>0 and line.split()[0] in u1]
#w2 = [w.split()[1] for w in unique_col1 if len(w)>0 ]
#f2 = Counter(w1)
#u2 = [word[0] for word in f1.items() if word[1] == 1 ]

#unique_col3 = [line for line in unique_col2 if len(line.split())>0 and line.split()[0] in u2]
#w3 = [w.split()[1] for w in unique_col1 if len(w)>0 ]
#f3 = Counter(w1)
#u3 = [word[0] for word in f1.items() if word[1] == 1 ]

#unique_col4 = [line for line in unique_col3 if len(line.split())>0 and line.split()[0] in u3]

#print(unique_col4)
