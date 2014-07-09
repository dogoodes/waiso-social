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
			elementPaginacao = elementPaginacao || elementPaginacao;
			$(elementPaginacao).empty();
			if (qtdPaginas > paginasVisiveis){
				$(elementPaginacao).append("<span class=\"spanPaginacaoOut\"><</span>");
				$(elementPaginacao + " span:first").bind("click", function(event){
					_self.menosPaginacao();
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
				var linha = callback(start, i, expressao, qtdPaginas);
				$(elementPaginacao).append(linha);
			}
			
			if (qtdPaginas > paginasVisiveis){
				$(elementPaginacao).append("<span class=\"spanPaginacaoOut\">></span>");
				$(elementPaginacao + " span:last").bind("click", function(){
					$(this).toggleClass('spanPaginacao');
					_self.maisPaginacao();
				});
				$(elementPaginacao + " span:last").bind("mouseenter mouseleave", function(){
					$(this).toggleClass('spanPaginacaoOver');
					$(this).toggleClass('spanPaginacaoOut');
				});
			}
		};
		
		this.menosPaginacao = function () {
			var primeiro = parseNum($(elementPaginacao + " a:first").text());
			var step = primeiro - paginasVisiveis;
			if (step <= 1){
				_self.gerarPaginacao(1);
			}else{
				_self.gerarPaginacao(step);
			}
		};
		
		this.maisPaginacao = function(){
			//pegar o ultimo visivel que seria a proxima pagina
			var step = parseNum($(elementPaginacao + " a:last").text()) + 1;
			if (step < qtdPaginas){
				var resto = qtdPaginas - step;
				if (resto < paginasVisiveis){
					step = step - (paginasVisiveis - resto) + 1;
				}
				_self.gerarPaginacao(step);
			}
		};
		var _self = this;
		
	};
	
	return new _paginacao(callback, expressao, qtdPaginas, paginasVisiveis);
 };
 