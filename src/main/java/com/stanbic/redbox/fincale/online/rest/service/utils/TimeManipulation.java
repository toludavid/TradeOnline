package com.stanbic.redbox.fincale.online.rest.service.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

 

public class TimeManipulation {

    // Method to get the current time in milliseconds
    public static String getCurrentTimeInMillis() {
        long unixTime = System.currentTimeMillis() / 1000L; //in sec format
        System.out.println("UNIX In SEC IS:  + " + unixTime);
        return String.valueOf(System.currentTimeMillis()); //in millisec

    }

 

    // Method to get future time (millisecondsToAdd) from the current time in milliseconds
    public static String getFutureTimeInMillis(long millisecondsToAdd) {
        return String.valueOf(System.currentTimeMillis() + millisecondsToAdd);
    }

    public static String getCurrentTimeWithMinutesAdded(int minutesToAdd) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime updatedTime = now.plusMinutes(minutesToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedTime.format(formatter);
    }
}