---
layout: writeup
title: 'Rabbit Hole'
level:
difficulty:
points: 400
categories: [stegano]
tags: []
flag: IceCTF{if_you_see_this_youve_breached_my_privacy}
---
## Challenge

Here's a picture of my favorite vegetable. I hope it doesn't make you
cry.

![](writeupfiles/rabbithole.jpg)

## Solution

After a lot of experimenting, we find out we can uncover a hidden
message from the image using steghide:

    $ steghide extract -sf rabbithole.jpg
    Enter passphrase: <onion>
    wrote extracted data to "address.txt".
{: .language-bash}

whoo! contents of the file `address.txt` is:

    wsqxiyhn23zdi6ia

might be an `.onion` link? Opening `http://wsqxiyhn23zdi6ia.onion` with
a tor browser (or via https://onion.link/) gives:

[rabbithole.html](writeupfiles/rabbithole.html)

![](writeupfiles/rabbithole_screenshot.png)

    <!DOCTYPE HTML>
    <html>
        <head>
            <title>Rabbit Hole</title>
    	<meta charset="UTF-8">
            <style>
                body {
                    background: black;
                }

                p {
                    max-width: 750px;
                    text-align: center;
                    color: #00ff00;
                    margin: 0px auto;
                }

                #header {
                    max-width: 989px;
                    margin: 0px auto;
                }

                #footer {
                    margin: 100px 0;
                    text-align: center;
                }

                #error {
                    max-width: 350px;
                }

                #eyes {
                    max-width: 200px;
                }
            </style>
        </head>
        <body>
            <div id="header">
                <img id="error" src="error.gif"/>
            </div>
            <p>聐㠃㐊㐀㐀膜舕㐀㐀㐀㐀㐀㐀㐵㐜ꕳ𓅡𔕨𓁯𓅤   [..] </p>

             <div id="footer">
                 <img id="eyes" src="eyes.gif"/>
             </div>
        </body>
    </html>
{: .language-html}

![](writeupfiles/rabbithole_error.gif)
![](writeupfiles/rabbithole_eyes.gif)

We find nothing in the images, but after some hints we find that the
chinese characters are [base65536][1]

[file with just the characters](writeupfiles/rabbithole_characters.txt)

    # pip install base65536

    import base65536

    with open('rabbithole_characters.txt','r') as f:
        ct = f.readline().rstrip().replace(' ','')

    with open('rabbithole_out','wb') as f2:
        f2.write(base65536.decode(ct))
{: .language-python}

which turns out to be an [epub](writeupfiles/rabbithole_out.epub) on
cell phone hacking. Searching the contents for the flag gives it to us

![](writeupfiles/rabbithole_cover.png)
![](writeupfiles/rabbithole_flag.png)

## Flag

    IceCTF{if_you_see_this_youve_breached_my_privacy}



[1]: https://github.com/Parkayun/base65536
