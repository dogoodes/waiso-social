package com.waiso.social.framework.configuracao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GerenciadorConfiguracao {
	
	private static Properties p = new Properties();
	static{
		try{
			String file = System.getProperty("configuracao");
			if (file == null){
				p.load(GerenciadorConfiguracao.class.getResourceAsStream("/configuracao.properties"));
			}else{
				p.load(new FileInputStream(file));
			}
		}catch(IOException e){
			throw new RuntimeException("ARQUIVO DE CONFIGURACAO N\u00c3O ENCONTRADO.");
		}
	}
	
	public static String getConfiguracao(String key){
		synchronized (p) {
			return (String)p.get(key);
		}
	}
}