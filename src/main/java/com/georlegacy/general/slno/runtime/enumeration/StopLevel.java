package com.georlegacy.general.slno.runtime.enumeration;

public enum StopLevel {

    EMERGENCY(5),
    ABRUPT(1),
    NORMAL(0),
    ;

    StopLevel(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

}
