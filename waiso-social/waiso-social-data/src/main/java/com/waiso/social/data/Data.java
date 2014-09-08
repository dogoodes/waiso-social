package com.waiso.social.data;

import static com.waiso.social.data.Constants.COLLECTION_DATA_GROUPS_CONTENT;
import static com.waiso.social.data.Constants.COLLECTION_DATA_GROUPS_POSTS;
import static com.waiso.social.data.Constants.COLLECTION_MESSAGES;
import static com.waiso.social.data.Constants.COLLECTION_TWEETS;
import static com.waiso.social.data.Constants.COLLECTION_USERS_MAIN;
import static com.waiso.social.data.Constants.COLLECTION_USERS_RETWEETS;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.waiso.social.framework.FileUtils;
import com.waiso.social.framework.exceptions.FileException;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public class Data {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ObjectNotFoundException {
		IGenericDAO dao = new GenericDAO();
		
		List<String> messages = (new FileUtils()).getFileData("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-messages");
		Integer messageCont = 1;
		for (String message : messages) {
			BasicDBObject dataMessage = new BasicDBObject(messageCont.toString(), message);
			dao.insert(COLLECTION_MESSAGES, dataMessage);
			messageCont++;
			
			System.out.println(dataMessage);
		}
		
		List<String> tweets = (new FileUtils()).getFileData("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-tweets");
		Integer tweetCont = 1;
		for (String tweet : tweets) {
			BasicDBObject dataTweet = new BasicDBObject(tweetCont.toString(), tweet);
			dao.insert(COLLECTION_TWEETS, dataTweet);
			tweetCont++;
			
			System.out.println(dataTweet);
		}
		
		List<String> usersMain = (new FileUtils()).getFileData("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-users-main");
		Integer userMainCont = 1;
		for (String userMain : usersMain) {
			BasicDBObject dataUserMain = new BasicDBObject(userMainCont.toString(), userMain);
			dao.insert(COLLECTION_USERS_MAIN, dataUserMain);
			userMainCont++;
			
			System.out.println(dataUserMain);
		}
		
		List<String> usersRetweet = (new FileUtils()).getFileData("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-users-retweets");
		Integer userRetweetCont = 1;
		for (String userRetweet : usersRetweet) {
			String[] arrayUserRetweet = userRetweet.split(":");
			String types = arrayUserRetweet[0].replace(",", ":");
			String user = arrayUserRetweet[1];
			BasicDBObject dataUser = new BasicDBObject("user", user);
			dataUser.put("types", types);
			BasicDBObject dataUserRetweet = new BasicDBObject(userRetweetCont.toString(), dataUser);
			dao.insert(COLLECTION_USERS_RETWEETS, dataUserRetweet);
			userRetweetCont++;
			
			System.out.println(dataUserRetweet);
		}
		
		List<String> groupsContent = (new FileUtils()).getFileData("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-groups-content");
		for (String groupContent : groupsContent) {
			String[] arrayGroupContent = groupContent.split(":");
			String types = arrayGroupContent[0];
			String groupId = arrayGroupContent[1];
			String nameId = arrayGroupContent[2];
			BasicDBObject groups = new BasicDBObject("groups", new BasicDBObject(groupId, nameId));
			BasicDBObject dataGroupContent = new BasicDBObject(types, groups);
			dao.insert(COLLECTION_DATA_GROUPS_CONTENT, dataGroupContent);
			
			System.out.println(dataGroupContent);
		}
		
		try {
			JSONObject jsonObject = (new FileUtils()).getFileJson("/waiso-social-data/src/main/resources/META-INF/data-mongo/", "data-groups-posts");
			Set<String> jsonKeys = jsonObject.keySet();
			for (String groupType : jsonKeys) {
				BasicDBObject dataGroupPost = new BasicDBObject();
				JSONObject groupsType = (JSONObject) jsonObject.get(groupType);
				JSONObject groups = (JSONObject) groupsType.get("groups");
				BasicDBObject dataGroups = new BasicDBObject();
				if (groups != null) {
					Set<String> groupKeys = groups.keySet();
					for (String groupName : groupKeys) {
						JSONObject group = (JSONObject) groups.get(groupName);
						String groupId = (String) group.get("groupId");
						JSONObject post = (JSONObject) group.get("post");
						BasicDBObject dataGroup = new BasicDBObject("groupId", groupId);
						if (post != null) {
							String message = (String) post.get("message");
							String link = (String) post.get("link");
							BasicDBObject dataPosts = new BasicDBObject("message", message);
							dataPosts.put("link", link);
							dataGroup.append("post", dataPosts);
						}
						dataGroups.put(groupName, dataGroup);
					}
				}
				dataGroupPost.put(groupType, dataGroups);
				dao.insert(COLLECTION_DATA_GROUPS_POSTS, dataGroupPost);
				
				System.out.println(dataGroupPost);
			}
		} catch (FileException e) {}
	}
}