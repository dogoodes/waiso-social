package com.waiso.social.framework.metadatabean.types.mainframe;

import com.waiso.social.framework.metadatabean.types.IType;
import com.waiso.social.framework.utilitario.StringUtils;

public class StringType extends AbstractType implements IType {

    public StringType(int length) {
        super(length);
    }

    public Object transform() {
        return StringUtils.normalizeToMainframe((String) valueIn, length);
    }
}
