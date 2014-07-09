package com.waiso.social.framework.concurrent;

/**
 * Uma <i>thread</i> e uma thread de execucao dentro do programa. 
 * A Java Virtual Machine permite que aplicacoes tenham multiplas threads
 * executando concorrentemente.
 * 
 * <p>Esta classe oferece as seguintes funcionalidades:<br>
 * 
 *  <li> Oferece uma forma de delegar a Thread Principal o poder de manipular excecoes.</li>
 *  <li> Oferece um mecanismo de notificacao de termino da execucao.</li>  
 * 
 */
final class TarefaHandler extends Thread {
	private GerenciadorTarefas manager;
	private ITarefa executable;
	private Sinalizador signal;

	/**
	 * Allocates a new <code>Thread</code> object.
	 * 
	 * @param manager The executable manager.
	 * @param context The user context
	 * @param executable The executable
	 * @param signal used to info that the process finished. 
	 */
	public TarefaHandler(GerenciadorTarefas manager, ITarefa executable, Sinalizador signal){
		this.manager = manager;
		this.executable = executable;
		this.signal = signal;
	}

	public void run()
	{
		try
		{
			// aguardando manager, inicializar.
			signal.startWait();

			// Processando a thread.
			executable.execute();
		}
		catch (Throwable te)
		{
			manager.notifyError(te);
		}
		finally
		{
			// informa que finalizou.
			signal.doneCountDown();
		}
	}
}
