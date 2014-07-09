package com.waiso.social.framework.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;

public abstract class GenericDAO<T> implements IGenericDAO<T> {
	
	@SuppressWarnings("unused")
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T atualizar(T orm) {
		return (T)getEntityManager().merge(orm);
	}

	public void excluir(T orm) {
		getEntityManager().remove(orm);	
	}

	public void inserir(T orm) {
		getEntityManager().persist(orm);
	}

	protected abstract EntityManager getEntityManager();
}