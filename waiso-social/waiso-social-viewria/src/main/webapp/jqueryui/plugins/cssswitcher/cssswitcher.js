function setActiveStyleSheet(title) {
  var i, a, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
      a.disabled = true;
      if(a.getAttribute("title") == title) a.disabled = false;
    }
  }
}

function getActiveStyleSheet() {
  var i, a;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title") && !a.disabled) return a.getAttribute("title");
  }
  return null;
}

function getPreferredStyleSheet() {
  var i, a;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1
       && a.getAttribute("rel").indexOf("alt") == -1
       && a.getAttribute("title")
       ) return a.getAttribute("title");
  }
  return null;
}

function createCookie(name,value,days) {
  if (days) {
    var date = new Date();
    date.setTime(date.getTime()+(days*24*60*60*1000));
    var expires = "; expires="+date.toGMTString();
  }
  else expires = "";
  document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
  }
  return null;
}


function createStyleEntry(name, value){
	var store = new Persist.Store('waveStore');
	store.set(name, value);
}

function readStyleEntry(name){
	var store = new Persist.Store('waveStore');
	var style = "";
    store.get(name, function(ok, value){
    	if (ok){
    		style = value;
    	}
    });	
    return style;
}

window.onload = function(e) {
  //var cookie = readStyleEntry("style");
  var cookie = readCookie("style");
  var title = cookie ? cookie : getPreferredStyleSheet();
  setActiveStyleSheet(title);
};

window.onunload = function(e) {
  var title = getActiveStyleSheet();
  //createStyleEntry("style", title);
  createCookie("style", title, 365);
};

var parameters = getParameters();
var parceiro = getParameter("parceiro", parameters);
if (parceiro != null){
	createCookie("style", parceiro, 365);
	//createStyleEntry("style", parceiro);
	setActiveStyleSheet(parceiro);
}else{
	//var cookie = readStyleEntry("style");
	var cookie = readCookie("style");
	var title = cookie ? cookie : getPreferredStyleSheet();
	setActiveStyleSheet(title);
}
