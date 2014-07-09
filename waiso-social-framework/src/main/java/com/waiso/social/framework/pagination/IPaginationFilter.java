package com.waiso.social.framework.pagination;

public interface IPaginationFilter<T> extends IPagination<T> {

	public void setFilter(String filter);
	
	public String getFilter();
	
}
