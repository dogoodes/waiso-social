jQuery.arrayUtils = function(){
	var arrayUtilsClass = function(){
		
		this.remove = function(array, item){
			if (_self.isValidInfoToPutInArray(array, item)){
				var idx = array.indexOf(item);
				if (idx != -1){
					array.splice(idx, 1);
				}
			}else{
				throw "O tipo passado no parametro array nao e um array";
			}
			
			return array;
		};
		
		this.add = function(array, item){
			if (_self.isValidInfoToPutInArray(array, item)){
				if (! (item in array)){
					array.push(item);
				}
			}
			return array;
		};
		
		this.isValidInfoToPutInArray = function(array, item){
			return (array !=  null && item != null && jQuery.isArray(array));
		};
		
		this.hasOneElement = function(array){
			var ret = false;
			if (jQuery.isArray(array)){
				ret =  (array.length == 1);
			}
			return ret;
		};
		
		this.contains = function(array, item){
			var contains = false;
			if (_self.isValidInfoToPutInArray(array, item)){
				if (array.length > 0 && item.indexOf(array) != -1){
					contains = true;
				}
			}
			return contains;
		};
		
		this.isEmpty = function(array){
			var empty = true;
			if (array != null){
				empty = (array.length == 0);
			}
			return empty;
		};
		
		var _self = this;	
	};
	return new arrayUtilsClass();
};
		
