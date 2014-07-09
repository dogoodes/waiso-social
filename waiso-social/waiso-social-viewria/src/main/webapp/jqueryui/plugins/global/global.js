$.ajaxSetup({
	contentType: "application/x-www-form-urlencoded; charset=utf-8"
});

var cometURL = (new String(document.location).replace(/http:\/\/[^\/]*/, '').replace(/\/waiso-social-viewria\/.*$/, '').replace(/\/waisosocial\/.*$/, '')) + "/cometd";
var months = ["janeiro", "fevereiro", "mar\u00e7o", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"];
var invoker = "";
var webClassId = "";

// Node Types
var ELEMENT_NODE = 1;
var ATTRIBUTE_NODE = 2;
var TEXT_NODE = 3;

var maskChar = "_";
var keyReturn = 13;	
var keyBackspace = 8;
var keyDelete = 46;
var keyTab = 9;
var key0 = 48;
var key9 = 57;
var keyLeftArrow = 37;
var keyUpArrow = 38;
var keyRightArrow = 39;
var keyDownArrow = 40;
var keyEnd = 35;
var keyHome = 36;
var keyEsq = 27;
var keyNumLock = 144;

var context = "/waiso-social-viewria/";
var contextServlet = "/waiso-social-viewria/"; 

var url = contextServlet + "servlet/servlet.waisosocial";
var attUrl = contextServlet + "servlet/attachment.waisosocial";
var uplUrl = contextServlet + "servlet/upload.waisosocial";
var msUrl = contextServlet + "servlet/monitor.waisosocial";

$(document).ready(function(){
	$("form").each(function(i, form){
		$(this).attr("action", url);
	});
});

/**
 * @returns String contendo mensagem de dado nao informado.
 */
function dataNotRegistered(){
	return "<span style='color:#B22222;'>N\u00e3o Informado.</span>";
}

/**
 * Calcula o resto da divisao.
 * 
 * @param dividend : Dividendo do calculo.
 * @param divisor : Divisor do calculo.
 * @returns Number resultante do calculo.
 */
function getRemainderDivision(dividend, divisor){
	return parseNum(dividend) % parseNum(divisor);
}

/**
 * Verificar se a variavel esta nula.
 * @return true caso a variavel esteja vazia.
 */
function isBlank(value){
	return typeof value == "undefined" || value == undefined || value == null || value == "null" || value == "";
}

/**
 * Verificar se a variavel foi selecionada
 * por algum valor.
 * @return true caso a variavel tenha valor
 * diferente de "Selecione".
 */
function isSelected(value){
	return value != "S" || value != "s";
}

/**
 * Verificar se a variavel esta nula.
 * @return String vazia caso a variavel seja nula.
 */
function toValidValue(value){
	if(value == null || value == "null"){
		value = "";
	}
	return value;
}

/**
 * Direciona para pagina home do sistema.
 * Em casos externos:
 * 		-Acessar pagina login, mas estiver com sessao
 * 		-Acessar pagina cadastro, mas estiver com sessao
 */
function redirectHome(){
	document.location.href = "/waiso-social-viewria/waisosocial/application/home.html";
}

/**
 * Direciona para pagina de erro fora do ambiente de usuario.
 * Em casos de erros internos:
 * 		-Busca de dados do usuario.
 * 		-Validacoes internas
 * 		-Ou qualquer outro tipo de erro (Consequence.ERROR),
 * 		que nao tenha sido planejado.
 */
function redirectInternalServerError(){
	document.location.href = "/waiso-social-viewria/error500.html";
}

/**
 * Direciona para pagina de erro dentro do ambiente de usuario.
 * Em casos de erros internos:
 * 		-Busca de dados do usuario.
 * 		-Validacoes internas
 * 		-Ou qualquer outro tipo de erro (Consequence.ERROR),
 * 		que nao tenha sido planejado.
 */
function redirectInternalLoadingServerError(){
	document.location.href = "/waiso-social-viewria/waisosocial/application/error500internal.html";
}

/**
 * Pega o nome do arquivo html carregado.
 * @returns string nome do arquivo.
 */
function getLocationFile(){
	var href = window.location.href;
	if(!isBlank(href)){
		var arrayHref = href.split("/");
		var locationFile = arrayHref[arrayHref.length-1];
		if(!isBlank(locationFile)){
			var arryLocationFile = locationFile.split(".");
			return arryLocationFile[0];
		}
	}
	return null;
}

/**
 * Pega todo os paramentros concatenado na url.
 * @return array de string todos os parametros no formato de:
 * 		parametro1=valor1
 * 		parametro2=valor2
 * 		parametro3=valor3
 */
function getParameters(){
	var parameters = window.location.search;
	if(!isBlank(parameters)){
		parameters = parameters.substring(1);
		var keyPairs = parameters.split("&");
		return keyPairs;
	}else{
		return null;
	}
}

/**
 * Pega o parametro na url.
 * @return string com o parametro da url.
 */
function getParameter(){
	var parameter = window.location.search;
	if(!isBlank(parameter)){
		parameter = parameter.substring(1);
		return parameter;
	}else{
		return null;
	}
}

/**
 * Pega o dia e o nome do mes de uma data a partir 
 * do formato dd/MM/yyyy.
 * @return string retorna apenas o dia e o nome do mes:
 * 		Exemplo:
 * 			01/Janeiro
 * 			01/Fevereiro
 */
function getDayAndMonth(date){
	if(!isBlank(date)){
		var d = date.split("/");
		var month = parseNum(d[1]) - 1;
		return d[0] + "/" + months[month];
	}else{
		return null;
	}
}

/**
 * Formatar campo de valores decimal.
 * Este metodo seta o valor direto no campo html.
 * @param field : ID do campo html.
 * @param value : Valor a ser formatado.
 * @param decimal : Quantidade de casas decimais que sera
 * formatado o campo.
 * 
 */
function setCurrencyValue(field, value, decimal){
	if(typeof value != "undefined" && value != "0,00" && value != null){
		if(decimal === undefined){
			field.val(formatCurrency(value, 2));
		}else{
			field.val(formatCurrency(value, decimal));
		}
	}	
	if(field.val() == "0,00" || value == 0 || value == "0,00"){
		field.val("");
	}
}

/**
 * Metodo responsavel por formatar valores para
 * decimal.
 * 
 * @param num : Valor a ser formatado.
 * @param decimal : Quantidade de casas decimais que sera
 * @returns String do valor formatado.
 * 
 */
function formatCurrency(num, decimal) {
	if(decimal === undefined){
		decimal = 2;
	}
	if(num == null || num == undefined){
		num = "0";
	}
	var base = Math.pow(10, decimal);
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num)){
		num = "0";
	}
	
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*base+0.50000000001);
	cents = num%base;
	if(cents < Math.pow(10, (decimal-1))){
		cents = cents.toString();
		var zeros = "";
		for(var i = decimal; i > cents.length;i--){
			zeros +="0";
		}
		cents = zeros + cents;
	}
	num = Math.floor(num/base).toString();
	for(var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
		num = num.substring(0,num.length-(4*i+3))+'.'+ num.substring(num.length-(4*i+3));
	}
	return (((sign)?'':'-') + num + ',' + cents);
}

