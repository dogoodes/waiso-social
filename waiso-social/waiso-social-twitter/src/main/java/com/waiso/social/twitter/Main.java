package com.waiso.social.twitter;

import java.io.IOException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.waiso.social.data.Constants;
import com.waiso.social.data.GenericDAO;
import com.waiso.social.data.IGenericDAO;
import com.waiso.social.framework.FileUtils;

public class Main {

	public static void main(String[] args) throws IOException {
		/*
		List<String> tweets = (new FileUtils()).getFileData("/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/", "tweets");
		IGenericDAO dao = new GenericDAO();
		Integer cont = 1;
		for (String tweet : tweets) {
			dao.insert(Constants.COLLECTION_TWEETS, new BasicDBObject(cont.toString(), tweet));
			cont++;
		}
		*/
		
		
	}
}