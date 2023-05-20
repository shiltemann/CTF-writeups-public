---
layout: writeup
title: 'General Skills 150: ssh-keyz'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{who_n33ds_p4ssw0rds_38dj21}
---
**Challenge**  
As nice as it is to use our webshell, sometimes its helpful to connect
directly to our machine. To do so, please add your own public key to
~/.ssh/authorized\_keys, using the webshell. The flag is in the ssh
banner which will be displayed when you login remotely with ssh to with
your username.

**Solution**  
Added a key to ~/.ssh/authorized\_keys. Hardest part was finding the ip
address with `curl icanhazip.com`

