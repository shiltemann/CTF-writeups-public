---
layout: writeup
title: 'Lost in the Forest'
level:
difficulty:
points: 300
categories: [forensics]
tags: []
flag: IceCTF{good_ol_history_lesson}
---
**Challenge**  
You've rooted a notable hacker's system and you're sure that he has
hidden something juicy on there. Can you find his secret?

**Solution**  
We receive a zip file named 'fs.zip' which contains a partial root file
system of our hacker's machine. After unzipping we looked for all
potentially interesting files:

    find -type f .

And spotted './home/hkr/Desktop/clue.png' which is just a picture of a
red  
herring. Cute. So the other dozens of JPGs are probably also red
herrings. Next  
we looked for more interesting files and just looked at them
individually with  
a text editor:

    vim `find -type f .`

Most were rather uninteresting, but there was a base64 looking string,  
`./home/hkr/hzpxbsklqvboyou` which might be interesting later. In  
`.bash_history` there were some interesting commands:

    wget https://gist.githubusercontent.com/Glitch-is/bc49ee73e5413f3081e5bcf5c1537e78/raw/c1f735f7eb36a20cb46b9841916d73017b5e46a3/eRkjLlksZp
    mv eRkjLlksZp tool.py
    ./tool.py ../secret > ../hzpxbsklqvboyou

So that script generated the base64 stuff on the desktop. We'll just
write a [decode version of the
script](./writeupfiles/lost-in-the-forest.py) and decrypt our output.

## Flag

    IceCTF{good_ol_history_lesson}

