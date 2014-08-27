package com.waiso.social.facebook;

import java.util.LinkedList;
import java.util.List;

import com.waiso.social.framework.Utils;

import facebook4j.FacebookException;

public class Like extends Thread {

	private static LinkedList<String> postIds = new LinkedList<String>();
	private static LinkedList<String> commentIds = new LinkedList<String>();
	private long time = 0;
	
	public Like(){}
	public Like(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	if (postIds.size() > 0) {
            		likePost();
            	} else {
            		Utils.log("without.list", "Post");
            	}
            	if (commentIds.size() > 0) {
            		likeComment();
            	} else {
            		Utils.log("without.list", "Comment");
            	}
                Like.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	public static void addPostId(String postId){
		postIds.add(postId);
	}
	
	public static void removePostId(String postId){
		postIds.remove(postId);
	}
	
	public static void addPostIdAll(List<String> postIds){
		postIds.addAll(postIds);
	}
	
	public static LinkedList<String> getPostIds() {
		return postIds;
	}
	
	public static void setPostIds(LinkedList<String> postIds) {
		Like.postIds = postIds;
	}
	
	public static void addCommentId(String commentId){
		commentIds.add(commentId);
	}
	
	public static void removeCommentId(String commentId){
		commentIds.remove(commentId);
	}
	
	public static void addCommentIdAll(List<String> commentIds){
		commentIds.addAll(commentIds);
	}
	
	public static LinkedList<String> getCommentIds() {
		return commentIds;
	}
	
	public static void setCommentPostIds(LinkedList<String> commentIds) {
		Like.commentIds = commentIds;
	}
	
	public String getPostId() {
		int posicao = postIds.size()-1;
		String postId = postIds.get(posicao);
		postIds.remove(posicao);
		return postId;
	}
	
	public String getCommentId() {
		int posicao = commentIds.size()-1;
		String commentId = commentIds.get(posicao);
		commentIds.remove(posicao);
		return commentId;
	}
	
	public void likePost() {
		try {
			String postId = getPostId();
			Utils.log("like.post.sending", postId);
			AppFacebook.getFacebook().likePost(postId);
			Utils.log("liked.post.sucess", postId);
		} catch (FacebookException e) {
			if (e.getErrorCode() == 100) {
				Utils.log("already.liked", "Post");
			}
			e.printStackTrace();
		}
	}
	
	public void likeComment() {
		try {
			String commentId = getCommentId();
			Utils.log("like.sending", "Comment", commentId);
			AppFacebook.getFacebook().likeComment(commentId);
			Utils.log("liked.sucess", "Comment", commentId);
		} catch (FacebookException e) {
			if (e.getErrorCode() == 100) {
				Utils.log("already.liked", "Comment");
			}
			e.printStackTrace();
		}
	}
}