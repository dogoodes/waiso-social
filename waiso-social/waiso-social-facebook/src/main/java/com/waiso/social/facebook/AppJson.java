package com.waiso.social.facebook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;

public class AppJson {

	public JSONObject getFileJson(String file) {
		try {
			JSONParser parser = new JSONParser();
			String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
			return (JSONObject) parser.parse(new FileReader(environment + "/waiso-social-facebook/src/main/resources/META-INF/facebook-json/" + file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}