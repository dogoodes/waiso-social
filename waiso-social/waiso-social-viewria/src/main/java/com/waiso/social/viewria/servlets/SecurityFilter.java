package com.waiso.social.viewria.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.framework.security.SecurityConstants;

public class SecurityFilter implements Filter {

	private static List<String> noSecurityActions = new ArrayList<String>();
	static {
		noSecurityActions.add(SecurityConstants.ACTION_LOGIN);
		noSecurityActions.add(SecurityConstants.ACTION_REGISTER);
	}
	private static List<String> firstAccessActions = new ArrayList<String>();
	static {
		firstAccessActions.add(SecurityConstants.ACAO_MONITOR_STATUS);
	}

	public void destroy() {}

	public void init(FilterConfig config) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String webClassId = request.getParameter("webClassId");
		if(webClassId == null){
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			String message = GerenciadorMensagem.getMessage("view.webclassid.uninformed");
			httpResponse.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
			return;
		}

		if(thisLoggingOrRegistering(webClassId)){
			chain.doFilter(request, response);
			return;
		}else{
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(true);
			String ip = (String) session.getAttribute(SecurityConstants.USER_KEY);
			if(accessingThisNoUserLogged(ip)){
				noAccess(request, response);
				return;
			}else{
				/*
				if(usuario.isPrimeiroAcesso() && naoEstaAcessandoPaginaExclusivaPrimeiraAcesso(webClassId)){
					erroAcesso(request, response);
					return;
				}
				*/
				/*
				if (!temAcessoAPagina(request, usuario)){
					acessoRestrito(httpRequest, response);
					return;
				}
				*/
			}
		}
		chain.doFilter(request, response);
	}
	
	private boolean temAcessoAPagina(ServletRequest request){
		String url = getUrl(request);
		boolean temAcesso = false;
		if ("main.html".equals(url)){
			temAcesso = true;
		}else if (url.indexOf("nfe.inc") != -1 || url.indexOf("nfe.out") != -1 || url.indexOf(".upl") != -1 || url.indexOf("monitor.erp") != -1 || url.indexOf("wave.ftp") != -1){
			temAcesso = true;
		}
		return temAcesso;
	}
	
	private String getUrl(ServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String pathBrowser = httpRequest.getHeader("referer");
		String url = pathBrowser.substring(pathBrowser.lastIndexOf("/")+1);
		return url;
	}

	private boolean naoEstaAcessandoPaginaExclusivaPrimeiraAcesso(String webClassId) {
		return !firstAccessActions.contains(webClassId);
	}

	private boolean thisLoggingOrRegistering(String webClassId) {
		return noSecurityActions.contains(webClassId);
	}

	private boolean accessingThisNoUserLogged(String ip) {
		return ip == null;
	}

	/*
	private void erroAcesso(ServletRequest request, ServletResponse response) throws IOException {
		String message = GerenciadorMensagem.getMessage("view.security.login.null");
		writeError(request, response, message, "index.html");
	}
	*/
	
	private void noAccess(ServletRequest request, ServletResponse response) throws IOException{
		String message = GerenciadorMensagem.getMessage("view.security.login.null");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String context = GerenciadorConfiguracao.getConfiguracao("context");
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.NO_ACCESS).message(message).page(context + "/" + "noAccess.html");
		out.print(jsonReturn.serialize());
	}
	
	//TODO: Ajustar...
	private void writeError(ServletRequest request, ServletResponse response, String message, String redirectPath) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String context = GerenciadorConfiguracao.getConfiguracao("contexto_erp");
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.ERROR).message(message).page(context+"/"+ redirectPath);
		out.print(jsonReturn.serialize());
	}
}