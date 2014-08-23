package com.waiso.social.facebook;

import java.util.LinkedList;
import java.util.List;

import com.waiso.social.framework.Utils;

import facebook4j.FacebookException;
import facebook4j.PostUpdate;

public class Post extends Thread {

	private static LinkedList<String> posts = new LinkedList<String>();
	private long time = 0;
	
	public Post(){}
	public Post(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	if (Post.getPosts().size() > 0) {
        			post();
            	} else {
            		Utils.log("without.posts");
            	}
                Post.sleep(time);
            }catch(Exception e){
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
	
	/**
	 * Posts na memoria. Seram enviados de acordo com a ordem da fila.
	 */
	public String getPost() {
		int posicao = posts.size()-1;
		String post = posts.get(posicao);
		posts.remove(posicao);
		return post;
	}

	public void post() {
		try {
			try {
				String post = getPost();
				Utils.log("post.sending", post);
				AppFacebook.getFacebook().postFeed(new PostUpdate(post));
				Utils.log("post.sent.sucess", post);
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}