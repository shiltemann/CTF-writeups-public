---
layout: writeup
title: 'JS Pls'
level:
difficulty:
points: 80
categories: [reversing]
tags: []
flag: ABCTF{node_is_w4Ck}
---
**Challenge**
Can you figure out the flag from [this](writeupfiles/jspls.js)? Have fun
;)

    eval(new Buffer('cHJvY2Vzcy5z [..snip..] pdCgpO30pOwo=','base64').toString());

## Solution

We base64 decode and find [obfuscated
javascript](writeupfiles/jspls2.js)

    process.stdin.resume();
    process.stdin.setEncoding('utf8');
    console.log('Give me a flag');
    process.stdin.on('data', (t) = > {
      t = t.trim();
      if (t.length === + [[ + ! + []] + [! + [] + ! + [] + ! + [] + ! + [] + ! + [] + ! + [] + ! + [] + ! + [] + ! + []]]) {

    [..snip..]

         ! + []] + (!![] + []) [ + ! + []]]) [ + ! + [] + [ + []]] + (!![] + []) [ + ! + []]]) [! + [] + ! + [] + [ + []]]](! + [] + ! + [] + [ + ! + []])) {
                    console.log('nice job!');
                    process.exit();
                  }
                }
              }
            }
          }
        }
      }
      console.log('nope!');
      process.exit();
    });
{: .language-javascript}

looks like [jsfuck][1] or [hieroglyphy][2]

there exist [poisonjs][3] for deobfuscation javascript

deobfuscating yields:

    process.stdin.resume();
    process.stdin.setEncoding('utf8');
    console.log("Give me a flag");
    process.stdin.on('data', function(t) {
       t = t.trim();
       t = t.split('}')[0]+'}';
       console.log(t.length);
       if (t.length == 19) {
        if (t.substr(0, 5) == "ABCTF") {console.log(t[18] == 125);
       if (t[18] == '}' && t[5] == '{') {console.log('2');
        if (t.substr(6, 4) === "node") {console.log('3');
         if (t[10] == t[13] && t[10] == '_') {console.log('4');
          if (t.substr(11, 2) == "is") {console.log('4');
           if (t.substr(14, 4) == "w4Ck"){
                 console.log("nice job!");
                 process.exit();
             }
           }
         }
        }
       }
      }
     }
     console.log("nope!");
     process.exit();
    });
{: .language-javascript}



[1]: https://esolangs.org/wiki/JSFuck
[2]: https://github.com/alcuadrado/hieroglyphy
[3]: https://ooze.ninja/javascript/poisonjs/
