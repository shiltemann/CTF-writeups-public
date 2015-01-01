# December 9th: iPhone Forensic

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=9](http://hackvent.hacking-lab.com/challenge.php?day=9)

**Hint**

reveal the message

**Challenge**

Get your daily work: [file](images/iPh0n3)

**Solution**


The file contains an mysql database. We open the database in sqliteman. We see a chat message. 


```
==Nn0EUp68lYbS2LeMKMhEaYbS2Leyzoa1PouWzYw9JoiRQJFS3qT10IIuxY3Szq

```

This looks like base64 string but reveresed. We also see some references to ROT13 in the database, so we try to combine ROT13, reverse, and base64 decode to find the message

```
==Nn0EUp68lYbS2LeMKMhEaYbS2Leyzoa1PouWzYw9JoiRQJFS3qT10IIuxY3Szq
reverse: qzS3YxuII01Tq3SFJQRioJ9wYzWuoP1aozyeL2SbYaEhMKMeL2SbYl86pUE0nN==
ROT13: dmF3LkhVV01Gd3FSWDEvbW9jLmJhbC1nbmlrY2FoLnRuZXZrY2FoLy86cHR0aA==
Base64 decode: vaw.HUWMFwqRX1/moc.bal-gnikcah.tnevkcah//:ptth
reverse: http://hackvent.hacking-lab.com/1XRqwFMWUH.wav
```

This is a link to [a wav file](images/XRqwFMWUH.wav).

The audio file contains what sounds like DTMF tones. We can decode them online [here](http://dialabc.com/sound/detect/index.html).
This gives us the following list of keys pressed:


```
33#97#122#33#110#103#97
```

These are ascii character encodings, with # as a separator. It reads:


```
Baz!nga
```

We put this into ball-o-matic to get the bauble:


![](images/Vhy8652eec21rVdRdYvA.png)



**Flag**

```
HV14-EX1a-UVIZ-3f7z-JwSw-I7lo
```

