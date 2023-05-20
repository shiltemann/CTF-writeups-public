var NOF_CHALLENGES = 24;
var HIGH_SCORE_LIMIT = 50;
var HIGH_SCORE_MAX_LINES = 10000;

var serviceUrl = 'https://hackyeaster.hacking-lab.com/hackyeaster/json?year=2017&service=';

var monthNames = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
var levelNames = new Array('easy', 'medium', 'hard');

var challLevels = new Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3);

var challNames = new Array('Puzzle this!', 'Lots of Dots', 'Favourite Letters', 'Cool Car', 'Key Strokes', 'Message to Ken', 'Crypto for Rookies', 'Snd Mny', 'Microscope',
'An egg or not ...', 'Tweaked Tweet', 'Once Upon a File', 'Lost the Thread', 'Shards', 'P Cap', 'Pathfinder', 'TODO', 'Lovely Vase',
'Disco Time', 'Spaghetti Hash', 'Monster Party', 'Game, Set and Hash', 'TODO', '[epass]');

var reUserName = /^[\w.\-]+$/g;

var challId = 0;

var scrambledEggCipher = '';

var navBarShown = false;

var pingDone = false;
var userName = '';
var ticket = '';

$.ajaxSetup({headers:{'Authorization':"Basic aGUyMDE3OmVnZ2dnaHVudGhhY2tpbmdsYWI="}});

function getUserLevel(p) {
    if (p >= 90) return 'Chief Bunny';
    else if (p >= 80) return 'Buck';
    else if (p >= 64) return 'Hare';
    else if (p >= 48) return 'Bunny';
    else if (p >= 32) return 'Cony';
    else if (p >= 16) return 'Leveret';
    else return 'Chinchilla';
}

function setChallId(newChallId) {
	challId = newChallId;
}

function setChallAuthor(newChallAuthor) {
	challAuthor = newChallAuthor;
}

function toggleNavBar() {
	navBarShown = !navBarShown;
	if (navBarShown) {
		$('#navPanel').css('height', $(window).innerHeight());
		$('#navPanel').show();
	} else {
		$('#navPanel').hide();
	}
}

function addHeader(selectedItem) {
	var cHome = '', cChal = '', cScor = '', cEggs = '', cScan = '', cNews = '', cBudd = '', selClass = 'current';
	if (selectedItem == 'home')
		cHome = selClass;
	else if (selectedItem == 'news')
		cNews = selClass;
	else if (selectedItem == 'challenges')
		cChal = selClass;
	else if (selectedItem == 'scanner')
		cScan = selClass;
	else if (selectedItem == 'scores')
		cScor = selClass;
	else if (selectedItem == 'eggs')
		cEggs = selClass;
	$('#header-wrapper')
			.append(
					'  <div id="header">'
						    + '    <h1 id="headertextmobile">Hacky Easter 2017</h1>'
							+ '    <nav id="nav">'
							+ '      <ul>'
							+ '        <li class="'
							+ cHome
							+ '"><a href="index.html">Home</a></li>'
							+ '        <li class="'
							+ cNews
							+ '"><a href="news.html">News</a></li>'
							+ '        <li class="'
							+ cChal
							+ '"><a href="challenges.html">Challenges</a></li>'
							+ '        <li class="'
							+ cScan
							+ '"><a href="scanner.html">Scanner</a></li>'
							+ '        <li class="'
							+ cScor
							+ '"><a href="scores.html">Scores</a></li>'
							+ '        <li class="'
							+ cEggs
							+ '"><a href="eggs.html">Eggs</a></li>'
							+ '        <li class="'
							+ cBudd
							+ '"><a href="eggs.html">Buddies</a></li>'
							+ '      </ul>'
							+ '    </nav>'
							+ '  </div>'
							+ '  <div id="titleBar">'
							+ '    <span class="toggle" onClick="toggleNavBar();"></span>'
							+ '  </div>'
							+ '  <div id="navPanel" style="display:none;">'
							+ '    <div>'
							+ '      <nav>'
							+ '        <a class="link depth-0" href="index.html"><span class="indent-0"></span>Home</a>'
							+ '        <a class="link depth-0" href="news.html"><span class="indent-0"></span>News</a>'
							+ '        <a class="link depth-0" href="challenges.html"><span class="indent-0"></span>Challenges</a>'
							+ '        <a class="link depth-0" href="scanner.html" style=""><span class="indent-0"></span>Scanner</a>'
							+ '        <a class="link depth-0" href="scores.html" style=""><span class="indent-0"></span>Scores</a>'
							+ '        <a class="link depth-0" href="eggs.html"><span class="indent-0"></span>Eggs</a>'
							+ '        <a class="link depth-0" href="buddies.html"><span class="indent-0"></span>Buddies</a>'
							+ '        <a class="link depth-0" href="#" onclick="javascript:window.location = \'ps://twitter\';"><span class="indent-0"></span><img src="images/twitter.png" style="height: 8; width: 32px;padding-top:16px;"></img></a>'
							+ '      </nav>' + '    </div>' + '  </div>');
}

