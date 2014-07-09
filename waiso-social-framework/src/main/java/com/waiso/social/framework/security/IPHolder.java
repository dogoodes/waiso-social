package com.waiso.social.framework.security;

import com.waiso.social.framework.exceptions.CriticalUserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;

public class IPHolder {

	static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<String>();

	public static void set(String ip) {
		threadLocal.set(ip);
	}

	public static String get() {
		return threadLocal.get();
	}
	
	public static String checkAndGet() {
		String ip = threadLocal.get();
		if(ip == null){
			String messageSystem = GerenciadorMensagem.getMessage("system.user.ip.undefinder");
			throw new CriticalUserException(IPHolder.class, messageSystem);//Erro critico!
		}else{
			return get();
		}
	}

	public static void clear() {
		threadLocal.remove();
	}
}