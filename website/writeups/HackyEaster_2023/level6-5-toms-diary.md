---
layout: writeup

title: Tom's Diary
level: 6  # optional, for events that use levels
difficulty: medium
categories: [crypto]
tags: []

flag: he2023{sl4sh3s_m4k3_m3_h4ppy}

---

## Challenge

Tom found a flag and wrote something about it in his diary.

Can you get the flag?

[diary.txt](writeupfiles/diary.txt)

```
// // // // // // // // // // // // // // // // // // // // // //
// // // // // // // // // // // // // // // // // // // // // //

Tom's Diary

\/\ \\\/ / \/\ /\\ /\ \/\ //\ /\/ /\// //\/ /\// /\/ //\ /\\\ \\/\
\/\ \\\/ / \/\ /\\ /\ \/\ //\ /\/ /\// //\/ /\// /\/ //\ /\\\ \\/\

Dear diary,

today I found a secret flag.

I need to keep it safe here:

UEsDBAoACQAAAJJEK1X6oNHsKgAAAB4AAAAIABwAZmxhZy50eHRVVAkAA/OBHWOR
gR1jdXgLAAEE9QEAAAQUAAAArGnVXoZRCLYaWU8HFSFo+dWfh2yfPa868sNqxTVP
xqHrGTs3dIVbxR9WUEsHCPqg0ewqAAAAHgAAAFBLAQIeAwoACQAAAJJEK1X6oNHs
KgAAAB4AAAAIABgAAAAAAAEAAACkgQAAAABmbGFnLnR4dFVUBQAD84EdY3V4CwAB
BPUBAAAEFAAAAFBLBQYAAAAAAQABAE4AAAB8AAAAAAA=

\\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\
\\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\
```

**Hint:** Neither brute force nor word lists are necessary.

## Solution

Let's see what is in this base64 string:

```bash
$ echo "UEsDBAoACQAAAJJEK1X6oNHsKgAAAB4AAAAIABwAZmxhZy50eHRVVAkAA/OBHWORgR1jdXgLAAEE9QEAAAQUAAAArGnVXoZRCLYaWU8HFSFo+dWfh2yfPa868sNqxTVPxqHrGTs3dIVbxR9WUEsHCPqg0ewqAAAAHgAAAFBLAQIeAwoACQAAAJJEK1X6oNHsKgAAAB4AAAAIABgAAAAAAAEAAACkgQAAAABmbGFnLnR4dFVUBQAD84EdY3V4CwABBPUBAAAEFAAAAFBLBQYAAAAAAQABAE4AAAB8AAAAAAA=" | base64 -d

PK
        D+Uflag.txtUT   ccux
                            i^YO!h՟l=:j5Oơ;7t[VP*PK
        D+Uflag.txtUTcux
```

That looks like a zip file, let's output it to a file

```
$ echo "UEsDBAoACQAAAJJEK1X6oNHsKgAAAB4AAAAIABwAZmxhZy50eHRVVAkAA/OBHWORgR1jdXgLAAEE9QEAAAQUAAAArGnVXoZRCLYaWU8HFSFo+dWfh2yfPa868sNqxTVPxqHrGTs3dIVbxR9WUEsHCPqg0ewqAAAAHgAAAFBLAQIeAwoACQAAAJJEK1X6oNHsKgAAAB4AAAAIABgAAAAAAAEAAACkgQAAAABmbGFnLnR4dFVUBQAD84EdY3V4CwABBPUBAAAEFAAAAFBLBQYAAAAAAQABAE4AAAB8AAAAAAA=" | base64 -d > diary.zip
```

let's try to unzip it:

```bash
$ unzip diary.zip
Archive:  diary.zip
[diary.zip] flag.txt password:
```

ok it needs a password, hmm..

the hint tells us we don't need brute force or a wordlist, so we must be able to find the password somewhere.

We google the line of slashes that's in the diary file

```
\/\ \\\/ / \/\ /\\ /\ \/\ //\ /\/ /\// //\/ /\// /\/ //\ /\\\ \\/\
```

And find that this could be [Tom Tom Code](https://www.dcode.fr/tom-tom-code), well that certainly fits with the challenge title. We decode it on [dcode.fr](https://www.dcode.fr/tom-tom-code) and find it translates to

```
slashesforprofit
```

We enter this as the password for the zip file and get our flag!
