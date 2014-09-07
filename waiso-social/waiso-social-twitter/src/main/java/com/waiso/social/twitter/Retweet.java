package com.waiso.social.twitter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.waiso.social.framework.FileUtils;
import com.waiso.social.framework.Utils;
import com.waiso.social.framework.utilitario.DateUtils;

public class Retweet extends Thread {  

	private long time = 0;
	
	public Retweet(){}
	public Retweet(long time){
		this.time = time;
	}
	
	@Override
    public void run(){
		while(true){
            try{
            	Calendar c = Calendar.getInstance();
            	c.setTime(new Date());
            	String dataFinal = DateUtils.getInstance().dateToString(c, "yyyy-MM-dd");
            	c.add(Calendar.DAY_OF_MONTH, -2);
            	String dataInicial = DateUtils.getInstance().dateToString(c, "yyyy-MM-dd");
        		List<String> usersDataRetweets = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "users-retweets");
        		//List<String> usersDataRetweets = (new DataTwitter()).findUsersRetweets();
    			for(String userData : usersDataRetweets){
    				Utils.log("checking.timeline.user", userData);
        			String mTweet = getLastTweet(dataInicial, dataFinal, userData);
            		if(mTweet != null){
        				Tweet.addTweet(mTweet);
        			}
            		//(new DataTwitter()).insertFirstContentPostGroup(user, types, mTweet);
    			}
            	Retweet.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

	public String getLastTweet(String dataInicial, String dataFinal, String user){
		String mTweet = null;
		try {
			Date data = new Date(0);
			Query query = new Query();
			query.setSince(dataInicial);
			query.setUntil(dataFinal);
			query.setQuery("from:" + user);
			QueryResult result = AppTwitter.getTwitter().search(query);
			while (result.hasNext()) {
				query = result.nextQuery();
				for (Status status : result.getTweets()) {
					Date dataCriacao = status.getCreatedAt();
					//Se a data do twitter for maior que a global, significa
					//que o twitter pode ser o ultimo postado pelo usuario.
					if (dataCriacao.after(data)) {
						Utils.log("catching.last.tweet.user", user);
						mTweet = status.getText();
						data = dataCriacao;
					}
				}
				result = AppTwitter.getTwitter().search(query);
			}
		} catch (TwitterException e) {
			if (e.getErrorCode() == 88) {
				Utils.log("twitter.error.limit");
			} else {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mTweet;
	}
}