package com.waiso.social.twitter;

import javax.servlet.http.HttpSession;

import com.waiso.social.framework.view.IClearCacheManageable;

public class CacheadorSessao implements IClearCacheManageable {

	public static String CACHE_NOME_FUNCIONALIDADE = "CACHE_NOME_FUNCIONALIDADE";
	
	public void clearCache(HttpSession session) {
		session.removeAttribute(CACHE_NOME_FUNCIONALIDADE);
	}
}