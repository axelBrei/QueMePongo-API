package utn.frba.dds.que_me_pongo.Utilities.Helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static Date sumarDiasAFecha(Date fechaOriginal, int diasASumar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millOriginal = fechaOriginal.getTime();
        millOriginal += diasASumar * 24 * 60 * 60 * 1000;
        return new Date(millOriginal);
//        return dateFormat.format(new Date(millOriginal));
    }
}
