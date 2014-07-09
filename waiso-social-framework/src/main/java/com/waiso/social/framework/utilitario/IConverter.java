package com.waiso.social.framework.utilitario;

import com.waiso.social.framework.exceptions.ConverterException;

public interface IConverter<T> {

	public T convert(String valor) throws ConverterException;
	
}
