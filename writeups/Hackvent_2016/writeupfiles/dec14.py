import binascii

with open("xmas_1Msps.cfile") as f:
    inbytes = f.readline()
    print binascii.hexlify(inbytes)
