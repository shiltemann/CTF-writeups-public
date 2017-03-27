import itertools
import subprocess

alphabet = "0123456789"

for pin in itertools.permutations(alphabet,6):
    p = ''.join(pin)
    cmd = "echo "+p+"|perl MandM.gif"
    ps = subprocess.Popen(cmd,shell=True,stdout=subprocess.PIPE,stderr=subprocess.STDOUT)
    output = ps.communicate()[0]
    if "HV16" in output:
        print output
