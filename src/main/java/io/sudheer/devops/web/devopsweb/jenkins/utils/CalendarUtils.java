package io.sudheer.devops.web.devopsweb.jenkins.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {

    public static String dateFormat = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    
    public static String timeFormat = "hh:mm:ss";
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat);

    public static String ConvertMilliSecondsToFormattedDate(Long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }
    
    public static String ConvertMilliSecondsToFormattedTime(Long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleTimeFormat.format(calendar.getTime());
    }
}
