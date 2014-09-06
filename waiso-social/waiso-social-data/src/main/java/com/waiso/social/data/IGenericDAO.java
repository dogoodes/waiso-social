package com.waiso.social.data;

import java.util.List;

import com.mongodb.BasicDBObject;

public interface IGenericDAO {
	
	public void insert(String dbName, BasicDBObject object);
	public void insert(String dbName, List<BasicDBObject> objects);
	public void update(String dbName, Object _id, BasicDBObject object);
	public void update(String dbName, Object _id, List<BasicDBObject> objects);
	public void insertAggregation(String dbName, Object _id, BasicDBObject object);
	public void remover(String dbName, BasicDBObject object);
}