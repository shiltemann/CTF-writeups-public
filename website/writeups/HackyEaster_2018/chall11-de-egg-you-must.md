---
layout: writeup

title: De Egg you must
level:          # optional, for events that use levels (like HackyEaster)
difficulty: medium    # easy/medium/hard etc, if applicable
points:         # if used
categories: []  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag:

---

## Challenge

Who was first, the cat or the egg?

![](writeupfiles/chall11/cover.jpg)

[basket.zip](writeupfiles/chall11/basket.zip)

## Solution

The zip file is password protected but easily cracked with fcrackzip and this [wordllist](http://mirrors.kernel.org/openwall/wordlists/passwords/)

```bash
$ fcrackzip -v --use-unzip -D -p dictionaries/password basket.zip
found file 'egg1', (size cp/uc 1389653/1433600, flags 9, chk 4f21)
found file 'egg2', (size cp/uc 1426168/1433600, flags 9, chk 4f21)
found file 'egg3', (size cp/uc 1425557/1433600, flags 9, chk 4f21)
found file 'egg4', (size cp/uc 1425787/1433600, flags 9, chk 4f21)
found file 'egg5', (size cp/uc 1423266/1433600, flags 9, chk 4f21)
found file 'egg6', (size cp/uc 362705/384584, flags 9, chk 4f21)


PASSWORD FOUND!!!!: pw == thumper

```

```bash
$ file egg1
egg1: ISO Media, Apple iTunes Video (.M4V) Video

```

The first file looks like a video, but it doesn't play properly. We try to extract frames and
get the following error:


```bash
$ ffmpeg -i egg1.m4v -r 1/1 $filename%03d.jpg
ffmpeg version 3.3.4-2 Copyright (c) 2000-2017 the FFmpeg developers
  built with gcc 7 (Ubuntu 7.2.0-8ubuntu2)
  configuration: --prefix=/usr --extra-version=2 --toolchain=hardened --libdir=/usr/lib/x86_64-linux-gnu --incdir=/usr/include/x86_64-linux-gnu --enable-gpl --disable-stripping --enable-avresample --enable-avisynth --enable-gnutls --enable-ladspa --enable-libass --enable-libbluray --enable-libbs2b --enable-libcaca --enable-libcdio --enable-libflite --enable-libfontconfig --enable-libfreetype --enable-libfribidi --enable-libgme --enable-libgsm --enable-libmp3lame --enable-libopenjpeg --enable-libopenmpt --enable-libopus --enable-libpulse --enable-librubberband --enable-libshine --enable-libsnappy --enable-libsoxr --enable-libspeex --enable-libssh --enable-libtheora --enable-libtwolame --enable-libvorbis --enable-libvpx --enable-libwavpack --enable-libwebp --enable-libx265 --enable-libxvid --enable-libzmq --enable-libzvbi --enable-omx --enable-openal --enable-opengl --enable-sdl2 --enable-libdc1394 --enable-libiec61883 --enable-chromaprint --enable-frei0r --enable-libopencv --enable-libx264 --enable-shared
  libavutil      55. 58.100 / 55. 58.100
  libavcodec     57. 89.100 / 57. 89.100
  libavformat    57. 71.100 / 57. 71.100
  libavdevice    57.  6.100 / 57.  6.100
  libavfilter     6. 82.100 /  6. 82.100
  libavresample   3.  5.  0 /  3.  5.  0
  libswscale      4.  6.100 /  4.  6.100
  libswresample   2.  7.100 /  2.  7.100
  libpostproc    54.  5.100 / 54.  5.100
[mov,mp4,m4a,3gp,3g2,mj2 @ 0x55e2047e2f20] moov atom not found
egg1: Invalid data found when processing input
```

a `moov atom` contains metadata about the video [link](https://developer.apple.com/library/content/documentation/QuickTime/QTFF/QTFFChap2/qtff2.html).

We remember the challenge description `cat or the egg` so we concatenate the different files together to get a working video

```bash
$ cat egg* > eggall
```

We notice some marking at the bottom of the frames during the last second orso, but not sure what that means?

```bash
$ ffmpeg -i eggall -y -ss 24 -an -r 20 frame%03d.jpg
```

