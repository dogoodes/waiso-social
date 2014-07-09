package com.waiso.social.framework.pagination;

import java.lang.ref.WeakReference;

import com.waiso.social.framework.exceptions.UserException;

public interface IPagination<T> {

    public void addDataInPage(int page, T value);
    
    /**
     * Atualiza os dados de uma pagina
     * @param page
     * @param value
     */
    public void updateDataInPage(int page, T value);

    public int getSize();

    public T getCurrentElement();

    public T getPreviousElements(int count);

    public T getNextElements(int count);

    public void resetIndex();

    public Integer getLastPage();
    
    public T getPage(int page) throws UserException;
    
    public int getLastViewedPageNumber();
    
    public T getPageByDirecao(int direcao) throws UserException;
    
    public static final int PROXIMO = 1;
    public static final int ANTERIOR = 2;
    public static final int AVANCA = 3;
    public static final int RETROCEDE = 4;
    

}
