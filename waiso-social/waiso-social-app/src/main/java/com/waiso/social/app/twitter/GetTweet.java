package com.waiso.social.app.twitter;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.waiso.social.framework.FileUtils;

public class GetTweet extends Thread {

	private long time = 0;
	
	public GetTweet(){}
	public GetTweet(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try {
            	Tweet.addTweet(getTweet());
                GetTweet.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public String getTweet() throws IOException {
		List<String> tweets = (new FileUtils()).getFileData("/waiso-social-app/src/main/resources/META-INF/app-txt/", "tweets");
    	Random random = new Random();
    	Integer indexTweet = random.nextInt(tweets.size()+1);
    	return tweets.get(indexTweet);
	}
}