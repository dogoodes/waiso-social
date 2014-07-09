package com.waiso.social.twitter.tweets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;

public class Tweets {

	public JSONReturn getTweets(ServletRequest request, ServletResponse response) throws IOException {
		Map<String, List<String>> mapTweetsSent = new HashMap<String, List<String>>();
		List<String> tweetsSent = com.waiso.social.twitter.Tweet.getTweetsSent();
		com.waiso.social.twitter.Tweet.tweetsSentClear();//Remove todos os tweets da pilha.
		mapTweetsSent.put("tweets_sent", tweetsSent);
		return JSONReturn.newInstance(Consequence.SUCCESS, mapTweetsSent).include("tweets_sent");
	}
}