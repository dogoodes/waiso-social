/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Equipe Waiso-Developer - Waiso
 * 
 */
jQuery.home = function(){
	var homeClass = function(){
		this.init = function(){
			_self.loadingInitialData();
		};
		
		this.loadingInitialData = function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "ping", invoke:"ping"}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						//
					}
				}
			});
		};

		var _self = this;
	};
	return new homeClass();
};