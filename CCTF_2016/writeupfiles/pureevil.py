factor=16384

done=False
count=0
total = 0
while not done:
    count += 1
    number = factor*count
    if len(str(number)) == 10:
        reverse = str(number)[::-1]
        if str(number) == reverse:
            print "ten-digit palindrome number found: "+str(number)
            total += number
    if len(str(number)) > 10:
        done = True

print "sum: "+str(total)