/**
 * Metodo responsavel por formatar valores para
 * datas.
 * 
 * Formato: {dd/MM/yyyy}
 * 
 * @param value : Valor a ser formatado.
 * @returns String do valor formatado.
 */
function toValidDate(value){
	var newValue = toValidValue(value);
	if(newValue != ""){
		newValue = new String(newValue);
		if(newValue.indexOf("/") == -1){
			value = null; //Memory-Leak prevents!
			var year = newValue.substring(0, 4);
			var month = newValue.substring(4, 6);
			var day = newValue.substring(6, 8);
			newValue = day + "/" + month + "/" + year;
		}
	}
	return newValue;
}

/**
 * Metodo responsavel por formatar valores para
 * datas.
 * 
 * Formato: {dd/MM/yyyy hh:mm:ss}
 * 
 * @param value : Valor a ser formatado.
 * @returns String do valor formatado.
 */
function toValidDateTime(value){
	var newValue = toValidValue(value);
	if(newValue != ""){
		newValue = new String(newValue);
		if(newValue.indexOf("/") == -1){
			value = null; //Memory-Leak prevents!
			var year = newValue.substring(0,4);
			var month = newValue.substring(4,6);
			var day = newValue.substring(6,8);
			var hour = newValue.substring(8,10);
			var min = newValue.substring(10,12);
			var seg = newValue.substring(12,14);
			newValue = day + "/" + month + "/" + year + " " + hour + ":" + min + ":" + seg;
		}
	}
	return newValue;
}

/**
 * Metodo reponsavel por formatar valores
 * em datas de tipo data base.
 * 
 * Formato : {yyyy + MM + dd}
 * 
 * @param value : Valor a ser formatado.
 * @returns String do valor formatado.
 */
