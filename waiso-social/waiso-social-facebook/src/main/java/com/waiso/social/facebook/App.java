package com.waiso.social.facebook;

import com.waiso.social.framework.Process;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.ResponseList;

public class App {
	/*
	public static void main(String[] args) throws FacebookException {
		Facebook facebook = AppFacebook.getFacebook();
		
		ResponseList<Post> posts = facebook.getHome();
		for (Post post : posts) {
			String postMessage = post.getMessage();
			System.out.println(postMessage);
			
			PagableList<Comment> comments = post.getComments();
			for (Comment comment : comments) {
				System.out.println(comment.getMessage());
			}
			
			String postName = post.getName();
			System.out.println(postName);
					
			if(postMessage != null){
				//facebook.likePost(post.getId());
			}
		}
	}
	*/
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppFacebook.getFacebook();
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		GetPost getPost = new GetPost(Process.in30Minutes.getTime());
		getPost.start();
		
		System.out.println("Thread 1");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		Like like = new Like(Process.in5Seconds.getTime());
		like.start();
		
		System.out.println("Thread 2");
		new Thread().sleep(Process.in10Seconds.getTime());
		
		/*
		ResponseList<Post> posts = facebook.searchPosts("techo");
		for (Post post : posts) {
			String postMessage = post.getMessage();
			System.out.println(postMessage);
			if(postMessage != null){
				facebook.likePost(post.getId());
			}
		}
		*/
		/*
		ResponseList<Friendlist> friendlists = facebook.getFriendlists();
		for (Friendlist friendlist : friendlists) {
			ResponseList<Friend> friends = facebook.getFriendlistMembers(friendlist.getId());
			System.out.println(friends.size());
			for (Friend friend : friends) {
				System.out.println(friend.getName());
			}
		}
		
		ResponseList<User> results = facebook.searchUsers("alberto");
		for (User user : results) {
			System.out.println(user.getName());
		}
		*/
		
		/*
		ResponseList<Post> posts = facebook.getFeed();
		for (Post post : posts) {
			String postMessage = post.getMessage();
			System.out.println(postMessage);
			if(postMessage != null){
				facebook.likePost(post.getId());
			}
		}
		*/
/*
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("all friends", "SELECT uid2 FROM friend WHERE uid1=me()");
		queries.put("my name", "SELECT name FROM user WHERE uid=me()");
		Map<String, JSONArray> result = facebook.executeMultiFQL(queries);
		JSONArray allFriendsJSONArray = result.get("all friends");
		for (int i = 0; i < allFriendsJSONArray.length(); i++) {
		    JSONObject jsonObject = allFriendsJSONArray.getJSONObject(i);
		    System.out.println(jsonObject.get("uid2"));
		}
		JSONArray myNameJSONArray = result.get("my name");
		System.out.println(myNameJSONArray.getJSONObject(0).get("name"));
		
	*/	
		
		//String query = "SELECT uid2 FROM friend WHERE uid1=me()";
		/*String query = "SELECT notification_id, sender_id, app_id, icon_url, title_html, body_html, href FROM notification WHERE recipient_id=me() AND is_unread = 1 AND is_hidden = 0";
		JSONArray jsonArray = facebook.executeFQL(query);
		for (int i = 0; i < jsonArray.length(); i++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(i);
		    String notificationId = jsonObject.getString("notification_id");
		    if(notificationId != null){
				facebook.likePost(notificationId);
			}
		}
		*/
		
		/*
		ResponseList<Account> accounts = facebook.getAccounts();
		Account yourPageAccount = accounts.get(0);  // if index 0 is your page account.
		String pageAccessToken = yourPageAccount.getAccessToken();
		 */
		
		/*
		//PostUpdate post = new PostUpdate(new URL("http://facebook4j.org"))
		PostUpdate post = new PostUpdate("Teste bot...");
        //.picture(new URL("http://facebook4j.org/images/hero.png"))
        //.name("Facebook4J - A Java library for the Facebook Graph API")
        //.caption("facebook4j.org")
        //.description("Facebook4J is a Java library for the Facebook Graph API.");
		facebook.postFeed(post);
		*/
	}
}