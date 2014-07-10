package com.waiso.social.twitter;

import java.util.LinkedList;
import java.util.List;

public class User extends Thread {

	//followers = que eu sigo
	//friends = que me segue

	//Pessoa que me segue, mas eu nao sigo ela...
	private static LinkedList<Long> friendsNotFollowers = new LinkedList<Long>();
	private static LinkedList<String> follow = new LinkedList<String>();
	private static LinkedList<String> followDataUrl = new LinkedList<String>();
	
	//Eu sigo a pessoa, mas ela nao me segue...
	private static LinkedList<Long> followersNotFriends = new LinkedList<Long>();
	private static LinkedList<String> unfollow = new LinkedList<String>();
	
	private long time = 0;
	
	public User(){}
	public User(long time){
		this.time = time;
	}
	
	public void start(){
		AppTwitter.tweet.start();
	}
	
	public void stop(long time){
		AppTwitter.tweet.stop(time);
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
            			follow.add(user.getScreenName());
            			if(user.getURL() != null){
            				followDataUrl.add(user.getURL());
            			}
            		}
            	}
            	
            	//Vou parar de seguir pessoa que eu sigo, mas ela nao me segue...
            	if(followersNotFriends.size() > 0){
            		Long idFollowers = getIdFollower();
            		String twitter = appTwitter.unfollow(idFollowers);
            		unfollow.add(twitter);
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
	
	public static LinkedList<String> getFollow() {
		return follow;
	}
	
	public static LinkedList<String> getUnfollow() {
		return unfollow;
	}
	
	public static LinkedList<String> getFollowDataUrl() {
		return followDataUrl;
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