package com.dreamdigitizers.drugmanagement.utils;

public class StringUtils {
    public static boolean isEmpty(String pValue) {
        return pValue == null || pValue.equals("") ? true : false;
    }
}
