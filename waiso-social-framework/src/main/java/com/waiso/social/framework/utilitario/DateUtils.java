package com.waiso.social.framework.utilitario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import com.waiso.social.framework.configuracao.GerenciadorConfiguracao;
import com.waiso.social.framework.exceptions.ConverterException;
import com.waiso.social.framework.i18n.LocaleUtils;

/**
 * Classe utilitaria de data. E fortemente recomendado que essa classe seja
 * utilizado quando um objeto Calendar precisar ser criado pois esta classe
 * forca o Locale para pt_BR padrao esse a ser utilizado pela aplicacao.
 */
public class DateUtils implements IConverter<Calendar>{
	
	private static DateUtils instance = new DateUtils();
	public static final String BRAZILIAN_PATTERN = "dd/MM/yyyy";

	private DateUtils(){}
	public static DateUtils getInstance(){
		return instance;
	}
	
	/**
	 * Retorna um objeto Calendar com o Locale pt_BR
	 * @return c Calendar
	 */
	public Calendar getCalendar() {
		Calendar c = Calendar.getInstance(LocaleUtils.DEFAULT_LOCALE);
		return c;
	}

	/**
	 * Retorna um Integer no formato YYYYMMDD do calendar informado como parametro;
	 * 
	 * @param data Data que sera transformanda em Integer
	 * @return Integer representando a data, ou nulo caso o parametro seja nulo.
	 */
	public Integer calendarToInteger(Calendar data) {
		if(data != null){
			return Integer.valueOf(data.get(Calendar.YEAR) + "" + StringUtils.leftPad("" + (data.get(Calendar.MONTH) + 1), "0", 2) + "" + StringUtils.leftPad("" + data.get(Calendar.DAY_OF_MONTH), "0", 2));
		}
		return null;
	}
	
	public Integer calendarToIntegerHour(Calendar data) {
		if(data != null){
			SimpleDateFormat format = new SimpleDateFormat("HHmmss", LocaleUtils.DEFAULT_LOCALE);
			String hour =  format.format(data.getTime());
			return Integer.valueOf(hour);
		}
		return Integer.valueOf(0);
	}
	
	/**
	 * Retorna uma String no formato DD/MM/YYYY do Integer informado como parametro;
	 * 
	 * @param data Integer que sera transformanda em String
	 * @return String representando a data, ou nulo caso o parametro seja nulo.
	 */
	public String IntegerToFormattedDate(Integer data) {
		if(data != null){
			String strData = ""+data;
			if(strData.length() == 8){
				String year = strData.substring(0,4);
				String month = strData.substring(4,6);
				String day = strData.substring(6,8);
				return day + "/" + month + "/" + year;
			}	
		}
		return null;
	}
	
	/**
	 * Retorna uma String no formato DD/MM/YYYY da String yyyy-MM-dd hh:mm:ss
	 * 
	 * @param data String que sera convertidade
	 * @return Integer String a data, ou nulo caso o parametro seja nulo.
	 */
	public String converterISO8601ToToFormattedDate(String data) {
		if(data != null){
			String strData = ""+data;
			if(strData.length() == 19){
				String year = strData.substring(0,4);
				String month = strData.substring(5,7);
				String day = strData.substring(8,10);
				return day + "/" + month + "/" + year;
			}	
		}
		return null;
	}
	
	public String IntegerToFormattedDate(Integer data, String pattern) {
		if(data != null){
			String strData = IntegerToFormattedDate(data);
			try{
				Calendar c = stringToCalendar(strData, "dd/MM/yyyy");
				SimpleDateFormat format = new SimpleDateFormat(pattern, LocaleUtils.DEFAULT_LOCALE);
				return format.format(c.getTime());
			}catch(ParseException e){
				
			}
		}
		return "";
	}

	/**
	 * Transforma o objeto Date em string seguindo o formato definido no
	 * parametro pattern
	 * 
	 * @param date Data a ser transformada
	 * @param pattern Formato de saida da data
	 * @return Uma string com a data formatada
	 */
	public String dateToString(Calendar date, String pattern) {
		if(date == null){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern, LocaleUtils.DEFAULT_LOCALE);
		return format.format(date.getTime());
	}
	
