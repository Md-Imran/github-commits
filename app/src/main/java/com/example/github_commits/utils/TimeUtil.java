package com.example.github_commits.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    private static final String FORMAT_1 = "dd MMM yyyy";
    private static final String FORMAT_3 = "MMMM dd, yyyy hh:mm a";
    private static final String FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String FORMAT_4 = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";

    //2022-06-03T08:45:57Z
    private static final String FORMAT_5 = "yyyy-MM-dd";

    public static String convertTimeToDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_1, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String convertTimeToDate(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat oldFormat;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            oldFormat = new SimpleDateFormat(FORMAT_2, Locale.getDefault());
        } else {
            oldFormat = new SimpleDateFormat(FORMAT_4, Locale.getDefault());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_1, Locale.getDefault());
        try {
            Date date = oldFormat.parse(time);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date convertToDate(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }
        SimpleDateFormat oldFormat;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            oldFormat = new SimpleDateFormat(FORMAT_2, Locale.getDefault());
        } else {
            oldFormat = new SimpleDateFormat(FORMAT_4, Locale.getDefault());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_4, Locale.getDefault());
        try {
            return oldFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String convertServerTimePurchaseTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat oldFormat;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            oldFormat = new SimpleDateFormat(FORMAT_2, Locale.getDefault());
        } else {
            oldFormat = new SimpleDateFormat(FORMAT_4, Locale.getDefault());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_3, Locale.getDefault());
        try {
            Date date = oldFormat.parse(time);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertNormalTimeToServer(String normalTime) {
        if (TextUtils.isEmpty(normalTime)) {
            return "";
        }

        SimpleDateFormat oldFormat = new SimpleDateFormat(FORMAT_1, Locale.ENGLISH);
        SimpleDateFormat dateFormat;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat(FORMAT_2, Locale.ENGLISH);
        } else {
            dateFormat = new SimpleDateFormat(FORMAT_4, Locale.ENGLISH);
        }

        try {
            Date date = oldFormat.parse(normalTime);
            String formattedDate = dateFormat.format(date);
            if (formattedDate.contains("+")) {
                String[] arr = formattedDate.split("\\+");
                return arr[0] + "Z";
            } else {
                return dateFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isValid(String validTill) {
        if (!TextUtils.isEmpty(validTill)) {
            SimpleDateFormat oldFormat;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                oldFormat = new SimpleDateFormat(FORMAT_2, Locale.getDefault());
            } else {
                oldFormat = new SimpleDateFormat(FORMAT_4, Locale.getDefault());
            }
            try {
                Date date = oldFormat.parse(validTill);
                return date.getTime() > System.currentTimeMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static String convertNormalToReadable(String time) {
        if (!TextUtils.isEmpty(time)) {
            time = time.split("\\.")[0];
            SimpleDateFormat oldFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            try {
                oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = oldFormat.parse(time);

                Log.d("Timezone", "zone " + tz.getDisplayName());
                SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm a");
                newFormat.setTimeZone(tz);
                return newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String convertRealMonthName(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat oldFormat = new SimpleDateFormat(FORMAT_5, Locale.getDefault());
            try {
                Date date = oldFormat.parse(time);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                return newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

