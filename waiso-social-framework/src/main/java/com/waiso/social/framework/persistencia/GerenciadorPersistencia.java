package com.waiso.social.framework.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GerenciadorPersistencia {
	private volatile static EntityManagerFactory emf = null;

	public static void conectar() {
		emf = Persistence.createEntityManagerFactory("webevolution");
	}
	
	public static void conectar(String persistenceUnitName) {
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	public static boolean isConectado(){
		return (emf != null);
	}

	private static class ThreadLocalEntityManager extends ThreadLocal {
		@Override
		protected Object initialValue() {
			return emf.createEntityManager();
		}
	}

	private static ThreadLocalEntityManager currentEM = new ThreadLocalEntityManager();

	public static EntityManager getEntityManager() {
		return (EntityManager) currentEM.get();
	}

	public static void close() {
		EntityManager em = (EntityManager) currentEM.get();
		if (em != null) {
			em.close();
			currentEM.remove();
		}
	}
}
