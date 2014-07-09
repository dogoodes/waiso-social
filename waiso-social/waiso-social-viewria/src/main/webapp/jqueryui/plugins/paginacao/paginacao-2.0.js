jQuery.paginacao = function (callback, expressao, qtdPaginas, paginasVisiveis)
 {
	var paginasVisiveis = paginasVisiveis || 30;

	if (!callback)
		return false;
	
	_paginacao = function (callback, expressao, qtdPaginas, paginasVisiveis) {
		this.callback = callback;
		this.expressao = expressao;
		this.qtdPaginas = qtdPaginas;
		this.paginasVisiveis = paginasVisiveis;
		
		this.gerarPaginacao = function(start, elementPaginacao){
			start = parseNum(start);
			elementPaginacao = elementPaginacao ||"paginacao";
			if (elementPaginacao.charAt(0) != "#"){
				elementPaginacao = "#"+elementPaginacao;
			}
			$(elementPaginacao).empty();
			if (qtdPaginas > paginasVisiveis){
				$(elementPaginacao).append("<span class=\"spanPaginacaoOut\"><</span>");
				$(elementPaginacao + " span:first").bind("click", function(event){
					_self.menosPaginacao(elementPaginacao);
				});
				$(elementPaginacao + " span:first").bind("mouseenter mouseleave", function(event){
					$(this).toggleClass('spanPaginacaoOver');
					$(this).toggleClass('spanPaginacaoOut');
				});
			}
			var lastPagina = start + (paginasVisiveis - 1);
			if (lastPagina > qtdPaginas){
				lastPagina = qtdPaginas;
			}
			for(var i = start; i <= lastPagina ;i++){
				var resultado = callback(start, i, expressao, qtdPaginas);
				$(elementPaginacao).append(resultado.linha);
				if (resultado.clickEvent != null){
					$(resultado.clickEvent.findElement).click(resultado.clickEvent.clickFire);
				}
			}
			
			if (qtdPaginas > paginasVisiveis){
				$(elementPaginacao).append("<span class=\"spanPaginacaoOut\">></span>");
				$(elementPaginacao + " span:last").bind("click", function(){
					$(this).toggleClass('spanPaginacao');
					_self.maisPaginacao(elementPaginacao);
				});
				$(elementPaginacao + " span:last").bind("mouseenter mouseleave", function(){
					$(this).toggleClass('spanPaginacaoOver');
					$(this).toggleClass('spanPaginacaoOut');
				});
			}
		};
		
		this.menosPaginacao = function (elementPaginacao) {
			var primeiro = parseNum($(elementPaginacao + " a:first").text());
			var step = primeiro - paginasVisiveis;
			if (step <= 1){
				_self.gerarPaginacao(1, elementPaginacao);
			}else{
				_self.gerarPaginacao(step, elementPaginacao);
			}
		};
		
		this.maisPaginacao = function(elementPaginacao){
			//pegar o ultimo visivel que seria a proxima pagina
			var step = parseNum($(elementPaginacao + " a:last").text()) + 1;
			if (step < qtdPaginas){
				var resto = qtdPaginas - step;
				if (resto < paginasVisiveis){
					step = step - (paginasVisiveis - resto) + 1;
				}
				_self.gerarPaginacao(step, elementPaginacao);
			}
		};
		var _self = this;
		
	};
	
	return new _paginacao(callback, expressao, qtdPaginas, paginasVisiveis);
 };
 