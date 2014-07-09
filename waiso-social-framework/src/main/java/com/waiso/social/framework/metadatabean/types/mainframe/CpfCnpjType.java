package com.waiso.social.framework.metadatabean.types.mainframe;

import com.waiso.social.framework.metadatabean.TransformerUtils;
import com.waiso.social.framework.metadatabean.types.IType;

public class CpfCnpjType extends NumberType implements IType {

    public CpfCnpjType(int length) {
        super(length);
    }

    public Object transform() {
        String valueStr = (String) this.valueIn;
        if (valueStr != null) {
            valueStr = valueStr.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "");
            valueStr = TransformerUtils.toString(this, valueStr.trim());
        }
        return valueStr;
    }
}
