---
layout: writeup

title: Igor's Gory Passwordsafe
level:  # optional, for events that use levels
difficulty: easy
categories: [web]
tags: []

flag: he2023{1d0R_c4n_d3str0y_ur_Crypt0_3ff0rt}

---

## Challenge

You found the following letter:

Hi Peter

Thanks again for your help in cryptography to make the passwordsafe secure. Now

- The passwords of the user are stored in a irreversible way (bcrypt)
- All passwords in the safe are encrypted by a strong symmetric key

Kind regards, Roy

Open the passwordsafe at at http://ch.hackyeaster.com:2312 to get your 🚩 flag.

Note: The service is restarted every hour at x:00.


## Solution
 website where we can create an account, then add passwords to our vault, en from there there is an option to copy, edit or delete the passwords in the vault

So we create an account. We cannot make one with the name igor, so probably we need to impersonate igor to get into his vault?

When we create a password, it gets id `12`, I wonder why it starts there..


We find the code for copying a password, its a simple call to `/get/<id>` ..so let's just try some other ids?

```javascript
$(document).ready(function () {
    console.log("Application is running")

    $(".copypassword").click(function (obj) {
        if (obj.target.id.startsWith("copypassword_")) {
            id = obj.target.id.split("_")[1];
            $.get("/get/" + id, function (data, status) {
                if (status == "success") {
                    navigator.clipboard.writeText(data);
                }
            });
        }
    });

    $(document).ready(function(){
        setInterval(flashingEyes,1000);
     });
     function flashingEyes(){
        $("#eyes").fadeIn(400).delay(200).fadeOut(400);
     }

});

```

In the end, we find our flag when we try id `07` (by simply going to http://ch.hackyeaster.com:2312/get/07), we
get the response `he2023{1d0R_c4n_d3str0y_ur_Crypt0_3ff0rt}`

The flag refers to *Insecure direct object reference (IDOR)*

(other id's contain responses like `SQLI_doesnt_help`, `verySecure`, `Well_not_the_flag`, `White_Rabbit_99`)

ok, that was.. easier than I thought it was going to be ..was definitely overthinking this one for the longest time.


