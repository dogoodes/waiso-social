package com.waiso.social.framework.view;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONConstant;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.framework.log.GerenciadorLog;
import com.waiso.social.framework.utilitario.StringUtils;

public class MonitorStatus implements IMonitorStatus {
	
	public JSONReturn generateTicket(ServletRequest request, ServletResponse response){
		Random r = new Random();
		final Long ticket = r.nextLong();
		JSONConstant jsonConstant = new JSONConstant();
		jsonConstant.setValor(ticket.toString());
		return JSONReturn.newInstance(Consequence.SUCCESS, jsonConstant);
	}
	
	public JSONReturn cleanTicket(ServletRequest request, ServletResponse response){
		String ticket = request.getParameter("ticket");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		session.removeAttribute(ticket);
		return JSONReturn.newInstance(Consequence.SUCCESS);
	}
	
	public JSONReturn updateStatusSuccess(ServletRequest request, ServletResponse response){
		String ticket = request.getParameter("ticket");
		String dado = request.getParameter("dado");
		String message = request.getParameter("message");
		if (StringUtils.isBlank(message)){
			message = (String)request.getAttribute("message");
		}
		if (dado == null){
			dado = "";
		}else{
			try{
				dado = URLDecoder.decode(dado, "UTF-8");
			}catch(UnsupportedEncodingException e){
				//ok deixa como esta
			}
		}
		JSONConstant jsonDado = new JSONConstant(dado);
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.SUCCESS, jsonDado);
		if (!StringUtils.isBlank(message)){
			updateStatus(request, ticket, jsonReturn.message(message) );
		}else{
			updateStatus(request, ticket,jsonReturn);
		}
		return jsonReturn;
	}
	
	public JSONReturn updateStatusError(ServletRequest request, ServletResponse response){
		String ticket = request.getParameter("ticket");
		String dado = request.getParameter("dado");
		String message = request.getParameter("message");
		if (StringUtils.isBlank(message)){
			message = (String)request.getAttribute("message");
		}
		JSONConstant jsonDado = new JSONConstant(dado);
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.ERROR, jsonDado);
		if (!StringUtils.isBlank(message)){
			updateStatus(request, ticket, jsonReturn.message(message) );
		}else{
			updateStatus(request, ticket,jsonReturn);
		}
		return jsonReturn;
	}
	
	public JSONReturn updateStatusRetry(ServletRequest request, ServletResponse response){
		String ticket = request.getParameter("ticket");
		String dado = request.getParameter("dado");
		String message = request.getParameter("message");
		if (StringUtils.isBlank(message)){
			message = (String)request.getAttribute("message");
		}
		JSONConstant jsonDado = new JSONConstant(dado);
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.RETRY, jsonDado);
		if (!StringUtils.isBlank(message)){
			updateStatus(request, ticket, jsonReturn.message(message) );
		}else{
			updateStatus(request, ticket,jsonReturn);
		}
		return jsonReturn;
	}
	
	public JSONReturn updateStatusStopRetry(ServletRequest request, ServletResponse response){
		String ticket = request.getParameter("ticket");
		String dado = request.getParameter("dado");
		String message = request.getParameter("message");
		if (StringUtils.isBlank(message)){
			message = (String)request.getAttribute("message");
		}
		JSONConstant jsonDado = new JSONConstant(dado);
		JSONReturn jsonReturn = JSONReturn.newInstance(Consequence.STOP_RETRY, jsonDado);
		if (!StringUtils.isBlank(message)){
			updateStatus(request, ticket, jsonReturn.message(message) );
		}else{
			updateStatus(request, ticket,jsonReturn);
		}
		return JSONReturn.newInstance(Consequence.SUCCESS, dado).message(message);
	}
	
	public void updateStatus(ServletRequest request,String ticket, JSONReturn jsonReturn){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		session.setAttribute(ticket, jsonReturn);
		if (GerenciadorLog.isDebug(MonitorStatus.class)){
			GerenciadorLog.debug(MonitorStatus.class, "Update Status:"+ jsonReturn.getConsequence().name()+ " do ticket: " + ticket);
		}
	}
	
	public JSONReturn stopRetry(ServletRequest request, ServletResponse response, String ticket){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		session.setAttribute(ticket, JSONReturn.newInstance(Consequence.STOP_RETRY));
		if (GerenciadorLog.isDebug(MonitorStatus.class)){
			GerenciadorLog.debug(MonitorStatus.class, "STOP_RETRY: STOP status do ticket: " + ticket);
		}
		return JSONReturn.newInstance(Consequence.SUCCESS);
	}
	
	public JSONReturn checkStatus(ServletRequest request,
			ServletResponse response){
		String ticket = request.getParameter("ticket");
		return checkStatus(request, response, ticket);
	}
	
	public JSONReturn checkStatus(ServletRequest request,
			ServletResponse response,String ticket){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		JSONReturn jsonReturn = (JSONReturn)session.getAttribute(ticket);
		if (jsonReturn == null){
			String retry = request.getParameter("retry");
			int iRetry = 0;
			if (retry != null && !retry.equals("")){
				iRetry = Integer.valueOf(retry);
				iRetry--;
			}
			if (iRetry < 0){
				//OK poderia mandar um ERROR dizendo na mensagem que nao foi possivel determinar o status do servico.
				//Isso seria o caso por exemplo de quando o APPLET dah um problema e nao atualiza o status do que esta fazendo
			}
			JSONConstant retries = new JSONConstant();
			retries.setValor(iRetry);
			jsonReturn = JSONReturn.newInstance(Consequence.RETRY, retries);
		}
		if (GerenciadorLog.isDebug(MonitorStatus.class)){
			GerenciadorLog.debug(MonitorStatus.class, "RETRY: Checando status do ticket: " + ticket);
		}

		return jsonReturn;
		
	}
}
