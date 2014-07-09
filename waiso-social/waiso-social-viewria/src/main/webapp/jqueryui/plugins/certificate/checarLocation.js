
function getParameters(){
	var parameters = window.location.search;
	if (typeof parameters != "undefined" && parameters != ""){
		parameters = parameters.substring(1);
		var keyPairs = parameters.split("&");
		return keyPairs;
	}else{
		return null;
	}
}
//param -> key
// screenId
//param -> keyPairs
// [teste=x]
// [testex=y]
// [testey=y]
// [backTo = window.html?screenId = 8]
function getParameter(key, keyPairs){
	if (keyPairs != null){
		for(var kp = 0; kp < keyPairs.length; kp++){
			var splitInterrogacao = keyPairs[kp].split("?");
			if (splitInterrogacao != null && splitInterrogacao.length != 0){
				for(var sI = 0; sI < splitInterrogacao.length; sI++){
					var splitIgualdade = splitInterrogacao[sI].split("=");
					if (splitIgualdade[0] == key){
						return splitIgualdade[1];
					}
				}	
			}else{
				var splitIgualdade = keyPairs[kp].split("=");
				if (splitIgualdade[0] == key){
					return splitIgualdade[1];
				}
			}
		}
	}
	return null;
}