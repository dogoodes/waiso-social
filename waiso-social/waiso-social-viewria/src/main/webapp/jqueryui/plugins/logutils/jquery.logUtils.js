/**
 * jQuery LogUtils plugin 1.0
 *
 * Copyright - Waiso 
 * 
 * @author: Equipe Waiso-Developer
 */
jQuery.logUtils = function(){
	var isLogError = true;
	var isLogDebug = true;
	var isLogInfo  = true;
	var logUtilsClass = function(){
		
		this.logError = function(message){
			if (isLogError){
				console.log("[ERRORR]:"+ message);
			}
		};
		
		this.logDebug = function(message){
			if (isLogDebug){
				console.log("[DEBUG]:"+ message);
			}
		};
		
		this.logInfo = function(message){
			if (isLogInfo){
				console.log("[INFO]:"+ message);
			}
		};
		
		var _self = this;
	};
	return new logUtilsClass();
};

var LogUtils = $.logUtils();