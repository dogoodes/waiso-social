package com.waiso.social.framework.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.TarefaException;

/**
 * Classe responsavel por executar simultaneamente <b><i>diversas</i></b>
 * Tarefas. </br><i> Por default, sera executado o numero de tarefas definido
 * em QT_THREADS_PROCESSO , porem o numero maximo do pool pode ser alterado.</i>
 * 
 * <p>
 * Para facilitar o entendimento de como utilizar o GerenciadorTarefas, vamos
 * fornecer um problema tipico de uma aplicacao, vamos supor que o desenvolvedor
 * necessite processar arquivos de importacao simultaneamente para otimizar a
 * performance de carga dos mesmos.
 * 
 * <p>
 * No exemplo abaixo vamos criar um GerenciadorTarefas para disparar <i>n</i>
 * Threads que forem necessarias para processar individualmente <i>n</i>
 * arquivos.
 * 
 * <p>
 * O GerenciadorTarefas pode ser configurado para <i><b>Esperar o Termino</b></i>
 * da execucao de todas as Threads disparadas ou pode ser executado de forma
 * assincrona,para isso ele fornece o construtor GerenciadorTarefas(boolean
 * esperarFinalizacao), caso seja omitido o esperarFinalizacao, o
 * GerenciadorTarefas esperara o termino da execucao de todas as Threads
 * (ITarefa adicionados), antes de finalizar a execucao.
 * 
 * <p>
 * Esta classe tambem tem suporte a coleta de erros, ou seja caso alguma Thread
 * gere um erro,a mesma podera ser coletada para que seja consultado no final do
 * processamento atraves do metodo <i><b>GerenciadorTarefas.getErrors()</b></i>.
 * 
 * <p>
 * Todas as Threads adicionadas no GerenciadorTarefas devem implementar a
 * interface {@link br.com.tecban.falcao.concorrencia.ITarefa}.
 * 
 * <p>
 * Veja abaixo como utilizar :
 * 
 * <pre>
 *  GerenciadorTarefas manager = new GerenciadorTarefas([poolSize], [esperarFinalizacao]);
 *  manager.add(new ProcessarTarefa(file0));
 *  manager.add(new ProcessarTarefa(file1));
 *  
 *  //dispara as threads por arquivo
 *  manager.execute();
 * </pre>
 * 
 * @see br.com.tecban.falcao.concorrencia.ITarefa
 * @see br.com.tecban.falcao.concorrencia.TarefaHandler
 * 
 * @author rodolfo.dias
 */
public class GerenciadorTarefas {
	private ThreadPoolExecutor executor;

	private Sinalizador sinal;

	private boolean esperarFinalizacao;

	private List<TarefaHandler> tarefas = new ArrayList<TarefaHandler>();

	private Collection<Throwable> errors = new ArrayList<Throwable>();

	private static final int DEFAULT_NUMBER_THREADS = Integer
			.parseInt(GerenciadorConfiguracao.getConfiguracao("QT_THREADS_PROCESSO"));;

	/**
	 * Este construtor usa o numero de threads definido em QT_THREADS_PROCESSO
	 * para iniciar o processamento, e aguarda que todas tenham sido finalizadas
	 * antes de voltar o controle para a Thread Principal.
	 * 
	 * @param context
	 *            The user context
	 * @param nThreads
	 *            the number of threads in the pool.
	 */
	public GerenciadorTarefas() {
		this(DEFAULT_NUMBER_THREADS, true);
	}

	/**
	 * Este construtor usa o numero de threads definido em QT_THREADS_PROCESSO
	 * para iniciar o processamento.
	 * 
	 * @param esperarFinalizacao
	 *            True or False para esperar todas as theads ativas finalizarem.
	 */
	public GerenciadorTarefas(boolean esperarFinalizacao) {
		this(DEFAULT_NUMBER_THREADS, esperarFinalizacao);
	}

	/**
	 * Constructor
	 * 
	 * @param poolSize
	 *            O numero de threads no pool.
	 * @param esperarFinalizacao
	 *            True or False para esperar todas as theads ativas finalizarem.
	 */
	public GerenciadorTarefas(int poolSize, boolean esperarFinalizacao) {
		this.executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(poolSize);
		this.esperarFinalizacao = esperarFinalizacao;
		this.sinal = new Sinalizador();
	}

	/**
	 * Inclui uma tarefa para ser executada.
	 * 
	 * @param runnable
	 *            The runnable
	 */
	public void add(ITarefa tarefa) {
		tarefas.add(new TarefaHandler(this, tarefa, sinal));
	}

	/**
	 * Execute all executables.
	 */
	public void execute() throws TarefaException {
		sinal.createSignals(tarefas.size());

		// Inicializando...
		for (TarefaHandler handler : tarefas) {

			executor.submit(handler);
		}

		// Inicializacao concluida.
		sinal.startCountDown();

		// Verifica se e necessario esperar todas as threads terminarem.
		esperarFinalizacao();

		// Necessario a chamada, pois utilizamos o
		// Executors.newFixedThreadPool().
		executor.shutdown();
	}

	/**
	 * Return all errors.
	 * 
	 * @return errors.
	 */
	public Collection<Throwable> getErrors() {
		return errors;
	}

	/**
	 * Checks that all threads performed until end.
	 * 
	 * @return True or False (all threads performed).
	 */
	public boolean isSuccessfull() {
		return tarefas.size() == executor.getCompletedTaskCount();
	}

	protected void esperarFinalizacao() {
		if (esperarFinalizacao) {
			sinal.doneWait();
		}
	}

	synchronized void notifyError(Throwable te) {
		errors.add(te);
	}
}
