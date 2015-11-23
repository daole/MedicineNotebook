package com.dreamdigitizers.medicinenote.utils;

import java.text.DateFormat;
import java.text.ParseException;

public class StringUtils {
    public static boolean isEmpty(String pValue) {
        return pValue == null || pValue.equals("") ? true : false;
    }

    public static boolean isInteger(String pValue) {
        try {
            Integer.parseInt(pValue);
            return true;
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    public static boolean isPositiveInteger(String pValue) {
        try {
            int value = Integer.parseInt(pValue);
            return value >= 0 ? true : false;
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    public static boolean isTime(String pValue, DateFormat pFormat) {
        try {
            pFormat.parse(pValue);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
