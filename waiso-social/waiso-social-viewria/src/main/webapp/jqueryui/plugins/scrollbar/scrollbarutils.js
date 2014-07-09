/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Rodolfo Dias - Waiso
 * 
 */
jQuery.scrollbarutils = function(){
	var scrollbarutilsClass = function(){
		var scrollbars = [];

		/**
		 * Utilize o replace quando vc tiver um combo que varia de acordo com outro campo.
		 * Por exemplo: Um combo de contatos do cliente, sempre que vc alterar o cliente vc deve carregar no combo
		 * os contatos relacionado ao cliente alterado. Nesse caso o caminho seria limpar os li do ul atraves
		 * do uso do .empty ( e.g: $("#ulPVMContato").empty(); ) e depois alimentar novamente o ul com os novos 
		 * contatos (e.g $("#ulPVMContato").append(linha); ) ao final ao inves de chamar o addScrollBar vc chama
		 * o replaceScrollBar. :)
		 * 
		 * 
		 */
		this.replaceScrollBar = function(nome, fillFunction){
			if (fillFunction != null){
				var hasScrollArea = $("#"+nome).parent().hasClass("scrollArea");
				if (hasScrollArea){
					$("#"+nome).unwrap();
				}
				fillFunction();
			}
			var scroll = _self.getScrollBar(nome);
			if (scroll == null){
				_self.addScrollBar(nome);
			}else{
				jQuery.each(scrollbars, function(i, item){
					if (item.nome == nome){
						var o = document.getElementById(nome);
						scrollbars[i] = {nome:nome, scroll:new ScrollBar(o, {trackV:"trackV", barV:"barraV", up:"bt_up", down:"bt_down"}, ScrollBar.VERTICAL)};
						scrollbars[i].scroll.refresh();
					}
				});
			}
		};
		
		this.addScrollBar = function(nome){
			var scroll = _self.getScrollBar(nome);
			if (scroll == null){
				var o = document.getElementById(nome);
				scrollbars.push({nome:nome, scroll:new ScrollBar(o, {trackV:"trackV", barV:"barraV", up:"bt_up", down:"bt_down"}, ScrollBar.VERTICAL)});
			}else{
				scroll.refresh();
			}
		};
		
		this.getScrollBar = function(nome){
			var scroll = null;
			jQuery.each(scrollbars, function(i, item){
				if (item.nome == nome){
					scroll = item.scroll;
				}
			});
			return scroll;
		};
		
		this.refreshScrollBar = function(){
			jQuery.each(scrollbars, function(i, item){
				item.scroll.refresh();
			});
		};
		
		var _self = this;
	};
	return new scrollbarutilsClass();
};