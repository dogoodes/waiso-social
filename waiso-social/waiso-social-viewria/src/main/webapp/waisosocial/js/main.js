/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Equipe Waiso-Developer - Waiso
 * 
 */
jQuery.main = function(){
	var mainClass = function(){
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
					}
				}
			});
		};

		var _self = this;
	};
	return new mainClass();
};