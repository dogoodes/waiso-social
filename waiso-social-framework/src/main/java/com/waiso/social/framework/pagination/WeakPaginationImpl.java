package com.waiso.social.framework.pagination;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import com.waiso.social.framework.exceptions.UserException;

public class  WeakPaginationImpl<T> implements IPaginationFilter<WeakReference<T>> {

    private Map<Integer, WeakReference<T>> pagination = new WeakHashMap<Integer, WeakReference<T>>();

    private Integer lastPage = Integer.valueOf(0);
    
    private String filter;
    
    private int lastViewedPageNumber = 1;

    /**
     * Metodo responsavel por guardar as informacoes que devem ser mostrada 
     * de acordo com a pagina.
     * 
     * Nao use Integer.valueOf() para geracao das chaves pois um WeakHashMap
	 * e usado internamente, caso o Integer.valueOf seja usado o Weak nao fara o cleanup
	 * devido ao cache da chave ser mantido internamento no objeto Integer.
     * 
     */
    public void addDataInPage(int page, WeakReference<T> value) {
    	Integer integerPage = new Integer(page);
        pagination.put(integerPage, value);
        this.lastPage = Integer.valueOf(page);
    }
    
    public void updateDataInPage(int page, WeakReference<T> value){
    	Integer integerPage = new Integer(page);
    	pagination.put(integerPage, value);
    }

    public WeakReference<T> getCurrentElement() throws UserException {
        return pagination.get(lastPage);
    }

    public WeakReference<T> getNextElements(int count) throws UserException {
        int posicao = lastPage.intValue() + count;
        WeakReference<T> value = pagination.get(Integer.valueOf(posicao));
        lastPage = Integer.valueOf(posicao);
        return value;
    }

    public WeakReference<T> getPreviousElements(int count) throws UserException {
        int posicao = lastPage.intValue() - count;
        WeakReference<T> value = null;
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
    
    public WeakReference<T> getPage(int page) throws UserException{
    	Integer ipage = Integer.valueOf(page);
    	this.lastViewedPageNumber = ipage;
    	WeakReference<T> value = pagination.get(ipage);
        return value;
    }
    
    public WeakReference<T> getPageByDirecao(int direcao) throws UserException{
    	WeakReference<T> valor = null;
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
