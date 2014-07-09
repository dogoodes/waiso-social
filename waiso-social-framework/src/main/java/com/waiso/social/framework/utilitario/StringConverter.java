package com.waiso.social.framework.utilitario;

import com.waiso.social.framework.exceptions.ConverterException;

public class StringConverter implements IConverter<String> {

	private static StringConverter instance = new StringConverter();

	private StringConverter() {
	}

	public static StringConverter getInstance() {
		return instance;
	}

	public String convert(String valor) throws ConverterException {
		return valor;
	}
}
