package com.waiso.social.viewria.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.exceptions.UserLinkException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.framework.log.GerenciadorLog;
import com.waiso.social.framework.view.ClearCacheManager;
import com.waiso.social.framework.view.InputHolder;
import com.waiso.social.framework.view.ScreenCollabUtil;

public class WaisoSocialServlet extends AbstractServlet<JSONReturn> {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String webClassId = request.getParameter("webClassId");
		String invoke = request.getParameter("invoke");
		String sendScreen = request.getParameter("sendScreen");
		if(ScreenCollabUtil.isSendScreen(sendScreen)){
			invoke = "sendScreen";
		}
		
		PrintWriter out = null;
		if(webClassId == null){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.ERROR);
			out = response.getWriter();
			out.print(jsonReturn.serialize());
			out.flush();
		}else if(webClassId.equals("ping")){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.SUCCESS);
			out = response.getWriter();
			out.print(jsonReturn.serialize());
			out.flush();
		}else{
			try{
				preExecute(request, response);
				JSONReturn jsonReturn = executeWebClassSpring(request, response, webClassId, invoke);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				out = getWriter(out, response);
				out.print(jsonReturn.serialize());
			}catch(UserException e){
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				JSONReturn jsonReturn;
				out = getWriter(out, response);
				if(e instanceof UserLinkException){
					jsonReturn = JSONReturn.newInstance(Consequence.MANY_ERRORS, InputHolder.get());
				}else{
					jsonReturn = JSONReturn.newInstance(Consequence.ERROR).message(e.getMessage());
				}
				out.print(jsonReturn.serialize());
			}catch(Exception e){
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				String message = GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL);
				GerenciadorLog.critical(WaisoSocialServlet.class, e, message);
				out = getWriter(out, response);
				out.print(JSONReturn.newInstance(Consequence.ERROR).message(message).serialize());
			}finally{
				posExecute(request, response);
			}
		}
		if(out != null){
			out.flush();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		if(GerenciadorLog.isDebug(WaisoSocialServlet.class)){
			String message = GerenciadorMensagem.getMessage("initial.application.server", "WaisoSocialServlet");
			GerenciadorLog.debug(GerenciadorLog.class, message);
		}
		super.init(config);
		ResourceBundle bundleMessagesFramework = ResourceBundle.getBundle("MessagesFramework", new Locale(GerenciadorMensagem.language));
		GerenciadorMensagem.addResourceBundle(bundleMessagesFramework);
		ResourceBundle bundleMessages = ResourceBundle.getBundle("Messages", new Locale(GerenciadorMensagem.language));
		GerenciadorMensagem.addResourceBundle(bundleMessages);
		ResourceBundle bundleMessagesErrors = ResourceBundle.getBundle("MessagesErrors", new Locale(GerenciadorMensagem.language));
		GerenciadorMensagem.addResourceBundle(bundleMessagesErrors);
		ResourceBundle bundleMessagesErrorsBusiness = ResourceBundle.getBundle("MessagesErrorsBusiness", new Locale(GerenciadorMensagem.language));
		GerenciadorMensagem.addResourceBundle(bundleMessagesErrorsBusiness);
		
		ClearCacheManager.addCache(com.waiso.social.twitter.CacheadorSessao.class);
	}
}