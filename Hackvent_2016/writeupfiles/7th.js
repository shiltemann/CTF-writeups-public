var makeCRCTable = function(){
    var c;
    var crcTable = [];
    for(var n =0; n < 256; n++){
        c = n;
        for(var k =0; k < 8; k++){
            c = ((c&1) ? (0xEDB88320 ^ (c >>> 1)) : (c >>> 1));
        }
        crcTable[n] = c;
    }
    return crcTable;
}

var crc32 = function(str) {
    var crcTable = crcTable || (crcTable = makeCRCTable());
    var crc = 0 ^ (-1);

    for (var i = 0; i < str.length; i++ ) {
        crc = (crc >>> 8) ^ crcTable[(crc ^ str.charCodeAt(i)) & 0xFF];
    }

    return ((crc&0xFFFFFFFF) ^ (-1)) >>> 0;
}

var secret = [
	2155568001,
	3847164610,
	2684356740,
	2908571526,

	2557362074,
	2853440707,
	3849194977,
	3171764887
];

var pass = "aaa";

var s="";
for(var i=0; i<secret.length; i++) {
	var pp="";
	for(var p = (secret[i] ^ crc32(pass)); p>0; p>>=8) {
		pp = String.fromCharCode(p&0xFF)+pp;
	}
	console.log(pp);
	s+=pp;
}

if(crc32(s) == 2343675265){
	console.log("Right " + s);
}else{
	console.log("Wrong");
}