function toValidDateDB(value){
	var newValue = toValidValue(value);
	if (newValue != ""){
		newValue = new String(newValue);
		if (newValue.indexOf("/") != -1){
			value = null; //Memory-Leak prevents!
			var day = newValue.substring(0, 2);
			var month = newValue.substring(3, 5);
			var year = newValue.substring(6);
			newValue = year + "" + month + "" + day;
		}
	}
	return newValue;
}

/**
 * Metodo responsavel por formatar valores para
 * tipo CPF.
 * 
 * Formato: {999.999.999-99}
 * 
 * @param value : Valor a ser formatado.
 * @returns String do valor formatado.
 */
function toValidCPF(value){
	var newValue = toValidValue(value);
	if(newValue != ""){
		value = null; //Memory-Leak prevents!
		var cpf_1 = newValue.substring(0, 3);
		var cpf_2 = newValue.substring(3, 6);
		var cpf_3 = newValue.substring(6, 9);
		var cpf_4 = newValue.substring(9);
		newValue = cpf_1 + "." + cpf_2 + "." + cpf_3 + "-" + cpf_4;
	}
	return newValue;
}

/**
 * Metodo responsavel por formatar valores para
 * tipo CNPJ.
 * 
 * Formato: {99.999.999/9999-9}
 * 
 * @param value : Valor a ser formatado.
 * @returns String do valor formatado.
 */
function toValidCNPJ(value){
	var newValue = toValidValue(value);
	if(newValue != ""){
		value = null; //Memory-Leak prevents!
		var cnpj_1 = newValue.substring(0, 2);
		var cnpj_2 = newValue.substring(2, 5);
		var cnpj_3 = newValue.substring(5, 8);
		var cnpj_4 = newValue.substring(8, 12);
		var cnpj_5 = newValue.substring(12);
		newValue = cnpj_1 + "." + cnpj_2 + "." + cnpj_3 + "/" + cnpj_4 + "-" + cnpj_5;
	}
	return newValue;
}

/**
 * Converte uma string numerica para um int javascript.
 * return int numero convertido.
 */
function parseNum(number){
	if(isBlank(number)){
		number = "0";
	}
	if(isNaN(number)){
		var value = number.toString().replace(/\./g,'');
		value = value.replace(/\,/,'.');
		return parseFloat(value);
	}
	return parseFloat(number);
}

/**
 * Metodo responsavel por validar o evento keyPress para 
 * apenas aceitar valores numericos. 
 * @param event Evento (geralmente sera executado em um campo html).
 * @returns Boolean true para valores numericos e false caso contrario.
 */
function typeOnlyNumber(event){
	var charCode = (event.which) ? event.which : event.keyCode;
	if(charCode < key0 || charCode > key9){
		if(charCode != keyDelete && charCode != keyBackspace &&
			charCode != keyLeftArrow && charCode != keyRightArrow &&
			charCode != keyTab && charCode != keyDownArrow &&
			charCode != keyUpArrow && charCode != keyHome &&
			charCode != keyEnd && charCode != keyNumLock &&
			charCode != keyEsq && charCode != keyReturn){
			// Cancelar evento
			if($.browser.msie){
				event.cancelBubble = true;
				event.returnValue = false;
			}else{
				event.preventDefault();
			}
			return false;
		}
	}
}

/**
 * Funcao responsavel por apresentar informativo ao usuario.
 * @param jsonParam {
 * 		message: Mensagem informativo do evento apresentada ao usuario,
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		tagId: <ID da tag html que tera a mascara carregada para garantir a integridade dos dados>
 * }
 */
function infoWaitJSON(jsonParam){
	var message = jsonParam.message;
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	if(!isBlank(tagId)){
		$("#" + tagId).mask(message + " <i class='icon-spinner icon-spin'></i>");
	}
	if(!isBlank(objectId)){
		$("#" + objectId).empty().attr("style", "color:blue").append(message + " <i class='icon-spinner icon-spin'></i>");
	}
}

/**
 * Funcao responsavel por remover informativo apresentado ao usuario.
 * @param jsonParam {
 * 		objectId: <ID da tag html que ira remover a mensagem apresentara>,
 * 		tagId: <ID da tag html que tera a mascara removida>
 * }
 */
function stopInfoWaitJSON(jsonParam){
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	if(!isBlank(tagId)){
		$("#" + tagId).unmask();
	}
	if(!isBlank(objectId)){
		$("#" + objectId).empty();
	}
}

