package com.waiso.social.facebook;

import java.io.File;

import facebook4j.FacebookException;
import facebook4j.Group;
import facebook4j.Media;
import facebook4j.PhotoUpdate;
import facebook4j.PostUpdate;
import facebook4j.ResponseList;

public class Teste {

	/**
	 * Pegar imagem no servidor do www.waiso.com e post no grupo.
	 * http://stackoverflow.com/questions/1316360/reading-a-remote-file-using-java
	 * 
	 * Criar arquivo json com a chave sendo o id do grupo.
	 * Ex:
	 * 		grupoId: {
	 * 			message: "Anunciando",
	 * 			link:    "Link do anuncio"
	 * 		}
	 * 
	 * 		ou
	 * 
	 * 		grupoId: {
	 * 			message: "Anunciando"
	 * 		}
	 * 
	 * http://www.devmedia.com.br/leitura-e-escrita-de-arquivos-json-em-java/27663
	 */
	public static void main(String[] args) throws FacebookException {
		/* IMPLEMENTACAO CORRETA!!!
		Media source = new Media(new File("/home/albertocerqueira/Downloads/cultura.png"));
		PhotoUpdate update = new PhotoUpdate(source);
		update.message("teste");
		AppFacebook.getFacebook().postPhoto(update);
		*/
		
		/*NO GRUPO NAO PODEMOS POSTAR IMAGENS...
		PostUpdate update =  new PostUpdate(null);
		AppFacebook.getFacebook().postGroupFeed(groupId, update);
		*/
		
		//ResponseList<Group> results = AppFacebook.getFacebook().searchGroups("programming");
		/*
		ResponseList<Group> results = AppFacebook.getFacebook().getGroups();
		for (Group group : results) {
			System.out.println(group.getName());
			System.out.println(group.getId());
			//AppFacebook.getFacebook().postGroupFeed(group.getId(), new PostUpdate("oi")));
		}
		*/
	}
}