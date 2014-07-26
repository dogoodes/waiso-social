package com.waiso.social.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class AppTwitter {

	private static Twitter twitter = null;

	public static Double calculoPorcentagemSeguindo(Integer seguidores, Integer seguindo){
		return (double) ((100*seguindo)/(seguidores));
	}

	public static Twitter getTwitter(){
		if(AppTwitter.twitter == null){
			ConfigurationBuilder cb = new ConfigurationBuilder();
			
			String accessToken = GerenciadorConfiguracao.getConfiguracao("twitter.oauth.accessToken");
			cb.setOAuthAccessToken(accessToken);
			
			String accessTokenSecret = GerenciadorConfiguracao.getConfiguracao("twitter.oauth.accessToken");
			cb.setOAuthAccessTokenSecret(accessTokenSecret);
			
			String consumerKey = GerenciadorConfiguracao.getConfiguracao("twitter.oauth.consumerKey");
			cb.setOAuthAccessTokenSecret(consumerKey);
			
			String consumerSecret = GerenciadorConfiguracao.getConfiguracao("twitter.oauth.consumerSecret");
			cb.setOAuthAccessTokenSecret(consumerSecret);
			
			OAuthAuthorization auth = new OAuthAuthorization(cb.build());
			
			Twitter twitter = (new TwitterFactory()).getInstance(auth);
			twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
			AppTwitter.twitter = twitter;
		}
		return AppTwitter.twitter;
	}
	
	public static void log(twitter4j.User user){
		if(GerenciadorLog.isDebug(AppTwitter.class)){
			GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("twitter.user.followers.friends", user.getId(), user.getScreenName(), user.getFriendsCount(), user.getFollowersCount()));
		}
	}
}