function addFooter() {
	$('#footer-wrapper')
			.append(
					'    <div id="footer" class="container">'
							+ '      <div class="row">'
							+ '        <div class="12u">'
							+ '          <div id="copyright">'
							+ '            Implementation: <a href="https://www.hacking-lab.com/user/profile/PS/">PS</a>'
							+ '          </div>' + '        </div>'
							+ '      </div>' + '    </div>');
}

function addChallengeSidebar() {
	if (!(challId && challId >= 1 && challId <= NOF_CHALLENGES))
		return;
	var challIdString = challId;
	if (challId < 10)
		challIdString = '0' + challIdString;
	$('#sidebar')
			.append(
					'            <section class="box">'
							+ '              <a href="#" class="image featured details"><img src="images/banner/challenge_'
							+ challIdString
							+ '.jpg" alt="Challenge '
							+ challIdString
							+ '" /></a>'
							+ '              <header>'
							+ '                <h3>Details</h3>'
							+ '              </header>'
							+ '              <p>Author: '
							+ '<a href="https://www.hacking-lab.com/user/profile/' + challAuthor + '/">' + challAuthor + '</a>'
							+ '<br/>'
							+ '              <p>Level: '
							+ levelNames[challLevels[challId - 1] - 1]
							+ '<br/>'
							+ '              Solutions: <span id="nofSolutions"></span></p>'
							+ '              <span id="latestSolutionsTitle" style="display:none;">Latest solutions:</span>'
							+ '              <ul class="dates small" id="solutions"></ul>'
							+ '            </section>');
	addSolutionsOfEgg(challId);
}

function addChallengeHeader() {
	if (!(challId && challId >= 1 && challId <= NOF_CHALLENGES))
		return;
	var challIdString = challId;
	if (challId < 10)
		challIdString = '0' + challIdString;
	$('#challenge-header').append(
			'            <p>Challenge ' + challIdString + '</p>'
					+ '            <h2>' + challNames[challId - 1] + '</h2>');
}

function addChallenge(challId, rowId) {
	if (!(challId && challId >= 1 && challId <= NOF_CHALLENGES))
		return;
	if (!(rowId && rowId >= 1 && rowId < 10))
		return;
	var challIdString = challId;
	if (challId < 10)
		challIdString = '0' + challIdString;
	$('#challenge_row_' + rowId)
			.append(
					'          <div class="4u">'
							+ '            <section class="box">'
							+ '              <header>'
							+ '                <h3><span class="level '
							+ levelNames[challLevels[challId - 1] - 1]
							+ '">'
							+ challIdString
							+ '</span>'
							+ challNames[challId - 1]
							+ '</h3>'
							+ '              </header>'
							+ '              <a href="challenge'
							+ challIdString
							+ '.html" class="image featured"><img src="images/banner/challenge_'
							+ challIdString + '.jpg" alt="" /></a>'
							+ '            </section>' + '          </div>');
}

