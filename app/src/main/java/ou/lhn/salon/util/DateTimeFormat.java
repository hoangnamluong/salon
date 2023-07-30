package ou.lhn.salon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormat {
    public static Date convertSqliteDateToDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        if(date == null || date.equals(""))
            return null;

        return simpleDateFormat.parse(date);
    }

    public static String convertDateToSqliteDate(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        if(date == null)
            return null;

        return simpleDateFormat.format(date);
    }

    public static Calendar convertDateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar;
    }
}
