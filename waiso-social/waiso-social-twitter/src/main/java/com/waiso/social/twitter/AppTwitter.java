package com.waiso.social.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class AppTwitter {

	private static Twitter twitter = null;
	private static final String ACCESS_TOKEN = "2457262974-0bDCCPwhgpEQRTqhPz1rEUoaYO1w7fF2C5uMAsa";
	private static final String ACCESS_TOKEN_SECRET = "xgtXUDMm6lUgthE4JgNkTiIYWd78zqt47hMZ3MKdhmI3r";
	private static final String CONSUMER_KEY = "F5ho1ydKgobrfLIgx1nhysgXK";
	private static final String CONSUMER_SECRET = "WD33Teamn4E3UUGhxPGQML51DeSyLxPxMoQhkWF61i2w199mqj";

	public static Double calculoPorcentagemSeguindo(Integer seguidores, Integer seguindo){
		return (double) ((100*seguindo)/(seguidores));
	}

	public static Twitter getTwitter(){
		if(AppTwitter.twitter == null){
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthAccessToken(ACCESS_TOKEN);
			builder.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
			builder.setOAuthConsumerKey(CONSUMER_KEY);
			builder.setOAuthConsumerSecret(CONSUMER_SECRET);
			OAuthAuthorization auth = new OAuthAuthorization(builder.build());
			Twitter twitter = (new TwitterFactory()).getInstance(auth);
			twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET));
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