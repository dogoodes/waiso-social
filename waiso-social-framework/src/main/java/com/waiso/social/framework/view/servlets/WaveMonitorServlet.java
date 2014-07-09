package com.waiso.social.framework.view.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.framework.log.GerenciadorLog;

public abstract class WaveMonitorServlet extends WaveAbstractServlet<JSONReturn>{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String invoke = request.getParameter("invoke");
		response.setContentType("text/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.ERROR);
		PrintWriter out = response.getWriter();
		try {
			preExecute(request, response);
			jsonReturn  = executeWebClassSpring(request, response, "monitorStatus", invoke);
		} catch (UserException e) {
			GerenciadorLog.error(WaveMonitorServlet.class, e);
		}catch(Exception e){
			String message = GerenciadorMensagem.getMessage(GerenciadorMensagem.ERROR_GERAL);
			GerenciadorLog.critical(WaveMonitorServlet.class, e, message);	
		}finally{
			posExecute(request, response);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		out = response.getWriter();
		out.print(jsonReturn.serialize());
		out.flush();
	}
}