package me.kailip.utils;

import java.util.Calendar;

public class Logger {
    public static void log(String result) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.getTime().getHours();
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String format = "[" + month + "/" + day + " | " + String.format("%02d", hour) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + "] ";
        System.out.println(format + result);
    }
}
