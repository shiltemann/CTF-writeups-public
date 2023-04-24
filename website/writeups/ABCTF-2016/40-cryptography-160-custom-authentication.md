---
layout: writeup
title: 'Cryptography 160: Custom Authentication'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
**Challenge**   
I just learned about encryption and tried to write my own authentication
system. Can you get in? Here is the source! And [here][1] is the site.

    var http = require('http');
    var express = require('express');
    var path = require('path');
    var cookieParser = require('cookie-parser');
    var bodyParser = require('body-parser');
    var crypto = require('crypto');
    
    var secrets = require('./secrets');
    
    var app = express();
    
    app.set('views', path.join(__dirname, 'views'));
    app.set('view engine', 'ejs');
    
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: false }));
    app.use(cookieParser());
    
    console.log("Starting server...");
    
    var encrypt = function(data) {
      var cipher = crypto.createCipheriv('aes-192-cbc', secrets.key, secrets.iv);
      cipher.setAutoPadding(true);
      var ctxt = cipher.update(data, 'ascii', 'hex');
      ctxt += cipher.final('hex');
      return ctxt;
    };
    
    var decrypt = function(data) {
      var decipher = crypto.createDecipheriv('aes-192-cbc', secrets.key, secrets.iv);
      decipher.setAutoPadding(true);
      var ptxt = decipher.update(data, 'hex', 'ascii');
      ptxt += decipher.final('ascii');
      return ptxt;
    };
    
    app.get('/', function(req, res) {
      if(req.cookies.auth) {
        var auth = decrypt(req.cookies.auth).replace(/[^0-9a-zA-Z{}":, ]+/g, '');
        auth = JSON.parse(auth);
        res.render('index', {auth: auth, flag: secrets.flag});
      }
      else {
        res.render('index', {auth: false, flag: secrets.flag});
      }
    });
    
    app.post('/logout', function(req, res) {
      res.append('Set-Cookie', 'auth=; Path=/; HttpOnly');
      res.redirect('/');
    });
    
    app.post('/login', function(req, res) {
      if(req.body.username && req.body.password) {
        var admin = "false";
        if(req.body.username===secrets.username && req.body.password===secrets.password)
            admin = "true";
        var auth = {username: req.body.username, password: req.body.password, admin: admin};
        auth = encrypt(JSON.stringify(auth));
        res.append('Set-Cookie', 'auth='+auth+'; Path=/; HttpOnly');
        res.redirect('/');
      }
    });
    
    // catch 404
    app.use(function(req, res, next) {
      var err = new Error('Not Found');
      err.status = 404;
      next(err);
    });
    
    // error handler
    app.use(function(err, req, res, next) {
      console.log(err);
      res.status(err.status || 500);
      res.render('error', {
        status: err.status
      });
    });
    
    var server = http.createServer(app).listen(3001, function(){
      console.log("HTTP server listening on port 3001!");
    });
{: .language-javascript}

## Solution

I ran it locally which indicated missing [ejs][2]. I  
hoped they did not sanitize their inputs so I tried using usernames like
`<%=
flag %>` but they sanitized these heavily.

Next I spent a fair amount of time inspecting the encryption and
decryption to  
see if I could get around solving it It is easy to feed the function bad
data,  
so it parses extra variables into the data structure. Unfortunately
`admin:
true` is always at the end and cannot be overwritten.



[1]: http://107.170.122.6:3001/
[2]: https://github.com/tj/ejs
