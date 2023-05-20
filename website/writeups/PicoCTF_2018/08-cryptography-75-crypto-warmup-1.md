---
layout: writeup
title: 'Cryptography 75: Crypto Warmup 1'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: picoCTF{SECRETMESSAGE}
---
## Challenge

Crpyto can often be done by hand, here's a message you got from a
friend, `llkjmlmpadkkc` with the key of `thisisalilkey`. Can you use
this table to solve it?.

        A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
       +----------------------------------------------------
    A | A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    B | B C D E F G H I J K L M N O P Q R S T U V W X Y Z A
    C | C D E F G H I J K L M N O P Q R S T U V W X Y Z A B
    D | D E F G H I J K L M N O P Q R S T U V W X Y Z A B C
    E | E F G H I J K L M N O P Q R S T U V W X Y Z A B C D
    F | F G H I J K L M N O P Q R S T U V W X Y Z A B C D E
    G | G H I J K L M N O P Q R S T U V W X Y Z A B C D E F
    H | H I J K L M N O P Q R S T U V W X Y Z A B C D E F G
    I | I J K L M N O P Q R S T U V W X Y Z A B C D E F G H
    J | J K L M N O P Q R S T U V W X Y Z A B C D E F G H I
    K | K L M N O P Q R S T U V W X Y Z A B C D E F G H I J
    L | L M N O P Q R S T U V W X Y Z A B C D E F G H I J K
    M | M N O P Q R S T U V W X Y Z A B C D E F G H I J K L
    N | N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
    O | O P Q R S T U V W X Y Z A B C D E F G H I J K L M N
    P | P Q R S T U V W X Y Z A B C D E F G H I J K L M N O
    Q | Q R S T U V W X Y Z A B C D E F G H I J K L M N O P
    R | R S T U V W X Y Z A B C D E F G H I J K L M N O P Q
    S | S T U V W X Y Z A B C D E F G H I J K L M N O P Q R
    T | T U V W X Y Z A B C D E F G H I J K L M N O P Q R S
    U | U V W X Y Z A B C D E F G H I J K L M N O P Q R S T
    V | V W X Y Z A B C D E F G H I J K L M N O P Q R S T U
    W | W X Y Z A B C D E F G H I J K L M N O P Q R S T U V
    X | X Y Z A B C D E F G H I J K L M N O P Q R S T U V W
    Y | Y Z A B C D E F G H I J K L M N O P Q R S T U V W X
    Z | Z A B C D E F G H I J K L M N O P Q R S T U V W X Y

## Solution

Looks like vigenere,

