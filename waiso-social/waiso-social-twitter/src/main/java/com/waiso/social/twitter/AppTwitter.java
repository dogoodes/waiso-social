package com.waiso.social.twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
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
			AccessToken accessToken = loadAccessToken();
			Twitter twitter = factory.getSingleton();
			twitter.setOAuthConsumer("F5ho1ydKgobrfLIgx1nhysgXK", "WD33Teamn4E3UUGhxPGQML51DeSyLxPxMoQhkWF61i2w199mqj");
			twitter.setOAuthAccessToken(accessToken);
			AppTwitter.twitter = twitter;
		}
		return AppTwitter.twitter;
	}
	
	private static AccessToken loadAccessToken() {
		String token = "2457262974-0bDCCPwhgpEQRTqhPz1rEUoaYO1w7fF2C5uMAsa";
		String tokenSecret = "xgtXUDMm6lUgthE4JgNkTiIYWd78zqt47hMZ3MKdhmI3r";
		return new AccessToken(token, tokenSecret);
	}
	
	public static void log(User user){
		if(GerenciadorLog.isDebug(AppTwitter.class)){
			GerenciadorLog.debug(AppTwitter.class, "Usu\u00e1rio com ID [" + user.getId() + "] e Twitter [" + user.getScreenName() + "]:");
			GerenciadorLog.debug(AppTwitter.class, "[Seguindo: " + user.getFriendsCount() + "] - [Seguido por: " + user.getFollowersCount() + "]");
		}
	}
	
	public List<Long> getFriends(String[] args){
		List<Long> idsFriends = new ArrayList<Long>();
		if(GerenciadorLog.isDebug(AppTwitter.class)){
			GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("initial.find.user", "Seguidores", "Friends"));
		}
		try{
			Twitter twitter = AppTwitter.getTwitter();
			long cursor = -1;
			IDs ids;
			do{
				if(0 < args.length){
					ids = twitter.getFriendsIDs(args[0], cursor);
				}else{
					ids = twitter.getFriendsIDs(cursor);
				}
				if(GerenciadorLog.isDebug(AppTwitter.class)){
					GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("count.find.user", ids.getIDs().length, "Seguidores", "Friends"));
				}
				for(long id : ids.getIDs()){
					idsFriends.add(id);
				}
			}while((cursor = ids.getNextCursor()) != 0);
		}catch(TwitterException e){
			e.printStackTrace();
		}
		return idsFriends;
	}
	
	public List<Long> getFollowers(String[] args){
		List<Long> idsFollowers = new ArrayList<Long>();
		if(GerenciadorLog.isDebug(AppTwitter.class)){
			GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("initial.find.user", "Seguidos", "Followers"));
		}
		try{
			Twitter twitter = AppTwitter.getTwitter();
			long cursor = -1;
			IDs ids;
			do{
				if(0 < args.length){
					ids = twitter.getFollowersIDs(args[0], cursor);
				}else{
					ids = twitter.getFollowersIDs(cursor);
				}
				if(GerenciadorLog.isDebug(AppTwitter.class)){
					GerenciadorLog.debug(AppTwitter.class, GerenciadorMensagem.getMessage("count.find.user", ids.getIDs().length, "Seguidos", "Followers"));
				}
				for(long id : ids.getIDs()){
					idsFollowers.add(id);
				}
			}while((cursor = ids.getNextCursor()) != 0);
		}catch(TwitterException e){
			e.printStackTrace();
		}
		return idsFollowers;
	}
	
	public User follow(long id){
		User user = null;
		try{
			user = AppTwitter.getTwitter().showUser(id);
			AppTwitter.log(user);
	        Double porcentagemSeguindo = AppTwitter.calculoPorcentagemSeguindo(user.getFollowersCount(), user.getFriendsCount());
	        //Porcentagem acima de 25% e nao estiver sendo seguido por nos ou no aguardo...
	        if(porcentagemSeguindo.intValue() > 25 && !user.isFollowRequestSent()){
	        	AppTwitter.getTwitter().createFriendship(id);
	        	if(GerenciadorLog.isDebug(Tweet.class)){
					GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("percentage.followers", porcentagemSeguindo));
					GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("building.friendship", user.getScreenName()));
				}
	        }
		}catch(TwitterException e){
			e.printStackTrace();
		}
		return user;
	}
	
	public User unfollow(long id){
		try{
			User user = AppTwitter.getTwitter().showUser(id);
			AppTwitter.log(user);
	        Double porcentagemSeguindo = AppTwitter.calculoPorcentagemSeguindo(user.getFollowersCount(), user.getFriendsCount());
	        boolean isUsuarioPrincipal = false;
			List<String> usersMains = (new AppTxt()).getUsersMain();
			for(String userStr : usersMains){
				if(userStr.equals(user.getScreenName())){
        			isUsuarioPrincipal = true;
        			break;
        		}
			}
	        if(!isUsuarioPrincipal && porcentagemSeguindo.intValue() < 25){
	        	AppTwitter.getTwitter().destroyFriendship(id);
	        	if(GerenciadorLog.isDebug(Tweet.class)){
					GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("percentage.followers", porcentagemSeguindo));
					GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("undoing.friendship", user.getScreenName()));
				}
	        }
	        if(isUsuarioPrincipal){
	        	GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("percentage.followers", porcentagemSeguindo));
	        	GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("user.main.content"));
	        }
	        return user;
		}catch(TwitterException e){
			if(e.getErrorCode() == 88){
				System.out.println(GerenciadorMensagem.getMessage("twitter.erro.limite"));
			}else{
				e.printStackTrace();
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}