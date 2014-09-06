package com.waiso.social.data;

import static com.waiso.social.data.Constants.COLLECTION_TWEETS;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public class GenericFinder implements IGenericFinder {
	
	@Override
	public DBCollection findCollectionByName(String collection) {
		return Connector.getDataBase().getCollection(collection);
	}
	
	@Override
	public String findTweet(String position) throws ObjectNotFoundException {
		DBCollection db = Connector.getDataBase().getCollection(COLLECTION_TWEETS);
		DBCursor cursor = db.find();
		String tweet = null;
		while (cursor.hasNext()) {
			BasicDBObject tweets = (BasicDBObject) cursor.next();
			tweet = (String)tweets.get(position);
        }
		if (com.waiso.social.framework.utilitario.StringUtils.isBlank(tweet)) {
			throw new ObjectNotFoundException();
		} else {
			return tweet;
		}
	}
}