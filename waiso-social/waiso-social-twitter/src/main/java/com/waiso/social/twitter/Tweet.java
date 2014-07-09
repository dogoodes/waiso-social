package com.waiso.social.twitter;

import java.util.LinkedList;
import java.util.List;

import twitter4j.TwitterException;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class Tweet extends Thread {

	private static LinkedList<String> tweets = new LinkedList<String>();
	private static LinkedList<String> tweetsSent = new LinkedList<String>();
	private long time = 0;
	
	public Tweet(){}
	public Tweet(long time){
		this.time = time;
	}

	public void start(){
		AppTwitter.tweet.start();
	}
	
	public void stop(long time){
		AppTwitter.tweet.stop(time);
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	if(Tweet.getTweets().size() > 0){
        			String mTweet = getTweet();
        			if(GerenciadorLog.isDebug(Tweet.class)){
						GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("tweet.sending", mTweet));
					}
            		Tweet tweet = new Tweet();
                	tweet.tweet(mTweet);
            	}
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

	public static LinkedList<String> getTweetsSent() {
		return tweetsSent;
	}
	
	public static void setTweetsSent(LinkedList<String> tweetsSent) {
		Tweet.tweetsSent = tweetsSent;
	}
	
	public static void tweetsSentClear() {
		tweetsSent.clear();
	}
	
	public String getTweet(){
		int posicao = tweets.size()-1;
		String mTweet = tweets.get(posicao);
		tweets.remove(posicao);
		return mTweet;
	}

	public void tweet(String mTweet) {
		try{
			try{
				AppTwitter.getTwitter().updateStatus(mTweet);
				if(GerenciadorLog.isDebug(Tweet.class)){
					GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("tweet.sent.sucess", mTweet));
				}
				tweetsSent.add(mTweet);
			}catch(TwitterException e){
				if(e.getErrorCode() == 187){
					if(GerenciadorLog.isDebug(Tweet.class)){
						GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("tweet.repetido", mTweet));
					}
					String tweet = getTweet();
					tweet(tweet);//Pega outro tweet e envia.
				}else{
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}