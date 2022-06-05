package com.example.github_commits.utils;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    //val SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    private static final String SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEKS_MILLIS = 7 * DAY_MILLIS;
    private static final int MONTHS_MILLIS = 4 * WEEKS_MILLIS;

    public static String getFormattedTime(String serverTime) {

        SimpleDateFormat oldFormat = new SimpleDateFormat(SERVER_FORMAT, Locale.getDefault());
        Date date = null;
        try {
            date = oldFormat.parse(serverTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = Calendar.getInstance().getTime();
        String output = "";


        if (date != null) {
            long timeDifference = currentDate.getTime() - date.getTime();
            Log.e("TIME", " : " + date.getTime());
            if (DateUtils.isToday(date.getTime())) {
                // It is today message now check hor minute etc
                Log.e("TIME", "Time difference $timeDifference");

                if (timeDifference > (5 * 60 * 1000)) {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    output = format.format(date);
                } else {
                    output = "Just Now";
                }
            } else {

                // Now check it is yesterday or not
                if (isYesterday(date.getTime())) {
                    output = "Yesterday";
                } else {
                    // Check it is last 5 days or not cause today and yesterday will be ignored
                    if (timeDifference < 7 * DAY_MILLIS) {
                        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.getDefault());
                        output=format.format(date);
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        output=  format.format(date);
                    }
                }

            }
        }
        return output;
    }

    private static boolean isYesterday(long whenInMillis) {
        return DateUtils.isToday(whenInMillis + DateUtils.DAY_IN_MILLIS);
    }
}

