---
layout: writeup

title: Ghost in a Shell
level: 5  # optional, for events that use levels
difficulty: medium
points: 200
categories: [forensics]
tags: []

flag: he2023{al1asses-4-fUn-and-pr0fit}

---

## Challenge

```
  _, _,_  _,  _, ___   _ _, _    _,    _, _,_ __, _,  _,    , ,   ,
 / _ |_| / \ (_   |    | |\ |   /_\   (_  |_| |_  |   |     | \   /
 \ / | | \ / , )  |    | | \|   | |   , ) | | |   | , | ,   |  \ /
  ~  ~ ~  ~   ~   ~    ~ ~  ~   ~ ~    ~  ~ ~ ~~~ ~~~ ~~~   ~   ~
______________________________________________________________________
 ,--.     ,--.     ,--.     ,--.
| oo |   | oo |   | oo |   | oo |
| ~~ |   | ~~ |   | ~~ |   | ~~ |  o  o  o  o  o  o  o  o  o  o  o  o
|/\/\|   |/\/\|   |/\/\|   |/\/\|
______________________________________________________________________
```

Connect to the server, snoop around, and find the flag!

- ssh `ch.hackyeaster.com -p 2306 -l blinky`
- password is: `blinkblink`


## Solution

Let's log in and see what we have:

```bash
$ ssh ch.hackyeaster.com -p 2306 -l blinky
blinky@ch.hackyeaster.com's password:

  _, _,_  _,  _, ___   _ _, _    _,    _, _,_ __, _,  _,    , ,   ,
 / _ |_| / \ (_   |    | |\ |   /_\   (_  |_| |_  |   |     | \   /
 \ / | | \ / , )  |    | | \|   | |   , ) | | |   | , | ,   |  \ /
  ~  ~ ~  ~   ~   ~    ~ ~  ~   ~ ~    ~  ~ ~ ~~~ ~~~ ~~~   ~   ~
______________________________________________________________________
 ,--.     ,--.     ,--.     ,--.
| oo |   | oo |   | oo |   | oo |
| ~~ |   | ~~ |   | ~~ |   | ~~ |  o  o  o  o  o  o  o  o  o  o  o  o
|/\/\|   |/\/\|   |/\/\|   |/\/\|
______________________________________________________________________

Find the flag!
ab81e1e4280b:~$ ls
about.txt
blinky
flag.txt
ab81e1e4280b:~$ cat flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ less flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ more flag.txt
|\---/|
| o_o |  meow!
 \___/
ab81e1e4280b:~$ ls -la
about.txt
blinky
flag.txt
```

ok, a bunch of commands are acting weird, let's see if they setup some aliases to make our lives difficult.. yep!

```bash
$ alias
alias ash='exit'
alias bash='echo "you are not a bash brother" && exit'
alias cat='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias cd='/bin/true'
alias egrep='echo "" #'
alias fgrep='echo "" #'
alias find='echo "command not found: find" #'
alias fzip='/usr/bin/zip -P "/bin/funzip"'
alias grep='echo "" #'
alias id='echo "uid=0(root) gid=0(root) groups=0(root)"'
alias java='echo "command not found: java" #'
alias less='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias ls='/bin/ls /home/blinky | /bin/grep -v home #'
alias more='echo "|\---/|" && echo "| o_o |  meow!" && echo " \___/" #'
alias pwd='echo /home/blinky #'
alias python='echo "command not found: python" #'
alias vi='echo "command not found: vi" #'
alias vim='echo "command not found: vim" #'
alias whoami='echo "you are you"'
alias zip='echo "command not found: zip" #'
alias zsh='exit'
```

to fix this we do

```
$ unalias sh
$ sh
```

to get a clean shell

```bash
ab81e1e4280b:~$ unalias sh
ab81e1e4280b:~$ sh
b81e1e4280b:~$ cat about.txt
Blinky, ankaŭ konata kiel Akabei, estas la gvidanto de la Fantomoj kaj la ĉefmalamiko de Pac-Man. Li ankaŭ estas prezentita kiel la plej agresema fantomo kiu ĉiam postkuras Pac-Man, kaj malfacilas skui post kiam li komencas. Li povas havi humoron, kaj estas bonaj amikoj kun Pinky, Inky, kaj Clyde. Li ankaŭ havas filinon nomitan Yum-Yum.

Dum origine la ĉefantagonisto en la unua Pac-Man arkadludo, lia antagonisma rolo de la franĉizo estis plejparte malpliigita al aliancano en lastatempaj enkarniĝoj, kvankam li daŭre estas konsiderita la serio-fakta ĉefa antagonisto en refilmigoj de la unua matĉo kaj de pli maljunaj adorantoj.

ab81e1e4280b:~$ cat flag.txt
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣶⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⠿⠟⠛⠻⣿⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀don't try⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣆⣀⣀⠀⣿⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀- brute force⠀⠀⠀⠀⠀⠀⢸⠻⣿⣿⣿⠅⠛⠋⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀- wordlists⠀⠀⠀⠀⠀⠀⠀⠀⠘⢼⣿⣿⣿⣃⠠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣟⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣛⣛⣫⡄⠀⢸⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⡆⠸⣿⣿⣿⡷⠂⠨⣿⣿⣿⣿⣶⣦⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣾⣿⣿⣿⣿⡇⢀⣿⡿⠋⠁⢀⡶⠪⣉⢸⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⣿⣿⡏⢸⣿⣷⣿⣿⣷⣦⡙⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣇⢸⣿⣿⣿⣿⣿⣷⣦⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣵⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⡁⠀
```

