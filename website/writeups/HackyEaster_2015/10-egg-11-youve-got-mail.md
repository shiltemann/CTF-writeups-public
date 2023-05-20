---
layout: writeup
title: "Egg 11: You've got mail "
level:
difficulty:
points:
categories: []
tags: []
flag: lZkG3aWzrpYrIWoBX5Kz

---
## Challenge:

*You caught a Thunderbird mail box, which contains an easter egg. Go
find it!*

[file](images/Mail.zip)

## Solution:

In the [Inbox](images/Mail/Inbox) file we see an email with a reference
to a password in it:

    From - Mon Sep 29 21:16:37 2014
    X-Account-Key: account1
    X-UIDL: GmailId148c2d6dd5085bb9
    X-Mozilla-Status: 0001
    X-Mozilla-Status2: 00000000
    X-Mozilla-Keys:
    MIME-Version: 1.0
    Received: by 10.140.18.225 with HTTP; Mon, 29 Sep 2014 12:15:34 -0700 (PDT)
    Date: Mon, 29 Sep 2014 21:15:34 +0200
    Delivered-To: hackyeaster@gmail.com
    Message-ID: <CAPPJap-OsLRmw93yqBDw5yOFQghqMjMfMBYyJ1gZiueCkFZgwg@mail.gmail.com>
    Subject:
    From: Hacky Easter <hackyeaster@gmail.com>
    To: Hacky Easter <hackyeaster@gmail.com>
    Content-Type: multipart/alternative; boundary=001a113a961850ddac05043917f7

    --001a113a961850ddac05043917f7
    Content-Type: text/plain; charset=UTF-8
    Content-Transfer-Encoding: quoted-printable

    Hi Hacky,

    here's the p4ssw0rd, as discussed:

    is=C3=A4rdragare

    Please keep it s3kr3t!

    Regards,
                   Dr. Bunny C. Easter

and an email with a base64-encoded zip file named `signature.zip` as
attachment.

We convert the base64 string (for instance [here][1]), and get the
following [zip file](images/egg_11_signature.zip). We open it (and don't
even need a password), to find our egg:

![](images/egg_11_qrcode_small.png)



[1]: http://www.opinionatedgeek.com/dotnet/tools/base64decode/
