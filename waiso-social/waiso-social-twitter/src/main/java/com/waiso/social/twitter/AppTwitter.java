package com.waiso.social.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class AppTwitter {

	private static Twitter twitter = null;

	public static Double calculoPorcentagemSeguindo(Integer seguidores, Integer seguindo){
		return (double) ((100*seguindo)/(seguidores));
	}
	
	@SuppressWarnings("static-access")
	public static Twitter getTwitter(){
		if(AppTwitter.twitter == null){
			TwitterFactory factory = new TwitterFactory();
			Twitter twitter = factory.getSingleton();
			twitter.setOAuthConsumer("F5ho1ydKgobrfLIgx1nhysgXK", "WD33Teamn4E3UUGhxPGQML51DeSyLxPxMoQhkWF61i2w199mqj");
			twitter.setOAuthAccessToken(new AccessToken("2457262974-0bDCCPwhgpEQRTqhPz1rEUoaYO1w7fF2C5uMAsa", "xgtXUDMm6lUgthE4JgNkTiIYWd78zqt47hMZ3MKdhmI3r"));
			AppTwitter.twitter = twitter;
		}
		return AppTwitter.twitter;
	}
	
	public static void log(User user){
		if(GerenciadorLog.isDebug(AppTwitter.class)){
			GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("twitter.user.followers.friends", user.getId(), user.getScreenName(), user.getFriendsCount(), user.getFollowersCount()));
		}
	}
}