function addEggOMatic() {
	$('#eggOMaticBox')
			.append(
					'          <header id="challenge-header">'
							+ '             <h2>Egg-O-Matic &trade;</h2>'
							+ '          </header>'
							+ '          <p>'
							+ '            Enter password and press enter.'
							+ '            <div class="eggOMatic">'
							+ '              <img class="eggImage" id="scrambledEggImage" onerror="emptyScrambledEgg();" /><br/>'
							+ '              <form action="#" onsubmit="return false;">'
							+ '                <input type="text" class="input-text" id="scrambledEggKey" onkeydown="if (event.keyCode==13) decryptScrambledEgg();" placeholder="lowercase and digits only"></input>'
							+ '              </form>' + '            </div>'
							+ '          </p>');
	emptyScrambledEgg();
}

function ajaxError(elemId) {
    $("#" + elemId).html('<div style="margin-right: 10px;"><span style="color:#ff5555;">Error loading data.</span> You might be offline. Please contact the system bunny for further assistance.</div>');
}

function addNews() {
	var sLinkHtml = '', sLinkText;
	$.ajax({
        type: "GET",
        url: serviceUrl + "news",
        dataType: 'json',
        success: function (data) {
        	$("#news").empty();
    		$.each(data.news, function(i, newsitem) {
    			var sDay = getDayString(newsitem.timestamp);
    			var sTime = getTimeString(newsitem.timestamp);
    			var sLink = '';
    			if (newsitem.linkurl) {
    				if (newsitem.linktext) {
    					sLinkText = newsitem.linktext;
    				} else {
    					sLinkText = 'Details';
    				}
    				sLinkHtml = '<br/><a href="' + newsitem.linkurl + '">'
    						+ sLinkText + '</a>';
    			}
    			$("#news").append(
    					'<li><span class="date"><span class="dateday">' + sDay
    							+ '</span><br/><span class="datetime">' + sTime
    							+ '</span></span>' + '<h3>' + newsitem.title
    							+ '</h3>' + '<p>' + newsitem.text + ' ' + sLinkHtml
    							+ '</p>');
    		});           
        },
    }).fail(function () {
        ajaxError("news");
    });
}

function addSolutions() {
	var eggId, sEggId, sDay, sTime;
	$.ajax({
        type: "GET",
        url: serviceUrl + "solutions",
        dataType: 'json',
        success: function (data) {
        	$("#solutions").empty();
    		$.each(data.solutions, function(i, solution) {
    			sEggId = solution.e;
    			if (sEggId < 10)
    				sEggId = '0' + sEggId;
    			sDay = getDayString(solution.t);
    			sTime = getTimeString(solution.t);
    			$("#solutions").append(
    					'<li><span class="date '
    							+ levelNames[challLevels[solution.e - 1] - 1]
    							+ '"><span class="dateday">' + sDay
    							+ '</span><br/>' + '<span class="datetime">'
    							+ sTime + '</span></span>'
    							+ '<p><img src="images/flags/' + solution.n + '.png"/><a href="eggs.html?name=' + solution.u + '">'
    							+ solution.u + '</a> solved Egg ' + sEggId
    							+ '</p></li>');
    		});           
        },
    }).fail(function () {
        ajaxError("solutions");
    });
}

function addSolutionsOfEgg(challId) {
	var nofSolutions = 0, sDay, sTime;
	if (!(challId && challId >= 1 && challId <= NOF_CHALLENGES))
		return;
    $.ajax({
        type: "GET",
        url: serviceUrl + "solutionsofegg&egg=" + challId,
        dataType: 'json',
        success: function (data) {
    		nofSolutions = data.count;
    		if (data.count > 0) {
    			$('#latestSolutionsTitle').show();
    			$.each(data.solutions, function(i, solution) {
    				sDay = getDayString(solution.t);
    				sTime = getTimeString(solution.t);
    				$("#solutions").append(
    						'<li><span class="date '
    								+ levelNames[challLevels[challId - 1] - 1]
    								+ '"><span class="dateday">' + sDay
    								+ '</span><br/>' + '<span class="datetime">'
    								+ sTime + '</span></span>' + '<p><img src="images/flags/' + solution.n + '.png"/>' + solution.u
    								+ '</p></li>');
    			});
    		} else {
    			nofSolutions = 0;
    		}
    		$("#nofSolutions").append(nofSolutions);           
        },
    }).fail(function () {
        ajaxError("solutions");
    });
}