The text is Esperanto, Google translates it as

*Blinky, also known as Akabei, is the leader of the Ghosts and Pac-Man's archenemy. He is also depicted as the most aggressive ghost who always chases Pac-Man, and is hard to shake once he starts. He can have a temper, and is good friends with Pinky, Inky, and Clyde. He also has a daughter named Yum-Yum. While originally the main antagonist in the first Pac-Man arcade game, his antagonistic role of the franchise has been largely diminished to an ally in recent incarnations, although he is still considered the series-de facto main antagonist in remakes of the first game and by older fans.*

We also find another directory that was previously hidden from us by the `ls` alias, in it we find a zip file (with a curious `.fzip` extension):

```bash
ab81e1e4280b:~$ ls
about.txt  blinky     flag.txt   home
ab81e1e4280b:~$ ls home/
blinky
ab81e1e4280b:~$ ls home/blinky/
blinkyflag.fzip
ab81e1e4280b:~$ cd home/blinky/
ab81e1e4280b:~/home/blinky$ unzip blinkyflag.fzip
Archive:  blinkyflag.fzip
[blinkyflag.fzip] flag.txt password:
password incorrect--reenter:
password incorrect--reenter:
   skipping: flag.txt                incorrect password
```

We are told we don't need to brutforce of guess the password, so there must be a hint to the password around here somewhere..

We look more closely to the aliases they set up, and see

```bash
alias fzip='/usr/bin/zip -P "/bin/fyunzip"'
```

ah! they fzipped it, which was an alias for zipping with password `/bin/funzip`

```bash
ab81e1e4280b:~/home/blinky$ unzip -P "/bin/funzip" blinkyflag.fzip
Archive:  blinkyflag.fzip
error:  cannot create flag.txt
        Permission denied
```

arg, so close, but we don't have permissions to create the unzipped file..

let's just get the zip file off the server and do it locally

```
ab81e1e4280b:~/home/blinky$ cat blinkyflag.fzip | base64
UEsDBAoACQAAABCUNlVt6MFvLgAAACIAAAAIABwAZmxhZy50eHRVVAkAAyCOLGMgjixjdXgLAAEE
9QEAAAQUAAAAUr8PpJEFxM8HYAIupC/n3QYqp8g44yt7Z/fJ6CdpTcNVM403V0iMcz9C8hb3DFBL
Bwht6MFvLgAAACIAAABQSwECHgMKAAkAAAAQlDZVbejBby4AAAAiAAAACAAYAAAAAAABAAAApIEA
AAAAZmxhZy50eHRVVAUAAyCOLGN1eAsAAQT1AQAABBQAAABQSwUGAAAAAAEAAQBOAAAAgAAAAAAA
```

and then locally:

```
$ echo "UEsDBAoACQAAABCUNlVt6MFvLgAAACIAAAAIABwAZmxhZy50eHRVVAkAAyCOLGMgjixjdXgLAAEE
9QEAAAQUAAAAUr8PpJEFxM8HYAIupC/n3QYqp8g44yt7Z/fJ6CdpTcNVM403V0iMcz9C8hb3DFBL
Bwht6MFvLgAAACIAAABQSwECHgMKAAkAAAAQlDZVbejBby4AAAAiAAAACAAYAAAAAAABAAAApIEA
AAAAZmxhZy50eHRVVAUAAyCOLGN1eAsAAQT1AQAABBQAAABQSwUGAAAAAAEAAQBOAAAAgAAAAAAA
" | base64 -d > pacman.fzip
$ unzip -P "/bin/funzip" pacman.fzip
Archive:  pacman.fzip
 extracting: flag.txt
$ cat flag.txt
he2023{al1asses-4-fUn-and-pr0fit}

```

whoo!!

that was fun :)

