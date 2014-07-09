package com.waiso.social.framework.queue;

import com.waiso.social.framework.concurrent.ITarefa;
import com.waiso.social.framework.exceptions.TarefaException;
import com.waiso.social.framework.log.GerenciadorLog;

public abstract class Consumidor implements ITarefa {

	enum tipoStatus {
		NAO_INICIADO, AGUARDANDO_DADO, TERMINADO, PROCESSANDO
	};

	public volatile double tempoEmEsperaUltimoRecebido = 0;
	public volatile double tempoTotalEmEsperaAcumulado = 0;
	public volatile int blocosRecebido = 0;
	public volatile tipoStatus status = tipoStatus.NAO_INICIADO;

	public void execute() throws TarefaException {
		while (true) {
			try {
				status = tipoStatus.AGUARDANDO_DADO;
				long tempoIni = System.currentTimeMillis();
				Object o = GerenciadorFilas.getInstance()
						.getResultadoProdutor();
				long tempoFim = System.currentTimeMillis();
				tempoEmEsperaUltimoRecebido = (tempoFim - tempoIni) / 1000d; // segundos
				tempoTotalEmEsperaAcumulado += tempoEmEsperaUltimoRecebido;
				if (o instanceof PoisonPill) {
					status = tipoStatus.TERMINADO;
					break;
				} else {
					blocosRecebido++;
					status = tipoStatus.PROCESSANDO;
					trace();
					consumir(o);
				}
			} catch (InterruptedException e) {
				GerenciadorLog.debug(Consumidor.class,
						"Feito cancelamento na fila.");
			}
		}
	}

	private void trace() {
		GerenciadorLog
				.trace(Consumidor.class, "Status:[" + status.name() + "]");
		GerenciadorLog.trace(Consumidor.class,
				"Tempo Em Espera Ultimo Recebido (s):["
						+ tempoEmEsperaUltimoRecebido + "]");
		GerenciadorLog.trace(Consumidor.class,
				"Tempo total Em Espera Acumulado (s):["
						+ tempoTotalEmEsperaAcumulado + "]");
		GerenciadorLog.trace(Consumidor.class, "Blocos recebidos:["
				+ blocosRecebido + "]");
	}

	public abstract void consumir(Object o);

	public boolean estaTerminado() {
		return status == tipoStatus.TERMINADO;
	}

	public String getStatus() {
		return status.name();
	}

	public double tempoMedioEnvio() {
		return tempoTotalEmEsperaAcumulado / blocosRecebido;
	}

	public int getBlocosRecebido() {
		return blocosRecebido;
	}

	public double getTempoEmEsperaUltimoRecebido() {
		return tempoEmEsperaUltimoRecebido;
	}

	public double getTempoTotalEmEsperaAcumulado() {
		return tempoTotalEmEsperaAcumulado;
	}

}