function addEggs(name) {
	var eggNr, sDay, sTime;
	var totalScore = 0, easyEggs = 0, mediumEggs = 0, hardEggs = 0;
	$.ajax({
        type: "GET",
        url: serviceUrl + "eggs&name=" + encodeURIComponent(name),
        dataType: 'json',
        success: function (data) {
    		$("#eggs").empty();
    		if (data.egg) {
    			$("#eggs").append(data.egg);
    		}
    		$.each(data.eggs, function(i, egg) {
    			totalScore += egg.p;
    			if (egg.l == 1)
    				easyEggs++;
    			else if (egg.l == 2)
    				mediumEggs++;
    			else if (egg.l == 3)
    				hardEggs++;
    			eggNr = egg.i;
    			if (egg.i < 10)
    				eggNr = '0' + eggNr;
    			sDay = getDayString(egg.t);
    			sTime = getTimeString(egg.t);
    			$("#eggs").append(
    					'<span class="eggImage ' + levelNames[egg.l - 1] + 'Egg">'
    							+ '  <span class="eggTitle">' + eggNr + '</span>'
    							+ '  <span class="eggDay">' + sDay + '</span>'
    							+ '  <span class="eggTime">' + sTime + '</span>'
    							+ '  <span class="eggPoints">' + (egg.l * 2)
    							+ ' Points</span>' + '</span>');
    		});
    	    $("#userLevel").text(getUserLevel(totalScore));
    		$("#totalScore").text(totalScore);
    		$("#easyEggs").text(easyEggs);
    		$("#mediumEggs").text(mediumEggs);
    		$("#hardEggs").text(hardEggs);
    		$("#totalEggs").text(easyEggs + mediumEggs + hardEggs);
            if (userName && userName != "" && userName==name) {
              $('#tweetButton').show();
            }
    		$("#statistics").show();
        },
    }).fail(function () {
        ajaxError("eggs");
    });
}

function addBuddies(name, ticket) {
	var userFont, rankFont;
	var lineCount = 0;
	$("#buddies").empty();
	$("#buddies")
			.append(
					'<div class="scoreLine"><span class="cHead center">#</span>'
							+ '<span class="cHead">Nt</span>'
							+ '<span class="cHead">Name</span>'
							+ '<span class="cHead center">Pt</span>'
							+ '<span class="cHead">Eggs<a href="#" class="switchScores" onclick="javascript:showBar=!showBar;changeScoreLines(showBar);">collapse</a></span></div>');

	 $.ajax(serviceUrl + "buddies", {
	    type : "POST",
	    data : {
	      "name" : name,
	      "ticket" : ticket
	    }
	  }).done(function(data) {

	    $.each(data.buddies, function(i, buddy) {
        lineCount++;
        userFont = (buddy.u.length > 10) ? "font09" : "";
        if (buddy.r < 100)
          rankFont = "";
        else if (buddy.r < 1000)
          rankFont = "font09";
        else
          rankFont = "font07";
        $("#buddies").append(
            '<div class="scoreLine"><span class="cRank ' + rankFont + '">' + buddy.r
                + '</span><span class="cNat"><img src="images/flags/' + buddy.n + '.png"/></span><span class="cUser '
                + userFont + '"><a href="eggs.html?name=' + buddy.u + '">' + buddy.u
                + '</a></span><span class="cScor">' + buddy.s + '</span>' + '<span class="cEggs">' + eggList(buddy.e)
                + '</span></div>');
      });
	  }).fail(function() {
	    showAlert('Error', 'Error while fetching buddies.')
	  });
}

