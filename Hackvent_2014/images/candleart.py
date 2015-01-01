f = open('candleoneline.txt')
candle= f.readline()
f.close

bf=''
for i in range(0,len(candle)):
  tmp=candle[i]
  if not tmp.strip('[]<>.,+-'):
    bf+=tmp

print bf
