package com.waiso.social.framework.utilitario;

import java.util.Date;

import com.waiso.social.framework.exceptions.ConverterException;

public class IntegerUtils implements IConverter<Integer> {

	private static IntegerUtils instance = new IntegerUtils();
	
	private IntegerUtils(){}
	
	public static IntegerUtils getInstance(){
		return instance;
	}
	
	
	public static boolean isBlank(String valor){
		boolean isBlank = StringUtils.isBlank(valor);
		if (!isBlank){
			if (StringUtils.isOnlyNumber(valor)){
				isBlank = (Integer.valueOf(valor).intValue() == 0);
			}
		}
		return isBlank;
	}
	
    public static int parseInt(Number value) {
        if (value != null) {
            return value.intValue();
        }
        return 0;
    }

    public static int parseInt(String value) {
        if (!StringUtils.isBlank(value)) {
            return Integer.parseInt(value.trim());
        }
        return 0;
    }

    public static int parseInt(Date data, String pattern) {
        int ret = 0;
        if (data != null) {
            String strData = DateUtils.getInstance().dateToString(data, pattern);
            ret = parseInt(strData);
        }
        return ret;
    }
	
	/**
	 * Metodo responsavel por converter o valor String informado em Integer.
	 * 
	 * @param valor
	 *            O valor a ser transformado em Integer
	 * @return O Valor transformado em Integer
	 * @throws ConverterException
	 *             Caso ocorra algum erro na conversao.
	 */
	public Integer convert(String valor)
			throws ConverterException {
		Integer newInt = null;
		if (valor != null && !valor.equals("")) {
			try {
				newInt = Integer.valueOf(valor);
			} catch (NumberFormatException nfe) {
				throw new ConverterException(this.getClass(), nfe);
			}
		}
		return newInt;
	}

}
