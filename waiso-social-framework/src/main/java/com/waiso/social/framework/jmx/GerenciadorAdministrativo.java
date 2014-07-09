package com.waiso.social.framework.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public final class GerenciadorAdministrativo {

	private static GerenciadorAdministrativo instance = new GerenciadorAdministrativo();
	
	private GerenciadorAdministrativo(){}
	
	public static GerenciadorAdministrativo getInstance(){
		return instance;
	}
	
	public void administrar(Object object, String name) {
		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		try{
			mbeanServer.registerMBean(object, new ObjectName("wave:" + name));
		}catch(InstanceAlreadyExistsException e){
			//Ok nao faz nada
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}