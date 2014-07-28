package com.waiso.social.twitter;

import com.waiso.social.framework.Process;;

public class App {

	/*
	 * Espera 1o segundos entre a execusao de uma thread e outra...
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppTwitter.getTwitter();//Estanciando chave...
		
		System.out.println("Estanciando OAuthAuthorization");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		//Pegar um tweet a cada intervalo de tempo determinado pelo cliente,
		//e adiciona na fila de envio.
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		System.out.println("Thread 1");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		//Pegar o ultimo tweet enviado por um usuario com conteudo principal, dentro
		//de um intervalo de tempo determinado pelo cliente, e adiciona na fila de envio.
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		
		System.out.println("Thread 2");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		//Enviar um tweet na fila cada intervalo de tempo determinado pelo cliente.
		Tweet tweet = new Tweet(Process.in10Minutes.getTime());
		tweet.start();
		
		System.out.println("Thread 3");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		//Pegar todo os usuarios que me segue e que eu sigo e compara.
		//Segue a pessoa que me segue, mas eu nao sigo ela... Adiciona na fila para acao.
		//Para de seguir a pessoa que eu sigo, mas ela nao me segue... Adiciona na fila para acao.
		//Dentro de um intervalo de tempo determinado pelo cliente.
		GetUser getUser = new GetUser(Process.in3hours.getTime(), args);
		getUser.start();
		
		System.out.println("Thread 4");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		//Pega a fila de usuario para seguir ou deixar de seguir e executa a acao, dentro
		//de um intervalo de tempo determinado pelo cliente.
		User user = new User(Process.in20Seconds.getTime());
		user.start();
		
		System.out.println("Thread 5");
		new Thread().sleep(Process.in10Seconds.getTime());//Esperar 10 segundos.
		
		(new App()).sendInfo();
	}

	@SuppressWarnings("static-access")
	private void sendInfo()  throws InterruptedException {
		while(true){
			(new Tweet()).tweet("Me adicionem no Facebook? https://www.facebook.com/waisoti - Em breve teremos novidades!!! =)");
			new Thread().sleep(Process.in3hours.getTime());
		}
	}
}