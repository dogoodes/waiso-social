package com.waiso.social.twitter;

import com.waiso.social.framework.Process;;

public class App {

	/*
	 * Espera 1o segundos entre a execusao de uma thread e outra...
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AppTwitter.getTwitter();//Estanciando chave...
		
		new Thread().sleep(10 * 1000);//Esperar 10 segundos.
		System.out.println("Estanciando OAuthAuthorization");
		
		//Pegar um tweet a cada intervalo de tempo determinado pelo cliente,
		//e adiciona na fila de envio.
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		new Thread().sleep(10 * 1000);//Esperar 10 segundos.
		System.out.println("Thread 1");
		
		//Pegar o ultimo tweet enviado por um usuario com conteudo principal, dentro
		//de um intervalo de tempo determinado pelo cliente, e adiciona na fila de envio.
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		
		new Thread().sleep(10 * 1000);//Esperar 10 segundos.
		System.out.println("Thread 2");
		
		//Enviar um tweet na fila cada intervalo de tempo determinado pelo cliente.
		Tweet tweet = new Tweet(Process.in10Minutes.getTime());
		tweet.start();
		
		new Thread().sleep(10 * 1000);//Esperar 10 segundos.
		System.out.println("Thread 3");
		
		//Pegar todo os usuarios que me segue e que eu sigo e compara.
		//Segue a pessoa que me segue, mas eu nao sigo ela... Adiciona na fila para acao.
		//Para de seguir a pessoa que eu sigo, mas ela nao me segue... Adiciona na fila para acao.
		//Dentro de um intervalo de tempo determinado pelo cliente.
		GetUser getUser = new GetUser(Process.in3hours.getTime(), args);
		getUser.start();
		
		new Thread().sleep(10 * 1000);//Esperar 10 segundos.
		System.out.println("Thread 4");
		
		//Pega a fila de usuario para seguir ou deixar de seguir e executa a acao, dentro
		//de um intervalo de tempo determinado pelo cliente.
		User user = new User(Process.in20Seconds.getTime());
		user.start();
		
		System.out.println("Thread 5");
	}
}