---
layout: writeup
title: 'Dec 16: Try to escape …'
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
*... from the snake cage*

## Challenge

Santa programmed a secure jail to give his elves access from remote.
Sadly the jail is not as secure as expected.

`nc challenges.hackvent.hacking-lab.com 1034`

## Solution

    $ nc challenges.hackvent.hacking-lab.com 1034
                            _____
                        .-'`     '.
                     __/  __       \\
                    /  \ /  \       |    ___
                   | /`\| /`\|      | .-'  /^\/^\\
                   | \(/| \(/|      |/     |) |)|
                  .-\__/ \__/       |      \_/\_/__..._
          _...---'-.                /   _              '.
         /,      ,             \   '|  `\                \\
        | ))     ))           /`|   \    `.       /)  /) |
        | `      `          .'       |     `-._         /
        \                 .'         |     ,_  `--....-'
         `.           __.' ,         |     / /`'''`
           `'-.____.-' /  /,         |    / /
               `. `-.-` .'  \        /   / |
                 `-.__.'|    \      |   |  |-.
                    _.._|     |     /   |  |  `'.
              .-''``    |     |     |   /  |     `-.
           .'`         /      /     /  |   |        '.
         /`           /      /     |   /   |\         \\
        /            |      |      |   |   /\          |
       ||            |      /      |   /     '.        |
       |\            \      |      /   |       '.      /
       \ `.           '.    /      |    \        '---'/
        \  '.           `-./        \    '.          /
         '.  `'.            `-._     '.__  '-._____.'--'''''--.
           '-.  `'--._          `.__     `';----`              \\
              `-.     `-.          `."'```                     ;
                 `'-..,_ `-.         `'-.                     /
                        '.  '.           '.                 .'
    
    Challenge by pyth0n33. Have fun!
    
    
    
    The flag is stored super secure in the function SANTA!
    >>> a =

Looks like a python jail. We poke around a bit

    >>> a = SANTA()
    name 'santa' is not defined
    >>> a = 2
    >>> a = print(a)
    2
    >>> a = 1
    Denied
    >>> a = eval('2+2')
    >>> print(a)
    4
    >>> a = 'b'
    Denied
    >>> a = 'a'
    >>> a =

..seems like certain characters are forbidden

possibly useful [link][1]

We try inputting all printables to see which are allowed and which
aren't:

    allowed:
    ['0', '1', '2', '3', '7', '9', 'a', 'c', 'd', 'e', 'i', 'l', 'n', 'o', 'p', 'r',
    's', 't', 'v', 'A', 'C', 'D', 'E', 'I', 'L', 'N', 'O', 'P', 'R', 'S', 'T', 'V',
    '_','"', "'", '(', ')', '+', '.', '[', ']', '\n', '\r']
    disallowed:
    ['4', '5', '6', '8', 'b', 'f', 'g', 'h', 'j', 'k', 'm', 'q', 'u', 'w', 'x', 'y',
    'z', 'B', 'F', 'G', 'H', 'J', 'K', 'M', 'Q', 'U', 'W', 'X', 'Y', 'Z', '!', '#',
    '$', '%', '&', '*', ',', '-', '/', ':', ';', '<', '=', '>', '?', '@', '\\', '^',
    '`', '{', '|', '}', '~', ' ', '\t', '\x0b', '\x0c']

functions we can use:

    eval()
    all()
    repr()
    print()
    
    
    disallowed:
    ['abs', 'any', 'apply', 'basestring', 'bin', 'bool', 'buffer', 'bytearray', 'bytes', 'callable', 'chr',
    'classmethod', 'cmp', 'compile', 'complex', 'copyright', 'divmod', 'enumerate', 'execfile', 'exit',
    'file', 'filter', 'float', 'format', 'frozenset', 'getattr', 'globals', 'hasattr', 'hash', 'help',
    'hex', 'input', 'issubclass', 'long', 'map', 'max',  'memoryview', 'min', 'next', 'object', 'open',
    'pow', 'property', 'quit', 'range', 'raw_input', 'reduce', 'round', 'staticmethod', 'sum', 'super',
    'tuple', 'type', 'unichr', 'unicode', 'xrange', 'zip']
    undefined:
    ['coerce', 'credits', 'delattr', 'dict', 'dir', 'id', 'int', 'intern', 'isinstance', 'iter', 'len',
    'license', 'list', 'locals', 'oct', 'ord', 'reload', 'reversed', 'set', 'setattr', 'slice', 'sorted',
    'str', 'vars']
    other:
    ['all', 'eval', 'print', 'repr']



[1]: https://wapiflapi.github.io/2013/04/22/plaidctf-pyjail-story-of-pythons-escape/
