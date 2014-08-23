package com.waiso.social.facebook;

import com.waiso.social.framework.Utils;

import facebook4j.Comment;
import facebook4j.FacebookException;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.ResponseList;

public class GetPost extends Thread {

	private long time = 0;
	
	public GetPost(){}
	public GetPost(long time){
		this.time = time;
	}
	
	@Override
    public void run() {
        while(true) {
            try{
            	Utils.logMessage("checking.timeline.home");
            	getTimelineHome();
            	GetPost.sleep(time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	private void getTimelineHome() {
		try {
			ResponseList<Post> posts = AppFacebook.getFacebook().getHome();
			for (Post post : posts) {
				String postId = post.getId();
				Like.removePostId(postId);
				Like.addPostId(postId);
				
				PagableList<Comment> comments = post.getComments();
				for (Comment comment : comments) {
					String commentId = comment.getId();
					Like.removeCommentId(commentId);
					Like.addCommentId(commentId);
				}
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		
	}
}