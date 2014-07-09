package com.waiso.social.framework.view;

import javax.servlet.ServletRequest;

import com.waiso.social.framework.exceptions.ConverterException;
import com.waiso.social.framework.exceptions.UserLinkException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;

/**
 * Esta implementacao de IComponentView trabalha em conjunto com o InputHolder
 * que eh uma variavel thread-local responsavel por armazenar as excecoes ocorridas
 * durante o processo de leitura do componente, esta previsto nesta implementacao
 * excecoes de conversao  @link{com.waiso.social.framework.exceptions.ConverterException} 
 * e excecao de restricao quando o required eh informado, porem todas as excecoes sao lancadas como
 * @link{com.waiso.social.framework.exceptions.UserLinkException}. Uma forma de
 * nao lancar excecao e informando na hora de recuperar o valor a parametro silent no qual
 * nao lanca a excecao deixando a cargo do usuario recupera-la somente atraves do InputHolder.
 * @see InputHolder
 *
 * @param <T>
 */

public class Input<T> implements IComponentView<T> {

	private final String name;
	private final String label;
	private final T value;
	private Boolean required = null;
	
	/**
	 * @param builder
	 */
	private Input(Builder<T> builder){
		this.name  = builder.name;
		this.label = builder.label;
		this.value = builder.convertedValue;
		this.required = builder.required;
	}
	
	public static <T> Input.Builder<T> builderInstance(String value, Class<T> type){
		return new Input.Builder<T>(value, type);
	}
	
	public static <T> Input.Builder<T> builderInstance(ServletRequest value, Class<T> type){
		return new Input.Builder<T>(value, type);
	}
	
	public static <T> Input.Builder<T> builderInstance(ServletRequest value, Class<T> type, boolean screenSaver){
		return new Input.Builder<T>(value, type, screenSaver);
	}
	
	public static <T> Input.Builder<T> builderInstance(String value, Class<T> type, boolean screenSaver){
		return new Input.Builder<T>(value, type, screenSaver);
	}
	
	public static class Builder<T>{
		private String name = null;
		private String label = null;
		private String value = null;
		private Boolean required = null;
		private String defaultValue = null;
		private ComplexValidation complexValidation = null;
		private IRegexValidation regexValidation = null;
		private ComplexValidation requiredComplexValidation = null;
		private DependenceValidation dependenceValidation = null;
		private IComponentView<?> dependence = null;
		private boolean screenSaver =false;
		private T convertedValue = null;
		private Class<T> type;
		private String focus = null;
		
		private ServletRequest request = null;
		
		public Builder(String value, Class<T> type, boolean screenSaver) {
			this.value = value;
			this.type = type;
			this.screenSaver = screenSaver;
		}
		
		public Builder(String value, Class<T> type) {
			this.value = value;
			this.type = type;
		}
		
		public Builder(ServletRequest request, Class<T> type){
			this.request = request;
			this.type = type;
		}
		
		public Builder(ServletRequest request, Class<T> type, boolean screenSaver){
			this.request = request;
			this.type = type;
			this.screenSaver = screenSaver;
		}
		
		public Builder<T> validation(ComplexValidation complexValidation){
			if(!screenSaver){
				this.complexValidation = complexValidation;
			}
			return this;
		}
		
		public Builder<T> validation(IRegexValidation regexValidation){
			if(!screenSaver){
				this.regexValidation = regexValidation;
			}
			return this;
		}
		
		public Builder<T> validation(DependenceValidation dependenceValidation, IComponentView<?> dependence){
			if(!screenSaver){
				this.dependenceValidation = dependenceValidation;
				this.dependence = dependence;
			}
			return this;
		}
		
		public Builder<T> name(String name){
			this.name = name;
			return this;
		}
		
		public Builder<T> label(String label){
			this.label = label;
			return this;
		}
		
		public Builder<T> required(){
			if(!screenSaver){
				this.required = Boolean.TRUE;
			}
			return this;
		}
		
		public Builder<T> required(boolean required){
			if(!screenSaver){
				this.required = required;
			}
			return this;
		}
		
		public Builder<T> required(ComplexValidation requiredComplexValidation){
			if(!screenSaver){
				this.requiredComplexValidation = requiredComplexValidation;
			}
			return this;
		}
		
		public Builder<T> defaultValue(String value){
			this.defaultValue = value;
			return this;
		}
		
		public Builder<T> focus(String focus){
			this.focus = focus;
			return this;
		}
		
		public Input<T> build() {
			try{
				if(recoverValueByRequest()){
					this.value = request.getParameter(name);
				}
				if(isNullValue() && defaultValue != null){
					this.value = defaultValue;
				}
				if(isNullValue()){ //Se for nulo entao vamos ver se ele eh requerido
					this.convertedValue = null;
					if(Boolean.TRUE.equals(required)){
						String messageKey = "framework.utils.required";
						String message = GerenciadorMensagem.getMessage(messageKey, label);
						String link = (focus == null?name:focus);
						UserLinkException userLinkException = new UserLinkException(link, message);
						InputHolder.get().add(userLinkException);
					}
					if(requiredComplexValidation != null){
						try{
							requiredComplexValidation.validate(name, value);
						}catch(UserLinkException e){
							InputHolder.get().add(e);
						}
					}
					if(dependenceValidation != null){
						try{
							dependenceValidation.validate(new Input<T>(this), dependence);
						}catch(UserLinkException e){
							InputHolder.get().add(e);
						}
					}
				}else{ //Se o campo nao for nulo entao faz sentido validar
					if(regexValidation != null){
						if(regexValidation.evaluate(value)){
							String messageKey = "framework.utils.regexValidation."+regexValidation.name();
							String message = GerenciadorMensagem.getMessage(messageKey);
							UserLinkException userLinkException = new UserLinkException(name, message);
							InputHolder.get().add(userLinkException);
						}
					}
					if(complexValidation != null){
						try{
							complexValidation.validate(name, value);
						}catch(UserLinkException e){
							InputHolder.get().add(e);
						}
					}
					this.convertedValue = ConverterView.convert(type,value);
					
					if(dependenceValidation != null){
						try{
							dependenceValidation.validate(new Input<T>(this), dependence);
						}catch(UserLinkException e){
							InputHolder.get().add(e);
						}
					}
				}
			}catch(ConverterException e){
				String message = null;
				if(e.getClass() == ConverterException.class){
					String nameType = type.getSimpleName();
					String messageKey = "framework.utils."+ nameType.toLowerCase()+".with.invalid.field";
					message = GerenciadorMensagem.getMessage(messageKey, label);		
				}else{
					message = e.getMessage();
				}
				UserLinkException userLinkException = new UserLinkException(name, message);
				InputHolder.get().add(userLinkException);
			}
			return new Input<T>(this);
		}
	
		private boolean recoverValueByRequest(){
			return (value == null && name != null);
		}
		
		private boolean isNullValue(){
			return (value == null || "".equals(value));
		}
	}
	
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public T getValue() {
		if(!InputHolder.get().isEmpty()){
			throw InputHolder.get().get(0);
		}
		return value;
	}
	
	public T getValue(boolean silent){
		if(silent){
			return value;
		}else{
			return getValue();
		}
	}
	
	public Boolean isRequired(){
		return required;
	}
}