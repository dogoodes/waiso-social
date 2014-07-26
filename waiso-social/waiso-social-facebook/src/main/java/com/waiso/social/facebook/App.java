package com.waiso.social.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PostUpdate;

public class App {
	
	public static void main(String[] args) throws FacebookException {
		Facebook facebook = AppFacebook.getFacebook();
		/*
		ResponseList<Post> feed = facebook.getHome();
		for(Post post : feed){
			//post.get
		}
		*/
		
		/*
		ResponseList<Account> accounts = facebook.getAccounts();
		Account yourPageAccount = accounts.get(0);  // if index 0 is your page account.
		String pageAccessToken = yourPageAccount.getAccessToken();
		 */
		
		//PostUpdate post = new PostUpdate(new URL("http://facebook4j.org"))
		PostUpdate post = new PostUpdate("Teste bot...");
        //.picture(new URL("http://facebook4j.org/images/hero.png"))
        //.name("Facebook4J - A Java library for the Facebook Graph API")
        //.caption("facebook4j.org")
        //.description("Facebook4J is a Java library for the Facebook Graph API.");
		facebook.postFeed(post);
	}
}