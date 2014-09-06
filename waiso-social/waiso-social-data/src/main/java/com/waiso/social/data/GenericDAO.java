package com.waiso.social.data;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class GenericDAO implements IGenericDAO {

	@Override
	public void insert(String dbName, BasicDBObject object) {
		DBCollection collection = Connector.getDataBase().getCollection(dbName);
		collection.insert(object);
	}

	@Override
	public void insert(String dbName, List<BasicDBObject> objects) {
		DBCollection collection = Connector.getDataBase().getCollection(dbName);
		for (BasicDBObject object : objects) {
			collection.insert(object);
		}
	}

	@Override
	public void update(String dbName, Object _id, BasicDBObject object) {
		DBCollection collection = Connector.getDataBase().getCollection(dbName);
		BasicDBObject searchQuery = new BasicDBObject("_id", _id);
     	collection.update(searchQuery, new BasicDBObject().append("$set", object));
	}

	@Override
	public void update(String dbName, Object _id, List<BasicDBObject> objects) {
		DBCollection collection = Connector.getDataBase().getCollection(dbName);
		BasicDBObject searchQuery = new BasicDBObject("_id", _id);
		for(BasicDBObject object : objects){
			collection.update(searchQuery, new BasicDBObject().append("$set", object));
		}
	}
	
	@Override
	public void insertAggregation(String dbName, Object _id, BasicDBObject object) {
		DBCollection collection = Connector.getDataBase().getCollection(dbName);
		BasicDBObject searchQuery = new BasicDBObject("_id", _id);
		collection.update(searchQuery, new BasicDBObject().append("$push", object));
	}
}