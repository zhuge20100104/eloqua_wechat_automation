package com.oracle.auto.web.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vemurl on 6/16/15.
 */
public class DateUtils {

    public static Date addDays(Date d, int days)
    {
        Long daysLong=Long.valueOf(days);
        d.setTime(d.getTime() + daysLong * 1000 * 60 * 60 * 24);
        return d;
    }

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("MMM d, h:mma");
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
