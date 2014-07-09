package com.waiso.social.twitter.threads;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.twitter.AppTwitter;

public class Threads {

	public JSONReturn execution(ServletRequest request, ServletResponse response) throws IOException {
		String thread = request.getParameter("thread");
		String execution = request.getParameter("execution");
		
		if(thread.equals("tweets")){
			if(execution.equals("on")){
				AppTwitter.tweet.start();
			}else{
				AppTwitter.tweet.stop(com.waiso.social.twitter.Process.in1Second.getTime());
			}
		}else if(thread.equals("retweets")){
			if(execution.equals("on")){
				AppTwitter.retweet.start();
			}else{
				AppTwitter.retweet.stop(com.waiso.social.twitter.Process.in1Second.getTime());
			}
		}else if(thread.equals("users")){
			if(execution.equals("on")){
				AppTwitter.user.start();
			}else{
				AppTwitter.user.stop(com.waiso.social.twitter.Process.in1Second.getTime());
			}
		}
		
		return JSONReturn.newInstance(Consequence.SUCCESS);
	}
	
	/**
	 * Threads em execucao...
	 */
	public JSONReturn executions(ServletRequest request, ServletResponse response) {
		Map<String, Boolean> mapThreads = new HashMap<String, Boolean>();
		mapThreads.put("tweet", AppTwitter.tweet.isAlive());
		mapThreads.put("retweet", AppTwitter.retweet.isAlive());
		mapThreads.put("user", AppTwitter.user.isAlive());
		return JSONReturn.newInstance(Consequence.SUCCESS, mapThreads);
	}
}