package com.waiso.social.app.facebook;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.waiso.social.framework.Utils;

import facebook4j.FacebookException;
import facebook4j.PostUpdate;

public class Post extends Thread {

	private static LinkedList<String> posts = new LinkedList<String>();
	private static Map<String, LinkedList<PostUpdate>> groupContentPosts = new HashMap<String, LinkedList<PostUpdate>>();
	private static Map<String, LinkedList<PostUpdate>> groupPosts = new HashMap<String, LinkedList<PostUpdate>>();
	private long time = 0;
	
	public Post(){}
	public Post(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try {
            	post();
            	
            	/*
            	if (groupPosts.size() > 0) {
            		postGroups();
            	} else {
            		postContentGroups();
            	}
            	*/
            	
                Post.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void addPosts(String post){
		posts.add(post);
	}
	
	public static void addIdAll(List<String> posts){
		posts.addAll(posts);
	}
	
	public static LinkedList<String> getPosts() {
		return posts;
	}
	
	public static void setPosts(LinkedList<String> posts) {
		Post.posts = posts;
	}
	
	public static void addPostContentGroupPosts(String groupId, PostUpdate post){
		LinkedList<PostUpdate> posts = groupPosts.get(groupId);
		if (posts == null) {
			posts = new LinkedList<PostUpdate>();
		}
		posts.add(post);
		groupContentPosts.put(groupId, posts);
	}
	
	public static void addPostGroupPosts(String groupId, PostUpdate post){
		LinkedList<PostUpdate> posts = groupPosts.get(groupId);
		if (posts == null) {
			posts = new LinkedList<PostUpdate>();
		}
		posts.add(post);
		groupPosts.put(groupId, posts);
	}
	
	public static Map<String, LinkedList<PostUpdate>> getGroupPosts() {
		return groupPosts;
	}
	
	public static void setGroupPosts(Map<String, LinkedList<PostUpdate>> groupPosts) {
		Post.groupPosts = groupPosts;
	}
	
	public String getPost() {
		int posicao = posts.size()-1;
		String post = posts.get(posicao);
		posts.remove(posicao);
		return post;
	}

	public void post() {
		try {
			try {
				if (posts.size() > 0) {
					String post = getPost();
					Utils.log("post.sending", post);
					AppFacebook.getFacebook().postFeed(new PostUpdate(post));
					Utils.log("post.sent.sucess", post);
				} else {
					Utils.log("without.list", "Post");
				}
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void postGroups() {
		try {
			try {
				for (String groupId : groupPosts.keySet()) {
					LinkedList<PostUpdate> posts = groupPosts.get(groupId);
					if (posts.size() > 0) {
						int posicao = posts.size()-1;
						PostUpdate post = posts.get(posicao);
						posts.remove(posicao);
						Utils.log("post.sending", post.getMessage());
						AppFacebook.getFacebook().postGroupFeed(groupId, post);
						Utils.log("post.sent.sucess", post.getMessage());
					} else {
						Utils.log("without.list", ("GroupId [" + groupId + "] - Post"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void postContentGroups() {
		try {
			try {
				for (String groupId : groupContentPosts.keySet()) {
					LinkedList<PostUpdate> posts = groupContentPosts.get(groupId);
					if (posts.size() > 0) {
						int posicao = posts.size()-1;
						PostUpdate post = posts.get(posicao);
						posts.remove(posicao);
						Utils.log("post.sending", post.getMessage());
						AppFacebook.getFacebook().postGroupFeed(groupId, post);
						Utils.log("post.sent.sucess", post.getMessage());
					} else {
						Utils.log("without.list", ("GroupId [" + groupId + "] - Post"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}