/**
 * Valicao de campos e dados do formulario, recursos HTML5 inseridos nas tags
 * seram usados para acao pelo script do layout.
 * @param jsonParam {
 * 		message: Mensagem informativo do evnto apresentada ao usuario,
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		formId: <ID do form html que tera seus dados validados>,
 * 		tagId: <ID da tag html que tera a mascara removida>
 * }
 * @return boolean de acordo com o resultado da validacao.
 */
function validationEngine(jsonParam){
	var message = jsonParam.message;
	var objectId = jsonParam.objectId;
	var formId = jsonParam.formId;
	var tagId = jsonParam.tagId;
	
	var isFormComplete = $("#" + formId).validationEngine('validate', {promptPosition : "topRight"});
	if(isFormComplete){
		infoWaitJSON({'message':message, 'objectId':objectId, 'tagId':tagId});
	}
	return isFormComplete;
}

/**
 * Funcao responsavel por apresentar a mensagem de erro ao usuario
 * no top do formulario.
 * @param jsonParam {
 * 		message: Mensagem de erro apresentada ao usuario,
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		tagId: <ID da tag html que teve informacoes enviadas para o servidor>
 * }
 * 
 * Obs: E ideal chamar a funcao infoWaitJSON() antes dessa,
 * para abrir a mascara de espera.
 */
function errorJSON(jsonParam){
	var message = jsonParam.message;
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	$("#" + tagId).unmask();
	$("#" + objectId).empty().attr("style", "color:red").append(message);
}

/**
 * Funcao responsavel por apresentar uma mensagen de erro 
 * ao usuario no top do formulario.
 * @param jsonParam {
 * 		message: Mensagen de erro apresentada ao usuario
 * }
 * 
 * Obs: E ideal chamar a funcao infoWaitJSON() antes dessa,
 * para abrir a mascara de espera.
 */
function errorJSONalert(jsonParam){
	var message = jsonParam.message;
	var alert = ["<div class='alert alert-error' id='alert-error'>"];
	alert.push("<button type='button' class='close' data-dismiss='alert'>×</button>");
	alert.push(message + "<br />");
	alert.push("</div>");
	$("#alerts-errors").append(alert.join(''));
}

/**
 * Funcao responsavel por apresentar uma lista de mensagens de erro 
 * ao usuario no top do formulario.
 * @param jsonParam {
 * 		messages: Mensagens de erro apresentada ao usuario,
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		tagId: <ID da tag html que teve informacoes enviadas para o servidor>
 * }
 * 
 * Obs: E ideal chamar a funcao infoWaitJSON() antes dessa,
 * para abrir a mascara de espera.
 */
function errorsJSONalert(jsonParam){
	var messages = jsonParam.messages;
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	var alert = ["<div class='alert alert-error' id='alert-error'>"];
	alert.push("<button type='button' class='close' data-dismiss='alert'>×</button>");
	jQuery.each(messages, function(i, message){
		alert.push(message.message + "<br />");
    });
	alert.push("</div>");
	$("#alerts-errors").append(alert.join(''));
	
	if(!isBlank(tagId)){
		$("#" + tagId).unmask();
	}
	if(!isBlank(objectId)){
		$("#" + objectId).empty().attr("style", "color:red").append("Ajuste o formul\u00e1rio de acordo com o informado acima.");
	}
}

/**
 * Funcao responsavel por apresentar uma mensagen de erro 
 * ao usuario abaixo do formulario. Quando nao existir conexao 
 * com a internet, ou algum outro erro de request.
 * @param jsonParam {
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		tagId: <ID da tag html que teve informacoes enviadas para o servidor>
 * }
 * 
 * Obs: E ideal chamar a funcao infoWaitJSON() antes dessa,
 * para abrir a mascara de espera.
 */
function erroAjaxSubmit(jsonParam){
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	var alert = ["<div class='alert alert-error' id='alert-error'>"];
	alert.push("<button type='button' class='close' data-dismiss='alert'>×</button>");
	alert.push("Algo aconteceu com sua conex\u00e3o! Verifique se voc\u00ea continua tendo acesso a internet e tente novamente mais tarde.");
	alert.push("</div>");
	$("#" + tagId).append(alert.join(''));
	
	if(!isBlank(tagId)){
		$("#" + tagId).unmask();
	}
	if(!isBlank(objectId)){
		$("#" + objectId).empty();
	}
}

