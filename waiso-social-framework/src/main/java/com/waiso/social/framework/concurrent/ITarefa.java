package com.waiso.social.framework.concurrent;

import com.waiso.social.framework.exceptions.TarefaException;


/**
 * A interface <code>Tarefa</code> deve ser implementada por qualquer classe que tenha a intencao
 * de ser executada como thread.
 *
 * @author  rodolfo.dias
 */
public interface ITarefa
{
	public void execute() throws TarefaException;
}
