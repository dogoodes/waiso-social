package com.waiso.social.twitter;

import java.io.IOException;
import java.util.List;

import javassist.expr.NewArray;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.waiso.social.data.Constants;
import com.waiso.social.data.GenericDAO;
import com.waiso.social.data.GenericFinder;
import com.waiso.social.data.IGenericDAO;
import com.waiso.social.data.IGenericFinder;
import com.waiso.social.framework.FileUtils;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public class Main {

	public static void main(String[] args) throws IOException, ObjectNotFoundException {
		List<String> usersretweets = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "users-main");
		Integer tweetCont = 1;
		for (String userretweets : usersretweets) {
			System.out.println("db.usersmain.insert( { \"" + tweetCont++ + "\" : \"" + userretweets + "\" }" + " )");
		}
		
		//IGenericDAO dao = new GenericDAO();
		/*
		for (int x=0; x<100; x++) {
			dao.insert(Constants.COLLECTION_USERS_RETWEETS, new BasicDBObject("teste", x + ""));
		}
		*/
		
		//dao.remover(Constants.COLLECTION_USERS_RETWEETS, new BasicDBObject("1", new BasicDBObject("olhardigital", "technology")));
		/*
		List<String> usersretweets = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "users-retweets");
		Integer userretweetsCont = 1;
		for (String userretweets : usersretweets) {
			String[] arrayUserData = userretweets.split(":");
			String types = arrayUserData[0].replace(",", ":");
			String user = arrayUserData[1];
			System.out.println(userretweetsCont);
			BasicDBObject userData = new BasicDBObject("user", user);
			userData.put("types", types);
			dao.insert(Constants.COLLECTION_USERS_RETWEETS, new BasicDBObject(userretweetsCont.toString(), userData));
			userretweetsCont++;
		}
		
		List<String> tweets = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "tweets");
		Integer tweetCont = 1;
		for (String tweet : tweets) {
			System.out.println(tweetCont);
			dao.insert(Constants.COLLECTION_TWEETS, new BasicDBObject(tweetCont.toString(), tweet));
			tweetCont++;
		}
		
		List<String> usersmain = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "users-main");
		Integer usermainCont = 1;
		for (String usermain : usersmain) {
			System.out.println(usermainCont);
			dao.insert(Constants.COLLECTION_USERS_MAIN, new BasicDBObject(usermainCont.toString(), usermain));
			usermainCont++;
		}
		*/
		//IGenericFinder finder = new GenericFinder();
		/*
		DBCollection db = finder.findCollectionByName(Constants.COLLECTION_TWEETS);
		DBCursor cursor = db.find();
		while (cursor.hasNext()) {
			BasicDBObject tweets = (BasicDBObject) cursor.next();
			for (String key : tweets.keySet()) {
				System.out.println(tweets.get(key));
			}
        }
        */
		
		//String tweet = finder.findTweet("3987");
		//System.out.println(tweet);
	}
}