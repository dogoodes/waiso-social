package com.waiso.social.twitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class User extends Thread {

	//followers = que eu sigo
	//friends = que me segue

	//Pessoa que me segue, mas eu nao sigo ela...
	private static LinkedList<Long> friendsNotFollowers = new LinkedList<Long>();
	
	//Eu sigo a pessoa, mas ela nao me segue...
	private static LinkedList<Long> followersNotFriends = new LinkedList<Long>();
	
	private long time = 0;
	
	public User(){}
	public User(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	//Vou seguir pessoa que me segue, mas eu nao sigo ela... 
            	if(friendsNotFollowers.size() > 0){
            		Long idFriend = getIdFriend();
            		twitter4j.User user = follow(idFriend);
            		if(user != null){
            			String tweetThanks = GerenciadorMensagem.getMessage("thanks.for.follow.us", ("@"+user.getScreenName()));
            			(new Tweet()).tweet(tweetThanks);//Agradecendo usuario...

            			if(user.getURL() != null){
            				(new AppTxt()).writerUserUrl(user.getURL());
            			}
            		}
            	}
            	
            	//Vou parar de seguir pessoa que eu sigo, mas ela nao me segue...
            	if(followersNotFriends.size() > 0){
            		Long idFollowers = getIdFollower();
            		twitter4j.User user = unfollow(idFollowers);
            		if(user != null){
            			if(user.getURL() != null){
            				(new AppTxt()).writerUserUrl(user.getURL());
            			}
            		}
            	}
                Tweet.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	public static void addFriendsNotFollowers(Long idFriend){
		friendsNotFollowers.add(idFriend);
	}
	
	public static void addFriendsNotFollowersAll(List<Long> idFriends){
		friendsNotFollowers.addAll(idFriends);
	}
	
	public static void addFollowersNotFriends(Long idFollowers){
		followersNotFriends.add(idFollowers);
	}
	
	public static void addFollowersNotFriendsAll(List<Long> idFollowerss){
		followersNotFriends.addAll(idFollowerss);
	}
	
	public static LinkedList<Long> getFriendsNotFollowers() {
		return friendsNotFollowers;
	}
	
	public static void setFriendsNotFollowers(LinkedList<Long> friendsNotFollowers) {
		User.friendsNotFollowers = friendsNotFollowers;
	}
	
	public static LinkedList<Long> getFollowersNotFriends() {
		return followersNotFriends;
	}
	
	public static void setFollowersNotFriends(LinkedList<Long> followersNotFriends) {
		User.followersNotFriends = followersNotFriends;
	}
	
	//Para seguir
	public Long getIdFriend(){
		int posicao = friendsNotFollowers.size()-1;
		Long idFriend = friendsNotFollowers.get(posicao);
		friendsNotFollowers.remove(posicao);
		return idFriend;
	}
	
	//Para nao seguir
	public Long getIdFollower(){
		int posicao = followersNotFriends.size()-1;
		Long idFollower = followersNotFriends.get(posicao);
		followersNotFriends.remove(posicao);
		return idFollower;
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
			if(e.getErrorCode() == 88){
				GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("twitter.error.limit"));
			}else{
				e.printStackTrace();
			}
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
			if(e.getErrorCode() == 88){
				GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("twitter.error.limit"));
			}else{
				e.printStackTrace();
			}
		}
		return idsFollowers;
	}
	
	public twitter4j.User follow(long id){
		twitter4j.User user = null;
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
			if(e.getErrorCode() == 88){
				GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("twitter.error.limit"));
			}else{
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public twitter4j.User unfollow(long id){
		try{
			twitter4j.User user = AppTwitter.getTwitter().showUser(id);
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
			//Caso seja um usuario que eu sigo e ele nao me segue, nao precisa nem verificar a porcentagem de seguidores...
	        if(!isUsuarioPrincipal){
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
				GerenciadorLog.debug(Tweet.class, GerenciadorMensagem.getMessage("twitter.error.limit"));
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