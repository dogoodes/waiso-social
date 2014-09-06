package com.waiso.social.facebook;

import java.net.MalformedURLException;
import java.net.URL;

import facebook4j.FacebookException;
import facebook4j.Group;
import facebook4j.PostUpdate;
import facebook4j.ResponseList;

public class TesteGrupo {

	/**
	 * Nao e ideal postar no grupo com images, nao faz o upload corretamente...
	 * O certo e posta a image como link.
	 * 
	 * Images e links nao podem estar junto no mesmo post...
	 */
	public static void main(String[] args) throws FacebookException, InterruptedException, MalformedURLException {
		ResponseList<Group> groups = AppFacebook.getFacebook().getGroups();
		for (Group group : groups) {
			System.out.println(group.getName() + " - " + group.getId());
		}
		
		/*
		for (int x=0; x<50; x++) {
			AppFacebook.getFacebook().postGroupFeed("839309209426139", new PostUpdate("Mensagem: " + (x+1)));
			Thread.sleep(2000);
		}
		*/
		
		//PostUpdate postUpdate = new PostUpdate("Teste");
		//postUpdate.setLink(new URL("http://www.google.com.br"));
		//postUpdate.setPicture(new URL("http://www.waveerp.com.br/erp-viewria/erp/img/home/logo_wave.gif"));
		//AppFacebook.getFacebook().postGroupFeed("839309209426139", postUpdate);
		//AppFacebook.getFacebook().postGroupLink("839309209426139", new URL("http://www.waveerp.com.br/erp-viewria/erp/img/home/logo_wave.gif"));
	}
}