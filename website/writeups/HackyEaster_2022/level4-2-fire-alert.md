---
layout: writeup

title: Fire Alert
level: 4         # optional, for events that use levels (like HackyEaster)
difficulty: easy     # easy/medium/hard etc, if applicable
points: 100        # if used
categories: [web]  # e.g. crypto, pwn, reversing
tags: []        # anything notable about challenge/solution, vuln/tools/etc

flag: he2022{th1s_fl4g_1s_bl4ck_n0t}

---

## Challenge

In case of fire, break the glass and press the button.

http://46.101.107.117:2204

Note: The service is restarted every hour at x:00.


## Solution

We see a website with a button, it logs some color lines to the console, but it looks like also a flag

```js
function firealert() {
    console.log("%c x", "color:transparent; border-left: 1000px solid lightgreen;");console.log("%c x", "color:transparent; border-left: 1000px solid blue;");
    console.log("%c x", "color:transparent; border-left: 1000px solid red;");console.log("%c x", "color:transparent; border-left: 1000px solid tomato;");
    console.log("%c x", "color:transparent; border-left: 1000px solid darkgray;");console.log("%c x", "color:transparent; border-left: 1000px solid yellow;");
    console.log("%c x", "color:transparent; border-left: 1000px solid black;");console.log(atob("JWMgZmxhZzogaGUyMDIye3RoMXNfZmw0Z18xc19ibDRja19uMHR9"), "color:transparent; border-left: 1000px solid magenta;");
    console.log("%c x", "color:transparent; border-left: 1000px solid purple;");console.log("%c x", "color:transparent; border-left: 1000px solid lightblue;");
    console.log("%c x", "color:transparent; border-left: 1000px solid green;");console.log("%c x", "color:transparent; border-left: 1000px solid gray;");
    window.location.href='https://www.youtube.com/watch?v=0oBx7Jg4m-o';
}
setInterval(function() {
    document.body.style.backgroundColor = "#" + Math.floor(Math.random()*16777215).toString(16);
}, 1000)

```

Running `atob("JWMgZmxhZzogaGUyMDIye3RoMXNfZmw0Z18xc19ibDRja19uMHR9")` it the console gets us our flag



