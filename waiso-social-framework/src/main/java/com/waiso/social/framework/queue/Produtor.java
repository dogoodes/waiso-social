package com.waiso.social.framework.queue;

/**
 * Esta classe e responsavel por incluir na fila os dados que devem ser
 * consumido. Esta classe possui informacoes em tempo real dos dados que ele
 * esta inserindo na fila e do tempo de inclusao de cada dado na fila, estas
 * informacoes podem ser vistas atraves do JMX.
 * 
 * @author rodolfo.dias
 * 
 */
public abstract class Produtor extends Thread implements ProdutorMBean {

	protected enum tipoStatus {
		NAO_INICIADO, INICIADO, TERMINADO, CANCELADO, ESPERANDO_ALGUEM_CONSUMIR, GERANDO_DADOS
	};

	public volatile int nrBlocos = 0;
	public volatile int blocosEnviado = 0;
	public volatile double tempoEmEsperaUltimoEnvio = 0d;
	public volatile double tempoTotalEmEsperaAcumulado = 0d;
	public volatile double tempoMedioEnvio = 0d;
	public volatile tipoStatus status = tipoStatus.NAO_INICIADO;

	protected void enviarParaFila(Object dados) throws InterruptedException {
		long tempoIni = System.currentTimeMillis();
		try {
			status = tipoStatus.ESPERANDO_ALGUEM_CONSUMIR;
			GerenciadorFilas.getInstance().addProdutor(dados);
			blocosEnviado++;
			long tempoFim = System.currentTimeMillis();
			tempoEmEsperaUltimoEnvio = ((tempoFim - tempoIni) / 1000d); // segundos
			tempoTotalEmEsperaAcumulado += tempoEmEsperaUltimoEnvio;
			tempoMedioEnvio = tempoTotalEmEsperaAcumulado
					/ (double) blocosEnviado;
		} catch (InterruptedException e) {
			// Ok nao preciso fazer nada pois o que aconteceu foi que alguem
			// acionou o cancel atraves do JMX!
			long tempoFim = System.currentTimeMillis();
			tempoEmEsperaUltimoEnvio = (tempoFim - tempoIni) / 1000d; // segundos
			tempoTotalEmEsperaAcumulado += tempoEmEsperaUltimoEnvio;
			tempoMedioEnvio = tempoTotalEmEsperaAcumulado
					/ (double) blocosEnviado;
			status = tipoStatus.CANCELADO;
			throw e;
		}
	}

	protected abstract void trace();

	public void setStatus(tipoStatus status) {
		this.status = status;
	}

	public void cancel() {
		interrupt();
	}

	public int getNrBlocos() {
		return nrBlocos;
	}

	public int getBlocosEnviado() {
		return blocosEnviado;
	}

	public boolean estaTerminado() {
		return status == tipoStatus.TERMINADO;
	}

	public String getStatus() {
		return status.name();
	}

	public double tempoMedioEnvio() {
		return tempoMedioEnvio;
	}

	public double getTempoEmEsperaUltimoEnvio() {
		return tempoEmEsperaUltimoEnvio;
	}

	public double getTempoTotalEmEsperaAcumulado() {
		return tempoTotalEmEsperaAcumulado;
	}

}
