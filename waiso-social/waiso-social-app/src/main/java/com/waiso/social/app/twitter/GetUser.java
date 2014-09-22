package com.waiso.social.app.twitter;

import java.util.List;

public class GetUser extends Thread {

	private long time = 0;
	private String[] args; 
	
	public GetUser(){}
	public GetUser(long time, String[] args){
		this.time = time;
		this.args = args;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	List<Long> idsFriends = (new User()).getFriends(args);
        		List<Long> idsFollowers = (new User()).getFollowers(args);
        		
        		//Eu sigo a pessoa, mas ela nao me segue...
        		for(Long idsFollower : idsFollowers){
        			if(!idsFriends.contains(idsFollower)){
        				com.waiso.social.twitter.User.addFriendsNotFollowers(idsFollower);
        			}
        		}
        		
        		//Pessoa que me segue, mas eu nao sigo ela...
        		for(Long idsFriend : idsFriends){
        			if(!idsFollowers.contains(idsFriend)){
        				com.waiso.social.twitter.User.addFollowersNotFriends(idsFriend);
        			}
        		}
                GetUser.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}