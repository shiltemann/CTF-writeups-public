// npm install base65536
var base32768 = require("base32768");
var base65536 = require("base65536");
var textEncoding = require('text-encoding');
var TextDecoder = textEncoding.TextDecoder;

//challenge
var d = '獭慬氭敬敧慮琭扵瑴敲晬礭汯癥猭杲慳猭浵搭桯牳';
var str2 = base65536.decode(d);
var str3 = base32768.decode(d);
console.log(str2);
console.log(str3);

var tstring = new TextDecoder("utf-8").decode(str2);
console.log(tstring)
var tstring = new TextDecoder("utf-8").decode(str3);
console.log(tstring)