/**
 * Funcao responsavel por apresentar ao usuario a mensagem de 
 * sucesso na acao, aparecera no top do formulario.
 * @param jsonParam {
 * 		message: Mensagem de sucesso apresentada ao usuario,
 * 		objectId: <ID da tag html que apresentara a mensagem>,
 * 		tagId: <ID da tag html que teve informacoes enviadas para o servidor>
 * }
 * 
 * Obs: E ideal chamar a funcao infoWaitJSON() antes dessa,
 * para abrir a mascara de espera.
 */
function sucessJSON(jsonParam){
	var message = jsonParam.message;
	var objectId = jsonParam.objectId;
	var tagId = jsonParam.tagId;
	
	$("#" + tagId).unmask();
	$("#" + objectId).empty().attr("style", "color:green").append(message);
}

/**
 * Funcao responsavel por buscar dados de visualizacao.
 * @param pageHTML : Nome da pagina que esta chamando os dados.
 * 
 * @returns Retorno um objeto no formato de json.
 */
function loadDataViewHTML(pageHTML){
	var dataHTML = null;
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		data: ({webClassId : "searchController", invoke:"searchDataViewHTML", pageHTML:pageHTML}),
		dataType:"json",
		success: function(jsonReturn){
			var consequence = jsonReturn.consequence;
			if(consequence == "NO_ACCESS"){
				document.location.href = jsonReturn.page;
			}else if(consequence == "ERROR"){
				redirectInternalServerError();
			}else if (consequence == "SUCCESS"){
				dataHTML = jsonReturn.dado;
			}
		}
	});
	return dataHTML;
}

/**
 * Metodo responsavel por buscar um respectivo dado de visualizacao,
 * carregado na tela. O arquivo xml deve esta sincronizado com os
 * dados da tabela data_view do DB Cassandra.
 * @param sc String : Grupo que o dado pertence.
 * @param tag String : Tag que o dadodeve ser encontrado no xml.
 * @param callback function() : Funcao de retorno, usada para pegar
 * o exato valor e sincronizar o retorno a funcao que realizou a 
 * chamada.
 */
function getDataViewHTML(sc, tag, callback){
	$.ajax({
		type: "GET",
		url: "/waiso-social-viewria/waisosocial/data/data-view.xml",
		sync: true,
		dataType: "xml",
		success: function(xml){
			$(xml).find(sc).each(function(){
				callback($(this).find(tag).text());
			}); 
		}
	});
}

/**
 * Funcao responsavel por alterar a configuracao de visualizacao do usuario.
 * 
 * objectId : <ID da tag html que apresentara a mensagem>,
 * tagId : <ID da tag html que teve informacoes enviadas para o servidor>
 * display : Dado do usuario que tera sua configuracao alterada.
 * value : Valor da configuracao para alterar.
 */
