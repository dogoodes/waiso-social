package com.waiso.social.facebook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import facebook4j.FacebookException;

public class JSONRead {

	
	/**
	 * 
	 * How can I send private message to my friends in facebook4j?
	 * You cannot send private messages via Graph API.
	 * https://groups.google.com/forum/#!topic/facebook4j/fkFfX1Vq2-8
	 * @throws InterruptedException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FacebookException, InterruptedException {
		try {
			JSONParser parser = new JSONParser(); 
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader( "D:/work/teste/src/main/resources/groups-posts.json"));
			if (jsonObject != null) {
				Set<String> jsonKeys = jsonObject.keySet();
				for (String jsonKey : jsonKeys) {
					System.out.println("Tipo de Grupo : " + jsonKey);
					JSONObject groupsType = (JSONObject) jsonObject.get(jsonKey);
					JSONObject groups = (JSONObject) groupsType.get("groups");
					if (groups != null) {
						Set<String> groupKeys = groups.keySet();
						for (String groupKey : groupKeys) {
							System.out.println("Nome do Grupo : " + groupKey);
							JSONObject group = (JSONObject) groups.get(groupKey);
							String groupId = (String) group.get("groupId");
							JSONObject posts = (JSONObject) group.get("posts");
							if (posts != null) {
								Set<String> postKeys = posts.keySet();
								for (String postKey : postKeys) {
									JSONObject post = (JSONObject) posts.get(postKey);
									String message = (String) post.get("message");
									String link = (String) post.get("link");
									String linkPicture = (String) post.get("linkPicture");
									System.out.println("groupId: " + groupId + " postKey: " + postKey + " message: " + message + " link: " + link + " linkPicture: " + linkPicture);
								}
							}
						}
					}
					System.out.println();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}