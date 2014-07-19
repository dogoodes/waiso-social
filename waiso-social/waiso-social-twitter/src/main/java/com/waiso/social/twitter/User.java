package com.waiso.social.twitter;

import java.util.LinkedList;
import java.util.List;

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
            	AppTwitter appTwitter = new AppTwitter();
            	
            	//Vou seguir pessoa que me segue, mas eu nao sigo ela... 
            	if(friendsNotFollowers.size() > 0){
            		Long idFriend = getIdFriend();
            		twitter4j.User user = appTwitter.follow(idFriend);
            		if(user != null){
            			if(user.getURL() != null){
            				(new AppTxt()).writerUserUrl(user.getURL());
            			}
            		}
            	}
            	
            	//Vou parar de seguir pessoa que eu sigo, mas ela nao me segue...
            	if(followersNotFriends.size() > 0){
            		Long idFollowers = getIdFollower();
            		appTwitter.unfollow(idFollowers);
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
}