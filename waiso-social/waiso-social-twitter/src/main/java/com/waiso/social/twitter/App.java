package com.waiso.social.twitter;

import com.waiso.social.framework.Process;;

public class App {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppTwitter.getTwitter();//Estanciando chave...
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		System.out.println("Thread 1");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		
		System.out.println("Thread 2");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Tweet tweet = new Tweet(Process.in10Minutes.getTime());
		tweet.start();
		
		System.out.println("Thread 3");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetUser getUser = new GetUser(Process.in3Hours.getTime(), args);
		getUser.start();
		
		System.out.println("Thread 4");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		User user = new User(Process.in20Seconds.getTime());
		user.start();
		
		System.out.println("Thread 5");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		(new App()).sendInfo();
	}

	@SuppressWarnings("static-access")
	private void sendInfo() throws InterruptedException {
		while(true){
			(new Tweet()).tweet("Me adicionem no Facebook? https://www.facebook.com/waisoti - Em breve teremos novidades!!! =)");
			new Thread().sleep(Process.in3Hours.getTime());
		}
	}
}