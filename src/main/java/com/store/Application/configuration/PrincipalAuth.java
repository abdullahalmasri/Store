package com.store.Application.configuration;

import java.io.Serializable;

public class PrincipalAuth implements Serializable {


    private final Type type;
    private final String str;

    public PrincipalAuth(Type type, String str) {
        this.type = type;
        this.str = str;
    }

    public Type getType() {
        return type;
    }

    public String getStr() {
        return str;
    }

    public enum Type {
        USER_NAME,
        PUBLIC_ID
    }
}
