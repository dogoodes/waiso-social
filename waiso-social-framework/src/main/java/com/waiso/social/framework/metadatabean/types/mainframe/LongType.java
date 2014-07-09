package com.waiso.social.framework.metadatabean.types.mainframe;

import com.waiso.social.framework.metadatabean.TransformerUtils;
import com.waiso.social.framework.metadatabean.types.IType;

public class LongType extends NumberType implements IType {

    public LongType(int length) {
        super(length);
    }

    public Object transform() {
        return TransformerUtils.toLong((String) valueIn);
    }

}
