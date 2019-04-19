# HackyEaster 2019

The annual advent calender from Hacking-lab

![](writeupfiles/title.png)


## Overview

Title                                                        | Difficulty | Egg
-------------------------------------------------            | ---------- | ------------------------------
[Teaser     ](#teaser)                                       |            | `he19-th1s-isju-5tAt-Eazr`
[01 - Twisted](#01-twisted)                                  |            | `he19-`
[02 - Just Watch](#02-just-watch)                            |            | `he19-DwWd-aUU2-yVhE-SbaG`
[03 - Sloppy Encryption](#03-sloppy-encryption)              |            | `he19-`
[04 - Disco 2](#04-disco-2)                                  |            | `he19-`
[05 - Call for Papers](#05-call-for-papers)                  |            | `he19-`
[06 - Dots](#06-rots)                                        |            | `he19-`
[07 - Shell we Argument](#07-shell-we-argument)              |            | `he19-Bxvs-Vno1-9l9D-49gX`
[08 - Modern Art](#08-modern-art)                            |            | `he19-`
[09 - rorriM rorriM](#09-rorrim-rorrim)                      |            | `he19-`
[10 - Stackunderflow](#10-stackunderflow)                    |            | `he19-`
[11 - Memeory 2.0](#11-memeory-2-0)                          |            | `he19-`
[12 - Decrypt0r](#12-decrypt0r)                              |            | `he19-`
[13 - Symphony in HEX](#13-symphony-in-hex)                  |            | `he19-`
[14 - White Box](#14-white-box)                              |            | `he19-`
[15 - Seen in Steem](#15-seen-in-steem)                      |            | `he19-`
[16 - Every-Thing](#16-every-thing)                          |            | `he19-`
[17 - New Egg Design](#17-new-egg-design)                    |            | `he19-`
[18 - Egg Storage](#18-egg-storage)                          |            | `he19-`
[19 - CoUmpact DiAsc](#19-coumpact-diasc)                    |            | `he19-`
[20 - Scrambled Egg](#20-scrambled-egg)                      |            | `he19-`
[21 - The Hunt: Misty Jungle](#21-the-hunt-misty-jungle)     |            | `he19-`
[22 - The Hunt: Muddy Quagmire](#22-the-hunt-muddy-quagmire) |            | `he19-`
[23 - The Maze](#23-the-maze)                                |            | `he19-`
[24 - CAPTEG](#24-capteg)                                    |            | `he19-`
[25 - Hidden Egg 1](#25-hidden-egg-1)                        |            | `he19-`
[26 - Hidden Egg 2](#26-hidden-egg-2)                        |            | `he19-`
[27 - Hidden Egg 3](#27-hidden-egg-3)                        |            | `he19-`


## Teaser

**Challenge**

In order to sweeten the waiting time, we are providing a teaser challenge in advance.
Download the [video file](writeupfiles/he2019_teaser.mp4), and find the teaser Easter egg.

**Solution**

The video seems to flash different colours. We use ffmpeg to find the number of frames:

```bash
$ ffprobe -v warning -show_frames he2019_teaser.mp4 | grep -c '\[/FRAME\]'
230400
```

so we find that it has 230400 frames, which all appear to be a single colour (mostly black and white, some purple).
230400 is exactly 480x480, so maybe each frame is a pixel and we need to convert this to an image, hopefully a QR code
let's try it:

```python3

import moviepy.editor as mpe
from PIL import Image

# load video
video = mpe.VideoFileClip('he2019_teaser.mp4')


outimg = Image.new( 'RGB', (480,480), "white")
pixels_out = outimg.load()

framenum = 0
for frame in video.iter_frames():

    # set pixel to colour of video frame
    pixels_out[framenum%480,framenum/480] = (frame[0][0][0],frame[0][0][1],frame[0][0][2])
    framenum += 1

# save image
outimg.save("teaser_out.png","png")

```

which gives us the following image:

![](writeupfiles/teaser_out.png)

**Egg**

```
he19-th1s-isju-5tAt-Eazr
```

## 01 Twisted

**Challenge**

As usual, the first one is very easy - just a little twisted, maybe.

![](writeupfiles/twisted.png)

**Solution**

**Egg**


## 02 Twisted

**Challenge**
Just watch and read the password.

![](writeupfiles/justWatch.gif)

Then enter it in the egg-o-matic below. Lowercase only, and no spaces!

**Solution**

It's in ASL finger spelling! They seem to have even used the hands from the following alphabet chart:

![](./writeupfiles/abc1280x960.png)

I spells out `givemeasign`, which we enter into the egg-o-matic to get our egg.

**Egg**

![](./writeupfiles/87340bc4296ff0f53b41c5ef2312139e1af818d4.png)

`he19-DwWd-aUU2-yVhE-SbaG`

## 03 - Sloppy Encryption

**Challenge**

The easterbunny is not advanced at doing math and also really sloppy.

He lost the encryption script while hiding your challenge. Can you decrypt it?

```
K7sAYzGlYx0kZyXIIPrXxK22DkU4Q+rTGfUk9i9vA60C/ZcQOSWNfJLTu4RpIBy/27yK5CBW+UrBhm0=
```

[sloppy.rb](writeupfiles/sloppy.rb)

**Solution**

**Egg**

## 04 Twisted

**Challenge**

**Solution**

**Egg**

## 05 Twisted

**Challenge**

**Solution**

**Egg**

## 06 Twisted

**Challenge**

**Solution**

**Egg**

## 07 Shell we argument

**Challenge**
Let's see if you have the right arguments to get the egg.

**Solution**
It's a bash script in two parts. First half is a series of variables, followed by a series of variable expansions. Extracting the first half into a file and sedding it into a file full of sed's:

```console
sed -i 's/$ACz/he/g' eggi.sh
sed -i 's/$CCz/ec/g' eggi.sh
sed -i 's/$iHz/Gr/g' eggi.sh
sed -i 's/$vEz/na/g' eggi.sh
sed -i 's/$LBz/ye/g' eggi.sh
sed -i 's/$OFz/aw/g' eggi.sh
sed -i 's/$kDz/ u/g' eggi.sh
sed -i 's/$lEz/r"/g' eggi.sh
sed -i 's/$GBz/Pz/g' eggi.sh
sed -i 's/$sDz/at/g' eggi.sh
```

and then running that on the eggi.sh script, we can find a [mostly readable shell script](./writeupfiles/eggi2.sh). Some of the replacements didn't work perfectly and it wasn't worth figuring out exactly.

Based on some of the lines:

```
if [ "$1" != "-R" ]; then
echo "Sorry, but I don$jDz understand your argument. $1 is rather an esoteric statement, isn$jDz it?"

Czit -1
fi
if [ "$3" != "-a" ]; then
echo "Oh no, not that again. $3 really a very boring type of argument"

Czit -1
fi
if [ "$5" != "-b" ]; then
echo "$UEzm clueless w
Ez you bring such a strange argument as $5?. I know you can do better"

Czit -1
fi
if [ "$7" != "-I" ]; then
echo "$7 always makes me mad. If you wanna discuss with be, then you should bring the right type of arguments, really!"
```

We know the odd numbered arguments should be the flags above, and based on

```bash
function e() {
	if [[ $1 -lt $2 ]]; then
		low=$((low + 1))
	elif [[ $1 -gt $2 ]]; then
		high=$((high + 1))
	else
		match=$((match + 1))
	fi
}
e $2 465
e $4 333
e $6 911
e $8 112
e ${10} 007
```

We can construct the final CLI:

```
$ bash eggi.sh -R 465 -a 333 -b 911 -I 112 -t 007
Ahhhh, finally! Let's discuss your arguments
...
Great, that are the perfect arguments. It took some time, but I'm glad, you see it now, too!
```

This opens a URL in our browser session with the egg.

**Egg**

![](./writeupfiles/a61ef3e975acb7d88a127ecd6e156242c74af38c.png)

## 08 Modern Art

**Challenge**
Do you like modern art?

![](./writeupfiles/modernart.jpg)

**Solution**

The corners all read "remove me". Replacing them the correctly results in a QR code that reads "isn't that a bit too easy?"

![](./writeupfiles/modernart2.jpg)

**Egg**

## 09 Twisted

**Challenge**

**Solution**

**Egg**

## 10 Twisted

**Challenge**

**Solution**

**Egg**As usual, the first one is very easy - just a little twisted, maybe.

## 11 Twisted

**Challenge**

**Solution**

**Egg**

## 12 Twisted

**Challenge**

**Solution**

**Egg**

## 13 Twisted

**Challenge**

**Solution**

**Egg**

## 14 Twisted

**Challenge**

**Solution**

**Egg**

## 15 Twisted

**Challenge**

**Solution**

**Egg**

## 16 Twisted

**Challenge**

**Solution**

**Egg**

## 17 Twisted

**Challenge**

**Solution**

**Egg**

## 18 Twisted

**Challenge**

**Solution**

**Egg**

## 19 Twisted

**Challenge**

**Solution**

**Egg**

## 20 Twisted

**Challenge**

**Solution**

**Egg**

## 21 Twisted

**Challenge**

**Solution**

**Egg**

## 22 Twisted

**Challenge**

**Solution**

**Egg**

## 23 Twisted

**Challenge**

**Solution**

**Egg**

## 24 Twisted

**Challenge**

**Solution**

**Egg**

## 25 Twisted

**Challenge**

**Solution**

**Egg**

## 26 Twisted

**Challenge**

**Solution**

**Egg**

## 27 Twisted

**Challenge**

**Solution**

**Egg**


