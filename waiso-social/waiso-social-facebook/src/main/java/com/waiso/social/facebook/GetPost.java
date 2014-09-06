package com.waiso.social.facebook;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.json.simple.JSONObject;

import com.waiso.social.framework.FileUtils;
import com.waiso.social.framework.Utils;
import com.waiso.social.framework.exceptions.FileException;
import com.waiso.social.framework.utilitario.StringUtils;

import facebook4j.Comment;
import facebook4j.FacebookException;
import facebook4j.Group;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.PostUpdate;
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
            try {
            	Utils.logMessage("checking.timeline.home");
            	getTimelineHome();
            	getTimelineGroups();
            	GetPost.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	private void getTimelineHome() {
		try {
			ResponseList<Post> posts = AppFacebook.getFacebook().getHome();
			estrousPostsData(posts);
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
	
	private void getTimelineGroups() {
		try {
			ResponseList<Group> groups = AppFacebook.getFacebook().getGroups();
			for (Group group : groups) {
				ResponseList<Post> posts = AppFacebook.getFacebook().getGroupFeed(group.getId());
				estrousPostsData(posts);
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
	
	private void estrousPostsData(ResponseList<Post> posts) {
		for (Post post : posts) {
			estrousPostData(post);
		}
	}
	
	private void estrousPostData(Post post) {
		String postId = post.getId();
		Like.removePostId(postId);
		Like.addPostId(postId);
		
		PagableList<Comment> comments = post.getComments();
		estrousCommentsData(comments);
	}
	
	private void estrousCommentsData(PagableList<Comment> comments) {
		for (Comment comment : comments) {
			estrousCommentData(comment);
		}
	}
	
	private void estrousCommentData(Comment comment) {
		String commentId = comment.getId();
		Like.removeCommentId(commentId);
		Like.addCommentId(commentId);
	}
	
	@SuppressWarnings("unchecked")
	public void setPostGroup(String user, String[] groupTypes, String message) {
		try {
			JSONObject jsonObject = (new FileUtils()).getFileJson("/waiso-social-facebook/src/main/resources/META-INF/facebook-json/", "groups-users-content");
			Set<String> jsonKeys = jsonObject.keySet();
			for (String groupType : jsonKeys) {
				for (int x=0, y=groupTypes.length; x<y; x++) {
					if (groupType.equals(groupTypes[x])) {
						JSONObject groupsType = (JSONObject) jsonObject.get(groupType);
						JSONObject groups = (JSONObject) groupsType.get("groups");
						if (groups != null) {
							Set<String> groupKeys = groups.keySet();
							for (String groupKey : groupKeys) {
								JSONObject group = (JSONObject) groups.get(groupKey);
								String users = (String) group.get("users-content-twitter");
								if (users.indexOf(user) != -1) {
									String groupId = (String) group.get("groupId");
									com.waiso.social.facebook.Post.addPostGroupPosts(groupId, new PostUpdate(message));
								}
							}
						}
					}
				}
			}
		} catch (FileException e) {}
	}
	
	@SuppressWarnings("unchecked")
	public void setPostGroup() {
		try {
			JSONObject jsonObject = (new FileUtils()).getFileJson("/waiso-social-facebook/src/main/resources/META-INF/facebook-json/", "groups-posts");
			Set<String> jsonKeys = jsonObject.keySet();
			for (String jsonKey : jsonKeys) {
				JSONObject groupsType = (JSONObject) jsonObject.get(jsonKey);
				JSONObject groups = (JSONObject) groupsType.get("groups");
				if (groups != null) {
					Set<String> groupKeys = groups.keySet();
					for (String groupKey : groupKeys) {
						JSONObject group = (JSONObject) groups.get(groupKey);
						String groupId = (String) group.get("groupId");
						JSONObject posts = (JSONObject) group.get("posts");
						if (posts != null) {
							Set<String> postKeys = posts.keySet();
							for (String postKey : postKeys) {
								JSONObject post = (JSONObject) posts.get(postKey);
								String message = (String) post.get("message");
								String link = (String) post.get("link");
								PostUpdate postUpdate = new PostUpdate(message);
								if (!StringUtils.isBlank(link)) {
									try {
										postUpdate.setLink(new URL(link));
									} catch (MalformedURLException e) {
										e.printStackTrace();
									}
								}
								com.waiso.social.facebook.Post.addPostGroupPosts(groupId, postUpdate);
							}
						}
					}
				}
			}
		} catch (FileException e) {}
	}
}