package com.waiso.social.framework.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.json.JSONFileAttachment;
import com.waiso.social.framework.log.GerenciadorLog;

public class GerenciadorEmail implements Runnable{

	private final String recipients[];
	private final String subject;
	private final String message;
	private final String from;
	private final String html;
	private final JSONFileAttachment attach;
	
	public static GerenciadorEmail.Builder builderInstance(){
		return new GerenciadorEmail.Builder();
	}
	
	private GerenciadorEmail(Builder builder){
		this.attach = builder.attach;
		this.from = builder.from;
		this.message = builder.message;
		this.recipients = builder.recipients;
		this.subject = builder.subject;
		this.html = builder.html;
	}
	
	public static class Builder{
		private String recipients[];
		private String subject;
		private String message;
		private String from;
		private String html;
		private JSONFileAttachment attach;
		
		public Builder recipients(String[] recipients){
			this.recipients = recipients;
			return this;
		}
		
		public Builder subject(String subject){
			this.subject = subject;
			return this;
		}
		
		public Builder html(String html){
			this.html = html;
			return this;
		}
		
		public Builder message(String message){
			this.message = message;
			return this;
		}
		
		public Builder from(String from){
			this.from = from;
			return this;
		}
		
		public Builder attach(JSONFileAttachment attach){
			this.attach = attach;
			return this;
		}

		public GerenciadorEmail build(){
			return new GerenciadorEmail(this);
		}
	}
	
	public void run() throws UserException {
		try{
			if (this.message != null){
				enviarEmail();
			}else{
				enviarHTMLEmail();
			}
		}catch(Exception e){
			 throw new UserException(e);
		}
	}
	
	public void enviarEmail() throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		//TODO: Adicionar host e smtp do dominio da empresa....
		Properties props = new Properties();
		props.put("mail.smtp.host", GerenciadorConfiguracao.getConfiguracao("mail.smtp.host"));
		props.put("mail.smtp.auth", GerenciadorConfiguracao.getConfiguracao("mail.smtp.auth"));

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);
		
		// set the from and to address
		MimeBodyPart corpoEmail = new MimeBodyPart();
		corpoEmail.setContent(message, "text/plain");
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(corpoEmail);
		
		if (attach != null){
			mp.addBodyPart(getMimeBodyAttachment(attach));
		}
		
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			try{
				addressTo[i] = new InternetAddress(recipients[i]);
			}catch(AddressException e){
				GerenciadorLog.debug(GerenciadorEmail.class, e, recipients[i]); 
			}
		}
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		msg.setContent(mp);

		Transport.send(msg);
	}
	
	public void enviarHTMLEmail() throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", GerenciadorConfiguracao.getConfiguracao("mail.smtp.host"));
		props.put("mail.smtp.auth", GerenciadorConfiguracao.getConfiguracao("mail.smtp.auth"));
		props.put("mail.transport.protocol", "smtp");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);
		
		// set the from and to address
		MimeBodyPart corpoEmail = new MimeBodyPart();
		corpoEmail.setContent(html, "text/html");
		
		Multipart mp = new MimeMultipart("related");
		mp.addBodyPart(corpoEmail);
		
		if (attach != null){
			mp.addBodyPart(getMimeBodyAttachment(attach));
		}
		
		/*MimeBodyPart imageCliente = new MimeBodyPart();
	    DataSource fds = new FileDataSource
	          (GerenciadorEmail.class.getResource("/email/logo_cliente.jpg").getFile());
	    imageCliente.setDataHandler(new DataHandler(fds));
	    imageCliente.setHeader("Content-ID","logo_cliente");
	    mp.addBodyPart(imageCliente);*/
	      
	    /*MimeBodyPart imageWave = new MimeBodyPart();
	    DataSource fdsWave = new FileDataSource
	          (GerenciadorEmail.class.getResource("/email/banner_notafiscal_wave.jpg").getFile());
	    imageWave.setDataHandler(new DataHandler(fdsWave));
	    imageWave.setHeader("Content-ID","banner_notafiscal_wave");
	    mp.addBodyPart(imageWave);*/
	       
		
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		msg.setContent(mp);

		Transport.send(msg);
	}
	
	private MimeBodyPart getMimeBodyAttachment(JSONFileAttachment attach) throws MessagingException{
		MimeBodyPart attachment = new MimeBodyPart();
		if ("text/plain".equals(attach.getContentType())){
			attachment.setDataHandler(new DataHandler(attach.getFile(), attach.getContentType()/*"text/plain"*/));
			attachment.setFileName(attach.getFileName());
		}else{
			DataSource dataSource = new ByteArrayDataSource((byte[])attach.getFile(), attach.getContentType());
			attachment.setDataHandler(new DataHandler(dataSource));
			attachment.setFileName(attach.getFileName());
			if (attach.getContentType() != null && attach.getContentType().equals(JSONFileAttachment.EMAIL_ATTACHMENT)){
				attachment.setHeader("Content-Transfer-Encoding", "base64");
			}
		}
		return attachment;
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator{
	    public PasswordAuthentication getPasswordAuthentication(){
	        String username = GerenciadorConfiguracao.getConfiguracao("mail.user");
	        String password = GerenciadorConfiguracao.getConfiguracao("mail.pass");
	        return new PasswordAuthentication(username, password);
	    }
	}
}