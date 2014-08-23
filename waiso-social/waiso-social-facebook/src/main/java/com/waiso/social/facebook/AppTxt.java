package com.waiso.social.facebook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;

public class AppTxt {

	@SuppressWarnings("resource")
	public List<String> getUsersMain() throws IOException {
		List<String> usersMain = new ArrayList<String>();
		String environment = GerenciadorConfiguracao.getConfiguracao("development.environment");
		FileInputStream stream = new FileInputStream(environment + "/waiso-social-facebook/src/main/resources/META-INF/facebook-txt/users-main");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String user = br.readLine();
		while(user != null) {
			usersMain.add(user);
			user = br.readLine();
		}
		return usersMain;
	}
}