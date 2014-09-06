package com.waiso.social.data;

import static com.waiso.social.data.Constants.DATA_BASE;
import static com.waiso.social.data.Constants.HOST;
import static com.waiso.social.data.Constants.PORT;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.waiso.social.framework.Utils;
import com.waiso.social.framework.exceptions.ConnectionException;

public class Connector {

	private static MongoClient mongo = null;
	
	@SuppressWarnings("static-access")
	public static MongoClient openConexao() {
		if (mongo == null) {
			try {
	            mongo = new MongoClient(HOST, PORT);
	        } catch (UnknownHostException e) {
	            (new Utils()).log("system.db.conexao.mongo.exception");
	            throw new ConnectionException();
	        }
		}
        return mongo;
    }
	
	@SuppressWarnings("static-access")
	public static DB getDataBase() {
		MongoClient mongo = openConexao();
		if (mongo != null) {
			return mongo.getDB(DATA_BASE);
		} else {
			(new Utils()).log("system.db.conexao.mongo.exception");
            throw new ConnectionException();
		}
	}
	
	public static void closeConexao() {
		if (mongo != null) {
			mongo.close();
		}
    }
}