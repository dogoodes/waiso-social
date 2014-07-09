package com.waiso.social.twitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;
import com.waiso.social.framework.utilitario.DateUtils;

public class Retweet extends Thread {  

	private long time = 0;
	
	public Retweet(){}
	public Retweet(long time){
		this.time = time;
	}

	public void start(){
		AppTwitter.retweet.start();
	}
	
	public void stop(long time){
		AppTwitter.retweet.stop(time);
	}
	
	@Override
	@SuppressWarnings("resource")
    public void run() {
		while(true){
            try{
            	Calendar c = Calendar.getInstance();
            	c.setTime(new Date());
            	String dataFinal = DateUtils.getInstance().dateToString(c, "yyyy-MM-dd");
            	c.add(Calendar.DAY_OF_MONTH, -2);
            	String dataInicial = DateUtils.getInstance().dateToString(c, "yyyy-MM-dd");
        		FileInputStream stream = new FileInputStream("/home/alberto/developer/workspace/workspace-waiso-social/waiso-social-git/waiso-social/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/users-retweets");
        		InputStreamReader reader = new InputStreamReader(stream);
        		BufferedReader br = new BufferedReader(reader);
        		String user = br.readLine();
        		while(user != null) {
        			if(GerenciadorLog.isDebug(Retweet.class)){
						GerenciadorLog.debug(Retweet.class, GerenciadorMensagem.getMessage("checking.timeline.user", user));
					}
        			String mTweet = getLastTweet(dataInicial, dataFinal, user);
            		if(mTweet != null){
        				Tweet.addTweet(mTweet);
        			}
        			user = br.readLine();
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
		}catch(Exception e){
			e.printStackTrace();
		}
		return mTweet;
	}
}