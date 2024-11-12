package com.example.library_management.utils;

public class ResponseCodeHandler {
    public static String getMessage(Integer code) {
        return PropertiesContext.getResponseCodesProperties().getProperty(code.toString());
    }
    public static String getMessage(String code) {
        return PropertiesContext.getResponseCodesProperties().getProperty(code);
    }
}

