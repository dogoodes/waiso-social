/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Equipe Waiso-Developer - Waiso
 * 
 */
jQuery.login = function(){
	var loginClass = function(){
		this.init = function(){
			$("#stbFormLogin").click(function(){
				_self.login();
				return false;
			});
			
			_self.loadingInitialData();
		};
		
		this.loadingInitialData = (function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "ping", invoke:"ping"}),
				dataType:"json",
				success: function(jsonReturn){
					if(jsonReturn.consequence == "SUCCESS"){
						redirectHome();
					}
				}
			});
		});
		
		this.login = (function(){
			$("#login").ajaxSubmit({
				url:url,
				dataType:"json",
				data: ({webClassId : "login"}),
				beforeSubmit: function(formData){
					return $("#login").validationEngine('validate', {promptPosition : "topRight"});
				},
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "ERROR"){
						errorJSONalert({'message':jsonReturn.message});
					}else if(consequence == "SUCCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "MANY_ERRORS"){
						errorsJSONalert({'messages':jsonReturn.dado});
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					erroAjaxSubmit({'tagId':'alerts-errors'});
				}
			});
		});

		var _self = this;
	}
	return new loginClass();
}