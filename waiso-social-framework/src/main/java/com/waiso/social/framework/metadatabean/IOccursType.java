package com.waiso.social.framework.metadatabean;

import com.waiso.social.framework.metadatabean.types.IType;

public interface IOccursType extends IType{
    public void addMetaDataBean(IMetaDataBean metaDataBean);
    public void flush();
}
