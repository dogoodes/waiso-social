package com.waiso.social.twitter;

import com.waiso.social.framework.Process;;

public class App {

	public static void main(String[] args) {
		//Pegar um tweet a cada intervalo de tempo determinado pelo cliente,
		//e adiciona na fila de envio.
		GetTweet getTweet = new GetTweet(Process.in30Minutes.getTime());
		getTweet.start();
		
		//Pegar o ultimo tweet enviado por um usuario com conteudo principal, dentro
		//de um intervalo de tempo determinado pelo cliente, e adiciona na fila de envio.
		Retweet retweet = new Retweet(Process.in40Minutes.getTime());
		retweet.start();
		
		//Enviar um tweet na fila cada intervalo de tempo determinado pelo cliente.
		Tweet tweet = new Tweet(Process.in8Minutes.getTime());
		tweet.start();
		
		//Pegar todo os usuarios que me segue e que eu sigo e compara.
		//Segue a pessoa que me segue, mas eu nao sigo ela... Adiciona na fila para acao.
		//Para de seguir a pessoa que eu sigo, mas ela nao me segue... Adiciona na fila para acao.
		//Dentro de um intervalo de tempo determinado pelo cliente.
		GetUser getUser = new GetUser(Process.in3hours.getTime(), args);
		getUser.start();
		
		//Pega a fila de usuario para seguir ou deixar de seguir e executa a acao, dentro
		//de um intervalo de tempo determinado pelo cliente.
		User user = new User(Process.in20Seconds.getTime());
		user.start();
	}
}