    /**
     * Transforma uma data (Date) em String. Exemplo de pattern: ddMMyyyy.
     * 
     * @param date Data.
     * @param pattern padrao desejado.
     * @return Data formato String.
     */
    public String dateToString(Date date, String pattern) {
        if(date == null){
        	return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern, LocaleUtils.DEFAULT_LOCALE);
        return format.format(date);
    }
	
	public String today(String pattern){
		return dateToString(Calendar.getInstance(), pattern);
	}

	/**
	 * Converte a string informada em data, a string deve ter o formato
	 * dd/MM/yyyy
	 * 
	 * @param date String que representa uma data
	 * @return A Data que estava representada na string
	 * @throws ConverterException Caso o formato nao seja compativel com a formatacao da String
	 */
	public Calendar convert(String date) throws ConverterException {
		Calendar data = null;
		if(date != null && !date.equals("")){
			try{
				data = stringToCalendar(date, BRAZILIAN_PATTERN);
			}catch(ParseException e){
				throw new ConverterException(this.getClass(), e);
			}
		}
		return data;
	}
	
	/**
	 * Converte a string informada em data, a string deve ter o formado
	 * informado em pattern
	 * 
	 * @param date String que representa uma data
	 * @param pattern Formato de como a data esta representado pelo string
	 * @return A Data que estava representada na string
	 * @throws ParseException Caso o formato nao seja compativel com a formatacao da String
	 */
	public Calendar stringToCalendar(String date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern, LocaleUtils.DEFAULT_LOCALE);
		Date d = format.parse(date);
		Calendar c = getCalendar();
		c.setTime(d);
		return c;
	}
	
	/**
     * Cria um objeto Date a partir de uma String e um padao. Exemplo de
     * pattern: ddMMyyyy.
     * 
     * @param date Data a ser criada.
     * @param pattern padrao.
     * @return Data criada
     * @throws ParseException Caso ocorra algum erro.
     */
    public Date stringToDate(String date, String pattern) throws ParseException {
        if(date.length() == 7){
            date = "0" + date;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern, LocaleUtils.DEFAULT_LOCALE);
        return format.parse(date);
    }

    public String toStringISO8601(Calendar calendar, ISO8061Enum formatter) {
    	String dayStart = GerenciadorConfiguracao.getConfiguracao("START_DAYLIGHT_DAY");
        String monthStart = GerenciadorConfiguracao.getConfiguracao("START_DAYLIGHT_MONTH");
        String dayEnd = GerenciadorConfiguracao.getConfiguracao("END_DAYLIGHT_DAY");
        String monthEnd = GerenciadorConfiguracao.getConfiguracao("END_DAYLIGHT_MONTH");
        
        int iDayStart = Integer.valueOf(dayStart);
        int iMonthStart = Integer.valueOf(monthStart);
        int iDayEnd = Integer.valueOf(dayEnd);
        int iMonthEnd = Integer.valueOf(monthEnd);
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, iMonthStart);
        c.set(Calendar.DAY_OF_MONTH, iDayStart);
        
        c.set(Calendar.MONTH, iMonthEnd);
        c.set(Calendar.DAY_OF_MONTH, iDayEnd);
        c.add(Calendar.YEAR, 1);
        
        int oneHour = 3600000;
		SimpleTimeZone timeZone = new SimpleTimeZone(oneHour, "America/Sao_Paulo", iMonthStart, iDayStart, 0, oneHour,SimpleTimeZone.UTC_TIME, iMonthEnd, iDayEnd, 0, oneHour, SimpleTimeZone.UTC_TIME, oneHour);
		boolean isDayLight = timeZone.inDaylightTime(calendar.getTime());
		SimpleDateFormat df = new SimpleDateFormat( formatter.getFormatter() );
        String output = df.format( calendar.getTime() );
        String utcBrazil = "-03:00";
        if(isDayLight){
        	utcBrazil = "-02:00";
        }
        return output+utcBrazil;
    }
    
    public int calculaIdade(String dataNasc, String pattern){
        DateFormat sdf = new SimpleDateFormat(pattern);
        Date dataNascInput = null;
        try{
            dataNascInput= sdf.parse(dataNasc);
        }catch(Exception e){
        	
        }
        return calculaIdade(dataNascInput);
    }
    
    public int calculaIdade(Date dataNasc){
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);
        // Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();
        // Obtém a idade baseado no year
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);
        if(today.before(dateOfBirth)){
            age--;
        }
        return age;
    }
}