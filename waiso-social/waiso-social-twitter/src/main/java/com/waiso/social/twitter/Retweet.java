package com.waiso.social.twitter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;
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
        		List<String> usersRetweets = (new AppTxt()).getUsersRetweets();
    			for(String user : usersRetweets){
    				if(GerenciadorLog.isDebug(Retweet.class)){
						GerenciadorLog.debug(Retweet.class, GerenciadorMensagem.getMessage("checking.timeline.user", user));
					}
        			String mTweet = getLastTweet(dataInicial, dataFinal, user);
            		if(mTweet != null){
        				Tweet.addTweet(mTweet);
        			}
    			}
            	Retweet.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

	public String getLastTweet(String dataInicial, String dataFinal, String usuario){
		String mTweet = null;//Iniciando twitter
		try{
			Date data = new Date(0);//Iniciando data 1970
			Query query = new Query();
			query.setSince(dataInicial);
			query.setUntil(dataFinal);
			query.setQuery("from:" + usuario);
			QueryResult result = AppTwitter.getTwitter().search(query);
			while(result.hasNext()){
				query = result.nextQuery();
				for(Status status : result.getTweets()){
					Date dataCriacao = status.getCreatedAt();
					//Se a data do twitter for maior que a global, significa
					//que o twitter pode ser o ultimo postado pelo usuario.
					if(dataCriacao.after(data)){
						if(GerenciadorLog.isDebug(Tweet.class)){
							GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("catching.last.tweet.user", usuario));
						}
						mTweet = status.getText();
						data = dataCriacao;
					}
				}
				result = AppTwitter.getTwitter().search(query);
			}
		}catch(TwitterException e){
			if(e.getErrorCode() == 88){
				GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("twitter.error.limit"));
			}else{
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mTweet;
	}
}