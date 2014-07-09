package com.waiso.social.framework.view;

public enum RegexValidation implements IRegexValidation {
	Email(".+@.+\\.[a-z]+"),
	Placa("[A-Z]{3}[0-9]{4}"),
	OnlyNumbers("^[0-9]+$"),
	OnlyLetters("^[a-zA-Z]+$"),
	OnlyLettersNumbers("^[a-zA-Z0-9]+$");
	
	private final String regexExpression;
	private final boolean applyFormat;
	private final Format formatInstance = new Format();
	
	private RegexValidation(String regexExpression){
		this.regexExpression = regexExpression;
		this.applyFormat = false;
	}
	
	private RegexValidation(String regexExpression, boolean applyFormat){
		this.regexExpression = regexExpression;
		this.applyFormat = applyFormat;
	}
	
	public String expression(){
		return regexExpression;
	}
	
	public boolean evaluate(String value){
		if(applyFormat){
			value = formatInstance.format(value);
		}
		return !value.matches(regexExpression);
	}
	
	final class Format{
		private String format(String value){
			if(value != null){
				value = value.replace(',', '|');
				value = value.replaceAll("[.]","");
				value = value.replace('|', '.');
			}	
			return value;
		}
	}
}