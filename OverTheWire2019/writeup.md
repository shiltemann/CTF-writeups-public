# Over the Wire 2019


## Overview


Title                                             | Category    | Points | Flag
------------------------------------------------- | ----------- | ------ | ------------------------------
[December 1 ](#day-01)                            | Easy        | 2/1    | `HV18-`
[December 2 ](#day-02)                            | Easy        | 2/1    | `HL18-`
[December 3 ](#day-03)                            | Easy        | 2/1    | `HV18-`
[December 4 ](#day-04)                            | Easy        | 2/1    | `HV18-`
[December 5 ](#day-05)                            | Easy        | 2/1    | `HV18-`
[December 6 ](#day-06)                            | Easy        | 2/1    | `HV18-`
[December 7 ](#day-07)                            | Easy        | 2/1    | `HV18-`
[December 8 ](#day-08)                            | Medium      | 3/2    | `HV18-`
[December 9 ](#day-09)                            | Medium      | 3/2    | `HV18-`
[December 10](#day-10)                            | Medium      | 3/2    | `HV18-`
[December 11](#day-11)                            | Medium      | 3/2    | `HV18-`
[December 12](#day-12)                            | Medium      | 3/2    | `HV18-`
[December 13](#day-13)                            | Medium      | 3/2    | `HV18-`
[December 14](#day-14-)                           | Medium      | 3/2    | `HV18-`
[December 15](#day-15-)                           | Hard        | 4/3    | `HV18-`
[December 16](#day-16-)                           | Hard        | 4/3    | `HV18-`
[December 17](#day-17-)                           | Hard        | 4/3    | `HV18-`
[December 18](#day-18-)                           | Hard        | 4/3    | `HV18-`
[December 19](#day-19-)                           | Hard        | 4/3    | `HV18-`
[December 20](#day-20-)                           | Hard        | 4/3    | `HV18-`
[December 21](#day-21-)                           | Hard        | 4/3    | `HV18-`
[December 22](#day-22-)                           | Expert      | 5/4    | `HV18-`
[December 23](#day-23-)                           | Expert      | 5/4    | `HV18-`
[December 24](#day-24-)                           | Expert      | 5/4    | `HV18-`
[December 25](#day-25-)                           | Expert      | 5/4    | `HV18-`

## Day 01

**Challenge**

Santa is stranded on the Christmas Islands and is desperately trying to reach his trusty companion via cellphone. We've bugged the device with a primitive keylogger and have been able to decode some of the SMS, but couldn't make much sense of the last one. Can you give us a hand?

**Solution**

We receive some text messages in txt format that we can easily read:

```
==> sms1.txt <==
date: 1999-11-23 03:01:10
to: 00611015550117
text: rudolf where are you brrr

==> sms2.txt <==
date: 1999-11-23 03:04:11
to: 00611015550117
text: its too damn cold here and im out of eggnog lul

==> sms3.txt <==
date: 1999-11-23 03:06:39
to: 00611015550117
text: sorry bout my last 2msg but i could really need your help bud :*
```

And we can write a quick script to decode the keypresses based on the information in the header file:

```
$ python tmp.py sms1.csv
['left', 'left', 'left', 'left']
T9rudolf where are you brrr['left']
['left']
0m, .l ,p['left']

$ python tmp.py sms2.csv
['left', 'left', 'left', 'left']
T9its fucking['right', 'right', 'right', 'right', 'right', 'right', 'right']
too damn cold here and im out of eggnog l¿l['left', 'left']
0m.. .l ,p['left']

$ python tmp.py sms3.csv
['left', 'left', 'left', 'left']
T9sorri bout my last 2msg but i could realy need your help bud['up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up']
l['up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up', 'up']
['right']
y['down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down', 'down']
 :*['left', 'left']
0m, .l ,p['left']

$ python tmp.py sms4.csv
['left', 'left', 'left', 'left']
T9alright pal hers['up']
e['down']
 ye flag go¿d lucj enter['up', 'up', 'up', 'up', 'up', 'up', 'up']
['down']
['right']
k['down', 'down', 'down', 'down', 'down', 'down']
ing it with those hooves lol its aotw{l3ts_dr1nk¿s0m3_egg¿og['right', 'right']
0g_y0u_cr4zy_d33r}['left', 'left']
0m.. .l ,p['left']
```

And from this we can guess probably made some decoding errors but looks like it is:

**Flag**
```
AOTW{l3ts_dr1nk_s0m3_eggn0g_y0u_cr4zy_d33r}
```
