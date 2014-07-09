package com.waiso.social.viewria.servlets;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.security.IPHolder;
import com.waiso.social.framework.security.SecurityConstants;
import com.waiso.social.framework.view.InputHolder;
import com.waiso.social.framework.view.ServletContextHolder;

public class ProcessRequest<T> extends AbstractServlet<T> {
	private static final long serialVersionUID = -8809428718882866714L;
	private ServletContext servletContext;
	
	public ProcessRequest(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	
	public T executeWebClassSpring(ServletRequest request, ServletResponse response, String webClassId, String invoke){
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Object webClass = webApplicationContext.getBean(webClassId);
		if(webClass == null){
			String message = GerenciadorMensagem.getMessage("view.webclassid.invalid", new Object[] { webClassId });
			throw new UserException(message);
		}
		return executeWebClass(request, response, webClass, invoke);
	}
	
	public void preExecute(ServletRequest request, ServletResponse response){
		InputHolder.set(new ArrayList<UserException>());
		ServletContextHolder.set(servletContext);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		String ip = (String) session.getAttribute(SecurityConstants.USER_KEY);
		if(ip != null){
			IPHolder.set(ip);
		}
	}
	
	public void posExecute(ServletRequest request, ServletResponse response) {
		InputHolder.clear();
		ServletContextHolder.clear();
		IPHolder.clear();
	}
}