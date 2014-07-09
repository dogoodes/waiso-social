package com.waiso.social.framework.concurrent;

import java.util.concurrent.ConcurrentMap;


/**
 * Para ambiente em cluster o ideal sera usar o TERRACOTA para cuidar das
 * variaves lock
 * @author rodolfodias
 *
 */
public class GerenciadorLock {
	public static ConcurrentMap<Long, Object> lockEmpresas = new java.util.concurrent.ConcurrentHashMap<Long, Object>();

}
