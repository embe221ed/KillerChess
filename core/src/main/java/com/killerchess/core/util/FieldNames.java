package com.killerchess.core.util;

public enum FieldNames {
    SUCCESS("Success"),
    ERROR_CODE("ErrorCode"),
    USERNAME("Username"),
    PASSWORD("Password"),
    REGISTER("Register"),
    PVP("pvp");
    private final String name;

    FieldNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
