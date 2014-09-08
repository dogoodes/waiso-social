package com.waiso.social.framework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.FileException;

public class FileUtils {

	public InputStreamReader getFileTxt(String path, String file) throws FileException {
		try {
			String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
			FileInputStream stream = new FileInputStream(environment + path + file);
			return new InputStreamReader(stream);
		} catch (IOException e) {
			Utils.log("system.file.exception", path, file);
			throw new FileException(FileUtils.class, e);
		}
	}
	
	public List<String> getInputStreamData(InputStreamReader reader) throws IOException {
		List<String> data = new ArrayList<String>();
		BufferedReader br = new BufferedReader(reader);
		String tweet = br.readLine();
		while(tweet != null) {
			data.add(tweet);
			tweet = br.readLine();
		}
		return data;
	}
	
	public List<String> getFileData(String path, String file) throws IOException {
		List<String> data = new ArrayList<String>();
		try {
			InputStreamReader reader = (new FileUtils()).getFileTxt(path, file);
			data = (new FileUtils()).getInputStreamData(reader);
		} catch (FileException e) {}
		return data;
	}
	
	public JSONObject getFileJson(String path, String file) throws FileException {
		try {
			JSONParser parser = new JSONParser();
			String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
			return (JSONObject) parser.parse(new FileReader(environment + path + file));
		} catch (FileNotFoundException e) {
			Utils.log("system.file.exception", (path + file));
			throw new FileException(FileUtils.class, e);
		} catch (IOException e) {
			Utils.log("system.file.exception", (path + file));
			throw new FileException(FileUtils.class, e);
		} catch (ParseException e) {
			Utils.log("system.file.exception", (path + file));
			throw new FileException(FileUtils.class, e);
		}
	}
}