function updateDisplay(objectId, tagId, display, value){
	$.ajax({
		type:"POST",
		async:false,
		url:url,
		data: ({webClassId : "peopleDataSettingController", invoke:"updateSetting", display:display, value:value}),
		dataType:"json",
		success: function(jsonReturn){
			var consequence = jsonReturn.consequence;
			if(consequence == "ERROR"){
				errorJSON({'message':jsonReturn.message, 'objectId':objectId, 'tagId':tagId});
			}else if(consequence == "SUCCESS"){
				sucessJSON({'message':jsonReturn.message, 'objectId':objectId, 'tagId':tagId});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			erroAjaxSubmit({'objectId':objectId, 'tagId':tagId});
		}
	});
}

/**
 * Metodo responsavel para tranformar a sigla de tipos e 
 * um dado visivel ao usuario.
 * @param type : Dado que sera convertido (formato String). 
 * @returns Retorna o valor da sigla.
 */
function getTypes(type){
	if(type == "HO"){
		return "Residencial";
	}else if(type == "CE"){
		return "Celular";
	}else if(type == "PH"){
		return "Telefone";
	}else if(type == "BU"){
		return "Residencial";
	}else if(type == "WO"){
		return "Trabalho";
	}else if(type == "ST"){
		return "Pessoal";
	}else{
		return "Outros";
	}
}

/**
 * Metodo responsavel por retorna a data atual 
 * no formato: [dd/MM/yyyy]
 * @returns Stirng data atual.
 */
function currentBRDate(){
	var a = new Date();
	var b = a.toISOString().split("T")[0].split("-");
	return b[2] + "/" + b[1] + "/" + b[0];
}

function loadWizardPicture(callback){
	$(function() {
	    $.fn.wizard.logging = true;
	    if($("#wizard-demo").length > 0){
	        var wizard = $("#wizard-demo").wizard();
	        wizard.el.find(".btn.btn-default.wizard-back")[0].textContent = "Cancelar";
	        wizard.el.find(".btn.btn-default.wizard-back").attr("title", "Cancelar");
	        wizard.el.find(".btn.btn-default.wizard-back").click(function() {
	            wizard.reset().close();
	        });
	        wizard.el.find(".btn.wizard-next.btn-blue").click(function() {
	        	wizard.reset().close();
	            callback();
	        });
	        wizard.el.find(".pull-left.wizard-steps").remove();
	        wizard.el.find("form").attr("name", "peopleDataPictureController");
	        wizard.el.find("form").attr("id", "peopleDataPictureController");
	        wizard.el.find("form").attr("method", "post");
	        wizard.el.find("form").attr("action", "/viewria/servlet/servlet.waisosocial");
	        wizard.show();
	    }
	});
}























//TODO: Atualizar dados abaixo....
/**
 * Calcula o valor de acordo com a porcentagem informada
 * @param txtValor Valor da Base de Calculo
 * @param txtPorcentagem Porcentagem do valor 
 */
function calculaValorAplicandoPorcentagem(txtValor, txtPorcentagem){
	if (txtValor != "" && txtPorcentagem != ""){
		var valor = parseFloat(txtValor);
		var porcentagem = parseFloat(txtPorcentagem);
		if (!isNaN(valor) && !isNaN(porcentagem)){
			var resultado = (valor * porcentagem)/100;
			return resultado;
		}
	}
	return null;
}

//Usado pelo auto-complete
function log(event, data, formatted) {
	$("<li>").html( !data ? "Nao Encontrado!" : "Selecionado: " + formatted).appendTo("#result");
}

function formatItem(row) {
	return row[0] + " (<strong>id: " + row[1] + "</strong>)";
}

function formatResult(row) {
	return row[0].replace(/(<.+?>)/gi, '');
}
//Fim Usado pelo auto-complete

function getIconWarning(message){
	var imgWarning = "<a href='javascript:void(0)'>";
		imgWarning += "<img src=\"../../img/ico/sign_warning.png\" alt='"+message+"' title='"+message+"'>";
		imgWarning += "</a>";
	return imgWarning;
}

function getIconError(message){
	var imgError = "<a href='javascript:void(0)'>";
	imgError += "<img src=\"../../img/ico/sign_error.png\" alt='"+message+"' title='"+message+"'>";
	imgError += "</a>";
	return imgError;
}

function getIconSuccess(message){
	var imgSuccess = "<a href='javascript:void(0)'>";
	imgSuccess += "<img src=\"../../img/ico/sign_success.png\" alt='"+message+"' title='"+message+"'>";
	imgSuccess += "</a>";
	return imgSuccess;
}

function getColumnNumber(columnId){
	var element = document.getElementById(columnId);
	if (element == undefined || element == null){
		throw "Nao existe coluna com o Id " + columnId + ".";
	}
	return parseInt(jQuery(element).attr("data-pos"));
}

function downloadFile(options){
	$.ajax({
		type:"POST",
		url:erpAttURL,
		async:options.async,
		data: ({webClassId:options.webClassId, invoke:options.invoke, ticket:options.ticket}),
		dataType:"json",
		success: function(jsonReturn){
			var consequence = jsonReturn.consequence;
			if (consequence == "ERROR"){
				if (options.boxMessagePrefix == null){
					erroJSON({jsonReturn:{'message':options.errorMessage}});
				}else{
					erroJSON({jsonReturn:{'message':options.errorMessage}, sufixoInfoMessageArea:options.boxMessagePrefix});
				}
			}else if (consequence == "SUCCESS"){
				var fileName = jsonReturn.dado.valor;
				var oldAction = $("#"+options.formId).attr("action");
				$("#"+options.formId).attr("action",erpAttURL+"?webClassId=nfeServletAtt&invoke=downloadFile&fileName="+fileName);
				$("#"+options.formId).submit();
				$("#"+options.formId).attr("action", oldAction);
				if (options.boxMessagePrefix == null){
					infoJSON({jsonReturn:{'message':options.successMessage}});
				}else{
					infoJSON({jsonReturn:{'message':options.successMessage}, sufixoInfoMessageArea:options.boxMessagePrefix});
				}
			}
		}
	});
}