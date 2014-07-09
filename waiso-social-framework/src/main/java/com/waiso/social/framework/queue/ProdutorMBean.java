package com.waiso.social.framework.queue;

public interface ProdutorMBean {
	public void cancel();

	public boolean estaTerminado();

	public String getStatus();

	public double tempoMedioEnvio();

	public int getNrBlocos();

	public int getBlocosEnviado();

	public double getTempoEmEsperaUltimoEnvio();

	public double getTempoTotalEmEsperaAcumulado();

}
