/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Rodolfo Dias - Waiso
 * @email  dias.rodolfo@gmail.com
 * 		   dias.rodolfo@wavesolutions.com.br
 * 
 * 
 * <li class="mg19">       
 *		<ul class="ferramentas">
 *           <li><a href="javascript:void(0)" id="lnkIncluirObservacao" title="Incluir">Incluir <img src="../img/ico/incluir.gif" alt="Incluir"/></a></li>
 *           <li><a href="javascript:void(0)" id="lnkAlterarObservacao" title="Alterar">Alterar <img src="../img/ico/editar.gif" alt="Alterar" /></a></li>
 *           <li><a href="javascript:void(0)" id="lnkExcluirObservacao" title="Limpar Tela">Excluir <img src="../img/ico/fechar.gif" alt="Excluir"/></a></li>
 *        </ul>       
 *     	<label for="txtObservacao" id="lblObservacao" class="labelsform">Dados Complementares</label>
 *		<div class="textarea_chamado">
 *			<input type="hidden" id="hdObservacao" name="hdObservacao"/>
 *			<textarea id="txtObservacao" name="txtObservacao" wrap="soft" rows="7" cols="154"></textarea>
 *			<label id="txtObservacaoLimites">0 / 500</label>
 *		</div>
 *	</li>
 * 
 *  how to use:
 *
 *  
 *   0 - You must be sure that you did the primary configuration to have a autocomplete working
 *      0.1 - Setting autocompleto to specify field
 *            var collectionData = manterInformacao.buscarInformacoes("0", "Observacao", "lancContaReceber");
 *            if (collectionData != null){
 *            	$("#txtObservacao").autocomplete(collectionData,{
 *					width: 310,
 *					matchContains: false,
 *					selectFirst:false,
 *					formatMatch: function(row, i, max) {
 *						return row.informacoes;
 *					},
 *			  		formatItem:function(row, i, max) {	
 *						return row.informacoes;
 *					},
 *					formatResult: function(row) {
 *						return row.informacoes;
 *					}
 *				});
 *			}
 *
 *
 *      0.2 - Setting the return of the data selected
 *      
 *  		$("#txtObservacao").result(function(event, data, formatted) {
 *				$("#hdObservacao").val(data.codigo);
				$("#txtObservacao").val(data.informacoes);
 *			});
 *			
 *  1 - Declare the variable and create a new instance of the manterInformacao()
 *     var manterInformacao =$.manterInformacao();
 *     
 *     
 *  2 - Create a relationship between the tool's link with your respective action
 *     2.1 - Creation a relationship between Incluir tool and manterInformacao.incluir action
 *     $("#lnkIncluirObservacao").click( function(){
 *     		manterInformacao.incluir("0", "Observacao", "lancContaReceber");
 *     });
 *     
 *     2.2 - Creation a relationship between Alterar tool and manterInformacao.alterar action
 *     $("#lnkAlterarObservacao").click( function(){
 *     		manterInformacao.alterar("0", "Observacao", "lancContaReceber");
 *     });
 *     
 *      2.3 - Creation a relationship between Alterar tool and manterInformacao.excluir action
 *     $("#lnkExcluirObservacao").click( function(){
 *     		manterInformacao.excluir("0", "Observacao", "lancContaReceber");        
 *     }); 
 */
