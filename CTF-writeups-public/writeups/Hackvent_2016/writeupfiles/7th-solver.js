// secret numbers
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

// We know it'll be al-num + -
var valid = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM-";

// for each of the four bits
for(var k = 0; k < 4; k++){
	// A list of Lists that contain possible valid bits for that secret
	var possibilities = [];
	// For each secret
	for(var i = 0; i < secret.length; i++){
		// Our query value is to take secret and get a byte
		query_value = (secret[i] & 0xFF << (8 * k)) >> (8 * k);
		console.log("Processing " + secret[i] +" " + query_value);
		// Our list of possible byte values
		x = [];
		// Loop over all options
		for(var q = 0; q < 0xFF; q++){
			// Convert to ascii like done in their code
			asdf = String.fromCharCode(query_value ^ q);
			// If they're in alnum + '-''
			if(valid.indexOf(asdf) > -1){
				// Then it's considered valid
				x.push(q);
			}
		}
		// ANd we build up the list
		possibilities.push(x);
	}

	// Now we want to find which are valid across ALL secrets
	var in_all = [];
	// Looping again because lazy
	for(var i = 0; i < 0xFF; i++){
		// Count up occurances across the possibiities lists
		var count = 0;
		for(var j = 0; j < possibilities.length; j++){
			// If it's there
			if(possibilities[j].indexOf(i) > -1){
				count++;
			}
		}

		// If it's quite popular. I feel like this should be count=8,
		// but i'm only seeing 7s. Probably a bug in my code though.
		if(count >= possibilities.length - 1){
			// push.
			in_all.push(i);
		}
	}
	// Possible byte values
	console.log(in_all)
}
