package com.waiso.social.framework.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.email.GerenciadorEmail;

public class GerenciadorLog {
	
	public static void critical(Class clazz, String message){
		criticalEmail(message);
		error(clazz, message);
	}
	
	public static void critical(Class clazz, Throwable error, String message){
		String completeMessage = completeMessage(error, message);
		criticalEmail(completeMessage);
		error(clazz, completeMessage);
	}

	public static void debug(Class clazz, Throwable error, String message){
		System.out.println("DEBUG: " + message);
		error(clazz, error);
	}
	
	public static void debug(Class clazz, String debug) {
		System.out.println("DEBUG: " + debug);
	}

	public static void error(Class clazz, String error) {
		System.out.println("ERRORR: " + error);
	}
	
	public static void error(Class clazz, Throwable error, String message){
		System.out.println("ERRORR: " + message);
		error(clazz, error);
	}
	
	public static void error(Class clazz, Throwable error) {
		boolean applyError = clazz.getSimpleName().matches("(MyIPServlet|PeopleBO|Login)");
		if(applyError){
			error.printStackTrace();
		}
	}
	
	public static void warn(Class clazz, String warn) {
		System.out.println("WARN: " + warn);
	}

	public static void trace(Class clazz, String trace) {
		System.out.println("TRACE : " + trace);
	}
	
	public static void info(Class clazz, String info){
		System.out.println("INFO: " + info);
	}

	public static boolean isDebug(Class clazz) {
		return clazz.getSimpleName().matches("(AppTwitter|Users|Tweet|Retweet)");
	}

	public static boolean isInfo(Class clazz) {
		boolean isEnabled = true ;
		return isEnabled;
	}
	
	private static String completeMessage(Throwable error, String message){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		error.printStackTrace(printStream);
		String stackTrace = baos.toString();
		StringBuilder b = new StringBuilder();
		b.append(message);
		b.append("\n");
		b.append(stackTrace);
		return b.toString();	
	}
	
	private static void criticalEmail(String message){
		try{
			String emailSuporteTecnico = GerenciadorConfiguracao.getConfiguracao("email.suporte.tecnico");
			GerenciadorEmail gerenciadorEmail = GerenciadorEmail.builderInstance().recipients(new String[] { emailSuporteTecnico })
			//.subject("Erro critico - Empresa: "+NomeEmpresaHolder.get() + " Usuario:" + NomeUsuarioHolder.get())
			.message(message).from(GerenciadorConfiguracao.getConfiguracao("mail.user")).build();
			
			/*GerenciadorEmail gerenciadorEmail = new GerenciadorEmail(
					new String[] { emailSuporteTecnico }, "Erro critico - Empresa: "+NomeEmpresaHolder.get() + " Usuario:" + NomeUsuarioHolder.get(), message,
					GerenciadorConfiguracao.getConfiguracao("mail.user"));*/
			Thread t = new Thread(gerenciadorEmail);
			
			//TODO: Evitar enviar email por enquando. Ate colocar em producao...
			//t.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}