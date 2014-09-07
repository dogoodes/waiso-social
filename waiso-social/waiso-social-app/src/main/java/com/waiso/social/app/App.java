package com.waiso.social.app;

import com.waiso.social.facebook.AppFacebook;
import com.waiso.social.facebook.GetPost;
import com.waiso.social.facebook.Like;
import com.waiso.social.facebook.Post;
import com.waiso.social.framework.Process;
import com.waiso.social.twitter.AppTwitter;
import com.waiso.social.twitter.GetTweet;
import com.waiso.social.twitter.GetUser;
import com.waiso.social.twitter.Retweet;
import com.waiso.social.twitter.Tweet;
import com.waiso.social.twitter.User;

public class App {

	public static void main(String[] args) {
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		/*
		GetPost getPost = new GetPost(Process.in1Second.getTime());
		getPost.start();
		
		Post post = new Post(Process.in1Second.getTime());
		post.start();*/
	}
	
	@SuppressWarnings("static-access")
	public static void main1(String[] args) throws InterruptedException {
		AppFacebook.getFacebook();
		AppTwitter.getTwitter();
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetPost getPost = new GetPost(Process.in10Minutes.getTime());
		getPost.start();
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		System.out.println("Thread 1");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		Like like = new Like(Process.in2Seconds.getTime());
		like.start();
		
		System.out.println("Thread 2");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Tweet tweet = new Tweet(Process.in10Minutes.getTime());
		tweet.start();
		Post post = new Post(Process.in1Second.getTime());
		post.start();
		
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
	private void sendInfo()  throws InterruptedException {
		while(true){
			(new Tweet()).tweet("Me adicionem no Facebook? https://www.facebook.com/waisoped - Em breve teremos novidades!!! =)");
			new Thread().sleep(Process.in3Hours.getTime());
		}
	}
}