/**
 * Carregar dados de include, barra superir (nav) do sistema.
 */

$(document).ready(function(){
	$("#logout").click(function(){
		logout();
	});
	
	$("#bar-top").load("/waiso-social-viewria/waisosocial/application/include/nav.html", function(){
		$("#logout").click(function(){
			logout();
		});
	});
});

function logout(){
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		data: ({webClassId : "logout", invoke:"logout"}),
		dataType:"json",
		success: function(jsonReturn){
			document.location.href = jsonReturn.page;
		}
	});
}