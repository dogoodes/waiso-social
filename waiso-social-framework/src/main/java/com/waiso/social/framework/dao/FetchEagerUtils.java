package com.waiso.social.framework.dao;

import java.util.Collection;
import java.util.List;

public class FetchEagerUtils {
	
	public static <T> void runFetch(T orm, IFetchEager<T>... eagers){
		if (eagers != null && orm != null && eagers.length > 0){
			for(IFetchEager<T> fetch : eagers){
				fetch.fetch(orm);
			}
		}
	}
	
	public static <T> void runFetch(List<T> orms, IFetchEager<T>... eagers){
		if (eagers != null && orms != null && eagers.length > 0){
			for(T orm : orms){
				if (orm != null){
					runFetch(orm, eagers);
				}
			}
		}
	}
	
	public static <T> void runFetch(Collection<T> orms, IFetchEager<T>... eagers){
		if (eagers != null && orms != null && eagers.length > 0){
			for(T orm : orms){
				if (orm != null){
					runFetch(orm, eagers);
				}
			}
		}
	}
}