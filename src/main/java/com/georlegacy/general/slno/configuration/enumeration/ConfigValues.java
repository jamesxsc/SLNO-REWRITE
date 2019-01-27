package com.georlegacy.general.slno.configuration.enumeration;

import java.util.LinkedList;
import java.util.List;

public enum ConfigValues {

    ;

    private final String propertyKey;
    private final String defaultPropertyValue;

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getDefaultPropertyValue() {
        return defaultPropertyValue;
    }

    public String getValidation() {
        return validation;
    }

    private final String validation;

    ConfigValues(String propertyKey, String defaultPropertyValue, String validation) {
        this.propertyKey = propertyKey;
        this.defaultPropertyValue = defaultPropertyValue;
        this.validation = validation;
    }

    public static List<String> getKeys() {
        List<String> keys = new LinkedList<>();
        for (ConfigValues value : ConfigValues.values())
            keys.add(value.getPropertyKey());
        return keys;
    }

}
