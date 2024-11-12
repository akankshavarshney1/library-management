package com.example.library_management.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesContext {
    private final Properties RESPONSE_CODES_PROPERTIES;

    private PropertiesContext() throws Throwable{
        RESPONSE_CODES_PROPERTIES = loadProperties();
    }

    private static Properties loadProperties() throws Throwable {
        try {
            Properties properties = new Properties();
            InputStream in = PropertiesContext.class.getClassLoader().getResourceAsStream("responseCode.properties");
            properties.load(in);

            return properties;
        } catch (Throwable t) {
            throw new RuntimeException();
        }
    }


    private static class SingletonHolder {
        private static PropertiesContext INSTANCE;

        static {
            PropertiesContext tmp = null;
            try {
                tmp = new PropertiesContext();
            } catch (Throwable t) {
                throw new ExceptionInInitializerError(t);
            }

            INSTANCE = tmp;
        }
    }
    private static final PropertiesContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static final Properties getResponseCodesProperties() {
        return getInstance().RESPONSE_CODES_PROPERTIES;
    }
}

