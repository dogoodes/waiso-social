package com.waiso.social.data;

import com.mongodb.DBCollection;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public interface IGenericFinder {

	public DBCollection findCollectionByName(String collection);
	public String findTweet(String position) throws ObjectNotFoundException;
}