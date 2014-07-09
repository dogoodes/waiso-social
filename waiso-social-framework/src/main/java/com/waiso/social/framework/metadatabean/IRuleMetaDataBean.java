package com.waiso.social.framework.metadatabean;

import com.waiso.social.framework.metadatabean.rules.IRule;

public interface IRuleMetaDataBean extends IMetaDataBean {

    public void addSetRule(IRule rule);
    
    public void addGetRule(IRule rule);
    
    public void addUseRule(IRule rule);
    
    public boolean isValidSet();
    
    public boolean isValiGet();
    
    public boolean isValidUse();
    
}
