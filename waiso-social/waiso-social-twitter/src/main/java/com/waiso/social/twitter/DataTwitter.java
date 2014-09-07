package com.waiso.social.twitter;

import static com.waiso.social.data.Constants.COLLECTION_GROUPS_CONTENT;
import static com.waiso.social.data.Constants.COLLECTION_TWEETS;
import static com.waiso.social.data.Constants.COLLECTION_USERS_RETWEETS;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.waiso.social.data.GenericDAO;
import com.waiso.social.data.GenericFinder;
import com.waiso.social.data.IGenericDAO;
import com.waiso.social.data.IGenericFinder;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public class DataTwitter {
	
	public void getFirstRetweet(){
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_GROUPS_CONTENT);
		DBCursor cursor = db.find().sort(new BasicDBObject("timestamp", -1)).limit(1);
		while (cursor.hasNext()) {
			BasicDBObject a = (BasicDBObject) cursor.next();
			System.out.println(a);
		}
	}
	
	public void insertFirstContentPostGroup(String user, String[] groupTypes, String message) {
		IGenericDAO dao = new GenericDAO();
		for (int x=0, y = groupTypes.length; x<y; x++) {
			BasicDBObject content = new BasicDBObject("type", groupTypes[x]);
			content.put("user", user);
			content.put("message", message);
			content.put("timestamp", System.currentTimeMillis());
			dao.insert(COLLECTION_GROUPS_CONTENT, content);
		}
	}
	
	public String findTweet(String position) throws ObjectNotFoundException {
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_TWEETS);
		DBCursor cursor = db.find();
		while (cursor.hasNext()) {
			BasicDBObject tweetNumber = (BasicDBObject) cursor.next();
			for (String number : tweetNumber.keySet()) {
				if (position.equals(number)) {
					return tweetNumber.getString(number);
				}
			}
        }
		throw new ObjectNotFoundException();
	}
	
	public List<String> findUsersRetweets() {
		List<String> usersRetweets = new ArrayList<String>();
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_USERS_RETWEETS);
		DBCursor cursor = db.find();
		while (cursor.hasNext()) {
			BasicDBObject dataUser = (BasicDBObject) cursor.next();
			for (String number : dataUser.keySet()) {
				if (!number.equals("_id")) { 
					BasicDBObject user = (BasicDBObject) dataUser.get(number);
					String userName = (String) user.get("user");
					usersRetweets.add(userName);
				}
			}
        }
		return usersRetweets;
	}
}