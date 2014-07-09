package com.waiso.social.framework.dao;


public interface IGenericDAO<T> {

	public void inserir(T orm);
	
	public T atualizar(T orm);
	
	public void excluir(T orm);
}