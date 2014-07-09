package com.waiso.social.framework.pagination;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PaginationHttpSessionStored {

	@SuppressWarnings("rawtypes")
	public static IPagination getPagination(ServletRequest request, String key){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		IPagination pagination = (IPagination)session.getAttribute(key);
		return pagination;
	}
	
	@SuppressWarnings("rawtypes")
	public static void setPagination(ServletRequest request, String key, IPagination pagination){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		session.setAttribute(key, pagination);
	}
	
	public static void clearCache(ServletRequest request, String key){
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		session.removeAttribute(key);
	}
}