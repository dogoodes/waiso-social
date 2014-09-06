package com.waiso.social.framework;

import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.log.GerenciadorLog;

public class Utils {

	public static void logMessage(String message){
		if(GerenciadorLog.isDebug(Utils.class)){
			GerenciadorLog.log(message);
		}
	}
	
	public static void log(String key){
		if(GerenciadorLog.isDebug(Utils.class)){
			GerenciadorLog.log(GerenciadorMensagem.getMessage(key));
		}
	}
	
	public static void log(String key, Object...params){
		if(GerenciadorLog.isDebug(Utils.class)){
			GerenciadorLog.log(GerenciadorMensagem.getMessage(key, params));
		}
	}
}