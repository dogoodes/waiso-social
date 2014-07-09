package com.waiso.social.framework.metadatabean.types;

import java.lang.reflect.Method;

import com.waiso.social.framework.exceptions.UserException;
import com.waiso.social.framework.i18n.GerenciadorMensagem;

public class ConstructorType {

    private final Class classType;

    private final String constructor;

    public static final String DEFAULT = "default";

    public ConstructorType(Class classType, String constructor) {
        this.classType = classType;
        this.constructor = constructor;
    }

    public Class getClassType() {
        return classType;
    }

    public String getConstructor() {
        return constructor;
    }

    public Object toObject() {
        try {
            if (DEFAULT.equals(constructor)) {
                return classType.newInstance();
            } else {
                Method constructorMethod = classType.getMethod(constructor);
                Object newInstance = constructorMethod.invoke(null, new Object[] {});
                return newInstance;
            }
        } catch (Exception e) {
            String message =
                    GerenciadorMensagem.getMessage("metadata.constructor.not.exist", 
                            constructor,
                            classType.getName());
           UserException erro = new UserException(message);
            throw erro;
        }
    }
}
