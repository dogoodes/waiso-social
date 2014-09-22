package com.waiso.social.app.twitter;

import java.util.LinkedList;
import java.util.List;

import twitter4j.TwitterException;

import com.waiso.social.framework.Utils;

public class Tweet extends Thread {

	private static LinkedList<String> tweets = new LinkedList<String>();
	private long time = 0;
	
	public Tweet() {}
	public Tweet(long time) {
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	send();
                Tweet.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	public static void addTweet(String tweet){
		tweets.add(tweet);
	}
	
	public static void addIdAll(List<String> tweets){
		tweets.addAll(tweets);
	}
	
	public static LinkedList<String> getTweets() {
		return tweets;
	}
	
	public static void setTweets(LinkedList<String> tweets) {
		Tweet.tweets = tweets;
	}
	
	public String getTweet(){
		int posicao = tweets.size()-1;
		String tweet = tweets.get(posicao);
		tweets.remove(posicao);
		return tweet;
	}

	private void send(){
		if (Tweet.getTweets().size() > 0) {
			String mTweet = getTweet();
			Utils.log("tweet.sending", mTweet);
			tweet(mTweet);
    	} else {
    		Utils.log("without.tweets");
    	}
	}
	
	public void tweet(String tweet) {
		try {
			try {
				AppTwitter.getTwitter().updateStatus(tweet);
				Utils.log("tweet.sent.sucess", tweet);
			} catch (TwitterException e) {
				if (e.getErrorCode() == 187) {
					Utils.log("tweet.repeated", tweet);
					send();
				} else if (e.getErrorCode() == 88) {
					Utils.log("twitter.error.limit");
				} else {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}