package com.waiso.social.app;

import com.waiso.social.app.facebook.AppFacebook;
import com.waiso.social.app.facebook.Post;
import com.waiso.social.app.twitter.AppTwitter;
import com.waiso.social.app.twitter.GetTweet;
import com.waiso.social.app.twitter.GetUser;
import com.waiso.social.app.twitter.Retweet;
import com.waiso.social.app.twitter.Tweet;
import com.waiso.social.app.twitter.User;
import com.waiso.social.framework.Process;

public class App {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppTwitter.getTwitter();
		AppFacebook.getFacebook();
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		System.out.println("Thread 1 - Twitter");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		
		System.out.println("Thread 2 - Twitter");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Tweet tweet = new Tweet(Process.in10Minutes.getTime());
		tweet.start();
		
		System.out.println("Thread 3 - Twitter");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetUser getUser = new GetUser(Process.in3Hours.getTime(), args);
		getUser.start();
		
		System.out.println("Thread 4 - Twitter");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		User user = new User(Process.in20Seconds.getTime());
		user.start();
		
		System.out.println("Thread 5 - Twitter");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Post post = new Post(Process.in40Minutes.getTime());
		post.start();
		
		System.out.println("Thread 6 - Facebook");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		(new App()).sendInfo();
	}

	@SuppressWarnings("static-access")
	private void sendInfo() throws InterruptedException {
		//while(true){
			//(new Tweet()).tweet("Acesse: http://www.waiso.com.br");
			//new Thread().sleep(Process.in3Hours.getTime());
		//}
	}
}