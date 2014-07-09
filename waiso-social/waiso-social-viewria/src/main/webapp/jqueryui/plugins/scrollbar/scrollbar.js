//+ Carlos R. L. Rodrigues
//@ http://jsfromhell.com/
ScrollBar = function(o, cn, sb, pT, pS){
	var pT = pT || "bottom", pS = pS || "right";
	var getStyle = function(o, p, n){
		var v = o.currentStyle ? o.currentStyle[p] : window.getComputedStyle ?
			document.defaultView.getComputedStyle(o, null).getPropertyValue(p) : null;
		return n ? parseInt(v, 10) : v;
	},
	cr = function(s, p){
		var o = document.createElement("div");
		return !p && (o.style.position = "absolute"), o.className = s, o;
	},
	pos = function(o){
		for(var p = {l: o.offsetLeft, t: o.offsetTop, w: o.offsetWidth, h: o.offsetHeight};
			o = o.offsetParent; p.l += o.offsetLeft, p.t += o.offsetTop);
		return p;
	},
	gW = function(o){return getStyle(o, "width", 1) || o.offsetWidth;},
	gH = function(o){return getStyle(o, "height", 1) || o.offsetHeight;}
	var s_, s, _ = (s = cr(cn.trackV)).style, $ = {sa:1, sl:1}, bar, bs = (bar = cr(cn.barV)).style, up = cr(cn.up), dw = cr(cn.down);
	var __ = (s_ = cr(cn.trackH)).style, bar_, bs_ = (bar_ = cr(cn.barH)).style, lf = cr(cn.left), rt = cr(cn.right);
	var a, as = (a = cr("scrollArea", true)).style;
	var ow = gW(o), oh = gH(o), sbox = o.style, ie = getStyle(o, "styleFloat") ? true : false, prop, f;
	for(var p in f = {"styleFloat" : "none", "float" : "none", "margin-top" : 0, "margin-bottom" : 0, "margin-left" : 0, "margin-right" : 0}){
		var propName = p.replace(/\-(\w)/g, function(a, l){return l.toUpperCase()});
		if((prop = getStyle(o, ie ? propName : p))){
			if(!ie && p == "float")
				sbox.cssFloat = f[p], a.style.cssFloat = prop;
			else
				sbox[propName] = f[p], a.style[propName] = prop;
		}
	}
	o.parentNode.replaceChild(a, o), a.appendChild(o);
	a.appendChild(s).appendChild(bar), s.appendChild(up), s.appendChild(dw);
	a.appendChild(s_).appendChild(bar_), s_.appendChild(lf), s_.appendChild(rt);
	sbox.width = ow - (sb == ScrollBar.HORIZONTAL ? 0 : s.offsetWidth) + "px",
	sbox.height = oh - (sb == ScrollBar.VERTICAL ? 0 : s_.offsetHeight) + "px";
	sbox.overflow = "hidden", sbox.position = as.position = "relative";
	var kW = getStyle(bar_, "width", 1), kH = getStyle(bar, "height", 1), tH = getStyle(s, "height", 1), tW = getStyle(s_, "width", 1);
	this.refresh = function(){
		o.scrollHeight > o.offsetHeight && (_.display = "");
		o.scrollWidth > o.offsetWidth && (__.display = "");
		$.w = tW || o.offsetWidth, $.ow = o.scrollWidth, $.lW = lf.offsetWidth, $.rW = rt.offsetHeight, $.owu = $.ow - o.offsetWidth;
		$.ow > $.w && sb != ScrollBar.VERTICAL && (sbox.height = gH(o) - (oh == gH(o) ? bar_.offsetHeight : 0) + "px");
		$.h = tH || o.offsetHeight, $.oh = o.scrollHeight, $.uH = up.offsetHeight, $.dH = dw.offsetHeight, $.ohu = $.oh - o.offsetHeight;
		if($.oh > o.offsetHeight && sb != ScrollBar.HORIZONTAL){
			_.display = "";
			bs.height = (kH || Math.max(($.h = tH || o.offsetHeight) / Math.max($.h, $.oh) * ($.h - $.uH - $.dH), 15)) + "px";
			as.width = o.offsetWidth + bar.offsetWidth + "px";
			_.height = $.h + "px",
			dw.style.top = $.h - dw.offsetHeight + "px",
			$.sa = $.h - $.uH - $.dH - bar.offsetHeight,
			$.mx = $.h - bar.offsetHeight - $.dH;
			_.left = (pS == "left" ? (sbox.left = bar.offsetWidth + "px", 0) : o.offsetWidth) + "px",
			_.top = (pT == "top" ? bar_.offsetHeight : 0) + "px",
			bs.top = Math.round(Math.min(o.scrollTop, $.oh - o.offsetHeight) / ($.ohu / $.sa)) + $.uH + "px", bs.left = 0;
			if(o.scrollTop > $.oh - o.offsetHeight)
				o.scrollTop = $.oh - o.offsetHeight;
		}
		else sbox.width = ow + "px", _.display = "none", as.width = o.offsetWidth + "px";
		if($.ow > o.offsetWidth && sb != ScrollBar.VERTICAL){
			__.display = "";
			bs_.width = (kW || Math.max(($.w = tW || o.offsetWidth) / Math.max($.w, $.ow) * ($.w - $.lW - $.rW), 15)) + "px";
			as.height = o.offsetHeight + bar_.offsetHeight + "px";
			__.width = $.w + "px",
			rt.style.left = $.w - rt.offsetWidth + "px",
			$.sl = $.w - $.rW - $.lW - bar_.offsetWidth,
			$.mx_ = $.w - bar_.offsetWidth - $.rW;
			__.left = (pS == "left" ? bar.offsetWidth : 0) + "px";
			__.top = (pT == "top" ? (sbox.top = bar_.offsetHeight + "px", 0) : o.offsetHeight) + "px",
			bs_.left = Math.round(Math.min(o.scrollLeft, $.ow - o.offsetWidth) / ($.owu / $.sl)) + $.lW + "px", bs_.top = 0;
			if(o.scrollLeft > $.ow - o.offsetWidth)
				o.scrollLeft = $.ow - o.offsetWidth;
		}
		else !($.oh > $.h) && (sbox.height = oh + "px"), __.display = "none", as.height = o.offsetHeight + "px";
	}
	this.refresh();
	new function(){
		var d = false, q = 20, x, y, it, _it, im, h, _tk, p = pos(o),
		move = function(p, trk){
			(trk && (h ? (im >= bar_.offsetLeft && im <= bar_.offsetLeft + bar_.offsetWidth) :
				(im >= bar.offsetTop && im <= bar.offsetTop + bar.offsetHeight)) && !cl()) ||
			(h ? (o.scrollLeft += p, bs_.left = Math.ceil(o.scrollLeft / ($.owu / $.sl) + $.lW) + "px") :
				(o.scrollTop += p, $.ohu && (bs.top = Math.ceil(o.scrollTop / ($.ohu / $.sa) + $.uH) + "px")));
		},
		__ = function(q, trk){
			_it = setTimeout(function(){
				it = setInterval(function(){move(q, trk);}, 50);
			}, (move(q, trk), 300));
		},
		_tk = function(e){
			var q = h ? bar_.offsetWidth * ($.owu / $.sl) : bar.offsetHeight * ($.ohu / $.sa), b = document.body,
			a = h ? (im = (e.clientX - p.l + (b.scrollLeft || b.parentNode.scrollLeft || 0))) < bar_.offsetLeft :
				(im = (e.clientY - p.t + (b.scrollTop || b.parentNode.scrollTop || 0))) < bar.offsetTop;
			__(a ? -q : q, 1);
		},
		wheel = function(e){
			var p = e.wheelDelta ? e.wheelDelta < 0 : e.detail > 0;
			h = +(_.display == "none"), d = 0;
			return __(q * (p ? h ? -1 : 1 : h ? 1 : -1)), cl(), e.preventDefault(), false;
		},
		cl = function(){clearInterval(it), clearTimeout(_it), ip = null}
		addEvent(bar, "mousedown", function(e){
			return e.stopPropagation((y = e.clientY - bar.offsetTop, h = !(d = true))), false;
		});
		addEvent(bar_, "mousedown", function(e){
			return e.stopPropagation((x = e.clientX - bar_.offsetLeft, h = d = true)), false;
		});
		addEvent(o, "mousewheel", wheel);
		o.addEventListener && o.addEventListener("DOMMouseScroll", wheel, false);
		addEvent(up, "mousedown", function(e){return __((h = 0, -q)), e.stopPropagation(), false});
		addEvent(dw, "mousedown", function(e){return __((h = 0, q)), e.stopPropagation(), false});
		addEvent(lf, "mousedown", function(e){return __(h = -q), e.stopPropagation(), false});
		addEvent(rt, "mousedown", function(e){return __(h = q), e.stopPropagation(), false});
		addEvent(s, "mousedown", function(e){return h = 0, _tk(e), false});
		addEvent(s_, "mousedown", function(e){return h = 1, _tk(e), false});
		addEvent(document, "mouseup", function(){d = cl()});
		addEvent(document, "mousemove", function(p){
			if(!d) return;
			h ? (bs_.left = ((p = p.clientX - x) < $.lW || p > $.mx_ ? (p < $.lW ? $.lW : $.mx_) : p) + "px") :
				(bs.top = ((p = p.clientY - y) < $.uH || p > $.mx ? (p < $.uH ? $.uH : $.mx) : p) + "px");
			return h ? o.scrollLeft = ((bar_.offsetLeft - $.lW) * ($.owu / $.sl)) >> 0:
				o.scrollTop = ((bar.offsetTop - $.uH) * ($.ohu / $.sa)) >> 0, false;
		});
	};
}
ScrollBar.BOTH = 0;
ScrollBar.VERTICAL = 1;
ScrollBar.HORIZONTAL = 2;