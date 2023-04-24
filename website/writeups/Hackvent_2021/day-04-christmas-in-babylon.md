---
layout: writeup
title: 'Day 4: Christmas in Babylon'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

## Solution

The file is a zip file with a single `code.txt` file inside.

    $ head code.txt
    using System;using System.Text;using static System.Console;void Rev(string s){var chars=Encoding.
    ASCII.GetString(Convert.FromBase64String(s)).ToCharArray();Array.Reverse(chars);WriteLine(new string
    (chars));}
    Rev("KzgrKitoKysrKysreysrKysraSsvPiswK3krKz4oKysrKysrICsrPlQrKysrKysrKyt9KysrPlsrVCsrK3grKysr");
    Rev("K2srICsrKysyKyt9KysrKysrPisrKytVKysrKysrKysrPisrNSsrKysrK3wrKysrNT4rKysrRCs2KytyKysqKz4r");
    Rev("K0MrKysrKysrMj4rKytqKyArKysrKytMKysrICsrbys+KysrKysrKysrKys+KysgKytNKysraStQKysrditxKys+");
    Rev("KysqKyArKysrK2c+KysrKysrKyt4KysrKyt1Kz4rKytTKytEK0UrKytpKysrdj5XKytnKysrKysrcisjKytCPkcr");
    Rev("Kz4rKysrcCspK1E+KysrRisrKysrbCsrPitKKytpKysrKys+KysrK1QrKytXKysrPisrKysrKz5nKyorKyArUCsr");
    Rev("KysrK3U+KysrKysrICsrK2orKysrPjErK3MrKysrKCsrK1UrMSsrKy8+UisrKysgKysrKytBKysrKytxKz4rK3Yr");
    Rev("KysrKysrKysrKzg+K1orKysrKz5MKyttK1IrSCsrICsrKz4rQisrKysrKz4rICsgKysrKysrK2QrKysrICsrPjIr");

apparently that's c# but we can just

    cat writeupfiles/04/code.txt | grep '^Rev' | sed 's/Rev("//g;s/".*//g' > tmp
    for line in `cat tmp`; do echo "$line" | base64 -d | tac >> decode; echo >> decode; done;

to mimic what the C# is doing and we get

    $ head decode
    +8+*+h++++++{+++++i+/>+0+y++>(++++++ ++>T+++++++++}+++>[+T+++x++++
    +k+ ++++2++}++++++>++++U+++++++++>++5++++++|++++5>++++D+6++r++*+>+
    +C+++++++2>+++j+ ++++++L+++ ++o+>+++++++++++>++ ++M+++i+P+++v+q++>
    ++*+ +++++g>++++++++x+++++u+>+++S++D+E+++i+++v>W++g++++++r+#++B>G+
    +>++++p+)+Q>+++F+++++l++>+J++i+++++>++++T+++W+++>++++++>g+*++ +P++
    ++++u>++++++ +++j++++>1++s++++(+++U+1+++/>R++++ +++++A+++++q+>++v+
    ++++++++++8>+Z+++++>L++m+R+H++ +++>+B++++++>+ + +++++++d++++ ++>2+
    v++ +&>9+ ++++++++R++++x+>++(+++>+++++++>++e++U+{++++>+++5+++> +Y+
    +h+++n+ ++++w>+#+++s+K+t++l++>+}++++E++q++++>+++w+>+++n++j+j++>+++
    ++++Q++Y>+++8++ ++V+#+u+++a+>F++W+++A++d++E>z++++++++B++++>+}+L++W

surely that's brainfuck, right?

But `beef` fails:

    $ beef writeupfiles/04/decode
    
    beef: writeupfiles/04/decode: Unbalanced brackets

And so does `bf` on ubuntu. It looks like there's extra weird characters
but removing those also doesn't help.
