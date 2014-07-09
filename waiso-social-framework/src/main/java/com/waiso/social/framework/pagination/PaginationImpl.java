package com.waiso.social.framework.pagination;

import java.util.HashMap;
import java.util.Map;

import com.waiso.social.framework.exceptions.UserException;

public class PaginationImpl<T> implements IPaginationFilter<T> {

    private Map<Integer, T> pagination = new HashMap<Integer, T>();
 
    private Integer lastPage = Integer.valueOf(0);
    
    private String filter;
    
    private int lastViewedPageNumber = 1;

    public void addDataInPage(int page, T value) {
    	Integer integerPage = new Integer(page);
        pagination.put(integerPage, value);
        this.lastPage = Integer.valueOf(page);
    }
    
    public void updateDataInPage(int page, T value){
    	Integer integerPage = new Integer(page);
    	pagination.put(integerPage, value);
    }

    public T getCurrentElement() throws UserException {
        return pagination.get(lastPage);
    }

    public T getNextElements(int count) throws UserException {
        int posicao = lastPage.intValue() + count;
        T value = pagination.get(Integer.valueOf(posicao));
        lastPage = Integer.valueOf(posicao);
        return value;
    }

    public T getPreviousElements(int count) throws UserException {
        int posicao = lastPage.intValue() - count;
        T value = null;
        if (posicao >= 1) {
            value = pagination.get(Integer.valueOf(posicao));
        }
        lastPage = Integer.valueOf(posicao);
        return value;
    }

    public int getSize() throws UserException {
        return pagination.size();
    }

    public void resetIndex() throws UserException {
        this.lastPage = Integer.valueOf(1);
    }

    public Integer getLastPage() {
        return lastPage;
    }
    
    public T getPage(int page) throws UserException{
    	Integer ipage = Integer.valueOf(page);
    	this.lastViewedPageNumber = ipage;
    	return pagination.get(ipage);
    }
    
    public T getPageByDirecao(int direcao) throws UserException{
    	T valor = null;
    	switch(direcao){
    	case PROXIMO: 
    		valor = getNextElements(1); break;
    	case ANTERIOR:
    		valor = getPreviousElements(1); break;
    	case AVANCA:
    		valor = getNextElements(10); break;
    	case RETROCEDE:
    		valor = getPreviousElements(10); break;
    	default:
    		valor = getCurrentElement();
    	}
    	return valor;
    }
    
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getLastViewedPageNumber() {
		return lastViewedPageNumber;
	}
	
	
}
