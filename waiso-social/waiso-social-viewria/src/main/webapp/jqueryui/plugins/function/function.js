try{
	document.execCommand("BackgroundImageCache",false,true);
}
catch(e){};

function ge(n){
	return document.getElementById(n);
}
var getOffset = function(o){
	for(var r = {x: o.offsetLeft, y: o.offsetTop, h: o.offsetHeight, w: o.offsetWidth}; o = o.offsetParent; r.x += o.offsetLeft, r.y += o.offsetTop);
	return r;
}
function getElementsByClassName(name, parent){
	for(var o = [], n = new RegExp("\\b" + name.replace(/([(){}|*+?.,^$\[\]\\])/g, "\\\$1") + "\\b"), l = (parent || document).getElementsByTagName("*"), j = 0, i = l.length; i--; j++)
		n.test(l[j].className) && (o[o.length] = l[j]);
	return o;
}
function getElementsInClassName(name, elements){
	for(var o = [], n = new RegExp("\\b" + name.replace(/([(){}|*+?.,^$\[\]\\])/g, "\\\$1") + "\\b"), l = elements, i = l.length; i--;)
		n.test(l[i].className) && (o[o.length] = l[i]);
	return o;
}
function findAncestor(o, tag, limit){
	for(tag = tag.toLowerCase(); o = o.parentNode;)
		if(o.tagName && o.tagName.toLowerCase() == tag)
			return o;
		else if(limit && o == limit)
			return null;
	return null;
}
function findNext(o, tag, limit){
	for(tag = tag.toLowerCase(); o = o.nextSibling;)
		if(o.tagName && o.tagName.toLowerCase() == tag)
			return o;
		else if(limit && o == limit)
			return null;
	return null;
}
function findPrevious(o, tag, limit){
	for(tag = tag.toLowerCase(); o = o.previousSibling;)
		if(o.tagName && o.tagName.toLowerCase() == tag)
			return o;
		else if(limit && o == limit)
			return null;
	return null;
}

function fontRezise(a, padrao, min, max) {
	var min = min || 8, max = max || 17, d = padrao || 14;
	var o = getElementsByClassName('fontSize');
	for(i = o.length; i--;) {
		for(var j = o[i].getElementsByTagName("*"), c = j.length; c--;) {
			var e = j[c], s = e.style.fontSize ? +e.style.fontSize.replace("px","") : d;
			a && (s < max) ? s++ : !a && (s > min && s--);
			e.style.fontSize = s + "px";
		}
	}
	window.SimpleScroll && window.SimpleScroll.refresh();
}

function addFavorite(){
	if (window.sidebar) { // Mozilla
		window.sidebar.addPanel(document.title, location.href,"");
	}else if(window.external) {
		window.external.AddFavorite(location.href, document.title);
	}
}

function simpleShowHide(o) {
	var obj = document.getElementById(o), d = obj.style.display;
	obj.style.display = d == "none" || !d ? "block" : "none";
}

function showLightBox(id, className){
	var o = ge(id);
	o.style.display = "block";

	if(!window.BOX){
		BOX = new LightBox();

		addEvent(document, "keydown", function(e){
			if(BOX.locked && e.key == 27) {
				o.style.display = "none";
				hideLightBox();
			}
		});

	}
	if(BOX.locked)
		return;

	BOX.setClassName(className ? className : "LightBox");
	BOX.setTopMost();
	var original = o.parentNode, node;
	original.style.display = "block";
	node = BOX.box.appendChild(o);
	hideLightBox = function(){
		o.style.display = "none";
		original.appendChild(node);
		BOX.hide();
	}
	BOX.show();
}

function hideLightBox(){
	if(window.BOX){
		BOX.hide();
	}
}

String.prototype.removeAccents = function(){
	var s = this, a = {a: "àáãäâå", e: "èéëê", i: "ìíïî", o: "òóõöô", u: "ùúûü", c: "ç", n: "ñ"};
	for(var i in a) s = s.replace(new RegExp("[" + a[i] + "]", "gi"), i);
	return s;
}

String.prototype.trim = function(){
	return this.replace(/^\s+|\s+$/g, "");
}

String.prototype.toURL = function(){
	return this.toLowerCase().trim().removeAccents().replace(/[^a-z0-9_]/gi, " ").replace(/ +/g, "-");
}

Add = {
	twitter: function(){
		open("http://twitter.com/home?status=" + encodeURI(document.title + "\r\n" + location.href));
	},
	favorite: function(){
		if(window.sidebar)
			window.sidebar.addPanel(document.title, location.href,"");
		else if(window.external)
			window.external.AddFavorite(location.href, document.title);
	},
	delicious: function(){
		open("http://delicious.com/save?url=" + encodeURI(location.href) + "&title=" + encodeURI(document.title));	
	}
};