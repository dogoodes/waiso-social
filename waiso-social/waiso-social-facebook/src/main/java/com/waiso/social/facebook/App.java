package com.waiso.social.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Post;
import facebook4j.ResponseList;

public class App {

	public static void main(String[] args) throws FacebookException {
		Facebook facebook = AppFacebook.getFacebook();
		
		ResponseList<Post> feed = facebook.getHome();
		for(Post post : feed){
			//post.get
		}
		
		
	}
}