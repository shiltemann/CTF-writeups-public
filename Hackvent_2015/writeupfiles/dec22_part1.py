from array import array

filename = 'dec22/Hackvent-cd/part1/pass'
outfile = 'dec22_part1_output'

a = array("I", open(filename, "rb").read())
a.byteswap()
open(outfile, "wb").write(a.tostring())
