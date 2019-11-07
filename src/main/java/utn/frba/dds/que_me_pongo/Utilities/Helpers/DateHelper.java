package utn.frba.dds.que_me_pongo.Utilities.Helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date sumarDiasAFecha(Date fechaOriginal, int diasASumar) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaOriginal);
        calendar.add(Calendar.DAY_OF_MONTH, diasASumar);
        return calendar.getTime();
//        long millOriginal = fechaOriginal.getTime();
//        millOriginal += diasASumar * 24 * 60 * 60 * 1000;
//        return new Date(millOriginal);
//        return dateFormat.format(new Date(millOriginal));
    }
    public static Date sumarMinutosAFecha(Date fechaOriginal, int minutosASumar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millOriginal = fechaOriginal.getTime();
        millOriginal += minutosASumar* 60 * 1000;
        return new Date(millOriginal);
//        return dateFormat.format(new Date(millOriginal));
    }
}
