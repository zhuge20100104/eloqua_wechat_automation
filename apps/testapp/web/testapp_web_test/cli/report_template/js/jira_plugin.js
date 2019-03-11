/**
 * Jira plugin services
 * 
 */

// Jira Fields
var defectSummary;
var defectDescription;
var defectTestCase;
var defectScreenShot;
var isLoggedIn = false;

var showJiraLogin = function() {
	$(".popup").show();
	$(".close").click(function(e) {
		$(".popup, .overlay").hide();
		loadApplet(e);
	});
};

saveLoginDetails = function() {
	document.cookie = "jiraUser=" + $("#user").val();
	document.cookie = "jirapwd=" + $("#pwd").val();
	$(".popup, .overlay").hide();
};

var reporter = function(e) {
	defectSummary = "Automation Story: "
			+ $(".path").text().replace(/(\r\n|\n|\r)/gm, "");
	defectDescription = $('.scenario').text();
	var p = $(e).parent().parent().find('img');
	if (p[0] != undefined) {
		var src = p[0].src;
		defectScreenShot = src;
	}
}

if (!String.prototype.startsWith) {
	String.prototype.startsWith = function(str) {
		return !this.indexOf(str);
	}
}

var createDefect = function(e) {
	if (getCookie('jiraUser') == "" || getCookie('jirapwd') == "") {
		showJiraLogin();
	} else {
		loadApplet(e);
	}
}

var loadApplet = function loadApplet(e) {
	var p = $(e).parent().find('applet');
	if (p[0] != undefined)
		p.remove();

	var myApplet = document.createElement('applet');
	myApplet.setAttribute('code', 'com.emc.jira.client.rest.Client2.class');
	myApplet.setAttribute('archive', './jira/jira.jar');
	myApplet.setAttribute('width', '40%');
	myApplet.setAttribute('height', '10%');
	reporter(e);
	var myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'baseUrl');
	myEle1.setAttribute('value', baseUrl);
	myApplet.appendChild(myEle1);

	myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'jiraUser');
	myEle1.setAttribute('value', getCookie('jiraUser'));
	myApplet.appendChild(myEle1);

	myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'jiraPwd');
	myEle1.setAttribute('value', getCookie('jirapwd'));
	myApplet.appendChild(myEle1);

	myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'defectSummary');
	myEle1.setAttribute('value', defectSummary);
	myApplet.appendChild(myEle1);

	myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'defectDescription');
	myEle1.setAttribute('value', defectDescription);
	myApplet.appendChild(myEle1);

	myEle1 = document.createElement('param');
	myEle1.setAttribute('name', 'defectScreenShot');
	myEle1.setAttribute('value', defectScreenShot);
	myApplet.appendChild(myEle1);

	e.parentElement.appendChild(myApplet);
}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1);
		if (c.indexOf(name) != -1)
			return c.substring(name.length, c.length);
	}
	return "";
}