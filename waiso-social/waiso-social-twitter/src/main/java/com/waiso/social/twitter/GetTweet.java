package com.waiso.social.twitter;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Thread responsavel por receber um tempo de intervalo para
 * pegar uma posicao de linha no arquivo de tweets e adicionar
 * o txt na lista de tweets para envio. 
 */
public class GetTweet extends Thread {

	private long time = 0;
	
	public GetTweet(){}
	public GetTweet(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	System.out.println(getTweet());
            	Tweet.addTweet(getTweet());
                GetTweet.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * Tweet no arquivo txt.
	 */
	public String getTweet() throws IOException {
		List<String> tweets = (new AppTxt()).getTweets();
    	Random random = new Random();
    	Integer indexTweet = random.nextInt(tweets.size()+1);//Precisa setar um alem da quantidade para pegar a ultima linha.
    	return tweets.get(indexTweet);
	}
}