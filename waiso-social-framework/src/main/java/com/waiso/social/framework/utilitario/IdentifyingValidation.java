package com.waiso.social.framework.utilitario;

import com.waiso.social.framework.exceptions.UserLinkException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;
import com.waiso.social.framework.view.ComplexValidation;

public class IdentifyingValidation implements ComplexValidation{

	public static IdentifyingValidation instance = new IdentifyingValidation();
	public static IdentifyingValidation cnpjInstance = new IdentifyingValidation(TYPE.CNPJ);
	public static IdentifyingValidation cpfInstance = new IdentifyingValidation(TYPE.CPF);
	private static enum TYPE {CNPJ, CPF};
	private TYPE type;
	
	public static IdentifyingValidation getInstance(){
		return instance;
	}
	
	public static IdentifyingValidation getCNPJInstance(){
		return cnpjInstance;
	}
	
	public static IdentifyingValidation getCPFInstance(){
		return cpfInstance;
	}
	
	private IdentifyingValidation(){}
	private IdentifyingValidation(TYPE type){
		this.type = type;
	}
	
	public void validate(String name, String valor) throws UserLinkException {
		if (valor != null && !valor.equals("")) {
			if (type != null){
				if (type.equals(TYPE.CPF)){
					validaCPF(name, valor);
				}else if (type.equals(TYPE.CNPJ)){
					validaCNPJ(name, valor); 
				}
			}else{
				if (valor.length() <= 11){
					validaCPF(name, valor);
				}else{
					validaCNPJ(name, valor);
				}
			}
		}
	}
	
	public void type(TYPE type){
		this.type = type;
	}
	

	private void validaCPF(String campo, String cpf) {
		boolean isOk = true;

        // Valida a mascara 9999999999999
        String pattern1 = "\\d{11}";
        // Valida a mascara 999.999.999-99
        String pattern2 = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}";
        // Valida se existe somentes numeros
        String pattern3 = "\\d*";

        // valida se o cpf est· num formato correto
        if (!cpf.matches(pattern1) && !cpf.matches(pattern2)) {
        	if(cpf.matches(pattern3) && cpf.length() <= 11){
        		int length = cpf.length();
    			int diferenca = 11 - length;
    			
    			for(int i = 0; i < diferenca; i++) {
    				cpf = "0" + cpf;
    			}
        	}else{
        		isOk = false;
        	}
        }
        	
        if (!isOk) {	
        	String message = GerenciadorMensagem.getMessage("framework.utils.identifying.invalid.format", "CPF");
            throw new UserLinkException(campo, message);
        }

        // valida se o cnpj eh um cnpj invalido
        if (cpf.equals("00000000000")
                || cpf.equals("11111111111")
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            isOk = false;
        }

        // Se n„o houver erros, continuam os c·lculos
        if (isOk) {

            int[] digitos = new int[11];
            int[] valoresCalculo1 = new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] valoresCalculo2 = new int[] {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

            int pos = 0;
            // Passa os dÌgitos a um array
            for (int i = 0; i < cpf.length(); i++) {
                if (Character.isDigit(cpf.charAt(i))) {
                    digitos[pos] = Integer.parseInt(cpf.substring(i, i + 1));
                    pos++;
                }
            }

            int sum = 0;
            // C·lculo do primeiro dÌgito de controle
            for (int i = 0; i < valoresCalculo1.length; i++) {
                sum = sum + (digitos[i] * valoresCalculo1[i]);
            }
            int digito1 = sum % 11;
            if (digito1 < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - digito1;
            }

            sum = 0;
            // C·lculo do segundo dÌgito de controle
            for (int i = 0; i < valoresCalculo2.length; i++) {
                sum = sum + (digitos[i] * valoresCalculo2[i]);
            }
            int digito2 = sum % 11;
            if (digito2 < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - digito2;
            }
            // valida os digitos do cpf com os digitos de controle
            if (digitos[9] != digito1 || digitos[10] != digito2) {
            	isOk = false;
            }
        }
        if (!isOk){
        	String message = GerenciadorMensagem.getMessage("framework.utils.identifying.invalid.format", "CPF");
            throw new UserLinkException(campo, message);
        }
	}
	
	private void validaCNPJ(String campo, String cnpj) {
		boolean isOk = false;

        // Valida se coincedir com a mascara 99999999999999
        String pattern1 = "\\d{14}";
        // Valida se coincedir com a mascara 99.999.999/9999-99
        String pattern2 = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}";
        // Valida se coincedir com a mascara 99.999.999/9999-99
        String pattern3 = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{1}";
        // Valida se existe somentes numeros
        String pattern4 = "\\d*";

        // valida se o cpnj esta num formato correto
		if (cnpj.matches(pattern1)) {
			isOk = true;
		} else if (cnpj.matches(pattern2)) {
			isOk = true;
		} else if (cnpj.matches(pattern3)) {
			cnpj = cnpj.substring(0,16) + "0" + cnpj.substring(16); 
			isOk = true;
		} else if (cnpj.matches(pattern4) && cnpj.length() <= 14) {
			
			int length = cnpj.length();
			int diferenca = 14 - length;

			for (int i = 0; i < diferenca; i++) {
				cnpj = "0" + cnpj;
			}
			isOk = true;
		} 
        
        if (!isOk) {
        	String message = GerenciadorMensagem.getMessage("framework.utils.identifying.invalid.format", "CNPJ");
            throw new UserLinkException(campo, message);
        }
        
        if (isOk) {
            int[] digitos = new int[14];
            int[] valoresCalculo1 = new int[] {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] valoresCalculo2 = new int[] {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int pos = 0;
            // Passa os digitos a um array
            for (int i = 0; i < cnpj.length(); i++) {
                if (Character.isDigit(cnpj.charAt(i))) {
                    digitos[pos] = Integer.parseInt(cnpj.substring(i, i + 1));
                    pos++;
                }
            }

            int sum = 0;
            // C·lculo do primeiro digito de controle
            for (int i = 0; i < valoresCalculo1.length; i++) {
                sum = sum + (digitos[i] * valoresCalculo1[i]);
            }
            int digito1 = sum % 11;
            if (digito1 < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - digito1;
            }

            sum = 0;
            // Calculo do segundo digito de controle
            for (int i = 0; i < valoresCalculo2.length; i++) {
                sum = sum + (digitos[i] * valoresCalculo2[i]);
            }
            int digito2 = sum % 11;
            if (digito2 < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - digito2;
            }

            // valida os digitos do cpnj com os digitos de controle
            if (digitos[12] != digito1 || digitos[13] != digito2) {
                isOk = false;
            }
        }
       if (!isOk){
    	   String message = GerenciadorMensagem.getMessage("framework.utils.identifying.invalid.format", "CNPJ");
           throw new UserLinkException(campo, message);
       }
	}
}