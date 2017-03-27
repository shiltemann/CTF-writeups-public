ct="DK16[OEdo[''lu[;\"Nl[R\"D4[2Qmi"

a1="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
a2="abcdefghijklmnopqrstyvwxyz"
#a3="!\"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ "
a3=" !\"#$%&'()*+,-./:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstyvwxyz{|}~ "
pt=''
for c in ct:
    if c in a1:
        alphabet=a1
    elif c in a2:
        alphabet=a2
    elif c in a3:
        alphabet=a3

    if c not in "0123456789":
        pt += alphabet[ (2*alphabet.find(c)+1)%len(alphabet)]
    else:
        pt+=c

print pt