package com.waiso.social.facebook;

import static com.waiso.social.data.Constants.COLLECTION_DATA_GROUPS_CONTENT;
import static com.waiso.social.data.Constants.COLLECTION_DATA_GROUPS_POSTS;
import static com.waiso.social.data.Constants.COLLECTION_GROUPS_CONTENT;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.waiso.social.data.GenericDAO;
import com.waiso.social.data.GenericFinder;
import com.waiso.social.data.IGenericDAO;
import com.waiso.social.data.IGenericFinder;
import com.waiso.social.framework.exceptions.ObjectNotFoundException;

public class DataFacebook {
	
	public BasicDBObject getFirstContentPostGroup(String group) throws ObjectNotFoundException {
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_GROUPS_CONTENT);
		DBCursor cursor = db.find(new BasicDBObject("type", group)).sort(new BasicDBObject("timestamp", -1)).limit(1);
		while (cursor.hasNext()) {
			return (BasicDBObject) cursor.next();
		}
		throw new ObjectNotFoundException();
	}
	
	public void removeFirstContentPostGroup(String group) throws ObjectNotFoundException {
		IGenericDAO dao = new GenericDAO();
		dao.remover(COLLECTION_GROUPS_CONTENT, new BasicDBObject("type", group));
	}
	
	public DBCursor findDataGroupsContent() {
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_DATA_GROUPS_CONTENT);
		return db.find();
	}
	
	public DBCursor findDataGroupsPosts() {
		IGenericFinder finder = new GenericFinder();
		DBCollection db = finder.findCollectionByName(COLLECTION_DATA_GROUPS_POSTS);
		return db.find();
	}
}