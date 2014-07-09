package com.waiso.social.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.UserLinkException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.json.Consequence;
import com.waiso.social.framework.json.JSONReturn;
import com.waiso.social.framework.security.SecurityConstants;
import com.waiso.social.framework.view.ComplexValidation;
import com.waiso.social.framework.view.Input;
import com.waiso.social.framework.view.RegexValidation;

public class Login {

	public JSONReturn login(ServletRequest request, ServletResponse response){
		//Campo Login
		Input<String> login = Input.builderInstance(request, String.class).name("login").label("Login").validation(new ComplexValidation() {
			public void validate(String name, String value) throws UserLinkException {
				if(value != null){
					if(value.length() < 5 || value.length() > 15){
						String message = GerenciadorMensagem.getMessage("framework.utils.min.and.max", "Login", 5, 15);
						throw new UserLinkException(name, message);
					}
				}	
			}
		}).validation(RegexValidation.OnlyLetters).build();
		
		//Campo Senha
		Input<String> password = Input.builderInstance(request, String.class).name("password").label("Senha").validation(new ComplexValidation() {
			public void validate(String name, String value) throws UserLinkException {
				if(value != null){
					if(value.length() < 5 || value.length() > 15){
						String message = GerenciadorMensagem.getMessage("framework.utils.min.and.max", "Senha", 5, 15);
						throw new UserLinkException(name, message);
					}
				}	
			}
		}).validation(RegexValidation.OnlyLetters).build();
		
		if("admin".equals(login.getValue()) && "admin".equals(password.getValue())){
			logarUsuario(request);
			String context = GerenciadorConfiguracao.getConfiguracao("context");
			return JSONReturn.newInstance(Consequence.SUCCESS).page(context + "/" + "waisosocial/application/home.html");
		}else{
			return JSONReturn.newInstance(Consequence.ERROR).message("login.or.password.invalid");
		}
	}
	
	private void logarUsuario(ServletRequest request){
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		session.setAttribute(SecurityConstants.USER_KEY, "logado");
	}
}