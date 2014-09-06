package com.waiso.social.data;

import com.mongodb.DBCollection;

public class GenericFinder implements IGenericFinder {
	
	@Override
	public DBCollection findCollectionByName(String collection) {
		return Connector.getDataBase().getCollection(collection);
	}
}