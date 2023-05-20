---
layout: writeup
title: Misaka Mikoto
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{l3v3l5esp3rs}
---
**Challenge**  
My friends like anime way too much . . . they decided that to give me an
encoded message, but I can't solve it because I'm not a weeb!

[file](writeupfiles/message.txt)

contents:

    rasfsasrettlepinebec353luteayvlrghs3s

Hint: Railguns are cool!

## Solution

Rail fence cypher.

[wikipedia][1]

Example:

    WE ARE DISCOVERED. FLEE AT ONCE
    
    W . . . E . . . C . . . R . . . L . . . T . . . E
    . E . R . D . S . O . E . E . F . E . A . O . C .
    . . A . . . I . . . V . . . D . . . E . . . N . .
    
    WECRL TEERD SOEEF EAOCA IVDEN

Use this to decode our method. We vary the number of rails until we get
something sensible

    rasfsasrettlepinebec353luteayvlrghs3s
    
    
    r.......a.......s.......f.......s....
    .a.....s.r.....e.t.....t.l.....e.p...
    ..i...n...e...b...e...c...3...5...3..
    ...l.u.....t.e.....a.y.....v.l.....r.
    ....g.......h.......s.......3.......s
    
    railgunsarethebesteasyctfl3f3l5esp3rs
    
    railguns are the best easyctf{l3v3l5esp3rs}

There is also a good online decoder here:
[http://rumkin.com/tools/cipher/railfence.php][2]



[1]: https://en.wikipedia.org/wiki/Rail_fence_cipher
[2]: http://rumkin.com/tools/cipher/railfence.php
