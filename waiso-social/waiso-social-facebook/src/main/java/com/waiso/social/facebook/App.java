package com.waiso.social.facebook;

import com.waiso.social.framework.Process;

public class App {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppFacebook.getFacebook();
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetPost getPost = new GetPost(Process.in10Minutes.getTime());
		getPost.start();
		
		System.out.println("Thread 1");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Like like = new Like(Process.in3Seconds.getTime());
		like.start();
		
		System.out.println("Thread 2");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Post post = new Post(Process.in2Hours.getTime());
		post.start();
		
		(new App()).getPost();
	}
	
	@SuppressWarnings("static-access")
	private void getPost() throws InterruptedException {
		while(true){
			(new GetPost()).getPostGroup();
			new Thread().sleep(Process.in3Hours.getTime());
		}
	}
}