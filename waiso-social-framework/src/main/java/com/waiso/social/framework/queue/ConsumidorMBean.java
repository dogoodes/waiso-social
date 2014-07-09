package com.waiso.social.framework.queue;

public interface ConsumidorMBean {
	public boolean estaTerminado();

	public String getStatus();
	
	public int getBlocosRecebido();

	public double tempoMedioEnvio();

	public double getTempoEmEsperaUltimoRecebido();

	public double getTempoTotalEmEsperaAcumulado();

}
