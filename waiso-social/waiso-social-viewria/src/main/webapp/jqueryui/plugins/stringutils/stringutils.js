jQuery.stringutils = function(){
	var stringutilsClass = function(){
		
		this.leftPad = function(str, character, size){
			if (str != null){
				var delta = size - (""+str).length;
				for(var i = 0; i < delta; i++){
					str = character + str;
				}
			}
			return str;
		};
		
		this.hasOneElement = function(array){
			var ret = false;
			if (jQuery.isArray(array)){
				ret =  (array.length == 1);
			}
			return ret;
		};
		
		this.isBlank = function(str){
			var str = toValidValue(str);
			return str == "";
		};
		
		var _self = this;	
	};
	return new stringutilsClass();
};
		
