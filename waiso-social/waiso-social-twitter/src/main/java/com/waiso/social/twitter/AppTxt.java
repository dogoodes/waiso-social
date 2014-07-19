package com.waiso.social.twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;

public class AppTxt {

	@SuppressWarnings("resource")
	public List<String> getTweets() throws IOException {
		List<String> tweets = new ArrayList<String>();
		String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
		FileInputStream stream = new FileInputStream(environment + "/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/tweets");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String tweet = br.readLine();
		while(tweet != null) {
			tweets.add(tweet);
			tweet = br.readLine();
		}
		return tweets;
	}
	
	@SuppressWarnings("resource")
	public List<String> getUsersRetweets() throws IOException {
		List<String> usersRetweets = new ArrayList<String>();
		String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
		FileInputStream stream = new FileInputStream(environment + "/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/users-retweets");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String user = br.readLine();
		while(user != null) {
			usersRetweets.add(user);
			user = br.readLine();
		}
		return usersRetweets;
	}
	
	@SuppressWarnings("resource")
	public List<String> getUsersMain() throws IOException {
		List<String> usersMain = new ArrayList<String>();
		String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
		FileInputStream stream = new FileInputStream(environment + "/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/users-main");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String user = br.readLine();
		while(user != null) {
			usersMain.add(user);
			user = br.readLine();
		}
		return usersMain;
	}
	
	public void writerUserUrl(String url) throws IOException {
		String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
		String file = environment + "/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/users-urls";
		String fileTemp = environment + "/waiso-social-twitter/src/main/resources/META-INF/twitter-txt/users-urls-temp";

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileTemp));
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String urls;
		while((urls = reader.readLine()) != null){
			writer.write(urls + "\n");
		}
		writer.write(url);

		writer.close();
		reader.close();

		new File(file).delete();
		new File(fileTemp).renameTo(new File(file));
	}
}