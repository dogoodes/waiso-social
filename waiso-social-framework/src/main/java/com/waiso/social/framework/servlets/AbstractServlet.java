package com.waiso.social.framework.servlets;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.waiso.social.framework.view.Servlet;

public abstract class AbstractServlet<T> extends Servlet<T>{

	private static final long serialVersionUID = 1L;
	
	protected abstract T executeWebClassSpring(ServletRequest request, ServletResponse response, String webClassId, String invoke);
	protected abstract void preExecute(ServletRequest request, ServletResponse response);
	protected abstract void posExecute(ServletRequest request, ServletResponse response);
}