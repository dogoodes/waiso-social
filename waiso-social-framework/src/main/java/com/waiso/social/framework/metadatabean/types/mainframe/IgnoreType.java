package com.waiso.social.framework.metadatabean.types.mainframe;

import com.waiso.social.framework.metadatabean.TransformerUtils;
import com.waiso.social.framework.metadatabean.types.IType;

public class IgnoreType extends AbstractType implements IType {

    public IgnoreType(int length) {
        super(length);
    }

    public Object transform() {
        return TransformerUtils.toString(this, (String) valueIn);
    }

}
