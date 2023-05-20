# December 8th: Pearl White Candle


**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=8](http://hackvent.hacking-lab.com/challenge.php?day=8)

**Hint**

None

**Challenge**

We are given an ascii art candle:

```

                                 (
                     (       (
                           (         (
                (    (         (            (
                                       (
             (      (       (    (          (    (
                             ''
                )      )      )))   )    )     )   )
           )                  )))))
                  )          =~('(?{'           .
                       (    '`'|'%').   (    (
                (           '['))^'-'
                             ).('`'  |   '!')
                      .    (    (     '`')|','
                                )  .('"\\$').(
                             '`'|'!').'=<>;'.
                        ('`'|'#').('`'|"\(").(
                    '`'|'/').('`'|'-').(('[')^
                    '+').('{'^'[').'\\$'.('`'|
                    '!').';\\$'.('`'|'!').'='
                    .'~'.('['^'/').('['^')').
                    '/'.('`'^'!').'-'.('{'^'!'
                   ).('{'^'[').('`'|'!').'-'.(
                  '['^'!').'/\\"-;'.('`'^'.').
                  '-'.('{'^'!').('`'^('!')).
                  '-'.('`'^"\-").'/;((\\$'.(
                     '`'|'!').('{'^'[').('`'
                     |'%').('['^'*').(('{')^
                     '[')."'".('^'^('`'|'.')
                     ).('`'^'"').('{'^'!').(
                     '`'^'-').('`'^'.').('`'
                     ^'$').('{'^'(').(('`')^
                     '&').('{'^'!').('`'^'.'
                     ).('{'^'*').('`'^'/').(
                     '`'^'"').('`'^'.').('`'
   ^                 '$').('`'^'/').('`'^'&'
).(('`')^            "'").('{'^'(').('`'^'.'        )     .
  ('^'^('`'|'/'      )).('{'^'(').('`'^'&').  (    '{'^"\!").
   '!'."\'".')&&('.( '['^'+').('['^')').('`'   |')').('`'|'.').(
   ((   '['))^'/').('{'^'[').'\\"'.('['^')')  .('`'|')').('`'|"'")
         .(  '`'|'(').('['^'/').'\\\\'.('`'|'.').'\\"));'.('!'^
               '+')   .'"})');$:='.'^'~';$~ =(   (     (     (
                (         '@')))))|'('
                      ;$^=')'^'[';$/
                  ='`'|'.';$,=('(')^
                '}';$\='`'|'!';#;#
                  ;#;  #;#   ;#;

```



**Solution**

This looks like code so it would follow that we need to evaluate it. We find out that it is compiled perl code (title of challenge is also a hint to this), 
and use [Deparse](http://perldoc.perl.org/B/Deparse.html) to obtain the source code.

Deparse is a backend module for the Perl compiler that generates perl source code, based on the internal compiled structure that perl itself creates after parsing a program



```
perl -Mre=eval -MO=Deparse candle.txt 
```

This gives us the following perl code: 


```perl
'' =~
m[(?{eval"\$a=<>;chomp \$a;\$a=~tr/A-Z a-z/\"-;N-ZA-M/;((\$a eq '0BZMNDSFZNQOBNDOFGSN1SFZ!')
   &&(print \"right\\n\"));\n"})];
$: = 'P';
$~ = 'h';
$^ = 'r';
$/ = 'n';
$, = 'U';
$\ = 'a';

```

This takes a file as argument, transforms the contents of the file, and the output should equal `0BZMNDSFZNQOBNDOFGSN1SFZ!` 
after transformation to get the output `right` from the program. We input a bunch of characters to view the mapping
 


```
input:   ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-~`[]{};':",.<>/?
output: N"#$%&'()*+,-./0123456789:;OPQRSTUVWXYZABCDEFGHIJKLMM0123456789!@#$%^&*()_+=-~`[]{};':",.<>/?
```

We find that the following would produce the correct ouput:

```
0nly perl can parse 1erl!
```

We indeed get the output `right` from the program, but the ball-o-matic does not accept the answer. 

But we notice that the mapping is not unique, and the follwoing text also gives the right answer, and makes more sense:


```
Only perl can parse Perl!
```

We put this phrase in the ball-o-matic to get the bauble:


![](images/PV4XsbUiUgurVcG7hEQL.png)


**Flag**

```
HV14-uKoC-U8wM-HpDl-6qdw-GyQX
```

