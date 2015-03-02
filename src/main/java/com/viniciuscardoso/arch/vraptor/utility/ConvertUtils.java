package com.viniciuscardoso.arch.vraptor.utility;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 16:02
 */
public class ConvertUtils {

    //<editor-fold desc="[toString]">
    public static String listToString(List<Long> lista, String sep) {
        String ret = new String();
        for (int i = 0; i < lista.size(); i++) {
            ret += String.valueOf(lista.get(i));
            if (lista.size() != i) {
                ret += sep;
            }
        }
        return ret;
    }

    public static String escapeToHtml(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for( char c : s.toCharArray() ) {
            if( c == ' ' ) {
                if( previousWasASpace ) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch(c) {
                case '<': builder.append("&lt;"); break;
                case '>': builder.append("&gt;"); break;
                case '&': builder.append("&amp;"); break;
                case '"': builder.append("&quot;"); break;
                case '\n': builder.append("<br>"); break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;
                default:
                    if( c < 128 ) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int)c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    public static String convertStringToSafeFilename(String s) {
        String ret = s.toLowerCase();
        ret = ret.replaceAll("[\\\\\\/\\:\\*\\?\\\"\\<\\>\\|\\s]", "-");
        return ret;
    }
    //</editor-fold>

    //<editor-fold desc="[toLocalDateTime]">
    public static LocalDateTime parseDate(String maybeDate, String format) throws Exception {
        LocalDateTime dt;
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
            DateTime dtTime = fmt.parseDateTime(maybeDate);
            dt = dtTime.toLocalDateTime();
        } catch (Exception e) {
            throw new Exception("Data inválida!");
        }
        return dt;
    }
    //</editor-fold>

    //<editor-fold desc="[toLocalDate]">
    public static LocalDate parseDateSavingTimeProof(String maybeDate, String format) throws Exception {
        try {
            String dateStripped = maybeDate.split(" ")[0] + " 01:00:00";
            String formatStripped = format.split(" ")[0] + " HH:mm:ss";
            DateTimeFormatter fmt = DateTimeFormat.forPattern(formatStripped);
            DateTime dtTime = fmt.parseDateTime(dateStripped);
            return dtTime.toLocalDate();
        } catch (Exception var5) {
            throw new Exception("Data inválida!");
        }

    }
    //</editor-fold>

    public static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static int toInteger(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        return bd.intValue();
    }
}
