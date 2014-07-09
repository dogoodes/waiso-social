package com.waiso.social.framework.metadatabean;

import com.waiso.social.framework.metadatabean.types.IType;

public class MetaDataBean implements IMetaDataBean{

    private final String name;

    private final IType type;

    public MetaDataBean(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IType getType() {
        return type;
    }
    

}
