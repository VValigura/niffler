package guru.qa.niffler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
