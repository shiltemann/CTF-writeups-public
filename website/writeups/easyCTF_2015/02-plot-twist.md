---
layout: writeup
title: Plot Twist
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{remember_to_check_everywhere}
---
**Challenge**  
We need to get the flag at this site. That shouldn't be too hard.

https://www.easyctf.com/static/problems/plot-twist/index.html

## Solution

Looking in the source we see this snippet:

    [..]
    <div id="stuff" style="text-align: center">
        <h1>I <3 THIS SITE</h1>
    
        <p>GIVE ME MY FLAG!!!</p>
    
        <!-- you thought the flag would be in the comments didn't you? nice try we're better than that -->
    </div>
    <script type="text/javascript" src="script.js"></script>
    
    [..]
{: .language-html}

The flag was hidden in the javascript file

    console.log('no one would EVER think to look in the console! flag backup: easyctf{remember_to_check_everywhere}');

