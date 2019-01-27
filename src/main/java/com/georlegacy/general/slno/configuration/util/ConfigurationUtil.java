package com.georlegacy.general.slno.configuration.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationUtil {

    public static Map<String, String> readProperties(Collection<String> toRead, Properties properties) {
        Map<String, String> values = new HashMap<>();
        for (String property : toRead) {
            values.put(property, properties.getProperty(property));
        }
        return values;
    }

}