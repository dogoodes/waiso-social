package com.waiso.social.twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;

public class AppTxt {
	
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