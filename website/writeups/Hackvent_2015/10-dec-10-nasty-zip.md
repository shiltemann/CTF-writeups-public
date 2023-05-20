---
layout: writeup
title: 'Dec 10: Nasty Zip'
level:
difficulty:
points:
categories: []
tags: []
flag:  HV15-iQYf-adNg-o4S9-JHc7-vfWu

---

## Challenge

*its so nasty, isnt it?*

get the ZIP, you'll know what's to do!

[zip file](writeupfiles/nasty-Zip.zip)

## Solution

The zip contains another zip, `1.zip`, which contains another zip,
`2.zip`, etc.. Lookz like it's zip files all the way down!

A peek in the `strings` command output leads us to suspect there are
31337 levels to this zipception

We unzip all the way down using the following bash script:

    #!/bin/bash

    unzip nasty-Zip.zip

    level=1

    while [[ $level -lt 31337 ]]
    do
        unzip -q -o -d  ${level} "${level}.zip"
        nextlevel=$[$level+1]
        cp "${level}/${nextlevel}.zip" .
        rm -R ${level}
        rm "${level}.zip"
        level=$nextlevel
    done
{: .language-bash}

probably not the fastest way but it worked..

the final zip file, `31337.zip` contains a text file, `worst.500`, but
is password protected. We check if it has a short password by
bruteforcing it with all passwords upto length 6 with `fcrackzip`

    $ fcrackzip -u -l 1-6 31337.zip


    PASSWORD FOUND!!!!: pw == love
{: .language-bash}

Yay! The zip file contained a text file with the nugget



