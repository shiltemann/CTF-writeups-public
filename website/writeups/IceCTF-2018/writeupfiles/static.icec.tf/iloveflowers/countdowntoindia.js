Countdown();
function Countdown()
{
	currentDate = new Date();
	targetDate = indiatime;
	//alert(targetDate);
	diff = targetDate.getTime() - currentDate.getTime();
	if (diff < 0){
		document.getElementById("counter").innerText = "00:00:00:00:000";
		return;
	}
	diffSecs = Math.floor(diff/1000);
	diffMins = Math.floor(diffSecs/60);
	diffHours = Math.floor(diffMins/60);
	diffDays = Math.floor(diffHours/24);
	milliseconds = diff % 1000;
	seconds = diffSecs % 60;
	mins = diffMins % 60;
	hours = diffHours % 24;
	days = diffDays;
	millisecondsStr = "";
	if (milliseconds < 10){
	millisecondsStr = "00"+milliseconds;
	}
	else if (milliseconds < 100){
	millisecondsStr = "0"+milliseconds;
	}
	else millisecondsStr = milliseconds+"";
	secondsStr = (seconds < 10 ? "0"+seconds : seconds+"");
	minsStr = (mins < 10 ? "0"+mins : mins+"");
	hoursStr = (hours < 10 ? "0"+hours : hours+"");
	daysStr = (days < 10 ? "0"+days : days+"");

	document.getElementById("counter").innerText = daysStr + ":" + hoursStr + ":" + minsStr + ":" + secondsStr + ":" + millisecondsStr;
//archival update: added .textContent for compatibility where .innerText is n/a
	document.getElementById("counter").textContent = daysStr + ":" + hoursStr + ":" + minsStr + ":" + secondsStr + ":" + millisecondsStr;
	setTimeout("Countdown()", 99);
}
