package com.waiso.social.framework.metadatabean;

import java.math.BigDecimal;

import com.waiso.social.framework.metadatabean.types.IType;
import com.waiso.social.framework.metadatabean.types.mainframe.NumberType;
import com.waiso.social.framework.utilitario.BigDecimalUtils;
import com.waiso.social.framework.utilitario.IntegerUtils;
import com.waiso.social.framework.utilitario.LongUtils;
import com.waiso.social.framework.utilitario.StringUtils;

public class TransformerUtils {

    public static Integer toInteger(String dado) {
        return IntegerUtils.parseInt(dado);
    }

    public static Long toLong(String dado) {
        return LongUtils.parseLong(dado);
    }

    public static BigDecimal toBigDecimal(String dado) {
        return BigDecimalUtils.parseBigDecimal(dado);
    }

   
    public static String toString(IType type, Object value){
        String pad = "";
        int length = type.getLength();
        String valueStr = (value == null)?"":value.toString();
        if (type instanceof NumberType){
            pad = "0";
            valueStr = valueStr.replaceAll("[.,]", "");
            valueStr = StringUtils.leftPad(valueStr, pad, length); 
        }else{
            valueStr = StringUtils.rightPad(valueStr, length, pad);
        }
        return valueStr;
    }

}
