package com.waiso.social.framework.metadatabean.types.mainframe;

import com.waiso.social.framework.metadatabean.TransformerUtils;
import com.waiso.social.framework.metadatabean.types.IType;

public class IntegerType extends NumberType implements IType {
  
    public IntegerType(int length) {
        super(length);
    }

    public Object transform() {
        return TransformerUtils.toInteger((String)valueIn);
    }
}
