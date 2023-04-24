---
layout: writeup
title: 'Stage 2 Web: Kitty'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{i_guess_hashing_isnt_everything_in_this_world}
---
## Challenge

They managed to secure their website this time and moved the hashing to
the server :(. We managed to leak this hash of the admin's password
though!
`c7e83c01ed3ef54812673569b2d79c4e1f6554ffeb27706e98c067de9ab12d1a`. Can
you get the flag? kitty.vuln.icec.tf

## Solution

We examine the source

    <!doctype html>
    <html>
    <head>
        <meta charset="utf-8" />
        <title>Log In</title>
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" />
    </head>
    <body>
        <div class="container">
            <form method="post" action="login.php">
                <label for="username">Username: </label>
                <input class="u-full-width" type="text" name="username" placeholder="Username" required minlength="5" />
                <label for="password">Password: </label>
                <input id="password" class="u-full-width" type="password" name="password" placeholder="Password" required pattern="[A-Z][a-z][0-9][0-9][\?%$@#\^\*\(\)\[\];:]" />
                <input type="submit" value="Log In" />
            </form>
        </div>
    </body>
    </html>
{: .language-html}

and see that the password is 5 characters following very specific
pattern, we can bruteforce this! It seems to refer to a previous
challenge where they did SHA-256 hashing client side, so we assume that
is what they use here too.

    import hashlib
    import string
    
    target="c7e83c01ed3ef54812673569b2d79c4e1f6554ffeb27706e98c067de9ab12d1a"
    
    for i in string.uppercase:
        for j in string.lowercase:
            for k in string.digits:
                for l in string.digits:
                    for m in list("\?%$@#^*()[];:"):
                        h = hashlib.sha256()
                        h.update(i+j+k+l+m)
                        if h.hexdigest() == target:
                            print i+j+k+l+m
{: .language-python}

This output password `Vo83*` and if we enter this on site with username
`admin`, we are given our flag

## Flag

    IceCTF{i_guess_hashing_isnt_everything_in_this_world}

