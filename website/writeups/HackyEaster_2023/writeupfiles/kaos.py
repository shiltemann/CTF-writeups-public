options = 0
for d14 in range(0,9):
  for b7 in range(0,9):
    for e2 in range(0,9):
      for g14 in range(0,9):
        for j6 in range(0,9):

          # equations for h
          i10 = (j6*g14+b7)      % 64
          f12 = (e2*b7+d14)      % 64
          d11 = (e2*g14)         % 64
          j13 = (d14 +b7*e2)     % 64
          b5 = (j13+d11+f12+i10) % 64

          # equations for e
          h3 = (b7*j6*7)        % 64
          g6 = (g14*b7+d14)     % 64
          i7 = (f12+d11*g6+h3)  % 64

          # equations for '2'
          d5 = (e2+j6+b7+d14+g14) % 64
          f4 = (e2*g14+d14+j6)    % 64
          j3 = (f12+d11+d5+f4)    % 64

          if b5 == 52 and i7 == 57 and j3 == 2:
            options += 1
            print("d14: "+str(d14)+" b7: "+str(b7)+" e2: "+str(e2)+" g14: "+str(g14)+" j6: "+str(j6))

print("Options: "+ str(options))
