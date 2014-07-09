package com.waiso.social.twitter.users;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.twitter.AppTwitter;
import com.waiso.social.twitter.User;

public class Users {

	public JSONReturn getUsers(ServletRequest request, ServletResponse response){
		try {
			AppTwitter appTwitter = new AppTwitter();
			Map<String, List<String>> mapUsers = new HashMap<String, List<String>>();
			
			List<String> usersRetweets = appTwitter.getUsersRetweets();
			mapUsers.put("users_retweets", usersRetweets);
			
			List<String> usersMain = appTwitter.getUsersMain();
			mapUsers.put("users_main", usersMain);
			
			List<String> usersFollow = User.getFollow();
			mapUsers.put("users_follow", usersFollow);

			List<String> usersUnfollow = User.getUnfollow();
			mapUsers.put("users_unfollow", usersUnfollow);
			
			return JSONReturn.newInstance(Consequence.SUCCESS, mapUsers).include("users_retweets", "users_main", "users_follow", "users_unfollow");
		} catch (IOException e) {
			return JSONReturn.newInstance(Consequence.ERROR).message(e.getMessage());
		}
	}

}