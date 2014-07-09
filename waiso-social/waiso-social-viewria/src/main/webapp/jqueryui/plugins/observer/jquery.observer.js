/**
 * jQuery Observer plugin 1.0
 *
 * Copyright - Waiso 
 * 
 * @author: Rodolfo Dias
 *  
 * A utilizacao pode ser da seguinte forma:
 * 
 * Imagine que vc tem a tela de gerenciarContaReceber e quer saber quando um usuario cadastrou alguma informacao no ManterContaReceber para que vc insira
 * um dado na tabela que tem no gerenciador. Entao a gente faz assim:
 *
 * Aqui vc diz que quer saber sempre que uma acao ocorrer nesse objeto contaReceberVO - O contaReceberVO eh a chave eh ele que esta sendo observado pois eh
 * ele que eh usado no ManterContaReceber, no exemplo abaixo estamos dizendo que se aconteceu uma inclusao, remocao ou atualizacao nesse objeto a tela de
 * gerenciarContaReceber quer ficar sabendo para nesse caso poder atualizar a linha da tabela que esta sendo apresentada ao usuario
 * 
 * OBS: Esse codigo vai no fonte do gerenciadorContaReceber pois ele que esta observando o que esta acontecendo com o contaReceberVO
 * 
 * observer.addObserver("contaReceberVO", {
 *				insert:function(contaReceberVO){
 *					_self.inserirLinhaNaTabela(contaReceberVO);
 *				},
 *				remove:function(contaReceberVO){
 *					var rowElement = $("#trGerContaReceber"+contaReceberVO.id);
 *					if (rowElement != undefined){
 *						var newRowElement = rowElement.next();
 *		                while(newRowElement.hasClass("childRow")){
 *		                    newRowElement.remove();
 *		                    newRowElement = rowElement.next(); 
 *		                }
 *		                rowElement.remove();
 *					}
 *				},
 *				update:function(contaReceberVO){
 *					var rowElement = $("#trGerContaReceber"+contaReceberVO.id);
 *					if (rowElement != undefined){
 *						var newRowElement = rowElement.next();
 *		                while(newRowElement.hasClass("childRow")){
 *		                    newRowElement.remove();
 *		                    newRowElement = rowElement.next(); 
 *		                }
 *		                rowElement.remove();
 *		                _self.inserirLinhaNaTabela(contaReceberVO);
 *					}
 *				}
 *			});
 *
 *  Agora o codigo abaixo deve estar no fonte do ManterContaReceber, esse fonte envia uma notificacao a todos os observadores que o contaReceberVO foi 
 *  alterado por exemplo
 *  
 *  if (acao == "alterar"){
 *		sucessoJSON({jsonReturn:jsonReturn, sufixoBoxErrors:"lancContaReceber"});
 *		_self.alterarLinhaNaTabela(contaReceberVO);
 *		observer.notifyObservers("contaReceberVO", contaReceberVO, "update");
 *	}else if (acao == "incluir"){
 *		sucessoJSON({jsonReturn:jsonReturn, sufixoBoxErrors:"lancContaReceber"});
 *		_self.inserirLinhaNaTabela(contaReceberVO);
 *		observer.notifyObservers("contaReceberVO", contaReceberVO, "insert");
 *	}
 *
 *  Outro comportamento que tambem pode ser observado eh o envio de notificacao de slice, a notificacao de slice acontece quando uma tela quer saber se 
 *  outra esta em conjunto com ela atraves do Slice. Isso eh legal quando queremos executar funcoes da outra tela atraves da tela que estamos
 *  
 *  Para isso as coisas devem acontecer da seguinte forma:
 *  
 *  1 - No metodo init da classe observadora vc deve catalogar todas as telas que vc quer saber se sao conjuntas a sua, veja que tem que ser no metodo init()
 *  Nesse exemplo estou na classe gerenciadorContaReceber e to querendo saber se tem a pagina manterContaReceber comigo, isso nao eh um requisito para que 
 *  o que foi explicado acima funcione com relacao ao contaReceberVO. 
 *  
 *  var slices = [];
 *  this.init = function(){
 *  	observer.addObserver("manterContaReceber", {
 *			slice:function(data){
 *				slices.push("manterContaReceber");
 *			}
 *		});	
 *	}
 *
 *  2 - No metodo complete da classe observadora vc deve notificar que vc esta no slice, repare que passo null no campo data, mas poderia passar qualquer informacao
 *  que fosse de comum acordo entre as telas, mas tomem cuidado com Memoryleak, passa a propria referencia do javascript por exemplo (_self) nao seria uma boa
 *  pratica.
 *  
 *  this.complete = function(){
 *  	observer.notifyObservers("gerenciadorContaReceber", null, "slice");
 *  }
 *  
 *  3 - Agora eh possivel por exemplo num evento de clique saber se a outra tela esta em conjunto para poder chamar uma funcao dela
 *  $("#AlgumBotaoDaTelaGerenciador").click(function(){
 *     if ("manterContaReceber".indexOf(slices) != -1){
 *         //chamando uma funcao da tela do manterContaReceber
 *     	   $.manterContaReceber().buscar();
 *     }
 *  });
 *  
 */
jQuery.observer = function(){
	var observerClass = function(){
		var observers = [];
		
		/**
		 * Funcao que recebe os callbacks observadores. 
		 * As funcoes clientes devem informar aqui qual item observavel ela quer observar
		 * atraves do campo keyObservable e tambem o parametro callbackStruct que deve conter
		 * a estrutura com algumas das seguintes funcoes {insert:function(data), update:function(data){}, remove:function(data){}}. 
		 * Repare que a estrutura pode as seguintes funcoes que serao executados de acordo com a acao que o registro estiver sofrendo
		 * @param keyObservable: Chave de qual item esta sendo observado
		 * @param callbackStruct: Uma estrutura no formato {insert:function(data), update:function(data){}, remove:function(data){}, slide:function(data){} }
		 * para receber a notificacao quando um novo dado chegar.
		 * 
		 */
		this.addObserver = function(keyObservable, callbackStruct){
			var callbacks = observers[keyObservable];
			if (callbacks == undefined){
				observers[keyObservable] = [];
			}
			observers[keyObservable].push(callbackStruct);
		};
		
		/**
		 * Funcao que sera executado pelas funcoes observaveis em resposta de alguma
		 * alteracao. Ao receber a notificacao a funcao ira executar todos os callbacks
		 * que estao observando o objeto observavel.
		 *  @param keyObservable: Chave de qual item esta sendo observado
		 *  @param data: O dado atualizado que deve ser enviado aos observadores
		 */
		this.notifyObservers = function(keyObservable, data, action){
			var action = action || "insert";
			var callbacks = observers[keyObservable];
			if (callbacks != undefined){
				jQuery.each(callbacks, function(i, callback){
					if (action == "insert"){
						if (callback.insert != undefined){
							callback.insert(data);
						}
					}else if (action == "update"){
						if (callback.update != undefined){
							callback.update(data);
						}
					}else if (action == "remove"){
						if (callback.remove != undefined){
							callback.remove(data);
						}			
					}else if (action == "slice"){
						if (callback.slice != undefined){
							callback.slice(data);
						}
					}
				});
			}
		};
		var _self = this;
	};
	return new observerClass();
};
//Global variable to observer class
var observer = $.observer();