function addScores(doLimit) {
	  var limit = (doLimit?HIGH_SCORE_LIMIT:HIGH_SCORE_MAX_LINES);
	  var lineCount = 0;
	  var userFont, rankFont;
	  $.ajax({
	      type: "GET",
	      url: serviceUrl + "scores&limit=" + (limit + 1),
	      dataType: 'json',
	      success: function (data) {
	    	$("#scores").empty();
	    	$("#scores").append('<div class="scoreLine"><span class="cHead center">#</span>' +
	    	                    '<span class="cHead">Nt</span>' +
	    	                    '<span class="cHead">Name</span>' +
	    	                    '<span class="cHead center">Pt</span>' +
	    	                    '<span class="cHead">Eggs<a href="#" class="switchScores" onclick="javascript:showBar=!showBar;changeScoreLines(showBar);">collapse</a></span></div>');
	  	    $.each( data.scores, function( i, score ) {
	 	       lineCount++;
	 	       if (lineCount <= limit) {
	 	         userFont = (score.u.length > 10) ? "font09" : "";
	 	         if (score.r < 100) rankFont = "";
	 	         else if (score.r < 1000) rankFont = "font09";
	 	         else rankFont = "font07";
	 	         $( "#scores" ).append( '<div class="scoreLine"><span class="cRank ' + rankFont + '">' + score.r +
	 	         '</span><span class="cNat"><img src="images/flags/' + score.n + '.png"/></span><span class="cUser ' + userFont + '"><a href="eggs.html?name=' + score.u + '">' +
	 	         score.u + '</a></span><span class="cScor">' + score.s + '</span>' +
	 	         '<span class="cEggs">' + eggList(score.e) + '</span></div>');
	 	       }
	 	    });    
	 	    $("#statsHackers").text(data.stats.hackers);
	 	    $("#statsSolutions").text(data.stats.solutions);
	 	    $("#statsNations").text(data.stats.nations);
	 	    $("#statsPoints").text(data.stats.points);
	 	    $("#statsPointsPerHacker").text(Math.round(100 * data.stats.points / data.stats.hackers) / 100);
	 	    $('#statistics').show();
	 	    if (doLimit && lineCount > HIGH_SCORE_LIMIT) $("#showAllButton").show();
	 	    else $("#showAllButton").hide();	         
	      },
	  }).fail(function () {
	      ajaxError("scores");
	  });
}

function eggList(solved) {
	var result = '';
	var eggClass;
	var eggSolved;
	for (var i = 1; i <= NOF_CHALLENGES; i++) {
		eggSolved = false;
		for (var j = 0; j < solved.length; j++) {
			if (solved[j] == i) {
				eggSolved = true;
			}
		}
		eggClass = levelNames[challLevels[i - 1] - 1];
		if (!eggSolved)
			eggClass += ' unsolved';
		result += '<span class="cell ' + eggClass + '" ></span>';
	}
	return result;
}

function changeScoreLines(showBar) {
	if (showBar) {
		$('.cEggs').addClass('bar');
		$('.switchScores').text('expand');
	} else {
		$('.cEggs').removeClass('bar');
		$('.switchScores').text('collapse');
	}
}

function getDayString(d) {
	if (!d)
		return '';
	var day = parseInt(d.substr(0, 2), 10);
	var monthName = monthNames[parseInt(d.substr(3, 2) - 1, 10)];
	return monthName + ' ' + day;
}

function getTimeString(d) {
	if (!d) return '';
	var hours = parseInt(d.substr(d.length-5,2),10);
	if (hours < 10) hours = '0' + hours;
	var minutes = parseInt(d.substr(d.length-2,2),10);
	if (minutes < 10) minutes = '0' + minutes;
	return hours + ':' + minutes;
}

function getQueryParams() {
	var qs = document.location.search;
	qs = qs.split("+").join(" ");
	var params = {}, tokens, re = /[?&]?([^=]+)=([^&]*)/g;
	while (tokens = re.exec(qs)) {
		var val = decodeURIComponent(tokens[2]);
		if (val != null && val.match(reUserName)) {
			params[decodeURIComponent(tokens[1])] = val;
		}
	}
	return params;
}

function decryptScrambledEgg() {
	decryptScrambledEggWithKey($('#scrambledEggKey').val());
}

function decryptScrambledEggWithKey(key) {
	var decrypted = CryptoJS.AES.decrypt(scrambledEggCipher, key);
	document.getElementById('scrambledEggImage')
			.setAttribute(
					'src',
					'data:image/png;base64,'
							+ CryptoJS.enc.Latin1.stringify(decrypted));
}

