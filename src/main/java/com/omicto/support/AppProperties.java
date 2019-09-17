package com.omicto.support;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static final Properties PROPERTIES = new Properties();
    //TODO: Use the current profile, do not default to this file
    private static final String PROPERTIES_FILENAME = "/application.properties";

    static {
        InputStream file;
        try {
            file = AppProperties.class.getResourceAsStream(PROPERTIES_FILENAME);
            PROPERTIES.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public enum Property {
        private String propertyName;

        Property(String propertyName) {
            this.propertyName = propertyName;
        }
    }

    public static String getPropertyValue(Property property){
        return getPropertyValue(property.propertyName);
    }*/

    public static String getPropertyValue(String property) {
        if (StringUtils.isEmpty(property))
            return null;
        return PROPERTIES.getProperty(property);
    }
}
