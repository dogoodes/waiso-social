package com.waiso.social.framework.utilitario;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionUtils {

	public static Object getValueInSession(ServletRequest request, String key){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		return session.getAttribute(key);
		
	}
	
	@SuppressWarnings("unchecked")
	public static void setValueInSession(ServletRequest request, String key, Object o){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		session.setAttribute(key, o);
	}
}
