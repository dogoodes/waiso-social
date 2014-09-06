package com.waiso.social.data;

import com.mongodb.DBCollection;

public interface IGenericFinder {

	public DBCollection findCollectionByName(String collection);
}