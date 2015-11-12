package com.dreamdigitizers.drugmanagement.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmUtils {
    public static final String INTENT_EXTRA_KEY__ALARM_DATA = "data";

    public static PendingIntent createPendingIntent(Context pContext, Class pActivityClass, int pId, int pFlag, Bundle pExtras) {
        Intent intent = new Intent(pActivityClass.getName());
        if(pExtras != null) {
            intent.putExtra(AlarmUtils.INTENT_EXTRA_KEY__ALARM_DATA, pExtras);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(pContext, pId, intent, pFlag);
        return pendingIntent;
    }

    public static void cancelAlarm(Context pContext, PendingIntent pPendingIntent) {
        AlarmManager alarmManager = (AlarmManager)pContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pPendingIntent);
    }

    public static void setAlarm(Context pContext, PendingIntent pPendingIntent, int pYear, int pMonth, int pDate, int pHour, int pMinute) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(pYear, pMonth, pDate, pHour, pMinute);
        long alarmTime = gregorianCalendar.getTimeInMillis();
        long currentTime = System.currentTimeMillis();
        if(alarmTime > currentTime) {
            AlarmManager alarmManager = (AlarmManager) pContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pPendingIntent);
        }
    }

    public static void enableBootReceiver(Context pContext, Class pReceiverClass) {
        ComponentName componentName = new ComponentName(pContext, pReceiverClass);
        PackageManager packageManager = pContext.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