jQuery.manterInformacao = function(){
	var manterInformacaoClass = function(){
		
		/**
		 * Function responsable to insert a Informacao in database.
		 * @param indice Identify unique of this information for this screen
		 *  	  The indice should be used because sometimes we have more than one field on screen and if you have this behaviour
		 *  	  we need so know which field we are working. If you have two field probably you have the indice 0 to the first and
		 *  	  indice 1 to second.
		 *  @param patternId The patternId is used to search the field in form. You have need ever different Id to different fields but with the same patternId
		 *  @param sufixoErro This is used to know which overlay we need use to show the message.
		 *  @return Return the data that was been create. 
		 */
		this.incluir = function(indice, patternId, sufixoErro){
			infoJSON({jsonReturn:{'message':'Gravando a Informação...'}});
			var informacao = $("#txt" + patternId).val();
			var labelText = $("#lbl"+ patternId).text();
			var data = null;
			$.ajax({
				type:"POST",
				url:erpURL,
				async:false,
				data: ({webClassId : "manterInformacao", invoke:"incluir", txtInformacoes:informacao, indice:indice, label:labelText}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if (consequence == "ERROR"){
						erroJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
					}else if (consequence == "SUCCESS"){
						sucessoJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
						data = jsonReturn.dado;
						if (data != null){
				     		$("#txt"+patternId).addNewData({data:data});
			     		}
					}else if (consequence == "MANY_ERRORS"){
						apresentarMensagens({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});	
					}
				}
			});
			return data;
		};

		/**
		 * Function responsable to update a Informacao in database.
		 * @param indice Identify unique of this information for this screen
		 *  	  The indice should be used because sometimes we have more than one field on screen and if you have this behaviour
		 *  	  we need so know which field we are working. If you have two field probably you have the indice 0 to the first and
		 *  	  indice 1 to second.
		 *  @param patternId The patternId is used to search the field in form. You have need ever different Id to different fields but with the same patternId
		 *  @param sufixoErro This is used to know which overlay we need use to show the message.
		 *  @return Return the collection data of the all information that you have persisted. 
		 *  		
		 */
		this.alterar = function(indice, patternId, sufixoErro){
			infoJSON({jsonReturn:{'message':'Gravando a Informação...'}});
			var informacao = $("#txt" + patternId).val();
			var labelText = $("#lbl"+patternId).text();
			var idInformacao = $("#hd" + patternId).val();
			var data = null;
			$.ajax({
				type:"POST",
				url:erpURL,
				async:false,
				data: ({webClassId : "manterInformacao", invoke:"alterar", txtInformacoes:informacao, indice:indice, label:labelText, idInformacao:idInformacao}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if (consequence == "ERROR"){
						erroJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
					}else if (consequence == "SUCCESS"){
						sucessoJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
						$("#txt" + patternId).flushCache();
						data = _self.buscarInformacoes(indice, patternId, sufixoErro);
						$("#txt"+patternId).setOptions({data:data});
					}else if (consequence == "MANY_ERRORS"){
						apresentarMensagens({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});	
					}
				}
			});
			return data;
		};
		
		/**
		 * Function responsable to remove a Informacao in database.
		 * @param indice Identify unique of this information for this screen
		 *  	  The indice should be used because sometimes we have more than one field on screen and if you have this behaviour
		 *  	  we need so know which field we are working. If you have two field probably you have the indice 0 to the first and
		 *  	  indice 1 to second.
		 *  @param patternId The patternId is used to search the field in form. You have need ever different Id to different fields but with the same patternId
		 *  @param sufixoErro This is used to know which overlay we need use to show the message.
		 *  @return Return the collection data of the all information that you have persisted. 
		 *  		You need use this data to fill the oncomplete again. 
		 */
		this.excluir = function(indice, patternId, sufixoErro){
			infoJSON({jsonReturn:{'message':'Excluindo a Informação...'}});
			var idInformacao = $("#hd" + patternId).val();
			var data = null;
			$.ajax({
				type:"POST",
				url:erpURL,
				async:false,
				data: ({webClassId : "manterInformacao", invoke:"excluir", idInformacao:idInformacao}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if (consequence == "ERROR"){
						erroJSON(jsonReturn);
					}else if (consequence == "SUCCESS"){
						sucessoJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
						$("#txt" + patternId).flushCache();
						data = _self.buscarInformacoes(indice, patternId, sufixoErro);
						$("#txt"+patternId).setOptions({data:data});
					}else if (consequence == "MANY_ERRORS"){
						apresentarMensagens({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});	
					}
				}
			});
			return data;
		};
		
		/**
		 * Function responsable to collect all Informacao of the database.
		 * @param indice Identify unique of this information for this screen
		 *  	  The indice should be used because sometimes we have more than one field on screen and if you have this behaviour
		 *  	  we need so know which field we are working. If you have two field probably you have the indice 0 to the first and
		 *  	  indice 1 to second.
		 *  @param patternId The patternId is used to search the field in form. You have need ever different Id to different fields but with the same patternId
		 *  @param sufixoErro This is used to know which overlay we need use to show the message.
		 *  @return Return the collection data of the all information that you have persisted. 
		 *  		
		 */
		this.buscarInformacoes = function(indice, patternId, sufixoErro){
			var data = null;
			$.ajax({
				type:"POST",
				url:erpURL,
				async:false,
				data: ({webClassId : "erpBuscador", invoke:"buscarInformacoes", indice:indice}),
				async:false,
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if (consequence == "ERROR"){
						erroJSON({jsonReturn:jsonReturn, sufixoBoxErrors:sufixoErro});
					}else if (consequence == "SUCCESS"){
						data = jsonReturn.dado;
					}
				}
			});
			return data;
		};
		
		var _self = this;
	};
	return new manterInformacaoClass();
};