/**
 * Carregar dados de menu, menu left (div) do sistema.
 */

$(document).ready(function(){
	$("#menu-left").load("/waiso-social-viewria/waisosocial/application/include/menu.html", function(){
		var locationFile = getLocationFile();
		if(locationFile == "home"){
			$("#menu-home").addClass("active");
		}else if(locationFile == "facebook"){
			$("#menu-facebook").addClass("facebook");
		}else if(locationFile == "twitters"){
			$("#menu-twitter").addClass("twitter");
		}else if(locationFile == "googleplus"){
			$("#menu-googleplus").addClass("googleplus");
		}else if(locationFile == "linkedin"){
			$("#menu-linkedin").addClass("linkedin");
		}else if(locationFile == "youtube"){
			$("#menu-youtube").addClass("youtube");
		}else if(locationFile == "pinterest"){
			$("#menu-pinterest").addClass("pinterest");
		}else if(locationFile == "instagram"){
			$("#menu-instagram").addClass("instagram");
		}else if(locationFile == "tumblr"){
			$("#menu-tumblr").addClass("tumblr");
		}
	});
});