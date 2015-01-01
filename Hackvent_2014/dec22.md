# December 22nd: xmas labyrinth
 
**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=22](http://hackvent.hacking-lab.com/challenge.php?day=22)

**Hint**
 
*find the hidden message*
 
**Challenge**
 
Get your daily job here: [file](images/YkZg9Fn5I29coGRi2e8q)
 
**Solution**
 
We have a binary file with no extension. We run the file command to see what we are dealing with: 

```
$ file YkZg9Fn5I29coGRi2e8q 
YkZg9Fn5I29coGRi2e8q: x86 boot sector
```
 
hmm, x86 boot sector, let's try to mount it: 
 
 
```
sudo mount -t vfat -o loop YkZg9Fn5I29coGRi2e8q /mnt/hackvent/
```

We can now see a number of files:

```
-rwxr-xr-x  1 root root    4107 Feb  2  1988 autoexec.bat*
-rwxr-xr-x  1 root root   25308 Feb  2  1988 COMMAND.COM*
-rwxr-xr-x  1 root root     284 Dec 14 10:05 config.sys*
-rwxr-xr-x  1 root root  151758 Feb  2  1988 dos.exe*
-rwxr-xr-x  1 root root 1179426 Feb  2  1988 explorer.exe*
-rwxr-xr-x  1 root root   55029 Aug  8  1988 FDISK.COM*
-rwxr-xr-x  1 root root   11968 Jul 13  1988 FORMAT.COM*
-r-xr-xr-x  1 root root   25613 Jul 18  1988 IO.SYS*
-r-xr-xr-x  1 root root   30128 Jul 24  1987 MSDOS.SYS*
-rwxr-xr-x  1 root root    4921 Jul 13  1988 SYS.COM*
```

Looks like a DOS partition, let's examine the files further:
 
```
$ file *
autoexec.bat: data
COMMAND.COM:  DOS executable (COM)
config.sys:   Zip archive data, at least v2.0 to extract
dos.exe:      PC bitmap, Windows 3.x format, 171 x 294 x 24
explorer.exe: JPEG image data, JFIF standard 1.01, comment: "CREATOR: gd-jpeg v1.0 (using IJG JPEG v62), quality = 80"
FDISK.COM:    DOS executable (COM)
FORMAT.COM:   DOS executable (COM)
IO.SYS:       data
MSDOS.SYS:    DOS executable (COM)
SYS.COM:      DOS executable (COM)
```
 
[config.sys](images/dec22DOS/config.zip) turns out to be a zip archive. There is one text file inside but the
archive is password protected. So seems we need to find the password somehow.

We also see that explorer.exe and dos.exe are actually images:
 
![](images/explorer.jpg)
 
![](images/dos.bmp)
 
 

We also take a closer look at the file [autoexec.bat](images/dec22DOS/autoexec.bat), the header seems 
like a GIF header, but not quite. We fix the file magic in a hex editor:
 
```
 FROM: 47 49 46 36 36 61	GIF66a
 TO:   47 49 46 38 37 61	GIF87a	
```

 
This gives us a valid GIF image with a barcode in it
 
![](images/dec22_autoexec.gif)
 
scanning this leads to:
 
```
 UNIXisuserfriendly
```

This is not the password to the zip archive, so we keep searching.


We view all files in a hex editor to see if we notice anything odd.

explorer.jpeg has the following after the end of its file:

```
X1oxcA==
base64 decoded: _Z1p
```

This is not the password for the zip archive, but may be part of it, the underscore makes it seem like
there should be more before it.

COMMAND.COM, SYS.COM, MSDOS.COM, IO.SYS and FDISK.COM all seem to be original, unaltered DOS files based on their
MD5 hashes. 



```
0387300bfa3d5c9630f27ba1ae9c33ea  autoexec.bat
97e5fe6d5a366d8476a3f6318f70bf09  COMMAND.COM
6dab80abc1e010343ea799c730216e7b  config.zip
ed2bec67901bd7bf78484c178abe62fc  dos.bmp
962b714f6936589da84793da308130f3  explorer.jpg
78fdc488f12082e47b06c3190d50cae7  FDISK.COM
6353686108764c5ad6ec57f0b229c565  FORMAT.COM
04505bd774ccf4be31b860b091620322  IO.SYS
71b8ccdf9c1bdfcb1cd5f5f3aff1f6ff  MSDOS.COM
4bec5fc4855c7dad0a81256abc8cfd96  SYS.COM
```

The `_Z1p` string we found seems like it may be a step toward opening the archive password, but we need more. 
We do a bit more exploring in [SleuthKit/Autopsy](http://www.sleuthkit.org/), and find the following information

```
FILE SYSTEM INFORMATION

File System Type: FAT12

OEM Name: ckRfZm9y
Volume ID: 0xdeadface
Volume Label (Boot Sector): 
Volume Label (Root Directory): UDRzc3cw
File System Type Label: �3��м|

Sectors before file system: 0

File System Layout (in sectors)
Total Range: 0 - 5759
* Reserved: 0 - 0
** Boot Sector: 0
* FAT 0: 1 - 9
* FAT 1: 10 - 18
* Data Area: 19 - 5759
** Root Directory: 19 - 33
** Cluster Area: 34 - 5759
```

Both the OEM name and the volume label seem like they could be part of a base64 string, so we see if we can combine them
with the string we already had, and indeed, this gives us the password for the zip file!


```
UDRzc3cwckRfZm9yX1oxcA==
P4ssw0rD_for_Z1p
```


We can now unzip the archive, and we get the following file:

```
ElGamal

Public Key:

p=CFF3829FE2BC008D
g=2367CA6FE33CF1A9
y=42F357F7636AA02F

Message:

a=7D3BDC843CE75CD3
b=275E625204563FAC
```

Seems like the next step is crypto. [ElGamal](http://en.wikipedia.org/wiki/ElGamal_encryption) encryption to be precise.

to decode the message we need to find secret key x. This involves solving the [discrete logarithm problem](http://en.wikipedia.org/wiki/Discrete_logarithm). 
This is hard to do in general, but when p is a smooth number (a number which factors completely into small prime numbers)
it becomes easy to compute.

p is smooth number with factors: 

```
2*2*3*7*83*5531*56629*6861851
```

This means we can use the [Pohlig-Hellman](http://en.wikipedia.org/wiki/Pohlig%E2%80%93Hellman_algorithm) and 
[Pollard's rho](http://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm_for_logarithms) algorithms to find secret key x. 
There is even an [online tool](http://www.alpertron.com.ar/DILOG.HTM) which will do this for us. We enter the p as the modulo, 
g as the generator, and y as the power, and is will return the exponent x, which is our secret key.

```
3888521305394705767
```

Now all we need to do is use the secret key to decrypt the message by calculting `b / a^x mod p`.

We do this in a small python [script](images/dec22.py). We convert the resulting number to hex and then to an ascii string.


```python

def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('modular inverse does not exist')
    else:
        return x % m
        
# mdular pow
def modexp_rl(a, b, n):
    r = 1
    while 1:
        if b % 2 == 1:
            r = r * a % n
        b /= 2
        if b == 0:
            break
        a = a * a % n
    return r
    

# ElGamal parameters   
p=0xCFF3829FE2BC008D  
g=0x2367CA6FE33CF1A9  
y=0x42F357F7636AA02F  
a=0x7D3BDC843CE75CD3  
b=0x275E625204563FAC  

# secret key
x=3888521305394705767    
    
# decrypt message,p using x
pt=(b* modinv( modexp_rl(a,x,p),p))%p

# print message
print "decrypted message: "+str(pt)
pt=hex(pt).rstrip("L").lstrip("0x")
print "hex value:         "+ pt
print "ascii :            "+''.join(chr(int(pt[i:i+2], 16)) for i in range(0, len(pt), 2))
 
```

This gives us the following output:

```
decrypted message: 6013542897370025543
hex value:         537465676f504e47
ascii :            StegoPNG
```

This is still not the password accepted by the ball-o-matic, it would seem there is another step.

[StegoPNG](http://stego-png.soft112.com/) is a Windows tool to hide data in images, sounds like this could be the next step.

We install StegoPNG and supply it with the [BMP file](images/dos.bmp) of the fingerprint, and enter `UNIXisuserfriendly` as the key, and find 
the following hidden message:

```
Don't code today what you can't debug tomorrow
```

We input this into the ball-o-matic to get the bauble:

![](images/ehQ1qGibPqAizL_jr-HH.png)


**Flag**

```
HV14-wcnv-2OhE-xKqe-lTGp-WGmn
```