function emptyScrambledEgg() {
	document.getElementById('scrambledEggImage').setAttribute('src',
			'images/egg_gray.png');
}

function injectUserData(json) {
	var jsonData = JSON.parse(json);
	userName = jsonData.userName;
	ticket = jsonData.ticket;
	if (onUserDataInjected) {
		onUserDataInjected();
	}
}

function ping() {
	if (pingDone) return;
	pingDone = true;
	$.ajax({
		type : "GET",
		url : serviceUrl + "ping",
		dataType : 'json',
		success : function(data) {
			if (data.status == 1) {
				showAlert("Sorry", "Sorry, the competition is over.");
			}
		},
	});
}

function registerUser(name, nation, pass) {
	$.ajax(serviceUrl + "register", {
		type : "POST",
		data : {
			"name" : name,
			"nation" : nation,
			"pass" : pass
		}
	}).done(
			function(data) {
				registrationFeedback(name, '{"status": ' + data.status
						+ ', "ticket": "' + data.ticket + '"}');
			}).fail(function() {
		registrationFeedback(name, '{"status":2}');
	});
}

function submitEgg(name, ticket, code) {
	$.ajax(serviceUrl + "solution", {
		type : "POST",
		data : {
			"name" : name,
			"ticket" : ticket,
			"code" : code
		}
	}).done(function(data) {
		solutionSubmitted('{"status": ' + data.status + '}');
	}).fail(function() {
		solutionSubmitted('{"status":5}');
	});
}

  function solutionSubmitted(json) {
	  var jsonRes = JSON.parse(json);
	  if (jsonRes.status == 0) {
		  $('#div1').hide();
		  $('#div2').hide();
		  $('#div3').show();
	  } else {
		if (jsonRes.status == 1) {
			showAlert('Error', 'We don\'t know you!');
		} else if (jsonRes.status == 2) {
			showAlert('Error', 'Unknown egg, or already solved');
		} else if (jsonRes.status == 3) {
			showAlert('Error', 'Invalid ticket');
		} else if (jsonRes.status == 4) {
			showAlert('Error', 'Egg list update failed');
		} else if (jsonRes.status == 5) {
			showAlert('Error', 'Communication error. Are you online, hobo?')
		} else if (jsonRes.status == 6) {
			showAlert('Error', 'Sorry, the competition is over. No solutions may be submitted anymore.')
		}
		scanAgain();
	  }
  }

function addBuddy(name, ticket, buddy) {
  $.ajax(serviceUrl + "addbuddy", {
    type : "POST",
    data : {
      "name" : name,
      "ticket" : ticket,
      "buddy" : buddy
    }
  }).done(function(data) {
    buddySubmitted('{"status": ' + data.status + '}');
  }).fail(function() {
    buddySubmitted('{"status":5}');
  });
}

function removeBuddy(name, ticket, buddy) {
  $.ajax(serviceUrl + "removebuddy", {
    type : "POST",
    data : {
      "name" : name,
      "ticket" : ticket,
      "buddy" : buddy
    }
  }).done(function(data) {
    buddySubmitted('{"status": ' + data.status + '}');
  }).fail(function() {
    buddySubmitted('{"status":5}');
  });
}

function buddySubmitted(json) {
  var jsonRes = JSON.parse(json);
  if (jsonRes.status == 0) {
    window.location.href = "buddies.html";
  } else if (jsonRes.status == 1) {
    showAlert('Error', 'Unknown buddy!');
  } else if (jsonRes.status == 2) {
    showAlert('Error', 'Something went wrong');
  } else if (jsonRes.status == 3) {
    showAlert('Error', 'Want to be your own buddy?!');
  } else if (jsonRes.status == 4) {
    showAlert('Error', 'Buddy already added');
  } else if (jsonRes.status == 5) {
    showAlert('Error', 'Communication error. Are you online, buddy?')
  }
}

function showAlert(title, text) {
	window.location = 'ps://alert?title=' + encodeURI(title) + '&text=' + encodeURI(text);
}
