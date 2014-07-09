package com.waiso.social.framework.utilitario;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

	public static boolean isBlank(Collection coll){
		return (coll == null || coll.isEmpty());
	}
	
	public static boolean isBlank(Map map){
		return (map == null || map.isEmpty());
	}
	
}
